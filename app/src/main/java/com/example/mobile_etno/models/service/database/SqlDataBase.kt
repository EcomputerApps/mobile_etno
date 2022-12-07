package com.example.mobile_etno.models.service.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import com.example.mobile_etno.models.Event
import com.example.mobile_etno.models.ImageModelDB


class SqlDataBase(
    context: Context?
): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){
    companion object {
        private const val DATABASE_VERSION = 9
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
        //private const val COL_SUBSCRIPTION = "subscription"

        //TABLE IMAGES ->
        private const val TABLE_IMAGES = "images"
        private const val ID_IMAGE = "idImage"
        private const val COL_LINK_IMAGE = "link"
        private const val ID_EVENT_FK = "idEvent"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val sqlCreateTableEvent = "CREATE TABLE $TABLE_EVENT ($ID_EVENT TEXT PRIMARY KEY, $COL_TITLE TEXT, $COL_ADDRESS TEXT, $COL_DESCRIPTION TEXT, $COL_ORGANIZATION TEXT, $COL_LINK TEXT, $COL_START_DATE TEXT, $COL_END_DATE TEXT, $COL_PUBLICATION_DATE TEXT, $COL_TIME TEXT, $COL_LAT TEXT, $COL_LOG TEXT, UNIQUE($COL_TITLE))"
        val sqlCreateTableImage = "CREATE TABLE $TABLE_IMAGES ($ID_IMAGE TEXT PRIMARY KEY, $COL_LINK_IMAGE TEXT, $ID_EVENT_FK TEXT , FOREIGN KEY($ID_EVENT_FK) REFERENCES $TABLE_EVENT($ID_EVENT), UNIQUE($COL_LINK_IMAGE))"
        db?.execSQL(sqlCreateTableEvent)
        db?.execSQL(sqlCreateTableImage)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_EVENT")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_IMAGES")
        onCreate(db)
    }

    fun deleteEvents(){
        val db = this.writableDatabase
        db.execSQL("DELETE FROM $TABLE_EVENT")
    }

    fun deleteImages(){
        val db = this.writableDatabase
        db.execSQL("DELETE FROM $TABLE_IMAGES")
    }

    fun insertEventDb(idEvent: String?, title: String?, address: String?, description: String?, organization: String?, link: String?, startDate: String?, endDate: String?, publicationDate: String?, time: String?, lat: String?, long: String?){
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
        values.put(COL_LOG, long)

        databaseSQL.insert(TABLE_EVENT, null, values)
        databaseSQL.close()
    }

    fun insertImageDb(idImage: String?, linkImage: String?, idEvent: String?){
        val databaseSQL = this.writableDatabase
        val values = ContentValues()

        values.put(ID_IMAGE, idImage)
        values.put(COL_LINK_IMAGE, linkImage)
        values.put(ID_EVENT, idEvent)

        databaseSQL.insert(TABLE_IMAGES, null, values)
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
        var long: String

        if(cursor.moveToFirst()){
            do {
                title = cursor.getString(cursor.getColumnIndex(COL_TITLE))
                address = cursor.getString(cursor.getColumnIndex(COL_ADDRESS))
                description = cursor.getString(cursor.getColumnIndex(COL_DESCRIPTION))
                organization = cursor.getString(cursor.getColumnIndex(COL_ORGANIZATION))
                link = cursor.getString(cursor.getColumnIndex(COL_LINK))
                startDate = cursor.getString(cursor.getColumnIndex(COL_START_DATE))
                endDate = cursor.getString(cursor.getColumnIndex(COL_END_DATE))
                publicationDate = cursor.getString(cursor.getColumnIndex(COL_PUBLICATION_DATE))
                time = cursor.getString(cursor.getColumnIndex(COL_TIME))
                lat = cursor.getString(cursor.getColumnIndex(COL_LAT))
                long = cursor.getString(cursor.getColumnIndex(COL_LOG))

                listEventSQL.add(Event(
                    title = title,
                    address = address,
                    description = description,
                    organization = organization,
                    link = link,
                    startDate = startDate,
                    endDate = endDate,
                    publicationDate = publicationDate,
                    time = time,
                    lat = lat,
                    long = long
                ))
            }while (cursor.moveToNext())
        }
     return listEventSQL
    }

    @SuppressLint("Recycle", "Range")
     fun getImagesDb(idEventSearch: String): MutableList<ImageModelDB>{


        val listImageSQL: MutableList<ImageModelDB> = mutableListOf()

        val selectQuery = "SELECT * FROM $TABLE_IMAGES WHERE $ID_EVENT_FK = '$idEventSearch'"
        val dataBaseSQL = this.readableDatabase

        var cursor: Cursor? = null

        try {
            cursor = dataBaseSQL.rawQuery(selectQuery, null)
        }catch (_: SQLiteException){
            dataBaseSQL.execSQL(selectQuery)
            return ArrayList()
        }
        var idImage: String
        var linkImage: String
        var idEvent: String

        if(cursor.moveToFirst()){
            do {
                idImage = cursor.getString(cursor.getColumnIndex(ID_IMAGE))
                linkImage = cursor.getString(cursor.getColumnIndex(COL_LINK_IMAGE))
                idEvent = cursor.getString(cursor.getColumnIndex(ID_EVENT))

                listImageSQL.add(
                    ImageModelDB(
                        idImage = idImage,
                        linkImage = linkImage,
                        idEvent = idEvent
                    )
                )
            }while (cursor.moveToNext())
        }
        return listImageSQL
    }
}