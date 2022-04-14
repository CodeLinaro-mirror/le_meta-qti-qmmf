DESCRIPTION = "github.com/bitly/go-simplejson"

GO_IMPORT = "github.com/bitly/go-simplejson"

inherit go

DEST_DIR :="${PN}-${PV}"
DEST_DIR_remove = "${BBEXTENDVARIANT}-"
SRC_URI = "git://git.codelinaro.org/clo/le/go-simplejson;protocol=git;nobranch=1;rev=39a59b1b2866b3d321f1d6ca8ad9d1748cc6d8ea;destsuffix=${DEST_DIR}/src/${GO_IMPORT}"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://src/${GO_IMPORT}/LICENSE;md5=838c366f69b72c5df05c96dff79b35f2"

FILES_${PN} += "${GOBIN_FINAL}/*"
