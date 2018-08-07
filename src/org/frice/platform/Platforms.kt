/**
 * Run your codes under a specific platform
 * @author ice1000
 * @since v1.7.12
 */
@file:JvmName("Platforms")

package org.frice.platform

/** for Windowses */
var onWindows: Runnable? = null

/** for Mac OS X */
var onMac: Runnable? = null

/** for Linuxes */
var onLinux: Runnable? = null

/** for SunOS and Solaris */
var onSolaris: Runnable? = null

/** for FreeBSD */
var onBSD: Runnable? = null

var isOnWindows = false @JvmName(" ### windows") internal set
var isOnMac = false @JvmName(" ### mac") internal set
var isOnLinux = false @JvmName(" ### linux") internal set
var isOnSolaris = false @JvmName(" ### solaris") internal set
var isOnBSD = false @JvmName(" ### bsd") internal set
