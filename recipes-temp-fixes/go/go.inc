DESCRIPTION = "Go is an open source programming language that makes it easy to build simple, reliable, and efficient software."
HOMEPAGE = "https://golang.org/"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=591778525c869cdde0ab5a1bf283cd81"

BOOTSTRAP_PV="1.4"
SRC_URI = " \
  http://golang.org/dl/go${PV}.src.tar.gz \
  http://golang.org/dl/go${BOOTSTRAP_PV}.src.tar.gz;name=bootstrap;subdir=go-${BOOTSTRAP_PV} \
  file://0001-build.go-Pass-CGO_CFLAGS-for-building-.c-files.patch \
  "

SRC_URI[md5sum] = "e67833ea37fbc002fbe38efe6c1bcd98"
SRC_URI[sha256sum] = "a96cce8ce43a9bf9b2a4c7d470bc7ee0cb00410da815980681c8353218dcf146"
SRC_URI[bootstrap.md5sum] = "c0d732ef7181f98db4b93fbce4eef5a2"
SRC_URI[bootstrap.sha256sum] = "3ae9f67e45a5ca7004b28808da8b1367d328a371d641ddbe636c0fb0ae0ffdae"

S = "${WORKDIR}/go/"
