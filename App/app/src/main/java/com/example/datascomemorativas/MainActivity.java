package com.example.datascomemorativas;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private TextView textViewDay, textViewMonth;
    private ListView listView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        requestPermissions();

        textViewDay = findViewById(R.id.textViewDay);
        textViewMonth = findViewById(R.id.textViewMonth);
        listView = findViewById(R.id.listViewDatas);
        progressBar = findViewById(R.id.progressBar);

        Map<String,String> mapDayMonth = setData();
        textViewDay.setText(mapDayMonth.get("day"));
        textViewMonth.setText(mapDayMonth.get("month"));

        setApiConnection("https://api-spring-dias-comemorativos.herokuapp.com/api/DatasComemorativas/hoje");
    }

    private void requestPermissions(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET},10);
        }
    }

    private Map<String,String> setData(){
        Map<String,String> map = new ArrayMap<>();

        Date date = new Date();
        Locale local = new Locale("pt","BR");
        DateFormat format = new SimpleDateFormat("dd ';' MMMM",local);

        String[] dayMonth = format.format(date).split(";");

        map.put("day",dayMonth[0]);
        map.put("month",dayMonth[1].toUpperCase());

        return map;
    }


    private void setApiConnection(String url) {
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        final List<String> dates = new ArrayList<>();
        progressBar.setVisibility(View.VISIBLE);
        progressBar.animate();
        StringRequest config = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("API","CONNECTED");

                try{
                    JSONArray jsonArray = new JSONArray(response);

                    for(int i = 0; i < jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String date = jsonObject.getString("nome");
                        Log.d("API",date);
                        dates.add(date);
                        Log.d("API",dates.get(i));
                    }

                    setListViewValues(dates);
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("API","ERROR" + error.getMessage());
                Toast.makeText(MainActivity.this, "ERROR: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(config);

    }


    private void setListViewValues(List<String> dates){
        if(dates.size() != 0){
            DateAdapter adapter = new DateAdapter(MainActivity.this,dates);
            listView.setAdapter(adapter);
            progressBar.clearAnimation();
            progressBar.setVisibility(View.GONE);
        }
    }
}