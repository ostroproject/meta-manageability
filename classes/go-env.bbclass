RDEPENDS_${PN} += "virtual/${TARGET_PREFIX}golibs"
DEPENDS += "virtual/${TARGET_PREFIX}golibs libffi"

GCCGO = "${TARGET_PREFIX}gccgo"
GCCGO_LIB_DIR = "${STAGING_DIR_TARGET}/${libdir}/go/$GCCGO_VERSION/$GCCGO_MACHINE"
GCCGO_BASE_FLAGS ?= "--sysroot=${STAGING_DIR_TARGET} ${TARGET_CC_ARCH} -L${GCCGO_LIB_DIR}"
GCCGO_CFLAGS ?= "-I${STAGING_DIR_TARGET}/${includedir}"
GCCGO_LDFLAGS ?= "-L${STAGING_DIR_TARGET}/${libdir}"

arch_to_goarch() {
  goarch=$1

  case $1 in
    "x86_64") goarch="amd64";;
    "i386"|"i486"|"i586"|"i686") goarch="386";;
  esac

  echo "$goarch"
}

do_compile_prepend() {
  export GOHOSTARCH=$(arch_to_goarch "${BUILD_ARCH}")
  export GOARCH=$(arch_to_goarch "${TARGET_ARCH}")
  if [ "${GOARCH}" = "arm" -a `echo ${TUNE_PKGARCH} | cut -c 1-7` = "cortexa" ]
  then
    export GOARM="7"
  fi

  export GOHOSTOS="linux"
  export GOOS="linux"
  export GCCGO="${GCCGO}"
  export GCCGO_VERSION=`${GCCGO} -dumpversion`
  export GCCGO_MACHINE=`${GCCGO} -dumpmachine`
  export CGO_CFLAGS="${GCCGO_CFLAGS}"
  export CGO_LDFLAGS="${GCCGO_LDFLAGS}"
  export PATH="${STAGING_BINDIR_NATIVE}/${HOST_SYS}/:$PATH"
  #export GOROOT="${GCCGO_LIB_DIR}"
  export GOROOT="${STAGING_LIBDIR_NATIVE}/${HOST_SYS}/go"
}
 
