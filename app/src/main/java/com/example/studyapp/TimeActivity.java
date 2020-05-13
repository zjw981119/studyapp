package com.example.studyapp;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class TimeActivity extends AppCompatActivity {
    private long time;
    private TextView tv_Timer;
    Calendar cal= Calendar.getInstance();//获取系统时间
    int year=cal.get(Calendar.YEAR);
    int month=cal.get(Calendar.MONTH)+1;
    int day=cal.get(Calendar.DATE)+1;
    int wekday=cal.get(Calendar.DAY_OF_WEEK);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);
        //获取用户输入的专注时间
        tv_Timer = findViewById(R.id.tv_timer1);
        Bundle bundle=getIntent().getExtras();
        long time=bundle.getLong("num");
        time = time *1000;
        final long finalTime = time/1000;
        new CountDownTimer(time,1000) {
            @Override
            public void onTick(long t) {
                long hour=t/(1000*60*60);
                long minute=(t-hour*(1000*60*60))/(1000*60);
                long second=(t-hour*(1000*60*60)-minute*(1000*60))/1000;
                tv_Timer.setText("剩余" + hour+"小时" + minute + "分钟" +second + "秒");
            }
            @Override
            public void onFinish() {
                Toast.makeText(TimeActivity.this,"您已成功完成专注计划", Toast.LENGTH_SHORT).show();
                Dao dao=new Dao(getApplicationContext());
                if(dao.query(year,month,day)) //该日已有学习记录,对改日学习记录进行修改
                {
                    dao.update(year,month,day,(int)finalTime);
                }
                else//没有学习记录，创建学习记录
                {
                    dao.insert(year,month,day,(int)finalTime);
                }
            }
        }
                .start();
    }


}
