package com.tbuonomo.bounceloading;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * Created by tommy on 02/07/16.
 */
public class BounceCircleLoadingView extends RelativeLayout {
    private static final int DEFAULT_DURATION = 700;
    private static final int DEFAULT_POINT_COLOR = Color.WHITE;
    private static final int DEFAULT_BACKGROUND_COLOR = Color.parseColor("#01579B");

    private enum InterpolatorValues {
        FAST_IN_FAST_OUT(new FastOutSlowInInterpolator()),
        ANTICIPATE_OVERSHOOT(new AnticipateOvershootInterpolator()),
        OVERSHOOT(new OvershootInterpolator()),
        ACCELERATE_DECELERATE(new AccelerateDecelerateInterpolator());

        private Interpolator mInterpolator;

        InterpolatorValues(Interpolator interpolator) {
            this.mInterpolator = interpolator;
        }

        public Interpolator getInterpolator() {
            return mInterpolator;
        }
    }

    private ImageView mLeftCircle, mCenterCircle, mRightCircle, mBackgroundCircle;
    private AnimatorSet mCircleAnimator;
    private int mDuration;
    private Interpolator mInterpolator;
    private PointF mLeftPoint, mRightPoint, mCenterPoint;

    public BounceCircleLoadingView(Context context) {
        super(context);
        init(context, null);
    }

    public BounceCircleLoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public BounceCircleLoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        removeViewsIfNeeded();

