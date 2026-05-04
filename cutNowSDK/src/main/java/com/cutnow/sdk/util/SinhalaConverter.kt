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

import com.cutnow.sdk.R

import android.content.Context
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableStringBuilder

object SinhalaConverter {

    // ─────────────────────────────────────────────────────────────────────────
    fun hasSinhalaUnicode(text: String?): Boolean {
        if (text == null) return false
        for (char in text) {
            val code = char.code
            if (code in 0x0D80..0x0D80 + 127) return true
        }
        return false
    }

    // ─────────────────────────────────────────────────────────────────────────
    fun unicodeToLegacySpannable(text: String?, context: Context, customFontPath: String? = null): CharSequence {
        if (text == null || text.isEmpty()) return ""

        val sinhalaFont: Typeface = if (customFontPath != null) {
            try {
                if (customFontPath.startsWith("fonts/")) {
                    Typeface.createFromAsset(context.assets, customFontPath)
                } else {
                    Typeface.createFromAsset(context.assets, "fonts/sinhala/$customFontPath")
                }
            } catch (e: Exception) {
                getAmanthaTypeface(context) ?: Typeface.DEFAULT
            }
        } else {
            getAmanthaTypeface(context) ?: Typeface.DEFAULT
        }

        val ssb = SpannableStringBuilder()
        val nonSinhalaBuf = StringBuilder()
        val sinhalaBuf = StringBuilder()

        for (i in text.indices) {
            val c = text[i]
            val isSinhala = c.code in 0x0D80..0x0DFF || c == '\u200D'

            if (isSinhala) {
                if (nonSinhalaBuf.isNotEmpty()) {
                    appendNonSinhala(ssb, nonSinhalaBuf.toString(),
                            if (isSinhalaLegacyFont(customFontPath)) Typeface.DEFAULT else sinhalaFont)
                    nonSinhalaBuf.setLength(0)
                }
                sinhalaBuf.append(c)
            } else {
                if (sinhalaBuf.isNotEmpty()) {
                    appendSinhalaLegacy(ssb, sinhalaBuf.toString(), sinhalaFont)
                    sinhalaBuf.setLength(0)
                }
                nonSinhalaBuf.append(c)
            }
        }
        
        if (nonSinhalaBuf.isNotEmpty()) {
            appendNonSinhala(ssb, nonSinhalaBuf.toString(),
                    if (isSinhalaLegacyFont(customFontPath)) Typeface.DEFAULT else sinhalaFont)
        }
        if (sinhalaBuf.isNotEmpty()) {
            appendSinhalaLegacy(ssb, sinhalaBuf.toString(), sinhalaFont)
        }

        return ssb
    }

    // ─────────────────────────────────────────────────────────────────────────
    fun isSinhalaLegacyFont(fontName: String?): Boolean {
        if (fontName == null) return false
        val lower = fontName.lowercase()
        val nameOnly = if (lower.contains("/")) lower.substringAfterLast("/") else lower
        
        return nameOnly.startsWith("4u-") || nameOnly.startsWith("fm") || nameOnly.startsWith("ams")
                || nameOnly.startsWith("apex-a.pura") || nameOnly.startsWith("bu_")
                || nameOnly.startsWith("sepalika") || nameOnly.startsWith("sara-")
                || nameOnly.startsWith("nidahasa") || nameOnly.startsWith("m-kusumi")
                || nameOnly.startsWith("npw-") || nameOnly.startsWith("pg-")
                || nameOnly.startsWith("ranmad") || nameOnly.startsWith("osevvandi")
                || nameOnly.startsWith("astra") || nameOnly.startsWith("ubin")
                || nameOnly.startsWith("samantha")
    }

