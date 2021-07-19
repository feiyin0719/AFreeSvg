package com.yf.afreesvg.filter;

import com.yf.afreesvg.SVGCanvas;
import com.yf.afreesvg.util.DoubleFunction;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.Objects;

public class SVGGaussianBlurFilter extends SVGBaseFilter {
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

    public static class SVGGaussianBlurFilterEffect extends SVGBaseFilterEffect {
        protected float stdDeviationX;
        protected float stdDeviationY;

        public SVGGaussianBlurFilterEffect(float stdDeviationX, float stdDeviationY) {
            this.stdDeviationX = stdDeviationX;
            this.stdDeviationY = stdDeviationY;
        }

        public SVGGaussianBlurFilterEffect() {
        }

        public float getStdDeviationX() {
            return stdDeviationX;
        }

        public void setStdDeviationX(float stdDeviationX) {
            this.stdDeviationX = stdDeviationX;
        }

        public float getStdDeviationY() {
            return stdDeviationY;
        }

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

    public float getStdDeviationX() {
        return gaussianBlurFilterEffect.getStdDeviationX();
    }

    public void setStdDeviationX(float stdDeviationX) {
        gaussianBlurFilterEffect.setStdDeviationX(stdDeviationX);
    }

    public float getStdDeviationY() {
        return gaussianBlurFilterEffect.getStdDeviationY();
    }

    public void setStdDeviationY(float stdDeviationY) {
        gaussianBlurFilterEffect.setStdDeviationY(stdDeviationY);
    }
}
