inherit cmake pkgconfig

SUMMARY = "Generic examples for system sample apps."
SECTION = "multimedia"

LICENSE = "BSD-3-Clause-Clear"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/${LICENSE};md5=3775480a712fc46a69647678acb234cb"

# Dependencies.
DEPENDS := "gstreamer1.0"

DEPENDS_append_sdmsteppe += "${@bb.utils.contains('MACHINE_FEATURES', 'hibernate', 'data', '', d)}"
DEPENDS_append_sdmsteppe += "${@bb.utils.contains('MACHINE_FEATURES', 'hibernate', 'dbus', '', d)}"

FILESPATH =+ "${WORKSPACE}:"
SRC_URI = "file://vendor/qcom/opensource/system/sample-apps/"
S = "${WORKDIR}/vendor/qcom/opensource/system/sample-apps/"

# Install directries.
INSTALL_BINDIR := "${bindir}"
INSTALL_LIBDIR := "${libdir}"

# S2D stands for "Suspend to Disk"
TARGET_SUPPORTS_S2D := "FALSE"
TARGET_SUPPORTS_S2D_sdmsteppe := "${@bb.utils.contains('MACHINE_FEATURES', 'hibernate', 'TRUE', 'FALSE', d)}"

EXTRA_OECMAKE += "-DGST_VERSION_REQUIRED=1.14.4"
EXTRA_OECMAKE += "-DSYSROOT_INCDIR=${STAGING_INCDIR}"
EXTRA_OECMAKE += "-DSYSROOT_LIBDIR=${STAGING_LIBDIR}"
EXTRA_OECMAKE += "-DKERNEL_BUILDDIR=${STAGING_KERNEL_BUILDDIR}"
EXTRA_OECMAKE += "-DSYSTEM_SAMPLE_APPS_INSTALL_BINDIR=${INSTALL_BINDIR}"
EXTRA_OECMAKE += "-DSYSTEM_SAMPLE_APPS_INSTALL_LIBDIR=${INSTALL_LIBDIR}"
EXTRA_OECMAKE += "-DTARGET_SUPPORTS_S2D=${TARGET_SUPPORTS_S2D}"

FILES_${PN} += "${INSTALL_BINDIR}"
FILES_${PN} += "${INSTALL_LIBDIR}"

SOLIBS = ".so*"
FILES_SOLIBSDEV = ""
