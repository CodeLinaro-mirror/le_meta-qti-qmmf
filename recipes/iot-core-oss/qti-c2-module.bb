inherit cmake pkgconfig

SUMMARY = "QTI open-source wrapper for Codec2"
SECTION = "multimedia"

LICENSE = "BSD-3-Clause-Clear"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta-qti-bsp/files/common-licenses/${LICENSE};md5=3771d4920bd6cdb8cbdf1e8344489ee0"

# Dependencies.
DEPENDS += "codec2"
DEPENDS:append:qrb5165 = " media-codec2"
DEPENDS:append:kalama = " media"
DEPENDS:append:kalama = " media-external"
DEPENDS:append:kalama = " audio-codec2"
DEPENDS:append:pineapple = " media"
DEPENDS:append:pineapple = " media-external"
DEPENDS:append:pineapple = " audio-codec2"
DEPENDS:append:kera = " media"
DEPENDS:append:kera = " media-external"
DEPENDS:append:kera = " audio-codec2"
DEPENDS:append:alor = " media"
DEPENDS:append:alor = " media-external"
DEPENDS:append:alor = " audio-codec2"
DEPENDS:append:bengal = " media"
DEPENDS:append:bengal = " media-external"
DEPENDS:append:sun = " media"
DEPENDS:append:sun = " media-external"
DEPENDS:append:sun = " mm-audio-headers"
DEPENDS:append:sun = " audio-codec2"
DEPENDS:append:vienna = " media"
DEPENDS:append:vienna = " media-external"
DEPENDS:append:vienna = " audio-codec2"

FILESPATH =+ "${WORKSPACE}/vendor/qcom/opensource/iot-core-oss/:"

SRC_URI = "file://c2-module/"
S = "${WORKDIR}/c2-module"

# Install directries.
INSTALL_BINDIR := "${bindir}"
INSTALL_LIBDIR := "${libdir}"
INSTALL_INCDIR := "${includedir}"

CODEC2_CONFIG_VERSION_MAJOR := "1"
CODEC2_CONFIG_VERSION_MAJOR:kalama := "2"
CODEC2_CONFIG_VERSION_MAJOR:pineapple := "2"
CODEC2_CONFIG_VERSION_MAJOR:kera := "2"
CODEC2_CONFIG_VERSION_MAJOR:alor := "2"
CODEC2_CONFIG_VERSION_MAJOR:bengal := "2"
CODEC2_CONFIG_VERSION_MAJOR:sun := "2"
CODEC2_CONFIG_VERSION_MAJOR:vienna := "2"

CODEC2_CONFIG_VERSION_MINOR := "0"
CODEC2_CONFIG_VERSION_MINOR:kalama := "1"
CODEC2_CONFIG_VERSION_MINOR:pineapple := "1"
CODEC2_CONFIG_VERSION_MINOR:kera := "1"
CODEC2_CONFIG_VERSION_MINOR:alor := "1"
CODEC2_CONFIG_VERSION_MINOR:sun := "1"

ENABLE_AUDIO_PLUGINS:kalama := "TRUE"
ENABLE_AUDIO_PLUGINS:pineapple := "TRUE"
ENABLE_AUDIO_PLUGINS:kera := "TRUE"
ENABLE_AUDIO_PLUGINS:alor := "TRUE"
ENABLE_AUDIO_PLUGINS:sun := "TRUE"
ENABLE_AUDIO_PLUGINS:vienna := "TRUE"

EXTRA_OECMAKE += "-DSYSROOT_INCDIR=${STAGING_INCDIR}"
EXTRA_OECMAKE += "-DSYSROOT_LIBDIR=${STAGING_LIBDIR}"
EXTRA_OECMAKE += "-DKERNEL_BUILDDIR=${STAGING_INCDIR}/linux-msm"
EXTRA_OECMAKE += "-DIOT_CORE_OSS_INSTALL_INCDIR=${INSTALL_INCDIR}"
EXTRA_OECMAKE += "-DIOT_CORE_OSS_INSTALL_BINDIR=${INSTALL_BINDIR}"
EXTRA_OECMAKE += "-DIOT_CORE_OSS_INSTALL_LIBDIR=${INSTALL_LIBDIR}"
EXTRA_OECMAKE += "-DGST_ENABLE_AUDIO_PLUGINS=${ENABLE_AUDIO_PLUGINS}"

