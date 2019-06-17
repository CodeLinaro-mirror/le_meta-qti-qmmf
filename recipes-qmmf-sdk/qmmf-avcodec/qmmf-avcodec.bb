inherit cmake sdllvm

DESCRIPTION = "AVCODEC"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "\
file://${COMMON_LICENSE_DIR}/${LICENSE};md5=3775480a712fc46a69647678acb234cb\
"

PR = "r0"

DEPENDS += "${BASE_DEPENDS}"
DEPENDS += "media"
DEPENDS += "jpeg"
DEPENDS += "libion"

FILESPATH =+ "${WORKSPACE}/vendor/qcom/opensource:"
SRC_URI := "file://qmmf-sdk"

S = "${WORKDIR}/qmmf-sdk"
SRC_DIR = "${WORKSPACE}/vendor/qcom/opensource/qmmf-sdk"

EXTRA_OECMAKE += "${BASE_EXTRAS_CMAKE}"
EXTRA_OECMAKE += "-DWORKSPACE=${WORKSPACE}"
EXTRA_OECMAKE += "-DPKG_CONFIG_SYSROOT_DIR=${PKG_CONFIG_SYSROOT_DIR}"
EXTRA_OECMAKE += "-DQMMF_SDK_INC_DIR=${SRC_DIR}"
EXTRA_OECMAKE += "-DBUILD_CATEGORY=AVCODEC"

FILES_${PN}-libcodec_adaptor-dbg    = "${libdir}/.debug/libcodec_adaptor.*"
FILES_${PN}-libcodec_adaptor        = "${libdir}/libcodec_adaptor.so.*"
FILES_${PN}-libcodec_adaptor-dev    = "${libdir}/libcodec_adaptor.so ${libdir}/libcodec_adaptor.la ${includedir}"

FILES_${PN}-libav_codec-dbg    = "${libdir}/.debug/libav_codec.*"
FILES_${PN}-libav_codec        = "${libdir}/libav_codec.so.*"
FILES_${PN}-libav_codec-dev    = "${libdir}/libav_codec.so ${libdir}/libav_codec.la ${includedir}"

SOLIBS = ".so*"
FILES_SOLIBSDEV = ""