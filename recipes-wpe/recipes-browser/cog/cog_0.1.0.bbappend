# build this version in preference to the others
DEFAULT_PREFERENCE = "1"

SRC_URI_remove = "https://github.com/Igalia/${PN}/archive/v${PV}.tar.gz"
SRC_URI += "${CAF_MIRROR}/cog/v0.1.0.tar.gz"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-${PV}:"
SRC_URI += "file://cog-utils.h.patch"
