# Copyright (C) 2015 Freescale Semiconductor
# Copyright 2017-2021 NXP
# Released under the MIT license (see COPYING.MIT for the terms)

DESCRIPTION = "NXP Image to validate i.MX machines. \
This image contains everything used to test i.MX machines including GUI, \
demos and lots of applications. This creates a very large image, not \
suitable for production."
LICENSE = "MIT"

inherit core-image

### WARNING: This image is NOT suitable for production use and is intended
###          to provide a way for users to reproduce the image used during
###          the validation process of i.MX BSP releases.

IMAGE_FSTYPES = " wic "

CORE_IMAGE_EXTRA_INSTALL += " wayland weston "
DISTRO_FEATURES:append = ' wayland wifi glib ipv6 ipv4 ipsec'

## Select Image Features
IMAGE_FEATURES += " \
    debug-tweaks \
    tools-profile \
    tools-sdk \
    package-management \
    splash \
    nfs-server \
    tools-debug \
    ssh-server-dropbear \
    tools-testapps \
    hwcodecs \
    ${@bb.utils.contains('DISTRO_FEATURES', 'wayland', 'weston', \
       bb.utils.contains('DISTRO_FEATURES',     'x11', 'x11-base x11-sato', \
                                                       '', d), d)} \
"

V2X_PKGS = ""
V2X_PKGS:mx8dxl-nxp-bsp = "packagegroup-imx-v2x"

DOCKER ?= ""
DOCKER:mx8-nxp-bsp = "docker"

G2D_SAMPLES              = ""
G2D_SAMPLES:imxgpu2d     = "imx-g2d-samples"
G2D_SAMPLES:mx93-nxp-bsp = "imx-g2d-samples"

CORE_IMAGE_EXTRA_INSTALL += " \
    packagegroup-core-full-cmdline \
    packagegroup-tools-bluetooth \
    packagegroup-fsl-tools-audio \
    packagegroup-fsl-tools-gpu \
    packagegroup-fsl-tools-gpu-external \
    packagegroup-fsl-tools-testapps \
    packagegroup-fsl-tools-benchmark \
    packagegroup-imx-isp \
    packagegroup-imx-security \
    packagegroup-fsl-gstreamer1.0 \
    packagegroup-fsl-gstreamer1.0-full \
    firmwared \
    ${@bb.utils.contains('DISTRO_FEATURES', 'x11 wayland', 'weston-xwayland xterm', '', d)} \
    ${V2X_PKGS} \
    ${DOCKER} \
    ${G2D_SAMPLES} \
"
PACKAGE_EXCLUDE = " connman connman-client connman-tools"

IMAGE_INSTALL:append = " \
			uuu \
			nxp-wlan-sdk \
			kernel-module-nxp89xx \
			dhcpcd \
			lshw \
			p7zip \
			apt \
			mpg123 \
			i2c-tools \
			minicom \
			ethtool \
			eth-config \	
			glibc \
			rsync \
			sqlite3 \
			cronie \
			packagegroup-qt6-essentials \
			packagegroup-qt6-imx  \
			qtserialbus \
			qtcharts  \
			qtvirtualkeyboard  \
			linux-firmware-rtl8188 \
			linux-firmware-mt7601u \
			read-edid \
			grub  \
			grub-bootconf  \
			grub-efi  \
			python3-pyserial \
			pulseaudio \
			alsa-plugins \
			alsa-lib \
			alsa-tools \
			alsa-utils \
			imx-codec \
			tzdata \
			modemmanager \
			wpa-supplicant \
			networkmanager \
			python3-pytz \
			libgpiod \
                       libgpiod-tools \
"

EXTRA_IMAGE_FEATURES ?= " tools-debug tools-sdk"
SDKMACHINE ?= "x86_64"


IMAGE_FEATURES_REMOVE = "nomodeset"
