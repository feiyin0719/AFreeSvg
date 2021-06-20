package com.yf.afreesvg;

import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.drawable.shapes.Shape;

import com.yf.afreesvg.gradient.SVGBaseGradient;
import com.yf.afreesvg.gradient.SVGGradient;
import com.yf.afreesvg.gradient.SVGLinearGradient;
import com.yf.afreesvg.gradient.SVGRadialGradient;
import com.yf.afreesvg.util.DoubleFunction;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.OutputStream;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;


/**
 * The type Svg canvas.
 */
public class SVGCanvas {
    /**
     * The prefix for keys used to identify clip paths.
     */
    private static final String CLIP_KEY_PREFIX = "clip-";

    /**
     * A prefix for the keys used in the DEFS element.  This can be used to
     * ensure that the keys are unique when creating more than one SVG element
     * for a single HTML page.
     */
    private String defsKeyPrefix = "_" + System.nanoTime();

    private static final String DEFAULT_STROKE_CAP = "butt";
    private static final String DEFAULT_STROKE_JOIN = "miter";
    private static final float DEFAULT_MITER_LIMIT = 4.0f;

    /**
     * The width of the SVG.
     */
    private final double width;

    /**
     * The height of the SVG.
     */
    private final double height;

    /**
     * The user clip (can be null).
     */
    private Shape clip;

    /**
     * The reference for the current clip.
     */
    private String clipRef;

    /**
     * The function used to convert double values to strings for the geometry
     * coordinates in the SVG output.
     */
    private DoubleFunction<String> geomDoubleConverter;

    /**
     * The function used to convert double values to strings when writing
     * matrix values for transforms in the SVG output.
     */
    private DoubleFunction<String> transformDoubleConverter;

    /**
     * A map of all the gradients used, and the corresponding id.  When
     * generating the SVG file, all the gradient paints used must be defined
     * in the defs element.
     */
    private Map<SVGGradient, String> gradients = new HashMap<>();

    /**
     * Units for the width and height of the SVG, if null then no
     * unit information is written in the SVG output.  This is set via
     * the class constructors.
     */
    private final SVGUnits units;

    /**
     * A set of element IDs.
     */
    private final Set<String> elementIDs;


    private final Element svgElement;

    private Element defElement;

    private final Document document;

    private Matrix transform = new Matrix();

    public SVGCanvas(double width, double height) throws ParserConfigurationException {
        this(width, height, null);
    }

    public SVGCanvas(double width, double height, SVGUnits units) throws ParserConfigurationException {
        this.width = width;
        this.height = height;

        this.units = units;
        elementIDs = new TreeSet<>();

        DocumentBuilderFactory factory = DocumentBuilderFactory
                .newInstance();
        DocumentBuilder builder = null;

        builder = factory.newDocumentBuilder();
        document = builder.newDocument();

        initXmlVersion();
        svgElement = document.createElement("svg");
        document.appendChild(svgElement);

        geomDoubleConverter = new DoubleFunction<String>() {
            @Override
            public String apply(double value) {
                return SVGUtils.doubleToString(value);
            }
        };
        transformDoubleConverter = new DoubleFunction<String>() {
            @Override
            public String apply(double value) {
                return SVGUtils.doubleToString(value);
            }
        };
    }

    public void drawLine(float x1, float y1, float x2, float y2, SVGPaint paint) {
        drawLine(x1, y1, x2, y2, paint, null);
    }


    public void drawLine(float x1, float y1, float x2, float y2, SVGPaint paint, String id) {
        Element element = document.createElement("line");
        setElementId(element, id);
        element.setAttribute("x1", geomDP(x1));
        element.setAttribute("y1", geomDP(y1));
        element.setAttribute("x2", geomDP(x2));
        element.setAttribute("y2", geomDP(y2));
        element.setAttribute("style", style(paint));
        addTransformToElement(element);
        svgElement.appendChild(element);

    }

    public void drawRect(RectF rectF, SVGPaint paint) {
        drawRect(rectF, paint, null);
    }


