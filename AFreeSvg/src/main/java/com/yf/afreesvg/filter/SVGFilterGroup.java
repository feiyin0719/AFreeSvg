package com.yf.afreesvg.filter;

import androidx.annotation.StringDef;

import com.yf.afreesvg.SVGCanvas;
import com.yf.afreesvg.util.DoubleFunction;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

/**
 * Filter group
 * It can combine multiple effects to use
 * For example
 * <pre>
 *
 *             SVGFilterGroup filterGroup = new SVGFilterGroup();
 *             filterGroup.setFilterUnits(SVGModes.MODE_BOX);
 *             filterGroup.setX(-0.2f);
 *             filterGroup.setY(-0.2f);
 *             filterGroup.setWidth(1.5f);
 *             filterGroup.setHeight(1.5f);
 *             SVGOffsetFilter.SVGOffsetFilterEffect offsetFilterEffect = new SVGOffsetFilter.SVGOffsetFilterEffect(0.05f, 0.05f);
 *             offsetFilterEffect.setIn(SVGBaseFilter.ALPHA_VALUE);
 *             offsetFilterEffect.setResult("offset");
 *             SVGGaussianBlurFilter.SVGGaussianBlurFilterEffect gaussianBlurFilterEffect = new SVGGaussianBlurFilter.SVGGaussianBlurFilterEffect(3, 3);
 *             gaussianBlurFilterEffect.setIn(offsetFilterEffect.getResult());
 *             gaussianBlurFilterEffect.setResult("blur");
 *             SVGFilterGroup.SVGBlendFilterEffect blendFilterEffect = new SVGFilterGroup.SVGBlendFilterEffect();
 *             blendFilterEffect.setIn(SVGBaseFilter.GRAPHIC_VALUE);
 *             blendFilterEffect.setIn2(gaussianBlurFilterEffect.getResult());
 *
 *             filterGroup.addEffect(offsetFilterEffect);
 *             filterGroup.addEffect(gaussianBlurFilterEffect);
 *             filterGroup.addEffect(blendFilterEffect);
 * </pre>
 * <p>
 * it will generate the filter
 * <pre>
 *   <filter x="-0.10000000149011612" y="-0.10000000149011612" width="1.100000023841858" height="1.100000023841858" filterUnits="objectBoundingBox" id="filter-0">
 *   <feOffset in="SourceAlpha" result="offset" dx="0.05000000074505806" dy="0.05000000074505806"/>
 *   <feGaussianBlur stdDeviation="3.0,3.0" in="offset" result="blur"/>
 *   <feBlend in="SourceGraphic" in2="blur"/>
 *   </filter>
 * </pre>
 *
 * @author iffly
 * @since 0.0.2
 */
public class SVGFilterGroup extends SVGBaseFilter {


    public SVGFilterGroup(float x, float y, float width, float height) {
        super(x, y, width, height);
    }

    public SVGFilterGroup() {
    }

    /**
     * Blend filter
     * Mix two inputs
     * For example:
     * <pre>
     *              SVGGaussianBlurFilter.SVGGaussianBlurFilterEffect gaussianBlurFilterEffect = new SVGGaussianBlurFilter.SVGGaussianBlurFilterEffect(3, 3);
     *             gaussianBlurFilterEffect.setIn(offsetFilterEffect.getResult());
     *             gaussianBlurFilterEffect.setResult("blur");
     *             SVGFilterGroup.SVGBlendFilterEffect blendFilterEffect = new SVGFilterGroup.SVGBlendFilterEffect();
     *             blendFilterEffect.setIn(SVGBaseFilter.GRAPHIC_VALUE);
     *             blendFilterEffect.setIn2(gaussianBlurFilterEffect.getResult());
     * </pre>
     * It will mixed blur result and graphic
     *
     * @since 0.0.2
     */
    public static class SVGBlendFilterEffect extends SVGBaseFilterEffect {
        /**
         * The second input
         */
        protected String in2;
        /**
         * The blend mode
         *
         * @see #MODE_NORMAL
         * @see #MODE_MULTIPLY
         * @see #MODE_DARKEN
         * @see #MODE_SCREEN
         * @see #MODE_LIGHTEN
         */
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

        /**
         * Get second input
         *
         * @return
         * @since 0.0.2
         */
        public String getIn2() {
            return in2;
        }

        /**
         * Set second input
         *
         * @param in2
         * @since 0.0.2
         */
        public void setIn2(String in2) {
            this.in2 = in2;
        }

        /**
         * Get blend mode
         *
         * @return
         * @since 0.0.2
         */
        public @BlendMode
        String getMode() {
            return mode;
        }

        /**
         * Set blend mode
         *
         * @param mode
         * @since 0.0.2
         */
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
