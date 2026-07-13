inherit cmake pkgconfig

SUMMARY = "Generic examples for system sample apps."
SECTION = "multimedia"

LICENSE = "BSD-3-Clause-Clear"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta-qti-bsp/files/common-licenses/${LICENSE};md5=3771d4920bd6cdb8cbdf1e8344489ee0"

# Dependencies.
DEPENDS := "gstreamer1.0"

DEPENDS:append:kalama = " ${@bb.utils.contains('MACHINE_FEATURES', 'hibernate', 'data', '', d)}"
DEPENDS:append:kalama = " ${@bb.utils.contains('MACHINE_FEATURES', 'hibernate', 'dbus', '', d)}"
DEPENDS:append:pineapple = " ${@bb.utils.contains('MACHINE_FEATURES', 'hibernate', 'data', '', d)}"
DEPENDS:append:pineapple = " ${@bb.utils.contains('MACHINE_FEATURES', 'hibernate', 'dbus', '', d)}"
DEPENDS:append:kera = " ${@bb.utils.contains('MACHINE_FEATURES', 'hibernate', 'data', '', d)}"
DEPENDS:append:kera = " ${@bb.utils.contains('MACHINE_FEATURES', 'hibernate', 'dbus', '', d)}"
DEPENDS:append:alor = "${@bb.utils.contains('MACHINE_FEATURES', 'hibernate', 'data', '', d)}"
DEPENDS:append:alor = "${@bb.utils.contains('MACHINE_FEATURES', 'hibernate', 'dbus', '', d)}"
DEPENDS:append:pebble = "${@bb.utils.contains('MACHINE_FEATURES', 'hibernate', 'data', '', d)}"
DEPENDS:append:pebble = "${@bb.utils.contains('MACHINE_FEATURES', 'hibernate', 'dbus', '', d)}"

FILESPATH =+ "${WORKSPACE}:"
SRC_URI = "file://vendor/qcom/opensource/system/sample-apps/"
S = "${WORKDIR}/vendor/qcom/opensource/system/sample-apps"

# Install directries.
INSTALL_BINDIR := "${bindir}"
INSTALL_LIBDIR := "${libdir}"

# S2D stands for "Suspend to Disk"
TARGET_SUPPORTS_S2D := "FALSE"
TARGET_SUPPORTS_S2D:kalama := "${@bb.utils.contains('MACHINE_FEATURES', 'hibernate', 'TRUE', 'FALSE', d)}"
TARGET_SUPPORTS_S2D:pineapple := "${@bb.utils.contains('MACHINE_FEATURES', 'hibernate', 'TRUE', 'FALSE', d)}"
TARGET_SUPPORTS_S2D:kera := "${@bb.utils.contains('MACHINE_FEATURES', 'hibernate', 'TRUE', 'FALSE', d)}"
TARGET_SUPPORTS_S2D:alor := "${@bb.utils.contains('MACHINE_FEATURES', 'hibernate', 'TRUE', 'FALSE', d)}"
TARGET_SUPPORTS_S2D:pebble := "${@bb.utils.contains('MACHINE_FEATURES', 'hibernate', 'TRUE', 'FALSE', d)}"

EXTRA_OECMAKE += "-DGST_VERSION_REQUIRED=1.14.4"
EXTRA_OECMAKE += "-DSYSROOT_INCDIR=${STAGING_INCDIR}"
EXTRA_OECMAKE += "-DSYSROOT_LIBDIR=${STAGING_LIBDIR}"
EXTRA_OECMAKE += "-DKERNEL_BUILDDIR=${STAGING_KERNEL_BUILDDIR}"
EXTRA_OECMAKE += "-DSYSTEM_SAMPLE_APPS_INSTALL_BINDIR=${INSTALL_BINDIR}"
EXTRA_OECMAKE += "-DSYSTEM_SAMPLE_APPS_INSTALL_LIBDIR=${INSTALL_LIBDIR}"
EXTRA_OECMAKE += "-DTARGET_SUPPORTS_S2D=${TARGET_SUPPORTS_S2D}"

FILES:${PN} += "${INSTALL_BINDIR}"
FILES:${PN} += "${INSTALL_LIBDIR}"

SOLIBS = ".so*"
FILES_SOLIBSDEV = ""

# This is only until other apps are added.
do_install[noexec] = "1"
do_install[noexec] = "${@bb.utils.contains('BASEMACHINE', 'kalama', bb.utils.contains('MACHINE_FEATURES', 'hibernate', '0', '1', d), '', d)}"
do_install[noexec] = "${@bb.utils.contains('BASEMACHINE', 'pineapple', bb.utils.contains('MACHINE_FEATURES', 'hibernate', '0', '1', d), '', d)}"
do_install[noexec] = "${@bb.utils.contains('BASEMACHINE', 'kera', bb.utils.contains('MACHINE_FEATURES', 'hibernate', '0', '1', d), '', d)}"
do_install[noexec] = "${@bb.utils.contains('BASEMACHINE', 'alor', bb.utils.contains('MACHINE_FEATURES', 'hibernate', '0', '1', d), '', d)}"
do_install[noexec] = "${@bb.utils.contains('BASEMACHINE', 'pebble', bb.utils.contains('MACHINE_FEATURES', 'hibernate', '0', '1', d), '', d)}"
