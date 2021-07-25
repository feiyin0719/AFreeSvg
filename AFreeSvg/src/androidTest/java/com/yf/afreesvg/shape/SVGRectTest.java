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
public class SVGRectTest extends SVGShapeBaseTest {
    private float x;
    private float y;
    private float width;
    private float height;

    public SVGRectTest(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
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
        SVGRect rect = new SVGRect(x, y, width, height);
        Assert.assertEquals(x, rect.getX(), TestConstant.DELTA_F);
        Assert.assertEquals(y, rect.getY(), TestConstant.DELTA_F);
        Assert.assertEquals(width, rect.getWidth(), TestConstant.DELTA_F);
        Assert.assertEquals(height, rect.getHeight(), TestConstant.DELTA_F);

        Element element = rect.convertToSVGElement(canvas, document, canvas.getGeomDoubleConverter());
        Assert.assertEquals(x, Float.valueOf(element.getAttribute("x")), TestConstant.DELTA_F);
        Assert.assertEquals(y, Float.valueOf(element.getAttribute("y")), TestConstant.DELTA_F);
        Assert.assertEquals(width, Float.valueOf(element.getAttribute("width")), TestConstant.DELTA_F);
        Assert.assertEquals(height, Float.valueOf(element.getAttribute("height")), TestConstant.DELTA_F);
    }
}