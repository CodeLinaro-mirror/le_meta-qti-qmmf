require pulseaudio.inc

FILESPATH =+ "${WORKSPACE}/:"
SRC_URI = "file://external/pulseaudio/ \
           file://volatiles.04_pulse \
           file://pulseaudio.service \
           file://volatile-var-lib-pulse.service \
           file://system-${BASEMACHINE}.pa \
           file://daemon_conf_in.patch \
"

S = "${WORKDIR}/external/pulseaudio"

do_configure_prepend() {
    cd ${S}
    NOCONFIGURE=1 ./bootstrap.sh
    cd ${B}
}

do_compile_prepend() {
    mkdir -p ${S}/libltdl
    cp ${STAGING_LIBDIR}/libltdl* ${S}/libltdl
}

FILES_${PN} = "${datadir}/* ${libdir}/* ${sysconfdir}/* ${bindir}/* ${base_libdir}/* ${prefix}/libexec/"
