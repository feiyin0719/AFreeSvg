package com.yf.afreesvg;

import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.text.TextUtils;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;

import com.yf.afreesvg.filter.SVGFilter;
import com.yf.afreesvg.gradient.SVGGradient;
import com.yf.afreesvg.shape.SVGCircle;
import com.yf.afreesvg.shape.SVGClipShape;
import com.yf.afreesvg.shape.SVGLine;
import com.yf.afreesvg.shape.SVGOval;
import com.yf.afreesvg.shape.SVGPath;
import com.yf.afreesvg.shape.SVGPolygon;
import com.yf.afreesvg.shape.SVGPolyline;
import com.yf.afreesvg.shape.SVGRect;
import com.yf.afreesvg.shape.SVGShape;
import com.yf.afreesvg.shape.SVGTextPath;
import com.yf.afreesvg.util.DoubleFunction;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.OutputStream;
import java.io.StringWriter;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
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
 * The  Svg canvas.
 * You can use it to generate svg file,the api refer to {@link android.graphics.Canvas}
 * When draw shape,use {@link SVGPaint} to set style
 * <p>
 * if you want to learn about the format and attrs of svg,plz refer to http://www.verydoc.net/svg/
 *
 * @author iffly
 * @since 0.0.1
 */
public class SVGCanvas {
    /**
     * The prefix for keys used to identify clip paths.
     */
    private static final String CLIP_KEY_PREFIX = "clip-";
    /**
     * The prefix for keys used to identify  paths.
     */
    private static final String TEXT_PATH_KEY_PREFIX = "Path-";
    /**
     * The prefix for keys used to identify filters.
     */
    private static final String FILTER_KEY_PREFIX = "filter-";

    private static final String SVG_NAME = "svg";
    private static final String DEFS_NAME = "defs";

    /**
     * A prefix for the keys used in the DEFS element.  This can be used to
     * ensure that the keys are unique when creating more than one SVG element
     * for a single HTML page.
     */
    private String defsKeyPrefix = "def_" + System.nanoTime() + "_";

    /**
     * The default stroke style
     */
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
     * The clip shape (can be null).
     * When use clip,it will only draw into the clip shape.
     *
     * @see #clip(SVGClipShape)
     * @see #addClipToElement(Element)
     */
    private SVGClipShape clip;

    /**
     * The reference for the current clip.
     *
     * @see #registerClip(SVGClipShape)
     */
    private String clipRef;
    /**
     * The clipCount to generate clipRef
     *
     * @see #registerClip(SVGClipShape)
     */
    private int clipCount = 0;

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
     *
     * @see #addGradient(SVGGradient)
     */
    private Map<SVGGradient, String> gradients = new HashMap<>();
    /**
     * A map of all the filters used, and the corresponding id.  When
     * generating the SVG file, all the filters used must be defined
     * in the defs element.
     *
     * @see #addFilterElementToDef(SVGFilter, String)
     */
    private Map<SVGFilter, String> filters = new HashMap<>();
    /**
     * A map of all the paths used, and the corresponding id.  When
     * generating the SVG file, all the path used must be defined
     * in the defs element.
     *
     * @see #addPathElementToDef(SVGPath, String)
     */
    private Map<SVGPath, String> textPaths = new HashMap<>();

    /**
     * Units for the width and height of the SVG, if null then no
     * unit information is written in the SVG output.  This is set via
     * the class constructors.
     */
    private final SVGUnits units;

    private SVGUnits fontSizeUnit = SVGUnits.PX;

    /**
     * A set of element IDs.
     * It use to ensure id uniqueness
     *
     * @see #setElementId(Element, String)
     */
    private final Set<String> elementIDs;

    /**
     * The current layer svg element,the drawop will add to the element
     * if have not created new layer, it is {@link #rootSvgElement}
     * if create a new layer, it will create a new element
     * if restore,it will add now layer to the prev layer element and restore it to the prev layer
     */
    private Element svgElement;

    /**
     * The def element,when generate svg xml string,it will append to svgElement
     */
    private Element defElement;

    /**
     * The root svg element,use dom to generate svg xml string
     *
     * @since 0.0.4
     */
    private Element rootSvgElement;


    /**
     * The dom document,use it to generate element
     */
    private final Document document;
    /**
     * The transform of canvas.
     * It is used for canvas coordinate transformation.
     * <p>You can use {@link #translate(float, float)},{@link #rotate(float, float, float)},
     * {@link #skew(float, float, float, float)},{@link #scale(float, float)} to transform canvas
     * coordinate.
     * </p>
     * it will be save when use {@link #save()}
     */
    private Matrix transform = new Matrix();

    /**
     * The clip save flags.
     *
     * @see Saveflags
     * @see #save(int)
     */
    public static final int SAVE_FLAG_CLIP = 0x01;
    /**
     * The matrix save flags.
     *
     * @see Saveflags
     * @see #save(int)
     */
    public static final int SAVE_FLAG_MATRIX = 0x02;

    /**
     * The layer save flags.
     *
     * @see #saveLayer(float, float, float, float)
     */
    public static final int SAVE_FLAG_LAYER = 0x04;

    @IntDef(flag = true,
            value = {
                    SAVE_FLAG_CLIP,
                    SAVE_FLAG_MATRIX,
                    SAVE_FLAG_CLIP | SAVE_FLAG_MATRIX
            })
    @Retention(RetentionPolicy.SOURCE)
    public @interface Saveflags {
    }

    /**
     * The all save flags.
     *
     * @see #save(int)
     */
    public static final int SAVE_FLAG_ALL = 0xffff;
    /**
     * The save flags stack.
     * Pop the flag when restore,and do some action to restore canvas
     *
     * @see #SAVE_FLAG_ALL {@link #SAVE_FLAG_CLIP} {@link #SAVE_FLAG_MATRIX}
     * @see #save()  {@link #restore()}
     */
    private Stack<Integer> saveFlags = new Stack<>();
    /**
     * The  matrix stack of save.
     * When {@link #save()},it will push the current transform
     * When {@link #restore()},it will pop the transform
     *
     * @see #save()  {@link #restore()}
     */
    private Stack<Matrix> matrixList = new Stack<>();
    /**
     * The clips stack of save
     * When {@link #save()},it will push the current clip shape
     * When {@link #restore()},it will pop the clip shape
     *
     * @see #clip
     * @see #save()  {@link #restore()}
     */
    private Stack<SVGClipShape> clipShapes = new Stack<>();
    /**
     * The clipRef stack of save
     * When {@link #save()},it will push the current clipRef
     * When {@link #restore()},it will pop the clipRef
     *
     * @see #clipRef
     * @see #save()  {@link #restore()}
     */
    private Stack<String> clipRefs = new Stack<>();

