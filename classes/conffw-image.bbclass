#
# Configuration base packages
#
CONFFW_BASE_PACKAGES ?= "\
  ${@bb.utils.contains('IMAGE_FEATURES', 'ssh-server-openssh', 'openssh-conffw', '', d)} \
  ${@bb.utils.contains('IMAGE_FEATURES', 'ssh-server-dropbear', 'dropbear-conffw', '', d)} \
  ${@bb.utils.contains('IMAGE_FEATURES', 'connectivity', 'connman-conffw', '', d)} \
  "
#
# Default forwarders to be part of ostro-image-swupd-conffw image
#
CONFFW_DEFAULT_FORWARDERS ?= "restconfs"
