# Copyright (C) 2013-2016 Freescale Semiconductor
# Copyright 2017 NXP
# Copyright 2018-2019 Variscite Ltd.
# Released under the MIT license (see COPYING.MIT for the terms)

SUMMARY = "Linux kernel provided and supported by Variscite"
DESCRIPTION = "Linux kernel provided and supported by Variscite (based on the kernel provided by NXP) \
with focus on i.MX Family SOMs. It includes support for many IPs such as GPU, VPU and IPU."

require recipes-kernel/linux/linux-imx.inc
include linux-common.inc

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=bbea815ee2795b2f4230826c0c6b8814"

DEPENDS += "lzop-native bc-native"

# Do not copy the kernel image to the rootfs by default
RDEPENDS:${KERNEL_PACKAGE_NAME}-base = ""

DEFAULT_PREFERENCE = "1"

LOCALVERSION:var-som-mx6 = "-imx6ul"
LOCALVERSION:imx6ul-var-dart = "-imx6ul"
LOCALVERSION:imx8mq-var-dart = "-imx8mq"
LOCALVERSION:imx8mm-var-dart = "-imx8mm"
LOCALVERSION:imx8mn-var-som = "-imx8mn"
LOCALVERSION:imx8qxp-var-som = "-imx8x"
LOCALVERSION:imx8qxpb0-var-som = "-imx8x"
LOCALVERSION:imx8qm-var-som = "-imx8qm"

KBUILD_DEFCONFIG:var-som-mx6 = "imx_v7_var_defconfig"
KBUILD_DEFCONFIG:imx6ul-var-dart = "imx_v7_var_defconfig"
KBUILD_DEFCONFIG:imx7-var-som = "imx_v7_var_defconfig"
KBUILD_DEFCONFIG:imx8mq-var-dart = "imx8mq_var_dart_defconfig"
KBUILD_DEFCONFIG:imx8mm-var-dart = "imx8_var_defconfig"
KBUILD_DEFCONFIG:imx8mn-var-som = "imx8_var_defconfig"
KBUILD_DEFCONFIG:imx8qxp-var-som = "imx8_var_defconfig"
KBUILD_DEFCONFIG:imx8qxpb0-var-som = "imx8_var_defconfig"
KBUILD_DEFCONFIG:imx8qm-var-som = "imx8_var_defconfig"

DEFAULT_DTB:imx8mq-var-dart = "sd-lvds"
DEFAULT_DTB:imx8qxp-var-som = "sd"
DEFAULT_DTB:imx8qxpb0-var-som = "sd"
DEFAULT_DTB:imx8qm-var-som = "lvds"
DEFAULT_DTB_PREFIX:imx8mq-var-dart = "imx8mq-var-dart-dt8mcustomboard"
DEFAULT_DTB_PREFIX:imx8qxp-var-som = "imx8qxp-var-som-symphony"
DEFAULT_DTB_PREFIX:imx8qxpb0-var-som = "imx8qxp-var-som-symphony"
DEFAULT_DTB_PREFIX:imx8qm-var-som = "imx8qm-var-som"

SRC_URI = "${KERNEL_SRC};branch=${SRCBRANCH}"

S = "${WORKDIR}/git"

pkg_postinst:kernel-devicetree:append () {
   rm -f $D/boot/devicetree-*
}

pkg_postinst:kernel-devicetree:append:imx8mq-var-dart () {
    cd $D/boot
    ln -s ${DEFAULT_DTB_PREFIX}-${DEFAULT_DTB}.dtb ${DEFAULT_DTB_PREFIX}.dtb
    ln -s ${DEFAULT_DTB_PREFIX}-legacy-${DEFAULT_DTB}.dtb ${DEFAULT_DTB_PREFIX}-legacy.dtb
}

pkg_postinst:kernel-devicetree:append:imx8qxp-var-som () {
    cd $D/boot
    ln -s ${DEFAULT_DTB_PREFIX}-${DEFAULT_DTB}.dtb ${DEFAULT_DTB_PREFIX}.dtb
}

pkg_postinst:kernel-devicetree:append:imx8qxpb0-var-som () {
    cd $D/boot
    ln -s ${DEFAULT_DTB_PREFIX}-${DEFAULT_DTB}.dtb ${DEFAULT_DTB_PREFIX}.dtb
}

pkg_postinst:kernel-devicetree:append:imx8qm-var-som () {
    cd $D/boot
    ln -s ${DEFAULT_DTB_PREFIX}-${DEFAULT_DTB}.dtb ${DEFAULT_DTB_PREFIX}.dtb
    ln -s imx8qm-var-spear-${DEFAULT_DTB}.dtb imx8qm-var-spear.dtb
}

# Added by meta-virtualization/recipes-kernel/linux/linux-yocto_5.4_virtualization.inc
KERNEL_FEATURES:remove = "cfg/virtio.scc"

COMPATIBLE_MACHINE = "(mx6|mx7|mx8)"
