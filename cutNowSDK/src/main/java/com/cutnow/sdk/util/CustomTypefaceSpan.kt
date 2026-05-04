/*
 * Copyright : (c) 2026 AndroPlaza
 * Company : AndroPlaza
 * Detailed : Software Development Company in Sri Lanka
 * Developer : Buddhika
 * Contact : support@androplaza.store
 * Whatsapp : +94711920144
 * All rights reserved.
 */

package com.cutnow.sdk.util
 
 import android.graphics.Paint
 import android.graphics.Typeface
 import android.text.TextPaint
 import android.text.style.MetricAffectingSpan
 
 class CustomTypefaceSpan(private val typeface: Typeface) : MetricAffectingSpan() {
     override fun updateDrawState(ds: TextPaint) {
         applyCustomTypeFace(ds, typeface)
     }
 
     override fun updateMeasureState(paint: TextPaint) {
         applyCustomTypeFace(paint, typeface)
     }
 
     private fun applyCustomTypeFace(paint: Paint, tf: Typeface) {
         val oldStyle = paint.typeface?.style ?: 0
         val fake = oldStyle and tf.style.inv()
 
         if (fake and Typeface.BOLD != 0) {
             paint.isFakeBoldText = true
         }
 
         if (fake and Typeface.ITALIC != 0) {
             paint.textSkewX = -0.25f
         }
 
         paint.typeface = tf
     }
 }
