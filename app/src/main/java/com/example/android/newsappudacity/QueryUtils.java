package com.example.android.newsappudacity;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class QueryUtils {
    private static final String LOG_TAG = QueryUtils.class.getSimpleName();
    private QueryUtils() {
    }
    private static URL createUrl(String queryUrl, Context context) {
        URL url = null;
        try {
            url = new URL(queryUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "PROBLEM GETTING THE HTTP REQ", e);
        }
        return url;
    }
    public static List<Newconstructor> fetchNewsData(String contenturl, Context context) {
        URL url = createUrl(contenturl, context);
        String jsonResponse =null;
        try{
            jsonResponse = makeHttpRequest(url,context);
        }catch (IOException e){
            Log.e(LOG_TAG,"problem making the http request",e);
        }
        return extractNews(jsonResponse,context);
    }

    private static String makeHttpRequest(URL url, Context context) throws IOException {
        String jsonResponse = "";
        if (url == null) {
            return jsonResponse;
        }
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream, context);
            } else {
                Log.e(LOG_TAG, "error respose code" + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "problem in retriving the data", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }
    private static String readFromStream(InputStream inputStream, Context context) throws IOException {
        StringBuilder outputString = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader isr = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader br = new BufferedReader(isr);
            String currentLine = br.readLine();
            while (currentLine != null) {
                outputString.append(currentLine);
                currentLine = br.readLine();
            }
        }
        return outputString.toString();
    }
    private static List<Newconstructor> extractNews(String jsonResponse, Context context){
        if (TextUtils.isEmpty(jsonResponse)) {
            return null;
        }
        List<Newconstructor> news = new ArrayList<>();
            try{
                JSONObject baseJsonResponse = new JSONObject(jsonResponse);
                if(baseJsonResponse.has("response")){
                    JSONObject responseJsonobject= baseJsonResponse.getJSONObject("response");
                    if(responseJsonobject.has("results")){
                        JSONArray resultJsonarray = responseJsonobject.getJSONArray("results");
                        for(int i=0;i<resultJsonarray.length(); i++){
                            JSONObject currentJsonobject = resultJsonarray.getJSONObject(i);
                            String tiTle;
                            if(currentJsonobject.has("webTitle")){
                                tiTle=currentJsonobject.getString("webTitle");
                            }else{
                                tiTle=null;
                            }
                            String discRiption;
                            if(currentJsonobject.has("sectionName")){
                                discRiption=currentJsonobject.getString("sectionName");
                            }else {
                                discRiption=null;
                            }
                            String dateandtime;
                            if(currentJsonobject.has("webPublicationDate")) {
                                dateandtime = currentJsonobject.getString("webPublicationDate");
                            }else{
                                dateandtime = null;
                            }
                            String webUrl;
                            if(currentJsonobject.has("webUrl")) {
                                webUrl = currentJsonobject.getString("webUrl");
                            }else{
                                webUrl = null;
                            }
                            news.add(new Newconstructor(tiTle, discRiption, dateandtime, webUrl));
                        }
                    }
                }

            } catch (JSONException e) {
                Log.e(LOG_TAG, "problem", e);
            }
            return news;
    }
}