    public void drawRect(RectF rectF, SVGPaint paint, String id) {
        Element element = document.createElement("rect");
        setElementId(element, id);
        element.setAttribute("x", "" + rectF.left);
        element.setAttribute("y", "" + rectF.top);
        element.setAttribute("width", "" + rectF.width());
        element.setAttribute("height", "" + rectF.height());
        element.setAttribute("style", style(paint));
        addTransformToElement(element);
        svgElement.appendChild(element);
    }

    public void drawOval(RectF rectF, SVGPaint paint) {
        drawOval(rectF, paint, null);
    }


    public void drawOval(RectF rectF, SVGPaint paint, String id) {
        Element element = document.createElement("ellipse");
        setElementId(element, id);
        element.setAttribute("cx", "" + rectF.centerX());
        element.setAttribute("cy", "" + rectF.centerY());
        element.setAttribute("rx", "" + rectF.width() / 2);
        element.setAttribute("ry", "" + rectF.height() / 2);
        element.setAttribute("style", style(paint));
        addTransformToElement(element);
        svgElement.appendChild(element);
    }

    public void drawPolygon(float[] points, SVGPaint paint) {
        drawPolygon(points, paint, null);

    }

    public void drawPolygon(float[] points, SVGPaint paint, String id) {
        if (points == null || points.length < 6) {
            throw new IllegalArgumentException("points is null or points length < 6");
        }

        drawPolygon(convertPoints(points), paint, id);

    }

    public void drawPolygon(PointF points[], SVGPaint paint) {
        drawPolygon(points, paint, null);
    }

    public void drawPolygon(PointF points[], SVGPaint paint, String id) {
        if (points == null || points.length < 3) {
            throw new IllegalArgumentException("points is null or points length <3");
        }
        Element element = document.createElement("polygon");
        setElementId(element, id);
        element.setAttribute("points", getPointsStr(points));
        element.setAttribute("style", style(paint));
        addTransformToElement(element);
        svgElement.appendChild(element);
    }

    public void drawPolyline(float points[], SVGPaint paint) {
        drawPolyline(points, paint, null);
    }

    public void drawPolyline(float points[], SVGPaint paint, String id) {
        drawPolyline(convertPoints(points), paint, id);
    }

    public void drawPolyline(PointF points[], SVGPaint paint) {
        drawPolyline(points, paint, null);
    }

    public void drawPolyline(PointF points[], SVGPaint paint, String id) {
        if (points == null || points.length < 2) {
            throw new IllegalArgumentException("points is null or points length <2");
        }
        Element element = document.createElement("polyline");
        setElementId(element, id);
        element.setAttribute("points", getPointsStr(points));
        element.setAttribute("style", style(paint));
        addTransformToElement(element);
        svgElement.appendChild(element);
    }

    private PointF[] convertPoints(float points[]) {
        PointF pointF[] = new PointF[points.length / 2];
        for (int i = 0; i < points.length; i += 2)
            pointF[i / 2] = new PointF(points[i], points[i + 1]);
        return pointF;
    }

    private String getPointsStr(PointF points[]) {
        StringBuilder sb = new StringBuilder();
        if (points.length > 0) {
            for (int i = 0; i < points.length; ++i)
                sb.append(" " + points[i].x + "," + points[i].y);
        }
        return sb.toString();
    }


    public Element getSVGElement() {
        return getSVGElement(null, true, null, null, null);
    }


