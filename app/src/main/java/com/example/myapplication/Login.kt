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

class Login : AppCompatActivity() {
    lateinit var binding: ActivityLogin2Binding
    private lateinit var firebaseref : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseref = FirebaseDatabase.getInstance().getReference("test")
        binding = ActivityLogin2Binding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login2)
        val username = binding.username
        val pass = binding.password
        val submit_login = binding.button

        binding.button.setOnClickListener{
            val input_user = username.text.toString()
            val input_pass = pass.text.toString()
            val intent = Intent(this,MainActivity::class.java)
            if (input_pass == "a" && input_user == "a" ){
                Log.d("Login","y")
                startActivity(intent)
            }
//            firebaseref.setValue(input_pass).addOnSuccessListener {
//                Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
//            }.addOnFailureListener{
//                    e ->
//                Log.e("RealtimeDB", "Error adding data", e)
//            }
        }

        }
    }
