package com.example.myapplication;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.drm.DrmManagerClient;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


public class ReportListActivity extends AppCompatActivity {
    private ListViewAdapter listViewAdapter;
    private static ArrayList<ListViewItem> item_list;
    private TextView tv_rUnitName;
    private static Button btn_lookup,button;
    private ListView listView;
    private static ArmyUser armyUser;
    static SimpleDateFormat yearFormat, monthFormat,dayFormat;
    static String set_year, set_month,set_day;
    TextView dateText;
    private static RequestQueue requestQueue;
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
        Intent intent= getIntent();
        item_list = new ArrayList<>();
        armyUser = (ArmyUser) intent.getSerializableExtra("User");
        // 날자 팝업 부2
        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        Date currentTime = Calendar.getInstance().getTime();
         yearFormat = new SimpleDateFormat("yyyy", Locale.getDefault());
         monthFormat = new SimpleDateFormat("MM", Locale.getDefault());
         dayFormat = new SimpleDateFormat("dd", Locale.getDefault());
        tv_rUnitName = findViewById(R.id.tv_rUnitName);
        tv_rUnitName.setText(armyUser.getUnitName());
        listView = findViewById(R.id.listview);

         set_year = yearFormat.format(currentTime);
         set_month = monthFormat.format(currentTime);
         set_day = dayFormat.format(currentTime);


        button = findViewById(R.id.date_picker_btn);
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
                        Log.e("date", set_year+set_month+set_day);
                    }
                },Integer.parseInt(set_year), Integer.parseInt(set_month), Integer.parseInt(set_day));

                datePickerDialog.show();

            }
        });

        btn_lookup = findViewById(R.id.btn_lookup);
        btn_lookup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lookUp();
            }
        });

        // ListView action 구현

    } // OnCreate

    public void lookUp() {
        //php url 입력
        String URL = "report_list.php";


        JsonArrayRequest jsonArrayRequest  = new JsonArrayRequest(Request.Method.POST, MainActivity.baseURL + URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                String uid = "";
                Log.e("response",response.toString());
                String report_date = "";
                String report_time = "";
                int isCheck = 0;
                try{
                    for(int i = 0 ; i<response.length();i++){
                        JSONObject jsonObject = response.getJSONObject(i);
                        Log.e("object",jsonObject.toString());
                        uid = jsonObject.getString("user_user_id");
                        report_date = jsonObject.getString("report_date");
                        report_time = jsonObject.getString("report_time");
                        isCheck = jsonObject.getInt("report_checko");
                        ListViewItem listViewItem = new ListViewItem();
                        listViewItem.setIsCheck(isCheck);
                        listViewItem.setReport_time(report_time);
                        listViewItem.setReport_date(report_date);
                        listViewItem.setUid(uid);
                        Log.e("item",listViewItem.toString());
                        item_list.add(listViewItem);
                    }

                    listViewAdapter = new ListViewAdapter(getApplicationContext(),item_list);
                    listView.setAdapter(listViewAdapter);
                }catch (Exception e){

                }
            }},
                new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
                    Log.e("error",error.toString());
                }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<String, String>();
                //php로 설정값을 보낼 수 있음
                param.put("unit_unit_code",String.valueOf(armyUser.getUnitCode()));
                param.put("select_date",set_year+set_month+set_day);
                Log.e("?",param.get("select_date"));
                return param;
            }};
        Log.e("unit_code",String.valueOf(armyUser.getUnitCode()));
        Log.e("report_date",set_year+set_month+set_day);

        jsonArrayRequest.setShouldCache(false);
        requestQueue.add(jsonArrayRequest);
    }
} // ReportListActivity
