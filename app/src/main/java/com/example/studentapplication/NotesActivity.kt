package com.example.studentapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.content.Context
import android.content.Intent

import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.navigation.NavigationView
import org.json.JSONObject

class NotesActivity : AppCompatActivity(),NavigationView.OnNavigationItemSelectedListener {
    lateinit var user:String
    private lateinit var drawerLayout: DrawerLayout
    lateinit var listView: ListView
    var arrayList: ArrayList<MyData> = ArrayList()
    var adapter: MyAdapter? = null
    var lessons = ArrayList<String>()
    var note1 = ArrayList<String>()
    var note2 = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes)
        user = intent.getStringExtra("username").toString()
        lesson_rec(user)
    }
    class MyAdapter(private val context: Context, private val arrayList: java.util.ArrayList<MyData>) : BaseAdapter() {
        private lateinit var ders: TextView
        private lateinit var not1: TextView
        private lateinit var not2: TextView

        override fun getCount(): Int {
            return arrayList.size
        }
        override fun getItem(position: Int): Any {
            return position
        }
        override fun getItemId(position: Int): Long {
            return position.toLong()
        }
        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
            var convertView = convertView
            convertView = LayoutInflater.from(context).inflate(R.layout.notes_item, parent, false)
            ders = convertView.findViewById(R.id.textView)
            not1 = convertView.findViewById(R.id.not1)
            not2 = convertView.findViewById(R.id.not2)

            ders.text = " " + arrayList[position].name
            not1.text = arrayList[position].note
            not2.text = arrayList[position].note2

            return convertView
        }
    }
    class MyData(var name: String, var note: String ,var note2: String)


    fun lesson_rec(uname: String) {
        val url = "http://10.0.2.2:5000/lessonnotes"

        val json = JSONObject()
        json.put("_id", uname)
        val queue = Volley.newRequestQueue(this)
        val jsonapi: JsonObjectRequest = object : JsonObjectRequest(
            Method.POST, url, json,
            Response.Listener { response->


                val values = response.getString("name").substring(0,response.getString("name").length-1)
                val not1 = response.getString("not1").substring(0,response.getString("not1").length-1)
                val not2 = response.getString("not2").substring(0,response.getString("not2").length-1)
                val items: List<String> = values.split(",")
                val items2 : List<String> = not1.split(",")
                val items3 : List<String> = not2.split(",")
                for (item in items)
                    lessons.add(item)
                for (item in items2)
                    note1.add(item)
                for (item in items3)
                    note2.add(item)

                listView = findViewById(R.id.listView)
                for (i in 0..note1.size-1) {
                    arrayList.add(MyData(lessons[i],note1[i],note2[i]))
                }



                adapter = MyAdapter(this, arrayList)
                listView.adapter = adapter

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