    /**
     * The layer stack of save
     * when {@link #saveLayer(float, float, float, float)} it will push the current layer
     * when {@link #restore()}  it will pop the saved layer
     *
     * @see #svgElement
     * @see #saveLayer(float, float, float, float)
     */
    private Stack<Element> layerStack = new Stack<>();


    /**
     * Construct
     *
     * @param width  svg width
     * @param height svg height
     * @throws ParserConfigurationException
     */

    public SVGCanvas(double width, double height) throws ParserConfigurationException {
        this(width, height, null);
    }

    /**
     * Construct
     *
     * @param width  svg width
     * @param height svg height
     * @param units  svg size units
     * @throws ParserConfigurationException
     */
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
        initRootSvgElement();

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

    private void initRootSvgElement() {
        rootSvgElement = document.createElement(SVG_NAME);
        document.appendChild(rootSvgElement);

        svgElement = rootSvgElement;
    }

    /**
     * Return the svg font size unit
     *
     * @return the svg font size unit
     * @since 0.0.1
     */

    public SVGUnits getFontSizeUnit() {
        return fontSizeUnit;
    }

    /**
     * Set font size unit.
     *
     * @param fontSizeUnit it will be used when drawText
     * @see #drawTextOnPath(String, float, float, float, int, SVGPath, SVGPaint, String)
     * @since 0.0.1
     */
    public void setFontSizeUnit(SVGUnits fontSizeUnit) {
        this.fontSizeUnit = fontSizeUnit;
    }

    /**
     * Return the prefix of defs element id
     *
     * @return
     * @since 0.0.1
     */
    public String getDefsKeyPrefix() {
        return defsKeyPrefix;
    }

    /**
     * Set the prefix of defs element
     *
     * @param defsKeyPrefix
     * @since 0.0.1
     */
    public void setDefsKeyPrefix(@NonNull String defsKeyPrefix) {
        this.defsKeyPrefix = defsKeyPrefix;
    }

    /**
     * The method to draw line
     *
     * @param x1    The line start x
     * @param y1    The line start y
     * @param x2    The line end x
     * @param y2    The line end y
     * @param paint The line style paint {@link SVGPaint}
     * @see #drawShape(SVGShape, SVGPaint, String)
     * @see SVGLine
     * @since 0.0.1
     */

    public void drawLine(float x1, float y1, float x2, float y2, SVGPaint paint) {
        drawLine(x1, y1, x2, y2, paint, null);
    }

    /**
     * The method to draw line
     *
     * @param x1    The line start x
     * @param y1    The line start y
     * @param x2    The line end x
     * @param y2    The line end y
     * @param paint The line style paint {@link SVGPaint}
     * @param id    The line id
     * @see #drawShape(SVGShape, SVGPaint, String)
     * @see SVGLine
     * @since 0.0.1
     */

    public void drawLine(float x1, float y1, float x2, float y2, SVGPaint paint, String id) {
        SVGLine svgLine = new SVGLine(x1, y1, x2, y2);
        drawShape(svgLine, paint, id);

    }

    /**
     * The method to drawRect
     *
     * @param rectF The rect area
     * @param paint The paint {@link SVGPaint}
     * @see #drawShape(SVGShape, SVGPaint, String)
     * @see SVGRect
     * @since 0.0.1
     */
    public void drawRect(RectF rectF, SVGPaint paint) {
        drawRect(rectF, paint, null);
    }

    /**
     * The method to drawRect
     *
     * @param rectF The rect area
     * @param paint The paint {@link SVGPaint}
     * @param id    The element id
     * @see #drawShape(SVGShape, SVGPaint, String)
     * @see SVGRect
     * @since 0.0.1
     */
    public void drawRect(RectF rectF, SVGPaint paint, String id) {
        SVGRect rect = new SVGRect(rectF.left, rectF.top, rectF.width(), rectF.height());
        drawShape(rect, paint, id);
    }

    /**
     * The method to draw circle
     *
     * @param cx    The centerX of circle
     * @param cy    The centerY of circle
     * @param r     The radius of circle
     * @param paint The paint {@link SVGPaint}
     * @see #drawShape(SVGShape, SVGPaint, String)
     * @see SVGCircle
     * @since 0.0.1
     */
    public void drawCircle(float cx, float cy, float r, SVGPaint paint) {
        drawCircle(cx, cy, r, paint, null);
    }

    /**
     * The method to draw circle
     *
     * @param cx    The centerX of circle
     * @param cy    The centerY of circle
     * @param r     The radius of circle
     * @param paint The paint {@link SVGPaint}
     * @param id    The element id
     * @see #drawShape(SVGShape, SVGPaint, String)
     * @see SVGCircle
     * @since 0.0.1
     */

    public void drawCircle(float cx, float cy, float r, SVGPaint paint, String id) {
        SVGCircle circle = new SVGCircle(cx, cy, r);
        drawShape(circle, paint, id);
    }

    /**
     * The method to draw oval
     *
     * @param rectF Circumscribed rectangle of ellipse
     * @param paint The paint {@link SVGPaint}
     * @see #drawShape(SVGShape, SVGPaint, String)
     * @see SVGOval
     * @since 0.0.1
     */
    public void drawOval(RectF rectF, SVGPaint paint) {
        drawOval(rectF, paint, null);
    }

    /**
     * The method to draw oval
     *
     * @param rectF Circumscribed rectangle of ellipse
     * @param paint The paint {@link SVGPaint}
     * @param id    The element id
     * @see #drawShape(SVGShape, SVGPaint, String)
     * @see SVGOval
     * @since 0.0.1
     */
    public void drawOval(RectF rectF, SVGPaint paint, String id) {
        SVGOval oval = new SVGOval(rectF.centerX(), rectF.centerY(), rectF.width() / 2, rectF.height() / 2);
        drawShape(oval, paint, id);
    }

