inherit cmake pkgconfig

SUMMARY = "QTI open-source wrapper for Codec2"
SECTION = "multimedia"

LICENSE = "BSD-3-Clause-Clear"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta-qti-bsp/files/common-licenses/${LICENSE};md5=3771d4920bd6cdb8cbdf1e8344489ee0"

# Dependencies.
DEPENDS += "codec2"
DEPENDS += "media-external"
DEPENDS += "media-codec2"

FILESPATH =+ "${WORKSPACE}/vendor/qcom/opensource/iot-core-oss/:"

SRC_URI = "file://c2-module/"
S = "${WORKDIR}/c2-module"

# Install directries.
INSTALL_BINDIR := "${bindir}"
INSTALL_LIBDIR := "${libdir}"
INSTALL_INCDIR := "${includedir}"

CODEC2_CONFIG_VERSION := "1.0"
CODEC2_CONFIG_VERSION_kalama := "2.0"

EXTRA_OECMAKE += "-DSYSROOT_INCDIR=${STAGING_INCDIR}"
EXTRA_OECMAKE += "-DSYSROOT_LIBDIR=${STAGING_LIBDIR}"
EXTRA_OECMAKE += "-DKERNEL_BUILDDIR=${STAGING_INCDIR}/linux-msm"
EXTRA_OECMAKE += "-DIOT_CORE_OSS_INSTALL_INCDIR=${INSTALL_INCDIR}"
EXTRA_OECMAKE += "-DIOT_CORE_OSS_INSTALL_BINDIR=${INSTALL_BINDIR}"
EXTRA_OECMAKE += "-DIOT_CORE_OSS_INSTALL_LIBDIR=${INSTALL_LIBDIR}"

EXTRA_OECMAKE += "-DGST_CODEC2_CONFIG_VERSION=${CODEC2_CONFIG_VERSION}"

FILES_${PN} += "${INSTALL_BINDIR}"
FILES_${PN} += "${INSTALL_LIBDIR}"

SOLIBS = ".so*"
FILES_SOLIBSDEV = ""
TOOLCHAIN = "sdllvm"
