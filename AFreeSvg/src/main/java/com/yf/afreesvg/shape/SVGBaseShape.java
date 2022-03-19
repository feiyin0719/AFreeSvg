/*
 * Copyright (c) 2022.  by iffly Limited.  All rights reserved.
 * This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 */

package com.yf.afreesvg.shape;

import androidx.annotation.NonNull;

import com.yf.afreesvg.SVGPaint;

import org.w3c.dom.Element;

/**
 * The Base shape class
 *
 * @author iffly
 * @since 0.0.1
 */
public abstract class SVGBaseShape implements SVGShape {
    //only  use in clipPath
    private @SVGPaint.FillRule
    String clipRule = SVGPaint.FillRule.FILL_RULE_DEFAULT;

    /**
     * The clipPath fill rule
     *
     * @return fill rule {@link SVGPaint.FillRule#FILL_RULE_EVENODD} {@link SVGPaint.FillRule#FILL_RULE_DEFAULT}
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
        if (clipRule.equals(SVGPaint.FillRule.FILL_RULE_EVENODD))
            element.setAttribute("clip-rule", clipRule);
    }


    @NonNull
    @Override
    abstract public Object clone();

}
