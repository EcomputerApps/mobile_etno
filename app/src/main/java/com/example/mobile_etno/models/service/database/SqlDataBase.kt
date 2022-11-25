package com.example.mobile_etno.models.service.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import com.example.mobile_etno.models.Event


class SqlDataBase(
    context: Context?
): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){
    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "etno_db"

        //TABLE EVENTS ->
        private const val TABLE_EVENT = "events"
        private const val ID_EVENT = "idEvent"
        private const val COL_TITLE = "title"
        private const val COL_ADDRESS = "address"
        private const val COL_DESCRIPTION = "description"
        private const val COL_ORGANIZATION = "organization"
        private const val COL_LINK = "link"
        private const val COL_START_DATE = "startDate"
        private const val COL_END_DATE = "endDate"
        private const val COL_PUBLICATION_DATE = "publicationDate"
        private const val COL_TIME = "time"
        private const val COL_LAT = "lat"
        private const val COL_LOG = "log"
        private const val COL_SUBSCRIPTION = "subscription"

        //TABLE IMAGES ->
        private const val TABLE_IMAGES = "images"
        private const val ID_IMAGE = "idImage"
        private const val LINK = "link"
        private const val ID_EVENT_FK = "idEvent"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val sqlCreateTableEvent = "CREATE TABLE $TABLE_EVENT ($ID_EVENT INTEGER PRIMARY KEY, $COL_TITLE TEXT, $COL_ADDRESS TEXT, $COL_DESCRIPTION TEXT, $COL_ORGANIZATION TEXT, $COL_LINK TEXT, $COL_START_DATE TEXT, $COL_END_DATE TEXT, $COL_PUBLICATION_DATE TEXT, $COL_TIME TEXT, $COL_LAT TEXT, $COL_LOG TEXT, $COL_SUBSCRIPTION TEXT)"
        db?.execSQL(sqlCreateTableEvent)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_EVENT")
        onCreate(db)
    }

    fun insertEventDb(idEvent: String?, title: String?, address: String?, description: String?, organization: String?, link: String?, startDate: String?, endDate: String?, publicationDate: String?, time: String?, lat: String?, log: String?, subscription: Boolean?){
        val databaseSQL = this.writableDatabase
        val values = ContentValues()

        values.put(ID_EVENT, idEvent)
        values.put(COL_TITLE, title)
        values.put(COL_ADDRESS, address)
        values.put(COL_DESCRIPTION, description)
        values.put(COL_ORGANIZATION, organization)
        values.put(COL_LINK, link)
        values.put(COL_START_DATE, startDate)
        values.put(COL_END_DATE, endDate)
        values.put(COL_PUBLICATION_DATE, publicationDate)
        values.put(COL_TIME, time)
        values.put(COL_LAT, lat)
        values.put(COL_LOG, log)
        values.put(COL_SUBSCRIPTION, subscription)

        databaseSQL.insert(TABLE_EVENT, null, values)
        databaseSQL.close()
    }
    @SuppressLint("Recycle", "Range")
    fun getEventDb(): MutableList<Event>{
        val listEventSQL: MutableList<Event> = mutableListOf()

        val selectQuery = "SELECT * FROM $TABLE_EVENT"
        val dataBaseSQL = this.readableDatabase

        var cursor: Cursor? = null

        try {
            cursor = dataBaseSQL.rawQuery(selectQuery, null)
        }catch (error: SQLiteException){
            dataBaseSQL.execSQL(selectQuery)
            return ArrayList()
        }
        var idEvent: String
        var title: String
        var address: String
        var description: String
        var organization: String
        var link: String
        var startDate: String
        var endDate: String
        var publicationDate: String
        var time: String
        var lat: String
        var log: String
        var subscription: String

        if(cursor.moveToFirst()){
            do {
                idEvent = cursor.getString(cursor.getColumnIndex(ID_EVENT))
                title = cursor.getString(cursor.getColumnIndex(COL_TITLE))
                address = cursor.getString(cursor.getColumnIndex(COL_ADDRESS))
                description = cursor.getString(cursor.getColumnIndex(COL_DESCRIPTION))
                organization = cursor.getString(cursor.getColumnIndex(COL_ORGANIZATION))
                link = cursor.getString(cursor.getColumnIndex(COL_LINK))
                startDate = cursor.getString(cursor.getColumnIndex(COL_START_DATE))
                publicationDate = cursor.getString(cursor.getColumnIndex(COL_PUBLICATION_DATE))
                time = cursor.getString(cursor.getColumnIndex(COL_TIME))
                lat = cursor.getString(cursor.getColumnIndex(COL_LAT))
                log = cursor.getString(cursor.getColumnIndex(COL_LOG))
                subscription = cursor.getString(cursor.getColumnIndex(COL_SUBSCRIPTION))

               // listEventSQL.add()
            }while (cursor.moveToNext())
        }
     return listEventSQL
    }
}