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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Shape group
 * It will generate shape combinations with "<g></g>"
 *
 * @author iffly
 * @since 0.0.1
 */
public class SVGShapeGroup implements SVGShape {
    /**
     * Shape list
     */
    private List<SVGShape> list;

    public SVGShapeGroup() {
        list = new ArrayList<>();
    }

    public SVGShapeGroup(SVGShapeGroup shapeGroup) {
        list = new ArrayList<>();
        for (SVGShape shape : shapeGroup.list) {
            list.add((SVGShape) shape.clone());
        }
    }

    /**
     * Add shape
     *
     * @param shape {@link SVGShape}
     * @since 0.0.1
     */
    public void addShape(SVGShape shape) {
        list.add(shape);
    }


    @Override
    public Element convertToSVGElement(SVGCanvas canvas, Document document, DoubleFunction<String> convert) {
        Element g = document.createElement("g");
        for (SVGShape shape : list) {
            g.appendChild(shape.convertToSVGElement(canvas, document, convert));
        }
        return g;
    }

    @NonNull
    @Override
    public Object clone() {
        return new SVGShapeGroup(this);
    }
}
