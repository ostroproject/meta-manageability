do_populate_lic[depends] += "iot-conf-fw-source:do_preconfigure"
do_configure[depends] = "iot-conf-fw-source:do_patch"

SRC_URI = ""
HOMEPAGE = "https://github.com/ostroproject/iot-conf-fw"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=1ecf087b2ba5b7e5ae8dc6b3aad18d99"
S = "${TMPDIR}/work-shared/iot-conf-fw-source/git"
B = "${WORKDIR}/${PN}"

