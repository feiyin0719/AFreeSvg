package com.yf.afreesvg.filter;

import androidx.annotation.StringDef;

import com.yf.afreesvg.SVGCanvas;
import com.yf.afreesvg.util.DoubleFunction;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

public class SVGFilterGroup extends SVGBaseFilter {


    public SVGFilterGroup(float x, float y, float width, float height) {
        super(x, y, width, height);
    }

    public SVGFilterGroup() {
    }

    public static class SVGBlendFilterEffect extends SVGBaseFilterEffect {

        protected String in2;

        protected @BlendMode
        String mode = MODE_NORMAL;
        public static final String MODE_NORMAL = "normal";
        public static final String MODE_MULTIPLY = "multiply";
        public static final String MODE_SCREEN = "screen";
        public static final String MODE_DARKEN = "darken";
        public static final String MODE_LIGHTEN = "lighten";

        @StringDef({MODE_NORMAL, MODE_MULTIPLY, MODE_SCREEN, MODE_DARKEN, MODE_LIGHTEN})
        @Retention(RetentionPolicy.SOURCE)
        public @interface BlendMode {
        }


        public String getIn2() {
            return in2;
        }

        public void setIn2(String in2) {
            this.in2 = in2;
        }

        public @BlendMode
        String getMode() {
            return mode;
        }

        public void setMode(@BlendMode String mode) {
            this.mode = mode;
        }

        @Override
        public Element convertToSVGElement(SVGCanvas canvas, Document document, DoubleFunction<String> convert) {
            Element element = document.createElement("feBlend");
            addBaseAttr(element);
            element.setAttribute("in2", in2);
            if (!MODE_NORMAL.equals(mode))
                element.setAttribute("mode", mode);
            return element;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            if (!super.equals(o)) return false;
            SVGBlendFilterEffect that = (SVGBlendFilterEffect) o;
            return Objects.equals(in2, that.in2) &&
                    Objects.equals(mode, that.mode);
        }

        @Override
        public int hashCode() {
            return Objects.hash(super.hashCode(), in2, mode);
        }
    }


}
