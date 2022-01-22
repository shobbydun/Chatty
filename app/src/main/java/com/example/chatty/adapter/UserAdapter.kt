package com.example.chatty.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.chatty.R
import com.google.firebase.firestore.auth.User
import de.hdodenhof.circleimageview.CircleImageView

class UserAdapter (private val context: Context,private val userList: ArrayList<com.example.chatty.model.User>) :
    RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = userList[position]
        holder.textUserName .text = user.userName
        Glide.with(context).load(user.userImage).placeholder(R.drawable.teamwork1).into(holder.imageUser)

    }
    class ViewHolder(view:View):RecyclerView.ViewHolder(view){
        val textUserName:TextView= view.findViewById(R.id.userName)
        val textTemp:TextView = view.findViewById(R.id.temp)
        val imageUser:CircleImageView = view.findViewById(R.id.userImage)
    }

}

