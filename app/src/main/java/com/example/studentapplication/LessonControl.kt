package com.example.studentapplication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.*
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

lateinit var user:String
lateinit var context: Context
class LessonControl : AppCompatActivity(),NavigationView.OnNavigationItemSelectedListener {
    var myLesson = ArrayList<String>()
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView
    lateinit var listview:ListView
    var arrayList: ArrayList<MyData> = ArrayList()
    var adapter: MyAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lesson_control)
        user = intent.getStringExtra("username").toString()
        context = this
        lesson_rec()
        drawerLayout = findViewById(R.id.drawerlayout)
        navView = findViewById(R.id.navigationview)

        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, 0, 0
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        navView.setNavigationItemSelectedListener(this)


    }
    private fun lesson_rec() {
        val url = "http://10.0.2.2:5000/lessons"
        val json = JSONObject()
        val queue = Volley.newRequestQueue(this)
        val jsonapi: JsonObjectRequest = object : JsonObjectRequest(
            Method.POST, url, json,
            Response.Listener { response ->
                val values =
                    response.getString("name").substring(0, response.getString("name").length - 1)
                val items: List<String> = values.split(",")
                for (item in items)
                    myLesson.add(item)
                listview = findViewById(R.id.listView)


                for (i in 0..myLesson.size-1) {
                    arrayList.add(MyData(myLesson[i]))
                }

                adapter = MyAdapter(this, arrayList)
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

    class MyAdapter(private val context: Context, private val arrayList: java.util.ArrayList<MyData>) : BaseAdapter() {
        private lateinit var name: TextView
        private lateinit var butn: Button
        private lateinit var butnn: Button
        override fun getCount(): Int {
            return arrayList.size
        }
        override fun getItem(position: Int): Any {
            return position
        }
        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override  fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
            var convertView = convertView
            convertView = LayoutInflater.from(context).inflate(R.layout.listitem2, parent, false)
            butn = convertView.findViewById(R.id.ekle)
            butnn = convertView.findViewById(R.id.cikar)
            name = convertView.findViewById(R.id.textView)

            name.text = arrayList[position].adi
            butn.setOnClickListener {
                lessonadd(user,arrayList[position].adi)

            }
            butnn.setOnClickListener {

                lessondel(user,arrayList[position].adi)
            }
            return convertView
        }
    }

    class MyData(var adi: String)

}

private fun lessonadd(uname:String,lessonname:String) {
    val url = "http://10.0.2.2:5000/addlesson"
    val json = JSONObject()
    json.put("_id", uname)
    json.put("adi",lessonname)
    val queue = Volley.newRequestQueue(context)
    val jsonapi: JsonObjectRequest = object : JsonObjectRequest(
        Method.POST, url, json,
        Response.Listener { response ->
            Toast.makeText(context, response.getString("name").toString(), Toast.LENGTH_LONG).show()
        },
        Response.ErrorListener {}) {}
    queue.add(jsonapi)
}
private fun lessondel(uname:String,lessonname: String) {
    val url = "http://10.0.2.2:5000/dellesson"
    val json = JSONObject()
    json.put("_id", uname)
    json.put("adi",lessonname)
    val queue = Volley.newRequestQueue(context)
    val jsonapi: JsonObjectRequest = object : JsonObjectRequest(
        Method.POST, url, json,
        Response.Listener { response ->
            Toast.makeText(context, response.getString("name").toString(), Toast.LENGTH_LONG).show()
        },
        Response.ErrorListener {}) {}
    queue.add(jsonapi)
}



