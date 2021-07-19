package com.yf.afreesvg.filter;

import androidx.annotation.StringDef;

import com.yf.afreesvg.SVGCanvas;
import com.yf.afreesvg.util.DoubleFunction;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;
import java.util.Objects;

public class SVGColorFilter extends SVGBaseFilter {

    protected SVGColorFilterEffect colorFilterEffect;

    public SVGColorFilter(float[] colorMatrix) {
        this(0, 0, 0, 0, colorMatrix, SVGColorFilterEffect.TYPE_MATRIX, GRAPHIC_VALUE);
    }

    public SVGColorFilter(float x, float y, float width, float height, float[] colorMatrix, @SVGColorFilterEffect.ColorFilterType String type, String in) {
        super(x, y, width, height);
        colorFilterEffect = new SVGColorFilterEffect(colorMatrix, type);
        colorFilterEffect.setIn(in);
        addEffect(colorFilterEffect);
    }

    public static class SVGColorFilterEffect extends SVGBaseFilterEffect {
        protected float[] colorMatrix;

        protected @ColorFilterType
        String type;

        public static final String TYPE_MATRIX = "matrix";
        public static final String TYPE_SATURATE = "saturate";
        public static final String TYPE_HUEROTATE = "hueRotate";
        public static final String TYPE_LUMINANCETOALPHA = "luminanceToAlpha";

        @StringDef({TYPE_MATRIX, TYPE_SATURATE, TYPE_HUEROTATE, TYPE_LUMINANCETOALPHA})
        @Retention(RetentionPolicy.SOURCE)
        public @interface ColorFilterType {
        }

        public @ColorFilterType
        String getType() {
            return type;
        }

        public void setType(@ColorFilterType String type) {
            this.type = type;
        }

        public SVGColorFilterEffect(float[] colorMatrix) {
            this(colorMatrix, TYPE_MATRIX);
        }

        public SVGColorFilterEffect(float[] colorMatrix, @ColorFilterType String type) {
            this.colorMatrix = colorMatrix;
            this.type = type;
        }

        public float[] getColorMatrix() {
            return colorMatrix;
        }

        public void setColorMatrix(float[] colorMatrix) {
            this.colorMatrix = colorMatrix;
        }

        private String getValue(DoubleFunction<String> convert) {
            StringBuilder stringBuilder = new StringBuilder();
            for (float f : colorMatrix)
                stringBuilder.append(convert.apply(f)).append(" ");
            return stringBuilder.toString();
        }

        @Override
        public Element convertToSVGElement(SVGCanvas canvas, Document document, DoubleFunction<String> convert) {
            Element element = document.createElement("feColorMatrix");
            addBaseAttr(element);
            element.setAttribute("value", getValue(convert));
            if (!TYPE_MATRIX.equals(type)) {
                element.setAttribute("type", type);
            }
            return element;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            if (!super.equals(o)) return false;
            SVGColorFilterEffect that = (SVGColorFilterEffect) o;
            return Arrays.equals(colorMatrix, that.colorMatrix) &&
                    Objects.equals(type, that.type);
        }

        @Override
        public int hashCode() {
            int result = Objects.hash(super.hashCode(), type);
            result = 31 * result + Arrays.hashCode(colorMatrix);
            return result;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SVGColorFilter that = (SVGColorFilter) o;
        return Objects.equals(colorFilterEffect, that.colorFilterEffect);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), colorFilterEffect);
    }

    public float[] getColorMatrix() {
        return colorFilterEffect.getColorMatrix();
    }

    public void setColorMatrix(float[] colorMatrix) {
        colorFilterEffect.setColorMatrix(colorMatrix);
    }

    public @SVGColorFilterEffect.ColorFilterType
    String getType() {
        return colorFilterEffect.getType();
    }

    public void setType(@SVGColorFilterEffect.ColorFilterType String type) {
        colorFilterEffect.setType(type);
    }

}
