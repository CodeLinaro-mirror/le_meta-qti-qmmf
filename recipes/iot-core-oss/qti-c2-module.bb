inherit cmake pkgconfig

SUMMARY = "QTI open-source wrapper for Codec2"
SECTION = "multimedia"

LICENSE = "BSD-3-Clause-Clear"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta-qti-bsp/files/common-licenses/${LICENSE};md5=3771d4920bd6cdb8cbdf1e8344489ee0"

# Dependencies.
DEPENDS += "codec2"
DEPENDS:append:qrb5165 += "media-codec2"
DEPENDS:append:kalama += "media"
DEPENDS:append:kalama += "media-external"
DEPENDS:append:kalama += "audio-codec2"
DEPENDS:append:pineapple += "media"
DEPENDS:append:pineapple += "media-external"
DEPENDS:append:pineapple += "audio-codec2"
DEPENDS:append:qcm2290-mtp += "media"
DEPENDS:append:qcm2290-mtp += "media-external"
DEPENDS:append:sun += "media"
DEPENDS:append:sun += "media-external"

FILESPATH =+ "${WORKSPACE}/vendor/qcom/opensource/iot-core-oss/:"

SRC_URI = "file://c2-module/"
S = "${WORKDIR}/c2-module"

# Install directries.
INSTALL_BINDIR := "${bindir}"
INSTALL_LIBDIR := "${libdir}"
INSTALL_INCDIR := "${includedir}"

CODEC2_CONFIG_VERSION := "1.0"
CODEC2_CONFIG_VERSION:kalama := "2.0"
ENABLE_AUDIO_PLUGINS:kalama := "TRUE"
CODEC2_CONFIG_VERSION:pineapple := "2.0"
ENABLE_AUDIO_PLUGINS:pineapple := "TRUE"
CODEC2_CONFIG_VERSION:qcm2290-mtp := "2.0"
CODEC2_CONFIG_VERSION:sun := "2.0"

EXTRA_OECMAKE += "-DSYSROOT_INCDIR=${STAGING_INCDIR}"
EXTRA_OECMAKE += "-DSYSROOT_LIBDIR=${STAGING_LIBDIR}"
EXTRA_OECMAKE += "-DKERNEL_BUILDDIR=${STAGING_INCDIR}/linux-msm"
EXTRA_OECMAKE += "-DIOT_CORE_OSS_INSTALL_INCDIR=${INSTALL_INCDIR}"
EXTRA_OECMAKE += "-DIOT_CORE_OSS_INSTALL_BINDIR=${INSTALL_BINDIR}"
EXTRA_OECMAKE += "-DIOT_CORE_OSS_INSTALL_LIBDIR=${INSTALL_LIBDIR}"
EXTRA_OECMAKE += "-DGST_ENABLE_AUDIO_PLUGINS=${ENABLE_AUDIO_PLUGINS}"

EXTRA_OECMAKE += "-DGST_CODEC2_CONFIG_VERSION=${CODEC2_CONFIG_VERSION}"

FILES:${PN} += "${INSTALL_BINDIR}"
FILES:${PN} += "${INSTALL_LIBDIR}"

SOLIBS = ".so*"
FILES_SOLIBSDEV = ""
TOOLCHAIN = "sdllvm"
DEBUG_PREFIX_MAP:remove = "-fcanon-prefix-map"
