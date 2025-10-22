inherit cmake pkgconfig

DESCRIPTION = "QMMF SDK"
LICENSE = "BSD-3-Clause-Clear"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta-qti-bsp/files/common-licenses/${LICENSE};md5=3771d4920bd6cdb8cbdf1e8344489ee0"

SSTATE_ALLOW_OVERLAP_FILES = "/"

# Mandatory DISTRO_FEATURES to set for QMMF

REQUIRED_DISTRO_FEATURES += "qti-camera"
REQUIRED_DISTRO_FEATURES += "qti-qmmf"

# Required Dependencies for qmmf-sdk

DEPENDS += "binder"
DEPENDS += "glib-2.0"
DEPENDS += "gtest"
DEPENDS += "libcutils"
DEPENDS += "liblog"
DEPENDS += "gbm"
DEPENDS:append:sdmsteppe += "${@bb.utils.contains('DISTRO_FEATURES', 'qti-camera', 'libcamera-client', '', d)}"
DEPENDS:append:kalama += "${@bb.utils.contains('DISTRO_FEATURES', 'qti-camera', 'libhardware', '', d)}"
DEPENDS:append:kalama += "${@bb.utils.contains('DISTRO_FEATURES', 'qti-camera', 'camera-metadata', '', d)}"
DEPENDS:append:pineapple += "${@bb.utils.contains('DISTRO_FEATURES', 'qti-camera', 'libhardware', '', d)}"
DEPENDS:append:pineapple += "${@bb.utils.contains('DISTRO_FEATURES', 'qti-camera', 'camera-metadata', '', d)}"
DEPENDS:append:kera += "${@bb.utils.contains('DISTRO_FEATURES', 'qti-camera', 'libhardware', '', d)}"
DEPENDS:append:kera += "${@bb.utils.contains('DISTRO_FEATURES', 'qti-camera', 'camera-metadata', '', d)}"
DEPENDS:append:sun += "${@bb.utils.contains('DISTRO_FEATURES', 'qti-camera', 'libhardware', '', d)}"
DEPENDS:append:sun += "${@bb.utils.contains('DISTRO_FEATURES', 'qti-camera', 'camera-metadata', '', d)}"
DEPENDS:append:qrb5165 += "${@bb.utils.contains('DISTRO_FEATURES', 'qti-camera', 'libhardware', '', d)}"
DEPENDS:append:qrb5165 += "${@bb.utils.contains('DISTRO_FEATURES', 'qti-camera', 'camera-metadata', '', d)}"
DEPENDS:append:qrbx210 += "${@bb.utils.contains('DISTRO_FEATURES', 'qti-camera', 'libhardware', '', d)}"
DEPENDS:append:qrbx210 += "${@bb.utils.contains('DISTRO_FEATURES', 'qti-camera', 'camera-metadata', '', d)}"
DEPENDS:append:bengal += "${@bb.utils.contains('DISTRO_FEATURES', 'qti-camera', 'libhardware', '', d)}"
DEPENDS:append:bengal += "${@bb.utils.contains('DISTRO_FEATURES', 'qti-camera', 'camera-metadata', '', d)}"
DEPENDS:append:vienna += "${@bb.utils.contains('DISTRO_FEATURES', 'qti-camera', 'libhardware', '', d)}"
DEPENDS:append:vienna += "${@bb.utils.contains('DISTRO_FEATURES', 'qti-camera', 'camera-metadata', '', d)}"

RDEPENDS:${PN} = "gbm"

# Data folder for qmmf sdk
QMMF_DATA = "/data/misc/qmmf"

EXTRA_OECMAKE += "-DSYSROOT_INCDIR=${STAGING_INCDIR}"
EXTRA_OECMAKE += "-DSYSROOT_LIBDIR=${STAGING_LIBDIR}"
EXTRA_OECMAKE += "-DKERNEL_INCDIR=${STAGING_INCDIR}/linux-msm"
EXTRA_OECMAKE += "-DBUILD_CATEGORY=ALL"
EXTRA_OECMAKE += "-DTARGET_BOARD_PLATFORM=${BASEMACHINE}"
EXTRA_OECMAKE += "-DTARGET_PRODUCT_PLATFORM=${PRODUCT}"
EXTRA_OECMAKE += "-DQMMF_SYSTEMD_DIR=${sysconfdir}/systemd/system"

FILESPATH =+ "${WORKSPACE}/vendor/qcom/opensource/:"
SRC_URI  := "file://qmmf-sdk"
SRC_URI  += "file://recorder_boottest.sh"
SRC_URI  += "file://boottime_config.txt"
SRC_URI  += "file://qmmf-server-env"
SRC_URI:append:qrb5165  += "file://qmmf-server-env_qrb5165"
SRC_URI:append:qrb5165  += "file://camera_cgroup.service"
SRC_URI:append:qrbx210  += "file://qmmf-server-env_qrb5165"
SRC_URI:append:qrbx210  += "file://camera_cgroup.service"
SRC_URI:append:kalama   += "file://qmmf-server-env_wayland"
SRC_URI:append:pineapple   += "file://qmmf-server-env_wayland"
SRC_URI:append:kera     += "file://qmmf-server-env_wayland"
SRC_URI:append:sun   += "file://qmmf-server-env_wayland"
SRC_URI:append:bengal  += "file://qmmf-server-env_qrb5165"
SRC_URI:append:bengal  += "file://camera_cgroup.service"
SRC_URI:append:vienna   += "file://qmmf-server-env_wayland"

