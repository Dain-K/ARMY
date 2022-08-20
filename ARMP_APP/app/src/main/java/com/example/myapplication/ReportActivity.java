package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.DataFormatException;

public class ReportActivity extends AppCompatActivity {
    private TextView tv_unitName,tv_userName,tv_userID,tv_reportDate;
    private EditText et_time, et_report;
    private Button btn_submit;
    private TextView tv_report;
    private static RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        Intent intent = getIntent();
        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        ArmyUser user = (ArmyUser) intent.getSerializableExtra("User");
        tv_unitName = findViewById(R.id.tv_unitName);
        tv_userName = findViewById(R.id.tv_userName);
        tv_userID = findViewById(R.id.tv_userID);
        tv_report = findViewById(R.id.tv_reportDate);
        et_time = findViewById(R.id.et_time);
        et_report = findViewById(R.id.et_report);
        tv_reportDate = findViewById(R.id.tv_reportDate);
        btn_submit = findViewById(R.id.btn_submit);
        tv_unitName.setText(user.getUnitName());
        tv_userName.setText(user.getUserName());
        Log.e("ID",String.valueOf(user.getUserID()));
        tv_userID.setText(String.valueOf(user.getUserID()));
        long now  = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String getTime = sdf.format(date);
        tv_reportDate.setText(getTime);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });
    }
    public void submit() {
        //php url 입력
        String URL = "report_write.php";

        StringRequest request = new StringRequest(Request.Method.POST, MainActivity.baseURL+URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //응답이 되었을때 response로 값이 들어옴

                try{
                    JSONObject jsonObject = new JSONObject(response);
                    Boolean success  =jsonObject.getBoolean("success");
                    if(success){
                        Toast.makeText(getApplicationContext(),"보고서 제출 성공",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(getApplicationContext(),"보고서 제출 실패",Toast.LENGTH_SHORT).show();
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
                param.put("user_user_id",tv_userID.getText().toString());
                String[] str = et_time.getText().toString().split(":");
                param.put("report_time",str[0]+str[1]);
                param.put("content",et_report.getText().toString());
                param.put("latitude",String.valueOf(36.2));
                param.put("longigude",String.valueOf(127.8));
                param.put("img_url",String.valueOf("disk/img.jpg"));
                return param;
            }
        };


        request.setShouldCache(false);
        requestQueue.add(request);
    }


}