EXTRA_OECMAKE += "-DGST_CODEC2_CONFIG_VERSION_MAJOR=${CODEC2_CONFIG_VERSION_MAJOR}"
EXTRA_OECMAKE += "-DGST_CODEC2_CONFIG_VERSION_MINOR=${CODEC2_CONFIG_VERSION_MINOR}"

do_configure:prepend() {
    if echo "${PN}" | grep -q "^lib32-"; then
        echo "Applying 32-bit specific configuration for ${PN}"

        # Symlinks for 32-bit gcc libs (from your camera fix)
        mkdir -p ${WORKDIR}/lib32-recipe-sysroot/usr/lib/gcc/arm-oemllib32-linux-gnueabi/11.5.0
        ln -sf ${WORKDIR}/lib32-recipe-sysroot/usr/lib/arm-oemllib32-linux-gnueabi/11.5.0/* \
               ${WORKDIR}/lib32-recipe-sysroot/usr/lib/gcc/arm-oemllib32-linux-gnueabi/11.5.0/

        # Fix VFP ABI mismatch: switch from hard to softfp (from your camera fix)
        sed -i 's/-mfloat-abi=hard/-mfloat-abi=softfp/g' ${WORKDIR}/toolchain.cmake || true

        # Add softfp stub header (CRITICAL FIX)
        target_incdir="${WORKDIR}/lib32-recipe-sysroot/usr/include/gnu"
        if [ ! -f "${target_incdir}/stubs-soft.h" ]; then
            echo "/* Stub: redirected to stubs-hard.h for build workaround */" > ${target_incdir}/stubs-soft.h
            echo '#include "stubs-hard.h"' >> ${target_incdir}/stubs-soft.h
        fi
    fi
}

FILES:${PN} += "${INSTALL_BINDIR}"
FILES:${PN} += "${INSTALL_LIBDIR}"

do_install:append() {
    if echo "${PN}" | grep -q "^lib32-"; then
        if [ -d "${D}/usr/lib64" ]; then
            echo "Moving lib64 files to lib for 32-bit build"
            mkdir -p ${D}/usr/lib
            cp -r ${D}/usr/lib64/* ${D}/usr/lib/ || true
            rm -rf ${D}/usr/lib64
        fi
    fi
}

SOLIBS = ".so*"
FILES_SOLIBSDEV = ""
TOOLCHAIN = "sdllvm"
DEBUG_PREFIX_MAP:remove = "-fcanon-prefix-map"

# Apply your proven camera fix approach for 32-bit builds
python __anonymous() {
    if not d.getVar("PN").startswith("lib32-"):
        return

    d.appendVar("DEPENDS", " lib32-gcc-runtime lib32-glibc")
    d.setVar("TARGET_TRIPLE", "arm-oemllib32-linux-gnueabi")

    gcc_flags = "\
      -DCMAKE_C_COMPILER=${RECIPE_SYSROOT_NATIVE}/usr/bin/llvm-arm-toolchain/bin/clang \
      -DCMAKE_CXX_COMPILER=${RECIPE_SYSROOT_NATIVE}/usr/bin/llvm-arm-toolchain/bin/clang++ \
      -DCMAKE_C_FLAGS='--target=${TARGET_TRIPLE} --sysroot=${RECIPE_SYSROOT} -fuse-ld=lld -march=armv7-a -mthumb -mfpu=neon -mfloat-abi=softfp' \
      -DCMAKE_CXX_FLAGS='--target=${TARGET_TRIPLE} --sysroot=${RECIPE_SYSROOT} -fuse-ld=lld -march=armv7-a -mthumb -mfpu=neon -mfloat-abi=softfp' \
      -DCMAKE_EXE_LINKER_FLAGS='-fuse-ld=lld' \
      -DCMAKE_SHARED_LINKER_FLAGS='-fuse-ld=lld' \
    "
    d.appendVar("EXTRA_OECMAKE", gcc_flags)
}
