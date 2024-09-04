package com.ax86.startmenu;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import androidx.recyclerview.widget.RecyclerView;

import java.util.function.Consumer;

public class MainActivity extends Activity {

    private final int mBackgroundBlurRadius = 20;

    // Use a rectangular shape drawable for the window background. The outline of this drawable
    // dictates the shape and rounded corners for the window background blur area.
    private Drawable mWindowBackgroundDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_profiles_apps);
        AppsGridAdapter adapter = new AppsGridAdapter(this);
        RecyclerView recyclerView = findViewById(R.id.apps_grid);
        recyclerView.setAdapter(adapter);

        // Dismiss start menu when user clicks outside
        View view = findViewById(R.id.root_view);
        view.setOnClickListener(v -> this.finish());

        mWindowBackgroundDrawable = getDrawable(R.drawable.window_background);
        getWindow().setBackgroundDrawable(mWindowBackgroundDrawable);

        setupWindowBlurListener();

    }

    private void setupWindowBlurListener() {
        Consumer<Boolean> windowBlurEnabledListener = this::updateWindowForBlurs;
        getWindow().getDecorView().addOnAttachStateChangeListener(
                new View.OnAttachStateChangeListener() {
                    @Override
                    public void onViewAttachedToWindow(View v) {
                        getWindowManager().addCrossWindowBlurEnabledListener(
                                windowBlurEnabledListener);
                    }

                    @Override
                    public void onViewDetachedFromWindow(View v) {
                        getWindowManager().removeCrossWindowBlurEnabledListener(
                                windowBlurEnabledListener);
                    }
                });
    }

    private void updateWindowForBlurs(boolean blursEnabled) {
            // Set the window background blur and blur behind radii
            getWindow().setBackgroundBlurRadius(mBackgroundBlurRadius);
    }

}