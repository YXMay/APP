package com.example.administrator.jzb;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import lecho.lib.hellocharts.gesture.ContainerScrollType;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.LineChartView;


public class ChartsActivity extends Activity {

    private LineChartView mchart;
    private Map<String,Integer> table=new TreeMap<>();
    private LineChartData mData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chart_view);
        mchart=(LineChartView)findViewById(R.id.chart);

        List<ConstBean> alldata= (List<ConstBean>) getIntent().getSerializableExtra("cost_list");

        generateValues(alldata);
        generateData();
    }

    private void generateData() {
        List<Line> lines = new ArrayList<Line>();
        List<PointValue> values = new ArrayList<PointValue>();

        List<AxisValue> mAxisValues = new ArrayList<AxisValue>();  //x轴

        int i = 0;
        for (String date:table.keySet()
                ){
            mAxisValues.add(new AxisValue(i).setLabel(date+""));
            i++;
        }

        int index=0;
        for (Integer value:table.values()
                ) {
            values.add(new PointValue(index,value));
           // mAxisValues.add(new AxisValue(index).setLabel(index+""));  //x轴加一
            index++;

        }

        Line line=new Line(values);
        //设置线条的颜色
        line.setColor(ChartUtils.COLORS[0]);
        //设置点的形状
        line.setShape(ValueShape.CIRCLE);
        //设置点的颜色
        line.setPointColor(ChartUtils.COLORS[1]);

        line.setHasLabels(true);//曲线的数据坐标是否加上备注
        line.setFilled(true);//是否填充曲线的面积

        LineChartData data = new LineChartData();

        Axis axisX = new Axis(); //X轴//
        axisX.setHasTiltedLabels(true);
        axisX.setTextColor(Color.BLUE);
        axisX.setName("消费日期");
        axisX.setMaxLabelChars(10);
        axisX.setValues(mAxisValues);


        Axis axisY = new Axis(); //y轴
        axisY.setName("消费金额");
        axisY.setTextColor(Color.BLUE);

        //添加
        lines.add(line);

        mData=new LineChartData(lines);

        mData.setAxisXBottom(axisX);
        mData.setAxisYLeft(axisY);
        mchart.setLineChartData(mData);

    }

    private void generateValues(List<ConstBean> alldata) {
        if (alldata!=null){
            for (int i = 0; i <alldata.size() ; i++) {
                ConstBean b=alldata.get(i);
                String costData=b.constDate;
                int costMoney= Integer.parseInt(b.constMoney);

                if(!table.containsKey(costData)){
                    table.put(costData,costMoney);
                }
                else{
                    table.put(costData,costMoney+( table.get(costData)));
                }

            }
        }

    }
}
