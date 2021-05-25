# qmmf Open source Packages

# IMAGE_INSTALL += "ipmon"
# IMAGE_INSTALL += "discovery"
# IMAGE_INSTALL += "rtsp-proxy"
#IMAGE_INSTALL += "webui"

# Following packages support only 32-bit compilation.
# When multilib is enabled, compile them with lib32- prefix.

# IMAGE_INSTALL += "${@bb.utils.contains('MULTILIB_VARIANTS', 'lib32', 'lib32-jsoncpp', 'jsoncpp', d)}"
# IMAGE_INSTALL += "${@bb.utils.contains('MULTILIB_VARIANTS', 'lib32', 'lib32-sqlite3', 'sqlite3', d)}"
# IMAGE_INSTALL += "${@bb.utils.contains('MULTILIB_VARIANTS', 'lib32', 'lib32-qmmf-support', 'qmmf-support', d)}"
# IMAGE_INSTALL += "${@bb.utils.contains('MULTILIB_VARIANTS', 'lib32', 'lib32-qmmf-webserver', 'qmmf-webserver', d)}"
# IMAGE_INSTALL += "${@bb.utils.contains('MULTILIB_VARIANTS', 'lib32', 'lib32-qmmf-sdk lib32-qmmf-sdk-qmmf-server', 'qmmf-sdk qmmf-sdk-qmmf-server', d)}"
IMAGE_INSTALL += "qmmf-sdk"

copy_edge_qmmf_rpm() {
    cp ${DEPLOY_DIR_RPM}/sm8250/qmmf-sdk-dev-git-r0.sm8250.rpm ${IMAGE_ROOTFS}/packages/sm8250/
    cp ${DEPLOY_DIR_RPM}/sm8250/qmmf-sdk-git-r0.sm8250.rpm     ${IMAGE_ROOTFS}/packages/sm8250/
    cp ${DEPLOY_DIR_RPM}/aarch64/gtest-git-r0.aarch64.rpm      ${IMAGE_ROOTFS}/packages/aarch64/
}

ROOTFS_POSTPROCESS_COMMAND += "copy_edge_qmmf_rpm;"
