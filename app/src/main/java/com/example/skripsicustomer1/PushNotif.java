package com.example.skripsicustomer1;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class PushNotif {
    private String SERVER_KEY = "AAAAq-fJnog:APA91bH5BkH0MPMzeuZbEkK41qgRQ8eThF4rVlre5QCeWyMJHfsOS9UpIkMIfOErHpy8yJVZgQ6sLCWWHytoOgV3GPU4r1O5PH4-X4Fpj6gx7I3W4JPEJ6yeC_-LPhT66pKry0Cy5YXV";

    public void pushNotiftoMontir(Context context, String DEVICE_TOKEN) throws JSONException {
//        String title = "Petir";
//        String message = "Kamu mendapat pesananan";
////        String pushMessage = "{\"data\":{\"title\":\"" +title + "\"," +
////                "\"message\":\"" +message +"\"}," +
////                "\"to\":\"" + DEVICE_TOKEN +"\"}";
//
//        String push = "\"to\":\"" + DEVICE_TOKEN + "\"," +
//                        "\"content_available\":" + true +"," +
//                        "\"priority\":\"high\"," +
//                        "\"notification\":{" +
//                            "\"title\":\"" + title + "\"," +
//                            "\"text\":\"" + message + "\"}";
//        URL url = new URL("https://fcm.googleapis.com/fcm/send");
//        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//        conn.setRequestProperty("Authorization", "key=" + SERVER_KEY);
//        conn.setRequestProperty("Content-Type", "application/json");
//        conn.setRequestMethod("POST");
//        conn.setDoOutput(true);
//
//        OutputStream outputStream = conn.getOutputStream();
//        outputStream.write(push.getBytes());
//
//        Log.e("asdad", String.valueOf(conn.getResponseCode()));
//        Log.e("asdazzzzzz",conn.getResponseMessage());

        JSONObject jsonBody = new JSONObject();
        jsonBody.put("to",DEVICE_TOKEN);
        jsonBody.put("priority","high");
        jsonBody.put("content_available",true);


        JSONObject jsonObject = new JSONObject();
        jsonObject.put("title","Petir");
        jsonObject.put("body","Kamu mendapatkan pesanan");
        jsonBody.putOpt("notification",jsonObject);

        final String requestBody = jsonBody.toString();

        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest sr = new StringRequest(Request.Method.POST,"https://fcm.googleapis.com/fcm/send", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("tag", "Response: " + response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("tag", "That didn't work..." + error);

            }

        }){
            @Override
            protected Map<String, String> getParams(){
                Map<String,String> params = new HashMap<String, String>();

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Authorization","key="+SERVER_KEY);
                params.put("Content-Type","application/json");

                return params;
            }



            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                    return null;
                }
            }

        };
        queue.add(sr);

    }
}