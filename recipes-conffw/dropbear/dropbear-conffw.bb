SUMMARY = "Configuration files for bropbear"
DESCRIPTION = "${SUMMARY}"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

inherit conffw

SRC_URI= " file://dropbear-systemd.toml \
           file://dropbear-systemd.tmpl \
           file://dropbear-tmpfiles.conf \
"

do_install() {
  mkdir -p ${D}${datadir}/factory/etc/confd/conf.d
  mkdir -p ${D}${datadir}/factory/etc/confd/templates
  mkdir -p ${D}${libdir}/tmpfiles.d
  install ${WORKDIR}/dropbear-systemd.toml ${D}${datadir}/factory/etc/confd/conf.d/
  install ${WORKDIR}/dropbear-systemd.tmpl ${D}${datadir}/factory/etc/confd/templates/
  install ${WORKDIR}/dropbear-tmpfiles.conf ${D}${libdir}/tmpfiles.d/dropbear-conffw.conf
}

FILES_${PN} = " \
  ${datadir}/factory/etc/confd/conf.d/dropbear-systemd.toml \
  ${datadir}/factory/etc/confd/templates/dropbear-systemd.tmpl \
  ${libdir}/tmpfiles.d/dropbear-conffw.conf \
"

RDEPENDS_${PN} += " dropbear"
