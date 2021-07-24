package com.yf.afreesvg;

import com.yf.afreesvg.util.DoubleConverter;

import org.junit.Before;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SVGBaseTest {
    protected SVGCanvas canvas;
    protected Document document;


    @Before
    public void setUp() throws Exception {

        DocumentBuilderFactory factory = DocumentBuilderFactory
                .newInstance();
        DocumentBuilder builder = null;

        builder = factory.newDocumentBuilder();
        document = builder.newDocument();
        canvas = mock(SVGCanvas.class);
        DoubleConverter doubleConverter = new DoubleConverter(1);
        when(canvas.getGeomDoubleConverter()).thenReturn(doubleConverter);
    }
}
