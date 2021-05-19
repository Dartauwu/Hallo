package com.darta.hallo

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_register.*
import java.io.ByteArrayOutputStream
import java.util.*

class Register : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth

    val PICK_PHOTO = 100
    var PHOTO_URI: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()

        btn_register.setOnClickListener {
            val nama = et_nama_register.text.toString()
            val kelamin = et_jeniskelamin_register.text.toString().trim()
            val email = et_email_register.text.toString().trim()
            val password = et_password_register.text.toString().trim()

            if (nama.isEmpty()){
               et_nama_register.error = "Data tidak lengkap!!"
                et_nama_register.requestFocus()
                return@setOnClickListener
            }

            if (kelamin.isEmpty()){
                et_jeniskelamin_register.error = "Data tidak lengkap!!"
                et_jeniskelamin_register.requestFocus()
                return@setOnClickListener
            }

            if (email.isEmpty()){
                et_email_register.error = "Email harus diisi"
                et_email_register.requestFocus()
                return@setOnClickListener
            }

            if (password.isEmpty()){
                et_password_register.error = "Password harus diisi"
                et_password_register.requestFocus()
                return@setOnClickListener
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                et_email_register.error = "Email tidak valid"
                et_password_register.requestFocus()
                return@setOnClickListener
            }
            if (password.isEmpty() || password.length < 8){
                et_password_register.error = "Password harus lebih dari 8 karakter"
                et_password_register.requestFocus()
                return@setOnClickListener
            }

           registerUser(email, password)

        }

        image_register.setOnClickListener {
            getPhotoFromPhone()
        }
    }

    private fun getPhotoFromPhone() {
       val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, PICK_PHOTO)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_PHOTO){
            if (resultCode == Activity.RESULT_OK && data!!.data !=null){
                PHOTO_URI = data.data
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver,PHOTO_URI)
                image_register.setImageBitmap(bitmap)
            }
        }
    }
    private fun registerUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this){
                if (it.isSuccessful){
                    Intent(this@Register,MainActivity::class.java).also {
                        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(it)
                    }

                    uploadPhotoToFirebase()

                }else{
                    Toast.makeText(this,it.exception?.message, Toast.LENGTH_SHORT).show()

            }
        }
    }

    private fun uploadPhotoToFirebase() {
        val photoName = UUID.randomUUID().toString()
        val uploadFirebase = FirebaseStorage.getInstance().getReference("profile/images/$photoName")
        uploadFirebase.putFile(PHOTO_URI!!)
                .addOnSuccessListener {
                    uploadFirebase.downloadUrl.addOnSuccessListener {
                        Toast.makeText(this, "$it", Toast.LENGTH_LONG).show()
                        //simpan semua data ke firebase
                        saveAllUserDataBase(it.toString())
                    }
                }
    }

    private fun saveAllUserDataBase(photoUrl:String) {
        val uid = FirebaseAuth.getInstance().uid
        val db = FirebaseDatabase.getInstance().getReference("user/$uid")
        db.setValue(User
               (photoUrl,
                et_nama_register.text.toString(),
                et_jeniskelamin_register.text.toString(),
                et_email_register.text.toString(),
                et_password_register.text.toString() ))

                .addOnSuccessListener {
                    Intent(this@Register,Login::class.java).also {
                        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(it)
                        Toast.makeText(this, "udah" ,Toast.LENGTH_LONG).show()
                    }
                }
                .addOnFailureListener {
                }
    }

    override fun onStart() {
        super.onStart()
        if (auth.currentUser != null){
            Intent(this@Register, MainActivity::class.java).also { intent ->
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
        }
    }

}