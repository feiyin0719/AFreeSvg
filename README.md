# AFreeSvg
一个可以在安卓上绘制svg图片的库
用法类似安卓canvas，通过SVGCanvas绘制图形，然后生成svg文本


参考代码
```java
SVGCanvas svgCanvas = null;
        try {
            svgCanvas = new SVGCanvas(500, 500);
            Paint paint = new Paint();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(Color.RED);
            paint.setStrokeWidth(2);
            svgCanvas.drawLine(10, 10, 200, 200, paint, new float[]{5, 5, 10});
            Paint paint1 = new Paint();
            paint1.setStyle(Paint.Style.FILL_AND_STROKE);
            paint1.setARGB(100, 200, 200, 0);
            svgCanvas.drawRect(new RectF(300, 300, 400, 450), paint1);
            svgCanvas.drawOval(new RectF(150, 150, 200, 200), paint1);
            Matrix matrix = new Matrix();
            matrix.postTranslate(10, 10);
            svgCanvas.setTransform(matrix);
            svgCanvas.drawRect(new RectF(300, 300, 400, 450), paint1);
            String s = svgCanvas.getSVGXmlString();
            Log.i("myyf", s);
            String s1 = svgCanvas.getSVGXmlString();
            Log.i("myyf", s1);

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
 ```
    
    
 目前仅支持线段、矩形、圆形、椭圆的绘制填充，以及transform操作，后续会支持更多功能
