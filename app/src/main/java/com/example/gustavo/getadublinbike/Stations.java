//Gustavo L de Oliveira - 15647
package com.example.gustavo.getadublinbike;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Stations extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stations);


        Intent in = getIntent();
        Bundle bundle = in.getExtras();

        ArrayList<String> stationsArr = bundle.getStringArrayList("stationsArr");



        //List View
        ListView listView = (ListView) findViewById(R.id.listView);
        ArrayAdapter <String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, stationsArr);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


            }
        });





    }
/*
    //Task Execution
    public void getStations (View view){

        DownloadTask task = new DownloadTask();

        String result = null;
        task.execute("https://api.jcdecaux.com/vls/v1/stations?contract=Dublin&apiKey=4a9827a5bfe6858c7f8b940877d1b78d03608e57");

    }

    //Download task
    public class DownloadTask extends AsyncTask<String, Void, String>{


    @Override
    protected String doInBackground(String... urls) {

        String result = "";
        URL url;
        HttpURLConnection urlConnection = null;

        try {
            url = new URL (urls[0]);
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
        } catch (IOException e) {
            e.printStackTrace();
        }



        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        String message = "";
        try {
            //Array with the JSON data Stations from Dublin Bikes
            JSONArray jsonArray = new JSONArray(result);


            for (int i=0; i<jsonArray.length(); i++){
                //Object inside the array to retrieve the data

                //Station Number
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String number = jsonObject.optString("number");
                System.out.println(number);

                //Station Name
                String name = jsonObject.optString("name");
                System.out.println(name);

                //Get LatLong
                JSONObject jPos = jsonObject.getJSONObject("position");
                String lat = jPos.optString("lat");
                System.out.println(lat);
                String lng = jPos.optString("lng");
                System.out.println(lng);





            }




        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
    }

*/
}
