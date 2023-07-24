package com.ax86.startmenu;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AppsGridAdapter extends RecyclerView.Adapter<AppsGridAdapter.RecyclerViewHolder> {

    private final ArrayList<RecyclerData> appsDataArrayList = new ArrayList<>();
    private final Context context;

    public AppsGridAdapter(Context context) {
        this.context = context;
        PackageManager pm = context.getPackageManager();
        Intent i = new Intent(Intent.ACTION_MAIN, null);
        i.addCategory(Intent.CATEGORY_LAUNCHER);

        List<ResolveInfo> allApps = pm.queryIntentActivities(i, 0);

        for (ResolveInfo ri : allApps)
            appsDataArrayList.add(new RecyclerData(
                    ri.activityInfo.packageName,
                    ri.loadLabel(pm),
                    ri.activityInfo.loadIcon(pm)));
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate Layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.app_view, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        // Set the data to textview and imageview.
        RecyclerData recyclerData = appsDataArrayList.get(position);
        holder.appName.setText(recyclerData.title);
        holder.appIcon.setImageDrawable(recyclerData.icon);
    }

    @Override
    public int getItemCount() {
        // this method returns the size of recyclerview
        return appsDataArrayList.size();
    }

    // View Holder Class    to handle Recycler View.
    public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final TextView appName;
        public final ImageView appIcon;

        public RecyclerViewHolder(View view) {
            super(view);
            appName = view.findViewById(R.id.app_name);
            appIcon = view.findViewById(R.id.app_icon);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int i = getAbsoluteAdapterPosition();
            String packageName = appsDataArrayList.get(i).packageName;
            Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage(packageName);
            context.startActivity(launchIntent);
            ((MainActivity)context).finish();
        }
    }

    private static class RecyclerData {
        public RecyclerData(String packageName, CharSequence title, Drawable icon) {
            this.packageName = packageName;
            this.icon = icon;
            this.title = title;
        }

        String packageName;
        CharSequence title;
        Drawable icon;
    }
}
