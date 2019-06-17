inherit cmake sdllvm

DESCRIPTION = "QMMF SERVER"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "\
file://${COMMON_LICENSE_DIR}/${LICENSE};md5=3775480a712fc46a69647678acb234cb\
"

PR = "r0"

SSTATE_DUPWHITELIST = "/"

DEPENDS += "${BASE_DEPENDS}"
DEPENDS += "fastcv-noship"
DEPENDS += "qsthw-api"
DEPENDS += "av-frameworks"
DEPENDS += "media"
DEPENDS += "cairo"
DEPENDS += "jsoncpp"
DEPENDS += "audiohal"
DEPENDS += "mm-parser"
DEPENDS += "mm-parser-noship"
DEPENDS += "mm-osal"
DEPENDS += "qmmf-algs"
DEPENDS += "libion"
DEPENDS_append_qcs605 += "weston wayland-native"
DEPENDS_append_sdmsteppe += "weston wayland-native"

FILESPATH =+ "${WORKSPACE}/vendor/qcom/opensource:"
SRC_URI := "file://qmmf-sdk"

S = "${WORKDIR}/qmmf-sdk"
SRC_DIR = "${WORKSPACE}/vendor/qcom/opensource/qmmf-sdk"

EXTRA_OECMAKE += "${BASE_EXTRAS_CMAKE}"
EXTRA_OECMAKE += "-DWORKSPACE=${WORKSPACE}"
EXTRA_OECMAKE += "-DPKG_CONFIG_SYSROOT_DIR=${PKG_CONFIG_SYSROOT_DIR}"
EXTRA_OECMAKE += "-DQMMF_SDK_INC_DIR=${SRC_DIR}"
EXTRA_OECMAKE += "-DBUILD_CATEGORY=SERVER"

FILES_${PN}-qmmf-server-dbg = "${bindir}/.debug/qmmf-server"
FILES_${PN}-qmmf-server     = "${bindir}/qmmf-server"
FILES_${PN}-qmmf-server    += "/etc/systemd/system/"
FILES_${PN}-qmmf-server    += "${userfsdatadir}/*"

SOLIBS = ".so*"
FILES_SOLIBSDEV = ""