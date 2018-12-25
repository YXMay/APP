package com.example.administrator.jzb;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class MainActivity extends AppCompatActivity {
    private List<ConstBean> mList;
    private ListView constList;
    private DataBaseHelper mDataBaseHelper;
    private ConstListAdapter constListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        //创建数据库
        mDataBaseHelper=new DataBaseHelper(this);
        mList=new ArrayList<>();
        initData();  //将现有数据库存入数组
        constList=(ListView)findViewById(R.id.lv_main);   //content_main
        constListAdapter = new ConstListAdapter(this, mList);  //把mlist放入adapter中
        constList.setAdapter(constListAdapter);  //放到面板上







        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                LayoutInflater inflater=LayoutInflater.from(MainActivity.this);
                View viewDialog=inflater.inflate(R.layout.new_cost_data,null);

                final EditText title=(EditText)viewDialog.findViewById(R.id.et_cost_title);
                final EditText money=(EditText)viewDialog.findViewById(R.id.et_cost_money);
                final DatePicker dp=(DatePicker)viewDialog.findViewById(R.id.dp_cost_date);


                //让它显示出来
                builder.setView(viewDialog);
                //添加标题
                builder.setTitle("New Cost");

                //确认的点击事件
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        ConstBean costbean=new ConstBean();
                        costbean.constTitle=title.getText().toString();
                        costbean.constMoney=money.getText().toString();
                        costbean.constDate=dp.getYear()+"-"+(dp.getMonth()+1)+"-"+dp.getDayOfMonth();

                        //插入
                        mDataBaseHelper.insert(costbean);

                        //虽然已插入数据库，但读取数据库文件是在初始化时读取的，并且只读取一次，所以需要插入到集合中
                        mList.add(costbean);

                        //通知adapter更新数据源
                        constListAdapter.notifyDataSetChanged();



                    }
                });

                //取消的点击事件
                //点击cancel，自动取消，因此不用配置点击事件
                builder.setNegativeButton("Cancel", null);

                //让对话框显示出来

                builder.create().show();


            }
        });

       // constListAdapter.delete(new View.OnClickListener() {
       //     @Override
        //    public void onClick(View v) {

        //    }
     //   });

    }

    private void initData() {      //将数据存入mlist
//           mDataBaseHelper.deleteAllData();
//        for (int i=0;i<6;i++) {
//            ConstBean b=new ConstBean();
//            b.constTitle=i+"Imooc";
//            b.constDate="11-11";
//            b.constMoney="100";
//
//            mDataBaseHelper.insert(b);
//        }

        Cursor c=mDataBaseHelper.getAllCursorData();    //查询数据库
        if(c!=null){
            while (c.moveToNext()){                      //数据放入数组
                ConstBean costbean=new ConstBean();
                costbean.constTitle=c.getString(c.getColumnIndex("cost_title"));
                costbean.constDate=c.getString(c.getColumnIndex("cost_data"));
                costbean.constMoney=c.getString(c.getColumnIndex("cost_money"));
                mList.add(costbean);
            }
            c.close();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_charts) {
            Intent intent=new Intent(this,ChartsActivity.class);
            intent.putExtra("cost_list", (Serializable) mList);
            startActivity(intent);
           // return true;
        }else {
            Intent intent=new Intent(this,AnalysisActivity.class);
            intent.putExtra("cost_list", (Serializable) mList);
            startActivity(intent);

        }
        return super.onOptionsItemSelected(item);
    }


}
