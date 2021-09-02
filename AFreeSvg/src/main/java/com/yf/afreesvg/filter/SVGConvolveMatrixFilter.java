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
 * Convolve filter
 *
 * @author iffly
 * @since 0.0.2
 */
public class SVGConvolveMatrixFilter extends SVGBaseFilter {
    protected SVGConvolveMatrixFilterEffect effect;

    public SVGConvolveMatrixFilter(float[] matrix) {
        this(matrix, 3, GRAPHIC_VALUE, 0, 0, 0, 0);
    }

    public SVGConvolveMatrixFilter(float[] matrix, float x, float y, float width, float height) {
        this(matrix, 3, GRAPHIC_VALUE, x, y, width, height);

    }

    public SVGConvolveMatrixFilter(float[] matrix, int order, String in, float x, float y, float width, float height) {
        super(x, y, width, height);
        effect = new SVGConvolveMatrixFilterEffect(matrix);
        effect.setOrder(order);
        effect.setIn(in);
        addEffect(effect);

    }

    /**
     * Convolve filter effect
     * It define a Convolve matrix to filter image
     * It calc rule is
     * <pre>
     * COLORX,Y = (
     *
     * SUM I=0 to [orderY-1] {
     *
     * SUM J=0 to [orderX-1] {
     *
     * SOURCE X-targetX+J, Y-targetY+I * kernelMatrixorderX-J-1, orderY-I-1
     *
     * }
     *
     * }
     *
     * ) / divisor + bias * ALPHAX,Y
     * </pre>
     * It will generate the element
     * <pre>
     *     <feConvolveMatrix order="3" kernelMatrix="
     *       2  0  0
     *       0 -1  0
     *       0  0 -1" edgeMode="none" bias="100">
     *   </feConvolveMatrix>
     * <pre/>
     *
     *
     * @since 0.0.2
     */
    public static class SVGConvolveMatrixFilterEffect extends SVGBaseFilterEffect {
        /**
         * The kernel size
         * default size is 3
         */
        protected int order = 3;
        /**
         * Convolve filter
         */
        protected float[] kernelMatrix;
        /**
         * The matrix sum
         * default is sum of kernelMatrix
         */
        protected float divisor = 0;
        /**
         * Bias value
         */
        protected float bias = 0;
        /**
         * The centerX bias of target
         */
        protected int targetX = 0;
        /**
         * The centerY bias of target
         */
        protected int targetY = 0;
        /**
         * Edge mode
         * Convolution boundary position repetition pattern
         *
         * @see EdgeMode
         */
        protected @EdgeMode
        String edgeMode = EdgeMode.EDGE_MODE_DUPLICATE;
        /**
         * The kernel unit of X-axis
         */
        protected int kernelUnitLengthX = 0;
        /**
         * The kernel unis of Y-axis
         */
        protected int kernelUnitLengthY = 0;
        /**
         * Need calc contains alpha
         * false contains alpha
         */
        protected boolean preserveAlpha = false;


        @StringDef({EdgeMode.EDGE_MODE_DUPLICATE, EdgeMode.EDGE_MODE_WRAP, EdgeMode.EDGE_MODE_NONE})
        @Retention(RetentionPolicy.SOURCE)
        public @interface EdgeMode {
            String EDGE_MODE_DUPLICATE = "duplicate";
            String EDGE_MODE_WRAP = "wrap";
            String EDGE_MODE_NONE = "none";
        }

        public SVGConvolveMatrixFilterEffect(float[] kernelMatrix) {
            this.kernelMatrix = kernelMatrix;
        }

        public SVGConvolveMatrixFilterEffect(int order, float[] kernelMatrix, float divisor, float bias, int targetX, int targetY, String edgeMode, int kernelUnitLengthX, int kernelUnitLengthY, boolean preserveAlpha) {
            this.order = order;
            this.kernelMatrix = kernelMatrix;
            this.divisor = divisor;
            this.bias = bias;
            this.targetX = targetX;
            this.targetY = targetY;
            this.edgeMode = edgeMode;
            this.kernelUnitLengthX = kernelUnitLengthX;
            this.kernelUnitLengthY = kernelUnitLengthY;
            this.preserveAlpha = preserveAlpha;
        }

        /**
         * Get order
         *
         * @return
         * @since 0.0.2
         */
        public int getOrder() {
            return order;
        }

        /**
         * Set order
         *
         * @param order
         * @since 0.0.2
         */
        public void setOrder(int order) {
            this.order = order;
        }

        /**
         * Get kernelMatrix
         *
         * @return
         * @since 0.0.2
         */
        public float[] getKernelMatrix() {
            return kernelMatrix;
        }

