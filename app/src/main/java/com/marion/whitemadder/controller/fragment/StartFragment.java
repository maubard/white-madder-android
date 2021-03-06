package com.marion.whitemadder.controller.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.marion.whitemadder.R;
import com.marion.whitemadder.model.Area;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class StartFragment extends Fragment {

    private Handler mHandler;
    private Area mArea;

    private TextView mvTime;
    private TextView mvDistance;

    private SimpleDateFormat mDateFormat;
    private DecimalFormat mDecimalFormat;

    public StartFragment() {
        // Required empty public constructor
        mDateFormat = new SimpleDateFormat("mm:ss");
        mDecimalFormat = new DecimalFormat("0.000 km");


        // Create area
        mArea = new Area();
        mArea.addLine(1.750f, 50.00f);
        mArea.addLine(2.420f, 30.00f);
        mArea.addLine(3.620f, 45.50f);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_start, container, false);
        mvTime = (TextView) view.findViewById(R.id.time);
        mvDistance = (TextView) view.findViewById(R.id.distance);
        Button button = (Button) view.findViewById(R.id.start);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                start();
            }
        });
        return view;
    }

    /**
     * Start the timer
     */
    public void start() {
        final long refMilliSeconds = Calendar.getInstance().getTimeInMillis();
        refresh(0);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                long newMilliSeconds = Calendar.getInstance().getTimeInMillis();
                refresh((newMilliSeconds - refMilliSeconds) / 1000);
                mHandler.postDelayed(this, 1000);
            }
        };

        mHandler = new Handler();
        mHandler.postDelayed(runnable, 1000);
    }

    /**
     * Refresh UI according to new time
     *
     * @param seconds
     */
    public void refresh(long seconds) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(seconds*1000);
        mvTime.setText(mDateFormat.format(calendar.getTime()));
        mvDistance.setText(mDecimalFormat.format(mArea.calculateDistance(seconds)));
    }
}
