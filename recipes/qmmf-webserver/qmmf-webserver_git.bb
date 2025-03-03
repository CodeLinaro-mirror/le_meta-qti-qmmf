inherit go

DESCRIPTION = "QMMF Webserver"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/\
${LICENSE};md5=89aea4e17d99a7cacdbeed46a0096b10"

DEPENDS := "go-cross-arm"
DEPENDS += "github.com-gorilla-muxer"
DEPENDS += "qmmf-support"
DEPENDS:remove:sdmsteppe = "go-cross-arm"
DEPENDS:append:sdmsteppe = " go-cross-canadian-arm"

FILESPATH =+ "${WORKSPACE}/vendor/qcom/opensource/:"
SRC_URI  := "file://qmmf-webserver"
SRC_URI  += "file://qmmf-webserver.service"
SRC_URI  += "file://qmmf-webserver-qcs605.service"
SRC_URI  += "file://qmmf-webserver-sdmsteppe.service"
SRC_URI  += "file://0001-qmmf-webserver-update-http-library-path.patch"
S = "${WORKDIR}/qmmf-webserver"

RECIPE_SYSROOT ?= "${STAGING_LIBDIR}/${TARGET_SYS}"
export CGO_ENABLED = "1"
export GOPATH="${S}:${STAGING_LIBDIR}/${TARGET_SYS}/go:${RECIPE_SYSROOT}/usr/lib/go"

do_compile() {
  export GOARCH=${TARGET_GOARCH}
  export CGO_LDFLAGS="$CGO_LDFLAGS -lcutils"
  export CGO_CFLAGS="$CGO_CFLAGS -DLOG_LEVEL_KPI"
  go build -o "${S}/qmmf-webserver" ${S}/qmmf-webserver.go
}

QMMF_WEBSERVER_SERVICE_FILENAME = "qmmf-webserver.service"
QMMF_WEBSERVER_SERVICE_FILENAME_qcs605 = "qmmf-webserver-qcs605.service"
QMMF_WEBSERVER_SERVICE_FILENAME_sdmsteppe = "qmmf-webserver-sdmsteppe.service"

do_install() {
  install -d "${D}/${bindir}"
  install -m 0755 "${S}/qmmf-webserver" "${D}/${bindir}"
  if ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'true', 'false', d)}; then
      install -d ${D}/etc/systemd/system/
      install -m 0644 ${WORKDIR}/${QMMF_WEBSERVER_SERVICE_FILENAME} -D ${D}/etc/systemd/system/qmmf-webserver.service
      install -d ${D}/etc/systemd/system/multi-user.target.wants/
      # enable the service for multi-user.target
      ln -sf /etc/systemd/qmmf-webserver.service \
          ${D}/etc/systemd/system/multi-user.target.wants/qmmf-webserver.service
  fi
  # Location for user configs
  install -d ${D}/${userfsdatadir}/misc/qmmf
}

FILES:${PN} = "${bindir}/qmmf-webserver"
FILES:${PN} += "/etc/systemd/system/"
FILES:${PN} += "${userfsdatadir}/*"

# Avoid QA Issue: No GNU_HASH in the elf binary
INSANE_SKIP:${PN} = "ldflags"
