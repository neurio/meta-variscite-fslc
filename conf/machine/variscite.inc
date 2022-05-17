INHERIT += "image-buildinfo"

MACHINE_FEATURES += " pci bluetooth wifi"

IMX_DEFAULT_BSP = "nxp"

USE_VT = "0"

KERNEL_IMAGETYPE:mx8-generic-bsp = "Image.gz"

WKS_FILE:mx8-generic-bsp = "imx-imx-boot-singlepart.wks.in"

DEFAULTTUNE:mx8mm-generic-bsp  ?= "cortexa53-crypto"
DEFAULTTUNE:mx8mn-generic-bsp  ?= "cortexa53-crypto"
DEFAULTTUNE:mx8mq-generic-bsp  ?= "cortexa53-crypto"
DEFAULTTUNE:mx8qxp-generic-bsp ?= "cortexa35-crypto"
DEFAULTTUNE:mx8qm-generic-bsp ?= "cortexa72-cortexa53-crypto"

# Variscite BSP default providers
PREFERRED_PROVIDER:u-boot = "u-boot-variscite"
PREFERRED_PROVIDER:u-boot-fw-utils = "u-boot-fw-utils"
PREFERRED_PROVIDER:virtual/bootloader = "u-boot-variscite"
PREFERRED_PROVIDER:virtual/kernel = "linux-variscite"

# Use i.MX Gstreamer Version
MACHINE_GSTREAMER_1_0_PLUGIN:mx8-generic-bsp = "imx-gst1.0-plugin"
PREFERRED_VERSION:gstreamer1.0:mx8-generic-bsp ?= "1.16.2.imx"
PREFERRED_VERSION:gstreamer1.0-plugins-base:mx8-generic-bsp ?= "1.16.2.imx"
PREFERRED_VERSION:gstreamer1.0-plugins-good:mx8-generic-bsp ?= "1.16.3.imx"
PREFERRED_VERSION:gstreamer1.0-plugins-bad:mx8-generic-bsp ?= "1.16.3.imx"
PREFERRED_VERSION:gstreamer1.0-libav:mx8-generic-bsp ?= "1.16.0"

# Add VPU and Cortex M4/M7 firmware
MACHINE_FIRMWARE:append:mx8-generic-bsp = " \
	firmware-imx-vpu-imx8-generic-bsp \
	freertos-variscite \
	"

MACHINE_EXTRA_RDEPENDS += " \
	gptfdisk \
	hostapd \
	hdparm \
	imx-kobs \
	iw \
	kernel-modules \
	kmod \
	${@bb.utils.contains('DISTRO', 'b2qt', '', \
           bb.utils.contains('DISTRO_FEATURES', 'wayland', 'networkmanager networkmanager-nmcli', \
                                                       '', d), d)} \
	packagegroup-tools-bluetooth \
	bluez-alsa \
	pm-utils \
	rng-tools \
	wpa-supplicant \
	wireless-regdb-static \
	u-boot-fw-utils \
	u-boot-splash \
"

# Packages added to all images (including core-image-minimal)
IMAGE_INSTALL:append:mx8-generic-bsp = " \
	kernel-image \
	kernel-devicetree \
"

IMAGE_FSTYPES = "tar.gz wic.gz"

# Wayland based distros add systemd to DISTRO_FEATURES
# Remove sysvinit or systemd based on DISTRO_FEATURES to avoid conflicts
DISTRO_FEATURES_BACKFILL_CONSIDERED:append = "${@bb.utils.contains('DISTRO_FEATURES', 'systemd', ' sysvinit', '', d)}"
DISTRO_FEATURES_BACKFILL_CONSIDERED:append = "${@bb.utils.contains('DISTRO_FEATURES', 'sysvinit', ' systemd', '', d)}"

# Use bluez-alsa instead of pulseaudio
DISTRO_FEATURES_BACKFILL_CONSIDERED:append = " pulseaudio"
