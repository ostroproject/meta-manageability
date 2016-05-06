PROVIDES += "virtual/${TARGET_PREFIX}gccgo"
LANGUAGES := "${LANGUAGES},go"

EXTRA_OECONF_BASE_append = " --disable-gotools"

do_install_append () {
  ln -sf ${BINRELPATH}/${TARGET_PREFIX}gccgo ${dest}gccgo
  ln -sf ${BINRELPATH}/${TARGET_PREFIX}gccgo ${dest}${TARGET_PREFIX}gccgo
}
