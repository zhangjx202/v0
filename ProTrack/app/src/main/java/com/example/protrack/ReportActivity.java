package com.example.protrack;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.Series;

/**
 * Created by Jun on 5/7/2016.
 */
public class ReportActivity extends Activity {

    private static final String TAG = "ProTrack-Report";

    public final static String REPORT = "REPORT";
    public final static String REPORT_HOURS = "REPORT_HOURS";

    private TextView mHours;
    private TextView mSummary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_view);

        mSummary = (TextView) findViewById(R.id.summarytext);
        mSummary.setText(getIntent().getStringExtra(REPORT));

        mHours = (TextView) findViewById(R.id.hoursText);
        mHours.setText("" + getIntent().getIntExtra(REPORT_HOURS, 0));

        GraphView line_graph = (GraphView) findViewById(R.id.graph);

        LineGraphSeries<DataPoint> series1 =
                new LineGraphSeries<DataPoint>(new DataPoint[] {
                        new DataPoint(0, 0),
                        new DataPoint(1, 5),
                        new DataPoint(2, 3),
                        new DataPoint(3, 2),
                        new DataPoint(4, 6)
                });

        //series1.setDrawDataPoints(true);
        //series1.setDataPointsRadius(10);

        series1.setColor(Color.RED);

        line_graph.addSeries(series1);

        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(line_graph);
        //staticLabelsFormatter.setHorizontalLabels(new String[]{"Jan", "Feb", "March"});
        //staticLabelsFormatter.setVerticalLabels(new String[]{"Jan", "Feb", "March"});

        series1.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {
                Toast.makeText(ReportActivity.this, "Series: On Data Point clicked: " + dataPoint, Toast.LENGTH_SHORT).show();
            }
        });


        //second graph
        LineGraphSeries<DataPoint> series2 =
                new LineGraphSeries<DataPoint>(new DataPoint[] {
                    new DataPoint(0, 0),
                    new DataPoint(1, 3),
                    new DataPoint(2, 6),
                    new DataPoint(3, 2),
                    new DataPoint(4, 5)
                });
        //draw points
        //series2.setDrawDataPoints(true);
        //series2.setDataPointsRadius(10);
        //fill background
        series2.setDrawBackground(true);
        series2.setBackgroundColor(Color.GREEN);
        //line color
        series2.setColor(Color.GREEN);

        series2.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {
                Toast.makeText(ReportActivity.this, "Series: On Data Point clicked: " + dataPoint, Toast.LENGTH_SHORT).show();
            }
        });

        line_graph.addSeries(series2);

        // legend
        series1.setTitle("Open Tasks");
        series2.setTitle("Closed Tasks");
        line_graph.getLegendRenderer().setVisible(true);
        line_graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);


    }


}
