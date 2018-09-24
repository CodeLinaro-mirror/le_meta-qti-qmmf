DESCRIPTION = "github.com/gorilla/websocket"

GO_IMPORT = "github.com/gorilla/websocket"

inherit go

DEPENDS = "github.com-gorilla-context"

DEST_DIR :="${PN}-${PV}"
DEST_DIR_remove = "${BBEXTENDVARIANT}-"
SRC_URI = "git://github.com/gorilla/websocket;protocol=https;destsuffix=${DEST_DIR}/src/${GO_IMPORT}"
SRCREV = "b378caee5b2a673abe3897c40fde529bff7b986e"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://src/${GO_IMPORT}/LICENSE;md5=c007b54a1743d596f46b2748d9f8c044"

FILES_${PN} += "${GOBIN_FINAL}/*"
