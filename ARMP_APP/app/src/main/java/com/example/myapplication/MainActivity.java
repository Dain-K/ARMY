package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private EditText et_id,et_pw;
    private Button btn_login;
    private String user_id, user_birth;
    private LinearLayout backlayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_id = findViewById(R.id.et_id);
        et_pw = findViewById(R.id.et_pw);
        btn_login = findViewById(R.id.btn_login);
        backlayout = findViewById(R.id.backlayout);
        Intent intent = new Intent(getApplicationContext(),ReportActivity.class);
        startActivity(intent);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user_id = et_id.getText().toString();
                user_birth = et_pw.getText().toString();
                LoginService loginService = new LoginService(getApplicationContext());
                loginService.userLogin(user_id, user_birth, new LoginService.VolleyResponseLogin() {
                    @Override
                    public void onError(String message) {
                        Toast.makeText(getApplicationContext(),"로그인에 실패했습니다.",Toast.LENGTH_SHORT).show();
                        Log.e("Main/Login",message);
                    }

                    @Override
                    public void onResponse(Boolean success) {
                        if(success){
                            Toast.makeText(getApplicationContext(),"로그인에 성공했습니다.",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(),ReportActivity.class);
                            intent.putExtra("uid",user_id);
                            startActivity(intent);
                        }
                    }
                });
            }
        });
        backlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                et_id.clearFocus();
                backlayout.clearFocus();
            }
        });


    }
}