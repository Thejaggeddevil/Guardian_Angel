package com.mansi.guardianangel

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

object FirebaseHelper {

    private val db = FirebaseFirestore.getInstance()

    private fun getUid(): String? = FirebaseService.getCurrentUserId()

    fun saveContact(number: String, name: String? = null) {
        val uid = getUid()
        if (uid == null) {
            Log.e("FirebaseHelper", "❌ Cannot save contact: User not logged in.")
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
            .addOnSuccessListener {
                Log.d("FirebaseHelper", "✅ Contact saved successfully.")
            }
            .addOnFailureListener {
                Log.e("FirebaseHelper", "❌ Failed to save contact: ${it.message}")
            }
    }

    fun deleteContact(context: Context, number: String) {
        val uid = getUid()
        if (uid == null) {
            Toast.makeText(context, "User not logged in", Toast.LENGTH_SHORT).show()
            return
        }

        db.collection("users")
            .document(uid)
            .collection("contacts")
            .whereEqualTo("contact", number)
            .get()
            .addOnSuccessListener { querySnapshot ->
                for (doc in querySnapshot.documents) {
                    doc.reference.delete()
                }
                Toast.makeText(context, "Contact deleted", Toast.LENGTH_SHORT).show()
                Log.d("FirebaseHelper", "✅ Contact deleted.")
            }
            .addOnFailureListener { e ->
                Log.e("FirebaseHelper", "❌ Failed to delete contact", e)
                Toast.makeText(context, "Failed to delete contact", Toast.LENGTH_SHORT).show()
            }
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
            .addOnSuccessListener {
                Log.d("FirebaseHelper", "✅ SOS saved successfully.")
            }
            .addOnFailureListener {
                Log.e("FirebaseHelper", "❌ Failed to save SOS: ${it.message}")
            }
    }

    fun getSOSHistory(onData: (List<SOsData>) -> Unit) {
        val uid = getUid()
        if (uid == null) {
            Log.w("FirebaseHelper", "⚠️ User not logged in while fetching SOS history.")
            onData(emptyList())
            return
        }

        db.collection("users")
            .document(uid)
            .collection("sos_history")
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { result ->
                val list = result.mapNotNull { doc ->
                    val timestamp = doc.getString("timestamp") ?: return@mapNotNull null
                    val locationLink = doc.getString("locationLink") ?: return@mapNotNull null
                    val contacts = doc.get("contacts") as? List<String> ?: return@mapNotNull null

                    SOsData(timestamp, locationLink, contacts)
                }
                Log.d("FirebaseHelper", "✅ SOS history fetched: ${list.size} entries")
                onData(list)
            }
            .addOnFailureListener {
                Log.e("FirebaseHelper", "❌ Failed to fetch SOS history: ${it.message}")
                onData(emptyList())
            }
    }

    fun debugPrintUserName(context: Context) {
        val uid = getUid() ?: return
        db.collection("users")
            .document(uid)
            .get()
            .addOnSuccessListener { doc ->
                val name = doc.getString("name")
                Log.d("FirebaseHelper", "👤 Firestore name = $name")
                Toast.makeText(context, "Firestore name: $name", Toast.LENGTH_LONG).show()
            }
            .addOnFailureListener {
                Log.e("FirebaseHelper", "❌ Failed to fetch user document: ${it.message}")
            }
    }
}
