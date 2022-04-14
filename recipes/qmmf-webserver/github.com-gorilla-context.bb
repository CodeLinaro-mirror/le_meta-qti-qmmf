DESCRIPTION = "github.com/gorilla/context"

GO_IMPORT = "github.com/gorilla/context"

inherit go

DEST_DIR :="${PN}-${PV}"
DEST_DIR_remove = "${BBEXTENDVARIANT}-"
SRC_URI = "git://git.codelinaro.org/clo/le/gorilla/context;protocol=git;nobranch=1;rev=08b5f424b9271eedf6f9f0ce86cb9396ed337a42;destsuffix=${DEST_DIR}/src/${GO_IMPORT}"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://src/${GO_IMPORT}/LICENSE;md5=c50f6bd9c1e15ed0bad3bea18e3c1b7f"

FILES_${PN} += "${GOBIN_FINAL}/*"
