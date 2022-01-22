package com.example.chatty.activity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.chatty.R
import com.example.chatty.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import de.hdodenhof.circleimageview.CircleImageView

class ProfileActivity : AppCompatActivity() {

    private lateinit var firebaseUser: FirebaseUser
    private lateinit var databaseReference: DatabaseReference
    private val filePath: Uri? = null

    private  val PICK_IMAGE_REQUEST : Int = 2020

    val imageProfile = findViewById<ImageView>(R.id.imageProfile)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        firebaseUser = FirebaseAuth.getInstance().currentUser!!
        databaseReference = FirebaseDatabase.getInstance().getReference().child(firebaseUser.uid)
        databaseReference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue(User::class.java)
                val userName = findViewById<TextView>(R.id.userName)
                userName.text = user!!.userName
val userImage = findViewById<CircleImageView>(R.id.userImage)
                if (user.userImage == ""){
                    userImage.setImageResource(R.drawable.teamwork1)
                }else{
                    Glide.with(this@ProfileActivity).load(user.userImage).into(userImage)

                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext,error.message,Toast.LENGTH_SHORT).show()
            }

        })

        val imageBlack = findViewById<ImageView>(R.id.imageBlack)
        imageBlack.setOnClickListener(){
            onBackPressed()
        }

        val imageProfile = findViewById<ImageView>(R.id.imageProfile)
        imageProfile.setOnClickListener(){
            val intent = Intent (this@ProfileActivity, UsersActivity :: class.java)
            startActivity(intent)
            finish()
        }


    }
    private fun chooseImage(){
        val intent: Intent = Intent()
        intent.type = "image/'"
        intent.action = Intent. ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent,"Select Image"),PICK_IMAGE_REQUEST)

    }
}