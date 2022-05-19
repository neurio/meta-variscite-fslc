DESCRIPTION = "Qt Web Browser"
LICENSE = "GPL-3.0 | The-Qt-Company-Commercial"
LIC_FILES_CHKSUM = "file://LICENSE.GPLv3;md5=a40e2bb02b1ac431f461afd03ff9d1d6"

inherit qmake5
require recipes-qt/qt5/qt5.inc
require recipes-qt/qt5/qt5-git.inc

QT_GIT_PROJECT = "qt-apps"
QT_MODULE_BRANCH = "dev"

SRCREV = "a09ec269e8b296d39d78b45ae251edb3d7bada41"

DEPENDS = "qtbase qtdeclarative qtwebengine"
RDEPENDS:${PN} = "qtvirtualkeyboard"

do_install:append() {
	install -d ${D}${datadir}/${PN}
	mv ${D}/data/user/qt/qtwebbrowser-app/* ${D}${datadir}/${PN}
	rm -rf ${D}/data
}

FILES:${PN} += "${datadir}/${PN}"
FILES:${PN}-dbg += "${datadir}/${PN}/.debug"
