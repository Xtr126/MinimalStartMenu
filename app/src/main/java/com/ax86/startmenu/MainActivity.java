package com.ax86.startmenu;

import android.app.Activity;
import android.os.Bundle;

import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends Activity {

    // Use a rectangular shape drawable for the window background. The outline of this drawable
    // dictates the shape and rounded corners for the window background blur area.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_profiles_apps);
        AppsGridAdapter adapter = new AppsGridAdapter(this);
        RecyclerView recyclerView = findViewById(R.id.apps_grid);
        recyclerView.setAdapter(adapter);
    }

}