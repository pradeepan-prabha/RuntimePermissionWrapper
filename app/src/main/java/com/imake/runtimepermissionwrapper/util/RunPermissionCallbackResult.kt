package com.imake.runtimepermissionwrapper.util

interface RunPermissionCallbackResult {
    fun permissionCallbackResult(permissionResultHashMap: HashMap<Int, PermissionPojo>)
}