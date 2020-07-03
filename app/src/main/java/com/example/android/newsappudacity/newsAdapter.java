package com.example.android.newsappudacity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ParseException;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.List;

public class newsAdapter extends ArrayAdapter<Newconstructor> {
    private static final String LOG_TAG = newsAdapter.class.getName();
    private Context context;
    private static final String TAG = "newsAdapter";


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
            Log.e(TAG, "getView:----------------------- " + currentdateandtime);

            if(currentdateandtime!=null){

                    String date= formattedDate(currentdateandtime);
                    contentDate.setText(date);
                    contentDate.setVisibility(View.VISIBLE);
                    contenttime.setVisibility(View.VISIBLE);

                }
            else {
                contentDate.setText("NO DATE EXIST");
                contenttime.setText("NO TIME EXIST");
            }

        }
        return listItemView;
    }
    private String formattedDate(String utcTime) {
        Date date = null;
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        try {
            date = format.parse(utcTime.replaceAll("Z$", "+0000"));
        } catch (ParseException | java.text.ParseException e) {
            e.printStackTrace();
        }
        assert date != null;
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        @SuppressLint("SimpleDateFormat") SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return "DATE:-"+dateFormat.format(date) + "\n" +"TIME:-"+ timeFormat.format(date);
    }

}
