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

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var firebaseUser: FirebaseUser


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()
        firebaseUser = auth.currentUser!!

        //check user login then navigate to the user screen
        if (firebaseUser != null){
            val intent = Intent (this@LoginActivity, UsersActivity :: class.java)
            startActivity(intent)
            finish()

        }

        val btnlogin: Button = findViewById(R.id.btnlogin)
        btnlogin.setOnClickListener(){
            val etMail: EditText = findViewById(R.id.etMail)
            val email = etMail.text.toString()
            val etPass1: EditText = findViewById(R.id.etPass1)
            val password = etPass1.text.toString()

            if (TextUtils.isEmpty(email) && TextUtils.isEmpty(password)){
               Toast.makeText(applicationContext,"Email and Password are required",Toast.LENGTH_SHORT).show()

        }else{
            auth . signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this){
                    if (it.isSuccessful){
                        etMail. setText("")
                        etPass1.setText("")
                        val intent = Intent (this@LoginActivity, UsersActivity :: class.java)
                        startActivity(intent)
                        finish()
                    }else{
                        Toast.makeText(applicationContext,"Email or Password are invalid",Toast.LENGTH_SHORT).show()

                    }
                }

            }
        }
        val btnsignup: Button = findViewById(R.id.btnsignup)
        btnsignup.setOnClickListener(){
            val intent = Intent (this@LoginActivity, SignUpActivity :: class.java)
            startActivity(intent)
            finish()
        }
    }
}