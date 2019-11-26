SUMMARY = "QTI QMMF opensource package groups"
LICENSE = "BSD-3-Clause"

inherit packagegroup

PROVIDES = "${PACKAGES}"

PACKAGES = ' \
    packagegroup-qti-qmmf \
    ${@bb.utils.contains("DISTRO_FEATURES", "qti-qmmf", bb.utils.contains("COMBINED_FEATURES", "qti-camera qti-video", "packagegroup-qti-qmmf-sdk", "", d), "", d)}  \
'

RDEPENDS_packagegroup-qti-qmmf = ' \
    ${@bb.utils.contains("DISTRO_FEATURES", "qti-qmmf", bb.utils.contains("COMBINED_FEATURES", "qti-camera qti-video", "packagegroup-qti-qmmf-sdk", "", d), "", d)}  \
'

RDEPENDS_packagegroup-qti-qmmf = ' \
    ${@bb.utils.contains("DISTRO_FEATURES", "qti-qmmf", bb.utils.contains("COMBINED_FEATURES", "qti-camera qti-video", "packagegroup-qti-qmmf-sdk", "", d), "", d)} \
    ${@bb.utils.contains("DISTRO_FEATURES", "qti-qmmf pulseaudio", bb.utils.contains("COMBINED_FEATURES", "qti-audio", "packagegroup-qti-pulseaudio", "", d), "", d)} \
'

RDEPENDS_packagegroup-qti-qmmf-sdk = ' \
    jsoncpp \
    qmmf-sdk \
'
