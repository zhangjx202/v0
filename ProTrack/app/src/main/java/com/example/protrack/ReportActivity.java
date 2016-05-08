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

    public final static String REPORT_OPEN = "REPORT_OPEN";
    public final static String REPORT_CLOSE = "REPORT_CLOSE";
    public final static String REPORT_HOURS = "REPORT_HOURS";
    public final static String REPORT_COUNT = "REPORT_COUNT";

    private TextView mHours;
    private TextView mOpenSummary;
    private TextView mCloseSummary;
    private TextView mCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_view);

        mOpenSummary = (TextView) findViewById(R.id.summaryOpenText);
        mOpenSummary.setText(MainActivity.listAdapter.getSummary());
        mCloseSummary = (TextView) findViewById(R.id.summaryCloseText);
        mCloseSummary.setText(MainActivity.closedAdapter.getSummary());
        mHours = (TextView) findViewById(R.id.hoursText);
        mHours.setText("" + (MainActivity.closedAdapter.getTotalHours()
                + MainActivity.listAdapter.getTotalHours()));
        mCount = (TextView) findViewById(R.id.countText);
        mCount.setText("" + (MainActivity.closedAdapter.getCount()
                + MainActivity.listAdapter.getCount()));

        GraphView line_graph = (GraphView) findViewById(R.id.graph);

        LineGraphSeries<DataPoint> series1 =
                new LineGraphSeries<DataPoint>(new DataPoint[] {
                        new DataPoint(0, 1),
                        new DataPoint(1, 2),
                        new DataPoint(2, 3),
                        new DataPoint(3, 4),
                        new DataPoint(4, 5),
                        new DataPoint(5, 6),
                        new DataPoint(6, 7),
                        new DataPoint(7, 8),
                        new DataPoint(8, 9),
                        new DataPoint(9, 10)
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
                        new DataPoint(1, 0),
                        new DataPoint(2, 1),
                        new DataPoint(3, 1),
                        new DataPoint(4, 2),
                        new DataPoint(5, 2),
                        new DataPoint(6, 3),
                        new DataPoint(7, 3),
                        new DataPoint(8, 4),
                        new DataPoint(9, 4)
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

        // bounds
        line_graph.getViewport().setBackgroundColor(Color.WHITE);
        line_graph.getViewport().setMaxX(12d);
        line_graph.getViewport().setMaxY(12d);
    }


}
