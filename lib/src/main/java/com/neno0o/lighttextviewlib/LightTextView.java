package com.neno0o.lighttextviewlib;

import android.content.Context;
import android.graphics.Matrix;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.TextView;

public class LightTextView extends TextView {

    private float offsetX;
    private float offsetY;
    private float anchorX;
    private float anchorY;
    private float angel;

    public LightTextView(Context context) {
        super(context, null);
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
        LEFT_TOP, RIGHT_TOP
    }

    public void setTargetView(View targetView, Position position) {

    }
}
