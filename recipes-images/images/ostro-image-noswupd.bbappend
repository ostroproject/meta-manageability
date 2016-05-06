inherit conffw-image

OSTRO_IMAGE_NOSWUPD_EXTRA_INSTALL_append = " \
  ${CONFFW_BASE_PACKAGES} \
  ${CONFFW_DEFAULT_FORWARDERS} \
  avahi-daemon \
  "
