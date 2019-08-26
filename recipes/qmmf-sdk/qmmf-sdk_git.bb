inherit cmake pkgconfig

DESCRIPTION = "QMMF SDK"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "\
file://${COMMON_LICENSE_DIR}/${LICENSE};md5=3775480a712fc46a69647678acb234cb\
"

PR = "r0"

SSTATE_DUPWHITELIST = "/"

DEPENDS = "liblog"
DEPENDS += "libcutils"
DEPENDS += "binder"
DEPENDS += "system-core"
DEPENDS += "glib-2.0"
DEPENDS += "av-frameworks"
DEPENDS += "jpeg"
DEPENDS += "gtest"
DEPENDS += "media"
DEPENDS += "cairo"
DEPENDS += "mm-parser"
DEPENDS += "mm-parser-noship"
DEPENDS += "mm-osal"
DEPENDS += "audiohal"
DEPENDS += "qsthw-api"
DEPENDS += "fastcv-noship"
DEPENDS += "jsoncpp"
DEPENDS += "adreno"
DEPENDS += "qmmf-algs"
DEPENDS += "libion"
DEPENDS_append_apq8053 += "camera"
DEPENDS_append_apq8053 += "libjpeg-turbo"

DEPENDS_append_qcs605 += "media-headers"
DEPENDS_append_qcs605 += "weston wayland-native"

DEPENDS_append_sdmsteppe += "media-headers"
DEPENDS_append_sdmsteppe += "weston wayland-native"

SRC_DIR = "${WORKSPACE}/vendor/qcom/opensource/qmmf-sdk"

# Data folder for qmmf sdk
QMMF_DATA = "${userfsdatadir}/misc/qmmf"

EXTRA_OECMAKE += "${BASE_EXTRAS_CMAKE}"
EXTRA_OECMAKE += "-DWORKSPACE=${WORKSPACE}"
EXTRA_OECMAKE += "-DPKG_CONFIG_SYSROOT_DIR=${PKG_CONFIG_SYSROOT_DIR}"
EXTRA_OECMAKE += "-DQMMF_DATA=${QMMF_DATA}"
EXTRA_OECMAKE += "-DQMMF_SDK_INC_DIR=${SRC_DIR}"
EXTRA_OECMAKE += "-DBUILD_CATEGORY=ALL"

FILESPATH =+ "${WORKSPACE}/vendor/qcom/opensource/:"
SRC_URI  := "file://qmmf-sdk"
SRC_URI  += "file://qmmf-server.service"
SRC_URI_append_qcs605 += "file://qmmf-server-qcs605.service"
SRC_URI_append_sdmsteppe += "file://qmmf-server-sdmsteppe.service"
SRC_URI  += "file://recorder_boottest.sh"
SRC_URI  += "file://boottime_config.txt"

S = "${WORKDIR}/qmmf-sdk"

QMMF_SERVICE_FILENAME = "qmmf-server.service"
QMMF_SERVICE_FILENAME_qcs605 = "qmmf-server-qcs605.service"
QMMF_SERVICE_FILENAME_sdmsteppe = "qmmf-server-sdmsteppe.service"

SOLIBS = ".so*"
FILES_SOLIBSDEV = ""

do_install_append () {
    if ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'true', 'false', d)}; then
        install -d ${D}/etc/systemd/system/
        install -m 0644 ${WORKDIR}/${QMMF_SERVICE_FILENAME}  -D ${D}/etc/systemd/system/qmmf-server.service
        install -d ${D}/etc/systemd/system/multi-user.target.wants/
        # enable the service for multi-user.target
        ln -sf /etc/systemd/qmmf-server.service \
           ${D}/etc/systemd/system/multi-user.target.wants/qmmf-server.service
    fi
    install -m 0750 ${WORKDIR}/recorder_boottest.sh -D ${D}/${sysconfdir}/init.d/recorder_boottest.sh
    install -m 0644 ${WORKDIR}/boottime_config.txt -D ${D}/${sysconfdir}/boottime_config.txt
    install -d ${D}/mnt/sdcard/data/misc/qmmf/
    install -d ${D}/${userfsdatadir}/misc/vam
}

PACKAGES =+ "${PN}-qmmf-server"

