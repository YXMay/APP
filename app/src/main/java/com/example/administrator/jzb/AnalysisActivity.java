package com.example.administrator.jzb;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class AnalysisActivity extends Activity {
    private List<ConstBean> mList;

    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.analysis_view);
        mList= (List<ConstBean>) getIntent().getSerializableExtra("cost_list");

        int sum = 0;
        int max = 0;
        String mdate = null;
        for (int i=0;i<mList.size();i++){
            ConstBean b=mList.get(i);
            String costData=b.constDate;
            int costMoney= Integer.parseInt(b.constMoney);
            sum = sum + costMoney;
            if(costMoney > max){
                max = costMoney;
                mdate = costData;
            }
        }

        TextView tv1=(TextView)findViewById(R.id.marks1);
        TextView tv2=(TextView)findViewById(R.id.marks2);
        TextView tv3=(TextView)findViewById(R.id.marks3);
        ImageView image = (ImageView)findViewById(R.id.image);

        if (sum >= 1000){
            tv1.setText("新成就：独臂大侠\n本月你一共消费"+sum+"人民币");
            tv2.setText(" 在"+mdate+"那一天，不知道你是出于快乐还是为了消愁，竟创下本月最高金额——"+max);
            tv3.setText("乖，咱不买了。");
            image.setImageResource(R.drawable.loser);

        }else if (sum > 0){
            tv1.setText("新成就：千手观音,\n本月你一共消费"+sum+"人民币");
            tv2.setText(" 在"+mdate+"那一天，不知道你是出于快乐还是为了消愁，竟创下本月最高金额——"+max);
            tv3.setText("亲，请对自己好一点。");
            image.setImageResource(R.drawable.rich);
        }
        else {
            tv1.setText("新成就：铁公鸡,\n本月你竟然一毛不拔");
            tv2.setText("对不起，千手观音记账本在你面前毫无价值，卸载吧，嘤嘤嘤~~~");
            tv3.setText("这么省钱一定没有对象吧，\n请前往应用商店下载My love story");
            image.setImageResource(R.drawable.no);
        }
    }
}