    /**
     * The method to draw Polygon
     *
     * @param points The points of Polygon,Arrange according to x1, y1, x2, y2...
     * @param paint  The paint {@link SVGPaint}
     * @see #drawPolygon(PointF[], SVGPaint, String)
     * @see #convertPoints(float[])
     * @since 0.0.1
     */
    public void drawPolygon(float[] points, SVGPaint paint) {
        drawPolygon(points, paint, null);

    }

    /**
     * The method to draw Polygon
     *
     * @param points The points of Polygon,Arrange according to x1, y1, x2, y2...
     * @param paint  The paint {@link SVGPaint}
     * @param id     The element id
     * @see #drawPolygon(PointF[], SVGPaint, String)
     * @see #convertPoints(float[])
     * @since 0.0.1
     */
    public void drawPolygon(float[] points, SVGPaint paint, String id) {
        if (points == null || points.length < 6) {
            throw new IllegalArgumentException("points is null or points length < 6");
        }

        drawPolygon(convertPoints(points), paint, id);

    }

    /**
     * The method to draw Polygon
     *
     * @param points The points of Polygon
     * @param paint  The paint {@link SVGPaint}
     * @see #drawShape(SVGShape, SVGPaint, String)
     * @see SVGPolygon
     * @since 0.0.1
     */
    public void drawPolygon(PointF points[], SVGPaint paint) {
        drawPolygon(points, paint, null);
    }

    /**
     * The method to draw Polygon
     *
     * @param points The points of Polygon
     * @param paint  The paint {@link SVGPaint}
     * @param id     The element id
     * @see #drawShape(SVGShape, SVGPaint, String)
     * @see SVGPolygon
     * @since 0.0.1
     */
    public void drawPolygon(PointF points[], SVGPaint paint, String id) {
        if (points == null || points.length < 3) {
            throw new IllegalArgumentException("points is null or points length <3");
        }
        SVGPolygon polygon = new SVGPolygon(points);
        drawShape(polygon, paint, id);
    }

    /**
     * The method to draw Polyline
     *
     * @param points The points of Polyline,Arrange according to x1, y1, x2, y2...
     * @param paint  The paint {@link SVGPaint}
     * @see #drawPolyline(PointF[], SVGPaint, String)
     * @see #convertPoints(float[])
     * @since 0.0.1
     */
    public void drawPolyline(float points[], SVGPaint paint) {
        drawPolyline(points, paint, null);
    }

    /**
     * The method to draw Polyline
     *
     * @param points The points of Polyline,Arrange according to x1, y1, x2, y2...
     * @param paint  The paint {@link SVGPaint}
     * @param id     The element id
     * @see #drawPolyline(PointF[], SVGPaint, String)
     * @see #convertPoints(float[])
     * @since 0.0.1
     */
    public void drawPolyline(float points[], SVGPaint paint, String id) {
        drawPolyline(convertPoints(points), paint, id);
    }

    /**
     * The method to draw Polyline
     *
     * @param points The points of Polyline
     * @param paint  The paint {@link SVGPaint}
     * @see #drawShape(SVGShape, SVGPaint, String)
     * @see SVGPolyline
     * @since 0.0.1
     */
    public void drawPolyline(PointF points[], SVGPaint paint) {
        drawPolyline(points, paint, null);
    }

    /**
     * The method to draw Polyline
     *
     * @param points The points of Polyline
     * @param paint  The paint {@link SVGPaint}
     * @param id     The element id
     * @see #drawShape(SVGShape, SVGPaint, String)
     * @see SVGPolyline
     * @since 0.0.1
     */
    public void drawPolyline(PointF points[], SVGPaint paint, String id) {
        if (points == null || points.length < 2) {
            throw new IllegalArgumentException("points is null or points length <2");
        }
        SVGPolyline polyline = new SVGPolyline(points);
        drawShape(polyline, paint, id);
    }

    /**
     * The method to draw Arc
     *
     * @param x          The centerX of Arc
     * @param y          The centerY of Arc
     * @param width      The width radius of Arc
     * @param height     The height radius of Arc
     * @param startAngle The start angle of Arc
     * @param arcAngle   The start angle of Arc
     * @param paint      The paint {@link SVGPaint}
     * @see #drawShape(SVGShape, SVGPaint, String)
     * @see SVGPath
     * @since 0.0.1
     */
    public void drawArc(float x, float y, float width, float height, float startAngle,
                        float arcAngle, SVGPaint paint) {
        drawArc(x, y, width, height, startAngle, arcAngle, paint, null);
    }

    /**
     * The method to draw Arc
     *
     * @param x          The centerX of Arc
     * @param y          The centerY of Arc
     * @param width      The width radius of Arc
     * @param height     The height radius of Arc
     * @param startAngle The start angle of Arc
     * @param arcAngle   The start angle of Arc
     * @param paint      The paint {@link SVGPaint}
     * @param id         The element id
     * @see #drawShape(SVGShape, SVGPaint, String)
     * @see SVGPath
     * @since 0.0.1
     */
    public void drawArc(float x, float y, float width, float height, float startAngle,
                        float arcAngle, SVGPaint paint, String id) {
        SVGPath path = new SVGPath();
        double sr = Math.toRadians(startAngle);
        float sx = (float) (x + width * Math.cos(sr));
        float sy = (float) (y + height * Math.sin(sr));
        path.moveTo(sx, sy);
        double er = Math.toRadians(startAngle + arcAngle);
        float ex = (float) (x + width * Math.cos(er));
        float ey = (float) (y + height * Math.sin(er));
        path.ellipticalArc(width, height, 0, arcAngle > 180 ? 1 : 0, 1, ex, ey);
        drawPath(path, paint, id);

    }

    /**
     * The method to draw Curve
     *
     * @param sx    The startX of Curve
     * @param sy    The startY of Curve
     * @param ex    The endX of Curve
     * @param ey    The endY of Curve
     * @param x     The centerX of Curve
     * @param y     The centerX of Curve
     * @param paint The paint {@link SVGPaint}
     * @see #drawShape(SVGShape, SVGPaint, String)
     * @see SVGPath
     * @since 0.0.1
     */

    public void drawCurve(float sx, float sy, float ex, float ey, float x, float y, SVGPaint paint) {
        drawCurve(sx, sy, ex, ey, x, y, paint, null);
    }

