SUMMARY = "Configuration files for openssh"
DESCRIPTION = "{SUMMARY}"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI= " file://openssh-socket.toml \
           file://openssh-socket.tmpl \
           file://openssh-fw.toml \
           file://openssh-fw.tmpl \
           file://openssh-systemctl.toml \
           file://openssh-systemctl.tmpl \
           file://openssh-tmpfiles.conf \
           file://ssh.js \
           file://ssh \
"

inherit conffw

do_install() {
  mkdir -p ${D}${datadir}/factory/etc/confd/conf.d
  mkdir -p ${D}${datadir}/factory/etc/confd/templates
  mkdir -p ${D}${datadir}/confs/ui/devel
  mkdir -p ${D}${datadir}/factory/confs/devel
  mkdir -p ${D}${libdir}/tmpfiles.d
  install ${WORKDIR}/openssh-socket.toml ${D}${datadir}/factory/etc/confd/conf.d/
  install ${WORKDIR}/openssh-socket.tmpl ${D}${datadir}/factory/etc/confd/templates/
  install ${WORKDIR}/openssh-fw.toml ${D}${datadir}/factory/etc/confd/conf.d/
  install ${WORKDIR}/openssh-fw.tmpl ${D}${datadir}/factory/etc/confd/templates/
  install ${WORKDIR}/openssh-systemctl.toml ${D}${datadir}/factory/etc/confd/conf.d/
  install ${WORKDIR}/openssh-systemctl.tmpl ${D}${datadir}/factory/etc/confd/templates/
  install ${WORKDIR}/ssh.js ${D}${datadir}/confs/ui/devel/
  install ${WORKDIR}/openssh-tmpfiles.conf ${D}${libdir}/tmpfiles.d/openssh-conffw.conf
  install ${WORKDIR}/ssh ${D}${datadir}/factory/confs/devel/
}

FILES_${PN} = "${datadir}/factory/etc/confd/conf.d/openssh-socket.toml \
               ${datadir}/factory/etc/confd/templates/openssh-socket.tmpl \
               ${datadir}/factory/etc/confd/conf.d/openssh-fw.toml \
               ${datadir}/factory/etc/confd/templates/openssh-fw.tmpl \
               ${datadir}/factory/etc/confd/conf.d/openssh-systemctl.toml \
               ${datadir}/factory/etc/confd/templates/openssh-systemctl.tmpl \
               ${datadir}/confs/ui/devel/ssh.js \
               ${libdir}/tmpfiles.d/openssh-conffw.conf \
               ${datadir}/factory/confs/devel/ssh \
"

RDEPENDS_${PN} += " openssh"
