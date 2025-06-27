package com.mansi.guardianangel

import com.google.firebase.auth.FirebaseAuth

object FirebaseService {
    fun getCurrentUserId(): String {
        return FirebaseAuth.getInstance().currentUser?.uid ?: "unknown_user"
    }
}
