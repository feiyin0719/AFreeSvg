package com.yf.afreesvg.ktx

import com.yf.afreesvg.filter.*

inline fun filterGroup(init: SVGFilterGroup.() -> Unit): SVGFilterGroup {
    val svgFilterGroup = SVGFilterGroup()
    svgFilterGroup.init()
    return svgFilterGroup
}

inline fun <T : SVGBaseFilter.SVGBaseFilterEffect> SVGBaseFilter.initFilterNode(
    filterEffect: T,
    init: T.() -> Unit
) {
    filterEffect.init()
    addEffect(filterEffect)
}


inline fun SVGBaseFilter.blendFilterNode(init: SVGFilterGroup.SVGBlendFilterEffect.() -> Unit) =
    initFilterNode(SVGFilterGroup.SVGBlendFilterEffect(), init)

inline fun SVGBaseFilter.mergeFilterNode(init: SVGFilterGroup.SVGMergeFilterEffect.() -> Unit) =
    initFilterNode(SVGFilterGroup.SVGMergeFilterEffect(), init)


fun SVGFilterGroup.SVGMergeFilterEffect.mergeNode(init: () -> String) {
    addMergeNode(init())
}

inline fun SVGBaseFilter.compositeFilterNode(init: SVGFilterGroup.SVGCompositeFilterEffect.() -> Unit) =
    initFilterNode(SVGFilterGroup.SVGCompositeFilterEffect(), init)

inline fun SVGBaseFilter.colorFilterNode(init: SVGColorFilter.SVGColorFilterEffect.() -> Unit) =
    initFilterNode(SVGColorFilter.SVGColorFilterEffect(floatArrayOf()), init)

inline fun SVGBaseFilter.gaussianFilterNode(init: SVGGaussianBlurFilter.SVGGaussianBlurFilterEffect.() -> Unit) =
    initFilterNode(SVGGaussianBlurFilter.SVGGaussianBlurFilterEffect(), init)

inline fun SVGBaseFilter.convolveMatrixFilterNode(init: SVGConvolveMatrixFilter.SVGConvolveMatrixFilterEffect.() -> Unit) =
    initFilterNode(SVGConvolveMatrixFilter.SVGConvolveMatrixFilterEffect(floatArrayOf()), init)

inline fun SVGBaseFilter.offsetFilterNode(init: SVGOffsetFilter.SVGOffsetFilterEffect.() -> Unit) =
    initFilterNode(SVGOffsetFilter.SVGOffsetFilterEffect(0f, 0f), init)

