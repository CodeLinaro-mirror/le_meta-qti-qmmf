DESCRIPTION = "Qualcomm Linux Embedded Camera Server"
LICENSE = "BSD-3-Clause-Clear"
LIC_FILES_CHKSUM = "file://${QCOM_COMMON_LICENSE_DIR}${LICENSE};md5=3771d4920bd6cdb8cbdf1e8344489ee0"

inherit cmake pkgconfig systemd

DEPENDS += "glib-2.0"
DEPENDS += "gtest"
DEPENDS += "protobuf-native protobuf-c protobuf-c-native"
DEPENDS += "gbm"
DEPENDS += "adreno"
DEPENDS:append:pebble = " camx"
DEPENDS:append:pebble = " ${@bb.utils.contains('DISTRO_FEATURES', 'qti-camera', 'libhardware', '', d)}"

RDEPENDS:${PN}:append:pebble = " ${@bb.utils.contains('DISTRO_FEATURES', 'qti-camera', 'libhardware', '', d)}"

FILESPATH =+ "${WORKSPACE}:"

SRC_URI  := "file://le-camera-server"

S = "${WORKDIR}/le-camera-server"

SYSTEMD_SERVICE:${PN} = "qti-cam-server.service"

EXTRA_OECMAKE += " -DTARGET_BOARD_PLATFORM=${BASEMACHINE} "
EXTRA_OECMAKE += "-DCAM_SERVER_USER=system"
EXTRA_OECMAKE += "-DCAM_SERVER_GROUP=video"
EXTRA_OECMAKE += "-DCAM_SERVER_SUPP_GROUP=system"

PACKAGE_ARCH = "${MACHINE_ARCH}"
