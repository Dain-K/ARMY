package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ReportActivity extends AppCompatActivity {

    private TextView tv_report;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        tv_report = findViewById(R.id.tv_report);
        Intent intent = getIntent();
        String str = intent.getStringExtra("uid");
        tv_report.setText(str+"님 환영합니다.");

    }



}
