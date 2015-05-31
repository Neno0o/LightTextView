package com.neno0o.lighttextviewlib;

import android.content.Context;

public class Util {

    public static int pixelFromDp(float dip, Context context) {
        return (int) (dip * context.getResources().getDisplayMetrics().density);
    }
}
