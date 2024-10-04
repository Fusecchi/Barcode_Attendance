package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.databinding.ActivityLogin2Binding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Login : AppCompatActivity() {
    lateinit var binding: ActivityLogin2Binding
    private lateinit var firebaseref: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseref = FirebaseDatabase.getInstance().getReference("test")
        binding = ActivityLogin2Binding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        val username = binding.username
        val pass = binding.password
        val db = Firebase.firestore

        binding.button.setOnClickListener {
            val inputUser = username.text.toString()
            val inputPass = pass.text.toString()
            val intent = Intent(this, MainActivity::class.java)
            Log.d("Login", inputUser)
            Log.d("Login", inputPass)

            // read data
            val userdb = db.collection("users")
            val query = userdb.whereEqualTo("email", inputUser)
            query.get()
                .addOnSuccessListener { doc ->
                    if (doc.size() > 0) {
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, "Wrongies", Toast.LENGTH_SHORT).show()
                    }
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
                }

            // Add data
//            val user = hashMapOf(
//                "id" to "IDTEST",
//                "email" to "email1@kei.com",
//                "name" to "NAMETEST",
//                "qr" to "https://google.com"
//            )
//
//            db.collection("users")
//                .add(user)
//                .addOnSuccessListener { docRef ->
//                    Toast.makeText(this, docRef.id, Toast.LENGTH_SHORT).show()
//                }
//                .addOnFailureListener { e ->
//                    Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
//                }
//        }
        }

    }
}
