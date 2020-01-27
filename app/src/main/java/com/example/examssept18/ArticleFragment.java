package com.example.examssept18;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;


public class ArticleFragment extends Fragment {

    NewsAdapter newsAdapter;

    public ArticleFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        newsAdapter = new NewsAdapter(getContext(), new ArrayList<Article>());
    }

    @Override
    public void onStart() {
        super.onStart();
        fetchArticles();
    }

    private void fetchArticles(){
        FetchNewsTask fetchNewsTask = new FetchNewsTask(newsAdapter);
        fetchNewsTask.execute();
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
                             
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        
       //...... newsAdapter = 
        ListView newsListView = (ListView)rootView.findViewById(R.id.listview_articles);
        newsListView.setAdapter(newsAdapter);
       //......
     
        //Extra bonus to whoever manages to open the url of the article with the webviewer

        return rootView;
    }
}