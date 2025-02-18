SUMMARY = "QTI QMMF opensource package groups"
LICENSE = "BSD-3-Clause"

inherit packagegroup

PROVIDES = "${PACKAGES}"

PACKAGES = ' \
    packagegroup-qti-qmmf \
    ${@bb.utils.contains("DISTRO_FEATURES", "qti-qmmf", bb.utils.contains("DISTRO_FEATURES", "qti-camera", "packagegroup-qti-qmmf-sdk", "", d), "", d)}  \
'

RDEPENDS:packagegroup-qti-qmmf = ' \
    ${@bb.utils.contains("DISTRO_FEATURES", "qti-qmmf", bb.utils.contains("DISTRO_FEATURES", "qti-camera", "packagegroup-qti-qmmf-sdk", "", d), "", d)} \
    ${@bb.utils.contains("DISTRO_FEATURES", "pulseaudio", bb.utils.contains("DISTRO_FEATURES", "qti-audio", "packagegroup-qti-pulseaudio", "", d), "", d)} \
'

RDEPENDS:packagegroup-qti-qmmf-sdk = ' \
    qmmf-sdk \
'

RDEPENDS:packagegroup-qti-qmmf:remove:kalama = "packagegroup-qti-pulseaudio"
RDEPENDS:packagegroup-qti-qmmf:remove:pineapple = "packagegroup-qti-pulseaudio"
