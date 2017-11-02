package com.example.user.sportslover.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.sportslover.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MotionGoalActivity extends AppCompatActivity {
    @Bind(R.id.goal_back)
    ImageView goal_back;
    @Bind(R.id.tvGoalday)
    TextView tvGoalDay;
    @Bind(R.id.tvGoalAll)
    TextView tvGoalAll;
    @Bind(R.id.btnGoal)
    Button btnGoal;
    String goal = new String();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_motion_goal);
        ButterKnife.bind(this);
        goal = getIntent().getStringExtra("goal");
        tvGoalAll.setText(goal);
        // tvGoalDay.setText(goal.charAt(6)+" days");
    }

    @OnClick({R.id.tvGoalday, R.id.tvGoalAll, R.id.btnGoal})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnGoal:
                startActivity(new Intent(MotionGoalActivity.this, MakeGoal.class));
                break;
            case R.id.goal_back:
                finish();
                break;
            default:
                break;

        }
    }
}
