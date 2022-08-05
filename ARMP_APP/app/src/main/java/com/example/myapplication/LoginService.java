package com.example.myapplication;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginService {

    private static  final String Query_FOR_USERLOGIN = "http://localhost:8080/login?id=";
    private Context context;
    private Boolean success_login;
    public LoginService(Context context){
        this.context = context;
    }

    public interface  VolleyResponseLogin{
        void onError(String message);
        void onResponse(Boolean success);
    }

    public void userLogin(String user_id, String birth_date, VolleyResponseLogin volleyResponseLogin){
        String url = Query_FOR_USERLOGIN+user_id+"&birth_date="+birth_date;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                success_login=false;
                try {
                    JSONObject jsonObject = response.getJSONObject(0);
                    success_login = jsonObject.getBoolean("success");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                volleyResponseLogin.onResponse(success_login);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                volleyResponseLogin.onError(error.getMessage());
            }
        });
        MySingleton.getInstance(context).addToRequestQueue(request);
    }
}
