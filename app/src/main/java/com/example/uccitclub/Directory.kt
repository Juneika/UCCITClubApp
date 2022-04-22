package com.example.uccitclub

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import com.example.uccitclub.R

class Directory : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_directory)

        //Reads faculty list from items in strings.xml file
        val facultyList: Array<String> = resources.getStringArray(R.array.faculty)
        //Creates adapter that sets list type and list item
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, facultyList)

        val listView: ListView = findViewById(R.id.lvFaculty)
        //ListView control populated with list items
        listView.adapter = adapter

        //Defines action when list item is clicked
        listView.setOnItemClickListener { parent, view, position, id ->
            //Displays a message showing list item index
            Toast.makeText(this, "Clicked item : "+position,Toast.LENGTH_SHORT).show()
            //Specifies which activity should be launched
            val intent = Intent(this, Details::class.java);
            //Stores index number to be shared with Details activity
            intent.putExtra("listIndex", position.toString());
            //Starts activity
            this.startActivity(intent);
        }


    }
}
