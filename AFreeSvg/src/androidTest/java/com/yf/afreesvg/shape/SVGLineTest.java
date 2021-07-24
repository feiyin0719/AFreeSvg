package com.yf.afreesvg.shape;

import com.yf.afreesvg.TestConstant;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.w3c.dom.Element;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class SVGLineTest extends SVGShapeBaseTest {
    private float x1;
    private float y1;
    private float x2;
    private float y2;

    public SVGLineTest(float x1, float y1, float x2, float y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {0f, 1f, 2f, 3f},
                {0, 0, 0, 0}
        });
    }

    @Test
    public void convertToSVGElement() {
        SVGLine line = new SVGLine(x1, y1, x2, y2);
        Assert.assertEquals(x1, line.getX1(), TestConstant.DELTA_F);
        Assert.assertEquals(x2, line.getX2(), TestConstant.DELTA_F);
        Assert.assertEquals(y1, line.getY1(), TestConstant.DELTA_F);
        Assert.assertEquals(y2, line.getY2(), TestConstant.DELTA_F);

        Element element = line.convertToSVGElement(canvas, document, canvas.getGeomDoubleConverter());
        Assert.assertEquals(x1, Float.valueOf(element.getAttribute("x1")), TestConstant.DELTA_F);
        Assert.assertEquals(x2, Float.valueOf(element.getAttribute("x2")), TestConstant.DELTA_F);
        Assert.assertEquals(y1, Float.valueOf(element.getAttribute("y1")), TestConstant.DELTA_F);
        Assert.assertEquals(y2, Float.valueOf(element.getAttribute("y2")), TestConstant.DELTA_F);
    }
}