package com.mansi.guardianangel

import com.google.firebase.firestore.FirebaseFirestore

object FirebaseHelper {
    private val db = FirebaseFirestore.getInstance()
    private val uid: String = FirebaseService.getCurrentUserId()

    fun saveContact(number: String) {
        val data = hashMapOf("contact" to number)
        db.collection("users").document(uid).collection("contacts").add(data)
    }

    fun saveSOS(message: String) {
        val data = hashMapOf("message" to message, "timestamp" to System.currentTimeMillis())
        db.collection("users").document(uid).collection("sos_history").add(data)
    }

    fun getSOSHistory(onData: (List<String>) -> Unit) {
        db.collection("users").document(uid).collection("sos_history").get()
            .addOnSuccessListener { result ->
                val list = result.mapNotNull { it.getString("message") }
                onData(list)
            }
    }
}
