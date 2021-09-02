package com.yf.afreesvg.shape;

import androidx.annotation.StringDef;

import com.yf.afreesvg.SVGCanvas;
import com.yf.afreesvg.util.DoubleFunction;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * The general path shape
 * It use some command line to generate path
 * Here is the command
 * <pre>
 * M = moveto
 * L = lineto
 * H = horizontal lineto
 * V = vertical lineto
 * C = curveto
 * S = smooth curveto
 * Q = quadratic Belzier curve
 * T = smooth quadratic Belzier curveto
 * A = elliptical Arc
 * Z = closepath
 * </pre>
 * It will generate svg element like this
 * <pre>
 * <path d="M10 10 H 90 V 90 H 10 Z" />
 * </pre>
 * if you want to know about the SVG path command,plz refer to http://www.verydoc.net/svg/00007415.html
 *
 * @author iffly
 * @since 0.0.1
 */
public class SVGPath extends SVGBaseShape {
    @Override
    public Element convertToSVGElement(SVGCanvas canvas, Document document, DoubleFunction<String> convert) {
        Element element = document.createElement("path");
        element.setAttribute("d", getSVGPathD(convert));
        addBaseAttr(element);
        return element;
    }

    /**
     * Get path command string
     *
     * @param convert The double convert
     * @return The path command string
     * @since 0.0.1
     */
    private String getSVGPathD(DoubleFunction<String> convert) {
        Iterator<SVGPath.SVGPathElement> iterator = iterator();
        StringBuilder sb = new StringBuilder();
        while (iterator.hasNext()) {
            SVGPath.SVGPathElement pathElement = iterator.next();
            sb.append(pathElement.valueStr(convert));
        }
        return sb.toString();
    }

    /**
     * The path command class
     * Here is the command
     * <pre>
     * M = moveto
     * L = lineto
     * H = horizontal lineto
     * V = vertical lineto
     * C = curveto
     * S = smooth curveto
     * Q = quadratic Belzier curve
     * T = smooth quadratic Belzier curveto
     * A = elliptical Arc
     * Z = closepath
     * </pre>
     *
     * @author iffly
     * @since 0.0.1
     */
    public static class SVGPathElement {

        @StringDef({PathElementType.M, PathElementType.L, PathElementType.H, PathElementType.V, PathElementType.C, PathElementType.S, PathElementType.Q, PathElementType.T, PathElementType.A, PathElementType.Z})
        @Retention(RetentionPolicy.SOURCE)
        public @interface PathElementType {
            /**
             * Path command
             */
            String M = "M";
            String L = "L";
            String H = "H";
            String V = "V";
            String C = "C";
            String S = "S";
            String Q = "Q";
            String T = "T";
            String A = "A";
            String Z = "Z";
        }

        /**
         * Command type
         */
        private @PathElementType
        String type;
        /**
         * Pos data
         */
        private float[] data;
        /**
         * Indicates whether the relative distance
         * true mean that is relative
         */
        private boolean isRelative;

        /**
         * The command type
         *
         * @return
         */
        public String getType() {
            return type;
        }

        /**
         * Set command type
         *
         * @param type
         */
        public void setType(String type) {
            this.type = type;
        }

        /**
         * The pos data
         *
         * @return
         */
        public float[] getData() {
            return data;
        }

        /**
         * Set pos data
         *
         * @param data
         */
        public void setData(float[] data) {
            this.data = data;
        }

        public SVGPathElement(String type, float[] data) {
            this(type, data, false);
        }

        public SVGPathElement(String type, float[] data, boolean isRelative) {
            this.type = type;
            this.data = data;
            this.isRelative = isRelative;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            SVGPathElement that = (SVGPathElement) o;
            return Objects.equals(type, that.type) &&
                    Arrays.equals(data, that.data);
        }

        @Override
        public int hashCode() {
            int result = Objects.hash(type);
            result = 31 * result + Arrays.hashCode(data);
            return result;
        }

