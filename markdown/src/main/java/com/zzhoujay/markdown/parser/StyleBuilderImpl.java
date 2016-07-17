package com.zzhoujay.markdown.parser;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BulletSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.QuoteSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.widget.TextView;

import com.zzhoujay.markdown.style.CodeBlockSpan;
import com.zzhoujay.markdown.style.CodeSpan;
import com.zzhoujay.markdown.style.LinkSpan;
import com.zzhoujay.markdown.style.MarkDownBulletSpan;
import com.zzhoujay.markdown.style.MarkDownInnerBulletSpan;
import com.zzhoujay.markdown.style.MarkDownQuoteSpan;
import com.zzhoujay.markdown.style.UnderLineSpan;

/**
 * Created by zhou on 16-6-28.
 */
public class StyleBuilderImpl implements StyleBuilder {

    private static final int h1_color = Color.parseColor("#333333");
    private static final int h6_color = Color.parseColor("#777777");
    private static final int quota_color = Color.parseColor("#DDDDDD");
    private static final int code_color = Color.parseColor("#F0F0F0");
    private static final int link_color = Color.parseColor("#4078C0");

    private static final float scale_h1 = 2.25f;
    private static final float scale_h2 = 1.75f;
    private static final float scale_h3 = 1.5f;
    private static final float scale_h4 = 1.25f;
    private static final float scale_h5 = 1, scale_h6 = 1;

    private TextView textView;
    private Html.ImageGetter imageGetter;

    public StyleBuilderImpl(TextView textView, Html.ImageGetter imageGetter) {
        this.textView = textView;
        this.imageGetter = imageGetter;
    }

