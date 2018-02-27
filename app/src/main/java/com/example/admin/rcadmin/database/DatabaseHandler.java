package com.example.admin.rcadmin.database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.admin.rcadmin.locality.database.CityTable;
import com.example.admin.rcadmin.locality.database.StateTable;
import com.example.admin.rcadmin.locality.database.VillageTable;

public class DatabaseHandler extends SQLiteOpenHelper {

Class a;
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
        Context context;
    // Database Name
    public static final String DATABASE_NAME = "rural_caravane";

    // Contacts table name

    public DatabaseHandler(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(CityTable.CREATE_CITY_TABLE);
        db.execSQL(VillageTable.CREATE_VILLAGE__TABLE);
        db.execSQL(StateTable.CREATE_STATE__TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed


        db.execSQL("DROP TABLE IF EXISTS "+ CityTable.CITY_TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+ VillageTable.VILLAGE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+ StateTable.STATE_TABLE);

        onCreate(db);
    }

}