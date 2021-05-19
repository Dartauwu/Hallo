package com.darta.hallo

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.klinker.android.link_builder.Link
import com.klinker.android.link_builder.applyLinks
import kotlinx.android.synthetic.main.activity_login.*


class Login : AppCompatActivity() {

    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        tv_daftar_login.setOnClickListener {
            Intent(this@Login, Register::class.java).also {
                startActivity(it)
            }
        }

        tv_reset_login.setOnClickListener {
            Intent(this@Login, Reset::class.java).also {
                startActivity(it)
            }
        }

        linkSetUp()

        auth = FirebaseAuth.getInstance()

        btn_login.setOnClickListener {
            val email = et_email_login.text.toString().trim()
            val password = et_password_login.text.toString().trim()

            if (email.isEmpty()){
                et_email_login.error = "Email harus diisi"
                et_email_login.requestFocus()
                return@setOnClickListener
            }

            if (password.isEmpty()){
                et_password_login.error = "Password harus diisi"
                et_password_login.requestFocus()
                return@setOnClickListener
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                et_email_login.error = "Email tidak valid"
                et_password_login.requestFocus()
                return@setOnClickListener
            }

            if (password.isEmpty() || password.length < 8){
                et_password_login.error = "Password harus lebih dari 8 karakter"
                et_password_login.requestFocus()
                return@setOnClickListener
            }

            loginUser(email, password)

        }
    }

    private fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this){
                if (it.isSuccessful){
                    Intent(this@Login, MainActivity::class.java).also { intent ->
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                    }
                }else{
                    Toast.makeText(this,"${it.exception?.message}",Toast.LENGTH_SHORT).show()
                }
            }

    }

    private fun linkSetUp() {
        val LinkReset : Link = Link("Reset")
            .setTextColor(Color.BLACK)
            .setTextColorOfHighlightedLink(Color.WHITE)
            .setHighlightAlpha(5f)
            .setBold(true)
        tv_reset_login.applyLinks(LinkReset)

        val LinkDaftar : Link = Link("Daftar")
            .setTextColor(Color.BLACK)
            .setTextColorOfHighlightedLink(Color.WHITE)
            .setHighlightAlpha(5f)
            .setBold(true)
        tv_daftar_login.applyLinks(LinkDaftar)
    }

    override fun onStart() {
        super.onStart()
        if (auth.currentUser != null){
            Intent(this@Login, MainActivity::class.java).also { intent ->
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
        }
    }
}

