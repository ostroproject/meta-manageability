FILESEXTRAPATHS_prepend := "${THISDIR}/files:"
SRC_URI += "file://connman-dhcp-ipv4.conf "

do_install_append() {
  #
  # temporary hack to get rid of some security caused issues in tethering
  #
  grep -v ProtectSystem=full ${D}/lib/systemd/system/connman.service | \
       grep -v ProtectHome=true | \
       grep -v CapabilityBoundingSet > ${WORKDIR}/connman.service
  cp ${WORKDIR}/connman.service ${D}/lib/systemd/system/connman.service
  #
  # let DHCP request through on the FW
  #
  mkdir -p ${D}/lib/systemd/system/connman.service.d
  cp ${WORKDIR}/connman-dhcp-ipv4.conf ${D}/lib/systemd/system/connman.service.d/
}

FILES_${PN} += " \
  /lib/systemd/system/connman.service.d \
"
