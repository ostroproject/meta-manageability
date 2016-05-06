deltask do_configure
deltask do_compile
deltask do_install
deltask do_populate_sysroot
deltask do_populate_lic
deltask do_rm_work

inherit nopackages

EXCLUDE_FROM_WORLD = "1"

SUMMARY = "Configuration framework Source for Iot"
DESCRIPTION = "${SUMMARY}"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=1ecf087b2ba5b7e5ae8dc6b3aad18d99"
WORKDIR = "${TMPDIR}/work-shared/${PN}"
STAMP = "${STAMPS_DIR}/work-shared/${PN}"
STAMPCLEAN = "${STAMPS_DIR}/work-shared/${PN}"
INHIBIT_DEFAULT_DEPS = "1"
DEPENDS = ""
PACKAGES = ""
SRC_URI=" \
  gitsm://git@github.com/ostroproject/iot-conf-fw.git;branch=master;protocol=ssh \
  file://0001-gccgo-syscalls-for-386.patch \
"
SRCREV="410874b70b1bde131f6c34d59c85c2bcd887b106"

S = "${WORKDIR}/git"

drop_go_1_5_specifics() {
  #
  # Temp fix: as gccgo not yet support go>=1.5
  # http.Request.Cancel needs atleast go-1.5
  # so commenting that to build with gccgo
  sed -i 's/req.Cancel/\/\/req.Cancel/g' "${S}/src/github.com/coreos/etcd/client/cancelreq.go"
}

apply_sys_unix_patch() {
  cd ${S}/src/golang.org/x/sys
  git apply "${WORKDIR}/0001-gccgo-syscalls-for-386.patch"
  cd -
}

move_vendor_source() {
  # go <= 1.5 does not support vendor directories
  if [ ! -d ${S}/src/github.com/coreos/etcd/cmd/vendor/src ]; then
    mkdir ${S}/src/github.com/coreos/etcd/cmd/vendor_src
    mv ${S}/src/github.com/coreos/etcd/cmd/vendor/* ${S}/src/github.com/coreos/etcd/cmd/vendor_src
    mv ${S}/src/github.com/coreos/etcd/cmd/vendor_src ${S}/src/github.com/coreos/etcd/cmd/vendor/src
  fi
}

python do_preconfigure() {
   bb.build.exec_func('apply_sys_unix_patch', d)
   bb.build.exec_func('move_vendor_source',d)
   bb.build.exec_func('drop_go_1_5_specifics', d)
}

addtask do_preconfigure after do_patch before do_configure
