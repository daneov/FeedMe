package tech.swiftin.feedme.scan

import android.Manifest

data class PermissionViewModel(
    val granted: Boolean,
    val permission: String,
    val description: String,
)
