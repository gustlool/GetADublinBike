//Gustavo L de Oliveira - 15647

package com.example.gustavo.getadublinbike;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

public class MainActivity extends AppCompatActivity {

    ArrayList<Double> lat = new ArrayList<Double>();
    ArrayList<Double> lng = new ArrayList<Double>();

    //ArrayList<String> posLatLng = new ArrayList<String>();
    ArrayList<String> stations = new ArrayList<String>();
    Bundle b = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DownloadTask task = new DownloadTask();

        String result = null;
        task.execute("https://api.jcdecaux.com/vls/v1/stations?contract=Dublin&apiKey=4a9827a5bfe6858c7f8b940877d1b78d03608e57");





    }



    //Download task
    public class DownloadTask extends AsyncTask<String, Void, String> {


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

                    //Station status
                    String status = jsonObject.optString("status");
                    System.out.println(name);

                    //Bikes Available
                    String bikesAv = jsonObject.optString("available_bikes");
                    System.out.println(bikesAv);

                    //Stands Available
                    String standAv = jsonObject.optString("available_bike_stands");
                    System.out.println(standAv);

                    //Get LatLong
                    JSONObject jPos = jsonObject.getJSONObject("position");
                    lat.add(jPos.getDouble("lat"));
                    System.out.println(lat);
                    lng.add(jPos.getDouble("lng"));
                    System.out.println(lng);

                    String list = number + " " + name + "\n" + "Status: "+ status +"\n" + "Bikes Available: " + bikesAv + "\n" + "Stands Available: " + standAv ;

                    stations.add(list);



                }




            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }



   public void goToListStations (View view){

       //Intent for the list of bike stations
        Intent stationsIntent = new Intent (this, Stations.class);
        b.putStringArrayList("stationsArr", (ArrayList<String>) stations);
        stationsIntent.putExtras(b);
        startActivity(stationsIntent);


    }

    public void goToCheckWeather(View view){
        //Intent for local Weather Forecast
        Intent weatherIntent = new Intent (this, WeatherActivity.class);
        startActivity(weatherIntent);

    }

    public void goToSeeMap (View view){
        //Intent for map location of stations
        Intent mapIntent = new Intent (this, MapsActivity.class);
        b.putStringArrayList("stationsArr", (ArrayList<String>) stations);
        mapIntent.putExtras(b);
        startActivity(mapIntent);

    }

    public void goToAbout (View view){
        //Intent for about of this app
        Intent aboutIntent = new Intent (this, About.class);
        startActivity(aboutIntent);


    }
}
