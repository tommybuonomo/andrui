package com.tbuonomo.fastcircleloading;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.util.AttributeSet;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * Created by tommy on 02/07/16.
 */
public class FastCircleLoading extends RelativeLayout {
    private enum InterpolatorValues {
        FAST_IN_FAST_OUT (new FastOutSlowInInterpolator()),
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
    private static final int DEFAULT_DURATION = 1000;
    private static final int DEFAULT_POINT_COLOR = Color.WHITE;
    private static final int DEFAULT_BACKGROUND_COLOR = Color.parseColor("#01579B");

    private ImageView mLeftCircle, mCenterCircle, mRightCircle, mBackgroundCircle;
    private AnimatorSet mCircleAnimator;

    public FastCircleLoading(Context context) {
        super(context);
        init(context, null);
    }

    public FastCircleLoading(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public FastCircleLoading(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
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
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.FastCircleLoading);
            int pointColor = a.getColor(R.styleable.FastCircleLoading_pointColor, -1);
            setUpCircleColors(mLeftCircle, pointColor == -1 ?
                    DEFAULT_POINT_COLOR : pointColor);
            setUpCircleColors(mCenterCircle, pointColor == -1 ?
                    DEFAULT_POINT_COLOR : pointColor);
            setUpCircleColors(mRightCircle, pointColor == -1 ?
                    DEFAULT_POINT_COLOR : pointColor);

            int backgroundColor = a.getColor(R.styleable.FastCircleLoading_backgroundColor, -1);
            setUpCircleColors(mBackgroundCircle, backgroundColor == -1 ? DEFAULT_BACKGROUND_COLOR : backgroundColor);

            int duration = a.getInt(R.styleable.FastCircleLoading_animationDuration, -1);
            int interpolator = a.getInt(R.styleable.FastCircleLoading_interpolator, -1);

            setUpAnimators(duration == -1 || duration < 0 ? DEFAULT_DURATION : duration,
                    interpolator == -1 ? InterpolatorValues.FAST_IN_FAST_OUT.getInterpolator() : InterpolatorValues.values()[interpolator].getInterpolator());

            int pointSize = a.getDimensionPixelSize(R.styleable.FastCircleLoading_pointSize, -1);
            if (pointSize != -1) {
                setUpCircleSize(pointSize, mLeftCircle);
                setUpCircleSize(pointSize, mRightCircle);
                setUpCircleSize(pointSize, mCenterCircle);
            }
            a.recycle();
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        setUpBackgroundSize(w, h, mBackgroundCircle);
        setUpCirclePositions(w, h);

        mCircleAnimator.start();
    }

    private void setUpCirclePositions(int w, int h) {
        int margin = w / 5;

        LayoutParams params1 = (LayoutParams) mCenterCircle.getLayoutParams();
        params1.addRule(CENTER_IN_PARENT, TRUE);

        LayoutParams params2 = (LayoutParams) mLeftCircle.getLayoutParams();
        params2.addRule(CENTER_VERTICAL, TRUE);
        params2.setMargins(margin, 0, 0, 0);

        LayoutParams params3 = (LayoutParams) mRightCircle.getLayoutParams();
        params3.addRule(CENTER_VERTICAL, TRUE);
        params3.addRule(ALIGN_PARENT_RIGHT, TRUE);
        params3.setMargins(0, 0, margin, 0);
    }

    private void setUpAnimators(int duration, Interpolator pointInterpolator) {
        final ObjectAnimator animator = ObjectAnimator.ofFloat(this, ROTATION, 0, 720);
        animator.setDuration(duration);
        animator.setInterpolator(pointInterpolator);
        animator.setStartDelay(500);

        Interpolator interpolator = new Interpolator() {
            float margin = 0.3f;

            @Override
            public float getInterpolation(float input) {
                float interpolation;
                if (input < margin) {
                    interpolation = input * 0.5f / margin;
                } else if (input > 1 - margin) {
                    interpolation = 1f - (1f - input) * 0.5f / margin;
                } else {
                    interpolation = 0.5f;
                }
                return interpolation;
            }
        };

        final ObjectAnimator animatorX = ObjectAnimator.ofFloat(mCenterCircle, SCALE_X, 1f, 0f, 1f);
        animatorX.setDuration(duration);
        animatorX.setInterpolator(interpolator);

        final ObjectAnimator animatorY = ObjectAnimator.ofFloat(mCenterCircle, SCALE_Y, 1f, 0f, 1f);
        animatorY.setDuration(duration);
        animatorY.setInterpolator(interpolator);
        mCircleAnimator = new AnimatorSet();
        mCircleAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mCircleAnimator.setStartDelay(500);
                mCircleAnimator.start();
            }
        });
        mCircleAnimator.playTogether(animator, animatorX, animatorY);
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
}
