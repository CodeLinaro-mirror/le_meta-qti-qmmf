inherit cmake

SUMMARY = "Protocol Buffers - structured data serialisation mechanism"
DESCRIPTION = "\
Protocol Buffers are a way of encoding structured data in an efficient yet \
extensible format. Google uses Protocol Buffers for almost all of its \
internal RPC protocols and file formats."

HOMEPAGE = "https://github.com/google/protobuf"
SECTION = "console/tools"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=37b5762e07f0af8c74ce80a8bda4266b"

SRCREV = "48cb18e5c419ddd23d9badcfe4e9df7bde1979b2"
SRC_URI = "git://source.codeaurora.org/quic/le/protobuf.git;protocol=git;branch=protobuf/master"

PV = "3.6.1+git${SRCPV}"
S = "${WORKDIR}/git"

DEPENDS = "zlib"

OECMAKE_SOURCEPATH = "${S}/cmake"

EXTRA_OECMAKE := "-Dprotobuf_BUILD_TESTS:BOOL=OFF"
EXTRA_OECMAKE += "-Dprotobuf_BUILD_EXAMPLES:BOOL=OFF"
EXTRA_OECMAKE += "-Dprotobuf_BUILD_PROTOC_BINARIES:BOOL=ON"
EXTRA_OECMAKE += "-Dprotobuf_WITH_ZLIB:BOOL=ON"
EXTRA_OECMAKE += "-DBUILD_SHARED_LIBS:BOOL=ON"

FILES_${PN}-dev += "${libdir}/cmake"

BBCLASSEXTEND = "native nativesdk"

