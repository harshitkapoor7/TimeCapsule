package com.example.timecapsule;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class Adapter extends ArrayAdapter {
    List<String> et;
    Activity a;

        public Adapter(Activity a, List<String> et)
            {
                super(a,R.layout.list);
                this.a=a;
                this.et=et;
            }
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView,@NonNull ViewGroup parent){
                LayoutInflater inflater=a.getLayoutInflater();
                View rowView=inflater.inflate(R.layout.list,null,true);
                TextView name=rowView.findViewById(R.id.textView);
                name.setText(et.get(position));
                return rowView;
        }


}
