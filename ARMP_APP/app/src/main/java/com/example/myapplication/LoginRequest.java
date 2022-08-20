package com.example.myapplication;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;

public class LoginRequest extends StringRequest {
    public final static String baseUrl = "http://165.229.125.26/army_server/";
    public final static String Url = "Login.php";
    HashMap<String,String> map;
    public LoginRequest(String user_id, String birth,Response.Listener<String> listener, @Nullable Response.ErrorListener errorListener) {
        super(Method.POST, baseUrl+Url, listener, errorListener);
        map = new HashMap<>();
        map.put("user_id",user_id);
        map.put("birth",birth);
    }

    @Nullable
    @Override
    public HashMap<String,String> getParams() throws AuthFailureError {
        return map;
    }
}