    /**
     * The method to draw Curve
     *
     * @param sx    The startX of Curve
     * @param sy    The startY of Curve
     * @param ex    The endX of Curve
     * @param ey    The endY of Curve
     * @param x     The centerX of Curve
     * @param y     The centerX of Curve
     * @param paint The paint {@link SVGPaint}
     * @param id    The element id
     * @see #drawShape(SVGShape, SVGPaint, String)
     * @see SVGPath
     * @since 0.0.1
     */
    public void drawCurve(float sx, float sy, float ex, float ey, float x, float y, SVGPaint paint, String id) {
        SVGPath path = new SVGPath();
        path.moveTo(sx, sy);
        path.quadraticBelzierCurve(x, y, ex, ey);
        drawPath(path, paint, id);
    }

    /**
     * The method to draw Path
     *
     * @param path  The path to draw {@link SVGPath}
     * @param paint The paint {@link SVGPaint}
     * @see #drawShape(SVGShape, SVGPaint, String)
     * @since 0.0.1
     */
    public void drawPath(SVGPath path, SVGPaint paint) {
        drawPath(path, paint, null);
    }

    /**
     * The method to draw Path
     *
     * @param path  The path to draw {@link SVGPath}
     * @param paint The paint {@link SVGPaint}
     * @param id    The element id
     * @see #drawShape(SVGShape, SVGPaint, String)
     * @since 0.0.1
     */
    public void drawPath(SVGPath path, SVGPaint paint, String id) {
        drawShape(path, paint, id);
    }

    /**
     * The method to draw Text
     *
     * @param text  The text to draw
     * @param x     The x of text pos
     * @param y     The Y of text pos
     * @param paint The paint {@link SVGPaint}
     * @see #drawTextOnPath(String, float, float, float, int, SVGPath, SVGPaint, String)
     * @since 0.0.1
     */
    public void drawText(String text, float x, float y, SVGPaint paint) {
        drawText(text, x, y, 0, paint, null);
    }

    /**
     * The method to draw Text
     *
     * @param text       The text to draw
     * @param x          The x of text pos
     * @param y          The Y of text pos
     * @param textLength The draw length,it used to {@link SVGPaint#lengthAdjust}
     * @param paint      The paint {@link SVGPaint}
     * @see #drawTextOnPath(String, float, float, float, int, SVGPath, SVGPaint, String)
     * @since 0.0.1
     */

    public void drawText(String text, float x, float y, int textLength, SVGPaint paint) {
        drawText(text, x, y, textLength, paint, null);
    }

    /**
     * The method to draw Text
     *
     * @param text  The text to draw
     * @param x     The x of text pos
     * @param y     The Y of text pos
     * @param paint The paint {@link SVGPaint}
     * @param id    The element id
     * @see #drawTextOnPath(String, float, float, float, int, SVGPath, SVGPaint, String)
     * @since 0.0.1
     */
    public void drawText(String text, float x, float y, SVGPaint paint, String id) {
        drawText(text, x, y, 0, paint, id);
    }

    /**
     * The method to draw Text
     *
     * @param text       The text to draw
     * @param x          The x of text pos
     * @param y          The Y of text pos
     * @param textLength The draw length,it used to {@link SVGPaint#lengthAdjust}
     * @param paint      The paint {@link SVGPaint}
     * @param id         The element id
     * @see #drawTextOnPath(String, float, float, float, int, SVGPath, SVGPaint, String)
     * @since 0.0.1
     */
    public void drawText(String text, float x, float y, int textLength, SVGPaint paint, String id) {
        drawTextOnPath(text, x, y, 0, textLength, null, paint, id);
    }

    /**
     * The method to draw Text on path
     *
     * @param text  The text to draw
     * @param x     The x of text pos
     * @param y     The Y of text pos
     * @param path  The path of text attach,it can be null,{@link SVGPath}
     * @param paint The paint {@link SVGPaint}
     * @see #drawShape(SVGShape, SVGPaint, String)
     * @see SVGTextPath
     * @since 0.0.1
     */
    public void drawTextOnPath(String text, float x, float y, SVGPath path, SVGPaint paint) {
        drawTextOnPath(text, x, y, 0, 0, path, paint, null);

    }

    /**
     * The method to draw Text on path
     *
     * @param text        The text to draw
     * @param x           The x of text pos
     * @param y           The Y of text pos
     * @param startOffset The offset size of start
     * @param path        The path of text attach,it can be null,{@link SVGPath}
     * @param paint       The paint {@link SVGPaint}
     * @param id          The element id
     * @see #drawShape(SVGShape, SVGPaint, String)
     * @see SVGTextPath
     * @since 0.0.1
     */
    public void drawTextOnPath(String text, float x, float y, float startOffset, SVGPath path, SVGPaint paint, String id) {
        drawTextOnPath(text, x, y, startOffset, 0, path, paint, id);
    }

    /**
     * The method to draw Text on path
     *
     * @param text        The text to draw
     * @param x           The x of text pos
     * @param y           The Y of text pos
     * @param startOffset The offset size of start
     * @param textLength  The draw length,it used to {@link SVGPaint#lengthAdjust}
     * @param path        The path of text attach,it can be null,{@link SVGPath}
     * @param paint       The paint {@link SVGPaint}
     * @param id          The element id
     * @see #drawShape(SVGShape, SVGPaint, String)
     * @see SVGTextPath
     * @since 0.0.1
     */
    public void drawTextOnPath(String text, float x, float y, float startOffset, int textLength, SVGPath path, SVGPaint paint, String id) {
        SVGTextPath textPath = new SVGTextPath.Builder()
                .setText(text)
                .setX(x)
                .setY(y)
                .setStartOffset(startOffset)
                .setTextLength(textLength)
                .setFontSizeUnit(fontSizeUnit)
                .setPaint(paint)
                .setPath(path)
                .build();
        drawShape(textPath, paint, id);
    }

    /**
     * The method to draw Shape
     *
     * @param shape The shape to draw {@link SVGShape}
     * @param paint The paint {@link SVGPaint}
     * @since 0.0.1
     */
    public void drawShape(SVGShape shape, SVGPaint paint) {
        drawShape(shape, paint, null);
    }

    /**
     * The method to draw Shape
     *
     * @param shape The shape to draw {@link SVGShape}
     * @param paint The paint {@link SVGPaint}
     * @param id    The element id
     * @see #addBaseAttrToDrawElement(Element, SVGPaint, String)
     * @since 0.0.1
     */
    public void drawShape(SVGShape shape, SVGPaint paint, String id) {
        Element element = shape.convertToSVGElement(this, document, geomDoubleConverter);
        addBaseAttrToDrawElement(element, paint, id);
        svgElement.appendChild(element);
    }

