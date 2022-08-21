package com.example.myapplication;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class ReportListActivity extends AppCompatActivity {

    TextView dateText;
    DatePickerDialog datePickerDialog;
    int y=0, m=0, d=0, h=0, mi=0;

    // item 데이터 객체, 클래스 생성
    private class ListView_Item {
        // 아이템 각각 내용, 'Title'
        private String title;
        // 아이템 각각 이미지 리소스 ID, 'Image'
        private int image;

        // 생성자 함수
        public ListView_Item(String title, int image) {
            this.title = title;
            this.image = image;
        }

        public String getTitle() {
            return title;
        }

        public int getImage() {
            return image;
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportlist);

        // 날자 팝업 부분
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy", Locale.getDefault());
        SimpleDateFormat monthFormat = new SimpleDateFormat("MM", Locale.getDefault());
        SimpleDateFormat dayFormat = new SimpleDateFormat("dd", Locale.getDefault());

        String set_year = yearFormat.format(currentTime);
        String set_month = monthFormat.format(currentTime);
        String set_day = dayFormat.format(currentTime);


        Button button = findViewById(R.id.date_picker_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(ReportListActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        y = year;
                        m = month+1;
                        d = dayOfMonth;
                        String set_date = y+"/"+m+"/"+d;
                        button.setText(set_date);
                    }
                },Integer.parseInt(set_year), Integer.parseInt(set_month), Integer.parseInt(set_day));

                datePickerDialog.show();

            }
        });

        // ListView action 구현

    } // OnCreate
} // ReportListActivity
