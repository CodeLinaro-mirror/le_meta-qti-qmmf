inherit cmake pkgconfig

SUMMARY = "HEIF and AVIF image file format decoder and encoder"
DESCRIPTION = "libheif is an ISO/IEC 23008-12:2017 HEIF file format decoder and encoder."
HOMEPAGE = "https://git.codelinaro.org/clo/le/external/libheif"
LICENSE = "LGPL-3.0-or-later"
LIC_FILES_CHKSUM = "file://COPYING;md5=9c0edc7369719b2c47d44e80ba74b4b7"

BRANCH = "github/v1.20.x-releases"
SRC_URI = "\
         git://git.codelinaro.org/clo/le/external/libheif.git;protocol=https;branch=${BRANCH} \
         file://0001-implement-grid-heif-for-encoded-tiles.patch \
"

SRCREV = "315144b9c61b15d36a9d135f2fa74a89851cf9bb"
S = "${WORKDIR}/git"

EXTRA_OECMAKE = "-DBUILD_SHARED_LIBS=ON"

# Install directries.
INSTALL_BINDIR := "${bindir}"
INSTALL_LIBDIR := "${libdir}"
INSTALL_INCDIR := "${includedir}"

FILES:${PN} += "${INSTALL_BINDIR}"
FILES:${PN} += "${INSTALL_LIBDIR}"

SOLIBS = ".so*"
FILES_SOLIBSDEV = ""
