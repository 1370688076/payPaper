package com.cac.dad.paypaper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.R.attr.id;
import static android.R.attr.key;
import static android.R.attr.version;
import static android.R.id.primary;

/**
 * Created by Dad on 2017/8/9.
 */

public class DBhelper extends SQLiteOpenHelper {


    public static final String IMOOC_COST = "imooc_cost";
    public static final String COST_TITLE = "cost_title";
    public static final String COST_DATE = "cost_date";
    public static final String COST_MONEY = "cost_money";

    public DBhelper(Context context) {
        super(context, "paper", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {    //初始化
        sqLiteDatabase.execSQL("create table if not exists imooc_cost(id integer primary key,cost_title varchar,cost_date varchar,cost_money varchar)");
    }


    public void insertCost(CostBean costBean) {        //插入
        SQLiteDatabase database = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COST_TITLE, costBean.costTitle);
        contentValues.put(COST_DATE, costBean.costData);
        contentValues.put(COST_MONEY, costBean.costMoney);
        database.insert(IMOOC_COST, null, contentValues);
    }


    public void deleteAllData()                   //删除
    {
        SQLiteDatabase database = getWritableDatabase();
        database.delete(IMOOC_COST,null,null);


    }

    public Cursor getAllcostData()                 //查找
    {
        SQLiteDatabase database=getWritableDatabase();
        return database.query(IMOOC_COST,null,null,null,null,null,"id" + " ASC");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
