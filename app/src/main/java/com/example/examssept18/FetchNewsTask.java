package com.example.examssept18;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class FetchNewsTask extends AsyncTask<String,Void,ArrayList<Article>> {

    private final String LOG_TAG = FetchNewsTask.class.getSimpleName();
    

    public static final String SERVICE_BASE_URL = "https://newsapi.org/v2/top-headlines";

    private  NewsAdapter newsAdapter;
    private ArrayList<Article> articles = new ArrayList<>() ;
    public FetchNewsTask(NewsAdapter newsAdapter){
       //should keep newsAdapter as a field. Do it!
        this.newsAdapter = newsAdapter;
    }
    @Override
    protected ArrayList<Article> doInBackground(String... params) {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        String articleJsonString = null;

        try {
        
            final String ARTICLES_URL  = "?country=gr&category=technology&apiKey=ff0a09a8b64c48a6bd1c3cbbf08052ac";

            Uri builtUri = Uri.parse(SERVICE_BASE_URL + ARTICLES_URL);


            URL url = new URL(builtUri.toString());
            

            // Create the request to Yummy Wallet server, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
            articleJsonString = buffer.toString();
            Log.d("art" , articleJsonString);
            return  getArticlesFromJSON(articleJsonString);
            
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);
            // If the code didn't successfully get the data, there's no point in attempting to parse it.
        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<Article> articles) {
        if(articles.size() > 0){
            this.newsAdapter.clear();
            for (Article article : articles){
                newsAdapter.add(article);
            }
        }
    }
    private ArrayList<Article> getArticlesFromJSON(String responseJsonStr) throws JSONException {

        try{

            JSONObject response = new JSONObject(responseJsonStr);
            JSONArray articlesJSON = response.getJSONArray("articles");

            for (int i=0; i<articlesJSON.length(); i++){
                JSONObject articleJSON = articlesJSON.getJSONObject(i);
                String sourceName = articleJSON.getJSONObject("source").getString("name");
                String description = articleJSON.getString("description");
                String title = articleJSON.getString("title");
                String url = articleJSON.getString("url");

                Article article = new Article(sourceName,title,description,url);

                articles.add(article);

                Log.d("GK", article.toString());
            }
            //parse json and create articles

            Log.d(LOG_TAG, "Article Fetching Complete. " + articles.size() + "articles inserted");

            return  articles; //actually here should return articles

        }catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
            return  articles;
        }
    }
}