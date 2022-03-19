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

package com.yf.afreesvg.filter;

import com.yf.afreesvg.SVGCanvas;
import com.yf.afreesvg.util.DoubleFunction;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.Objects;

/**
 * Gaussian blur filter
 *
 * @author iffly
 * @since 0.0.2
 */
public class SVGGaussianBlurFilter extends SVGBaseFilter {
    /**
     * Gaussian blur filter effect
     */
    protected SVGGaussianBlurFilterEffect gaussianBlurFilterEffect;

    public SVGGaussianBlurFilter(float stdDeviationX, float stdDeviationY) {
        this(0, 0, 0, 0, stdDeviationX, stdDeviationY, GRAPHIC_VALUE);
    }


    public SVGGaussianBlurFilter(float x, float y, float width, float height, float stdDeviationX, float stdDeviationY, String in) {
        super(x, y, width, height);
        gaussianBlurFilterEffect = new SVGGaussianBlurFilterEffect();
        gaussianBlurFilterEffect.setIn(in);
        gaussianBlurFilterEffect.setStdDeviationX(stdDeviationX);
        gaussianBlurFilterEffect.setStdDeviationY(stdDeviationY);
        addEffect(gaussianBlurFilterEffect);
    }

    /**
     * Gaussian blur filter effect
     *
     * @since 0.0.2
     */
    public static class SVGGaussianBlurFilterEffect extends SVGBaseFilterEffect {
        /**
         * x-axis blur radius
         */
        protected float stdDeviationX;
        /**
         * y-axis blur radius
         */
        protected float stdDeviationY;

        public SVGGaussianBlurFilterEffect(float stdDeviationX, float stdDeviationY) {
            this.stdDeviationX = stdDeviationX;
            this.stdDeviationY = stdDeviationY;
        }

        public SVGGaussianBlurFilterEffect() {
        }

        /**
         * Get x-axis blur radius
         *
         * @return
         * @since 0.0.2
         */
        public float getStdDeviationX() {
            return stdDeviationX;
        }

        /**
         * Set x-axis blur radius
         *
         * @param stdDeviationX
         * @since 0.0.2
         */
        public void setStdDeviationX(float stdDeviationX) {
            this.stdDeviationX = stdDeviationX;
        }

        /**
         * Get y-axis blur radius
         *
         * @return
         * @since 0.0.2
         */
        public float getStdDeviationY() {
            return stdDeviationY;
        }

        /**
         * Set y-axis blur radius
         *
         * @param stdDeviationY
         * @since 0.0.2
         */
        public void setStdDeviationY(float stdDeviationY) {
            this.stdDeviationY = stdDeviationY;
        }


        @Override
        public Element convertToSVGElement(SVGCanvas canvas, Document document, DoubleFunction<String> convert) {
            Element element = document.createElement("feGaussianBlur");
            element.setAttribute("stdDeviation", convert.apply(stdDeviationX) + "," + convert.apply(stdDeviationY));
            addBaseAttr(element);
            return element;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            if (!super.equals(o)) return false;
            SVGGaussianBlurFilterEffect that = (SVGGaussianBlurFilterEffect) o;
            return Float.compare(that.stdDeviationX, stdDeviationX) == 0 &&
                    Float.compare(that.stdDeviationY, stdDeviationY) == 0;
        }

        @Override
        public int hashCode() {
            return Objects.hash(super.hashCode(), stdDeviationX, stdDeviationY);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SVGGaussianBlurFilter that = (SVGGaussianBlurFilter) o;
        return Objects.equals(gaussianBlurFilterEffect, that.gaussianBlurFilterEffect);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), gaussianBlurFilterEffect);
    }

    /**
     * Get x-axis blur radius
     *
     * @return
     * @since 0.0.2
     */
    public float getStdDeviationX() {
        return gaussianBlurFilterEffect.getStdDeviationX();
    }

    /**
     * Set x-axis blur radius
     *
     * @param stdDeviationX
     * @since 0.0.2
     */
    public void setStdDeviationX(float stdDeviationX) {
        gaussianBlurFilterEffect.setStdDeviationX(stdDeviationX);
    }

    /**
     * Get y-axis blur radius
     *
     * @return
     * @since 0.0.2
     */
    public float getStdDeviationY() {
        return gaussianBlurFilterEffect.getStdDeviationY();
    }

    /**
     * Set y-axis blur radius
     *
     * @param stdDeviationY
     * @since 0.0.2
     */
    public void setStdDeviationY(float stdDeviationY) {
        gaussianBlurFilterEffect.setStdDeviationY(stdDeviationY);
    }
}
