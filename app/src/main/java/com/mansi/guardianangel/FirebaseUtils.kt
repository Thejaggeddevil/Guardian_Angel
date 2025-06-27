package com.mansi.guardianangel

import com.google.firebase.firestore.FirebaseFirestore

object FirebaseUtils {
    private val db = FirebaseFirestore.getInstance()

    fun saveToFirestore(collection: String, document: String, data: Map<String, Any>) {
        db.collection(collection).document(document).set(data)
    }
}
