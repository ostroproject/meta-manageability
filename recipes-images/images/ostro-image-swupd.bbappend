inherit conffw-image

#
# Configuration bundles
#
SWUPD_BUNDLES = "restconfs etcdconfs usbconfs neardconfs"

BUNDLE_CONTENTS[restconfs] =  "${FEATURE_PACKAGES_restconfs}"
BUNDLE_CONTENTS[etcdconfs] =  "${FEATURE_PACKAGES_etcdconfs}"
BUNDLE_CONTENTS[usbconfs]  =  "${FEATURE_PACKAGES_usbconfs}"
BUNDLE_CONTENTS[neardconfs] = "${FEATURE_PACKAGES_neardconfs}"

FEATURE_PACKAGES_restconfs = "${CONFFW_BASE_PACKAGES} restconfs"
FEATURE_PACKAGES_etcdconfs = "${CONFFW_BASE_PACKAGES} etcdconfs"
FEATURE_PACKAGES_usbconfs  = "${CONFFW_BASE_PACKAGES} usbconfs"
FEATURE_PACKAGES_neardconfs = "${CONFFW_BASE_PACKAGES} neardconfs"

#
# Configuration image defination
SWUPD_IMAGES = " \
  conffw \
  "

SWUPD_IMAGES[conffw] = "\
  ${CONFFW_DEFAULT_FORWARDERS} \
  "
OSTRO_IMAGE_EXTRA_INSTALL_append = " avahi-daemon"
