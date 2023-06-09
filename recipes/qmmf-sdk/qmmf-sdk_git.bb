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

# Required Dependencies for qmmf-sdk

DEPENDS += "binder"
DEPENDS += "glib-2.0"
DEPENDS += "gtest"
DEPENDS += "libcutils"
DEPENDS += "liblog"
DEPENDS += "gbm"
DEPENDS_append_sdmsteppe += "${@bb.utils.contains('DISTRO_FEATURES', 'qti-camera', 'libcamera-client', '', d)}"
DEPENDS_append_qrb5165 += "${@bb.utils.contains('DISTRO_FEATURES', 'qti-camera', 'libhardware', '', d)}"
DEPENDS_append_qrb5165 += "${@bb.utils.contains('DISTRO_FEATURES', 'qti-camera', 'camera-metadata', '', d)}"
DEPENDS_append_qrbx210 += "${@bb.utils.contains('DISTRO_FEATURES', 'qti-camera', 'libhardware', '', d)}"
DEPENDS_append_qrbx210 += "${@bb.utils.contains('DISTRO_FEATURES', 'qti-camera', 'camera-metadata', '', d)}"
DEPENDS_append_qcs6490 += "${@bb.utils.contains('DISTRO_FEATURES', 'qti-camera', 'libhardware', '', d)}"
DEPENDS_append_qcs6490 += "${@bb.utils.contains('DISTRO_FEATURES', 'qti-camera', 'camera-metadata', '', d)}"

RDEPENDS_${PN} = "gbm"

# Data folder for qmmf sdk
QMMF_DATA = "/data/misc/qmmf"

GBM_FREE_FD := "FALSE"

EXTRA_OECMAKE += "-DSYSROOT_INCDIR=${STAGING_INCDIR}"
EXTRA_OECMAKE += "-DSYSROOT_LIBDIR=${STAGING_LIBDIR}"
EXTRA_OECMAKE += "-DKERNEL_INCDIR=${STAGING_KERNEL_BUILDDIR}"
EXTRA_OECMAKE += "-DBUILD_CATEGORY=ALL"
EXTRA_OECMAKE += "-DTARGET_BOARD_PLATFORM=${BASEMACHINE}"
EXTRA_OECMAKE += "-DTARGET_PRODUCT_PLATFORM=${PRODUCT}"
EXTRA_OECMAKE += "-DQMMF_SYSTEMD_DIR=${sysconfdir}/systemd/system"
EXTRA_OECMAKE += "-DGBM_FREE_FD=${GBM_FREE_FD}"

FILESPATH =+ "${WORKSPACE}/vendor/qcom/opensource/:"
SRC_URI  := "file://qmmf-sdk"
SRC_URI  += "file://recorder_boottest.sh"
SRC_URI  += "file://boottime_config.txt"
SRC_URI  += "file://qmmf-server-env"
SRC_URI_append_qrb5165  += "file://qmmf-server-env_qrb5165"
SRC_URI_append_qrb5165  += "file://camera_cgroup.service"
SRC_URI_append_qrbx210  += "file://qmmf-server-env_qrb5165"
SRC_URI_append_qrbx210  += "file://camera_cgroup.service"
SRC_URI_append_qcs6490  += "file://qmmf-server-env_qrb5165"
SRC_URI_append_qcs6490  += "file://camera_cgroup.service"

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
        if [ ${BASEMACHINE} == "qrb5165" ] || [ ${BASEMACHINE} == "qrbx210" ] || [ ${BASEMACHINE} == "qcs6490" ]; then
            install -m 0644 ${WORKDIR}/camera_cgroup.service -D ${D}/etc/systemd/system/camera_cgroup.service
            ln -sf /etc/systemd/system/camera_cgroup.service \
               ${D}/etc/systemd/system/multi-user.target.wants/camera_cgroup.service
        fi

    fi
    install -m 0750 ${WORKDIR}/recorder_boottest.sh -D ${D}/${sysconfdir}/init.d/recorder_boottest.sh
    install -m 0644 ${WORKDIR}/boottime_config.txt -D ${D}/${sysconfdir}/boottime_config.txt
    install -d ${D}/mnt/sdcard/data/misc/qmmf/
    install -d ${D}/data/misc/qmmf
    if [ ${BASEMACHINE} == "qrb5165" ] || [ ${BASEMACHINE} == "qrbx210" ] || [ ${BASEMACHINE} == "qcs6490" ]; then
        install ${WORKDIR}/qmmf-server-env_qrb5165 -D ${D}/${sysconfdir}/qmmf-server-env
    else
        install ${WORKDIR}/qmmf-server-env -D ${D}/${sysconfdir}/qmmf-server-env
    fi
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

FILES_${PN}-libcamera_adaptor-dbg    = "${libdir}/.debug/libcamera_adaptor.*"
FILES_${PN}-libcamera_adaptor        = "${libdir}/libcamera_adaptor.so.*"
FILES_${PN}-libcamera_adaptor-dev    = "${libdir}/libcamera_adaptor.so ${libdir}/libcamera_adaptor.la ${includedir}"

FILES_${PN} += "/data/*"

INSANE_SKIP_${PN} += "build-deps dev-deps file-rdeps dev-so"
do_configure[depends] += "virtual/kernel:do_shared_workdir"

PACKAGE_ARCH = "${MACHINE_ARCH}"
