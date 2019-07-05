package com.example.phaniraj.photos;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private String SearchAPIKey = "AIzaSyA_FJMRLMTsocQeHDzt5DBze4r1oR51_YI";
    private String SearchEngineId = "009600544099514143307:vww78n5qpgy";
    private String SearchURL = "https://www.googleapis.com/customsearch/v1?key="+SearchAPIKey+"&cx="+SearchEngineId+"&searchType=image";
    private EditText searchText;
    private Button goButton;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchText = (EditText)findViewById(R.id.editText);
        goButton = (Button) findViewById(R.id.goButton);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (searchText.getText().length()!=0) {
                    progressBar.setVisibility(View.VISIBLE);
                    new SearchForPhotos().execute(SearchURL + "&q=" + searchText.getText().toString());
                    goButton.setEnabled(false);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        goButton.setEnabled(true);
        super.onResume();
    }

    private class SearchForPhotos extends AsyncTask<String, Integer, String> {
        public String doInBackground(String... urls) {
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                BufferedReader r = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder total = new StringBuilder();
                for (String line; (line = r.readLine()) != null; ) {
                    total.append(line).append('\n');
                }
                return String.valueOf(total);
            } catch (Exception e){
                return null;
            }
        }
         public void onPostExecute(String result) {
             try {
                 if (result!=null) {
                     JSONObject jsonObject = new JSONObject(result);
                     JSONArray itemArray = jsonObject.getJSONArray("items");
                     ArrayList<String> imageTitles = new ArrayList<>();
                     ArrayList<String> imageUrls = new ArrayList<>();
                     for (int i = 0; i < itemArray.length(); i++) {
                         JSONObject jsonObject1 = itemArray.getJSONObject(i);
                         imageTitles.add(jsonObject1.getString("title"));
                         imageUrls.add(jsonObject1.getJSONObject("image").getString("thumbnailLink"));
                     }

                     Intent intent = new Intent(MainActivity.this, SearchResultActivity.class);
                     intent.putExtra("Titles", imageTitles);
                     intent.putExtra("Urls", imageUrls);
                     MainActivity.this.startActivity(intent);
                 }
                 else {
                     goButton.setEnabled(true);
                 }
                 progressBar.setVisibility(View.GONE
                 );
             } catch (JSONException e) {
                 e.printStackTrace();
             }

        }
    }
}
