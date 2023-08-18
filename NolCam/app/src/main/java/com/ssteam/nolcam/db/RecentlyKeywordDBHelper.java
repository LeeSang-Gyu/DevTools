package com.ssteam.nolcam.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ssteam.nolcam.generic.Camping;
import com.ssteam.nolcam.generic.Keyword;

import java.util.ArrayList;

public class RecentlyKeywordDBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "KeywordDB";
    private static int DB_VERSION = 1;

    private String TABLE_NAME = "RECENTLY_KEYWORD_TB";

    private String COLUMN_id = "id";
    private String COLUMN_keyword = "keyword";

    private SQLiteDatabase db;


    public RecentlyKeywordDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createSql = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT);",
                TABLE_NAME, COLUMN_id, COLUMN_keyword);

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

    public void insert(String keyword) {
        boolean isOverlap = getKeywordOverlap(keyword);
        if (!isOverlap) {
            openDB();

            StringBuffer insertSql = new StringBuffer();
            insertSql.append(String.format("INSERT INTO %s VALUES(", TABLE_NAME));
            insertSql.append("null, ");
            insertSql.append(String.format("'%s'", keyword));
            insertSql.append(");");

            db.execSQL(insertSql.toString());

            closeDB();
        }
    }

    public ArrayList select() {
        openDB();

        ArrayList<Keyword> arrayList = new ArrayList<>();
        String selectSql = String.format("SELECT * FROM %s ORDER BY %s DESC;", TABLE_NAME, COLUMN_id);
        Cursor c = db.rawQuery(selectSql, null);

        while (c.moveToNext()) {
            int id = c.getInt(0);
            String keyword = c.getString(1);

            arrayList.add(new Keyword(id, keyword));
        }
        closeDB();

        return arrayList;
    }

    public boolean getKeywordOverlap(String keyword) {
        boolean isOverlap = false;

        openDB();

        String selectSql = String.format("SELECT * FROM %s WHERE %s = '%s';", TABLE_NAME, COLUMN_keyword, keyword);
        Cursor c = db.rawQuery(selectSql, null);

        while (c.moveToNext()) {
            isOverlap = true;
        }
        closeDB();

        return isOverlap;
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
