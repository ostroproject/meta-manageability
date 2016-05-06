FILESEXTRAPATHS_prepend := "${THISDIR}/files:"
SRC_URI += "file://neard-tmpfiles.conf"

do_install_append() {
  mkdir -p ${D}/${libdir}/tmpfiles.d

  cp ${WORKDIR}/neard-tmpfiles.conf ${D}${libdir}/tmpfiles.d/neard.conf
}

process_stateless() {
  mkdir -p ${D}${datadir}/factory/etc/dbus-1/system.d
  mv ${D}${sysconfdir}/dbus-1/system.d/org.neard.conf ${D}${datadir}/factory/etc/dbus-1/system.d
  
}
do_install[postfuncs] += " process_stateless "

FILES_${PN} += " \
  ${datadir}/factory/etc/dbus-1/system.d \
  ${libdir}/tmpfiles.d \
"
