inherit go

DESCRIPTION = "IPC Webserver"
SECTION = "networking"

LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/\
${LICENSE};md5=3775480a712fc46a69647678acb234cb"

DEPENDS := "go-cross-arm"
DEPENDS += "github.com-gorilla-muxer"
DEPENDS += "github.com-gorilla-websocket"
DEPENDS += "github.com-bitly-simplejson"
DEPENDS += "libcutils"
DEPENDS += "qmmf-support"
DEPENDS_remove_sdmsteppe = "go-cross-arm"
DEPENDS_append_sdmsteppe = " go-cross-canadian-arm"

FILESPATH =+ "${WORKSPACE}/vendor/qcom/opensource/qmmf-webserver:"
SRC_URI = "file://ipc-webserver"
SRC_URI += "file://ipc-webserver.service"
SRC_URI += "file://ipc-webserver-qcs605.service"
SRC_URI += "file://ipc-webserver-sdmsteppe.service"
SRCREV = "b378caee5b2a673abe3897c40fde529bff7b986e"

S = "${WORKDIR}/ipc-webserver"

FILES_${PN} += "ipc-webserver"

RECIPE_SYSROOT ?= "${STAGING_LIBDIR}/${TARGET_SYS}"
export CGO_ENABLED = "1"
export GOPATH="${S}:${STAGING_LIBDIR}/${TARGET_SYS}/go:${RECIPE_SYSROOT}/usr/lib/go"

do_compile() {
  export GOARCH=${TARGET_GOARCH}
  export CGO_LDFLAGS="$CGO_LDFLAGS -lcutils"
  export CGO_CFLAGS="$CGO_CFLAGS -I${STAGING_INCDIR}/qmmf-support"
  go build -o "${S}/ipc-webserver" ${S}/ipc-webserver.go
}

IPC_WEBSERVER_SERVICE_FILENAME = "ipc-webserver.service"
IPC_WEBSERVER_SERVICE_FILENAME_sdmsteppe = "ipc-webserver-sdmsteppe.service"
IPC_WEBSERVER_SERVICE_FILENAME_qcs605 = "ipc-webserver-qcs605.service"

do_install() {
  install -d ${D}/data/misc/qmmf/ipc_webserver
  install -d ${D}/data/misc/qmmf/ipc_webserver/channel1
  install -d ${D}/data/misc/qmmf/ipc_webserver/channel2
  install -d ${D}/data/misc/qmmf/ipc_webserver/channel3
  install -d ${D}/data/misc/qmmf/ipc_webserver/vam
  install -d ${D}/data/misc/qmmf/ipc_webserver/image
  install -d ${D}/data/misc/qmmf/ipc_webserver/video
  install -d ${D}/${bindir}

  install -m 0755 ${S}/ipc-webserver ${D}/${bindir}
  install -m 0444 ${S}/res_config ${D}/data/misc/qmmf/ipc_webserver
  install -m 0755 ${S}/net_config ${D}/data/misc/qmmf/ipc_webserver
  install -m 0444 ${S}/audio_config ${D}/data/misc/qmmf/ipc_webserver
  install -m 0444 ${S}/360_cam.conf ${D}/data/misc/qmmf/ipc_webserver
  install -m 0444 ${S}/ip_cam.conf ${D}/data/misc/qmmf/ipc_webserver
  if ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'true', 'false', d)}; then
      install -d ${D}/etc/systemd/system/
      install -m 0644 ${WORKDIR}/${IPC_WEBSERVER_SERVICE_FILENAME} -D ${D}/etc/systemd/system/ipc-webserver.service
      install -d ${D}/etc/systemd/system/multi-user.target.wants/
      # enable the service for multi-user.target
      ln -sf /etc/systemd/ipc-webserver.service \
          ${D}/etc/systemd/system/multi-user.target.wants/ipc-webserver.service
  fi
}

sysroot_preprocess() {
  install -d ${SYSROOT_DESTDIR}/data/misc/qmmf/ipc_webserver
  install -d ${SYSROOT_DESTDIR}/data/misc/qmmf/ipc_webserver/channel1
  install -d ${SYSROOT_DESTDIR}/data/misc/qmmf/ipc_webserver/channel2
  install -d ${SYSROOT_DESTDIR}/data/misc/qmmf/ipc_webserver/channel3
  install -d ${SYSROOT_DESTDIR}/data/misc/qmmf/ipc_webserver/vam
  install -d ${SYSROOT_DESTDIR}/data/misc/qmmf/ipc_webserver/image
  install -d ${SYSROOT_DESTDIR}/data/misc/qmmf/ipc_webserver/video
  install -d ${SYSROOT_DESTDIR}/${bindir}

  install -m 0755 ${S}/ipc-webserver ${SYSROOT_DESTDIR}/${bindir}
  install -m 0444 ${S}/res_config ${SYSROOT_DESTDIR}/data/misc/qmmf/ipc_webserver
  install -m 0755 ${S}/net_config ${SYSROOT_DESTDIR}/data/misc/qmmf/ipc_webserver
  install -m 0444 ${S}/audio_config ${SYSROOT_DESTDIR}/data/misc/qmmf/ipc_webserver
  install -m 0444 ${S}/360_cam.conf ${SYSROOT_DESTDIR}/data/misc/qmmf/ipc_webserver
  install -m 0444 ${S}/ip_cam.conf ${SYSROOT_DESTDIR}/data/misc/qmmf/ipc_webserver
}

SYSROOT_PREPROCESS_FUNCS += "sysroot_preprocess"

FILES_${PN} += "/data/misc/qmmf/ipc_webserver/*"
FILES_${PN} += "/etc/systemd/system/"
