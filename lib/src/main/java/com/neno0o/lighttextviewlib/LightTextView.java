package com.neno0o.lighttextviewlib;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Matrix;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.FrameLayout;
import android.widget.TextView;

public class LightTextView extends TextView {

    private float offsetX;
    private float offsetY;
    private float anchorX;
    private float anchorY;
    private float angel;

    public LightTextView(Context context) {
        super(context, null);
        initLightTextView();
    }

    private void initLightTextView() {

        animation.setFillBefore(true);
        animation.setFillAfter(true);
        animation.setFillEnabled(true);

        if (!(getLayoutParams() instanceof ViewGroup.LayoutParams)) {
            FrameLayout.LayoutParams layoutParams =
                    new FrameLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
            setLayoutParams(layoutParams);
        }

        // default textview attributes
        setGravity(android.view.Gravity.CENTER);
        setTextColor(Color.WHITE);
        setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
        setBackgroundColor(Color.RED);
    }

    private Animation animation = new Animation() {
        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            Matrix matrix = t.getMatrix();
            matrix.postTranslate(offsetX, offsetY);
            matrix.postRotate(angel, anchorX, anchorY);
        }
    };

    public enum Position {
        LEFT_CORNER, RIGHT_CORNER
    }
}
