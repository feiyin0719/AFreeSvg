package com.yf.afreesvg.shape;

import com.yf.afreesvg.SVGPaint;

import org.w3c.dom.Element;

import java.util.Objects;

/**
 * The Base shape class
 *
 * @author iffly
 * @since 0.0.1
 */
public abstract class SVGBaseShape implements SVGShape {
    //only  use in clipPath
    private @SVGPaint.FillRule
    String clipRule = SVGPaint.FILL_RULE_DEFAULT;

    /**
     * The clipPath fill rule
     *
     * @return fill rule {@link SVGPaint#FILL_RULE_EVENODD} {@link SVGPaint#FILL_RULE_DEFAULT}
     */
    public @SVGPaint.FillRule
    String getClipRule() {
        return clipRule;
    }

    /**
     * Set clipPath full rule
     *
     * @param clipRule
     */
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
