# AFreeSvg
一个可以在安卓上绘制svg图片的库
用法类似安卓canvas，通过SVGCanvas绘制图形，然后可以导出svg文件

## 使用方法
gradle添加jitpack仓库和依赖
```
 maven { url 'https://jitpack.io' }
 implementation 'com.github.feiyin0719:AFreeSvg:0.0.1-beta3'
```

## 参考代码
```java
        SVGCanvas svgCanvas = null;
        try {
            svgCanvas = new SVGCanvas(500, 500);
            SVGPaint paint = new SVGPaint();
            paint.setStyle(Paint.Style.STROKE);
            paint.setDashArray(new float[]{5, 5, 10});
            paint.setColor(Color.RED);
            paint.setStrokeWidth(2);
            svgCanvas.drawLine(10, 10, 200, 200, paint);
            SVGPaint paint1 = new SVGPaint();
            SVGLinearGradient svgLinearGradient = new SVGLinearGradient(new PointF(0, 0), new PointF(1, 0));
            svgLinearGradient.addStopColor(0, 0xffff0000);
            svgLinearGradient.addStopColor(0.5f, 0xff00ff00);
            svgLinearGradient.addStopColor(0.75f, 0xff00eeee);
            svgLinearGradient.addStopColor(1, 0xff0000ff);
            paint1.setGradient(svgLinearGradient);
            paint1.setStyle(Paint.Style.FILL_AND_STROKE);
            paint1.setStrokeWidth(2);
            paint1.setARGB(100, 200, 200, 0);
            svgCanvas.drawRect(new RectF(300, 300, 400, 450), paint1);
            svgCanvas.drawOval(new RectF(150, 150, 200, 200), paint1);
            SVGPaint paint2 = new SVGPaint();
            paint2.setStyle(Paint.Style.FILL);
            SVGRadialGradient gradient = new SVGRadialGradient(0.5f, 0.5f, 1, 0.8f, 0.8f);
            gradient.addStopColor(0.5f, 0xffff0000);
            gradient.addStopColor(1f, 0xff0000ff);
            paint2.setGradient(gradient);
            paint2.setFillRule(SVGPaint.FILL_RULE_EVENODD);
            Matrix matrix = new Matrix();
            matrix.postTranslate(10, 10);
            svgCanvas.setTransform(matrix);
            svgCanvas.drawRect(new RectF(300, 300, 400, 450), paint2);
            svgCanvas.drawPolygon(new float[]{100, 10, 40, 198, 190, 78, 10, 78, 160, 198}, paint2);
            svgCanvas.drawPolyline(new float[]{20, 20, 40, 25, 60, 40, 80, 120, 120, 140, 200, 180}, paint);
            String s = svgCanvas.getSVGXmlString();
            Log.i("myyf", s);


        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
```

生成图片如下
![](https://raw.githubusercontent.com/feiyin0719/AFreeSvg/fe7008bb8ac81d406506c79c0ad06aab8950c23e/test.svg)
## api介绍
- **SVGCanvas**

绘制canvas，使用方法类似安卓canvas。

图形绘制api 
   1. drawRect(RectF rectF, SVGPaint paint) //绘制矩形
   2. drawLine(float x1, float y1, float x2, float y2, SVGPaint paint) //绘制线段
   3. drawOval(RectF rectF, SVGPaint paint) //绘制椭圆或者圆
   4. drawPolygon(float[] points, SVGPaint paint) //绘制多边形
   5. drawPolyline(float points[], SVGPaint paint) //绘制多线段 

- **SVGPaint**

绘制画笔类，继承自安卓paint，主要用来添加一些安卓paint不支持的功能，以及解决无法获取渐变色和dash的问题
   1. setDashArray(float[] dashArray) //设置线段dash值
   2. setGradient(SVGGradient gradient) //设置渐变色
   3. setFillRule(String fillRule) //设置填充规则 值意义参考svg    nonzero / evenodd / inherit 默认nonzero

- **SVGGradient**

渐变色
  1. SVGLinearGradient  //线性渐变
  2. SVGRadialGradient //放射渐变
## 版本日志
- **0.0.1-beta3**
  1.新增渐变色支持
  2.新增绘制多边形、绘制多线段支持
  3.重构paint实现，新增SVGPaint类，用来解决安卓自带Paint无法获取dash和渐变值
- **0.0.1-beta2**
 1.解决描边和填充默认颜色问题
- **0.0.1-beta1**
 1.支持线段、矩形、圆形、椭圆的绘制填充
 2.支持transform

目前还处于开发阶段，后续会支持更多功能
