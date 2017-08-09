package com.cac.dad.paypaper;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static android.R.attr.data;
import static android.R.id.list;
import static com.cac.dad.paypaper.R.id.fab;

public class MainActivity extends AppCompatActivity {
    ListView costlist;
    List<CostBean> list;
    private DBhelper mdatabaseHelper;
    String new_title;
    String new_money;
    String new_date;
    private costListAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        mdatabaseHelper = new DBhelper(this);
        list = new ArrayList<CostBean>();
        costlist = (ListView) findViewById(R.id.listview);
        adapter = new costListAdapter(this, display());
        costlist.setAdapter(adapter);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
                View new_view = inflater.inflate(R.layout.new_cost, null);
                final EditText title = (EditText) new_view.findViewById(R.id.et_cost_title);
                final EditText money = (EditText) new_view.findViewById(R.id.et_cost_money);
                final DatePicker date = (DatePicker) new_view.findViewById(R.id.dp_cost_date);
                builder.setView(new_view);
                builder.setTitle("new cost");
                builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        new_title = "" + title.getText();
                        new_money = "" + money.getText();
                        new_date = "" + date.getYear() + "-" + (date.getMonth() + 1) + "-" + date.getDayOfMonth();

                        insert(new_title, new_money, new_date);
                        adapter.notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("cancel", null);
                builder.create().show();
            }
        });
    }


    private void insert(String new_title, String new_money, String new_date) {
       
        if((!new_title.equals(""))&&(!new_money.equals(""))) {
            CostBean bean = new CostBean();
            bean.costTitle = new_title;
            bean.costMoney = new_money;
            bean.costData = new_date;
            mdatabaseHelper.insertCost(bean);
            list.add(bean);
        }

    }


    private List<CostBean> display() {

        Cursor c = mdatabaseHelper.getAllcostData();
        if (c != null) {
            while (c.moveToNext()) {
                CostBean costbean = new CostBean();
                costbean.costTitle = c.getString(c.getColumnIndex("cost_title"));
                costbean.costData = c.getString(c.getColumnIndex("cost_date"));
                costbean.costMoney = c.getString(c.getColumnIndex("cost_money"));
                list.add(costbean);
            }
            c.close();
        }
        return list;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent intent=new Intent(MainActivity.this,ChartActivity.class);
            intent.putExtra("cost_list",(Serializable) list);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
