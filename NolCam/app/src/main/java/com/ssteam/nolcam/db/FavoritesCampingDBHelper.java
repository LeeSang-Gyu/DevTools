package com.ssteam.nolcam.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.ssteam.nolcam.generic.Camping;
import com.ssteam.nolcam.util.CalendarEngine;

import java.util.ArrayList;

public class FavoritesCampingDBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "FavoritesDB";
    private static int DB_VERSION = 1;

    private String TABLE_NAME = "FAVORITES_CAMPING_TB";

    private String COLUMN_id = "id";
    private String COLUMN_campid = "campid";
    private String COLUMN_facltNm = "facltNm";
    private String COLUMN_firstImageURL = "firstImageURL";
    private String COLUMN_addr1 = "addr1";
    private String COLUMN_addr2 = "addr2";
    private String COLUMN_induty = "induty";
    private String COLUMN_lineIntro = "lineIntro";
    private String COLUMN_favorites = "favorites";

    private SQLiteDatabase db;


    public FavoritesCampingDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createSql = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s INTEGER);",
                TABLE_NAME, COLUMN_id, COLUMN_campid, COLUMN_facltNm, COLUMN_addr1, COLUMN_addr2, COLUMN_firstImageURL, COLUMN_induty, COLUMN_lineIntro, COLUMN_favorites);

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
        openDB();

        // 혹시모르니 중복체크 한가지 해주면 좋겠다.

        StringBuffer insertSql = new StringBuffer();
        insertSql.append(String.format("INSERT INTO %s VALUES(", TABLE_NAME));
        insertSql.append("null, ");
        insertSql.append(String.format("'%s', ", camping.id));
        insertSql.append(String.format("'%s', ", camping.facltNm));
        insertSql.append(String.format("'%s', ", camping.addr1));
        insertSql.append(String.format("'%s', ", camping.addr2));
        insertSql.append(String.format("'%s', ", camping.firstImageURL));
        insertSql.append(String.format("'%s',", camping.induty));
        insertSql.append(String.format("'%s',", camping.lineIntro));
        insertSql.append(String.format("%d", camping.favorites));
        insertSql.append(");");


        db.execSQL(insertSql.toString());

        closeDB();
    }

    public void update(Camping camping) {
        openDB();

        StringBuffer insertSql = new StringBuffer();
        insertSql.append(String.format("UPDATE %s SET ", TABLE_NAME));
        insertSql.append(String.format("%s = '%s', ", COLUMN_facltNm, camping.facltNm));
        insertSql.append(String.format("%s = '%s', ", COLUMN_addr1, camping.addr1));
        insertSql.append(String.format("%s = '%s', ", COLUMN_addr2, camping.addr2));
        insertSql.append(String.format("%s = '%s', ", COLUMN_firstImageURL, camping.firstImageURL));
        insertSql.append(String.format("%s = '%s'", COLUMN_induty, camping.induty));
        insertSql.append(String.format("%s = '%s'", COLUMN_lineIntro, camping.lineIntro));
        insertSql.append(String.format("%s = %d ", COLUMN_favorites, camping.favorites));
        insertSql.append(String.format("WHERE %s = '%s';", COLUMN_campid, camping.id));


        db.execSQL(insertSql.toString());

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
            String induty = c.getString(6);
            String lineIntro = c.getString(7);
            int favorites = c.getInt(8);


            arrayList.add(new Camping(id, campID, name, addr1, addr2, firstImageURL, induty, lineIntro, favorites));
        }

        closeDB();

        return arrayList;
    }

    public boolean getFavritesExist(String id) {
        boolean isExist = false;

        openDB();

        String selectSql = String.format("SELECT * FROM %s WHERE %s = '%s';", TABLE_NAME, COLUMN_campid, id);
        Cursor c = db.rawQuery(selectSql, null);

        while (c.moveToNext()) {
            isExist = true;
        }


        return isExist;
    }

    public void delete(String id) {
        openDB();
        String deleteSql = String.format("DELETE FROM %s WHERE %s = '%s';", TABLE_NAME, COLUMN_campid, id);
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