S = "${WORKDIR}/qmmf-sdk"

SOLIBS = ".so*"
FILES_SOLIBSDEV = ""

DEBUG_PREFIX_MAP:remove = "-fcanon-prefix-map"

do_install:append () {
    if ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'true', 'false', d)}; then
        install -d ${D}/etc/systemd/system/
        install -d ${D}/etc/systemd/system/multi-user.target.wants/
        # enable the service for multi-user.target
        ln -sf /etc/systemd/system/qmmf-server.service \
           ${D}/etc/systemd/system/multi-user.target.wants/qmmf-server.service
        if [ ${BASEMACHINE} == "qrb5165" ] || [ ${BASEMACHINE} == "qrbx210" ] || [ ${BASEMACHINE} == "qcm2290-mtp" ] || [ ${BASEMACHINE} == "qcm4325-mtp" ]; then
            install -m 0644 ${WORKDIR}/camera_cgroup.service -D ${D}/etc/systemd/system/camera_cgroup.service
            ln -sf /etc/systemd/system/camera_cgroup.service \
               ${D}/etc/systemd/system/multi-user.target.wants/camera_cgroup.service
        fi

    fi
    install -m 0750 ${WORKDIR}/recorder_boottest.sh -D ${D}/${sysconfdir}/init.d/recorder_boottest.sh
    install -m 0644 ${WORKDIR}/boottime_config.txt -D ${D}/${sysconfdir}/boottime_config.txt
    install -d ${D}/mnt/sdcard/data/misc/qmmf/
    install -d ${D}/data/misc/qmmf
    if [ ${BASEMACHINE} == "qrb5165" ] || [ ${BASEMACHINE} == "qrbx210" ] || [ ${BASEMACHINE} == "qcm2290-mtp" ] || [ ${BASEMACHINE} == "qcm4325-mtp" ]; then
        install ${WORKDIR}/qmmf-server-env_qrb5165 -D ${D}/${sysconfdir}/qmmf-server-env
    elif [ ${BASEMACHINE} == "kalama" ] || [ ${BASEMACHINE} == "pineapple" ] || [ ${BASEMACHINE} == "kera" ] || [ ${BASEMACHINE} == "sun" ] || [ ${BASEMACHINE} == "vienna" ]; then
        install ${WORKDIR}/qmmf-server-env_wayland -D ${D}/${sysconfdir}/qmmf-server-env
    else
        install ${WORKDIR}/qmmf-server-env -D ${D}/${sysconfdir}/qmmf-server-env
    fi
}

FILES:${PN}-qmmf-server-dbg = "${bindir}/.debug/qmmf-server"
FILES:${PN}-qmmf-server     = "${bindir}/qmmf-server"
FILES:${PN}-qmmf-server    += "/etc/systemd/system/"
FILES:${PN}-qmmf-server    += "/data/*"
FILES:${PN}-qmmf-server    += "/mnt/sdcard/data/misc/qmmf/"

FILES:${PN}-libqmmf_recorder_client-dbg    = "${libdir}/.debug/libqmmf_recorder_client.*"
FILES:${PN}-libqmmf_recorder_client        = "${libdir}/libqmmf_recorder_client.so.*"
FILES:${PN}-libqmmf_recorder_client-dev    = "${libdir}/libqmmf_recorder_client.so ${libdir}/libqmmf_recorder_client.la ${includedir}"

FILES:${PN}-libqmmf_recorder_service-dbg    = "${libdir}/.debug/libqmmf_recorder_service.*"
FILES:${PN}-libqmmf_recorder_service        = "${libdir}/libqmmf_recorder_service.so.*"
FILES:${PN}-libqmmf_recorder_service-dev    = "${libdir}/libqmmf_recorder_service.so ${libdir}/libqmmf_recorder_service.la ${includedir}"

FILES:${PN}-libcamera_adaptor-dbg    = "${libdir}/.debug/libcamera_adaptor.*"
FILES:${PN}-libcamera_adaptor        = "${libdir}/libcamera_adaptor.so.*"
FILES:${PN}-libcamera_adaptor-dev    = "${libdir}/libcamera_adaptor.so ${libdir}/libcamera_adaptor.la ${includedir}"

FILES:${PN} += "/data/*"
FILES:${PN} += "/mnt/sdcard/data/misc/qmmf"

INSANE_SKIP:${PN} += "build-deps dev-deps file-rdeps dev-so"
do_configure[depends] += "virtual/kernel:do_shared_workdir"

PACKAGE_ARCH = "${MACHINE_ARCH}"
