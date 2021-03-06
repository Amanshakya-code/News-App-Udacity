package com.example.android.newsappudacity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Newconstructor>> {

    private static final int NEWS_LOADER_ID = 1;
    private static final String GUARDIAN_KEY = "fd984008-1416-4e41-9a95-f0044f48f5f2";
    private static final String GUARDIANS_REQUEST_URL = "https://content.guardianapis.com/search?&show-tags=contributor&api-key=1205bd93-b6c3-4f90-9eac-eba3ff2c2b02";
    private newsAdapter mAdapter;
    private TextView mEmptyStateTextView;
    private View loadingIndicator;
    private ListView newsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEmptyStateTextView = findViewById(R.id.empty_view);
        loadingIndicator = findViewById(R.id.loading_indicator);
        newsListView = findViewById(R.id.list);
        mAdapter = new newsAdapter(this, new ArrayList<Newconstructor>());
        newsListView.setAdapter(mAdapter);
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (cm != null) {
            networkInfo = cm.getActiveNetworkInfo();
        }
        if (networkInfo != null && networkInfo.isConnected()) {
            getSupportLoaderManager().initLoader(NEWS_LOADER_ID, null, this);
        } else {
            loadingIndicator.setVisibility(View.GONE);
            mEmptyStateTextView.setText(getString(R.string.no_internet));
            mEmptyStateTextView.setVisibility(View.VISIBLE);
        }
        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Newconstructor currentNews = mAdapter.getItem(i);
                if (currentNews != null) {
                    String webUrl = currentNews.getUrl();
                    Intent webIntent = new Intent(Intent.ACTION_VIEW);
                    if (webUrl != null) {
                        webIntent.setData(Uri.parse(webUrl));
                    } else {
                        webIntent.setData(Uri.parse(GUARDIANS_REQUEST_URL));
                    }
                    if (webIntent.resolveActivity(getPackageManager()) != null) {

                        startActivity(webIntent);

                    }
                }
            }
        });
    }

    @NonNull
    @Override
    public Loader<List<Newconstructor>> onCreateLoader(int id, @Nullable Bundle args) {
        Uri baseUri= Uri.parse(GUARDIANS_REQUEST_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();
        uriBuilder.appendQueryParameter("show-tags", "contributor");
        uriBuilder.appendQueryParameter("format", "json");
        uriBuilder.appendQueryParameter("order-by", "newest");
        uriBuilder.appendQueryParameter("page-size", "50");
        return new newLoader(this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<Newconstructor>> loader, List<Newconstructor> data) {
        loadingIndicator.setVisibility(View.GONE);
        mEmptyStateTextView.setText(getString(R.string.nonews));
        newsListView.setEmptyView(mEmptyStateTextView);
        mAdapter.clear();
        if (data != null && !data.isEmpty()) {
            mAdapter.addAll(data);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<Newconstructor>> loader) {
        mAdapter.clear();
    }
}