    /**
     * Clear all elements in SVG, reset it
     *
     * @since 0.0.4
     */
    public void clear() {
        if (rootSvgElement != null) {
            document.removeChild(rootSvgElement);
            initRootSvgElement();
        }

        if (defElement != null) {
            defElement = null;
        }

        if (elementIDs != null) {
            elementIDs.clear();
        }

        if (gradients != null) {
            gradients.clear();
        }

        if (filters != null) {
            filters.clear();
        }

        if (textPaths != null) {
            textPaths.clear();
        }

        saveFlags.clear();
        matrixList.clear();
        clipShapes.clear();
        clipRefs.clear();
        layerStack.clear();
        transform.reset();
    }

    /**
     * The method to draw Image
     *
     * @param uri    The image url
     * @param x      The x of image pos
     * @param y      The y of image pos
     * @param width  The image draw width
     * @param height The image draw height
     * @param paint  The paint {@link SVGPaint}
     * @since 0.0.1
     */
    public void drawImage(String uri, float x, float y, float width, float height, SVGPaint paint) {
        drawImage(uri, x, y, width, height, paint, null);
    }

    /**
     * The method to draw Image
     *
     * @param uri    The image url
     * @param x      The x of image pos
     * @param y      The y of image pos
     * @param width  The image draw width
     * @param height The image draw height
     * @param paint  The paint {@link SVGPaint}
     * @param id     The element id
     * @see #addBaseAttrToDrawElement(Element, SVGPaint, String)
     * @since 0.0.1
     */
    public void drawImage(String uri, float x, float y, float width, float height, SVGPaint paint, String id) {
        Element element = document.createElement("image");
        element.setAttribute("xlink:href", uri);
        element.setAttribute("x", geomDP(x));
        element.setAttribute("y", geomDP(y));
        element.setAttribute("width", geomDP(width));
        element.setAttribute("height", geomDP(height));
        addBaseAttrToDrawElement(element, paint, id);
        svgElement.appendChild(element);
    }

    /**
     * Add path element to defs
     * <p>
     * It used by {@link SVGTextPath#convertToSVGElement(SVGCanvas, Document, DoubleFunction)}
     * When use {@link #drawTextOnPath(String, float, float, float, int, SVGPath, SVGPaint, String)},
     * it need add path to defs
     * </p>
     *
     * @param path The path{@link SVGPath}
     * @return The path id
     * @see #addPathElementToDef(SVGPath, String)
     * @since 0.0.1
     */
    public String addPathToDef(SVGPath path) {
        if (textPaths.containsKey(path)) {
            return textPaths.get(path);
        }

        String id = defsKeyPrefix + TEXT_PATH_KEY_PREFIX + textPaths.size();
        textPaths.put(path, id);
        addPathElementToDef(path, id);

        return id;
    }

    /**
     * Add path element to {@link #defElement}
     *
     * @param path The path
     * @param id   The path id
     * @see #addElementToDef(Element)
     * @since 0.0.1
     */

    private void addPathElementToDef(SVGPath path, String id) {
        Element element = path.convertToSVGElement(this, document, geomDoubleConverter);
        element.setAttribute("id", id);
        addElementToDef(element);
    }

    /**
     * Convert points type float[] to PointF[]
     *
     * @param points The points
     * @return The points of type PointF[]
     * @since 0.0.1
     */
    private PointF[] convertPoints(float points[]) {
        PointF pointF[] = new PointF[points.length / 2];
        for (int i = 0; i < points.length; i += 2)
            pointF[i / 2] = new PointF(points[i], points[i + 1]);
        return pointF;
    }

    /**
     * Add base common Attrs to draw element.
     * <p>
     * it will add id,style,clip,transform attrs to element
     * </p>
     *
     * @param element The draw element
     * @param paint   The paint {@link SVGPaint}
     * @param id      The element id,can be null
     * @see #setElementId(Element, String)
     * @see #addTransformToElement(Element)
     * @see #style(SVGPaint)
     * @see #addClipToElement(Element)
     * @see #addFilterToElement(Element, SVGFilter) it support since 0.0.2
     * @since 0.0.1
     */
    private void addBaseAttrToDrawElement(Element element, SVGPaint paint, String id) {
        setElementId(element, id);
        if (paint != null)
            element.setAttribute("style", style(paint));
        if (paint != null && paint.getFilter() != null) {
            addFilterToElement(element, paint.getFilter());
        }
        addTransformToElement(element);
        addClipToElement(element);
    }

    /**
     * Add filter to draw Element
     *
     * @param element The draw Element
     * @param filter  The filter {@link SVGFilter}
     * @see #addFilterElementToDef(SVGFilter, String)
     * @since 0.0.2
     */

    private void addFilterToElement(Element element, SVGFilter filter) {
        String filterId = null;
        if (filters.containsKey(filter)) {
            filterId = filters.get(filter);
        } else {
            filterId = defsKeyPrefix + FILTER_KEY_PREFIX + filters.size();
            filters.put(filter, filterId);
            addFilterElementToDef(filter, filterId);
        }
        element.setAttribute("filter", "url(#" + filterId + ")");
    }

    /**
     * Add filter element to def
     *
     * @param filter The filter,{@link SVGFilter}
     * @param id     The filter id
     * @see #addElementToDef(Element)
     * @since 0.0.2
     */
    private void addFilterElementToDef(SVGFilter filter, String id) {
        Element element = filter.convertToSVGElement(this, document, geomDoubleConverter);
        element.setAttribute("id", id);
        addElementToDef(element);
    }

    /**
     * Add clip to draw Element
     *
     * @param element The draw Element
     * @see #getClipRef()
     * @since 0.0.1
     */
    private void addClipToElement(Element element) {
        String clipId = getClipRef();
        if (!TextUtils.isEmpty(clipId)) {
            element.setAttribute("clip-path", clipId);
        }
    }

