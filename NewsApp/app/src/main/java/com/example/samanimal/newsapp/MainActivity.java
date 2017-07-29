package com.example.samanimal.newsapp;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    static final String TAG = "MainActivity";
    private EditText search;
    private ProgressBar progress;
    private TextView textView;
    private RecyclerView rv;
    private String s = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        search = (EditText) findViewById(R.id.searchQuery);
        progress = (ProgressBar) findViewById(R.id.progressBar);
        textView = (TextView) findViewById(R.id.displayJSON);
        rv = (RecyclerView)findViewById(R.id.recyclerView);
        rv.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        NetworkTask net = null;

        switch (item.getItemId()) {
            case R.id.refresh:
                Log.i("Menu", "Refresh clicked");
                s = "";
                //net = new NetworkTask(""); // no query string, will refresh page
                //net.execute();
                return true;
            case R.id.search:
                Log.i("Menu", "Search clicked");
                s = "54a9ccbaf0874645903d2c513cfd9be6";
                //net = new NetworkTask("54a9ccbaf0874645903d2c513cfd9be6"); // news api key as query string
                //net.execute();
                return true;
        }
        return super.onOptionsItemSelected(item);

    }

    class NetworkTask extends AsyncTaskLoader<ArrayList<NewsItem>> {

        String query;
        ArrayList<NewsItem> mData;

        public NetworkTask(Context context, String s) {
            super(context);
            query = s;
        }

        @Override
        protected void onStartLoading() {

            progress.setVisibility(View.VISIBLE);
            if (mData != null) {
                // used cached data
                deliverResult(mData);
            }

        }


        @Override
        public ArrayList<NewsItem> loadInBackground() {
            ArrayList<NewsItem> result = null;
            URL url = NetworkUtils.buildUrl(query);
            Log.d(TAG, "url: " + url.toString());
            try {
                String json = NetworkUtils.getResponseFromHttpUrl(url);
                result = NetworkUtils.parseJSON(json);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        public void deliverResult( final ArrayList<NewsItem> data) {
            super.deliverResult(data);
            progress.setVisibility(View.GONE);
            if (data != null) {
                NewsAdapter adapter = new NewsAdapter(data, new NewsAdapter.ItemClickListener(){

                    @Override
                    public void onItemClick(int clickedItemIndex){ // add code here to finish

                            String url = data.get(clickedItemIndex).getUrl();
                            Log.d(TAG, String.format("Url %s", url));
                            openWebPage(url);
                        }
                });
                rv.setAdapter(adapter);
            }
        }

        public void openWebPage(String url) {
            Uri webpage = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
        }
    }
}
