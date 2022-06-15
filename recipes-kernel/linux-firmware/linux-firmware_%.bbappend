# Support additional firmware for bc43xx and wl18xx WIFI+BT modules

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRCREV_FORMAT = "brcm_tiwlan_tibt"

SRCREV_brcm = "8081cd2bddb1569abe91eb50bd687a2066a33342"
BRANCH_brcm = "8.2.0.16"

# TI WiFi FW 8.9.0.0.86 and BT FW 4.5
SRCREV_tiwlan = "5ec05007f2662f460f881c5868311fd3ab7e6e71"
BRANCH_tiwlan = "master"
SRCREV_tibt = "6c9104f0fb7ca1bfb663c61e9ea599b3eafbee67"
BRANCH_tibt = "master"

SRC_URI:append = " \
	git://github.com/varigit/bcm_4343w_fw.git;protocol=https;branch=${BRANCH_brcm};destsuffix=brcm;name=brcm \
	git://git.ti.com/git/wilink8-wlan/wl18xx_fw.git;protocol=https;branch=${BRANCH_tiwlan};destsuffix=tiwlan;name=tiwlan;depth=1 \
	git://git.ti.com/git/ti-bt/service-packs.git;protocol=https;branch=${BRANCH_tibt};destsuffix=tibt;name=tibt;depth=1 \
	file://wl1271-nvs.bin \
"
do_install:append() {
	install -d ${D}${nonarch_base_libdir}/firmware/bcm
	install -m 0755 ${WORKDIR}/brcm/brcm/* ${D}${nonarch_base_libdir}/firmware/brcm/
	install -m 0755 ${WORKDIR}/tibt/initscripts/TIInit_*.bts ${D}${nonarch_base_libdir}/firmware/ti-connectivity
	install -m 0755 ${WORKDIR}/tiwlan/*.bin ${D}${nonarch_base_libdir}/firmware/ti-connectivity
	install -m 0755 ${WORKDIR}/wl1271-nvs.bin ${D}${nonarch_base_libdir}/firmware/ti-connectivity
}

FILES:${PN}-bcm4339 += " \
  ${nonarch_base_libdir}/firmware/brcm/BCM4335C0.hcd \
  ${nonarch_base_libdir}/firmware/brcm/brcmfmac4339-sdio.txt \
"

FILES:${PN}-bcm43430 += " \
  ${nonarch_base_libdir}/firmware/brcm/BCM43430A1.hcd \
  ${nonarch_base_libdir}/firmware/brcm/brcmfmac43430-sdio.txt \
"