    @Override
    public SpannableStringBuilder em(CharSequence charSequence) {
        SpannableStringBuilder builder = SpannableStringBuilder.valueOf(charSequence);
        StyleSpan styleSpan = new StyleSpan(Typeface.BOLD);
        builder.setSpan(styleSpan, 0, charSequence.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(h1_color);
        builder.setSpan(colorSpan, 0, charSequence.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return builder;
    }

    @Override
    public SpannableStringBuilder italic(CharSequence charSequence) {
        SpannableStringBuilder builder = SpannableStringBuilder.valueOf(charSequence);
        StyleSpan styleSpan = new StyleSpan(Typeface.ITALIC);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(h1_color);
        builder.setSpan(colorSpan, 0, charSequence.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setSpan(styleSpan, 0, charSequence.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return builder;
    }

    @Override
    public SpannableStringBuilder emItalic(CharSequence charSequence) {
        SpannableStringBuilder builder = SpannableStringBuilder.valueOf(charSequence);
        StyleSpan styleSpan = new StyleSpan(Typeface.BOLD_ITALIC);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(h1_color);
        builder.setSpan(colorSpan, 0, charSequence.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setSpan(styleSpan, 0, charSequence.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return builder;
    }

    @Override
    public SpannableStringBuilder delete(CharSequence charSequence) {
        SpannableStringBuilder builder = SpannableStringBuilder.valueOf(charSequence);
        StrikethroughSpan span = new StrikethroughSpan();
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(h1_color);
        builder.setSpan(colorSpan, 0, charSequence.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setSpan(span, 0, charSequence.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return builder;
    }

    @Override
    public SpannableStringBuilder email(CharSequence charSequence) {
        return link(charSequence,"","");
//        return new SpannableStringBuilder(String.format("{email:%s}", charSequence));
    }

    @Override
    public SpannableStringBuilder code(CharSequence charSequence) {
        SpannableStringBuilder builder = SpannableStringBuilder.valueOf(charSequence);
        CodeSpan span = new CodeSpan(code_color);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(h1_color);
        builder.setSpan(colorSpan, 0, charSequence.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setSpan(span, 0, charSequence.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return builder;
    }

    @Override
    public SpannableStringBuilder h1(CharSequence charSequence) {
        return hWithUnderLine(charSequence, scale_h1);
    }

    @Override
    public SpannableStringBuilder h2(CharSequence charSequence) {
        return hWithUnderLine(charSequence, scale_h2);
    }

    @Override
    public SpannableStringBuilder h3(CharSequence charSequence) {
        return h(charSequence, scale_h3, h1_color);
    }

    @Override
    public SpannableStringBuilder h4(CharSequence charSequence) {
        return h(charSequence, scale_h4, h1_color);
    }

    @Override
    public SpannableStringBuilder h5(CharSequence charSequence) {
        return h(charSequence, scale_h5, h1_color);
    }

    @Override
    public SpannableStringBuilder h6(CharSequence charSequence) {
        return h(charSequence, scale_h6, h6_color);
    }

    @Override
    public SpannableStringBuilder quota(CharSequence charSequence) {
        SpannableStringBuilder spannableStringBuilder = SpannableStringBuilder.valueOf(charSequence);
        QuoteSpan span = new MarkDownQuoteSpan(quota_color);
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(h6_color);
        spannableStringBuilder.setSpan(span, 0, spannableStringBuilder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableStringBuilder.setSpan(foregroundColorSpan, 0, spannableStringBuilder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableStringBuilder;
    }

    @Override
    public SpannableStringBuilder ul(CharSequence charSequence) {
        SpannableStringBuilder spannableStringBuilder = SpannableStringBuilder.valueOf(charSequence);
        BulletSpan bulletSpan = new MarkDownBulletSpan(40, h1_color, 0);
        spannableStringBuilder.setSpan(bulletSpan, 0, spannableStringBuilder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableStringBuilder;
    }

    @Override
    public SpannableStringBuilder ol(CharSequence charSequence, int index) {
        SpannableStringBuilder spannableStringBuilder = SpannableStringBuilder.valueOf(charSequence);
        BulletSpan bulletSpan = new MarkDownBulletSpan(40, h1_color, index, textView);
        spannableStringBuilder.setSpan(bulletSpan, 0, spannableStringBuilder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableStringBuilder;
    }

    @Override
    public SpannableStringBuilder ul2(CharSequence charSequence) {
        SpannableStringBuilder spannableStringBuilder = SpannableStringBuilder.valueOf(charSequence);
        MarkDownInnerBulletSpan bulletSpan = new MarkDownInnerBulletSpan(40, h1_color, 0);
        spannableStringBuilder.setSpan(bulletSpan, 0, spannableStringBuilder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableStringBuilder;
    }

    @Override
    public SpannableStringBuilder ol2(CharSequence charSequence, int index) {
        SpannableStringBuilder spannableStringBuilder = SpannableStringBuilder.valueOf(charSequence);
        MarkDownInnerBulletSpan bulletSpan = new MarkDownInnerBulletSpan(40, h1_color, index);
        spannableStringBuilder.setSpan(bulletSpan, 0, spannableStringBuilder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableStringBuilder;
    }

    @Override
    public SpannableStringBuilder codeBlock(CharSequence... charSequence) {
        SpannableStringBuilder builder = SpannableStringBuilder.valueOf("$");
        AbsoluteSizeSpan sizeSpan = new AbsoluteSizeSpan(14, true);
        CodeBlockSpan codeBlockSpan = new CodeBlockSpan(getTextViewRealWidth(), code_color, charSequence);
        builder.setSpan(codeBlockSpan, 0, builder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setSpan(sizeSpan, 0, builder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return builder;
//        return new SpannableStringBuilder(String.format("codeBlock1:{%s}", charSequence));
    }

    @Override
    public SpannableStringBuilder link(CharSequence title, String link, String hint) {
        SpannableStringBuilder builder = SpannableStringBuilder.valueOf(title);
        LinkSpan linkSpan = new LinkSpan(link, link_color);
        builder.setSpan(linkSpan, 0, title.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return builder;
//        return new SpannableStringBuilder(String.format("{title:%s,link:%s,hint:%s}", title, link, hint));
    }

    @Override
    public SpannableStringBuilder image(CharSequence title, String url, String hint) {
        SpannableStringBuilder builder = SpannableStringBuilder.valueOf(title);
        ImageSpan imageSpan = new ImageSpan(imageGetter.getDrawable(url));
        builder.setSpan(imageSpan, 0, builder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return builder;
//        return new SpannableStringBuilder(String.format("{title:%s,url:%s,hint:%s}", title, url, hint));
    }

    protected SpannableStringBuilder h(CharSequence charSequence, float s, int color) {
        SpannableStringBuilder spannableStringBuilder = SpannableStringBuilder.valueOf(charSequence);
        StyleSpan styleSpan = new StyleSpan(Typeface.BOLD);
        RelativeSizeSpan sizeSpan = new RelativeSizeSpan(s);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(color);
        spannableStringBuilder.setSpan(styleSpan, 0, spannableStringBuilder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableStringBuilder.setSpan(sizeSpan, 0, spannableStringBuilder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableStringBuilder.setSpan(colorSpan, 0, spannableStringBuilder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableStringBuilder;
    }

    private SpannableStringBuilder hWithUnderLine(CharSequence charSequence, float s) {
        SpannableStringBuilder spannableStringBuilder =new SpannableStringBuilder(charSequence);
        int start = 0;
        StyleSpan styleSpan = new StyleSpan(Typeface.BOLD);
        RelativeSizeSpan sizeSpan = new RelativeSizeSpan(s);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(h1_color);
        spannableStringBuilder.setSpan(styleSpan, 0, spannableStringBuilder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableStringBuilder.setSpan(sizeSpan, 0, spannableStringBuilder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableStringBuilder.setSpan(colorSpan, 0, spannableStringBuilder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        Drawable underLine = new ColorDrawable(Color.parseColor("#eeeeee"));
        UnderLineSpan underLineSpan = new UnderLineSpan(underLine, getTextViewRealWidth(), 5);
        spannableStringBuilder.append('\n');
        start += charSequence.length() + 1;
        spannableStringBuilder.append("$");
        spannableStringBuilder.setSpan(underLineSpan, start, spannableStringBuilder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableStringBuilder;
    }

    private int getTextViewRealWidth() {
        if (textView != null) {
            return textView.getWidth() - textView.getPaddingRight() - textView.getPaddingLeft();
        }
        return 0;
    }

    @Override
    public SpannableStringBuilder gap() {
        SpannableStringBuilder builder = new SpannableStringBuilder("$");
        Drawable underLine = new ColorDrawable(Color.parseColor("#eeeeee"));
        UnderLineSpan underLineSpan = new UnderLineSpan(underLine, getTextViewRealWidth(), 10);
        builder.setSpan(underLineSpan, 0, builder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return builder;
    }
}
