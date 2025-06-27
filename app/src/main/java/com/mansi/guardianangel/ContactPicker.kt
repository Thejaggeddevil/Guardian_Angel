package com.mansi.guardianangel

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.ContactsContract

object ContactPicker {
    const val CONTACT_PICKER_REQUEST = 101

    fun pickContact(activity: Activity) {
        val intent = Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI)
        activity.startActivityForResult(intent, CONTACT_PICKER_REQUEST)
    }

    fun handleContactPicked(context: Context, data: Intent?): String? {
        val uri: Uri = data?.data ?: return null
        val cursor = context.contentResolver.query(
            uri,
            null,
            null,
            null,
            null
        )
        cursor?.use {
            if (it.moveToFirst()) {
                val numberIndex = it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                return it.getString(numberIndex)
            }
        }
        return null
    }
}
