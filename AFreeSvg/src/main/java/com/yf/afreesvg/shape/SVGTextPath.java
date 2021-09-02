package com.yf.afreesvg.shape;

import android.graphics.Paint;

import com.yf.afreesvg.SVGCanvas;
import com.yf.afreesvg.SVGPaint;
import com.yf.afreesvg.SVGUnits;
import com.yf.afreesvg.font.SVGFont;
import com.yf.afreesvg.util.DoubleFunction;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.Objects;

/**
 * Text path shape
 * It will generate a text path
 *
 * @author iffly
 * @since 0.0.1
 */
public class SVGTextPath extends SVGBaseShape {
    /**
     * text string
     */
    private String text;
    /**
     * x pos
     */
    private float x;
    /**
     * y pos
     */
    private float y;
    /**
     * The draw length,it used to {@link SVGPaint#getLengthAdjust()}
     */
    private int textLength;
    /**
     * draw start offset
     */
    private float startOffset;
    /**
     * The path of text draw attach,can be null
     */
    private SVGPath path;
    /**
     * style paint,{@link SVGPaint}
     */
    private SVGPaint paint;
    /**
     * font size units
     */
    private SVGUnits fontSizeUnit = SVGUnits.PX;

    private SVGTextPath(String text, float x, float y, int textLength, float startOffset, SVGPath path, SVGPaint paint, SVGUnits fontSizeUnit) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.textLength = textLength;
        this.startOffset = startOffset;
        this.path = path;
        this.paint = paint;
    }

    /**
     * Text string
     *
     * @return
     * @since 0.0.1
     */
    public String getText() {
        return text;
    }

    /**
     * Pos x
     *
     * @return
     * @since 0.0.1
     */
    public float getX() {
        return x;
    }

    /**
     * Pos y
     *
     * @return
     * @since 0.0.1
     */
    public float getY() {
        return y;
    }

    /**
     * Get textLength
     * The draw length,it used to {@link SVGPaint#getLengthAdjust()}
     *
     * @return
     * @since 0.0.1
     */
    public int getTextLength() {
        return textLength;
    }

    /**
     * The draw start offset
     *
     * @return
     * @since 0.0.1
     */
    public float getStartOffset() {
        return startOffset;
    }

    /**
     * The path of text draw attach
     *
     * @return
     * @since 0.0.1
     */
    public SVGPath getPath() {
        return path;
    }

    /**
     * style paint
     *
     * @return {@link SVGPaint}
     * @since 0.0.1
     */
    public SVGPaint getPaint() {
        return paint;
    }

    /**
     * font size unit
     *
     * @return {@link SVGUnits}
     * @since 0.0.1
     */
    public SVGUnits getFontSizeUnit() {
        return fontSizeUnit;
    }

    /**
     * The TextPath builder class
     * Use it to build TextPath
     *
     * @since 0.0.1
     */
    public static class Builder {
        private String text;
        private float x;
        private float y;
        private int textLength;
        private float startOffset;
        private SVGPath path;
        private SVGPaint paint;
        private SVGUnits fontSizeUnit = SVGUnits.PX;

        public Builder setText(String text) {
            this.text = text;
            return this;
        }

        public Builder setX(float x) {
            this.x = x;
            return this;
        }

        public Builder setY(float y) {
            this.y = y;
            return this;
        }

        public Builder setTextLength(int textLength) {
            this.textLength = textLength;
            return this;
        }

        public Builder setStartOffset(float startOffset) {
            this.startOffset = startOffset;
            return this;
        }

        public Builder setPath(SVGPath path) {
            this.path = path;
            return this;
        }

        public Builder setPaint(SVGPaint paint) {
            this.paint = paint;
            return this;
        }

        public Builder setFontSizeUnit(SVGUnits fontSizeUnit) {
            this.fontSizeUnit = fontSizeUnit;
            return this;
        }

        public SVGTextPath build() {
            return new SVGTextPath(text, x, y, textLength, startOffset, path, paint, fontSizeUnit);
        }
    }


    @Override
    public Element convertToSVGElement(SVGCanvas canvas, Document document, DoubleFunction<String> convert) {
        Element element = document.createElement("text");
        element.setAttribute("x", convert.apply(x));
        element.setAttribute("y", convert.apply(y));
        if (textLength > 0)
            element.setAttribute("textLength", "" + textLength);
        if (path != null) {
            String id = canvas.addPathToDef(path);
            element.appendChild(getTextPathElement(document, convert, text, path, startOffset, id));
        } else
            element.setTextContent(text);
        setTextStyle(element, paint, convert);
        addBaseAttr(element);
        return element;
    }

    private Element getTextPathElement(Document document, DoubleFunction<String> convert, String text, SVGPath path, float startOffset, String id) {
        Element element = document.createElement("textPath");
        element.setAttribute("xlink:href", "#" + id);
        if (startOffset > 0)
            element.setAttribute("startOffset", convert.apply(startOffset));
        element.setTextContent(text);
        return element;
    }

    private void setTextStyle(Element element, SVGPaint paint, DoubleFunction<String> convert) {
        if (paint.getFont() != null) {
            SVGFont font = paint.getFont();
            element.setAttribute("font-family", font.getFontFamily());
            element.setAttribute("font-style", font.getFontStyle());
            element.setAttribute("font-weight", font.getFontWeight());
            element.setAttribute("font-size", font.getFontSize() + (fontSizeUnit != null ? fontSizeUnit.toString() : ""));
        }
        if (paint.getTextAlign() != Paint.Align.LEFT)
            element.setAttribute("text-anchor", textAlignToAnchor(paint.getTextAlign()));

        if (paint.getLetterSpacing() > 0) {
            element.setAttribute("letter-spacing", convert.apply(paint.getLetterSpacing()));
        }

        if (paint.getWordSpacing() > 0) {
            element.setAttribute("word-spacing", convert.apply(paint.getWordSpacing()));
        }
        if (!SVGPaint.TextDecoration.TEXT_DECORATION_NONE.equals(paint.getTextDecoration()))
            element.setAttribute("text-decoration", paint.getTextDecoration());
        if (!SVGPaint.LengthAdjust.LENGTH_ADJUST_SPACING.equals(paint.getLengthAdjust()) && element.hasAttribute("textLength"))
            element.setAttribute("lengthAdjust", paint.getLengthAdjust());
    }

    private String textAlignToAnchor(Paint.Align align) {
        switch (align) {
            case CENTER:
                return "middle";
            case RIGHT:
                return "end";
            default:
                return "start";
        }
    }

    @Override
    public Object clone() {
        SVGPaint paint = new SVGPaint();
        SVGFont svgFont = this.paint.getFont();

        paint.setFont(svgFont);
        paint.setLengthAdjust(this.paint.getLengthAdjust());
        paint.setWordSpacing(this.paint.getWordSpacing());
        paint.setTextAlign(this.paint.getTextAlign());
        paint.setLetterSpacing(this.paint.getLetterSpacing());
        paint.setTextDecoration(this.paint.getTextDecoration());
        return new Builder().setText(this.text).setX(this.x).setY(this.y).setTextLength(this.textLength).setStartOffset(this.startOffset).setPath((SVGPath) this.path.clone()).setPaint(paint).setFontSizeUnit(this.fontSizeUnit).build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SVGTextPath that = (SVGTextPath) o;
        return Float.compare(that.x, x) == 0 &&
                Float.compare(that.y, y) == 0 &&
                textLength == that.textLength &&
                Float.compare(that.startOffset, startOffset) == 0 &&
                Objects.equals(text, that.text) &&
                Objects.equals(path, that.path) &&
                Objects.equals(paint, that.paint) &&
                fontSizeUnit == that.fontSizeUnit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(text, x, y, textLength, startOffset, path, paint, fontSizeUnit);
    }
}
