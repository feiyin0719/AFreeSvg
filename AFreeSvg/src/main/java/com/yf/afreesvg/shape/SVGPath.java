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

public class SVGPath extends SVGBaseShape {
    @Override
    public Element convertToSVGElement(SVGCanvas canvas, Document document, DoubleFunction<String> convert) {
        Element element = document.createElement("path");
        element.setAttribute("d", getSVGPathD(convert));
        addBaseAttr(element);
        return element;
    }

    private String getSVGPathD(DoubleFunction<String> convert) {
        Iterator<SVGPath.SVGPathElement> iterator = iterator();
        StringBuilder sb = new StringBuilder();
        while (iterator.hasNext()) {
            SVGPath.SVGPathElement pathElement = iterator.next();
            sb.append(pathElement.valueStr(convert));
        }
        return sb.toString();
    }

    public static class SVGPathElement {
        public static final String M = "M";
        public static final String L = "L";
        public static final String H = "H";
        public static final String V = "V";
        public static final String C = "C";
        public static final String S = "S";
        public static final String Q = "Q";
        public static final String T = "T";
        public static final String A = "A";
        public static final String Z = "Z";

        @StringDef({M, L, H, V, C, S, Q, T, A, Z})
        @Retention(RetentionPolicy.SOURCE)
        public @interface PathElementType {
        }

        private @PathElementType
        String type;

        private float[] data;

        private boolean isRelative;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public float[] getData() {
            return data;
        }

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

        public String valueStr(DoubleFunction convert) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(isRelative ? type.toLowerCase() : type).append(" ");
            if (type != A) {
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

    private List<SVGPathElement> pathElements = new ArrayList<>();

    public SVGPath() {
    }

    public SVGPath(SVGPath path) {
        pathElements.addAll(path.pathElements);
    }

    public void moveTo(float x, float y) {
        moveTo(x, y, false);
    }

    public void moveTo(float x, float y, boolean isRelative) {
        pathElements.add(new SVGPathElement(SVGPathElement.M, new float[]{x, y}, isRelative));
    }

    public void lineTo(float x, float y) {
        lineTo(x, y, false);
    }

    public void lineTo(float x, float y, boolean isRelative) {
        pathElements.add(new SVGPathElement(SVGPathElement.L, new float[]{x, y}, isRelative));
    }

    public void horizontalLineTo(float x) {
        horizontalLineTo(x, false);
    }

    public void horizontalLineTo(float x, boolean isRelative) {
        pathElements.add(new SVGPathElement(SVGPathElement.H, new float[]{x}, isRelative));
    }

    public void verticalLineTo(float y) {
        verticalLineTo(y, false);
    }

    public void verticalLineTo(float y, boolean isRelative) {
        pathElements.add(new SVGPathElement(SVGPathElement.V, new float[]{y}, isRelative));
    }

    public void curveTo(float x1, float y1, float x2, float y2, float ex, float ey) {
        curveTo(x1, y1, x2, y2, ex, ey, false);
    }

    public void curveTo(float x1, float y1, float x2, float y2, float ex, float ey, boolean isRelative) {
        pathElements.add(new SVGPathElement(SVGPathElement.C,
                new float[]{x1, y1, x2, y2, ex, ey},
                isRelative));
    }

    public void smoothCurveTo(float x2, float y2, float ex, float ey) {
        smoothCurveTo(x2, y2, ex, ey, false);
    }

    public void smoothCurveTo(float x2, float y2, float ex, float ey, boolean isRelative) {
        pathElements.add(new SVGPathElement(SVGPathElement.C,
                new float[]{x2, y2, ex, ey},
                isRelative));
    }

    public void quadraticBelzierCurve(float x1, float y1, float ex, float ey) {
        quadraticBelzierCurve(x1, y1, ex, ey, false);
    }

    public void quadraticBelzierCurve(float x1, float y1, float ex, float ey, boolean isRelative) {
        pathElements.add(new SVGPathElement(SVGPathElement.Q,
                new float[]{x1, y1, ex, ey},
                isRelative));
    }

    public void smoothQuadraticBelzierCurve(float x, float y) {
        smoothQuadraticBelzierCurve(x, y, false);
    }

    public void smoothQuadraticBelzierCurve(float x, float y, boolean isRelative) {
        pathElements.add(new SVGPathElement(SVGPathElement.T, new float[]{x, y}, isRelative));
    }


    public void ellipticalArc(float rx, float ry, float rotation, int isLarge, int sweepFlag, float x, float y) {
        ellipticalArc(rx, ry, rotation, isLarge, sweepFlag, x, y, false);
    }

    public void ellipticalArc(float rx, float ry, float rotation, int isLarge, int sweepFlag, float x, float y, boolean isRelative) {
        pathElements.add(new SVGPathElement(SVGPathElement.A,
                new float[]{rx, ry, rotation, isLarge, sweepFlag, x, y},
                isRelative));
    }

    public void oval(float x, float y, float rx, float ry) {
        moveTo(x - rx, y);
        ellipticalArc(rx, ry, 0, 1, 0, x + rx, y);
        ellipticalArc(rx, ry, 0, 1, 0, x - rx, y);
    }

    public void rect(float x, float y, float width, float height) {
        moveTo(x, y);
        horizontalLineTo(width, true);
        verticalLineTo(height, true);
        horizontalLineTo(-width, true);
        verticalLineTo(-height, true);
    }

    public void closePath() {
        pathElements.add(new SVGPathElement(SVGPathElement.Z, null));
    }

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