package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private EditText et_id,et_pw;
    private Button btn_login;
    private static String user_id, user_birth,birth,unitName;
    private LinearLayout backlayout;
    static int uid,uClassCode,unitCode;
    static String userName;
    private static RequestQueue requestQueue;
    public final static String baseURL = "http://165.229.187.242/army_server/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_id = findViewById(R.id.et_id);
        et_pw = findViewById(R.id.et_pw);
        btn_login = findViewById(R.id.btn_login);
        backlayout = findViewById(R.id.backlayout);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user_id = et_id.getText().toString();
                user_birth = et_pw.getText().toString();
                Log.e("id",user_id);
                Log.e("birth",user_birth);
                login();

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
    public void login() {
        //php url 입력
        String URL = "Login.php";

        StringRequest request = new StringRequest(Request.Method.POST, baseURL+URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //응답이 되었을때 response로 값이 들어옴

                try{
                    JSONObject jsonObject = new JSONObject(response);
                    Boolean success = jsonObject.getBoolean("success");
                    if(success) {
                        uid = jsonObject.getInt("userID");
                        birth = jsonObject.getString("birth");
                        userName = jsonObject.getString("userName");
                        uClassCode = jsonObject.getInt("uclass_code");
                        unitCode = jsonObject.getInt("unit_code");
                        unitName = jsonObject.getString("unit_name");
                        ArmyUser user = new ArmyUser(uid, birth, userName, uClassCode, unitCode, unitName);
                        Intent intent;
                        Toast.makeText(getApplicationContext(), userName+"님 환영합니다." , Toast.LENGTH_SHORT).show();
                        if (uClassCode >= 200) {
                            intent = new Intent(getApplicationContext(), ReportViewActivity.class);

                        } else {
                            intent = new Intent(getApplicationContext(), ReportActivity.class);

                        }
                        intent.putExtra("User", user);
                        startActivity(intent);
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
                param.put("user_id",user_birth);
                param.put("birth",user_id);
                return param;
            }
        };


        request.setShouldCache(false);
        requestQueue.add(request);
    }

}