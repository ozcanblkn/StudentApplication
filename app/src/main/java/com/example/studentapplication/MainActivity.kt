package com.example.studentapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject


open class MainActivity : AppCompatActivity(){






    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val button = findViewById<Button>(R.id.button)
        val button2 = findViewById<Button>(R.id.button2)
        val username = findViewById<EditText>(R.id.username)
        val sifre = findViewById<EditText>(R.id.sifre)
        val url1 = "http://10.0.2.2:5000/login"
        val url2 = "http://10.0.2.2:5000/login2"
        button.setOnClickListener {
            login(username.text.toString(),sifre.text.toString(),url1)
        }

        button2.setOnClickListener {
            login(username.text.toString(),sifre.text.toString(),url2)
        }

    }

    private fun login(uname: String, sifre: String, url: String) {


        val json = JSONObject()
        json.put("_id", uname)
        json.put("sifre", sifre)

        val queue = Volley.newRequestQueue(this)
        val jsonapi: JsonObjectRequest = object : JsonObjectRequest(
            Method.POST, url, json,
            Response.Listener { response->
                if(response.getString("_id").toString() == uname)
                {
                    val intent = Intent(this, LessonActivity::class.java)
                    intent.putExtra("username", response.getString("_id").toString())
                    startActivity(intent)
                }
            },
            Response.ErrorListener { Toast.makeText(this, "Giriş Yapılamadı" , Toast.LENGTH_LONG).show()}) {}
        queue.add(jsonapi)

    }





}

