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

package com.yf.afreesvg.shape;

import androidx.annotation.NonNull;

import com.yf.afreesvg.ConvertToSVGElement;
import com.yf.afreesvg.SVGCanvas;

/**
 * The shape interface
 *
 * @author yinfei
 * @since 0.0.1
 */
public interface SVGShape extends ConvertToSVGElement, Cloneable {
    /**
     * clone
     * need deep clone when push in save stacks,{@link SVGCanvas#save()}
     *
     * @return
     */
    @NonNull
    public Object clone();
}