    // ─────────────────────────────────────────────────────────────────────────
    private fun appendNonSinhala(ssb: SpannableStringBuilder, text: String, tf: Typeface?) {
        val start = ssb.length
        ssb.append(text)
        ssb.setSpan(CustomTypefaceSpan(tf ?: Typeface.DEFAULT),
                start, ssb.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    }

    private fun appendSinhalaLegacy(ssb: SpannableStringBuilder, unicodeText: String, font: Typeface) {
        val legacyText = convertUnicodeToLegacy(unicodeText)
        val start = ssb.length
        ssb.append(legacyText)
        ssb.setSpan(CustomTypefaceSpan(font), start, ssb.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    }

    // ─────────────────────────────────────────────────────────────────────────
    private fun convertUnicodeToLegacy(str: String?): String {
        if (str == null) return ""
        var s = str

        // Step 0: Escape ASCII chars that overlap with legacy output glyphs
        s = s.replace(",",  "\uFFE6")
        s = s.replace(".",  "\uE001")
        s = s.replace("(",  "\uFFEB")
        s = s.replace(")",  "\uFFE9")
        s = s.replace("%",  "\uFFD5")
        s = s.replace("/",  "\uFFD4")
        s = s.replace("?",  "\uFFD3")
        s = s.replace("!",  "\uFFD2")
        s = s.replace("=",  "\uFFCF")
        s = s.replace("'",  "\uFFCE")
        s = s.replace("+",  "\uFFCD")
        s = s.replace(":",  "\uFFCC")
        s = s.replace(";",  "\uFEB6")

        // Step 1: Complex multi-char clusters (must come first)
        s = s.replace("ත්රෛ", "ff;\uFFD5")
        s = s.replace("්ය",   "H")
        s = s.replace("්ර",   "\uFFD5")   // % placeholder

        // Step 2: Ekaraya group — full consonant+vowel pre-computed pairs
        // ── ෞ ──
        s = s.replace("ෂෞ","fI!"); s = s.replace("ශෞ","fY!"); s = s.replace("බෞ","fn!")
        s = s.replace("චෞ","fp!"); s = s.replace("ඩෞ","fv!"); s = s.replace("ෆෞ","f*!")
        s = s.replace("ගෞ","f.!"); s = s.replace("ජෞ","fc!"); s = s.replace("කෞ","fl!")
        s = s.replace("ලෞ","f,!"); s = s.replace("මෞ","fu!"); s = s.replace("නෞ","fk!")
        s = s.replace("පෞ","fm!"); s = s.replace("දෞ","fo!"); s = s.replace("රෞ","fr!")
        s = s.replace("සෞ","fi!"); s = s.replace("ටෞ","fg!"); s = s.replace("තෞ","f;!")
        s = s.replace("භෞ","fN!"); s = s.replace("ඤෞ","f[!"); s = s.replace("ඡෞ","fP!")
        s = s.replace("වෞ","fj!"); s = s.replace("ධෞ","fO!"); s = s.replace("ථෞ","f:!")
        s = s.replace("ණෞ","fK!"); s = s.replace("යෞ","fh!"); s = s.replace("හෞ","fy!")
        // ── ෝ ──
        s = s.replace("ෂෝ","fIda"); s = s.replace("ඹෝ","fUda"); s = s.replace("ඡෝ","fPda")
        s = s.replace("ඪෝ","fVda"); s = s.replace("ඝෝ","f>da"); s = s.replace("ඛෝ","fLda")
        s = s.replace("ළෝ","f<da"); s = s.replace("ණෝ","fKda"); s = s.replace("ඵෝ","fMdda")
        s = s.replace("ඨෝ","fGda"); s = s.replace("ශෝ","fYda"); s = s.replace("ඥෝ","f{da")
        s = s.replace("ඳෝ","f|da"); s = s.replace("බෝ","fnda"); s = s.replace("චෝ","fpda")
        s = s.replace("ඩෝ","fvda"); s = s.replace("ෆෝ","f*da"); s = s.replace("ගෝ","f.da")
        s = s.replace("හෝ","fyda"); s = s.replace("ජෝ","fcda"); s = s.replace("කෝ","flda")
        s = s.replace("ලෝ","f,da"); s = s.replace("මෝ","fuda"); s = s.replace("නෝ","fkda")
        s = s.replace("පෝ","fmda"); s = s.replace("දෝ","foda"); s = s.replace("රෝ","frda")
        s = s.replace("සෝ","fida"); s = s.replace("ටෝ","fgda"); s = s.replace("වෝ","fjda")
        s = s.replace("තෝ","f;da"); s = s.replace("භෝ","fNda"); s = s.replace("යෝ","fhda")
        s = s.replace("ඤෝ","f[da"); s = s.replace("ධෝ","fOda"); s = s.replace("ථෝ","f:da")
        // ── ො ──
        s = s.replace("ෂො","fId"); s = s.replace("ඹො","fU"); s = s.replace("ඡො","fP")
        s = s.replace("ඪො","fVd"); s = s.replace("ඝො","f>d"); s = s.replace("ඛො","fLd")
        s = s.replace("ළො","f<d"); s = s.replace("ණො","fKd"); s = s.replace("ඵො","fMd")
        s = s.replace("ඨො","fGd"); s = s.replace("ශො","fYd"); s = s.replace("ඥො","f{d")
        s = s.replace("ඳො","f|d"); s = s.replace("බො","fnd"); s = s.replace("චො","fpd")
        s = s.replace("ඩො","fvd"); s = s.replace("ෆො","f*d"); s = s.replace("ගො","f.d")
        s = s.replace("හො","fyd"); s = s.replace("ජො","fcd"); s = s.replace("කො","fld")
        s = s.replace("ලො","f,d"); s = s.replace("මො","fud"); s = s.replace("නො","fkd")
        s = s.replace("පො","fmd"); s = s.replace("දො","fod"); s = s.replace("රො","frd")
        s = s.replace("සො","fid"); s = s.replace("ටො","fgd"); s = s.replace("වො","fjd")
        s = s.replace("තො","f;d"); s = s.replace("භො","fNd"); s = s.replace("යො","fhd")
        s = s.replace("ඤො","f[d"); s = s.replace("ධො","fOd"); s = s.replace("ථො","f:d")
        // ── ේ ──
        s = s.replace("ෂේ","fIa"); s = s.replace("ඝේ","f>a"); s = s.replace("ළේ","f<a")
        s = s.replace("ණේ","fKa"); s = s.replace("ඵේ","fMa"); s = s.replace("ඨේ","fGa")
        s = s.replace("ශේ","fYa"); s = s.replace("ඥේ","f{a"); s = s.replace("ඳේ","f|a")
        s = s.replace("ගේ","f.a"); s = s.replace("හේ","fya"); s = s.replace("පේ","fma")
        s = s.replace("කේ","fla"); s = s.replace("ලේ","f,a"); s = s.replace("නේ","fka")
        s = s.replace("දේ","foa"); s = s.replace("සේ","fia"); s = s.replace("තේ","f;a")
        s = s.replace("භේ","fNa"); s = s.replace("යේ","fha"); s = s.replace("ඤේ","f[a")
        s = s.replace("ථේ","f:a"); s = s.replace("රේ","\u00BE" + "a")
        // special single-glyph forms for ේ
        s = s.replace("ඛේ","f\u00C4"); // fÄ
        s = s.replace("ධේ","f\u00E8"); // fè
        s = s.replace("මේ","f\u00EF"); // fï
        s = s.replace("ෆේ","f*")
        s = s.replace("ජේ","f\u2013")
        s = s.replace("ටේ","f\u00DC")
        s = s.replace("බේ","f\u00ED")
        s = s.replace("ඩේ","f\u00E2")
        s = s.replace("චේ","f\u00C9")
        s = s.replace("ඹේ","f\u00F2")
        s = s.replace("ඡේ","f\u00FE")
        s = s.replace("වේ","f\u00F5")   // fõ
        // ── ෙ ──
        s = s.replace("ෂෙ","fI"); s = s.replace("ඹෙ","fU"); s = s.replace("ඡෙ","fP")
        s = s.replace("ඪෙ","fV"); s = s.replace("ඝෙ","f>"); s = s.replace("ළෙ","f<")
        s = s.replace("ණෙ","fK"); s = s.replace("ඵෙ","fM"); s = s.replace("ඨෙ","fG")
        s = s.replace("ශෙ","fY"); s = s.replace("ඥෙ","f{"); s = s.replace("ඳෙ","\u00CB" + "") // fË
        s = s.replace("බෙ","fn"); s = s.replace("චෙ","fp"); s = s.replace("ඩෙ","fv")
        s = s.replace("ෆෙ","f*"); s = s.replace("ගෙ","f."); s = s.replace("හෙ","fy")
        s = s.replace("ජෙ","fc"); s = s.replace("කෙ","fl"); s = s.replace("ලෙ","f,")
        s = s.replace("මෙ","fu"); s = s.replace("නෙ","fk"); s = s.replace("පෙ","fm")
        s = s.replace("දෙ","fo"); s = s.replace("රෙ","fr"); s = s.replace("සෙ","fi")
        s = s.replace("ටෙ","fg"); s = s.replace("වෙ","fj"); s = s.replace("තෙ","f;")
        s = s.replace("භෙ","fN"); s = s.replace("යෙ","fh"); s = s.replace("ඤෙ","f[")
        s = s.replace("ධෙ","fO"); s = s.replace("ථෙ","f:"); s = s.replace("ඛෙ","fn")
        // ── ෛ ──
        s = s.replace("ශෛ","ffY"); s = s.replace("චෛ","ffp"); s = s.replace("ජෛ","ffc")
        s = s.replace("කෛ","ffl"); s = s.replace("මෛ","ffu"); s = s.replace("පෛ","ffm")
        s = s.replace("දෛ","ffo"); s = s.replace("තෛ","ff;"); s = s.replace("නෛ","ffk")
        s = s.replace("ධෛ","ffO"); s = s.replace("වෛ","ffj")

        // Step 3: Special Paapilla (ු/ූ) forms — only a few have unique glyphs
        s = s.replace("තු", ";=");  s = s.replace("තූ", ";+")
        s = s.replace("ගු", ".=");  s = s.replace("ගූ", ".+")
        s = s.replace("කු", "l=");  s = s.replace("කූ", "l+")
        s = s.replace("රු", "re");  s = s.replace("රූ", "rE")

        // Step 4: Special single-glyph combinations (unique FM Abhaya glyphs)
        s = s.replace("ඳි",  "\u00A2");   // ¢
        s = s.replace("ඳී",  "\u00A3");   // £
        s = s.replace("දූ",  "\u00A5");   // ¥
        s = s.replace("දී",  "\u00A7");   // §
        s = s.replace("ලූ",  "\u00A8");   // ¨
        s = s.replace("ර්ය", "\u00A9"); // ©
        s = s.replace("ඳූ",  "\u00AA");   // ª
        s = s.replace("ඨි",  "\u00C0");   // À
        s = s.replace("ඨී",  "\u00C1");   // Á
        s = s.replace("ඡී",  "\u00C2");   // Â
        s = s.replace("ඛ්",  "\u00C4");   // Ä
        s = s.replace("ඛි",  "\u00C5");   // Å
        s = s.replace("ලු",  "\u00C6");   // Æ
        s = s.replace("ඛී",  "\u00C7");   // Ç
        s = s.replace("දි",  "\u00C8");   // È
        s = s.replace("ච්",  "\u00C9");   // É
        s = s.replace("ජ්",  "\u00CA");   // Ê
        s = s.replace("රී",  "\u00CD");   // Í
        s = s.replace("ඪී",  "\u00CE");   // Î
        s = s.replace("චි",  "\u00D1");   // Ñ
        s = s.replace("ථී",  "\u00D2");   // Ò
        s = s.replace("ජී",  "\u00D4");   // Ô
        s = s.replace("චී",  "\u00D6");   // Ö
        s = s.replace("ඞ්",  "\u00D9");   // Ù
        s = s.replace("ඵී",  "\u00DA");   // Ú
        s = s.replace("ට්",  "\u00DC");   // Ü
        s = s.replace("ඵි",  "\u00DD");   // Ý
        s = s.replace("රි",  "\u00DF");   // ß
        s = s.replace("ටී",  "\u00E0");   // à
        s = s.replace("ටි",  "\u00E1");   // á
        s = s.replace("ඩ්",  "\u00E2");   // â
        s = s.replace("ඩී",  "\u00E3");   // ã
        s = s.replace("ඩි",  "\u00E4");   // ä
        s = s.replace("ඬ්",  "\u00E5");   // å
        s = s.replace("ඬි",  "\u00E7");   // ç
        s = s.replace("ධ්",  "\u00E8");   // è
        s = s.replace("ඬී",  "\u00E9");   // é
        s = s.replace("ධි",  "\u00EA");   // ê
        s = s.replace("ධී",  "\u00EB");   // ë
        s = s.replace("බි",  "\u00EC");   // ì
        s = s.replace("බ්",  "\u00ED");   // í
        s = s.replace("බී",  "\u00EE");   // î
        s = s.replace("ම්",  "\u00EF");   // ï
        s = s.replace("ජි",  "\u00F0");   // ð
        s = s.replace("මි",  "\u00F1");   // ñ
        s = s.replace("ඹ්",  "\u00F2");   // ò
        s = s.replace("මී",  "\u00F3");   // ó
        s = s.replace("ඹි",  "\u00F4");   // ô
        s = s.replace("ව්",  "\u00F5");   // õ
        s = s.replace("ඹී",  "\u00F6");   // ö
        s = s.replace("ඳු",  "\u00F7");   // ÷
        s = s.replace("ද්ර",  "\u00F8");  // ø
        s = s.replace("වී",  "\u00F9");   // ù
        s = s.replace("වි",  "\u00FA");   // ú
        s = s.replace("ඞී",  "\u00FC");   // ü
        s = s.replace("ඡි",  "\u00FD");   // ý
        s = s.replace("ඡ්",  "\u00FE");   // þ
        s = s.replace("දු",  "\u00FF");   // ÿ
        s = s.replace("ඤු",  "\u2122");   // ™
        s = s.replace("ළු",  "\u00BF");   // ¿
        s = s.replace("ර්",  "\u00BE");   // ¾ (Repaya)

        // Step 5: Consonant substitutions
        s = s.replace("ශ", "Y"); s = s.replace("ෂ", "I"); s = s.replace("ස", "i")
        s = s.replace("හ", "y"); s = s.replace("ල", ","); s = s.replace("ව", "j")
        s = s.replace("ය", "h"); s = s.replace("ර", "r"); s = s.replace("ළ", "<")
        s = s.replace("ෆ", "*"); s = s.replace("ද", "o"); s = s.replace("ධ", "O")
        s = s.replace("ත", ";"); s = s.replace("ථ", ":"); s = s.replace("න", "k")
        s = s.replace("ම", "u"); s = s.replace("ඹ", "U"); s = s.replace("ප", "m")
        s = s.replace("ඵ", "M"); s = s.replace("බ", "n"); s = s.replace("භ", "N")
        s = s.replace("ක", "l"); s = s.replace("ඛ", "L"); s = s.replace("ග", ".")
        s = s.replace("ඝ", ">"); s = s.replace("ඞ", "X"); s = s.replace("ඟ", "\u00D5")
        s = s.replace("ච", "p"); s = s.replace("ඡ", "P"); s = s.replace("ජ", "c")
        s = s.replace("ඣ", "\u00AE"); s = s.replace("ඤ", "["); s = s.replace("ඥ", "{")
        s = s.replace("ට", "g"); s = s.replace("ඨ", "G"); s = s.replace("ඩ", "v")
        s = s.replace("ඪ", "V"); s = s.replace("ණ", "K"); s = s.replace("ඬ", "~")
        s = s.replace("ඳ", "|")

        // Step 6: Independent vowels
        s = s.replace("අ", "w"); s = s.replace("ආ", "wd"); s = s.replace("ඇ", "we")
        s = s.replace("ඈ", "wE"); s = s.replace("ඉ", "b"); s = s.replace("ඊ", "B")
        s = s.replace("උ", "W"); s = s.replace("ඌ", "W!"); s = s.replace("ඍ", "RD")
        s = s.replace("ඎ", "RRD"); s = s.replace("එ", "t"); s = s.replace("ඒ", "ta")
        s = s.replace("ඓ", "ft"); s = s.replace("ඔ", "T"); s = s.replace("ඕ", "\u00B4")
        s = s.replace("ඖ", "T!")

        // Step 7: Remaining dependent vowels (post-base)
        s = s.replace("ා", "d"); s = s.replace("ැ", "e"); s = s.replace("ෑ", "E")
        s = s.replace("ි", "s"); s = s.replace("ී", "S"); s = s.replace("ු", "q")
        s = s.replace("ූ", "Q"); s = s.replace("ෘ", "D"); s = s.replace("ෲ", "DD")
        s = s.replace("ෟ", "!"); s = s.replace("ං", "x"); s = s.replace("ඃ", "#")
        s = s.replace("්", "a")

        // Step 8: Restore escaped ASCII punctuation
        s = s.replace("\uFFE6", ",")
        s = s.replace("\uE001",  ".")
        s = s.replace("\uFFEB", "(")
        s = s.replace("\uFFE9", ")")
        s = s.replace("\uFFD5", "%")
        s = s.replace("\uFFD4", "/")
        s = s.replace("\uFFD3", "?")
        s = s.replace("\uFFD2", "!")
        s = s.replace("\uFFCF", "=")
        s = s.replace("\uFFCE", "'")
        s = s.replace("\uFFCD", "+")
        s = s.replace("\uFFCC", ":")
        s = s.replace("\uFEB6", ";")

        return s
    }

    // ─────────────────────────────────────────────────────────────────────────
    fun getAmanthaTypeface(context: Context): Typeface? {
        return try {
            Typeface.createFromAsset(context.assets, "fonts/sinhala/4u-Amantha.ttf")
        } catch (e: Exception) {
            null
        }
    }
}
