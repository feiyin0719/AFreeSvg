package com.yf.afreesvg;

import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.shapes.Shape;

import com.yf.afreesvg.util.DoubleFunction;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;

import java.io.OutputStream;
import java.io.StringWriter;
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


public class SVGCanvas {
    /**
     * The prefix for keys used to identify clip paths.
     */
    private static final String CLIP_KEY_PREFIX = "clip-";

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

    private final Document document;

    private Matrix transform;

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

    public void drawLine(float x1, float y1, float x2, float y2, Paint paint) {
        drawLine(x1, y1, x2, y2, paint, null, null);
    }

    public void drawLine(float x1, float y1, float x2, float y2, Paint paint, float dashArray[]) {
        drawLine(x1, y1, x2, y2, paint, dashArray, null);
    }

    public void drawLine(float x1, float y1, float x2, float y2, Paint paint, String id) {
        drawLine(x1, y1, x2, y2, paint, null, null);
    }

    public void drawLine(float x1, float y1, float x2, float y2, Paint paint, float dashArray[], String id) {
        Element element = document.createElement("line");
        setElementId(element, id);
        element.setAttribute("x1", geomDP(x1));
        element.setAttribute("y1", geomDP(y1));
        element.setAttribute("x2", geomDP(x2));
        element.setAttribute("y2", geomDP(y2));
        element.setAttribute("style", style(paint, dashArray));
        addTransformToElement(element);
        svgElement.appendChild(element);

    }

    public void drawRect(RectF rectF, Paint paint) {
        drawRect(rectF, paint, null, null);
    }

    public void drawRect(RectF rectF, Paint paint, float dashArray[]) {
        drawRect(rectF, paint, dashArray, null);
    }

    public void drawRect(RectF rectF, Paint paint, String id) {
        drawRect(rectF, paint, null, id);
    }

    public void drawRect(RectF rectF, Paint paint, float dashArray[], String id) {
        Element element = document.createElement("rect");
        setElementId(element, id);
        element.setAttribute("x", "" + rectF.left);
        element.setAttribute("y", "" + rectF.top);
        element.setAttribute("width", "" + rectF.width());
        element.setAttribute("height", "" + rectF.height());
        element.setAttribute("style", style(paint, dashArray));
        addTransformToElement(element);
        svgElement.appendChild(element);
    }

    public void drawOval(RectF rectF, Paint paint) {
        drawOval(rectF, paint, null, null);
    }

    public void drawOval(RectF rectF, Paint paint, float dashArray[]) {
        drawOval(rectF, paint, dashArray, null);
    }

    public void drawOval(RectF rectF, Paint paint, String id) {
        drawOval(rectF, paint, null, id);
    }

    public void drawOval(RectF rectF, Paint paint, float dashArray[], String id) {
        Element element = document.createElement("ellipse");
        setElementId(element, id);
        element.setAttribute("cx", "" + rectF.centerX());
        element.setAttribute("cy", "" + rectF.centerY());
        element.setAttribute("rx", "" + rectF.width() / 2);
        element.setAttribute("ry", "" + rectF.height() / 2);
        element.setAttribute("style", style(paint, dashArray));
        addTransformToElement(element);
        svgElement.appendChild(element);
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
        return svgElement;
    }

    public void setTransform(Matrix matrix) {
        transform = matrix;
    }

    public Matrix getTransform() {
        return transform;
    }

    public void resetTransform() {
        transform = null;
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

    private String style(Paint paint, float[] dashArray) {
        if (paint == null || paint.getStyle() == Paint.Style.STROKE) {
            return strokeStyle(paint, dashArray, true);
        } else if (paint.getStyle() == Paint.Style.FILL)
            return getSVGFillStyle(paint);
        else
            return strokeStyle(paint, dashArray, false) + ";" + getSVGFillStyle(paint);
    }

    /**
     * Returns a fill style string based on the current paint and
     * alpha settings.
     *
     * @return A fill style string.
     */
    private String getSVGFillStyle(Paint paint) {
        StringBuilder b = new StringBuilder();
        b.append("fill:").append(svgColorStr(paint));
        double opacity = getColorAlpha(paint.getAlpha());
        if (opacity < 1.0) {
            b.append(';').append("fill-opacity:").append(opacity);
        }
        return b.toString();
    }

    private String strokeStyle(Paint paint, float[] dashArray, boolean needFillAlpha) {

        double strokeWidth = 1.0f;
        String strokeCap = DEFAULT_STROKE_CAP;
        String strokeJoin = DEFAULT_STROKE_JOIN;
        float miterLimit = DEFAULT_MITER_LIMIT;
        int alpha = 255;
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
            alpha = paint.getAlpha();
        }

        StringBuilder b = new StringBuilder();
        b.append("stroke-width:").append(strokeWidth).append(";");
        b.append("stroke:").append(svgColorStr(paint)).append(";");
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
        if (needFillAlpha)
            b.append(";fill-opacity:0.0");
        return b.toString();
    }


    /**
     * Returns an SVG color string based on the current paint.  To handle
     * {@code GradientPaint} we rely on the {@code setPaint()} method
     * having set the {@code gradientPaintRef} attribute.
     *
     * @return An SVG color string.
     */
    private String svgColorStr(Paint paint) {
        return rgbColorStr(paint.getColor());
    }

    /**
     * Returns the SVG RGB color string for the specified color.
     *
     * @param color the color ({@code null} not permitted).
     * @return The SVG RGB color string.
     */

    private String rgbColorStr(int color) {
        StringBuilder b = new StringBuilder("rgb(");
        b.append(((color >> 16) & 0xff)).append(",").append(((color >> 8) & 0xff)).append(",")
                .append((color) & 0xff).append(")");
        return b.toString();
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


    /**
     * A utility method that appends an optional element id if one is
     * specified via the rendering hints.
     *
     * @param builder the string builder ({@code null} not permitted).
     */
    private void setElementId(StringBuilder builder, String elementID) {

        if (elementID != null) {
            if (this.elementIDs.contains(elementID)) {
                throw new IllegalStateException("The element id "
                        + elementID + " is already used.");
            } else {
                this.elementIDs.add(elementID);
            }
            builder.append(" id='").append(elementID).append("'");
        }
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
