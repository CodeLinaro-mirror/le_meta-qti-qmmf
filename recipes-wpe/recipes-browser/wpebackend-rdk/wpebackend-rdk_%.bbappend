# select the Wayland option for the RDK backend
PACKAGECONFIG_pn-wpebackend-rdk = "wayland"

SRC_URI_remove = "git://github.com/WebPlatformForEmbedded/WPEBackend-rdk.git;protocol=https;branch=master"
SRC_URI += "${CAF_GIT}/WebPlatformForEmbedded/WPEBackend-rdk.git;protocol=git;branch=webback/master"

