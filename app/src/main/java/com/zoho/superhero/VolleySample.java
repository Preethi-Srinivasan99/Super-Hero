package com.zoho.superhero;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.zoho.superhero.util.AppController;

import org.json.JSONObject;

public class VolleySample extends AppCompatActivity {

    private TextView tv;
    private String[] strArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volley_sample);
        Button btn = findViewById(R.id.requestBtn);
        strArray = this.getApplicationContext().getResources().getStringArray(R.array.super_hero);
        tv = findViewById(R.id.textView);
        int count = strArray.length;
        tv.setText(String.valueOf(count)+strArray[730]);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getRequest();
            }
        });
    }

    void getRequest() {
        // Tag used to cancel the request
        String tag_json_obj = "json_obj_req";

        String url = "https://superheroapi.com/api/3847348325379211/search/superman";
//        String url = "https://www.superheroapi.com/api.php/access-token/character-id/image";


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        tv.setText(response.toString());
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                tv.setText(error.getMessage());
                // hide the progress dialog
            }
        });
// Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
    }
}
