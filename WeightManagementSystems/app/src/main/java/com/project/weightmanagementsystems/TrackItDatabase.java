package com.project.weightmanagementsystems;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class TrackItDatabase extends SQLiteOpenHelper {

    private static final int VERSION = 4;
    private static final String DATABASE_NAME = "Track.db";
    private static TrackItDatabase mTrackDB;

    public static enum WeightSort {UPDATE_DESC, UPDATE_ASC };

    static TrackItDatabase getInstance(Context context){
        if(mTrackDB == null){
            mTrackDB = new TrackItDatabase(context);
        }
        return mTrackDB;
    }
    private TrackItDatabase (Context context){
        super (context, DATABASE_NAME, null, VERSION);
    }

    private static final class UserTable {
        private static final String TABLE = "users";
        private static final String COL_ID = "_id";
        private static final String COL_USER = "username";
        private static final String COL_PW = "password"; //Not worried about encryption at this time.
    }

    private static final class WeightTable {
        private static final String TABLE = "tracked_weights";
        private static final String COL_ID = "_id";
        private static final String COL_UID = "uid";
        private static final String COL_WEIGHT = "weight";
        private static final String COL_DATE = "add_date";
        private static final String COL_UNIT = "unit";
    }

    private static final class GoalsTable {
        private static final String TABLE = "tracked_goals";
        private static final String COL_ID = "_id";
        private static final String COL_UID = "uid";
        private static final String COL_WEIGHT = "goal_weight";
        private static final String COL_DATE = "goal_date";
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + UserTable.TABLE + " (" +
                UserTable.COL_ID + " integer primary key autoincrement," +
                UserTable.COL_USER + " text unique, " +
                UserTable.COL_PW + " text)");
        db.execSQL("create table " + WeightTable.TABLE + " (" +
                WeightTable.COL_ID + " integer primary key autoincrement," +
                WeightTable.COL_UID + " integer, " +
                WeightTable.COL_WEIGHT + " text, " +
                WeightTable.COL_DATE + " text, " +
                WeightTable.COL_UNIT + " text, " +
                " FOREIGN KEY (" + WeightTable.COL_UID +" ) REFERENCES " + UserTable.TABLE + "(" + UserTable.COL_ID + "));");
        db.execSQL("create table " + GoalsTable.TABLE + " (" +
                GoalsTable.COL_ID + " integer primary key autoincrement," +
                GoalsTable.COL_UID + " integer, " +
                GoalsTable.COL_WEIGHT + " text, " +
                GoalsTable.COL_DATE + " text, " +
                " FOREIGN KEY (" + GoalsTable.COL_UID +" ) REFERENCES " + UserTable.TABLE + "(" + UserTable.COL_ID + "));");
        Log.e(TAG, "DB CREATED");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("drop table if exists " + GoalsTable.TABLE);
        db.execSQL("drop table if exists " + WeightTable.TABLE);
        db.execSQL("drop table if exists " + UserTable.TABLE);
        onCreate(db);
    }

    public boolean getUserExist(User user){
        SQLiteDatabase db = this.getReadableDatabase();

        String sql = "select * from " + UserTable.TABLE +
                " where " + UserTable.COL_USER + " = ?";
        Cursor cursor = db.rawQuery(sql, new String[] {user.getUser()});
        Log.e(TAG,"Password returned: " + user.getPassword());
        Log.e(TAG,"User returned: " + user.getUser());
        if(cursor.moveToFirst()){
            Log.e(TAG,"Password returned: " + user.getPassword());
            return cursor.getString(2).equals(user.getPassword());
        }
        else
            return false;
    }

    public User getUser(User user){
        SQLiteDatabase db = this.getReadableDatabase();

        String sql = "select * from " + UserTable.TABLE +
                " where " + UserTable.COL_USER + " = ?";
        Cursor cursor = db.rawQuery(sql, new String[] {user.getUser()});
        if(cursor.moveToFirst()){
            user.setId(cursor.getLong(0));
            cursor.close();
            return user;
        }
        else{
            cursor.close();
            return user;
        }
    }

    public void addUser(User user){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        if(!getUserExist(user)) {
            values.put(UserTable.COL_USER, user.getUser());
            values.put(UserTable.COL_PW, user.getPassword());
            long userId = db.insert(UserTable.TABLE, null, values);
            user.setId(userId);
        }
    }

    public void addGoal(User user){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        if(!getGoalExist(user)) {
            values.put(GoalsTable.COL_UID, user.getId());
            values.put(GoalsTable.COL_WEIGHT, user.getGoals().getGoal());
            values.put(GoalsTable.COL_DATE, user.getGoals().getDate());
            long goalId = db.insert(GoalsTable.TABLE, null, values);
            user.getGoals().setId(goalId);
        }
    }

    public boolean getGoalExist(User user){
        SQLiteDatabase db = this.getReadableDatabase();

        String sql = "select * from " + GoalsTable.TABLE +
                " where " + GoalsTable.COL_UID + " = ?";
        Cursor cursor = db.rawQuery(sql, new String[] {user.getUser()});
        if(cursor.moveToFirst())
            return cursor.getString(1).equals(user.getUser());
        else
            return false;
    }

    public void addWeight(User user, Weight weight){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        if(!false) {
            values.put(WeightTable.COL_UID, user.getId());
            values.put(WeightTable.COL_WEIGHT, weight.getWeight());
            values.put(WeightTable.COL_DATE, weight.getDate());
            values.put(WeightTable.COL_UNIT, weight.getUnits());
            long weightId = db.insert(WeightTable.TABLE, null, values);
            weight.setId(weightId);
        }
    }

    public List<Weight> getWeights(WeightSort order, String month, String year, User user){
        List<Weight> weights = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        String orderBy;
        switch (order){
            case UPDATE_DESC:
                orderBy = WeightTable.COL_DATE + " desc";
                break;
            default:
                orderBy = WeightTable.COL_DATE + " asc";
                break;
        }

        String sql = "select * from " + WeightTable.TABLE + " where " + WeightTable.COL_DATE + " like \'" + year + "-" + month + "-%\' AND "+ WeightTable.COL_UID + " = "+ user.getId() +" order by " + orderBy;
        Log.e("SQL", "SQL QUERY: " + sql);
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst()){
            do{
                Weight weight = new Weight();
                weight.setId(cursor.getLong(0));
                weight.setUid(cursor.getLong(1));
                weight.setWeight(cursor.getString(2));
                weight.setDate(cursor.getString(3));
                weight.setUnits(cursor.getString(4));
                weights.add(weight);
            }while(cursor.moveToNext());
        }

        cursor.close();

        return weights;
    }
    //Un-used Currently, for future development
    public void updateGoal(Goals goal){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(GoalsTable.COL_ID, goal.getId());
        values.put(GoalsTable.COL_UID, goal.getUid());
        values.put(GoalsTable.COL_WEIGHT, goal.getGoal());
        values.put(GoalsTable.COL_DATE, goal.getDate());
        db.update(GoalsTable.TABLE, values, GoalsTable.COL_ID + " = " + goal.getId(), null);
    }

    public void updateWeight(Weight weight){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(WeightTable.COL_ID, weight.getId());
        values.put(WeightTable.COL_UID, weight.getUid());
        values.put(WeightTable.COL_WEIGHT, weight.getWeight());
        values.put(WeightTable.COL_DATE, weight.getDate());
        values.put(WeightTable.COL_UNIT, weight.getUnits());
        db.update(WeightTable.TABLE, values, WeightTable.COL_ID + " = " + weight.getId(), null);
    }
    public Weight getWeight(User user, Weight weight){
        SQLiteDatabase db = this.getReadableDatabase();

        String sql = "select * from " + WeightTable.TABLE +
                " where " + WeightTable.COL_DATE + " = ? AND " + WeightTable.COL_UID + " = ?";
        Cursor cursor = db.rawQuery(sql, new String[] {weight.getDate(), String.valueOf(user.getId())});
        if(cursor.moveToFirst()){
            weight.setId(cursor.getLong(0));
            cursor.close();
            return weight;
        }
        else{
            cursor.close();
            return weight;
        }
    }
    public void deleteGoal(long goalId) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(GoalsTable.TABLE,
                GoalsTable.COL_ID + " = " + goalId, null);
    }

    public void deleteWeight(long weightId) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(WeightTable.TABLE,
                WeightTable.COL_ID + " = " + weightId, null);
    }

}























