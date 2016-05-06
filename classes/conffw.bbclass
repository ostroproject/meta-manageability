# Don't build complimentary packages
PACKAGES = "${PN}"

CONFFW_BASE_COMPONENTS ?= "confd"
RDEPENDS_${PN} = "${CONFFW_BASE_COMPONENTS}"

do_configure[noexec] = "1"
do_compile[noexec] = "1"

