package com.imake.runtimepermissionwrapper

interface RunPermissionCallbackResult {
    fun permissionCallbackResult(permissionResultHashMap: HashMap<Int, PermissionPojo>)
}