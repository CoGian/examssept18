package com.example.examssept18;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class NewsAdapter extends ArrayAdapter<Article> {
    private ArrayList<Article> articles;
    private Context context;


    public NewsAdapter(Context context, ArrayList<Article> objects) {
        super(context, 0, objects);
        this.articles = objects;
        this.context = context;
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;

        ///fetch article from 'position'.  how?
        Article article = articles.get(position);
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        rowView = inflater.inflate(R.layout.list_item_article, null);
        
       //get and update textViews. how?
        TextView titleView = rowView.findViewById(R.id.title_txtview);
        TextView sourceView = rowView.findViewById(R.id.sourceName_txtview);
        TextView descriptionView = rowView.findViewById(R.id.descr_txtview);

        titleView.setText(article.getTitle());
        sourceView.setText(article.getSourceName());
        descriptionView.setText(article.getDescription());
        return  rowView;
    }

}