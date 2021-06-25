package com.yf.afreesvg.shape;

import com.yf.afreesvg.SVGPaint;

import org.w3c.dom.Element;

import java.util.Objects;

public abstract class SVGBaseShape implements SVGShape {
    //only can use in clipPath
    private @SVGPaint.FillRule
    String clipRule = SVGPaint.FILL_RULE_DEFAULT;

    public @SVGPaint.FillRule
    String getClipRule() {
        return clipRule;
    }

    public void setClipRule(@SVGPaint.FillRule String clipRule) {
        this.clipRule = clipRule;
    }

    protected void addBaseAttr(Element element) {
        if (clipRule.equals(SVGPaint.FILL_RULE_EVENODD))
            element.setAttribute("clip-rule", clipRule);
    }


    @Override
    public Object clone() {
        return null;
    }

}
