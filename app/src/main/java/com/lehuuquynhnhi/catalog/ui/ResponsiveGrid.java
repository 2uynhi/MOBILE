package com.lehuuquynhnhi.catalog.ui;

import android.content.Context;
import android.util.DisplayMetrics;

public final class ResponsiveGrid {
    private ResponsiveGrid() {
    }

    public static int spanCount(Context context, int itemMinWidthDp) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        int widthDp = (int) (metrics.widthPixels / metrics.density);
        return Math.max(1, widthDp / itemMinWidthDp);
    }
}
