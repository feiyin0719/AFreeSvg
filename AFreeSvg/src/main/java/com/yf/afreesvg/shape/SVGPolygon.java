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

import java.util.Arrays;

/**
 * Polygon shape
 *
 * @author iffly
 * @since 0.0.1
 */
public class SVGPolygon extends SVGBaseShape {
    /**
     * points
     */
    protected PointF[] points;

    public SVGPolygon(PointF[] points) {
        this.points = points;
    }

    /**
     * Get polygon points
     *
     * @return
     * @since 0.0.1
     */
    public PointF[] getPoints() {
        return points;
    }

    @NonNull
    @Override
    public Object clone() {
        return new SVGPolygon(points);
    }

    @Override
    public Element convertToSVGElement(SVGCanvas canvas, Document document, DoubleFunction<String> convert) {
        Element element = document.createElement("polygon");
        element.setAttribute("points", getPointsStr(points, convert));
        addBaseAttr(element);
        return element;
    }

    protected String getPointsStr(PointF[] points, DoubleFunction<String> convert) {
        StringBuilder sb = new StringBuilder();
        if (points.length > 0) {
            for (PointF point : points)
                sb.append(" ").append(convert.apply(point.x)).append(",").append(convert.apply(point.y));
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SVGPolygon polygon = (SVGPolygon) o;
        return Arrays.equals(points, polygon.points);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(points);
    }
}
