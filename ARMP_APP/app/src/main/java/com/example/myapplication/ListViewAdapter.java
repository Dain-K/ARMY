package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ListViewAdapter extends BaseAdapter {
    private Context context;

    TextView tv_uid, tv_date,tv_time;
    CheckBox ck_report;

    // Adapter 에 추가된 데이터를 저장하기 위한 ArrayList
    private ArrayList<ListViewItem> listViewItemList = new ArrayList<ListViewItem>();

    // ListViewAdapter 의 생성자
    public ListViewAdapter(Context context, ArrayList report_list) {
        this.context = context;
        this.listViewItemList = report_list;
    }

    // Adapter 에 사용되는 데이터의 개수를 리턴
    @Override
    public int getCount() {
        return listViewItemList.size();
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //listview아이템에 culturelist2 레이아웃을 사용
        View v = View.inflate(context,R.layout.reportlist_item,null);
        tv_uid= v.findViewById(R.id.tv_uid);
        tv_date= v.findViewById(R.id.tv_date);
        tv_time= v.findViewById(R.id.tv_time);
        ck_report= v.findViewById(R.id.ck_report);
        tv_uid.setText(listViewItemList.get(position).getUid());
        tv_uid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,ReportViewActivity.class);
                intent.putExtra("user_id",tv_uid.getText().toString());
                intent.putExtra("report_date",tv_date.getText().toString());
                context.startActivity(intent);
            }
        });
        tv_date.setText(listViewItemList.get(position).getReport_date());
        tv_time.setText(listViewItemList.get(position).getReport_time());

        if(listViewItemList.get(position).getIsCheck()==0){
            ck_report.setText("미확인");
        }else if(listViewItemList.get(position).getIsCheck()==1){
            ck_report.setText("불합격");
        }else{
            ck_report.setText("합격");
        }
        return v;
    }

    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴
    @Override
    public long getItemId(int position) {
        return position;
    }

    // 지정한 위치(position)에 있는 데이터 리턴
    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position);
    }

    // 아이템 데이터 추가를 위한 함수.
    public void addItem(String uid, String date, String time, int isCheck) {
        ListViewItem item = new ListViewItem();

        item.setUid(uid);
        item.setReport_date(date);
        item.setReport_time(time);
        item.setIsCheck(isCheck);
        
        listViewItemList.add(item);
    }
}