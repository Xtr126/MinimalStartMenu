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

    private final int mBackgroundBlurRadius = 80;
    private final int mBlurBehindRadius = 150;

    // We set a different dim amount depending on whether window blur is enabled or disabled
    private final float mDimAmountWithBlur = 0.6f;
    private final float mDimAmountNoBlur = 0.8f;

    // We set a different alpha depending on whether window blur is enabled or disabled
    private final int mWindowBackgroundAlphaWithBlur = 170;
    private final int mWindowBackgroundAlphaNoBlur = 255;

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

        // Enable blur behind. This can also be done in xml with R.attr#windowBlurBehindEnabled
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        getWindow().setGravity(Gravity.BOTTOM);

        // Register a listener to adjust window UI whenever window blurs are enabled/disabled
        setupWindowBlurListener();

        // Enable dim. This can also be done in xml, see R.attr#backgroundDimEnabled
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
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
        mWindowBackgroundDrawable.setAlpha(blursEnabled && mBackgroundBlurRadius > 0 ?
                mWindowBackgroundAlphaWithBlur : mWindowBackgroundAlphaNoBlur);
        getWindow().setDimAmount(blursEnabled && mBlurBehindRadius > 0 ?
                mDimAmountWithBlur : mDimAmountNoBlur);

            // Set the window background blur and blur behind radii
            getWindow().setBackgroundBlurRadius(mBackgroundBlurRadius);
            getWindow().getAttributes().setBlurBehindRadius(mBlurBehindRadius);
            getWindow().setAttributes(getWindow().getAttributes());
    }

}