package com.technics.dogwizard;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.AttributeSet;

import com.parse.ParseImageView;

public class RoundedParseImageView extends ParseImageView {
    private boolean isLoaded = false;

    public RoundedParseImageView(Context context) {
        super(context);
    }
    public RoundedParseImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }
    public RoundedParseImageView(Context context, AttributeSet attributeSet, int defStyle) {
        super(context, attributeSet, defStyle);
    }
    @Override
    public void setImageBitmap(Bitmap bitmap){
        RoundedBitmapDrawable rounded = RoundedBitmapDrawableFactory.create(getResources(),bitmap);
        rounded.setCornerRadius(bitmap.getWidth());
        super.setImageDrawable(rounded);
        this.isLoaded = true;
    }
}
