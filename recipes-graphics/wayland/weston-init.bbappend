FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

RDEPENDS:${PN}:append = " adwaita-icon-theme adwaita-icon-theme-cursors"

# [Shell] is already uncommented by default in Variscite's weston.ini
INI_UNCOMMENT_ASSIGNMENTS:remove:mx8mq-nxp-bsp = " \
    \\[shell\\] \
"

INI_UNCOMMENT_ASSIGNMENTS:append:mx6-nxp-bsp = " \
    use-g2d=1 \
"

SRC_URI:append = " \
	file://weston.service \
"

do_install:append() {
	install -D -p -m0644 ${WORKDIR}/weston.service ${D}${systemd_system_unitdir}/weston.service	
}
