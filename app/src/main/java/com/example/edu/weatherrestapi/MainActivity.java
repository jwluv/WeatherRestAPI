package com.example.edu.weatherrestapi;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button buttonLondon, buttonSeoul;
    TextView textViewResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonLondon = findViewById(R.id.buttonLondon);
        buttonSeoul = findViewById(R.id.buttonSeoul);

        buttonLondon.setOnClickListener(this);
        buttonSeoul.setOnClickListener(this);

        textViewResult = findViewById(R.id.textViewResult);
    }

    @Override
    public void onClick(View v) {

        String str = "";
        if(v == buttonLondon)
            str = "London";
        else if(v == buttonSeoul)
            str = "Seoul";

        OpenWeatherAPITask task = new OpenWeatherAPITask();
            try {
                String weather = task.execute(str).get();
                textViewResult.setText(weather);
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

    }

}

    class OpenWeatherAPITask extends AsyncTask<String, Void, String> {
        @Override
        public String doInBackground(String... params) {
            String id = params[0];
            String urlString = "http://api.openweathermap.org/data/2.5/weather" + "?q=" + id + "&appid=fb7dc0a2ebcb1f430f23cc2744f35c5f";
            URL url = null;

            try {
                url = new URL(urlString);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            HttpURLConnection urlConnection = null;
            try {
                urlConnection = (HttpURLConnection) url.openConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
            InputStream in = null;
            try {
                in = urlConnection.getInputStream();
            } catch (Exception e) {
                e.printStackTrace();
            }
            byte[] buffer = new byte[1000];
            String weather = "";
            try {
                in.read(buffer);
                weather = new String(buffer);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return weather;

        }
    }

