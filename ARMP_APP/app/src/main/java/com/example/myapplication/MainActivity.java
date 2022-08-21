package com.example.myapplication;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

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
    public static final int REQUEST_PERMISSION = 11;
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
        checkPermission();
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

                        Toast.makeText(getApplicationContext(), userName+"님 환영합니다." , Toast.LENGTH_SHORT).show();
                        if (uClassCode >= 200) {
                            Intent intent = new Intent(getApplicationContext(), ReportListActivity.class);
                            intent.putExtra("User", user);
                            startActivity(intent);
                        } else {
                            isReport();
                        }

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

    //권한 확인
    public void checkPermission() {
        int permissionCamera = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        int permissionRead = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int permissionWrite = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        //권한이 없으면 권한 요청
        if (permissionCamera != PackageManager.PERMISSION_GRANTED
                || permissionRead != PackageManager.PERMISSION_GRANTED
                || permissionWrite != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                Toast.makeText(this, "이 앱을 실행하기 위해 권한이 필요합니다.", Toast.LENGTH_SHORT).show();
            }

            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSION);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode,permissions,grantResults);
        switch (requestCode) {
            case REQUEST_PERMISSION: {
                // 권한이 취소되면 result 배열은 비어있다.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(this, "권한 확인", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(this, "권한 없음", Toast.LENGTH_LONG).show();
                    finish(); //권한이 없으면 앱 종료
                }
            }
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        checkPermission(); //권한체크
    }

    public void isReport() {
        //php url 입력
        String URL = "report_request.php";

        StringRequest request = new StringRequest(Request.Method.POST, baseURL+URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //응답이 되었을때 response로 값이 들어옴
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    Boolean success = jsonObject.getBoolean("success");
                    Log.e("isReport? ",success.toString());
                    ArmyUser user = new ArmyUser(uid, birth, userName, uClassCode, unitCode, unitName);
                    if(success){
                        Intent intent = new Intent(getApplicationContext(),ReportreviseActivity.class);
                        intent.putExtra("User", user);
                        Log.e("User",user.toString());
                        String desc = jsonObject.getString("content");
                        intent.putExtra("desc",desc);
                        String address = jsonObject.getString("uAddress");
                        String report_time = jsonObject.getString("report_time");
                        intent.putExtra("address",address);
                        intent.putExtra("time",report_time);

                        startActivity(intent);
                    }else{
                        Log.e("User",user.toString());
                        Intent intent = new Intent(getApplicationContext(),ReportActivity.class);
                        intent.putExtra("User",user);
                        startActivity(intent);
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
                return param;
            }
        };


        request.setShouldCache(false);
        requestQueue.add(request);
    }
}