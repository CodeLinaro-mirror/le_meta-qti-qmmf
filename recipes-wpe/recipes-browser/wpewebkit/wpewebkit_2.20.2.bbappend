# patches
FILESEXTRAPATHS_prepend := "${THISDIR}/files:"
SRC_URI_append = " \
  file://0001-compositor-timeout-increase.patch \
  file://0001-Merge-r236391-ARM-Building-FELightingNEON.cpp-fails-.patch \
"

# set the configuration options, and build this version in preference
PACKAGECONFIG_pn-wpewebkit = "remote-inspector"
DEFAULT_PREFERENCE = "1"