        /**
         * Set kernelMatrix
         *
         * @return
         * @since 0.0.2
         */
        public void setKernelMatrix(float[] kernelMatrix) {
            this.kernelMatrix = kernelMatrix;
        }

        /**
         * Get divisor
         *
         * @return
         * @since 0.0.2
         */
        public float getDivisor() {
            return divisor;
        }

        /**
         * Set divisor
         *
         * @return
         * @since 0.0.2
         */
        public void setDivisor(float divisor) {
            this.divisor = divisor;
        }

        /**
         * Get bias
         *
         * @return
         * @since 0.0.2
         */
        public float getBias() {
            return bias;
        }

        /**
         * Set bias
         *
         * @return
         * @since 0.0.2
         */
        public void setBias(float bias) {
            this.bias = bias;
        }

        /**
         * Get targetX
         *
         * @return
         * @since 0.0.2
         */
        public int getTargetX() {
            return targetX;
        }

        /**
         * Set targetX
         *
         * @return
         * @since 0.0.2
         */
        public void setTargetX(int targetX) {
            this.targetX = targetX;
        }

        /**
         * Get targetY
         *
         * @return
         * @since 0.0.2
         */
        public int getTargetY() {
            return targetY;
        }

        /**
         * Set targetY
         *
         * @return
         * @since 0.0.2
         */
        public void setTargetY(int targetY) {
            this.targetY = targetY;
        }

        /**
         * Get edgeMode
         *
         * @return
         * @since 0.0.2
         */
        public String getEdgeMode() {
            return edgeMode;
        }

        /**
         * Set edgeMode
         *
         * @return
         * @since 0.0.2
         */
        public void setEdgeMode(String edgeMode) {
            this.edgeMode = edgeMode;
        }

        /**
         * Get kernelUnitsLengthX
         *
         * @return
         * @since 0.0.2
         */
        public int getKernelUnitLengthX() {
            return kernelUnitLengthX;
        }

        /**
         * Set kernelUnitsLengthX
         *
         * @return
         * @since 0.0.2
         */
        public void setKernelUnitLengthX(int kernelUnitLengthX) {
            this.kernelUnitLengthX = kernelUnitLengthX;
        }

        /**
         * Get kernelUnitsLengthY
         *
         * @return
         * @since 0.0.2
         */
        public int getKernelUnitLengthY() {
            return kernelUnitLengthY;
        }

        /**
         * Set kernelUnitsLengthY
         *
         * @return
         * @since 0.0.2
         */
        public void setKernelUnitLengthY(int kernelUnitLengthY) {
            this.kernelUnitLengthY = kernelUnitLengthY;
        }

        /**
         * Get preserveAlpha
         *
         * @return
         * @since 0.0.2
         */
        public boolean isPreserveAlpha() {
            return preserveAlpha;
        }

        /**
         * Set preserveAlpha
         *
         * @return
         * @since 0.0.2
         */
        public void setPreserveAlpha(boolean preserveAlpha) {
            this.preserveAlpha = preserveAlpha;
        }

        protected String getMatrixValueStr(DoubleFunction<String> convert) {
            StringBuilder sb = new StringBuilder();
            for (float f : kernelMatrix) {
                sb.append(convert.apply(f)).append(" ");
            }
            return sb.toString();
        }

        @Override
        public Element convertToSVGElement(SVGCanvas canvas, Document document, DoubleFunction<String> convert) {
            Element element = document.createElement("feConvolveMatrix");
            if (order != 3)
                element.setAttribute("order", "" + order);
            element.setAttribute("kernelMatrix", getMatrixValueStr(convert));
            if (divisor != 0)
                element.setAttribute("divisor", convert.apply(divisor));
            if (bias != 0)
                element.setAttribute("bias", convert.apply(bias));
            if (targetX != 0)
                element.setAttribute("targetX", "" + targetX);
            if (targetY != 0)
                element.setAttribute("targetY", "" + targetY);
            if (!EdgeMode.EDGE_MODE_DUPLICATE.equals(edgeMode))
                element.setAttribute("edgeMode", edgeMode);
            if (kernelUnitLengthX != 0 || kernelUnitLengthY != 0) {
                if (kernelUnitLengthX == kernelUnitLengthY) {
                    element.setAttribute("kernelUnitLength", "" + kernelUnitLengthX);
                } else {
                    element.setAttribute("kernelUnitLength", kernelUnitLengthX + " " + kernelUnitLengthY);
                }
            }
            if (preserveAlpha) {
                element.setAttribute("preserveAlpha", "" + preserveAlpha);
            }
            addBaseAttr(element);
            return element;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            if (!super.equals(o)) return false;
            SVGConvolveMatrixFilterEffect that = (SVGConvolveMatrixFilterEffect) o;
            return order == that.order &&
                    Float.compare(that.divisor, divisor) == 0 &&
                    Float.compare(that.bias, bias) == 0 &&
                    targetX == that.targetX &&
                    targetY == that.targetY &&
                    kernelUnitLengthX == that.kernelUnitLengthX &&
                    kernelUnitLengthY == that.kernelUnitLengthY &&
                    preserveAlpha == that.preserveAlpha &&
                    Arrays.equals(kernelMatrix, that.kernelMatrix) &&
                    Objects.equals(edgeMode, that.edgeMode);
        }

