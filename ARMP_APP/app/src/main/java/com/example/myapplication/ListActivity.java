package com.example.myapplication;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class ListActivity extends AppCompatActivity {

    private ListView listview ;
    private ListViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportlist);

        // Adapter 생성
        adapter = new ListViewAdapter();

        // 리스트뷰 참조 및 Adapter 달기
        listview = (ListView) findViewById(R.id.listview);
        listview.setAdapter(adapter);
        //listview.setOnItemClickListener(listener);

        adapter.addItem("제목1", R.drawable.camera, "내용1");  //(제목 부분, 이미지, 내용)
        adapter.addItem("제목2", R.drawable.camera, "내용2");
        adapter.addItem("제목3", R.drawable.camera, "내용3");
        adapter.addItem("제목4", R.drawable.camera, "내용4");
        adapter.addItem("제목5", R.drawable.camera, "내용5");

        adapter.notifyDataSetChanged(); //어댑터의 변경을 알림.
    }
}