        /**
         * Get path command string
         *
         * @param convert The double convert,convert double to string
         * @return The path command string
         */
        public String valueStr(DoubleFunction convert) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(isRelative ? type.toLowerCase() : type).append(" ");
            if (type != PathElementType.A) {
                if (data != null)
                    for (float f : data)
                        stringBuilder.append(convert.apply(f)).append(" ");
            } else {
                if (data != null)
                    for (int i = 0; i < data.length; ++i) {
                        if (i == 3 || i == 4) {
                            stringBuilder.append((int) data[i]).append(" ");
                        } else
                            stringBuilder.append(data[i]).append(" ");
                    }
            }
            return stringBuilder.toString();

        }


    }

    /**
     * Path commands
     * Use it to save ptah commands
     *
     * @see SVGPathElement
     */
    private List<SVGPathElement> pathElements = new ArrayList<>();

    public SVGPath() {
    }

    public SVGPath(SVGPath path) {
        pathElements.addAll(path.pathElements);
    }

    /**
     * Move point to (x,y)
     *
     * @param x
     * @param y
     * @since 0.0.1
     */
    public void moveTo(float x, float y) {
        moveTo(x, y, false);
    }

    /**
     * Move point to (x,y)/(x0+x,y0+y)(if isRelative is true)
     *
     * @param x
     * @param y
     * @param isRelative Indicates whether the relative distance,true means that relative
     * @since 0.0.1
     */
    public void moveTo(float x, float y, boolean isRelative) {
        pathElements.add(new SVGPathElement(SVGPathElement.PathElementType.M, new float[]{x, y}, isRelative));
    }

    /**
     * draw line to (x,y)
     *
     * @param x
     * @param y
     * @since 0.0.1
     */
    public void lineTo(float x, float y) {
        lineTo(x, y, false);
    }

    /**
     * draw line to (x,y)/(x0+x,y0+y)(if isRelative is true)
     *
     * @param x
     * @param y
     * @param isRelative Indicates whether the relative distance,true means that relative
     * @since 0.0.1
     */
    public void lineTo(float x, float y, boolean isRelative) {
        pathElements.add(new SVGPathElement(SVGPathElement.PathElementType.L, new float[]{x, y}, isRelative));
    }

    /**
     * draw hor line to(x,y0)
     *
     * @param x
     * @since 0.0.1
     */
    public void horizontalLineTo(float x) {
        horizontalLineTo(x, false);
    }

    /**
     * draw hor line to(x,y0)/(x0+x,y0)(if isRelative is true)
     *
     * @param x
     * @param isRelative Indicates whether the relative distance,true means that relative
     * @since 0.0.1
     */
    public void horizontalLineTo(float x, boolean isRelative) {
        pathElements.add(new SVGPathElement(SVGPathElement.PathElementType.H, new float[]{x}, isRelative));
    }

    /**
     * draw ver line to(x0,y)
     *
     * @param y
     * @since 0.0.1
     */
    public void verticalLineTo(float y) {
        verticalLineTo(y, false);
    }

    /**
     * draw ver line to(x0,y)/(x0,y0+y)(if isRelative is true)
     *
     * @param y
     * @param isRelative Indicates whether the relative distance,true means that relative
     * @since 0.0.1
     */
    public void verticalLineTo(float y, boolean isRelative) {
        pathElements.add(new SVGPathElement(SVGPathElement.PathElementType.V, new float[]{y}, isRelative));
    }

    /**
     * curveTo (ex,ey)
     * Cubic Bezier curve
     *
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @param ex
     * @param ey
     * @since 0.0.1
     */
    public void curveTo(float x1, float y1, float x2, float y2, float ex, float ey) {
        curveTo(x1, y1, x2, y2, ex, ey, false);
    }

    /**
     * curveTo (ex,ey)/(x0+ex,y0+ey)(if isRelative is true)
     * Cubic Bezier curve
     *
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @param ex
     * @param ey
     * @param isRelative Indicates whether the relative distance,true means that relative
     * @since 0.0.1
     */
    public void curveTo(float x1, float y1, float x2, float y2, float ex, float ey, boolean isRelative) {
        pathElements.add(new SVGPathElement(SVGPathElement.PathElementType.C,
                new float[]{x1, y1, x2, y2, ex, ey},
                isRelative));
    }

    /**
     * curveTo (ex,ey)
     * When multiple cubic Bezier curves are connected, the control points of the previous curve will be used to smoothly connect
     * Some times we use like this
     * <pre>
     * SVGPath path = new SVGPath();
     * path.moveTo(10,10);
     * path.curveTo(15,13,18,20,10,30);
     * path.smoothCurveTo(20,30,10,40);
     * </pre>
     *
     * @param x2
     * @param y2
     * @param ex
     * @param ey
     * @since 0.0.1
     */
    public void smoothCurveTo(float x2, float y2, float ex, float ey) {
        smoothCurveTo(x2, y2, ex, ey, false);
    }

    /**
     * curveTo (ex,ey)/(x0+ex,y0+ey)(if isRelative is true)
     * When multiple cubic Bezier curves are connected, the control points of the previous curve will be used to smoothly connect
     *
     * @param x2
     * @param y2
     * @param ex
     * @param ey
     * @param isRelative Indicates whether the relative distance,true means that relative
     * @see #smoothCurveTo(float, float, float, float)
     * @since 0.0.1
     */
    public void smoothCurveTo(float x2, float y2, float ex, float ey, boolean isRelative) {
        pathElements.add(new SVGPathElement(SVGPathElement.PathElementType.C,
                new float[]{x2, y2, ex, ey},
                isRelative));
    }

    /**
     * curveTo(ex,ey)
     * Quadratic bezier curve
     *
     * @param x1
     * @param y1
     * @param ex
     * @param ey
     * @since 0.0.1
     */
    public void quadraticBelzierCurve(float x1, float y1, float ex, float ey) {
        quadraticBelzierCurve(x1, y1, ex, ey, false);
    }

    /**
     * curveTo(ex,ey)/(x0+ex,y0+ey)(if isRelative is true)
     * Quadratic bezier curve
     *
     * @param x1
     * @param y1
     * @param ex
     * @param ey
     * @param isRelative Indicates whether the relative distance,true means that relative
     * @since 0.0.1
     */
    public void quadraticBelzierCurve(float x1, float y1, float ex, float ey, boolean isRelative) {
        pathElements.add(new SVGPathElement(SVGPathElement.PathElementType.Q,
                new float[]{x1, y1, ex, ey},
                isRelative));
    }

    /**
     * curveTo(x,y)
     * When connecting multiple quadratic Bezier curves, use the control points of the previous curve to smoothly connect
     *
     * @param x
     * @param y
     * @since 0.0.1
     */
    public void smoothQuadraticBelzierCurve(float x, float y) {
        smoothQuadraticBelzierCurve(x, y, false);
    }

    /**
     * curveTo(x,y)/(x0+x,y0+y)(if isRelative is true)
     * When connecting multiple quadratic Bezier curves, use the control points of the previous curve to smoothly connect
     *
     * @param x
     * @param y
     * @param isRelative Indicates whether the relative distance,true means that relative
     * @since 0.0.1
     */
    public void smoothQuadraticBelzierCurve(float x, float y, boolean isRelative) {
        pathElements.add(new SVGPathElement(SVGPathElement.PathElementType.T, new float[]{x, y}, isRelative));
    }

    /**
     * arcTo(x,y)
     *
     * @param rx
     * @param ry
     * @param rotation  The rotation of axis-x
     * @param isLarge   1 means that large arc
     * @param sweepFlag 1 means that arcTo with clockwise
     * @param x
     * @param y
     * @since 0.0.1
     */
    public void ellipticalArc(float rx, float ry, float rotation, int isLarge, int sweepFlag, float x, float y) {
        ellipticalArc(rx, ry, rotation, isLarge, sweepFlag, x, y, false);
    }

    /**
     * arcTo(x,y)/(x0+x,y0+y)(if isRelative is true)
     *
     * @param rx
     * @param ry
     * @param rotation   The rotation of axis-x
     * @param isLarge    1 means that large arc
     * @param sweepFlag  1 means that arcTo with clockwise
     * @param x
     * @param y
     * @param isRelative Indicates whether the relative distance,true means that relative
     * @since 0.0.1
     */
    public void ellipticalArc(float rx, float ry, float rotation, int isLarge, int sweepFlag, float x, float y, boolean isRelative) {
        pathElements.add(new SVGPathElement(SVGPathElement.PathElementType.A,
                new float[]{rx, ry, rotation, isLarge, sweepFlag, x, y},
                isRelative));
    }

    /**
     * draw oval
     * Conveniently draw ellipse,it will moveTo(x-rx,y)
     *
     * @param x
     * @param y
     * @param rx
     * @param ry
     * @since 0.0.1
     */
    public void oval(float x, float y, float rx, float ry) {
        moveTo(x - rx, y);
        ellipticalArc(rx, ry, 0, 1, 0, x + rx, y);
        ellipticalArc(rx, ry, 0, 1, 0, x - rx, y);
    }

    /**
     * draw a rect
     * Conveniently draw rectangles,it will not move to(x,y)
     *
     * @param x
     * @param y
     * @param width
     * @param height
     * @since 0.0.1
     */
    public void rect(float x, float y, float width, float height) {
        moveTo(x, y);
        horizontalLineTo(width, true);
        verticalLineTo(height, true);
        horizontalLineTo(-width, true);
        verticalLineTo(-height, true);
    }

    /**
     * Close path
     * Connect start and end
     *
     * @since 0.0.1
     */
    public void closePath() {
        pathElements.add(new SVGPathElement(SVGPathElement.PathElementType.Z, null));
    }

    /**
     * The path command iterator
     *
     * @return
     * @since 0.0.1
     */
    public Iterator<SVGPathElement> iterator() {
        return pathElements.listIterator();
    }

    public Object clone() {
        return new SVGPath(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SVGPath path = (SVGPath) o;
        return Objects.equals(pathElements, path.pathElements);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pathElements);
    }
}