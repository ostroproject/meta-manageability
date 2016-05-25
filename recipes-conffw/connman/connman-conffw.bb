require recipes-core/iot-conf-fw/iot-conf-fw-shared-source.inc

SUMMARY = "Configuration files for connman"
DESCRIPTION = "${SUMMARY}"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = " \
  file://connman-main.toml \
  file://connman-main.tmpl \
  file://connmanupd.toml \
  file://connmanupd.tmpl \
  file://connman-modules.conf \
  file://connman-tmpfiles.conf \
  file://wifi.js \
  file://ethernet.js \
  file://wifi \
  file://ethernet \
"
SRCREV="ce0d0c69b95d909f06af578849a208068823dea0"

INSANE_SKIP_${PN} += "already-stripped"
DEPENDS = "go-cross"
inherit go-env conffw

python() {
    d.delVarFlag('do_configure', 'noexec')
    d.delVarFlag('do_compile', 'noexec')
}

do_compile() {
  if [ ! -d ${B}/bin ]; then
    mkdir ${B}/bin
  fi
  export GOPATH="${S}"

  go build -x -o ${B}/bin/connmanupd -compiler gccgo -gccgoflags "-s ${GCCGO_BASE_FLAGS}" ${S}/connmanupd.go
}

do_install() {
  mkdir -p ${D}${libexecdir}
  mkdir -p ${D}${datadir}/factory/etc/confd/conf.d
  mkdir -p ${D}${datadir}/factory/etc/confd/templates
  mkdir -p ${D}${datadir}/factory/etc/modules-load.d
  mkdir -p ${D}${datadir}/factory/confs/device
  mkdir -p ${D}${libdir}/tmpfiles.d
  mkdir -p ${D}${datadir}/confs/ui/device
  cp ${B}/bin/connmanupd ${D}${libexecdir}
  install ${WORKDIR}/*.toml ${D}${datadir}/factory/etc/confd/conf.d/
  install ${WORKDIR}/*.tmpl ${D}${datadir}/factory/etc/confd/templates/
  install ${WORKDIR}/connman-modules.conf ${D}${datadir}/factory/etc/modules-load.d/connman.conf
  install ${WORKDIR}/*.js ${D}${datadir}/confs/ui/device
  install ${WORKDIR}/connman-tmpfiles.conf ${D}${libdir}/tmpfiles.d/connman-conffw.conf
  install ${WORKDIR}/wifi ${D}${datadir}/factory/confs/device/wifi
  install ${WORKDIR}/ethernet ${D}${datadir}/factory/confs/device/ethernet
}

FILES_${PN} = " \
  ${libexecdir}/connmanupd \
  ${datadir}/factory/etc/confd/conf.d/connman-main.toml \
  ${datadir}/factory/etc/confd/templates/connman-main.tmpl \
  ${datadir}/factory/etc/confd/conf.d/connmanupd.toml \
  ${datadir}/factory/etc/confd/templates/connmanupd.tmpl \
  ${datadir}/factory/etc/modules-load.d/connman.conf \
  ${datadir}/factory/confs/device/wifi \
  ${datadir}/factory/confs/device/ethernet \
  ${datadir}/confs/ui/device/*.js \
  ${libdir}/tmpfiles.d/connman-conffw.conf \
"

RRECOMMENDS_${PN} += " connman"
