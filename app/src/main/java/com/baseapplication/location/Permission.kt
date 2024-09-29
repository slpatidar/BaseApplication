package com.baseapplication.location

import android.content.Context
import android.content.pm.PackageManager
import android.content.pm.PermissionInfo

class Permission @JvmOverloads constructor(
    var name: String,
    protected var title: Int = 0,
    protected var description: Int = 0
) {
    fun permissions(): Array<String> {
        val p: Array<String> = arrayOf(name)
        return p
    }

    fun getPermissionLabel(permission: String?, packageManager: PackageManager): CharSequence? {
        try {
            val permissionInfo: PermissionInfo = packageManager.getPermissionInfo((permission)!!, 0)
            return permissionInfo.loadLabel(packageManager)
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return null
    }

    fun getPermissionDescription(
        permission: String?,
        packageManager: PackageManager
    ): CharSequence? {
        try {
            val permissionInfo: PermissionInfo = packageManager.getPermissionInfo((permission)!!, 0)
            return permissionInfo.loadDescription(packageManager)
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return null
    }

    fun title(context: Context): String {
        if (title != 0) {
            return context.getString(title)
        } else {
            return getPermissionLabel(name, context.getPackageManager()).toString() + ""
        }
    }

    fun description(context: Context): String {
        if (description != 0) {
            return context.getString(description)
        } else {
            return getPermissionDescription(name, context.getPackageManager()).toString() + ""
        }
    }
}
