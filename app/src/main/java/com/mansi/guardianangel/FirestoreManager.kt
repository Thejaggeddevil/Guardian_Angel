package com.mansi.guardianangel

import com.google.firebase.firestore.FirebaseFirestore

object FirestoreManager {
    private val db = FirebaseFirestore.getInstance()

    fun writeDocument(collection: String, data: Map<String, Any>, onSuccess: () -> Unit = {}, onFailure: (Exception) -> Unit = {}) {
        db.collection(collection).add(data)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onFailure(it) }
    }

    fun readCollection(collection: String, onComplete: (List<Map<String, Any>>) -> Unit) {
        db.collection(collection).get().addOnSuccessListener {
            val data = it.map { doc -> doc.data }
            onComplete(data)
        }
    }
}
