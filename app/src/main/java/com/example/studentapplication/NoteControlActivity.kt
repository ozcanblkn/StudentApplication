package com.example.studentapplication

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class NoteControlActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_control)
        lateinit var user:String
        user = intent.getStringExtra("username").toString()
        val button = findViewById<Button>(R.id.button)
        val sname = findViewById<EditText>(R.id.student)
        val vize = findViewById<EditText>(R.id.not)
        val final = findViewById<EditText>(R.id.not2)
        button.setOnClickListener {
            note_save(user,sname.text.toString(),vize.text.toString(), final.text.toString())
        }

    }
    private fun note_save(uname: String,sname: String,vize: String,final: String) {
        val url = "http://10.0.2.2:5000/notesave"

        val json = JSONObject()
        json.put("_id", uname)
        json.put("studentname", sname)
        json.put("vize", vize)
        json.put("final", final)
        val queue = Volley.newRequestQueue(this)
        val jsonapi: JsonObjectRequest = object : JsonObjectRequest(
            Method.POST, url, json,
            Response.Listener { response ->
                Toast.makeText( this,response.getString("name") ,Toast.LENGTH_SHORT).show()
            },
            Response.ErrorListener {}) {}
        queue.add(jsonapi)

    }
}