    /**
     * Add clip area to canvas
     * <p>
     * When set clip,the draw element will only to draw in clip area
     * The clip shape will not add to defs immediately，it will be add when it used,{@link #getClipRef()}
     * </p>
     * Example code
     * <pre>
     *     //Set clip path
     *      SVGShapeGroup clipGroup = new SVGShapeGroup();
     *      SVGPath clipPath = new SVGPath();
     *      clipPath.oval(0.2f, 0.2f, 0.2f, 0.2f);
     *      SVGPath clipPath1 = new SVGPath();
     *      clipPath1.oval(0.6f, 0.2f, 0.2f, 0.2f);
     *      clipGroup.addShape(clipPath);
     *      clipGroup.addShape(clipPath1);
     *      SVGClipShape clipShape = new SVGClipShape(clipGroup, POS_MODE.MODE_BOX);
     *      svgCanvas.save();
     *      svgCanvas.clip(clipShape);
     *
     *     //set Text clip shape
     *     SVGTextPath svgTextPath = new SVGTextPath.Builder()
     *                     .setPath(textPath)
     *                     .setPaint(textPaint)
     *                     .setText("hello").build();
     *     svgCanvas.clip(new SVGClipShape(svgTextPath, POS_MODE.MODE_USERSPACE));
     *
     * </pre>
     *
     * @param clipShape The clip shape {@link SVGClipShape}
     * @since 0.0.1
     */
    public void clip(SVGClipShape clipShape) {
        this.clip = clipShape;
        clipRef = null;
    }

    /**
     * Get current clip shape id
     * <p>
     * When draw element,it will use getClipRef to get clipShape id,
     * if it is null,it means that not have clip shape.
     * And if it have a clip shape and not add to defs,it will add clip shape to defs
     * {@link #addClipToElement(Element)}
     * </p>
     *
     * @return The clip id,can be null
     * @see #registerClip(SVGClipShape)
     * @since 0.0.1
     */
    public String getClipRef() {
        if (clip == null) {
            this.clipRef = null;
            return null;
        }
        if (this.clipRef == null)
            clipRef = registerClip(clip);
        return "url(#" + clipRef + ")";

    }

    /**
     * Add clip element to defs
     *
     * @param clipShape The clip shape
     * @return The clip element id
     * @see #addElementToDef(Element)
     * @since 0.0.1
     */
    private String registerClip(SVGClipShape clipShape) {
        if (clipShape == null) {
            clipRef = null;
            return null;
        }
        Element element = clipShape.convertToSVGElement(this, document, geomDoubleConverter);
        String id = this.defsKeyPrefix + CLIP_KEY_PREFIX + clipCount;
        element.setAttribute("id", id);
        addElementToDef(element);
        ++clipCount;
        return id;
    }

    /**
     * Get the svg dom element
     *
     * @return The svg element
     * @see #getSVGElement(String, boolean, ViewBox, PreserveAspectRatio, MeetOrSlice)
     * @since 0.0.1
     */
    public Element getSVGElement() {
        return getSVGElement(null, true, null, null, null);
    }

    /**
     * Get the svg dom element
     *
     * @param id                  The svg id,can be null
     * @param includeDimensions   true mean that include width and height
     * @param viewBox             The svg view box,{@link ViewBox}
     * @param preserveAspectRatio The svg preserveAspectRatio,only viewBox not null,it will useful{@link PreserveAspectRatio}
     * @param meetOrSlice         The svg use meet or slice,{@link MeetOrSlice}
     * @return The svg dom element
     * @since 0.0.1
     */
    public Element getSVGElement(String id, boolean includeDimensions,
                                 ViewBox viewBox, PreserveAspectRatio preserveAspectRatio,
                                 MeetOrSlice meetOrSlice) {
        if (id != null) {
            rootSvgElement.setAttribute("id", id);
        }
        rootSvgElement.setAttribute("xmlns", "http://www.w3.org/2000/svg");
        rootSvgElement.setAttribute("xmlns:xlink", "http://www.w3.org/1999/xlink");

        if (includeDimensions) {
            String unitStr = this.units != null ? this.units.toString() : "";
            rootSvgElement.setAttribute("width", geomDP(width) + unitStr);
            rootSvgElement.setAttribute("height", geomDP(height) + unitStr);

        }
        if (viewBox != null) {
            rootSvgElement.setAttribute("viewBox", viewBox.valueStr(this.geomDoubleConverter));
            if (preserveAspectRatio != null) {
                rootSvgElement.setAttribute("preserveAspectRatio", preserveAspectRatio.toString() + (meetOrSlice == null ? "" : " " + meetOrSlice.toString()));

            }
        }

        restoreToCount(0);
        return rootSvgElement;
    }

    /**
     * Set the transform to canvas
     * <p>
     * Do not recommend the way,plz use {@link #translate(float, float)} ,{@link #scale(float, float)},
     * {@link #rotate(float, float, float)} ,{@link #skew(float, float, float, float)} to change the transform.
     * </p>
     *
     * @param matrix The transform matrix
     * @since 0.0.1
     */

    public void setTransform(Matrix matrix) {
        transform = matrix;
    }

    /**
     * Get the transform matrix of canvas
     *
     * @return The transform matrix
     * @since 0.0.1
     */
    public Matrix getTransform() {
        return transform;
    }

    /**
     * Reset the transform
     *
     * @since 0.0.1
     */
    public void resetTransform() {
        transform.reset();
    }

    /**
     * Translate the canvas
     *
     * @param dx The distance of x
     * @param dy The distance of Y
     * @since 0.0.1
     */
    public void translate(float dx, float dy) {
        transform.postTranslate(dx, dy);
    }

    /**
     * Scale the canvas
     * Postconcats the matrix with the specified scale. M' = S(sx, sy, px, py) * M
     *
     * @param sx the scale of X-axis
     * @param sy the scale of Y-axis
     * @param px the scale centerX
     * @param py the scale centerY
     * @since 0.0.1
     */
    public void scale(float sx, float sy, float px, float py) {
        transform.postScale(sx, sy, px, py);
    }

    /**
     * Scale the canvas
     * Postconcats the matrix with the specified scale. M' = S(sx, sy) * M
     *
     * @param sx the scale of X-axis
     * @param sy the scale of Y-axis
     * @since 0.0.1
     */
    public void scale(float sx, float sy) {
        transform.postScale(sx, sy);
    }

    /**
     * Rotate the canvas
     * Postconcats the matrix with the specified rotation. M' = R(degrees, px, py) * M
     *
     * @param degree The rotate degree
     * @param px     The rotate centerX
     * @param py     The rotate centerY
     * @since 0.0.1
     */
    public void rotate(float degree, float px, float py) {
        transform.postRotate(degree, px, py);
    }

