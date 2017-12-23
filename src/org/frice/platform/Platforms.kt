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

var isOnWindows = false @JvmName(" ### windows") internal set
var isOnMac = false @JvmName(" ### mac") internal set
var isOnLinux = false @JvmName(" ### linux") internal set
var isOnSolaris = false @JvmName(" ### solaris") internal set
var isOnBSD = false @JvmName(" ### bsd") internal set
