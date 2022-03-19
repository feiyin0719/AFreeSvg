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

import com.yf.afreesvg.SVGCanvas;
import com.yf.afreesvg.util.DoubleFunction;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.Objects;

/**
 * The oval shape
 *
 * @author iffly
 * @since 0.0.1
 */
public class SVGOval extends SVGBaseShape {
    /**
     * centerX
     */
    private float cx;
    /**
     * centerY
     */
    private float cy;
    /**
     * radius of axis-X
     */
    private float rx;
    /**
     * radius of axis-Y
     */
    private float ry;

    public SVGOval(float cx, float cy, float rx, float ry) {
        this.cx = cx;
        this.cy = cy;
        this.rx = rx;
        this.ry = ry;
    }

    /**
     * Get centerX
     *
     * @return
     */
    public float getCx() {
        return cx;
    }

    /**
     * Get centerY
     *
     * @return
     */
    public float getCy() {
        return cy;
    }

    /**
     * Get radius of axis-X
     *
     * @return
     */
    public float getRx() {
        return rx;
    }

    /**
     * Get radius of axis-Y
     *
     * @return
     */
    public float getRy() {
        return ry;
    }

    @Override
    public Element convertToSVGElement(SVGCanvas canvas, Document document, DoubleFunction<String> convert) {
        Element element = document.createElement("ellipse");
        element.setAttribute("cx", convert.apply(cx));
        element.setAttribute("cy", convert.apply(cy));
        element.setAttribute("rx", convert.apply(rx));
        element.setAttribute("ry", convert.apply(ry));
        addBaseAttr(element);
        return element;
    }

    @NonNull
    @Override
    public Object clone() {
        return new SVGOval(cx, cy, rx, ry);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SVGOval oval = (SVGOval) o;
        return Float.compare(oval.cx, cx) == 0 &&
                Float.compare(oval.cy, cy) == 0 &&
                Float.compare(oval.rx, rx) == 0 &&
                Float.compare(oval.ry, ry) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cx, cy, rx, ry);
    }
}
