package com.example.user.sportslover.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.user.sportslover.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MakeGoal extends AppCompatActivity {
    @Bind(R.id.tvDay)
    TextView tvDay;
    @Bind(R.id.tvTime)
    TextView tvTime;
    @Bind(R.id.btnSave)
    Button btnSave;
    SeekBar seekBar1;
    SeekBar seekBar2;
    int day;
    int time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_make_goal);
        ButterKnife.bind(this);

        seekBar1 = (SeekBar) findViewById(R.id.seekDay);
        seekBar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvDay.setText("Everyweek: "+progress+" day");
                day=progress;

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekBar2= (SeekBar) findViewById(R.id.seekTime);
        seekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvTime.setText("Everyday: "+progress+" min");
                time=progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        btnSave = (Button) findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MakeGoal.this, MotionGoalActivity.class);
                intent.putExtra("goal","Gaol "+day+" days,ervrey day "+time+" min");
                startActivity(intent);
            }
        });

    }
}
