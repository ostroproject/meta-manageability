require recipes-core/iot-conf-fw/iot-conf-fw-shared-source.inc

SUMMARY = "Handles configuration fragments from USB"
DESCRIPTION = "${SUMMARY}"
HOMEPAGE = "https://github.com/ostroproject/iot-conf-fw"

SRC_URI = " \
  file://97-automount.rules \
  file://98-confs-usbstorage.rules \
  file://confs-usbstorage@.path \
  file://confs-usbstorage@.service \
  "

DEPENDS = "go-cross"
INSANE_SKIP_${PN} = "already-stripped"

inherit go-env

do_compile () {
  if [ ! -d ${B}/bin ]; then
    mkdir ${B}/bin
  fi
  export GOPATH="${S}"

  go build -x -o ${B}/bin/tarconfs -compiler gccgo -gccgoflags "-s ${GCCGO_BASE_FLAGS}" ${S}/tarconfs.go
}

do_install () {
  mkdir -p ${D}${bindir}
  mkdir -p ${D}/${base_libdir}/udev/rules.d/

  cp ${B}/bin/tarconfs ${D}/${bindir}
  cp ${WORKDIR}/97-automount.rules ${D}/${base_libdir}/udev/rules.d/
  cp ${WORKDIR}/98-confs-usbstorage.rules ${D}/${base_libdir}/udev/rules.d/
}

FILES_${PN} += " \
  ${bindir}/tarconfs \
  ${base_libdir}/udev/rules.d/ \
"
