package com.example.todoist;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteOpenHelper {
    public static final String DATABASE_TABLE = "plan.db";
    public static final String TABLE_NAME = "kehoach";
    public Database(@Nullable Context context) {
        super(context, DATABASE_TABLE, null, 1);
    }

    private String SQLQuery1 = "INSERT INTO kehoach VAlUES (null, 'Nguyễn Văn Chính', 'dsggadfhadh', '20/4/2022')";


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE " + TABLE_NAME + " ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, " +
                "content TEXT, " +
                "date TEXT" + ")";
        sqLiteDatabase.execSQL(query);
        sqLiteDatabase.execSQL(SQLQuery1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public List<Plan> getAllPlan(){
        List<Plan> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * from kehoach", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            int idplan = cursor.getInt(0);
            String nameplan = cursor.getString(1);
            String contentplan = cursor.getString(2);
            String dateplan = cursor.getString(3);
            list.add(new Plan(idplan, nameplan, contentplan, dateplan));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public Plan getPlanId(int ID) {
        Plan plan = null;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * from kehoach where id = ?",
                new String[]{ID + ""});

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            int planID = cursor.getInt(0);
            String planName = cursor.getString(1);
            String planContent = cursor.getString(2);
            String planDate = cursor.getString(3);
            plan = new Plan(planID, planName, planContent, planDate);
        }
        cursor.close();
        return plan;
    }

    void updatePlan(Plan plan) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE kehoach SET name=?, content = ?, date = ? where id = ?",
                new String[]{plan.getName(), plan.getContent(), plan.getDate() + "", plan.getId() + ""});
    }

    void insertPlan(Plan plan) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO kehoach (name, content, date) VALUES (?,?,?)",
                new String[]{plan.getName(), plan.getContent(), plan.getDate() + ""});
    }

    void deletePlanByID(int PlanID) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM kehoach where id = ?", new String[]{String.valueOf(PlanID)});
    }
}
