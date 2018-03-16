//Gustavo L de Oliveira - 15647

package com.example.gustavo.getadublinbike;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class WeatherActivity extends AppCompatActivity {
    EditText cityName;
    TextView resultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        cityName = (EditText) findViewById(R.id.cityName);
        resultTextView = (TextView) findViewById(R.id.resultTextView);


    }



    public void findWeather(View view) {

        Log.i("cityName", cityName.getText().toString());

        InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(cityName.getWindowToken(), 0);

        String encodedCityName = null;


        try {
            encodedCityName = URLEncoder.encode(cityName.getText().toString(), "UTF-8");
            DownloadTask task = new DownloadTask();

            //task.execute("http://api.openweathermap.org/data/2.5/forecast?id=524901&APPID=f0edfed428b32d90005e2bd6045bd32f");
            task.execute("http://api.openweathermap.org/data/2.5/weather?q=" + encodedCityName + "&APPID=f0edfed428b32d90005e2bd6045bd32f");


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();

            Toast.makeText(getApplicationContext(), "Could not find weather", Toast.LENGTH_LONG).show();

        }




    }



    public class DownloadTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;

            try {
                url = new URL(urls[0]);

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.connect();
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);

                int data = reader.read();

                while (data != -1) {

                    char current = (char) data;

                    result += current;

                    data = reader.read();
                }

                return result;


            } catch (MalformedURLException e) {
                e.printStackTrace();
                System.out.println("T1");

            } catch (IOException e) {
                System.out.println("T2");
                e.printStackTrace();


            } catch (Exception e){
                System.out.println("T3");

            }

            System.out.println("" + result);

            urlConnection.disconnect();

            System.out.println("This is the " + result);
            return result;


        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            System.out.println("T4");
            if (result != null) {

                try {

                    String message = "";

                    JSONObject jsonObject = new JSONObject(result);

                    String weatherInfo = jsonObject.getString("weather");

                    Log.i("Website content", weatherInfo);

                    JSONArray array = new JSONArray(weatherInfo);

                    for (int i = 0; i < array.length(); i++) {

                        JSONObject jsonPart = array.getJSONObject(i);

                        String main = "";
                        String description = "";

                        main=  jsonPart.optString("main");
                        description = jsonPart.optString("description");

                        if (main != "" && description != ""){

                            message += main + ": " + description + "\r\n";
                        }



                    }

                    if (message != ""){

                        resultTextView.setText(message);
                    } else {
                        System.out.println("T5");
                        Toast.makeText(getApplicationContext(), "Could not find weather", Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    System.out.println("T6");
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Could not find weather", Toast.LENGTH_LONG).show();

                }

            }

        }
    }
}
