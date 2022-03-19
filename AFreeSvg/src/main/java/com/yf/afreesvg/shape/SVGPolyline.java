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

import android.graphics.PointF;

import androidx.annotation.NonNull;

import com.yf.afreesvg.SVGCanvas;
import com.yf.afreesvg.util.DoubleFunction;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Polyline shape
 * inherit {@link SVGPolygon}
 *
 * @author iffly
 * @since 0.0.1
 */
public class SVGPolyline extends SVGPolygon {

    public SVGPolyline(PointF[] points) {
        super(points);
    }

    @Override
    public Element convertToSVGElement(SVGCanvas canvas, Document document, DoubleFunction<String> convert) {
        Element element = document.createElement("polyline");
        element.setAttribute("points", getPointsStr(points, convert));
        addBaseAttr(element);
        return element;
    }

    @NonNull
    @Override
    public Object clone() {
        return new SVGPolyline(points);
    }
}
