FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

RDEPENDS_${PN} += "iptables"

SRC_URI_append = "\
    file://workstation.service \
    file://avahi-ipv4.conf \
    file://avahi-ipv6.conf \
"

do_install_append() {
    install -d ${D}${sysconfdir}/avahi/services
    install -m 0644 ${WORKDIR}/workstation.service ${D}${sysconfdir}/avahi/services
    install -d ${D}${systemd_unitdir}/system/avahi-daemon.service.d
    install -m 0644 ${WORKDIR}/avahi-ipv4.conf ${D}${systemd_unitdir}/system/avahi-daemon.service.d
    if ${@bb.utils.contains('DISTRO_FEATURES', 'ipv6', 'true', 'false', d)}; then
        install -m 0644 ${WORKDIR}/avahi-ipv6.conf ${D}${systemd_unitdir}/system/avahi-daemon.service.d
    fi
}

FILES_avahi-daemon_append = " \
    ${sysconfdir}/avahi/services/workstation.service \
    ${systemd_unitdir}/system/avahi-daemon.service.d \
"
