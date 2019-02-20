package com.rg17.credittransfer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Win8 on 4/20/2018.
 */

public class DatabaseAdapter
{
    DatabaseHelper helper;

    public DatabaseAdapter(Context context)
    {
        helper = new DatabaseHelper(context);
    }

    public long insertInitialData(int userid, String firstName, String lastName, String email, int currentCredit)
    {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.COL_11,userid);
        contentValues.put(DatabaseHelper.COL_12,firstName);
        contentValues.put(DatabaseHelper.COL_13,lastName);
        contentValues.put(DatabaseHelper.COL_14,email);
        contentValues.put(DatabaseHelper.COL_15,currentCredit);
        long id = db.insert(DatabaseHelper.TABLE1_NAME,null,contentValues);
        db.close();
        return  id;
    }

    public Cursor getInitialDetails()
    {
        SQLiteDatabase db = helper.getWritableDatabase();
        String[] columns = {DatabaseHelper.COL_12, DatabaseHelper.COL_13, DatabaseHelper.COL_15};
        Cursor cursor = db.query(DatabaseHelper.TABLE1_NAME,columns,null,null,null,null,null);
        return cursor;
    }

    public Cursor getDetails(String fName, String lName)
    {
        SQLiteDatabase db = helper.getWritableDatabase();
        String[] columns = {DatabaseHelper.COL_11, DatabaseHelper.COL_12, DatabaseHelper.COL_13, DatabaseHelper.COL_14, DatabaseHelper.COL_15};
        String whereclause = DatabaseHelper.COL_12+"=? AND "+ DatabaseHelper.COL_13+"=?";
        String[] whereargs = new String[]{fName,lName};
        Cursor cursor = db.query(DatabaseHelper.TABLE1_NAME,columns,whereclause,whereargs,null,null,null);
        return  cursor;
    }

    public ArrayList<String> getNames(String firstName, String lastName)
    {
        SQLiteDatabase db = helper.getWritableDatabase();
        ArrayList<String> names = new ArrayList<String>();
        String[] columns = {DatabaseHelper.COL_12, DatabaseHelper.COL_13};
        Cursor cursor = db.query(DatabaseHelper.TABLE1_NAME,columns,null,null,null,null,null);
        String fName,lName;
        if(cursor.getCount() > 0)
        {
            while(cursor.moveToNext())
            {
                fName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_12));
                lName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_13));
                if(!fName.equals(firstName) && !lName.equals(lastName))
                {
                    String fullName = fName+" "+lName;
                    names.add(fullName);
                }
            }
        }
        return  names;
    }

    public int getCurrentCredit(String fName, String lName)
    {
        SQLiteDatabase db = helper.getWritableDatabase();
        String[] columns = {DatabaseHelper.COL_15};
        String selection = DatabaseHelper.COL_12+"=? AND "+ DatabaseHelper.COL_13+"=?";
        String[] selectionArgs = {fName,lName};
        Cursor cursor = db.query(DatabaseHelper.TABLE1_NAME,columns,selection,selectionArgs,null,null,null);
        int count = 0;
        int currentCredit = 0;
        while (cursor.moveToNext() && count != 1)
        {
            currentCredit = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COL_15));
            count++;
        }
        return  currentCredit;
    }

    public void updateCredit(int credit, String fName, String lName)
    {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.COL_15,credit);
        String whereClause = DatabaseHelper.COL_12+"=? AND "+ DatabaseHelper.COL_13+"=?";
        String[] whereArgs = {fName,lName};
        db.update(DatabaseHelper.TABLE1_NAME,contentValues,whereClause,whereArgs);
    }

    static  class DatabaseHelper extends SQLiteOpenHelper
    {
        public    static final String DATABASE_NAME = "CT";
        public    static final String TABLE1_NAME = "User";

        public    static final String COL_11 = "User_Id";
        public    static final String COL_12 = "First_Name";
        public    static final String COL_13 = "Last_Name";
        public    static final String COL_14 = "Email";
        public    static final String COL_15 = "Current_Credit";

        public    static  int version = 8;
        public static Context context;

        public DatabaseHelper(Context context)
        {
            super(context, DATABASE_NAME, null, 1);
            this.context = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
            db.execSQL("create table User (User_Id INTEGER PRIMARY KEY, First_Name TEXT, Last_Name TEXT, Email TEXT, Current_Credit INTEGER);");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            db.execSQL("DROP TABLE IF EXISTS User;");
            onCreate(db);
        }

    }


}
