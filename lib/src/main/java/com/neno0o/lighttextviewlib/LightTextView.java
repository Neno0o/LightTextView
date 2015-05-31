package com.neno0o.lighttextviewlib;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Build;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class LightTextView extends TextView {

    private float offsetX;
    private float offsetY;
    private float anchorX;
    private float anchorY;
    private float angel;
    private int position = 1;
    private int viewContainerId = -1;

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

    public void setPosition(int position) {
        this.position = position;
    }

    public void setCurrentView(View targetView, int position) {

        // add new layout for combining the textview with the current view
        if (!addNewLayout(targetView)) {
            return;
        }

        ViewTreeObserver viewTreeObserver = getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT < 16) {
                    getViewTreeObserver().removeGlobalOnLayoutListener(this);
                } else {
                    getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }

            }
        });
    }

    private Animation animation = new Animation() {
        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            Matrix matrix = t.getMatrix();
            matrix.postTranslate(offsetX, offsetY);
            matrix.postRotate(angel, anchorX, anchorY);
        }
    };

    public class Position {
        public static final int LEFT_CORNER = 1;
        public static final int RIGHT_CORNER = 2;
    }

    private boolean addNewLayout(View targetView) {

        // check current views
        if (getParent() != null || targetView == null || targetView.getParent() == null || viewContainerId != -1) {
            return false;
        }

        ViewGroup parentContainer = (ViewGroup) targetView.getParent();

        // if the current view is framelayout
        if (targetView.getParent() instanceof FrameLayout) {
            ((FrameLayout) targetView.getParent()).addView(this);
        } else if (targetView.getParent() instanceof ViewGroup) {

            // get the index of the tagretview
            int targetViewIndex = parentContainer.indexOfChild(targetView);

            // generate new ID
            viewContainerId = generateViewId();

            // if relative layout
            if (targetView.getParent() instanceof RelativeLayout) {
                // loop through the tagetView parent childs
                for (int i = 0; i < parentContainer.getChildCount(); i++) {
                    if (i == targetViewIndex) {
                        continue;
                    }
                    View view = parentContainer.getChildAt(i);
                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
                    for (int j = 0; j < layoutParams.getRules().length; j++) {
                        if (layoutParams.getRules()[j] == targetView.getId()) {
                            layoutParams.getRules()[j] = viewContainerId;
                        }
                    }
                    view.setLayoutParams(layoutParams);
                }
            }
            parentContainer.removeView(targetView);

            // new layout
            FrameLayout lightTextViewContainer = new FrameLayout(getContext());
            ViewGroup.LayoutParams targetLayoutParam = targetView.getLayoutParams();
            lightTextViewContainer.setLayoutParams(targetLayoutParam);
            targetView.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

            // add target and label in this layout
            lightTextViewContainer.addView(targetView);
            lightTextViewContainer.addView(this);
            lightTextViewContainer.setId(viewContainerId);

            // add layout in parent container
            parentContainer.addView(lightTextViewContainer, targetViewIndex, targetLayoutParam);
        }
        return true;
    }
}

