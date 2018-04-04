package com.vs.vipsai.widget.countdown;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vs.vipsai.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Author: cynid
 * Created on 4/4/18 1:44 PM
 * Description:
 *
 * 倒计时
 */

public class CountDownTimerView extends LinearLayout {

    private TextView tv_day_decade;

    private TextView tv_day_unit;
    // 小时，十位
    private TextView tv_hour_decade;
    // 小时，个位
    private TextView tv_hour_unit;
    // 分钟，十位
    private TextView tv_min_decade;
    // 分钟，个位
    private TextView tv_min_unit;
    // 秒，十位
    private TextView tv_sec_decade;
    // 秒，个位
    private TextView tv_sec_unit;
    private Context context;


    private int day_decade;
    private int day_unit;
    private int hour_decade;
    private int hour_unit;
    private int min_decade;
    private int min_unit;
    private int sec_decade;
    private int sec_unit;
    // 计时器
    private Timer timer;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            countDown();
        };
    };

    public CountDownTimerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.view_countdowntimer, this);
        tv_day_decade = (TextView) view.findViewById(R.id.tv_day_decade);
        tv_day_unit = (TextView) view.findViewById(R.id.tv_day_unit);
        tv_hour_decade = (TextView) view.findViewById(R.id.tv_hour_decade);
        tv_hour_unit = (TextView) view.findViewById(R.id.tv_hour_unit);
        tv_min_decade = (TextView) view.findViewById(R.id.tv_min_decade);
        tv_min_unit = (TextView) view.findViewById(R.id.tv_min_unit);
        tv_sec_decade = (TextView) view.findViewById(R.id.tv_sec_decade);
        tv_sec_unit = (TextView) view.findViewById(R.id.tv_sec_unit);
    }

    /**
     *
     * @Description: 开始计时
     * @param
     * @return void
     * @throws
     */
    public void start() {
        if (timer == null) {
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.sendEmptyMessage(0);
                }
            }, 0, 1000);
        }
    }
    /**
     *
     * @Description: 停止计时
     * @param
     * @return void
     * @throws
     */
    public void stop() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    public void setDateTime(int day, int hour, int min, int sec) {
        day_decade = day /10;
        day_unit = day % 10;
        setTime(hour, min, sec);
        tv_day_decade.setText(day_decade + "");
        tv_day_unit.setText(day_unit + "");
    }

    /**
     * @throws Exception
     *
     * @Description: 设置倒计时的时长
     * @param
     * @return void
     * @throws
     */
    public void setTime(int hour, int min, int sec) {
        if (hour >= 24 || min >= 60 || sec >= 60 || hour < 0 || min < 0|| sec < 0) {
            throw new RuntimeException("Time format is error,please check out your     code");
        }
        hour_decade = hour / 10;
        hour_unit = hour - hour_decade * 10;
        min_decade = min / 10;
        min_unit = min - min_decade * 10;
        sec_decade = sec / 10;
        sec_unit = sec - sec_decade * 10;
        tv_hour_decade.setText(hour_decade + "");
        tv_hour_unit.setText(hour_unit + "");
        tv_min_decade.setText(min_decade + "");
        tv_min_unit.setText(min_unit + "");
        tv_sec_decade.setText(sec_decade + "");
        tv_sec_unit.setText(sec_unit + "");
    }
    /**
     *
     * @Description: 倒计时
     * @param
     * @return boolean
     * @throws
     */
    private void countDown() {
        if (isCarry4Unit(tv_sec_unit)) {
            if (isCarry4Decade(tv_sec_decade)) {
                if (isCarry4Unit(tv_min_unit)) {
                    if (isCarry4Decade(tv_min_decade)) {
                        if (isCarry4Unit24(tv_hour_unit)) {
                            if (isCarry4Decade24(tv_hour_decade)) {
//                                Toast.makeText(context,         "时间到了",Toast.LENGTH_SHORT).show();
                                if (isCarry4Unit(tv_day_unit)) {
                                    if (isCarry4Decade(tv_day_decade)) {
                                        stop();
                                    }
                                }
//                                stop();
                            }
                        }
                    }
                }
            }
        }
    }
    /**
     *
     * @Description: 变化十位，并判断是否需要进位
     * @param
     * @return boolean
     * @throws
     */
    private boolean isCarry4Decade(TextView tv) {
        int time = Integer.valueOf(tv.getText().toString());
        time = time - 1;
        if (time < 0) {
            time = 5;
            tv.setText(time + "");
            return true;
        } else {
            tv.setText(time + "");
            return false;
        }
    }
    /**
     *
     * @Description: 变化个位，并判断是否需要进位
     * @param
     * @return boolean
     * @throws
     */
    private boolean isCarry4Unit(TextView tv) {
        int time = Integer.valueOf(tv.getText().toString());
        time = time - 1;
        if (time < 0) {
            time = 9;
            tv.setText(time + "");
            return true;
        } else {
            tv.setText(time + "");
            return false;
        }
    }


    /**
     *
     * @Description: 变化十位，并判断是否需要进位
     * @param
     * @return boolean
     * @throws
     */
    private boolean isCarry4Decade24(TextView tv) {
        int time = Integer.valueOf(tv.getText().toString());
        time = time - 1;
        if (time < 0) {
            time = 2;
            tv.setText(time + "");
            return true;
        } else {
            tv.setText(time + "");
            return false;
        }
    }

    /**
     *
     * @Description: 变化个位，并判断是否需要进位
     * @param
     * @return boolean
     * @throws
     */
    private boolean isCarry4Unit24(TextView tv) {
        int time = Integer.valueOf(tv.getText().toString());
        time = time - 1;
        if (time < 0) {
            time = 3;
            tv.setText(time + "");
            return true;
        } else {
            tv.setText(time + "");
            return false;
        }
    }

}
