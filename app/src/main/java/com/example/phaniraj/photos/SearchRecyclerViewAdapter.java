package com.example.phaniraj.photos;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

class SearchRecyclerViewAdapter extends  RecyclerView.Adapter<SearchRecyclerViewAdapter.ImageViewHolder>{
    private ArrayList<Bitmap> imageBitmaps;
    private ArrayList<String> imageTitles;

    SearchRecyclerViewAdapter(ArrayList<Bitmap> imageBitmap, ArrayList<String> imageTitle) {
        this.imageBitmaps = imageBitmap;
        this.imageTitles = imageTitle;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.recyclerview_item, parent, false);
        return new ImageViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder imageViewHolder, int i) {
        try {
            imageViewHolder.imageTitle.setText(imageTitles.get(i));
            imageViewHolder.imageView.setImageBitmap(imageBitmaps.get(i));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return imageTitles.size();
    }

    static class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView imageTitle;

        ImageViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView)itemView.findViewById(R.id.imageView);
            imageTitle = (TextView)itemView.findViewById(R.id.imageTitle);
        }
    }
}
