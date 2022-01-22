package com.example.chatty.activity

import android.content.Intent
import android.os.Bundle
import android.os.Message
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.chatty.R
import com.example.chatty.adapter.UserAdapter
import com.example.chatty.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class UsersActivity : AppCompatActivity() {
    val imageProfile = findViewById<ImageView>(R.id.imageProfile)

    var userList = ArrayList<User>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this,LinearLayout.VERTICAL,false)


val imageBlack = findViewById<ImageView>(R.id.imageBlack)
        imageBlack.setOnClickListener(){
            onBackPressed()
        }

        imageProfile.setOnClickListener(){
            val intent = Intent (this@UsersActivity,
                ProfileActivity :: class.java)
            startActivity(intent)
        }

getUsersLists()

    }

    fun getUsersLists(){
        val firebase:FirebaseUser = FirebaseAuth.getInstance().currentUser!!
        val databaseReference : DatabaseReference = FirebaseDatabase.getInstance().getReference("Users")


        databaseReference.addValueEventListener(object : ValueEventListener{

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext,error.message ,Toast.LENGTH_SHORT).show()


            }

            override fun onDataChange(snapshot: DataSnapshot) {
                userList.clear()
                val currentUser = snapshot.getValue(User::class.java)

                if (currentUser!!.userName == ""){
                    imageProfile.setImageResource(R.drawable.teamwork1)
                }else{
                    Glide.with(this@UsersActivity).load(currentUser.userImage).into(imageProfile)

                }

                for (dataSnapShot:DataSnapshot in snapshot.children){
                    val user = dataSnapShot.getValue(User::class.java)
                    if (!user!!. UserId . equals(firebase.uid)){
                        userList.add(user)
                    }
                }
                val userAdapter = UserAdapter(this@UsersActivity,userList)

            }

        })


    }
}




