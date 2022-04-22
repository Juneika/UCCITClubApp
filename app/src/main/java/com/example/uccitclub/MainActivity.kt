package com.example.uccitclub

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import android.content.Context
import android.view.*
import android.widget.*
import java.util.ArrayList


class MainActivity : AppCompatActivity() {

    var listCourses = ArrayList<Course>()

    var facebook: ImageView? = null
    var instagram: ImageView? = null
    var twitter: ImageView? = null


    private lateinit var btnemail: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        var facebook: ImageView?
        var instagram: ImageView?
        var twitter: ImageView?
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Creates directory object and links to button
        //Method launches Directory activity
        val directory: ImageView = findViewById(R.id.directory)
        directory.setOnClickListener {
            val intent = Intent(this, Directory::class.java)
            startActivity(intent)
        }

        //Method launches Admission activity
        val admission: ImageView = findViewById(R.id.admission)
        admission.setOnClickListener {
            val intent = Intent(this, Admissions::class.java)
            startActivity(intent)
        }

        //Method launches Timetable activity
        val timetable: ImageView = findViewById(R.id.timetable)
        timetable.setOnClickListener {
            val intent = Intent(this, Timetable::class.java)
            startActivity(intent)
        }

        //Method launches Course activity
        val course: ImageView = findViewById(R.id.course)
        course.setOnClickListener {
            val intent = Intent(this, Course::class.java)
            startActivity(intent)
        }

        btnemail = findViewById(R.id.fabEmail)
        btnemail.setOnClickListener {
            val emailIntent = Intent(Intent.ACTION_SENDTO,
                Uri.fromParts("mailto", "ithod@ucc.edu.jm ", null))
            startActivity(Intent(Intent.createChooser(emailIntent, "Send Email..")))
        }

        facebook = findViewById(R.id.facebook)
        instagram = findViewById(R.id.instagram)
        twitter = findViewById(R.id.twitter)

        facebook.setOnClickListener(View.OnClickListener {
            val sAppLink = "fb://page/uccjamaica"
            val sPackage = "com.facebook.uccjamaica"
            val sWebLink = "https://www.facebook.com/uccjamaica/"
            openLink(sAppLink, sPackage, sWebLink)
        });
        instagram.setOnClickListener(View.OnClickListener {
            val sAppLink = "https://www.instagram.com/uccjamaica"
            val sPackage = "com.instagram.uccjamaica"
            val sWebLink = "https://www.instagram.com/uccjamaica/?hl=en"
            openLink(sAppLink, sPackage, sWebLink)
        });
        twitter.setOnClickListener(View.OnClickListener {
            val sAppLink = "twitter://user?screen_name=UCCjamaica"
            val sPackage = "com.twitter.uccjamaica"
            val sWebLink = "https://twitter.com/UCCjamaica"
            openLink(sAppLink, sPackage, sWebLink)
        });
    }


    @SuppressLint("Range")
    private fun LoadQueryAscending(title: String) {
        var dbManager = DbManager(this)
        val projections = arrayOf("Code", "Name", "Credits", "PreRequisites", "Description")
        val selectionArgs = arrayOf(title)
        //sort by title
        val cursor = dbManager.Query(projections, "Title like ?", selectionArgs, "Title")
        listCourses.clear()
        //ascending
        if (cursor.moveToFirst()) {
            do {
                val Code = cursor.getString(cursor.getColumnIndex("Code"))
                val Name = cursor.getString(cursor.getColumnIndex("Name"))
                val Credits = cursor.getInt(cursor.getColumnIndex("Credits"))
                val PreRequisites = cursor.getString(cursor.getColumnIndex("Pre-requisites"))
                val Description = cursor.getString(cursor.getColumnIndex("Description"))

                listCourses.add(Course(Code, Name, Credits, PreRequisites, Description))

            } while (cursor.moveToNext())
        }

        //adapter
        var myCourseAdapter = MyBooksAdapter(this, listCourses)
        //set adapter
        var svCourse: ScrollView = findViewById(R.id.svCourse)
    }

    inner class MyBooksAdapter : BaseAdapter {
        var listCourseAdapter = ArrayList<Course>()
        var context: Context? = null

        constructor(context: Context, listCoursesAdapter: ArrayList<Course>) : super() {
            this.listCourseAdapter = listCourseAdapter
            this.context = context
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
            //inflate layout course.xml
            var myView = layoutInflater.inflate(R.layout.activity_course, null)
            val myCourse = listCourseAdapter[position]

            val tvCode: TextView = myView.findViewById(R.id.tvCode)
            val tvName: TextView = myView.findViewById(R.id.tvName)
            val tvCredits: TextView = myView.findViewById(R.id.tvCredits)
            val tvPreRequisites: TextView = myView.findViewById(R.id.tvPreRequisites)
            val tvDescription: TextView = myView.findViewById(R.id.tvDescription)

            tvCode.text = myCourse.Code.toString()
            tvName.text = myCourse.Name
            tvCredits.text = myCourse.Credits.toString()
            tvPreRequisites.text = myCourse.PreRequisites
            tvDescription.text = myCourse.Description

            return myView
        }

        override fun getItem(position: Int): Any {
            return listCourseAdapter[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return listCourseAdapter.size
        }

    }

    private fun openLink(sAppLink: String, sPackage: String, sWebLink: String) {
        try {
            val uri = Uri.parse(sAppLink)
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = uri
            intent.setPackage(sPackage)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        } catch (activityNotFoundException: ActivityNotFoundException) {
            val uri = Uri.parse(sWebLink)
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = uri
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }


    }
}