    /**
     * Skew the canvas
     * Postconcats the matrix with the specified skew. M' = K(kx, ky, px, py) * M
     *
     * @param sx The skew of X-axis
     * @param sy The skew of Y-axis
     * @param px The skew centerX
     * @param py The skew centerY
     * @since 0.0.1
     */
    public void skew(float sx, float sy, float px, float py) {
        transform.postSkew(sx, sy, px, py);
    }

    /**
     * Get doubleConvert
     *
     * @return The convert
     * @since 0.0.1
     */
    public DoubleFunction<String> getGeomDoubleConverter() {
        return geomDoubleConverter;
    }

    /**
     * Set double convert
     *
     * @param geomDoubleConverter the convert
     * @since 0.0.1
     */

    public void setGeomDoubleConverter(DoubleFunction<String> geomDoubleConverter) {
        this.geomDoubleConverter = geomDoubleConverter;
    }

    /**
     * Get doubleConvert
     *
     * @return The convert
     * @since 0.0.1
     */
    public DoubleFunction<String> getTransformDoubleConverter() {
        return transformDoubleConverter;
    }

    /**
     * Set double convert
     *
     * @param transformDoubleConverter the convert
     * @since 0.0.1
     */
    public void setTransformDoubleConverter(DoubleFunction<String> transformDoubleConverter) {
        this.transformDoubleConverter = transformDoubleConverter;
    }

    /**
     * Saves the current matrix and clip onto a private stack.
     *
     * @return The value to {@link #restoreToCount(int)} to balance this save()
     * @see #restore()
     * @see #restoreToCount(int)
     * @since 0.0.1
     */
    public int save() {
        return save(SAVE_FLAG_MATRIX | SAVE_FLAG_CLIP);
    }

    /**
     * Saves the current matrix and clip onto a private stack.
     *
     * @param flags The save flags,refer to {@link Saveflags}
     * @return The value to {@link #restoreToCount(int)} to balance this save()
     * @see #restore()
     * @see #restoreToCount(int)
     * @since 0.0.1
     */
    public int save(@Saveflags int flags) {
        return saveFlagInternal(flags);
    }

    private int saveFlagInternal(int flags) {
        saveFlags.push(flags);
        if ((flags & SAVE_FLAG_MATRIX) == SAVE_FLAG_MATRIX) {
            Matrix matrix = new Matrix();
            float[] values = new float[9];
            transform.getValues(values);
            matrix.setValues(values);
            matrixList.push(matrix);
        }
        if ((flags & SAVE_FLAG_CLIP) == SAVE_FLAG_CLIP) {
            if (clip != null)
                clipShapes.push((SVGClipShape) clip.clone());
            else
                clipShapes.push(null);
            if (clipRef != null)
                clipRefs.push(clipRef);
            else clipRefs.push(null);
        }
        return saveFlags.size();
    }

    /**
     * create a new layer and save the current layer
     * it will generate <svg></svg> element
     * when {@link #restore() } {@link #restoreToCount(int)} the element will be added to the prev layer
     *
     * @param x      the layer x pos
     * @param y      the layer y pos
     * @param width  the layer width
     * @param height the layer height
     * @return The value to {@link #restoreToCount(int)} to balance this saveLayer()
     */

    public int saveLayer(float x, float y, float width, float height) {
        int saveCount = saveFlagInternal(SAVE_FLAG_ALL);
        layerStack.push(svgElement);
        svgElement = initLayer(x, y, width, height);
        return saveCount;
    }

    /**
     * Clear all elements in the current layer
     */
    public void clearLayer() {
        NodeList childList = svgElement.getChildNodes();
        for (int i = 0; i < childList.getLength(); ++i) {
            Node node = childList.item(i);
            if (svgElement != rootSvgElement || !node.getNodeName().equals(DEFS_NAME)) {
                svgElement.removeChild(childList.item(i));
            }
        }
    }

    private Element initLayer(float x, float y, float width, float height) {
        Element layer = document.createElement(SVG_NAME);
        layer.setAttribute("x", geomDP(x));
        layer.setAttribute("y", geomDP(y));
        layer.setAttribute("width", geomDP(width));
        layer.setAttribute("height", geomDP(height));
        return layer;
    }

    /**
     * Restore the matrix and clip from stack.
     * <p>
     * This call balances a previous call to save(), and is used to remove all
     * modifications to the matrix/clip state since the last save call.
     * </p>
     *
     * @see #save()
     * @since 0.0.1
     */
    public void restore() {
        if (saveFlags.size() > 0) {
            int flags = saveFlags.pop();
            if ((flags & SAVE_FLAG_MATRIX) == SAVE_FLAG_MATRIX) {
                transform = matrixList.pop();
            }

            if ((flags & SAVE_FLAG_CLIP) == SAVE_FLAG_CLIP) {
                clipRef = clipRefs.pop();
                clip = clipShapes.pop();
            }

            if ((flags & SAVE_FLAG_LAYER) == SAVE_FLAG_LAYER) {
                Element nowElement = svgElement;
                svgElement = layerStack.pop();
                if (nowElement.hasChildNodes()) {
                    svgElement.appendChild(nowElement);
                }
            }

        }
    }

    /**
     * Restore the matrix and clip from stack.
     *
     * @param count Restore to the count
     * @see #save()
     * @since 0.0.2
     */
    public void restoreToCount(int count) {
        if (count < 0) count = 0;
        while (saveFlags.size() > count) {
            restore();
        }
    }

    /**
     * Return the save count.
     *
     * @return The save count,
     * @see #restore()
     * @see #restoreToCount(int)
     * @since 0.0.2
     */
    public int getSaveCount() {
        return saveFlags.size();
    }

    /**
     * Get svg xml string
     *
     * @return The svg xml string
     * @throws TransformerException
     * @see #getSVGXMLTransformer()
     * @since 0.0.1
     */
    public String getSVGXmlString() throws TransformerException {

        Transformer transformer = getSVGXMLTransformer();
        DOMSource source = new DOMSource(document);
        StringWriter stringWriter = new StringWriter();
        StreamResult result = new StreamResult(stringWriter);
        transformer.transform(source, result);
        return stringWriter.toString();
    }

