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

/**
 * The circle shape
 *
 * @author iffly
 * @since 0.0.1
 */
public class SVGCircle extends SVGBaseShape {
    /**
     * centerX
     */
    private float cx;
    /**
     * centerY
     */
    private float cy;
    /**
     * radius
     */
    private float r;

    public SVGCircle(float cx, float cy, float r) {
        this.cx = cx;
        this.cy = cy;
        this.r = r;
    }

    /**
     * The centerX
     *
     * @return
     * @since 0.0.2
     */
    public float getCx() {
        return cx;
    }

    /**
     * The centerY
     *
     * @return
     * @since 0.0.2
     */
    public float getCy() {
        return cy;
    }

    /**
     * Radius
     *
     * @return
     * @since 0.0.2
     */
    public float getR() {
        return r;
    }


    @Override
    public Element convertToSVGElement(SVGCanvas canvas, Document document, DoubleFunction<String> convert) {
        Element element = document.createElement("circle");
        element.setAttribute("cx", convert.apply(cx));
        element.setAttribute("cy", convert.apply(cy));
        element.setAttribute("r", convert.apply(r));
        addBaseAttr(element);
        return element;
    }

    @NonNull
    @Override
    public Object clone() {
        return new SVGCircle(cx, cy, r);
    }
}
