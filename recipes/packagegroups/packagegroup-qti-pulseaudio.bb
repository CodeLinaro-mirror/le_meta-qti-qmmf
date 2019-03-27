SUMMARY = "QTI PulseAudio opensource package groups"
LICENSE = "BSD-3-Clause"

inherit packagegroup

PROVIDES = "${PACKAGES}"

PACKAGES = ' \
    ${@bb.utils.contains("DISTRO_FEATURES", "pulseaudio", bb.utils.contains("COMBINED_FEATURES", "qti-audio", "packagegroup-qti-pulseaudio", "", d), "", d)} \
'

RDEPENDS_packagegroup-qti-pulseaudio = ' \
    pulseaudio-server \
    pulseaudio-module-loopback \
    pulseaudio-module-null-source \
    pulseaudio-module-combine-sink \
    pulseaudio-module-role-exclusive \
    pulseaudio-module-role-ignore \
    pulseaudio-module-role-ducking \
    pulseaudio-module-policy-voiceui \
    pulseaudio-module-switch-on-port-available \
    pulseaudio-misc \
'
