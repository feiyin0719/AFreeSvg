/*
 * Copyright (c) 2022.  by iffly Limited.  All rights reserved.
 * This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 */

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

