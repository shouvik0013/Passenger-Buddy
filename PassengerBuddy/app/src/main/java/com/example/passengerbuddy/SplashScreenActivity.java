package com.example.passengerbuddy;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.EventLogTags;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class SplashScreenActivity extends AppCompatActivity implements Animation.AnimationListener {
    // PHP FILE WHICH RETURNS STATION NAME AND STATION CODE
    private final String fetchStationInfoUrl = "http://3.18.81.69/AWSUpload/fetchStationInfo3.php";
    // ARRAYLIST WHICH STORES STATION NAMES AND STATION CODES
    ArrayList<String> stationNameStationCodeList = new ArrayList<String>();
    Animation fadeIn;
    private int sleepCounter = 3;   // VARIABLE TO SET THE DURATION OF THE ANIMATION
    ImageView imageView;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        getSupportActionBar().hide();
        fadeIn = new AlphaAnimation(0.0F, 1.0F);    // ALPHA ANIMATION WHICH APPEARS FROM WITHIN THE SCREEN
        fadeIn.setDuration(1000 * sleepCounter);   // SETTING DURATION OF THE ANIMATION
        fadeIn.setAnimationListener(this);
        imageView = (ImageView) findViewById(R.id.splashImage);
        imageView.startAnimation(fadeIn);


    }


    public void checkAndDownload(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo == null){
            Toast.makeText(this, "No internet", Toast.LENGTH_LONG).show();
            finish();
        } else{
            new DownloadStationList().execute(fetchStationInfoUrl);
        }
    }

    @Override
    public void onAnimationStart(Animation animation) {
        checkAndDownload();
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        while(intent == null){

        }
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_left);
        SplashScreenActivity.this.finish();
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }


    // CLASS WHICH DOWNLOADS SUGGESTION FOR THE AUTOCOMPLE TEXTVIEW
    // INTO JSON STRING
    public class DownloadStationList extends AsyncTask<String, Void, String> {
        URL url;
        HttpURLConnection httpURLConnection;
        InputStream inputStream;
        StringBuffer buffer = new StringBuffer();


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        protected String doInBackground(String... strings) {
            try{
                url = new URL(strings[0]);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while((line=bufferedReader.readLine()) != null){
                    buffer.append(line + "\n");
                }
            } catch (MalformedURLException exc){
                System.out.println("Inside MalformedURL.");
                System.out.println(exc.getMessage());
            } catch(IOException exc){
                System.out.println("Inside IOException.");
                System.out.println(exc.getMessage());
            }

            return buffer.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            // GETTING STATION NAMES FROM JSON STRING
            try{
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("server_response");
                for(int i=0; i<jsonArray.length(); i++){
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    stationNameStationCodeList.add(jsonObject1.getString("station"));
                }
                for (String name:
                        stationNameStationCodeList) {
                    System.out.println(name);
                }
                final String[] listStation = new String[stationNameStationCodeList.size()];
                for(int i=0; i<stationNameStationCodeList.size(); i++){
                    listStation[i] = stationNameStationCodeList.get(i);
                }
                // Implement Code


                /*
                * This intent has the listStation String array which contains names of
                * the stations from the stations table. This listStation helps autocompleteTextViews
                * in the MainActivity to show up station name suggestions
                * */
                intent = new Intent(getApplicationContext(), LoginActivity.class); // POINTS TOWARDS MAINACTIVITY
                intent.putExtra("StationList", listStation);
                /*startActivity(intent);
                SplashScreenActivity.this.finish();*/
            } catch (JSONException exc){
                System.out.println("JsonException block.");
                System.out.println(exc.getMessage());
            }
        }
    }
}