        @Override
        public int hashCode() {
            int result = Objects.hash(super.hashCode(), order, divisor, bias, targetX, targetY, edgeMode, kernelUnitLengthX, kernelUnitLengthY, preserveAlpha);
            result = 31 * result + Arrays.hashCode(kernelMatrix);
            return result;
        }
    }


    /**
     * Get order
     *
     * @return
     * @since 0.0.2
     */
    public int getOrder() {
        return effect.getOrder();
    }

    /**
     * Set order
     *
     * @param order
     * @since 0.0.2
     */
    public void setOrder(int order) {
        effect.setOrder(order);
    }

    /**
     * Get kernelMatrix
     *
     * @return
     * @since 0.0.2
     */
    public float[] getKernelMatrix() {
        return effect.getKernelMatrix();
    }

    /**
     * Set kernelMatrix
     *
     * @return
     * @since 0.0.2
     */
    public void setKernelMatrix(float[] kernelMatrix) {
        effect.setKernelMatrix(kernelMatrix);
    }

    /**
     * Get divisor
     *
     * @return
     * @since 0.0.2
     */
    public float getDivisor() {
        return effect.getDivisor();
    }

    /**
     * Set divisor
     *
     * @return
     * @since 0.0.2
     */
    public void setDivisor(float divisor) {
        effect.setDivisor(divisor);
    }

    /**
     * Get bias
     *
     * @return
     * @since 0.0.2
     */
    public float getBias() {
        return effect.getBias();
    }

    /**
     * Set bias
     *
     * @return
     * @since 0.0.2
     */
    public void setBias(float bias) {
        effect.setBias(bias);
    }

    /**
     * Get targetX
     *
     * @return
     * @since 0.0.2
     */
    public int getTargetX() {
        return effect.getTargetX();
    }

    /**
     * Set targetX
     *
     * @return
     * @since 0.0.2
     */
    public void setTargetX(int targetX) {
        effect.setTargetX(targetX);
    }

    /**
     * Get targetY
     *
     * @return
     * @since 0.0.2
     */
    public int getTargetY() {
        return effect.getTargetY();
    }

    /**
     * Set targetY
     *
     * @return
     * @since 0.0.2
     */
    public void setTargetY(int targetY) {
        effect.setTargetY(targetY);
    }

    /**
     * Get edgeMode
     *
     * @return
     * @since 0.0.2
     */
    public String getEdgeMode() {
        return effect.getEdgeMode();
    }

    /**
     * Set edgeMode
     *
     * @return
     * @since 0.0.2
     */
    public void setEdgeMode(String edgeMode) {
        effect.setEdgeMode(edgeMode);
    }

    /**
     * Get kernelUnitsLengthX
     *
     * @return
     * @since 0.0.2
     */
    public int getKernelUnitLengthX() {
        return effect.getKernelUnitLengthX();
    }

    /**
     * Set kernelUnitsLengthX
     *
     * @return
     * @since 0.0.2
     */
    public void setKernelUnitLengthX(int kernelUnitLengthX) {
        effect.setKernelUnitLengthX(kernelUnitLengthX);
    }

    /**
     * Get kernelUnitsLengthY
     *
     * @return
     * @since 0.0.2
     */
    public int getKernelUnitLengthY() {
        return effect.getKernelUnitLengthY();
    }

    /**
     * Set kernelUnitsLengthY
     *
     * @return
     * @since 0.0.2
     */
    public void setKernelUnitLengthY(int kernelUnitLengthY) {
        effect.setKernelUnitLengthY(kernelUnitLengthY);
    }

    /**
     * Get preserveAlpha
     *
     * @return
     * @since 0.0.2
     */
    public boolean isPreserveAlpha() {
        return effect.isPreserveAlpha();
    }

    /**
     * Set preserveAlpha
     *
     * @return
     * @since 0.0.2
     */
    public void setPreserveAlpha(boolean preserveAlpha) {
        effect.setPreserveAlpha(preserveAlpha);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SVGConvolveMatrixFilter that = (SVGConvolveMatrixFilter) o;
        return Objects.equals(effect, that.effect);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), effect);
    }
}
