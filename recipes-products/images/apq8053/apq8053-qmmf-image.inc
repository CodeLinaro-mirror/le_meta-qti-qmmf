# qmmf Open source Packages

IMAGE_INSTALL += "ipmon"
IMAGE_INSTALL += "discovery"
IMAGE_INSTALL += "rtsp-proxy"
IMAGE_INSTALL += "webui"

# Following packages support only 32-bit compilation.
# When multilib is enabled, compile them with lib32- prefix.

IMAGE_INSTALL += "${@base_contains('MULTILIB_VARIANTS', 'lib32', 'lib32-jsoncpp', 'jsoncpp', d)}"
IMAGE_INSTALL += "${@base_contains('MULTILIB_VARIANTS', 'lib32', 'lib32-qmmf-sdk', 'qmmf-sdk', d)}"
IMAGE_INSTALL += "${@base_contains('MULTILIB_VARIANTS', 'lib32', 'lib32-libjpeg-turbo', 'libjpeg-turbo', d)}"
