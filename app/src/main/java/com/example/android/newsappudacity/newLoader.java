package com.example.android.newsappudacity;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import java.util.List;

public class newLoader extends AsyncTaskLoader<List<Newconstructor>> {
    private String url;

    public newLoader(Context context, String guardiansRequestUrl) {
        super(context);
        this.url= guardiansRequestUrl;
    }
    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Nullable
    @Override
    public List<Newconstructor> loadInBackground() {
        if (url == null) {
            return null;
        }
        return QueryUtils.fetchNewsData(url, getContext());
    }
}
