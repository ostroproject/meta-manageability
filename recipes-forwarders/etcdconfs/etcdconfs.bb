require recipes-core/iot-conf-fw/iot-conf-fw-shared-source.inc

SUMMARY = "Restconfs forwarder"
DESCRIPTION = "${SUMMARY}"

SRC_URI = " \
  file://etcdconfs.service \
"

DEPENDS = "go-cross"

inherit systemd go-env

SYSTEMD_SERVICE_${PN} = "etcdconfs.service"

do_compile () {
  if [ ! -d ${B}/bin ]; then
    mkdir ${B}/bin
  fi
  export GOPATH="${S}:${S}/src/github.com/coreos/etcd/cmd/vendor"

  go build -x -o ${B}/bin/etcdconfs -compiler gccgo -gccgoflags "${GCCGO_BASE_FLAGS}" ${S}/etcdconfs.go
}

do_install () {
  mkdir -p ${D}/${bindir}
  cp ${B}/bin/* ${D}/${bindir}

  mkdir -p ${D}/${systemd_unitdir}/system/
  cp ${WORKDIR}/etcdconfs.service ${D}/${systemd_unitdir}/system/
}

FILES_${PN} += " \
  ${bindir}/etcdconfs \
  {systemd_unitdir}/system/etcdconfs.service \
  "
