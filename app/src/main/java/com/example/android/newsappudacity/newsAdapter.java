package com.example.android.newsappudacity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ParseException;
import android.os.TestLooperManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.TimeZone;

public class newsAdapter extends ArrayAdapter<Newconstructor> {
    private static final String LOG_TAG = newsAdapter.class.getName();
    private Context context;


    public newsAdapter(Context context, List<Newconstructor> newconstructors) {
        super(context,0,newconstructors);
        this.context= context;
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.single_list_view, parent, false);
        }
        Newconstructor currentnews= getItem(position);
        if(currentnews!=null){
            TextView contenttitle=listItemView.findViewById(R.id.Title);
            String title=currentnews.getTitle();
            if(title!=null){
                contenttitle.setText(title);
            }else {
                contenttitle.setText("TITLE NOT EXIST");
            }
            TextView contentdiscription= listItemView.findViewById(R.id.Discription);
            String discrption= currentnews.getDiscription();
            if(discrption!=null){
                contentdiscription.setText(discrption);
            }else{
                contentdiscription.setText("DISCRIPTION NOT EXIST");
            }
            TextView contentDate=listItemView.findViewById(R.id.date);
            TextView contenttime=listItemView.findViewById(R.id.TIme);
            String currentdateandtime=currentnews.getWebdateandtime();
            if(currentdateandtime!=null){
                try{
                    String date= getdate(currentdateandtime);
                    String time= gettime(currentdateandtime);
                    contentDate.setText(date);
                    contentDate.setVisibility(View.VISIBLE);
                    contenttime.setText(time);
                    contenttime.setVisibility(View.VISIBLE);

                }catch (java.text.ParseException e){
                    Log.e(LOG_TAG,"PROBLEM IN DATE AND TIME",e);
                }
            }else {
                contentDate.setText("NO DATE EXIST");
                contenttime.setText("NO TIME EXIST");
            }

        }
        return listItemView;
    }
    private String getdate(String currentDateAndTime) throws java.text.ParseException {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdfDate = new SimpleDateFormat("LLL dd, yyy");
        return sdfDate.format(Objects.requireNonNull(sdf.parse(currentDateAndTime)));
    }

    private String gettime(String currentDateAndTime) throws java.text.ParseException {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdfTime = new SimpleDateFormat("h:mm a");
        sdfTime.setTimeZone(TimeZone.getTimeZone("UTC"));
        return sdfTime.format(Objects.requireNonNull(sdf.parse(currentDateAndTime)));
    }

}
