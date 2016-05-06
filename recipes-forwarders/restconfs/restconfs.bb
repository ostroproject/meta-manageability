require recipes-core/iot-conf-fw/iot-conf-fw-shared-source.inc

SUMMARY = "Restconfs forwarder"
DESCRIPTION = "${SUMMARY}"
HOMEPAGE = "https://github.com/ostroproject/iot-conf-fw"
SRC_URI=" \
  file://restconfs.service \
  file://restconfs.socket \
  file://restconfs-ipv4.conf \
  file://restconfs-ipv6.conf \
  file://restconfs.crt \
  file://restconfs.key \
  file://root.js \
  file://device.js \
  file://devel.js \
  file://http-confs.service \
"

DEPENDS = "go-cross"
INSANE_SKIP_${PN} = "already-stripped"

inherit systemd go-env

SYSTEMD_SERVICE_${PN} = "restconfs.socket"

do_compile () {
  if [ ! -d ${B}/bin ]; then
    mkdir ${B}/bin
  fi
  export GOPATH="${S}"

  go build -x -o ${B}/bin/restconfs -compiler gccgo -gccgoflags "-s ${GCCGO_BASE_FLAGS}" ${S}/restconfs.go
}

do_install () {
  mkdir -p ${D}${bindir}
  mkdir -p ${D}${systemd_unitdir}/system/restconfs.socket.d
  mkdir -p ${D}${datadir}/confs/ui/infra
  mkdir -p ${D}${datadir}/confs/ui/device
  mkdir -p ${D}${datadir}/confs/ui/devel
  mkdir -p ${D}${datadir}/confs/ca-files
  mkdir -p ${D}${datadir}/factory/etc/avahi/services

  cp ${B}/bin/restconfs ${D}/${bindir}
  cp ${WORKDIR}/restconfs.service   ${D}/${systemd_unitdir}/system/
  cp ${WORKDIR}/restconfs.socket ${D}/${systemd_unitdir}/system/
  cp ${WORKDIR}/restconfs-ipv4.conf ${D}/${systemd_unitdir}/system/restconfs.socket.d/
  cp ${WORKDIR}/restconfs-ipv6.conf ${D}/${systemd_unitdir}/system/restconfs.socket.d/
  cp ${WORKDIR}/restconfs.crt ${D}/${datadir}/confs/ca-files/
  cp ${WORKDIR}/restconfs.key ${D}/${datadir}/confs/ca-files/
  cp ${S}/src/ostro/confui/*.js  ${D}${datadir}/confs/ui/infra/
  cp ${S}/src/ostro/confui/*.css ${D}${datadir}/confs/ui/infra/
  cp ${WORKDIR}/root.js   ${D}${datadir}/confs/ui/
  cp ${WORKDIR}/device.js ${D}${datadir}/confs/ui/
  cp ${WORKDIR}/devel.js  ${D}${datadir}/confs/ui/
  cp ${WORKDIR}/http-confs.service ${D}${datadir}/factory/etc/avahi/services
}

FILES_${PN} += " \
  ${bindir}/restconfs \
  ${systemd_unitdir}/system/restconfs.socket.d/* \
  ${datadir}/confs/ca-files \
  ${datadir}/confs/ui/* \
  ${datadir}/factory/etc/avahi/services \
"
