package com.yf.afreesvg.shape;

import android.graphics.Paint;

import com.yf.afreesvg.SVGCanvas;
import com.yf.afreesvg.SVGPaint;
import com.yf.afreesvg.SVGUnits;
import com.yf.afreesvg.font.SVGFont;
import com.yf.afreesvg.util.DoubleFunction;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class SVGTextPath implements SVGShape {
    private String text;
    private float x;
    private float y;
    private int textLength;
    private float startOffset;
    private SVGPath path;
    private SVGPaint paint;
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

    public String getText() {
        return text;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public int getTextLength() {
        return textLength;
    }

    public float getStartOffset() {
        return startOffset;
    }

    public SVGPath getPath() {
        return path;
    }

    public SVGPaint getPaint() {
        return paint;
    }

    public SVGUnits getFontSizeUnit() {
        return fontSizeUnit;
    }

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
        if (!SVGPaint.TEXT_DECORATION_NONE.equals(paint.getTextDecoration()))
            element.setAttribute("text-decoration", paint.getTextDecoration());
        if (!SVGPaint.LENGTH_ADJUST_SPACING.equals(paint.getLengthAdjust()) && element.hasAttribute("textLength"))
            element.setAttribute("lengthAdjust", paint.getLengthAdjust());
    }

    private String textAlignToAnchor(Paint.Align align) {
        switch (align) {
            case RIGHT:
                return "middle";
            case CENTER:
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
        return new SVGTextPath(this.text, this.x, this.y, this.textLength, this.startOffset, (SVGPath) this.path.clone(), paint, this.fontSizeUnit);
    }
}
