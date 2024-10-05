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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.Date

class Login : AppCompatActivity() {
    lateinit var binding: ActivityLogin2Binding
    private lateinit var firebaseref: DatabaseReference
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseref = FirebaseDatabase.getInstance().getReference("test")
        binding = ActivityLogin2Binding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        val username = binding.username
        val pass = binding.password
        val submit_button = binding.button
        auth = Firebase.auth

        submit_button.setOnClickListener{
            val input_user = username.text.toString()
            val input_pass = pass.text.toString()
            if (!binding.username.text.isNullOrBlank()&&!binding.password.text.isNullOrBlank()){
                login(input_user,input_pass,auth)
                }else{
                    return@setOnClickListener
                }
            }
        }


        private fun login(email:String, password:String, auth: FirebaseAuth){
            auth.signInWithEmailAndPassword(email,password).addOnCompleteListener() {
                task -> if (task.isSuccessful){
                    val user = auth.currentUser
                    val intent = Intent(this,MainActivity::class.java)
                    val nameofmandor = email.split("@",".")
                    val mandorlogin = nameofmandor[0]
                val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
                val currentDate = sdf.format(Date())
                val db = Firebase.firestore
                db.collection("Indirect").add(
                    com.example.myapplication.`object`.admin(
                        id = mandorlogin,
                        Waktu = currentDate.toString()
                    )
                )
                intent.putExtra("Scanner",mandorlogin)
                Toast.makeText(this, "Welcome!", Toast.LENGTH_SHORT).show()
                startActivity(intent)
            }else{
                Toast.makeText(this, "Authentication Failed", Toast.LENGTH_SHORT).show()
            }
            }
        }
    }
