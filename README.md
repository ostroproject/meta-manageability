DISCONTINUATION OF PROJECT

This project will no longer be maintained by Intel.

Intel has ceased development and contributions including, but not limited to, maintenance, bug fixes, new releases, or updates, to this project.  

Intel no longer accepts patches to this project.

If you have an ongoing need to use this project, are interested in independently developing it, or would like to maintain patches for the open source software community, please create your own fork of this project.  
This README file contains information on the contents of ostro manageability
layer.


Dependencies
============
This layer depends on:

    URI: git://git.openembedded.org/bitbake
    branch: master

    URI: git://git.openembedded.org/openembedded-core
    layers: meta
    branch: master

Patches
=======

Please submit any patches against the meta-manageability layer via Github pull
requests.

Maintainers: Janos Kovacs <janos.kovacs@intel.com>
             Amarnath Valluri <amarnath.valluri@intel.com>


Recipes in meta-manageability
==============================

- recipes-core:
    Core components of ostro configuration manageability framework.

- recipes-forwarders:
    Sample forwarders that delivers the configuration fragments.
    + etcdconfs - etcd based forwarder
    + restconfs - REST API server and UI, delivers the local configuration
    changes
    + usbconfs  - Supports setting configuration from USB drive.
    + neardconfs - Neard based NFC configuration forwarder.

- recipes-conffw:
    Configuration packages for main Ostro components such as 'connman',
    'openssh', 'dropbear'.

- recipes-temp-fixes:
    Fixes made to core component recipes, eventually these should be merged to
    appropriate layers, such as:
    + chagnes to gcc to enable gccgo compiler
    + avahi,connman - iptable rules.
    + linux-yocto - to enable kernel modules required for connman tethering.


Usage
=====

In order to use this layer, you need to make the build system aware of it. You
can add it to the build system by adding the location of manageability layer
to bblayers.conf, along with other layers needed. e.g:

    BBLAYES ?= " 
      ... \
      /path/to/yocto/meta \
      /path/to/yocto/meta-manageability \
      "

Pulling in the meta-manageability layer does not automatically pull the
component configuration packages. These packages should be explicitly pull in
by adding components to IMAGE_INSTALL in your local.conf file. e.g:

    IMAGE_INSTALL_append = " \
      ... \
      connman-conffw \
      openssh-conffw \
      restconfs \
      "
