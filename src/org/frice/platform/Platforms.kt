/**
 * Run your codes under a specific platform
 * @author ice1000
 * @since v1.7.12
 */
@file:JvmName("Platforms")

package org.frice.platform

import org.frice.obj.SideEffect

/** for Windowses */
var onWindows: SideEffect? = null

/** for Mac OS X */
var onMac: SideEffect? = null

/** for Linuxes */
var onLinux: SideEffect? = null

/** for SunOS and Solaris */
var onSolaris: SideEffect? = null

/** for FreeBSD */
var onBSD: SideEffect? = null
