package com.mansi.guardianangel

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore

object FirebaseHelper {

    private val db = FirebaseFirestore.getInstance()

    private fun getUid(): String? = FirebaseService.getCurrentUserId()

    fun saveContact(number: String, name: String? = null) {
        val uid = getUid()
        if (uid == null) {
            Log.e("FirebaseHelper", "❌ Cannot save SOS: User not logged in.")
            return
        }


        val data = hashMapOf(
            "contact" to number,
            "name" to (name ?: "Unknown"),
            "timestamp" to System.currentTimeMillis()
        )

        db.collection("users")
            .document(uid)
            .collection("contacts")
            .add(data)
    }

    fun saveSOS(sos: SOsData) {
        val uid = getUid()
        if (uid == null) {
            Log.e("FirebaseHelper", "❌ Cannot save SOS: User not logged in.")
            return
        }

        val data = hashMapOf(
            "timestamp" to sos.timestamp,
            "locationLink" to sos.locationLink,
            "contacts" to sos.contacts
        )

        db.collection("users")
            .document(uid)
            .collection("sos_history")
            .add(data)
    }

    fun getSOSHistory(onData: (List<SOsData>) -> Unit) {
        val uid = getUid()
        if (uid == null) {
            onData(emptyList())
            return
        }

        db.collection("users")
            .document(uid)
            .collection("sos_history")
            .get()
            .addOnSuccessListener { result ->
                val list = result.mapNotNull { doc ->
                    val timestamp = doc.getString("timestamp")
                    val locationLink = doc.getString("locationLink")
                    val contacts = doc.get("contacts") as? List<String>
                    if (timestamp != null && locationLink != null && contacts != null)
                        SOsData(timestamp, locationLink, contacts)
                    else null
                }
                onData(list)
            }
            .addOnFailureListener {
                onData(emptyList())
            }
    }
}
