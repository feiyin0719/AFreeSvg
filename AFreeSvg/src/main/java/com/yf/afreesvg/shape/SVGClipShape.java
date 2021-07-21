package com.yf.afreesvg.shape;

import com.yf.afreesvg.ConvertToSVGElement;
import com.yf.afreesvg.SVGCanvas;
import com.yf.afreesvg.SVGModes;
import com.yf.afreesvg.SVGPaint;
import com.yf.afreesvg.util.DoubleFunction;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * The ClipShape
 * It used by {@link SVGCanvas#clip(SVGClipShape)}
 * when set clipShape,it will only draw into the clip shape.
 *
 * @author iffly
 * @since 0.0.1
 */
public class SVGClipShape implements ConvertToSVGElement, Cloneable {
    /**
     * clip shape
     *
     * @see SVGShape
     */
    private SVGShape shape;
    /**
     * Pos mode
     *
     * @see SVGModes
     */
    private @SVGModes.POS_MODE
    String posMode = SVGModes.MODE_USERSPACE;

    public SVGClipShape(SVGShape shape, @SVGModes.POS_MODE String posMode) {
        this.shape = shape;
        this.posMode = posMode;
    }

    public SVGClipShape(SVGClipShape clipShape) {
        this.shape = (SVGShape) clipShape.shape.clone();
        this.posMode = clipShape.posMode;
    }

    /**
     * Get shape
     *
     * @return
     * @since 0.0.1
     */
    public SVGShape getShape() {
        return shape;
    }

    /**
     * Set shape
     *
     * @param shape
     * @since 0.0.1
     */
    public void setShape(SVGShape shape) {
        this.shape = shape;
    }

    /**
     * Get pos mode
     *
     * @return
     * @since 0.0.1
     */
    public @SVGModes.POS_MODE
    String getPosMode() {
        return posMode;
    }

    /**
     * Set pos mode
     *
     * @param posMode
     * @since 0.0.1
     */
    public void setPosMode(@SVGModes.POS_MODE String posMode) {
        this.posMode = posMode;
    }

    @Override
    public Element convertToSVGElement(SVGCanvas canvas, Document document, DoubleFunction<String> convert) {
        Element element = document.createElement("clipPath");
        element.setAttribute("clipPathUnits", posMode);
        if (shape instanceof SVGShapeGroup) {
            //group in clip need remove <g> element
            Element g = shape.convertToSVGElement(canvas, document, convert);
            NodeList list = g.getChildNodes();
            if (list != null && list.getLength() > 0) {
                for (int i = 0; i < list.getLength(); ++i) {
                    Node n = list.item(i);
                    if (n instanceof Element) {
                        Element element1 = (Element) n;
                        element.appendChild(element1);
                    }
                }
            }
        } else {
            Element element1 = shape.convertToSVGElement(canvas, document, convert);
            element.appendChild(element1);
        }
        return element;
    }

    public Object clone() {
        return new SVGClipShape(this);
    }
}
