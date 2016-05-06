require go.inc

inherit cross

DEPENDS = "virtual/${TARGET_PREFIX}gcc libgcc"

GOROOT_BOOTSTRAP = "${STAGING_DIR_HOST}/go-${BOOTSTRAP_PV}/go"

arch_to_goarch() {
  goarch=$1

  case $1 in
    "x86_64") goarch="amd64";;
    "i386"|"i486"|"i586"|"i686") goarch="386";;
  esac

  echo "$goarch"
}

do_bootstrap() {
  export CC="${BUILD_CC}"
  export GOARCH=$(arch_to_goarch "${TARGET_ARCH}")
  export GOHOSTARCH=$(arch_to_goarch "${HOST_ARCH}")
  export GOHOSTOS="linux"
  export GOOS="linux"

  cd "${WORKDIR}/go-${BOOTSTRAP_PV}/go/src" && sh -x ./make.bash --no-banner
}

do_compile() {
  export CC="$(which ${BUILD_CC})"
  export GO_GCFLAGS="${HOST_CFLAGS}"
  export GO_LDFLAGS="${HOST_LDFLAGS} -extldflags -static"
  export CGO_ENABLED="0"
  export CC_FOR_TARGET="${TARGET_PREFIX}gcc ${TARGET_CC_ARCH} --sysroot=${STAGING_DIR_TARGET}"
  export CXX_FOR_TARGET="${TARGET_PREFIX}g++ ${TARGET_CC_ARCH} --sysroot=${STAGING_DIR_TARGET}"
  #export CGO_CFLAGS="${TARGET_CFLAGS} --sysroot=${STAGING_DIR_TARGET} -I${STAGING_DIR_TARGET}/usr/include"
  #export CGO_LDFLAGS="${TARGET_LDFLAGS} --sysroot=${STAGING_DIR_TARGET}"
  
  export GOROOT_FINAL="${SYSROOT}${libdir}/go"
  export GOROOT_BOOTSTRAP="${WORKDIR}/go-${BOOTSTRAP_PV}/go"
  cd "${S}/src" && sh -x ./make.bash -x
}

do_install() {
  install -d "${D}${bindir}"
  install -m 0755 "${WORKDIR}/go/bin/go" "${D}${bindir}"
  install -d "${D}${libdir}/go"
  ## TODO: use `install` instead of `cp`
  for dir in lib pkg src 
  do cp -a "${WORKDIR}/go/${dir}" "${D}${libdir}/go/"
  done
}

addtask do_bootstrap before do_compile after do_unpack

## TODO: implement do_clean() and ensure we actually do rebuild super cleanly
