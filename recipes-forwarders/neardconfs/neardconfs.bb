require recipes-core/iot-conf-fw/iot-conf-fw-shared-source.inc

SUMMARY = "Neard NFC confs forwarder"
DESCRIPTION = "${SUMMARY}"
HOMEPAGE = "https://github.com/ostroproject/iot-conf-fw"
SRC_URI = " \
  file://org.ostro.neard.conf \
  file://neardconfs-tmpfiles.conf \
  file://neardconfs.service \
  "

DEPENDS = "go-cross"
INSANE_SKIP_${PN} = "already-stripped"

inherit systemd go-env

SYSTEMD_SERVICE_${PN} = "neardconfs.service"

do_compile () {
  if [ ! -d ${B}/bin ]; then
    mkdir ${B}/bin
  fi
  export GOPATH="${S}"

  go build -x -o ${B}/bin/neardconfs -compiler gccgo -gccgoflags "-s ${GCCGO_BASE_FLAGS}" ${S}/neardconfs.go
}

do_install () {
  mkdir -p ${D}/${systemd_unitdir}/system/
  mkdir -p ${D}${bindir}
  mkdir -p ${D}${datadir}/factory/etc/dbus-1/system.d
  mkdir -p ${D}/${libdir}/tmpfiles.d

  cp ${WORKDIR}/neardconfs.service   ${D}/${systemd_unitdir}/system/
  cp ${WORKDIR}/org.ostro.neard.conf ${D}/${datadir}/factory/etc/dbus-1/system.d
  cp ${WORKDIR}/neardconfs-tmpfiles.conf ${D}/${libdir}/tmpfiles.d/neardconfs.conf
  cp ${B}/bin/neardconfs ${D}/${bindir}
}

FILES_${PN} += " \
  ${systemd_unitdir}/system/neardconfs.service \
  ${bindir}/neardconfs \
  ${libdir}/tmpfiles.d/neardconfs.conf \
  ${datadir}/factory/etc/dbus-1/system.d \
"

RDEPENDS_${PN} = "neard"
