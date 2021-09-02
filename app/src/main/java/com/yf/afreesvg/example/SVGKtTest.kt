package com.yf.afreesvg.example

import com.yf.afreesvg.PosMode
import com.yf.afreesvg.filter.SVGBaseFilter
import com.yf.afreesvg.filter.SVGFilterGroup
import com.yf.afreesvg.gradient.SVGLinearGradient
import com.yf.afreesvg.ktx.*
import com.yf.afreesvg.shape.SVGClipShape

/**
 *             SVGFilterGroup filterGroup = new SVGFilterGroup();
filterGroup.setFilterUnits(PosMode.MODE_BOX);
filterGroup.setX(-0.2f);
filterGroup.setY(-0.2f);
filterGroup.setWidth(1.5f);
filterGroup.setHeight(1.5f);
SVGOffsetFilter.SVGOffsetFilterEffect offsetFilterEffect = new SVGOffsetFilter.SVGOffsetFilterEffect(0.05f, 0.05f);
offsetFilterEffect.setIn(SVGBaseFilter.ALPHA_VALUE);
offsetFilterEffect.setResult("offset");
SVGGaussianBlurFilter.SVGGaussianBlurFilterEffect gaussianBlurFilterEffect = new SVGGaussianBlurFilter.SVGGaussianBlurFilterEffect(3, 3);
gaussianBlurFilterEffect.setIn(offsetFilterEffect.getResult());
gaussianBlurFilterEffect.setResult("blur");
SVGFilterGroup.SVGBlendFilterEffect blendFilterEffect = new SVGFilterGroup.SVGBlendFilterEffect();
blendFilterEffect.setIn(SVGBaseFilter.GRAPHIC_VALUE);
blendFilterEffect.setIn2(gaussianBlurFilterEffect.getResult());

filterGroup.addEffect(offsetFilterEffect);
filterGroup.addEffect(gaussianBlurFilterEffect);
filterGroup.addEffect(blendFilterEffect);
 */
fun createFilterGroup(): SVGFilterGroup {
    return filterGroup {
        filterUnits = PosMode.MODE_BOX
        x = -0.2f
        y = -0.2f
        width = 1.5f
        height = 1.5f
        offsetFilterNode {
            `in` = SVGBaseFilter.ALPHA_VALUE
            result = "offset"
            dx = 0.05f
            dy = 0.05f
        }
        gaussianFilterNode {
            `in` = "offset"
            result = "blur"
            stdDeviationX = 3f
            stdDeviationY = 3f
        }

        blendFilterNode {
            `in` = SVGBaseFilter.GRAPHIC_VALUE
            in2 = "blur"
        }
    }
}


fun createClipShape(): SVGClipShape {
    return clipShape {
        shape = shapeGroup {
            path {
                oval(0.2f, 0.2f, 0.2f, 0.2f)
            }
            path {
                oval(0.6f, 0.2f, 0.2f, 0.2f)
            }
        }
        posMode = PosMode.MODE_BOX
    }
}

fun createClipShape1(): SVGClipShape {
    return clipShape {
        shape = shapeGroup {
            path {
                oval(0.2f, 0.2f, 0.2f, 0.2f)
            }

        }
        posMode = PosMode.MODE_BOX
    }
}

fun createLinearGradient(): SVGLinearGradient {
    return linearGradient {
        startX(0f)
        startY(0f)
        endX(1f)
        endY(0f)
        color(0xffff0000, 0f)
        color(0xff00ff00, 0.5f)
        color(0xff00eeee, 0.75f)
        color(0xff0000ff, 1f)
    }
}