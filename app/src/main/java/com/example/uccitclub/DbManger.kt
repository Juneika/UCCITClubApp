package com.example.uccitclub

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteQueryBuilder
import android.widget.Toast

class DbManager {

    var dbName = "MyCourse"
    var dbTable = "Course"
    var colID = "Code"
    var colName = "Name"
    var colCredits = "Credits"
    var colPreRequisites = "PreRequisites"
    var colDescription = "Description"
    var dbVersion = 1

    val sqlCreateTable =
        "CREATE TABLE IF NOT EXISTS "+dbTable+" ("+colID +" TEXT PRIMARY KEY, "+ colName +" TEXT, "+ colCredits +" INTEGER, "+ colPreRequisites +" TEXT, "+ colDescription +" TEXT)"

    var sqlDB: SQLiteDatabase? = null

    constructor(context: Context)
    {
        var db = DatabaseHelperBooks(context)
        sqlDB = db.writableDatabase
    }

    inner class DatabaseHelperBooks : SQLiteOpenHelper {
        var context: Context? = null

        constructor(context: Context) : super(context, dbName, null, dbVersion) {
            this.context = context
        }

        override fun onCreate(db: SQLiteDatabase?) {
            db!!.execSQL(sqlCreateTable)
            Toast.makeText(this.context, "The database is created!", Toast.LENGTH_SHORT).show()
        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
            db!!.execSQL("DROP TABLE IF Exists" + dbTable)
        }
    }

    fun Query(projection : Array<String>, selection : String, selectionArgs : Array<String>, sorOrder : String) : Cursor
    {
        val qb = SQLiteQueryBuilder()
        qb.tables = dbTable
        val cursor = qb.query(sqlDB, projection, selection, selectionArgs, null, null, sorOrder)
        return cursor
    }

}