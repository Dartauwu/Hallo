package com.darta.hallo.ui.notifications

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.darta.hallo.Login
import com.darta.hallo.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_logout.*

class Logout : AppCompatActivity() {

    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logout)


        auth = FirebaseAuth.getInstance()


        btn_logout.setOnClickListener {
            auth.signOut()
            Intent(this@Logout, Login::class.java).also {
                it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(it)
            }
        }
    }
}