FILES_${PN}-qmmf-server-dbg = "${bindir}/.debug/qmmf-server"
FILES_${PN}-qmmf-server     = "${bindir}/qmmf-server"
FILES_${PN}-qmmf-server    += "/etc/systemd/system/"
FILES_${PN}-qmmf-server    += "${userfsdatadir}/*"
FILES_${PN}-qmmf-server    += "/mnt/sdcard/data/misc/qmmf/"

FILES_${PN}-libqmmf_recorder_client-dbg    = "${libdir}/.debug/libqmmf_recorder_client.*"
FILES_${PN}-libqmmf_recorder_client        = "${libdir}/libqmmf_recorder_client.so.*"
FILES_${PN}-libqmmf_recorder_client-dev    = "${libdir}/libqmmf_recorder_client.so ${libdir}/libqmmf_recorder_client.la ${includedir}"

FILES_${PN}-libqmmf_recorder_service-dbg    = "${libdir}/.debug/libqmmf_recorder_service.*"
FILES_${PN}-libqmmf_recorder_service        = "${libdir}/libqmmf_recorder_service.so.*"
FILES_${PN}-libqmmf_recorder_service-dev    = "${libdir}/libqmmf_recorder_service.so ${libdir}/libqmmf_recorder_service.la ${includedir}"

FILES_${PN}-libqmmf_display_client-dbg    = "${libdir}/.debug/libqmmf_display_client.*"
FILES_${PN}-libqmmf_display_client        = "${libdir}/libqmmf_display_client.so.*"
FILES_${PN}-libqmmf_display_client-dev    = "${libdir}/libqmmf_display_client.so ${libdir}/libqmmf_display_client.la ${includedir}"

FILES_${PN}-libqmmf_display_service-dbg    = "${libdir}/.debug/libqmmf_display_service.*"
FILES_${PN}-libqmmf_display_service        = "${libdir}/libqmmf_display_service.so.*"
FILES_${PN}-libqmmf_display_service-dev    = "${libdir}/libqmmf_display_service.so ${libdir}/libqmmf_display_service.la ${includedir}"

FILES_${PN}-libcamera_adaptor-dbg    = "${libdir}/.debug/libcamera_adaptor.*"
FILES_${PN}-libcamera_adaptor        = "${libdir}/libcamera_adaptor.so.*"
FILES_${PN}-libcamera_adaptor-dev    = "${libdir}/libcamera_adaptor.so ${libdir}/libcamera_adaptor.la ${includedir}"

FILES_${PN}-libcodec_adaptor-dbg    = "${libdir}/.debug/libcodec_adaptor.*"
FILES_${PN}-libcodec_adaptor        = "${libdir}/libcodec_adaptor.so.*"
FILES_${PN}-libcodec_adaptor-dev    = "${libdir}/libcodec_adaptor.so ${libdir}/libcodec_adaptor.la ${includedir}"

FILES_${PN}-libav_codec-dbg    = "${libdir}/.debug/libav_codec.*"
FILES_${PN}-libav_codec        = "${libdir}/libav_codec.so.*"
FILES_${PN}-libav_codec-dev    = "${libdir}/libav_codec.so ${libdir}/libav_codec.la ${includedir}"

FILES_${PN}-libqmmf_audio_client-dbg    = "${libdir}/.debug/libqmmf_audio_client.*"
FILES_${PN}-libqmmf_audio_client        = "${libdir}/libqmmf_audio_client.so.*"
FILES_${PN}-libqmmf_audio_client-dev    = "${libdir}/libqmmf_audio_client.so ${libdir}/libqmmf_audio_client.la ${includedir}"

FILES_${PN}-libqmmf_audio_service-dbg    = "${libdir}/.debug/libqmmf_audio_service.*"
FILES_${PN}-libqmmf_audio_service        = "${libdir}/libqmmf_audio_service.so.*"
FILES_${PN}-libqmmf_audio_service-dev    = "${libdir}/libqmmf_audio_service.so ${libdir}/libqmmf_audio_service.la ${includedir}"

FILES_${PN} += "${userfsdatadir}/misc/qmmf/*.json"

INSANE_SKIP_${PN} += "build-deps dev-deps file-rdeps dev-so"
do_configure[depends] += "virtual/kernel:do_shared_workdir"
