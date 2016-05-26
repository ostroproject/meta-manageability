FILESEXTRAPATHS_prepend := "${THISDIR}/files:"
SRC_URI += " \
  file://neard-tmpfiles.conf \
  file://org.neard.service \
  "

do_install_append() {
  install -d ${D}/${libdir}/tmpfiles.d
  install -m 0755 -T ${WORKDIR}/neard-tmpfiles.conf ${D}${libdir}/tmpfiles.d/neard.conf

  install -d ${D}/${datadir}/dbus-1/system-services/
  install -m 0755 ${WORKDIR}/org.neard.service ${D}/${datadir}/dbus-1/system-services/
}

process_stateless() {
  mkdir -p ${D}${datadir}/factory/etc/dbus-1/system.d
  mv ${D}${sysconfdir}/dbus-1/system.d/org.neard.conf ${D}${datadir}/factory/etc/dbus-1/system.d
}
do_install[postfuncs] += " process_stateless "

FILES_${PN} += " \
  ${datadir}/factory/etc/dbus-1/system.d \
  ${datadir}/dbus-1/system-services/ \
  ${libdir}/tmpfiles.d \
"
