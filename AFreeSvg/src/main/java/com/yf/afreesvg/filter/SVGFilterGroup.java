package com.yf.afreesvg.filter;

import androidx.annotation.StringDef;

import com.yf.afreesvg.SVGCanvas;
import com.yf.afreesvg.util.DoubleFunction;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;
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

    /**
     * Merge filter node
     * It will generate the svg element
     * <pre>
     *     <feMerge>
     *         <feMergeNode in="C2" />
     *         <feMergeNode in="C1" />
     *         <feMergeNode in="SourceGraphic" />
     *      </feMerge>
     * </pre>
     * "<feMerge>" It can add more than one filter effect to draw element at the same time
     * The "in" is the other filter effect "result"
     * For example
     * <pre>
     *     <filter id="test" filterUnits="objectBoundingBox" x="0" y="0" width="1.5" height="4">
     *       <feOffset result="Off1" dx="15" dy="20" />
     *       <feFlood style="flood-color:#ff0000;flood-opacity:0.8" />
     *       <feComposite in2="Off1" operator="in" result="C1" />
     *       <feOffset in="SourceGraphic" result="Off2" dx="30" dy="40" />
     *       <feFlood style="flood-color:#ff0000;flood-opacity:0.4" />
     *       <feComposite in2="Off2" operator="in" result="C2" />
     *       <feMerge>
     *         <feMergeNode in="C2" />
     *         <feMergeNode in="C1" />
     *         <feMergeNode in="SourceGraphic" />
     *       </feMerge>
     *     </filter>
     *  </pre>
     *
     * @since 0.0.2
     */
    public static class SVGMergeFilterEffect extends SVGBaseFilterEffect {
        private List<String> inList = new ArrayList<>();

        /**
         * Add merge node
         *
         * @param in
         * @since 0.0.2
         */
        public void addMergeNode(String in) {
            inList.add(in);
        }

        @Override
        public Element convertToSVGElement(SVGCanvas canvas, Document document, DoubleFunction<String> convert) {
            Element element = document.createElement("feMerge");
            addBaseAttr(element);
            for (String in : inList) {
                Element mergeNode = document.createElement("feMergeNode");
                mergeNode.setAttribute("in", in);
                element.appendChild(mergeNode);
            }
            return element;
        }
    }

    /**
     * Composite filter effect
     * It combines the two inputs according to certain rules
     * It will generate the filter effect
     * <pre>
     * <feComposite in="SourceGraphic" in2="BackgroundImage" operator="over" result="comp"/>
     * </pre>
     *
     * @since 0.0.2
     */

    public static class SVGCompositeFilterEffect extends SVGBaseFilterEffect {
        /**
         * The second input
         */
        protected String in2;
        /**
         * The operate to point combines certain rule
         *
         * @see #OPERATE_IN
         * @see #OPERATE_ATOP
         * @see #OPERATE_OUT
         * @see #OPERATE_OVER
         * @see #OPERATE_XOR
         * @see #OPERATE_LIGHTER
         * @see #OPERATE_ARITHMETIC
         */
        protected @OperateType
        String operate;
        /**
         * only use when operate is arithmetic
         *
         * @see #OPERATE_ARITHMETIC
         */
        protected float k1;
        /**
         * only use when operate is arithmetic
         *
         * @see #OPERATE_ARITHMETIC
         */
        protected float k2;
        /**
         * only use when operate is arithmetic
         *
         * @see #OPERATE_ARITHMETIC
         */
        protected float k3;
        /**
         * only use when operate is arithmetic
         *
         * @see #OPERATE_ARITHMETIC
         */
        protected float k4;

        @StringDef({OPERATE_IN, OPERATE_ATOP, OPERATE_OUT, OPERATE_OVER, OPERATE_XOR, OPERATE_LIGHTER, OPERATE_ARITHMETIC})
        public @interface OperateType {

        }

        public static final String OPERATE_OVER = "over";
        public static final String OPERATE_IN = "in";
        public static final String OPERATE_ATOP = "atop";
        public static final String OPERATE_OUT = "out";
        public static final String OPERATE_XOR = "xor";
        public static final String OPERATE_LIGHTER = "lighter";
        /**
         * When operate is arithmetic,it will use the rule to generate result
         * result = k1*i1*i2 + k2*i1 + k3*i2 + k4
         */
        public static final String OPERATE_ARITHMETIC = "arithmetic";

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
         * Get operate type
         *
         * @return
         * @see #OPERATE_IN
         * @see #OPERATE_ATOP
         * @see #OPERATE_OUT
         * @see #OPERATE_OVER
         * @see #OPERATE_XOR
         * @see #OPERATE_LIGHTER
         * @see #OPERATE_ARITHMETIC
         * @since 0.0.2
         */
        public @OperateType
        String getOperate() {
            return operate;
        }

        /**
         * Set operate type
         *
         * @return
         * @see #OPERATE_IN
         * @see #OPERATE_ATOP
         * @see #OPERATE_OUT
         * @see #OPERATE_OVER
         * @see #OPERATE_XOR
         * @see #OPERATE_LIGHTER
         * @see #OPERATE_ARITHMETIC
         * @since 0.0.2
         */
        public void setOperate(@OperateType String operate) {
            this.operate = operate;
        }

        /**
         * Get k1
         * only use when operate is arithmetic
         *
         * @return
         * @see #OPERATE_ARITHMETIC
         * @since 0.0.2
         */
        public float getK1() {
            return k1;
        }

        /**
         * Set k1
         * only use when operate is arithmetic
         *
         * @param k1
         * @see #OPERATE_ARITHMETIC
         * @since 0.0.2
         */
        public void setK1(float k1) {
            this.k1 = k1;
        }

        /**
         * Get k2
         * only use when operate is arithmetic
         *
         * @return
         * @see #OPERATE_ARITHMETIC
         * @since 0.0.2
         */
        public float getK2() {
            return k2;
        }

        /**
         * Set k2
         * only use when operate is arithmetic
         *
         * @param k2
         * @see #OPERATE_ARITHMETIC
         * @since 0.0.2
         */
        public void setK2(float k2) {
            this.k2 = k2;
        }

        /**
         * Get k3
         * only use when operate is arithmetic
         *
         * @return
         * @see #OPERATE_ARITHMETIC
         * @since 0.0.2
         */
        public float getK3() {
            return k3;
        }

        /**
         * Set k3
         * only use when operate is arithmetic
         *
         * @param k3
         * @see #OPERATE_ARITHMETIC
         * @since 0.0.2
         */
        public void setK3(float k3) {
            this.k3 = k3;
        }

        /**
         * Get k4
         * only use when operate is arithmetic
         *
         * @return
         * @see #OPERATE_ARITHMETIC
         * @since 0.0.2
         */
        public float getK4() {
            return k4;
        }

        /**
         * Set k4
         * only use when operate is arithmetic
         *
         * @param k4
         * @see #OPERATE_ARITHMETIC
         * @since 0.0.2
         */
        public void setK4(float k4) {
            this.k4 = k4;
        }

        @Override
        public Element convertToSVGElement(SVGCanvas canvas, Document document, DoubleFunction<String> convert) {
            Element element = document.createElement("feComposite");
            addBaseAttr(element);
            element.setAttribute("in2", in2);
            element.setAttribute("operate", operate);
            if (OPERATE_ARITHMETIC.equals(operate)) {
                element.setAttribute("k1", convert.apply(k1));
                element.setAttribute("k2", convert.apply(k2));
                element.setAttribute("k3", convert.apply(k3));
                element.setAttribute("k4", convert.apply(k4));
            }
            return element;
        }
    }


}