    /**
     * Write SVG xml string to outputStream
     *
     * @param outputStream The stream which output
     * @throws TransformerException
     * @see #getSVGXMLTransformer()
     * @since 0.0.1
     */
    public void writeSVGXMLToStream(OutputStream outputStream) throws TransformerException {
        Transformer transformer = getSVGXMLTransformer();
        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(outputStream);
        transformer.transform(source, result);
    }

    /**
     * Get svg dom transformer to write xml string
     *
     * @return Svg dom transformer to write xml string,used by {@link #getSVGXmlString()},{@link #getSVGXMLTransformer()}
     * @throws TransformerConfigurationException
     * @since 0.0.1
     */
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

    /**
     * Get the paint style string to element
     *
     * @param paint The style paint,{@link SVGPaint}
     * @return The style string
     * @see #addBaseAttrToDrawElement(Element, SVGPaint, String)
     * @see #getSVGFillStyle(SVGPaint)
     * @see #strokeStyle(SVGPaint, boolean)
     * @since 0.0.1
     */
    private String style(SVGPaint paint) {
        if (paint == null || paint.getStyle() == Paint.Style.STROKE) {
            return strokeStyle(paint, true);
        } else if (paint.getStyle() == Paint.Style.FILL)
            return getSVGFillStyle(paint);
        else
            return strokeStyle(paint, false) + getSVGFillStyle(paint);
    }

    /**
     * Get the strokeStyle of Paint
     *
     * @param paint        The style paint,{@link SVGPaint}
     * @param needFillNone true means that fill none
     * @return the strokeStyle of Paint
     * @since 0.0.1
     */
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
            strokeColor = strokeColor(paint);
            alpha = paint.getAlpha();
        }

        StringBuilder b = new StringBuilder();
        b.append("stroke-width:").append(strokeWidth).append(";");
        b.append("stroke:").append(strokeColor).append(";");
        if (alpha < 255)
            b.append("stroke-opacity:").append(SVGUtils.getColorAlpha(alpha)).append(';');
        if (!strokeCap.equals(DEFAULT_STROKE_CAP)) {
            b.append("stroke-linecap:").append(strokeCap).append(';');
        }
        if (!strokeJoin.equals(DEFAULT_STROKE_JOIN)) {
            b.append("stroke-linejoin:").append(strokeJoin).append(';');
        }
        if (Math.abs(DEFAULT_MITER_LIMIT - miterLimit) > 0.001) {
            b.append("stroke-miterlimit:").append(geomDP(miterLimit)).append(';');
        }
        if (dashArray != null && dashArray.length != 0) {
            b.append("stroke-dasharray:");
            for (int i = 0; i < dashArray.length; i++) {
                if (i != 0) b.append(';');
                b.append(dashArray[i]);
            }
            b.append(';');
        }
        if (needFillNone)
            b.append("fill:none;");
        return b.toString();
    }

    /**
     * Get svg fill style of Paint
     *
     * @param paint The style paint,{@link SVGPaint}
     * @return The fill style
     * @since 0.0.1
     */
    private String getSVGFillStyle(SVGPaint paint) {
        StringBuilder b = new StringBuilder();
        b.append("fill:").append(fillColor(paint)).append(';');

        double opacity = SVGUtils.getColorAlpha(paint.getFillColorAlpha());
        if (opacity < 1.0) {
            b.append("fill-opacity:").append(opacity).append(';');
        }
        if (!paint.getFillRule().equals(SVGPaint.FillRule.FILL_RULE_DEFAULT)) {
            b.append("fill-rule:" + paint.getFillRule()).append(';');
        }
        return b.toString();
    }

    /**
     * Get fillColor of paint
     *
     * @param paint The style paint,{@link SVGPaint}
     * @return The fillColor
     * @since 0.0.1
     */
    private String fillColor(SVGPaint paint) {
        if (paint.getGradient() != null) {
            String id = addGradient(paint.gradient);
            return "url(#" + id + ")";
        }

        return SVGUtils.rgbColorStr(paint.getFillColor());
    }

    /**
     * Get the strokeColor of Paint
     *
     * @param paint The style paint,{@link SVGPaint} ,if {@link SVGPaint#isUseGradientStroke()} true,means that stroke color use gradient
     * @return the strokeColor of Paint
     * @since 0.0.1
     */
    private String strokeColor(SVGPaint paint) {
        if (paint.getGradient() != null && paint.isUseGradientStroke()) {
            String id = addGradient(paint.gradient);
            return "url(#" + id + ")";
        }

        return SVGUtils.rgbColorStr(paint.getColor());
    }

    /**
     * Add gradient color to defs
     *
     * @param gradient The gradient,{@link SVGGradient}
     * @return The gradient id
     * @since 0.0.1
     */
    private String addGradient(SVGGradient gradient) {
        if (!gradients.containsKey(gradient)) {
            String id = this.defsKeyPrefix + "gp" + gradients.size();
            gradients.put(gradient, id);
            addElementToDef(getGradientElement(id, gradient));
            return id;
        }
        return gradients.get(gradient);
    }

    /**
     * Add gradient color to defs
     *
     * @param id       The gradient id
     * @param gradient The gradient,{@link SVGGradient}
     * @return The gradient doc element
     * @since 0.0.1
     */

    private Element getGradientElement(String id, SVGGradient gradient) {
        if (gradient == null) return null;
        Element element = gradient.convertToSVGElement(this, document, geomDoubleConverter);
        element.setAttribute("id", id);
        return element;
    }

    /**
     * Add transform to draw element
     *
     * @param element The draw element
     * @since 0.0.1
     */
    private void addTransformToElement(Element element) {
        if (transform != null && !transform.isIdentity())
            element.setAttribute("transform", getSVGTransform(transform));
    }

    /**
     * Get canvas transform value
     *
     * @param t The transform matrix
     * @return The value of matrix
     * @since 0.0.1
     */
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
     * Add element to defs
     *
     * @param element The dom element
     * @since 0.0.1
     */
    private void addElementToDef(Element element) {
        if (element == null) return;
        if (defElement == null) {
            defElement = document.createElement(DEFS_NAME);
            rootSvgElement.insertBefore(defElement, rootSvgElement.getFirstChild());
        }
        defElement.appendChild(element);
    }

    /**
     * Set draw element id
     *
     * @param element   The draw element
     * @param elementID The id
     * @since 0.0.1
     */
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
