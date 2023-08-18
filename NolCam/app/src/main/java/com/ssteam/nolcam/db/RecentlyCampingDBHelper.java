package com.ssteam.nolcam.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.google.gson.annotations.SerializedName;
import com.ssteam.nolcam.generic.Camping;
import com.ssteam.nolcam.util.CalendarEngine;

import java.util.ArrayList;

public class RecentlyCampingDBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "CampingDB";
    private static int DB_VERSION = 1;

    private String TABLE_NAME = "RECENTLY_CAMPING_TB";

    private String COLUMN_id = "id";
    private String COLUMN_campid = "campid";
    private String COLUMN_facltNm = "facltNm";
    private String COLUMN_firstImageURL = "firstImageURL";
    private String COLUMN_addr1 = "addr1";
    private String COLUMN_addr2 = "addr2";
    private String COLUMN_date = "date";

    private SQLiteDatabase db;


    public RecentlyCampingDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createSql = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT);",
                TABLE_NAME, COLUMN_id, COLUMN_campid, COLUMN_facltNm, COLUMN_addr1, COLUMN_addr2, COLUMN_firstImageURL, COLUMN_date);

        db.execSQL(createSql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void openDB() {
        db = getWritableDatabase();
    }

    public void closeDB() {
        db.close();
    }

    public void insert(Camping camping) {
        CalendarEngine calendarEngine = new CalendarEngine();
        camping.date = calendarEngine.getDate();

        openDB();

        // 중복체크 할 것.
        boolean isOverlap = overlapSelect(camping.id);
        if (isOverlap) {
            // auto_increment의 마지막 값을 찾는다.
            int max = getAutoIncrementMaxSelect();

            StringBuffer updateSql = new StringBuffer();
            updateSql.append(String.format("UPDATE %s SET ", TABLE_NAME));
            updateSql.append(String.format("%s = %s, ", COLUMN_id, (max + 1)));
            updateSql.append(String.format("%s = %s ", COLUMN_date, camping.date));
            updateSql.append(String.format("WHERE %s = %s;", COLUMN_campid, camping.id));
            db.execSQL(updateSql.toString());

            String autoIncrementUpdateSql = String.format("UPDATE SQLITE_SEQUENCE SET SEQ = %d;", (max + 1));
            db.execSQL(autoIncrementUpdateSql);
        } else {
            StringBuffer insertSql = new StringBuffer();
            insertSql.append(String.format("INSERT INTO %s VALUES(", TABLE_NAME));
            insertSql.append("null, ");
            insertSql.append(String.format("'%s', ", camping.id));
            insertSql.append(String.format("'%s', ", camping.facltNm));
            insertSql.append(String.format("'%s', ", camping.addr1));
            insertSql.append(String.format("'%s', ", camping.addr2));
            insertSql.append(String.format("'%s', ", camping.firstImageURL));
            insertSql.append(String.format("'%s'", camping.date));
            insertSql.append(");");

            db.execSQL(insertSql.toString());
        }

        closeDB();
    }

    public ArrayList select() {
        openDB();

        ArrayList<Camping> arrayList = new ArrayList<>();
        String selectSql = String.format("SELECT * FROM %s ORDER BY %s DESC;", TABLE_NAME, COLUMN_id);
        Cursor c = db.rawQuery(selectSql, null);

        while (c.moveToNext()) {
            int id = c.getInt(0);
            String campID = c.getString(1);
            String name = c.getString(2);
            String addr1 = c.getString(3);
            String addr2 = c.getString(4);
            String firstImageURL = c.getString(5);
            String date = c.getString(6);

            arrayList.add(new Camping(id, campID, name, addr1, addr2, firstImageURL, date));
        }

        closeDB();

        return arrayList;
    }

    public boolean overlapSelect(String campID) {
        boolean isOverlap = false;

        String selectSql = String.format("SELECT * FROM %s WHERE %s = %s;", TABLE_NAME, COLUMN_campid, campID);
        Cursor c = db.rawQuery(selectSql, null);

        while (c.moveToNext()) {
            isOverlap = true;
        }

        return isOverlap;
    }

    public int getAutoIncrementMaxSelect() {
        int max = 0;

        String selectSql = String.format("SELECT SEQ FROM SQLITE_SEQUENCE WHERE NAME='%s'", TABLE_NAME);
        Cursor c = db.rawQuery(selectSql, null);

        while (c.moveToNext()) {
            max = c.getInt(0);

        }

        return max;
    }

    public void delete(int id) {
        openDB();
        String deleteSql = String.format("DELETE FROM %s WHERE %s = %d;", TABLE_NAME, COLUMN_id, id);
        db.execSQL(deleteSql);

        closeDB();
    }

    public void deleteAll() {
        openDB();

        String deleteSql = String.format("DELETE FROM %s;", TABLE_NAME);
        db.execSQL(deleteSql);

        closeDB();
    }
}