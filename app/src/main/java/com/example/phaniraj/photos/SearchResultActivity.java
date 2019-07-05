package com.example.phaniraj.photos;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import java.net.URL;
import java.util.ArrayList;

public class SearchResultActivity extends AppCompatActivity {
        private RecyclerView vertcalRecyclerView,horizontalRecyclerView;
        private ArrayList<Bitmap> imageBitmap;
        private ArrayList<String> imageTitle;
        private SearchRecyclerViewAdapter adapter;
        private ArrayList<String> imageUrls;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_search_results);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            vertcalRecyclerView = (RecyclerView)findViewById(R.id.vertical_recyclerView);
            horizontalRecyclerView = (RecyclerView) findViewById(R.id.horizontal_recyclerView);

            imageBitmap = new ArrayList<>();
            imageTitle = (ArrayList<String>) getIntent().getSerializableExtra("Titles");
            imageUrls = (ArrayList<String>) getIntent().getSerializableExtra("Urls");

            adapter = new SearchRecyclerViewAdapter(imageBitmap,imageTitle);
            vertcalRecyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),2,LinearLayoutManager.VERTICAL,false));
            vertcalRecyclerView.setAdapter(adapter);

            horizontalRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false));
            horizontalRecyclerView.setAdapter(adapter);

            for (int i=0;i<imageUrls.size();i++){
              new GetImageBitmap(i).execute(imageUrls.get(i));
            }

        }

    private class GetImageBitmap extends AsyncTask<String, Integer, String> {
        int i;
         GetImageBitmap(int i) {
            this.i = i;
        }

        public String doInBackground(String... urls) {
            try {
                URL url = new URL(urls[0]);
                imageBitmap.add(BitmapFactory.decodeStream(url.openConnection().getInputStream()));
            } catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }
        public void onPostExecute(String result) {
            adapter.notifyItemChanged(i);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
       onBackPressed();
        return true;
    }
}