        mLeftCircle = new ImageView(context);
        mLeftCircle.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.point_circle));

        mCenterCircle = new ImageView(context);
        mCenterCircle.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.point_circle));

        mRightCircle = new ImageView(context);
        mRightCircle.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.point_circle));

        mBackgroundCircle = new ImageView(context);
        mBackgroundCircle.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.point_circle));

        addView(mBackgroundCircle);
        addView(mLeftCircle);
        addView(mCenterCircle);
        addView(mRightCircle);

        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.BounceCircleLoadingView);
            int pointColor = a.getColor(R.styleable.BounceCircleLoadingView_pointColor, -1);
            setUpCircleColors(mLeftCircle, pointColor == -1 ?
                    DEFAULT_POINT_COLOR : pointColor);
            setUpCircleColors(mCenterCircle, pointColor == -1 ?
                    DEFAULT_POINT_COLOR : pointColor);
            setUpCircleColors(mRightCircle, pointColor == -1 ?
                    DEFAULT_POINT_COLOR : pointColor);

            int backgroundColor = a.getColor(R.styleable.BounceCircleLoadingView_backgroundColor, -1);
            setUpCircleColors(mBackgroundCircle, backgroundColor == -1 ? DEFAULT_BACKGROUND_COLOR : backgroundColor);

            int duration = a.getInt(R.styleable.BounceCircleLoadingView_animationDuration, -1);
            int interpolator = a.getInt(R.styleable.BounceCircleLoadingView_interpolator, -1);
            mInterpolator = interpolator == -1 ? mInterpolator = InterpolatorValues.FAST_IN_FAST_OUT.getInterpolator() :
                    InterpolatorValues.values()[interpolator].getInterpolator();
            mDuration = duration == -1 || duration < 0 ? DEFAULT_DURATION : duration;

            int pointSize = a.getDimensionPixelSize(R.styleable.BounceCircleLoadingView_pointSize, -1);
            if (pointSize != -1) {
                setUpCircleSize(pointSize, mLeftCircle);
                setUpCircleSize(pointSize, mRightCircle);
                setUpCircleSize(pointSize, mCenterCircle);
            }
            a.recycle();
        } else {
            setUpCircleColors(mLeftCircle,
                    DEFAULT_POINT_COLOR);
            setUpCircleColors(mCenterCircle,
                    DEFAULT_POINT_COLOR);
            setUpCircleColors(mRightCircle,
                    DEFAULT_POINT_COLOR);

            setUpCircleColors(mBackgroundCircle, DEFAULT_BACKGROUND_COLOR);

            mDuration = DEFAULT_DURATION;
        }
    }

    private void removeViewsIfNeeded() {
        if (getChildCount() > 0) {
            removeAllViews();
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        setUpBackgroundSize(w, h, mBackgroundCircle);
        setUpCirclePositions(w, h);
        setUpAnimators(mDuration, mInterpolator);
        mCircleAnimator.start();
    }

    private void setUpCirclePositions(int w, int h) {
        int margin = w / 5;
        int pointRadius = mCenterCircle.getMeasuredWidth() / 2;

        // Center circle
        mCenterPoint = new PointF();
        LayoutParams paramCenter = (LayoutParams) mCenterCircle.getLayoutParams();
        mCenterPoint.x = paramCenter.leftMargin = w / 2 - pointRadius;
        mCenterPoint.y = paramCenter.topMargin = h / 2 - pointRadius;


        // Left circle
        mLeftPoint = new PointF();
        LayoutParams paramLeft = (LayoutParams) mLeftCircle.getLayoutParams();
        mLeftPoint.x = paramLeft.leftMargin = margin - pointRadius;
        mLeftPoint.y = paramLeft.topMargin = h / 2 - pointRadius;

        // Center circle
        mRightPoint = new PointF();
        LayoutParams paramRight = (LayoutParams) mRightCircle.getLayoutParams();
        mRightPoint.x = paramRight.leftMargin = w - margin - pointRadius;
        mRightPoint.y = paramRight.topMargin = h / 2 - pointRadius;
    }

    private void setUpAnimators(int duration, Interpolator interpolator) {
        //STEP 1
        ObjectAnimator centerPointAnimatorX1 = ObjectAnimator.ofFloat(mCenterCircle, View.TRANSLATION_X, 0, mLeftPoint.x - mCenterPoint.x);
        ObjectAnimator centerPointAnimatorY1 = ObjectAnimator.ofFloat(mCenterCircle, View.TRANSLATION_Y, 0, mLeftPoint.x - mCenterPoint.x, 0);
        final ObjectAnimator leftPointAnimatorX1 = ObjectAnimator.ofFloat(mLeftCircle, View.TRANSLATION_X, 0, mCenterPoint.x - mLeftPoint.x);
        leftPointAnimatorX1.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                leftPointAnimatorX1.setCurrentPlayTime(0);
            }
        });
        AnimatorSet step1 = new AnimatorSet();
        step1.playTogether(centerPointAnimatorX1, centerPointAnimatorY1, leftPointAnimatorX1);

        //STEP 2
        ObjectAnimator centerPointAnimatorX2 = ObjectAnimator.ofFloat(mCenterCircle, View.TRANSLATION_X, 0, mRightPoint.x - mCenterPoint.x);
        final ObjectAnimator rightPointAnimatorX1 = ObjectAnimator.ofFloat(mRightCircle, View.TRANSLATION_X, 0, mCenterPoint.x - mRightPoint.x);
        rightPointAnimatorX1.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                rightPointAnimatorX1.setCurrentPlayTime(0);
            }
        });
        final AnimatorSet step2 = new AnimatorSet();
        step2.playTogether(centerPointAnimatorX2, centerPointAnimatorY1, rightPointAnimatorX1);

        // STEP 3
        ObjectAnimator centerPointAnimatorY2 = ObjectAnimator.ofFloat(mCenterCircle, View.TRANSLATION_Y, 0, mCenterPoint.x - mLeftPoint.x, 0);
        final AnimatorSet step3 = new AnimatorSet();
        step3.playTogether(centerPointAnimatorX1, leftPointAnimatorX1, centerPointAnimatorY2);
        // STEP 4
        ObjectAnimator centerPointAnimatorX3 = ObjectAnimator.ofFloat(mCenterCircle, View.TRANSLATION_X, 0, mRightPoint.x - mCenterPoint.x);
        final ObjectAnimator rightPointAnimatorX2 = ObjectAnimator.ofFloat(mRightCircle, View.TRANSLATION_X, 0, mCenterPoint.x - mRightPoint.x);
        rightPointAnimatorX2.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                rightPointAnimatorX2.setCurrentPlayTime(0);
            }
        });

        ObjectAnimator centerPointAnimatorY3 = ObjectAnimator.ofFloat(mCenterCircle, View.TRANSLATION_Y, 0, mCenterPoint.x - mLeftPoint.x, 0);
        final AnimatorSet step4 = new AnimatorSet();
        step4.playTogether(centerPointAnimatorX3, rightPointAnimatorX2, centerPointAnimatorY3);

        // FINAL ANIMATOR SET
        mCircleAnimator = new AnimatorSet();
        mCircleAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mCircleAnimator.start();
            }
        });
        mCircleAnimator.playSequentially(step1, step2, step3, step4);

        for (Animator animator : mCircleAnimator.getChildAnimations()) {
            animator.setDuration(duration);
            animator.setInterpolator(interpolator);
            animator.setStartDelay(-200);
        }
    }

    private void setUpBackgroundSize(int w, int h, ImageView circle) {
        LayoutParams params = (LayoutParams) circle.getLayoutParams();
        params.addRule(CENTER_IN_PARENT, TRUE);
        int minSize = Math.min(w, h);
        params.width = minSize;
        params.height = minSize;
        circle.setLayoutParams(params);
    }

    private void setUpCircleSize(int size, ImageView circle) {
        LayoutParams params = (LayoutParams) circle.getLayoutParams();
        params.width = size;
        params.height = size;
        circle.setLayoutParams(params);
    }


    private void setUpCircleColors(ImageView circle, int color) {
        GradientDrawable gradientDrawable = (GradientDrawable) circle.getDrawable();
        gradientDrawable.setColor(color);
    }

    //*********************************************************
    // Users Methods
    //*********************************************************

    public void setBackgroudColor(int color) {
        setUpCircleColors(mBackgroundCircle, color);
    }

    public void setPointsColor(int color) {
        setUpCircleColors(mLeftCircle, color);
        setUpCircleColors(mRightCircle, color);
        setUpCircleColors(mCenterCircle, color);
    }

    //*********************************************************
    // Lifecycle
    //*********************************************************

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mCircleAnimator != null) {
            mCircleAnimator.end();
            mCircleAnimator.removeAllListeners();
            mCircleAnimator = null;
        }
    }
}
