package com.example.myapplication;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.Button;
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
    private static int isCheck =1;
    private static TextView view_uid;
    private Button btn_confirm;
    private static TextView view_reportdate;
    private static TextView view_time;
    private static TextView view_content;
    private static TextView view_location;
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
        btn_confirm = findViewById(R.id.btn_confirm);
        view_content = findViewById(R.id.view_content);
        view_location = findViewById(R.id.view_location);
        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirm();
            }
        });
        radioGroup = findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rg_btn1:
                        isCheck=2;
                        break;
                    case R.id.rg_btn2:
                        isCheck=1;
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
                Log.e("response",response.toString());
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    Boolean success = jsonObject.getBoolean("success");
                    Log.e("success",success.toString());
                    if(success) {
                        String name = jsonObject.getString("name");
                        view_name.setText(name);
                        String uid = jsonObject.getString("user_user_id");
                        view_uid.setText(uid);
                        String date = jsonObject.getString("report_date");
                        view_reportdate.setText(date);
                        String time = jsonObject.getString("report_time");
                        view_time.setText(time);
                        String content = jsonObject.getString("content");
                        view_content.setText(content);
                        String address = jsonObject.getString("uAddress");
                        view_location.setText(address);

                        Toast.makeText(getApplicationContext(), "보고서 로딩 성공" , Toast.LENGTH_SHORT).show();

                    }else{
                        Toast.makeText(getApplicationContext(),"보고서 로딩 실패",Toast.LENGTH_SHORT).show();
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
                Log.e("date",date);

                return param;
            }
        };


        request.setShouldCache(false);
        requestQueue.add(request);
    }
    public void confirm() {
        //php url 입력
        String URL = "report_confirm.php";

        StringRequest request = new StringRequest(Request.Method.POST, MainActivity.baseURL+URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e("response",response.toString());
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    Boolean success = jsonObject.getBoolean("success");
                    Log.e("success",success.toString());
                    if(success) {


                        Toast.makeText(getApplicationContext(), "보고서 확인 완료" , Toast.LENGTH_SHORT).show();

                        finish();
                    }else{
                        Toast.makeText(getApplicationContext(),"보고서 확인 실패 ",Toast.LENGTH_SHORT).show();
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
                param.put("user_id",user_id);
                param.put("date",date);
                param.put("isCheck",String.valueOf(isCheck));

                Log.e("date",date);

                return param;
            }
        };


        request.setShouldCache(false);
        requestQueue.add(request);
    }

}
