package com.example.allan.newsapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {
    ListView list;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list = (ListView) findViewById(R.id.list);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        Log.v("ONCREATE", "ACTIVATED");
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        if (info == null || !info.isConnected()) {
            Toast.makeText(MainActivity.this, "No Internet Connection.", Toast.LENGTH_SHORT).show();
        } else {
            getSupportLoaderManager().initLoader(0, null, MainActivity.this).forceLoad();
        }
    }

    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        Log.v("ONCREATELOADER", "ACTIVATED");
        progressBar.setVisibility(View.VISIBLE);
        list.setVisibility(View.INVISIBLE);
        return new NewsAsyncTaskLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
        if (data != null && !data.isEmpty()) {
            UpdateUI(data);
        } else {
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
        progressBar.setVisibility(View.INVISIBLE);
        list.setVisibility(View.VISIBLE);
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {
    }

    public void UpdateUI(String s) {
        try {
            JSONObject root = new JSONObject(s);
            JSONObject response = root.getJSONObject("response");
            JSONArray results = response.getJSONArray("results");
            final ArrayList<News> newsArrayList = new ArrayList<News>();
            for (int i = 0; i < results.length(); i++) {
                JSONObject result = results.getJSONObject(i);
                String webTitle;
                if (result.has("webTitle")) {
                    webTitle = result.getString("webTitle");
                } else {
                    webTitle = "NONE";
                }
                String webPublicationDate;
                if (result.has("webPublicationDate")) {
                    webPublicationDate = result.getString("webPublicationDate");
                } else {
                    webPublicationDate = "Unknown";
                }
                String sectionName;
                if (result.has("sectionName")) {
                    sectionName = result.getString("sectionName");
                } else {
                    sectionName = "Other";
                }
                String webURL;
                if (result.has("webUrl")) {
                    webURL = result.getString("webUrl");
                    JSONObject fields;
                    if (result.has("fields")) {
                        fields = result.getJSONObject("fields");
                        String byline;
                        if (fields.has("byline")) {
                            byline = fields.getString("byline");
                        } else {
                            byline = "Unknown";
                        }
                        String thumbnail;
                        if (fields.has("thumbnail")) {
                            thumbnail = fields.getString("thumbnail");
                            News currentNews = new News(webTitle, sectionName, webPublicationDate, webURL, thumbnail, byline);
                            newsArrayList.add(currentNews);
                        }
                    }
                }
            }
            NewsAdapter adapter = new NewsAdapter(this, R.layout.news_layout, newsArrayList);
            list.setAdapter(adapter);
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    String URL = newsArrayList.get(i).getWebURL();
                    intent.setData(Uri.parse(URL));
                    startActivity(intent);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh:
                getSupportLoaderManager().restartLoader(0, null, MainActivity.this).forceLoad();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
