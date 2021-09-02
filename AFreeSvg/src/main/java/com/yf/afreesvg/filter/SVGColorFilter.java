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

/**
 * Color filter
 *
 * @author iffly
 * @since 0.0.2
 */
public class SVGColorFilter extends SVGBaseFilter {
    /**
     * Color filter effect
     */
    protected SVGColorFilterEffect colorFilterEffect;

    public SVGColorFilter(float[] colorMatrix) {
        this(0, 0, 0, 0, colorMatrix, SVGColorFilterEffect.ColorFilterType.TYPE_MATRIX, GRAPHIC_VALUE);
    }

    public SVGColorFilter(float x, float y, float width, float height, float[] colorMatrix, @SVGColorFilterEffect.ColorFilterType String type) {
        this(x, y, width, height, colorMatrix, type, GRAPHIC_VALUE);
    }

    public SVGColorFilter(float x, float y, float width, float height, float[] colorMatrix, @SVGColorFilterEffect.ColorFilterType String type, String in) {
        super(x, y, width, height);
        colorFilterEffect = new SVGColorFilterEffect(colorMatrix, type);
        colorFilterEffect.setIn(in);
        addEffect(colorFilterEffect);
    }

    /**
     * Color filter effect
     *
     * @since 0.0.2
     */
    public static class SVGColorFilterEffect extends SVGBaseFilterEffect {
        /**
         * The color filter matrix
         */
        protected float[] colorMatrix;
        /**
         * The filter type
         */
        protected @ColorFilterType
        String type;

        @StringDef({ColorFilterType.TYPE_MATRIX, ColorFilterType.TYPE_SATURATE, ColorFilterType.TYPE_HUEROTATE, ColorFilterType.TYPE_LUMINANCETOALPHA})
        @Retention(RetentionPolicy.SOURCE)
        public @interface ColorFilterType {
            String TYPE_MATRIX = "matrix";
            String TYPE_SATURATE = "saturate";
            String TYPE_HUEROTATE = "hueRotate";
            String TYPE_LUMINANCETOALPHA = "luminanceToAlpha";
        }

        /**
         * Get filter type
         *
         * @return
         * @since 0.0.2
         */
        public @ColorFilterType
        String getType() {
            return type;
        }

        /**
         * Set filter type
         *
         * @param type
         * @since 0.0.2
         */
        public void setType(@ColorFilterType String type) {
            this.type = type;
        }

        public SVGColorFilterEffect(float[] colorMatrix) {
            this(colorMatrix, ColorFilterType.TYPE_MATRIX);
        }

        public SVGColorFilterEffect(float[] colorMatrix, @ColorFilterType String type) {
            this.colorMatrix = colorMatrix;
            this.type = type;
        }

        /**
         * Get color matrix value
         *
         * @return
         * @since 0.0.2
         */
        public float[] getColorMatrix() {
            return colorMatrix;
        }

        /**
         * Set color matrix value
         *
         * @param colorMatrix
         * @since 0.0.2
         */
        public void setColorMatrix(float[] colorMatrix) {
            this.colorMatrix = colorMatrix;
        }

        /**
         * Get matrix value string
         *
         * @param convert double convert,convert double to string
         * @return
         * @since 0.0.2
         */
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
            if (!ColorFilterType.TYPE_MATRIX.equals(type)) {
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

    /**
     * Get color matrix value
     *
     * @return
     * @since 0.0.2
     */
    public float[] getColorMatrix() {
        return colorFilterEffect.getColorMatrix();
    }

    /**
     * Set color matrix value
     *
     * @param colorMatrix
     * @since 0.0.2
     */
    public void setColorMatrix(float[] colorMatrix) {
        colorFilterEffect.setColorMatrix(colorMatrix);
    }

    /**
     * Get filter type
     *
     * @return
     * @since 0.0.2
     */
    public @SVGColorFilterEffect.ColorFilterType
    String getType() {
        return colorFilterEffect.getType();
    }

    /**
     * Set filter type
     *
     * @param type
     * @since 0.0.2
     */
    public void setType(@SVGColorFilterEffect.ColorFilterType String type) {
        colorFilterEffect.setType(type);
    }

}
