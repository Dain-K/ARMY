package com.example.myapplication;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class ReportViewActivity extends AppCompatActivity {
    RadioGroup radioGroup;
    private static TextView view_name;
    private TextView view_uid;
    private TextView view_reportdate;
    private TextView view_time;
    private TextView view_content;
    private TextView view_location;
    private static String user_id, date;
    private static RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_view);
        Intent intent = getIntent();
        user_id = intent.getStringExtra("user_id");
        date = intent.getStringExtra("date");
        view_name = findViewById(R.id.view_name);
        view_uid = findViewById(R.id.view_uid);
        view_reportdate = findViewById(R.id.view_reportdate);
        view_time = findViewById(R.id.view_time);
        view_content = findViewById(R.id.view_content);
        view_location = findViewById(R.id.view_location);
        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        radioGroup = findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rg_btn1:

                        break;
                    case R.id.rg_btn2:

                        break;
                }
            }
        });
        reportView();

    }
    public void reportView() {
        //php url 입력
        String URL = "report_view.php";

        StringRequest request = new StringRequest(Request.Method.POST, MainActivity.baseURL+URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //응답이 되었을때 response로 값이 들어옴

                try{
                    JSONObject jsonObject = new JSONObject(response);
                    Boolean success = jsonObject.getBoolean("success");
                    if(success) {
                        view_name.setText(jsonObject.getString("name"));
                        view_uid.setText(jsonObject.getInt("user_user_id"));
                        view_reportdate.setText(jsonObject.getInt("report_date"));
                        view_time.setText(jsonObject.getInt("report_time"));
                        view_content.setText(jsonObject.getString("content"));
                        view_location.setText(jsonObject.getString("uAddress"));

                        Toast.makeText(getApplicationContext(), "" , Toast.LENGTH_SHORT).show();

                    }else{
                        Toast.makeText(getApplicationContext(),"로그인 실패",Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //에러나면 error로 나옴
                Toast.makeText(getApplicationContext(), "에러:" + error.getMessage(), Toast.LENGTH_SHORT).show();

            }

        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<String, String>();
                //php로 설정값을 보낼 수 있음
                param.put("user_user_id",user_id);
                param.put("report_date",date);
                return param;
            }
        };


        request.setShouldCache(false);
        requestQueue.add(request);
    }


}
