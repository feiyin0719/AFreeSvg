package com.yf.afreesvg.gradient;

import com.yf.afreesvg.SVGBaseTest;
import com.yf.afreesvg.SVGModes;

import org.w3c.dom.Element;

import static org.junit.Assert.assertEquals;

public class SVGGradientBaseTest extends SVGBaseTest {

    public void baseGradientTest(SVGBaseGradient gradient) {
        gradient.addStopColor(0.5f, 0xffff0000);
        gradient.addStopColor(0.75f, 0xffff0000);
        assertEquals(SVGBaseGradient.SPREAD_PAD, gradient.getSpreadMode());
        gradient.setSpreadMode(SVGBaseGradient.SPREAD_REFLECT);
        assertEquals(SVGBaseGradient.SPREAD_REFLECT, gradient.getSpreadMode());
        assertEquals(SVGModes.MODE_BOX, gradient.getPosMode());
        gradient.setPosMode(SVGModes.MODE_USERSPACE);
        assertEquals(SVGModes.MODE_USERSPACE, gradient.getPosMode());
        Element element = gradient.convertToSVGElement(canvas, document, canvas.getGeomDoubleConverter());
        assertEquals(SVGModes.MODE_USERSPACE, element.getAttribute("gradientUnits"));
        assertEquals(SVGBaseGradient.SPREAD_REFLECT, element.getAttribute("spreadMethod"));
        assertEquals(2, element.getChildNodes().getLength());
    }


}
