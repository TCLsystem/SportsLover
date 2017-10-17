package com.example.user.sportslover.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.user.sportslover.R;
import com.example.user.sportslover.activity.PhoneValidateActivity;
import com.example.user.sportslover.util.TelNumMatch;
import com.example.user.sportslover.util.ToastUtil;

public class GetPhoneNumber extends AppCompatActivity {

         EditText et_number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_phone_number);
        et_number = (EditText) findViewById(R.id.et_get_phoneNumber) ;
        Button phoneNumber = (Button)findViewById(R.id.btn_get_phoneNumber);
        phoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!et_number.getText().toString().isEmpty()&&TelNumMatch.isValidPhoneNumber(et_number.getText().toString())) {
                    Intent intent = new Intent(GetPhoneNumber.this, PhoneValidateActivity.class);
                    intent.putExtra("phoneNumber",et_number.getText().toString());//传递数据，关键字为“bookName”，传递的是书名
                    startActivity(intent);

                }
                else{
                        ToastUtil.showShort(GetPhoneNumber.this,"Please insert right phone number");
                }
            }
        });


    }
}
