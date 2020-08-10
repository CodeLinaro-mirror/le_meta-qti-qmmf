inherit cmake pkgconfig

DESCRIPTION = "QMMF SDK"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "\
file://${COMMON_LICENSE_DIR}/${LICENSE};md5=3775480a712fc46a69647678acb234cb\
"

SSTATE_DUPWHITELIST = "/"

# Mandatory DISTRO_FEATURES to set for QMMF

REQUIRED_DISTRO_FEATURES += "qti-camera"
REQUIRED_DISTRO_FEATURES += "qti-qmmf"
REQUIRED_DISTRO_FEATURES += "qti-video"

# Required Dependencies for qmmf-sdk

DEPENDS += "binder"
DEPENDS += "cairo"
DEPENDS += "glib-2.0"
DEPENDS += "gtest"
DEPENDS += "jpeg"
DEPENDS += "libcutils"
DEPENDS += "libion"
DEPENDS += "liblog"
DEPENDS += "${@bb.utils.contains('DISTRO_FEATURES', 'pulseaudio', 'pulseaudio', '', d)}"
DEPENDS_append_sdmsteppe += "${@bb.utils.contains('DISTRO_FEATURES', 'qti-camera', 'libcamera-client', '', d)}"
DEPENDS_append_qrb5165 += "${@bb.utils.contains('DISTRO_FEATURES', 'qti-camera', 'libhardware', '', d)}"
DEPENDS_append_qrb5165 += "${@bb.utils.contains('DISTRO_FEATURES', 'qti-camera', 'camera-metadata', '', d)}"
DEPENDS += "${@bb.utils.contains('DISTRO_FEATURES', 'wayland', 'wayland-native weston', '', d)}"
DEPENDS += "${@bb.utils.contains('DISTRO_FEATURES', 'qti-qmmf-legacy', 'system-core av-frameworks', '', d)}"
DEPENDS += "${@bb.utils.contains('DISTRO_FEATURES', 'qti-video', 'media media-headers', '', d)}"

PACKAGECONFIG ??= " \
${@bb.utils.contains('DISTRO_FEATURES', 'pulseaudio', 'audio', '', d)} \
${@bb.utils.contains('DISTRO_FEATURES', 'qti-video', 'avcodec', '', d)} \
${@bb.utils.contains('DISTRO_FEATURES', 'jpeg', 'jpeg', '', d)} \
${@bb.utils.contains('TARGET_ARCH', 'arm', 'neonresizer', '', d)} \
"

PACKAGECONFIG[audio] = " -D_ENABLE_AUDIO=true, -D_ENABLE_AUDIO=false,,"
PACKAGECONFIG[avcodec] = " -D_ENABLE_AVCODEC=true, -D_ENABLE_AVCODEC=false,,"
PACKAGECONFIG[jpeg] = " -D_ENABLE_JPEG=true, -D_ENABLE_JPEG=false,,"
PACKAGECONFIG[neonresizer] = "-DRESIZER_NEON_ENABLED=1, -DRESIZER_NEON_ENABLED=0"


# Data folder for qmmf sdk
QMMF_DATA = "/data/misc/qmmf"

EXTRA_OECMAKE += "-DSYSROOT_INCDIR=${STAGING_INCDIR}"
EXTRA_OECMAKE += "-DSYSROOT_LIBDIR=${STAGING_LIBDIR}"
EXTRA_OECMAKE += "-DKERNEL_INCDIR=${STAGING_KERNEL_BUILDDIR}"
EXTRA_OECMAKE += "-DWORKSPACE=${WORKSPACE}"
EXTRA_OECMAKE += "-DPKG_CONFIG_SYSROOT_DIR=${PKG_CONFIG_SYSROOT_DIR}"
EXTRA_OECMAKE += "-DQMMF_DATA=${QMMF_DATA}"
EXTRA_OECMAKE += "-DQMMF_SDK_INC_DIR=${SRC_DIR}"
EXTRA_OECMAKE += "-DBUILD_CATEGORY=ALL"
EXTRA_OECMAKE += "-DTARGET_BOARD_PLATFORM=${BASEMACHINE}"
EXTRA_OECMAKE += "-DQMMF_SYSTEMD_DIR=${sysconfdir}/systemd/system"

FILESPATH =+ "${WORKSPACE}/vendor/qcom/opensource/:"
SRC_URI  := "file://qmmf-sdk"
SRC_URI  += "file://recorder_boottest.sh"
SRC_URI  += "file://boottime_config.txt"
SRC_URI  += "file://qmmf-server-env"

S = "${WORKDIR}/qmmf-sdk"

SOLIBS = ".so*"
FILES_SOLIBSDEV = ""

do_install_append () {
    if ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'true', 'false', d)}; then
        install -d ${D}/etc/systemd/system/
        install -d ${D}/etc/systemd/system/multi-user.target.wants/
        # enable the service for multi-user.target
        ln -sf /etc/systemd/system/qmmf-server.service \
           ${D}/etc/systemd/system/multi-user.target.wants/qmmf-server.service
    fi
    install -m 0750 ${WORKDIR}/recorder_boottest.sh -D ${D}/${sysconfdir}/init.d/recorder_boottest.sh
    install -m 0644 ${WORKDIR}/boottime_config.txt -D ${D}/${sysconfdir}/boottime_config.txt
    install -d ${D}/mnt/sdcard/data/misc/qmmf/
    install -d ${D}/data/misc/qmmf
    install ${WORKDIR}/qmmf-server-env -D ${D}/${sysconfdir}/qmmf-server-env
}

FILES_${PN}-qmmf-server-dbg = "${bindir}/.debug/qmmf-server"
FILES_${PN}-qmmf-server     = "${bindir}/qmmf-server"
FILES_${PN}-qmmf-server    += "/etc/systemd/system/"
FILES_${PN}-qmmf-server    += "/data/*"
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

FILES_${PN} += "/data/misc/qmmf/*.json"
FILES_${PN} += "/data/*"

INSANE_SKIP_${PN} += "build-deps dev-deps file-rdeps dev-so"
do_configure[depends] += "virtual/kernel:do_shared_workdir"

PACKAGE_ARCH = "${MACHINE_ARCH}"
