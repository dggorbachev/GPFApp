package com.singlelab.gpf.interactive_games.piano_tiles;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.singlelab.gpf.interactive_games.piano_tiles.model.PianoGameScore;

import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "PianoTiles.sqlite";
    private static final String TABLE_SCORE = "score";

    private static final String KEY_ID = "id";
    private static final String KEY_SCORE = "score";
    private static final String KEY_NAME = "name";
    private static final String KEY_DATETIME = "datetime";

    public DBHandler(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_FOODS_TABLE = "CREATE TABLE " + TABLE_SCORE + "(" + KEY_ID + " INTEGER PRIMARY KEY, "
                + KEY_SCORE + " INTEGER," + KEY_NAME + " TEXT," + KEY_DATETIME + " TEXT)";

        sqLiteDatabase.execSQL(CREATE_FOODS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_SCORE);
        onCreate(sqLiteDatabase);
    }

    public void addRecord(PianoGameScore item){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_SCORE, item.getScore());
        values.put(KEY_NAME, item.getName());
        values.put(KEY_DATETIME, item.getDatetime());

        db.insert(TABLE_SCORE, null, values);
        db.close();
    }

    public PianoGameScore getScoreWithId(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_SCORE, new String[] { KEY_ID, KEY_SCORE, KEY_NAME, KEY_DATETIME }, KEY_ID + "=?", new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        PianoGameScore pianoGameScore = new PianoGameScore(Integer.parseInt(cursor.getString(0)), Integer.parseInt(cursor.getString(1)), cursor.getString(2), cursor.getString(3));
        return pianoGameScore;
    }

    public List<PianoGameScore> getAllScoresWithName(String name){
        List<PianoGameScore> pianoGameScoreList = new ArrayList<PianoGameScore>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_SCORE + " WHERE name LIKE '%" + name + "%'" ;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                PianoGameScore pianoGameScore = new PianoGameScore();
                pianoGameScore.setId(Integer.parseInt(cursor.getString(0)));
                pianoGameScore.setScore(Integer.parseInt(cursor.getString(1)));
                pianoGameScore.setName(cursor.getString(2));
                pianoGameScore.setDatetime(cursor.getString(3));

                pianoGameScoreList.add(pianoGameScore);
            } while (cursor.moveToNext());
        }

        return pianoGameScoreList;
    }

    public List<PianoGameScore> getHighScore(int limit){
        List<PianoGameScore> pianoGameScoreList = new ArrayList<PianoGameScore>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_SCORE + " ORDER BY " + KEY_SCORE + " DESC " + " LIMIT " + limit ;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                PianoGameScore pianoGameScore = new PianoGameScore();
                pianoGameScore.setId(Integer.parseInt(cursor.getString(0)));
                pianoGameScore.setScore(Integer.parseInt(cursor.getString(1)));
                pianoGameScore.setName(cursor.getString(2));
                pianoGameScore.setDatetime(cursor.getString(3));

                pianoGameScoreList.add(pianoGameScore);
            } while (cursor.moveToNext());
        }

        return pianoGameScoreList;
    }

    public int getScoreCount() {
        String countQuery = "SELECT * FROM " + TABLE_SCORE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

    public void deleteAllScore(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_SCORE, null, null);
        db.close();
    }
}
