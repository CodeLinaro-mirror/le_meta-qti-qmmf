SUMMARY = "WPE opensource package groups"
LICENSE = "BSD-3-Clause"

inherit packagegroup

PROVIDES = "${PACKAGES}"

PACKAGES = " \
    packagegroup-wpe \
"

RDEPENDS_packagegroup-wpe = " \
    cog \
    wpewebkit \
"
