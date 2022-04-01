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
