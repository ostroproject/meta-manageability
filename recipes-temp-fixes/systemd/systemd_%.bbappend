
fix_mount_flags() {
  #
  # For automounting to work 'MountFlags' should be 'shared'
  #
  sed -i -e 's/MountFlags=slave/MountFlags=shared/g' ${D}${systemd_unitdir}/system/systemd-udevd.service
}

do_install[postfuncs] = "fix_mount_flags"
