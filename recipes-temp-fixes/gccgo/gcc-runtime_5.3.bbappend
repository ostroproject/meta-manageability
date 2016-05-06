#
# Originally from https://github.com/digitallumens/meta-golang
#
LANGUAGES := "${LANGUAGES},go"
RUNTIMETARGET += "libbacktrace libffi libgo"

PROVIDES += "virtual/${TARGET_PREFIX}golibs"
RPROVIDES_libgo = "virtual/${TARGET_PREFIX}golibs"
DEPENDS_libgo  += "virtual/${TARGET_PREFIX}gccgo libffi"

PACKAGES += "\
    libgo libgo-dev libgo-staticdev \
"

FILES_libgo = "${libdir}/libgo.so.* ${libdir}/go"
FILES_libgo-dev = "\
  ${libdir}/libgo.so \
  ${libdir}/libgo.la \
  "
FILES_libgo-staticdev = "\
  ${libdir}/libgo.a \
  ${libdir}/libgobegin.a \
  ${libdir}/libnetgo.a \
  ${libdir}/libgolibbegin.a \
  "

do_configure_prepend () {
  export GOC="${TARGET_PREFIX}gccgo ${TARGET_CC_ARCH} --sysroot=$SDKTARGETSYSROOT"
}

do_compile_prepend() {
  export GOC="${TARGET_PREFIX}gccgo ${TARGET_CC_ARCH} --sysroot=$SDKTARGETSYSROOT"
}

do_install_append() {
  rm -rf ${D}${infodir}/libffi.info ${D}${infodir}/dir
  if [ -d ${D}${infodir} ]; then
    rmdir --ignore-fail-on-non-empty -p ${D}${infodir}
  fi

  rm -rf ${D}${libdir}/libffi.*
  rm -rf ${D}${libdir}/pkgconfig
  rm -rf ${D}${libdir}/gcc/${TARGET_SYS}/${BINV}/include/ffi.h
  rm -rf ${D}${libdir}/gcc/${TARGET_SYS}/${BINV}/include/ffitarget.h

  rm -rf ${D}${mandir}
}

#GCCGO runtime expects libgo to have the .debug_ranges section intact
python do_package_append() {
    import subprocess
    workdir = d.getVar('WORKDIR',True)
    lib = workdir+"/package/usr/lib"
    golib = "libgo.so.5.0.0"
    objcopy = d.getVar('OBJCOPY',True)

    cmd1 = objcopy+" -O binary --set-section-flags .debug_ranges=alloc -j .debug_ranges %s/.debug/%s %s/temp/%s.dbg"%(lib, golib, workdir, golib)
    cmd2 = objcopy+" --add-section .debug_ranges=%s/temp/%s.dbg %s/%s"%(workdir, golib, lib, golib)
    cmd_rm = "rm %s/temp/%s.dbg"%(workdir, golib)
    print cmd1
    subprocess.call(cmd1, shell=True)
    print cmd2
    subprocess.call(cmd2, shell=True)
    print cmd_rm
    subprocess.call(cmd_rm, shell=True)
}

