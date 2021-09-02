package com.yf.afreesvg;

import androidx.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 * Pos mode
 * Coordinate mode,
 * {@link #MODE_USERSPACE} is global coordinates.
 * {@link #MODE_BOX} is relative to the coordinates of the reference object
 *
 * @since 0.0.1
 */

@StringDef({PosMode.MODE_BOX, PosMode.MODE_USERSPACE})
@Retention(RetentionPolicy.SOURCE)
public @interface PosMode {
    String MODE_BOX = "objectBoundingBox";
    String MODE_USERSPACE = "userSpaceOnUse";
}

