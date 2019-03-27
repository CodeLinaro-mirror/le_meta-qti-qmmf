# qmmf Open source Packages

IMAGE_INSTALL += "ipmon"
IMAGE_INSTALL += "discovery"
IMAGE_INSTALL += "rtsp-proxy"

# Following packages support only 32-bit compilation.
# When multilib is enabled, compile them with lib32- prefix.

IMAGE_INSTALL += "${@bb.utils.contains('MULTILIB_VARIANTS', 'lib32', 'lib32-jsoncpp', 'jsoncpp', d)}"
IMAGE_INSTALL += "${@bb.utils.contains('MULTILIB_VARIANTS', 'lib32', 'lib32-sqlite3', 'sqlite3', d)}"
IMAGE_INSTALL += "${@bb.utils.contains('MULTILIB_VARIANTS', 'lib32', 'lib32-qmmf-support', 'qmmf-support', d)}"
IMAGE_INSTALL += "${@bb.utils.contains('MULTILIB_VARIANTS', 'lib32', 'lib32-qmmf-webserver', 'qmmf-webserver', d)}"
IMAGE_INSTALL += "${@bb.utils.contains('MULTILIB_VARIANTS', 'lib32', 'lib32-qmmf-sdk', 'qmmf-sdk', d)}"
IMAGE_INSTALL += "${@bb.utils.contains('MULTILIB_VARIANTS', 'lib32', 'lib32-ipc-webserver', 'ipc-webserver', d)}"
IMAGE_INSTALL += "${@bb.utils.contains('MULTILIB_VARIANTS', 'lib32', 'lib32-live555-qti', 'live555-qti', d)}"
IMAGE_INSTALL += "${@bb.utils.contains('MULTILIB_VARIANTS', 'lib32', 'lib32-pulseaudio-server', 'pulseaudio-server', d)}"
IMAGE_INSTALL += "${@bb.utils.contains('MULTILIB_VARIANTS', 'lib32', 'lib32-pulseaudio-module-loopback', 'pulseaudio-module-loopback', d)}"
IMAGE_INSTALL += "${@bb.utils.contains('MULTILIB_VARIANTS', 'lib32', 'lib32-pulseaudio-module-null-source', 'pulseaudio-module-null-source', d)}"
IMAGE_INSTALL += "${@bb.utils.contains('MULTILIB_VARIANTS', 'lib32', 'lib32-pulseaudio-module-combine-sink', 'pulseaudio-module-combine-sink', d)}"
IMAGE_INSTALL += "${@bb.utils.contains('MULTILIB_VARIANTS', 'lib32', 'lib32-pulseaudio-module-role-exclusive', 'pulseaudio-module-role-exclusive', d)}"
IMAGE_INSTALL += "${@bb.utils.contains('MULTILIB_VARIANTS', 'lib32', 'lib32-pulseaudio-module-role-ignore', 'pulseaudio-module-role-ignore', d)}"
IMAGE_INSTALL += "${@bb.utils.contains('MULTILIB_VARIANTS', 'lib32', 'lib32-pulseaudio-module-role-ducking', 'pulseaudio-module-role-ducking', d)}"
IMAGE_INSTALL += "${@bb.utils.contains('MULTILIB_VARIANTS', 'lib32', 'lib32-pulseaudio-module-policy-voiceui', 'pulseaudio-module-policy-voiceui', d)}"
IMAGE_INSTALL += "${@bb.utils.contains('MULTILIB_VARIANTS', 'lib32', 'lib32-pulseaudio-misc', 'pulseaudio-misc', d)}"
