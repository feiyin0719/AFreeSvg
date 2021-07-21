package com.yf.afreesvg;

import androidx.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * SVG mode class
 * It provide svg mode constants
 *
 * @author iffly
 * @since 0.0.1
 */
public class SVGModes {
    public static final String MODE_BOX = "objectBoundingBox";
    public static final String MODE_USERSPACE = "userSpaceOnUse";

    /**
     * Pos mode
     * Coordinate mode,
     * {@link #MODE_USERSPACE} is global coordinates.
     * {@link #MODE_BOX} is relative to the coordinates of the reference object
     *
     * @since 0.0.1
     */
    @StringDef({MODE_BOX, MODE_USERSPACE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface POS_MODE {
    }
}
