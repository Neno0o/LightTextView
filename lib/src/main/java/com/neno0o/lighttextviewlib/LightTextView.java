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

import com.devspark.robototextview.widget.RobotoTextView;

public class LightTextView extends RobotoTextView {

    /* Animation attributes */
    private float offsetX;
    private float offsetY;
    private float anchorX;
    private float anchorY;
    private float angel;
    /* End of Animation attributes */

    /* Layout attributes */
    private int position;
    private int viewContainerId;
    /* End of Layout attributes */

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
        position = 1;
        viewContainerId = -1;
        setGravity(android.view.Gravity.CENTER);
        setTextColor(Color.WHITE);
        setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
        setBackgroundColor(Color.RED);
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setCurrentView(final View targetView) {

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
                doAnime(getMeasuredWidth(), targetView.getMeasuredWidth(), position);
            }
        });
    }

    private void doAnime(int lightWidth, int targetWidth, int position) {

        float edge = (float) ((lightWidth - 2 * Util.pixelFromDp(Util.pixelFromDp(10, getContext()), getContext())) / (2 * 1.414));
        // if LEFT_CORNER
        if (position == 1) {
            anchorX = -edge;
            offsetX = anchorX;
            angel = -45;
        } else if (position == 2) {
            offsetX = targetWidth + edge - lightWidth;
            anchorX = targetWidth + edge;
            angel = 45;
        }

        anchorY = (float) (1.414 * Util.pixelFromDp(Util.pixelFromDp(10, getContext()), getContext()) + edge);
        offsetY = anchorY;

        clearAnimation();
        startAnimation(animation);
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

}

