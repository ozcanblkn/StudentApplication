package com.example.studentapplication

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.navigation.NavigationView
import org.json.JSONObject
import java.util.*

class LessonActivity : AppCompatActivity(),NavigationView.OnNavigationItemSelectedListener {
    var myLesson = ArrayList<String>()
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView
    lateinit var listview:ListView
    private lateinit var user:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lesson)
        user = intent.getStringExtra("username").toString()
        listview = findViewById(R.id.listView)
        lesson_rec(user)
        drawerLayout = findViewById(R.id.drawerlayout)
        navView = findViewById(R.id.navigationview)

        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, 0, 0
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        navView.setNavigationItemSelectedListener(this)
    }


    private fun lesson_rec(uname: String) {
        val url = "http://10.0.2.2:5000/lesson"

        val json = JSONObject()
        json.put("_id", uname)
        val queue = Volley.newRequestQueue(this)
        val jsonapi: JsonObjectRequest = object : JsonObjectRequest(
            Method.POST, url, json,
            Response.Listener { response ->
                val values =
                    response.getString("name").substring(0, response.getString("name").length - 1)
                val items: List<String> = values.split(",")
                for (item in items)
                    myLesson.add(item)

                val adapter = ArrayAdapter(this, R.layout.listitem, R.id.textView, myLesson)
                listview.adapter = adapter
            },
            Response.ErrorListener {}) {}
        queue.add(jsonapi)

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.dersler -> {
                val intent = Intent(this, LessonActivity::class.java).putExtra("username",user)
                startActivity(intent)
            }
            R.id.derskayit -> {
                val intent = Intent(this, LessonControl::class.java).putExtra("username",user)
                startActivity(intent)
            }
            R.id.notlar -> {
                val intent = Intent(this, NotesActivity::class.java).putExtra("username",user)
                startActivity(intent)
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

}