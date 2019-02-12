inherit cmake sdllvm

DESCRIPTION = "COMMON"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "\
file://${COMMON_LICENSE_DIR}/${LICENSE};md5=3775480a712fc46a69647678acb234cb\
"

PR = "r0"

SSTATE_DUPWHITELIST = "/"

DEPENDS += "${BASE_DEPENDS}"
DEPENDS += "audiohal"
DEPENDS += "fastcv-noship"
DEPENDS += "av-frameworks"
DEPENDS += "media"
DEPENDS += "cairo"
DEPENDS += "qmmf-algs"
DEPENDS += "libion"

FILESPATH =+ "${WORKSPACE}/vendor/qcom/opensource:"
SRC_URI := "file://qmmf-sdk"

S = "${WORKDIR}/qmmf-sdk"
SRC_DIR = "${WORKSPACE}/vendor/qcom/opensource/qmmf-sdk"

EXTRA_OECMAKE += "${BASE_EXTRAS_CMAKE}"
EXTRA_OECMAKE += "-DWORKSPACE=${WORKSPACE}"
EXTRA_OECMAKE += "-DPKG_CONFIG_SYSROOT_DIR=${PKG_CONFIG_SYSROOT_DIR}"
EXTRA_OECMAKE += "-DQMMF_SDK_INC_DIR=${SRC_DIR}"
EXTRA_OECMAKE += "-DBUILD_CATEGORY=BASE"

FILES_${PN}-libqmmf_audio_client-dbg    = "${libdir}/.debug/libqmmf_audio_client.*"
FILES_${PN}-libqmmf_audio_client        = "${libdir}/libqmmf_audio_client.so.*"
FILES_${PN}-libqmmf_audio_client-dev    = "${libdir}/libqmmf_audio_client.so ${libdir}/libqmmf_audio_client.la ${includedir}"

FILES_${PN}-libqmmf_audio_service-dbg    = "${libdir}/.debug/libqmmf_audio_service.*"
FILES_${PN}-libqmmf_audio_service        = "${libdir}/libqmmf_audio_service.so.*"
FILES_${PN}-libqmmf_audio_service-dev    = "${libdir}/libqmmf_audio_service.so ${libdir}/libqmmf_audio_service.la ${includedir}"

FILES_${PN}-libcamera_adaptor-dbg    = "${libdir}/.debug/libcamera_adaptor.*"
FILES_${PN}-libcamera_adaptor        = "${libdir}/libcamera_adaptor.so.*"
FILES_${PN}-libcamera_adaptor-dev    = "${libdir}/libcamera_adaptor.so ${libdir}/libcamera_adaptor.la ${includedir}"

SOLIBS = ".so*"
FILES_SOLIBSDEV = ""