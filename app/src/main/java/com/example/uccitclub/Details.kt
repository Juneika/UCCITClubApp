package com.example.uccitclub

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class Details : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        //Gets value sent from Directory activity
        val index: String? = this.intent.getStringExtra("listIndex")

        //Creates objects for each activity element
        val name: TextView = findViewById(R.id.tvName)
        val email: TextView = findViewById(R.id.tvEmail)
        val photo: ImageView = findViewById(R.id.ivProfile)

        //Assigns value to elements according to retrieved index
        when (index) {
            "0" -> {
                name.text = "ROSE, Natalie"
                email.text = "ithod@ucc.edu.jm"
                photo.setImageResource(R.drawable.rose)
            }
            "1" -> {
                name.text = "DAVIDSON, Sonia"
                email.text = "businessadminhod@ucc.edu.jm"
                photo.setImageResource(R.drawable.davidson)
            }
            "2" -> {
                name.text = "AMONDE, Tom"
                email.text = "graduatestudiesdirector@ucc.edu.jm"
                photo.setImageResource(R.drawable.amonde)
            }
            "3" -> {
                name.text = "MILLER, Ionie"
                email.text = "appliedpsychologyfaculty@ucc.edu.jm"
                photo.setImageResource(R.drawable.miller)
            }
            "4" -> {
                name.text = "NDAJAH, Peter"
                email.text = "headofschoolsmathit@ucc.edu.jm"
                photo.setImageResource(R.drawable.ndajah)
            }
        }

        //Launches email client when email address is tapped
        email.setOnClickListener {
            val intent =
                Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", email.toString(), null))
            intent.putExtra(Intent.EXTRA_EMAIL, email.toString())
            startActivity(intent)
        }
    }
}
