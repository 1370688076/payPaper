package com.cac.dad.paypaper;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import lecho.lib.hellocharts.model.ChartData;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.Chart;
import lecho.lib.hellocharts.view.LineChartView;

import static android.R.attr.data;

/**
 * Created by Dad on 2017/8/9.
 */

public class ChartActivity extends Activity {
    private LineChartView mchart;
    private Map<String, Integer> table = new TreeMap<String, Integer>();
    List<CostBean> allData;
    private LineChartData mData;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chart);
        mchart = (LineChartView) findViewById(R.id.chart);
        allData = (List<CostBean>) getIntent().getSerializableExtra("cost_list");
        generateValues();
        generateChart();
    }

    private void generateChart() {
        List<Line> lines=new ArrayList<Line>();
        List<PointValue>values=new ArrayList<PointValue>();

        int indexX=0;
        for (Integer value:table.values()) {
            values.add(new PointValue(indexX,value));
            indexX++;
        }
        Line line=new Line(values);
        line.setColor(ChartUtils.COLOR_BLUE);
        line.setShape(ValueShape.CIRCLE);
        line.setPointColor(ChartUtils.COLOR_GREEN);
        lines.add(line);

        mData=new LineChartData(lines);
        mchart.setLineChartData(mData);
    }

    private void generateValues() {
        if (allData != null) {

            for (int i = 0; i < allData.size(); i++) {
                CostBean bean = allData.get(i);
                String costDate = bean.costData;
                int costMoney = Integer.parseInt(bean.costMoney);
                if (!table.containsKey(costDate)) {
                    table.put(costDate, costMoney);
                } else {
                    int originMoney=table.get(costDate);
                    table.put(costDate,originMoney+costMoney);
                }
            }
        }
    }
}
