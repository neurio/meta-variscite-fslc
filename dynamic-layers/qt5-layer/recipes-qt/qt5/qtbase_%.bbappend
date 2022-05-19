FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI:append = " \
	file://0001-linuxfb-platform-plugin-add-rotation-support.patch \
"

BACKEND_FB = "linuxfb"
BACKEND_FB:imxgpu3d = "eglfs"
BACKEND_WAYLAND = "wayland"
BACKEND_WAYLAND:imxgpu3d = "wayland-egl"
BACKEND = "${@bb.utils.contains('DISTRO_FEATURES', 'wayland', '${BACKEND_WAYLAND}', \
	   bb.utils.contains('DISTRO_FEATURES', 'x11', 'xcb', '${BACKEND_FB}', d), d)}"
EGLFS_INTEGRATION:mx6 = "eglfs_viv"
EGLFS_INTEGRATION:mx8 = "eglfs_kms"
QML_USE_SWCTX = "yes"
QML_USE_SWCTX:imxgpu3d = "no"

# build linuxfb backend if required
PACKAGECONFIG:append = " fontconfig tslib ${@bb.utils.filter('BACKEND', 'linuxfb', d)}"

do_install:append () {
	if ${@bb.utils.contains('DISTRO','b2qt','false','true',d)}; then
		mkdir -p ${D}${sysconfdir}/profile.d
		echo "export QT_QPA_PLATFORM=${BACKEND}" >> ${D}${sysconfdir}/profile.d/qt5.sh
		if [ "${BACKEND}" = "eglfs" ]; then
			echo "export QT_QPA_EGLFS_FORCEVSYNC=1" >> ${D}${sysconfdir}/profile.d/qt5.sh
			echo "export QT_QPA_EGLFS_INTEGRATION=${EGLFS_INTEGRATION}" >> ${D}${sysconfdir}/profile.d/qt5.sh
		elif [ "${BACKEND}" = "xcb" ]; then
			echo "export DISPLAY=:0" >> ${D}${sysconfdir}/profile.d/qt5.sh
		fi
		if [ "${QML_USE_SWCTX}" = "yes" ]; then
			# allow using QML with SW rendering for iMX6UL and iMX7
			echo "export QMLSCENE_DEVICE=softwarecontext" >> ${D}${sysconfdir}/profile.d/qt5.sh
		else
			# enable multiple buffer for iMX6 and iMX8 families
			echo "export FB_MULTI_BUFFER=2" >> ${D}${sysconfdir}/profile.d/qt5.sh
		fi
		echo "if test -z \"\$XDG_RUNTIME_DIR\"; then" >> ${D}${sysconfdir}/profile.d/qt5.sh
		echo "   export XDG_RUNTIME_DIR=/run/user" >> ${D}${sysconfdir}/profile.d/qt5.sh
		echo "fi" >> ${D}${sysconfdir}/profile.d/qt5.sh
	fi
}
