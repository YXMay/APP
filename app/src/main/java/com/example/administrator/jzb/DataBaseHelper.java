package com.example.administrator.jzb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DataBaseHelper extends SQLiteOpenHelper {

    //public static final String id ="id";
    public static final String COST_MONEY = "cost_money";
    public static final String COST_DATA = "cost_data";
    public static final String COST_TITLE = "cost_title";
    public static final String IMOOC_COST = "imooc_cost";

    public DataBaseHelper(Context context) {
        super(context,"Imooc_daily", null, 1);
    }

    //创建的方法
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists imooc_cost("+
        "id integer primary key,"+
        "cost_title varchar,"+
        "cost_data varchar,"+
        "cost_money varchar)");
    }


    //插入方法
    public void insert(ConstBean bean){
        SQLiteDatabase db=getWritableDatabase();
        ContentValues cv=new ContentValues();

        //cv.put(id,bean.Id);
        cv.put(COST_TITLE,bean.constTitle);
        cv.put(COST_DATA,bean.constDate);
        cv.put(COST_MONEY,bean.constMoney);

        db.insert(IMOOC_COST,null,cv);
    }

    //查询的方法
    public Cursor getAllCursorData(){
        SQLiteDatabase db=getWritableDatabase();
        //按时间进行排序
        return db.query("imooc_cost",null,null,null,null,null,"cost_data "+"ASC");
    }


    //删除整张表
    public void delete(String title){
        SQLiteDatabase db=getWritableDatabase();
        if (db.isOpen()) {
            String sql = "delete from imooc_cost where cost_title = title";
            db.execSQL(sql);
            db.close();
        }
    }

    //更新的方法
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