    public Element getSVGElement(String id, boolean includeDimensions,
                                 ViewBox viewBox, PreserveAspectRatio preserveAspectRatio,
                                 MeetOrSlice meetOrSlice) {
        if (id != null) {
            svgElement.setAttribute("id", id);
        }
        svgElement.setAttribute("xmlns", "http://www.w3.org/2000/svg");
        svgElement.setAttribute("xmlns:xlink", "http://www.w3.org/1999/xlink");
        svgElement.setAttribute("xmlns:jfreesvg", "http://www.jfree.org/jfreesvg/svg");

        if (includeDimensions) {
            String unitStr = this.units != null ? this.units.toString() : "";
            svgElement.setAttribute("width", geomDP(width) + unitStr);
            svgElement.setAttribute("height", geomDP(height) + unitStr);

        }
        if (viewBox != null) {
            svgElement.setAttribute("viewBox", viewBox.valueStr(this.geomDoubleConverter));
            if (preserveAspectRatio != null) {
                svgElement.setAttribute("preserveAspectRatio", preserveAspectRatio.toString() + (meetOrSlice == null ? "" : " " + meetOrSlice.toString()));

            }
        }
        if (defElement != null) {
            NodeList list = svgElement.getElementsByTagName("defs");
            if (list != null && list.getLength() > 0)
                svgElement.removeChild(defElement);
            svgElement.appendChild(defElement);
        }
        return svgElement;
    }

    public void setTransform(Matrix matrix) {
        transform = matrix;
    }

    public Matrix getTransform() {
        return transform;
    }

    public void resetTransform() {
        transform.reset();
    }

    public DoubleFunction<String> getGeomDoubleConverter() {
        return geomDoubleConverter;
    }

    public void setGeomDoubleConverter(DoubleFunction<String> geomDoubleConverter) {
        this.geomDoubleConverter = geomDoubleConverter;
    }

    public DoubleFunction<String> getTransformDoubleConverter() {
        return transformDoubleConverter;
    }

    public void setTransformDoubleConverter(DoubleFunction<String> transformDoubleConverter) {
        this.transformDoubleConverter = transformDoubleConverter;
    }

    public String getSVGXmlString() throws TransformerException {

        Transformer transformer = getSVGXMLTransformer();
        DOMSource source = new DOMSource(document);
        StringWriter stringWriter = new StringWriter();
        StreamResult result = new StreamResult(stringWriter);
        transformer.transform(source, result);
        return stringWriter.toString();
    }

    public void writeSVGXMLToStream(OutputStream outputStream) throws TransformerException {
        Transformer transformer = getSVGXMLTransformer();
        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(outputStream);
        transformer.transform(source, result);
    }

