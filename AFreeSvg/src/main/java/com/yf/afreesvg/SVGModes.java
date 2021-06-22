package com.yf.afreesvg;

import androidx.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class SVGModes {
    public static final String MODE_BOX = "objectBoundingBox";
    public static final String MODE_USERSPACE = "userSpaceOnUse";

    @StringDef({MODE_BOX, MODE_USERSPACE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface POS_MODE {
    }
}
