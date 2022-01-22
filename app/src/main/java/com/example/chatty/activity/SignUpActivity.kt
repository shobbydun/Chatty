package com.example.chatty.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.chatty.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUpActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        auth = FirebaseAuth.getInstance()


        val btnsignup: Button = findViewById(R.id.btnsignup)
        btnsignup.setOnClickListener() {
            val etName: EditText = findViewById(R.id.etName)
            val userName = etName.text.toString()
            val etMail: EditText = findViewById(R.id.etMail)
            val email = etMail.text.toString()
            val etPass1: EditText = findViewById(R.id.etPass1)
            val password = etPass1.text.toString()
            val etNamePass2: EditText = findViewById(R.id.etNamePass2)
            val confirmPassword = etNamePass2.text.toString()

            if (TextUtils.isEmpty(userName)) {
                Toast.makeText(applicationContext, "username is required", Toast.LENGTH_SHORT)
                    .show()
            }
            if (TextUtils.isEmpty(email)) {
                Toast.makeText(applicationContext, "Email is required", Toast.LENGTH_SHORT).show()
            }
            if (TextUtils.isEmpty(password)) {
                Toast.makeText(applicationContext, "Password is required", Toast.LENGTH_SHORT)
                    .show()
            }
            if (TextUtils.isEmpty(confirmPassword)) {
                Toast.makeText(
                    applicationContext,
                    "confirm password is required",
                    Toast.LENGTH_SHORT
                ).show()
            }
            if (password != confirmPassword) {
                Toast.makeText(applicationContext, "password not match", Toast.LENGTH_SHORT).show()
            }
            registerUser(userName, email, password)

        }
        val btnlogin : Button =findViewById(R.id.btnlogin)
        btnlogin.setOnClickListener(){
            val intent = Intent (this@SignUpActivity, LoginActivity :: class.java)
            startActivity(intent)
            finish()
        }


    }


    private fun registerUser(userName: String, email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    val user: FirebaseUser? = auth.currentUser
                    val userId: String = user!!.uid
                    databaseReference =
                        FirebaseDatabase.getInstance().getReference("users").child(userId)

                    val hashMap: HashMap<String, String> = HashMap()
                    hashMap.put("userId", userId)
                    hashMap.put("username", userName)
                    hashMap.put("profileImage", "")
                    databaseReference.setValue(hashMap).addOnCompleteListener(this) {
                        if (it.isSuccessful) {
                            val etName: EditText = findViewById(R.id.etName)
                            etName.setText("")
                            val etMail: EditText = findViewById(R.id.etMail)
                            etMail.setText("")
                            val etPass1: EditText = findViewById(R.id.etPass1)
                            etPass1.setText("")
                            val etNamePass2: EditText = findViewById(R.id.etNamePass2)
                            etNamePass2.setText("")

                            val intent = Intent(this@SignUpActivity, UsersActivity::class.java)
                            startActivity(intent)
                            finish()
                        }

                    }
                }
            }
    }
}