    private Transformer getSVGXMLTransformer() throws TransformerConfigurationException {
        getSVGElement();
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        DOMImplementation domImpl = document.getImplementation();
        DocumentType doctype = domImpl.createDocumentType("doctype",
                "-//W3C//DTD SVG 1.0//EN",
                "http://www.w3.org/TR/2001/REC-SVG-20010904/DTD/svg10.dtd");
        transformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, doctype.getPublicId());
        transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, doctype.getSystemId());
        return transformer;
    }


    private void initXmlVersion() {
        document.setXmlStandalone(true);
        document.setXmlVersion("1.0");
    }


    /**
     * Returns a string representation of the specified number for use in the
     * SVG output.
     *
     * @param d the number.
     * @return A string representation of the number.
     */
    private String geomDP(final double d) {
        return this.geomDoubleConverter.apply(d);
    }

    private String style(SVGPaint paint) {
        if (paint == null || paint.getStyle() == Paint.Style.STROKE) {
            return strokeStyle(paint, true);
        } else if (paint.getStyle() == Paint.Style.FILL)
            return getSVGFillStyle(paint);
        else
            return strokeStyle(paint, false) + ";" + getSVGFillStyle(paint);
    }

    /**
     * Returns a fill style string based on the current paint and
     * alpha settings.
     *
     * @return A fill style string.
     */
    private String getSVGFillStyle(SVGPaint paint) {
        StringBuilder b = new StringBuilder();
        b.append("fill:").append(svgColorStr(paint));

        double opacity = getColorAlpha(paint.getAlpha());
        if (opacity < 1.0) {
            b.append(';').append("fill-opacity:").append(opacity);
        }
        if (!paint.getFillRule().equals(SVGPaint.FILL_RULE_DEFAULT)) {
            b.append(';').append("fill-rule:" + paint.getFillRule());
        }
        return b.toString();
    }

    private String addGradient(SVGGradient gradient) {
        if (!gradients.containsKey(gradient)) {
            String id = this.defsKeyPrefix + "gp" + gradients.size();
            gradients.put(gradient, id);
            addElementToDef(getGradientElement(id, gradient));
            return id;
        }
        return gradients.get(gradient);
    }

    private String strokeStyle(SVGPaint paint, boolean needFillNone) {

        double strokeWidth = 1.0f;
        String strokeCap = DEFAULT_STROKE_CAP;
        String strokeJoin = DEFAULT_STROKE_JOIN;
        float miterLimit = DEFAULT_MITER_LIMIT;
        int alpha = 255;
        String strokeColor = "white";
        float dashArray[] = null;
        if (paint != null) {
            if (paint.getStrokeWidth() > 0)
                strokeWidth = paint.getStrokeWidth();
            switch (paint.getStrokeCap()) {
                case ROUND:
                    strokeCap = "round";
                    break;
                case SQUARE:
                    strokeCap = "square";
                    break;
                case BUTT:
                default:
                    // already set to "butt"
            }
            switch (paint.getStrokeJoin()) {
                case BEVEL:
                    strokeJoin = "bevel";
                    break;
                case ROUND:
                    strokeJoin = "round";
                    break;
                case MITER:
                default:
                    // already set to "miter"
            }
            miterLimit = paint.getStrokeMiter();

            dashArray = paint.getDashArray();
            strokeColor = svgColorStr(paint);
            alpha = paint.getAlpha();
        }

        StringBuilder b = new StringBuilder();
        b.append("stroke-width:").append(strokeWidth).append(";");
        b.append("stroke:").append(strokeColor).append(";");
        if (alpha < 255)
            b.append("stroke-opacity:").append(getColorAlpha(alpha));
        if (!strokeCap.equals(DEFAULT_STROKE_CAP)) {
            b.append(";stroke-linecap:").append(strokeCap);
        }
        if (!strokeJoin.equals(DEFAULT_STROKE_JOIN)) {
            b.append(";stroke-linejoin:").append(strokeJoin);
        }
        if (Math.abs(DEFAULT_MITER_LIMIT - miterLimit) > 0.001) {
            b.append(";stroke-miterlimit:").append(geomDP(miterLimit));
        }
        if (dashArray != null && dashArray.length != 0) {
            b.append(";stroke-dasharray:");
            for (int i = 0; i < dashArray.length; i++) {
                if (i != 0) b.append(",");
                b.append(dashArray[i]);
            }
        }
        if (needFillNone)
            b.append(";fill:none");
        return b.toString();
    }

    private Element getGradientElement(String id, SVGGradient gradient) {
        if (gradient == null) return null;
        if (gradient instanceof SVGLinearGradient) {
            return getLinearGradientElement(id, (SVGLinearGradient) gradient);
        } else if (gradient instanceof SVGRadialGradient) {
            return getRadialGradientElement(id, (SVGRadialGradient) gradient);
        }
        return null;
    }


    /**
     * Returns an element to represent a linear gradient.  All the linear
     * gradients that are used get written to the DEFS element in the SVG.
     *
     * @param id       the reference id.
     * @param gradient the gradient.
     * @return The SVG element.
     */
    private Element getLinearGradientElement(String id, SVGLinearGradient gradient) {

        Element element = document.createElement("linearGradient");
        setElementId(element, id);

        PointF p1 = gradient.getStartPoint();
        PointF p2 = gradient.getEndPoint();
        element.setAttribute("x1", geomDP(p1.x));
        element.setAttribute("y1", geomDP(p1.y));
        element.setAttribute("x2", geomDP(p2.x));
        element.setAttribute("y2", geomDP(p2.y));
        if (gradient.getPosMode() == SVGBaseGradient.MODE_USERSPACE)
            element.setAttribute("gradientUnits", "userSpaceOnUse");
        for (int i = 0; i < gradient.getStopCount(); ++i) {
            Element stop = document.createElement("stop");
            long c1 = gradient.getStopColor(i);
            stop.setAttribute("offset", "" + gradient.getStopOffset(i));
            stop.setAttribute("stop-color", rgbColorStr(c1));


            if (colorAlpha(c1) < 255) {
                double alphaPercent = colorAlpha(c1) / 255.0;
                stop.setAttribute("stop-opacity", transformDP(alphaPercent));

            }
            element.appendChild(stop);
        }

        return element;
    }

    private Element getRadialGradientElement(String id, SVGRadialGradient gradient) {

        Element element = document.createElement("radialGradient");
        setElementId(element, id);

        element.setAttribute("cx", geomDP(gradient.getCx()));
        element.setAttribute("cy", geomDP(gradient.getCy()));
        element.setAttribute("r", geomDP(gradient.getR()));
        element.setAttribute("fx", geomDP(gradient.getFx()));
        element.setAttribute("fy", geomDP(gradient.getFy()));
        if (gradient.getPosMode() == SVGBaseGradient.MODE_USERSPACE)
            element.setAttribute("gradientUnits", "userSpaceOnUse");
        for (int i = 0; i < gradient.getStopCount(); ++i) {
            Element stop = document.createElement("stop");
            long c1 = gradient.getStopColor(i);
            stop.setAttribute("offset", "" + gradient.getStopOffset(i));
            stop.setAttribute("stop-color", rgbColorStr(c1));


            if (colorAlpha(c1) < 255) {
                double alphaPercent = colorAlpha(c1) / 255.0;
                stop.setAttribute("stop-opacity", transformDP(alphaPercent));

            }
            element.appendChild(stop);
        }

        return element;
    }


    /**
     * Returns an SVG color string based on the current paint.  To handle
     * {@code GradientPaint} we rely on the {@code setPaint()} method
     * having set the {@code gradientPaintRef} attribute.
     *
     * @return An SVG color string.
     */
    private String svgColorStr(SVGPaint paint) {
        if (paint.getGradient() != null) {
            String id = addGradient(paint.gradient);
            return "url(#" + id + ")";
        }

        return rgbColorStr(paint.getColor());
    }

    /**
     * Returns the SVG RGB color string for the specified color.
     *
     * @param color the color ({@code null} not permitted).
     * @return The SVG RGB color string.
     */

    private String rgbColorStr(long color) {
        StringBuilder b = new StringBuilder("rgb(");
        b.append(((color >> 16) & 0xff)).append(",").append(((color >> 8) & 0xff)).append(",")
                .append((color) & 0xff).append(")");
        return b.toString();
    }

    private int colorAlpha(long color) {
        return (int) (color >> 24 & 0xff);
    }

    private float getColorAlpha(int alpha) {
        return alpha / 255.0f;
    }

    private void addTransformToElement(Element element) {
        if (transform != null && !transform.isIdentity())
            element.setAttribute("transform", getSVGTransform(transform));
    }

    private String getSVGTransform(Matrix t) {
        float value[] = new float[9];
        t.getValues(value);

        StringBuilder b = new StringBuilder("matrix(");
        b.append(transformDP(value[Matrix.MSCALE_X])).append(",");
        b.append(transformDP(value[Matrix.MSKEW_Y])).append(",");
        b.append(transformDP(value[Matrix.MSKEW_X])).append(",");
        b.append(transformDP(value[Matrix.MSCALE_Y])).append(",");
        b.append(transformDP(value[Matrix.MTRANS_X])).append(",");
        b.append(transformDP(value[Matrix.MTRANS_Y])).append(")");
        return b.toString();
    }

    /**
     * Returns a string representation of the specified number for use in the
     * SVG output.
     *
     * @param d the number.
     * @return A string representation of the number.
     */
    private String transformDP(final double d) {
        return this.transformDoubleConverter.apply(d);
    }


    private void addElementToDef(Element element) {
        if (element == null) return;
        if (defElement == null) {
            defElement = document.createElement("defs");
        }
        defElement.appendChild(element);
    }


    private void setElementId(Element element, String elementID) {

        if (elementID != null) {
            if (this.elementIDs.contains(elementID)) {
                throw new IllegalStateException("The element id "
                        + elementID + " is already used.");
            } else {
                this.elementIDs.add(elementID);
            }
            element.setAttribute("id", elementID);
        }
    }

}
