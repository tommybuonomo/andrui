package com.tbuonomo.circleloading;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * Created by tommy on 02/07/16.
 */
public class CircleLoading extends RelativeLayout {
    private static final int DEFAULT_DURATION = 500;
    private static final int DEFAULT_INNER_APLHA = 255;
    private static final int DEFAULT_OUTER_APLHA = 120;

    private ImageView mOuterCircle, mInnerCircle;
    private AnimatorSet mCircleAnimator;

    public CircleLoading(Context context) {
        super(context);
        init(context, null);
    }

    public CircleLoading(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CircleLoading(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mOuterCircle = new ImageView(context);
        mOuterCircle.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mOuterCircle.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.outer_circle));

        mInnerCircle = new ImageView(context);
        mInnerCircle.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mInnerCircle.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.inner_circle));

        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.CircleLoading);
            int outerColor = a.getColor(R.styleable.CircleLoading_outerColor, -1);
            int outerAlpha = a.getInt(R.styleable.CircleLoading_outerAlpha, -1);
            if (outerColor != -1) {
                setUpCircleColors(mOuterCircle,
                        outerColor,
                        outerAlpha == -1 || outerAlpha < 0 || outerAlpha > 255 ?
                                DEFAULT_OUTER_APLHA : outerAlpha);
            }

            int innerColor = a.getColor(R.styleable.CircleLoading_innerColor, -1);
            int innerAlpha = a.getInt(R.styleable.CircleLoading_innerAlpha, -1);
            if (innerColor != -1) {
                setUpCircleColors(mInnerCircle, innerColor,
                        innerAlpha == -1 || innerAlpha < 0 || innerAlpha > 255 ?
                                DEFAULT_INNER_APLHA : innerAlpha);
            }

            int duration = a.getInt(R.styleable.CircleLoading_animationDuration, -1);
            setUpAnimators(duration == -1 || duration < 0 ? DEFAULT_DURATION : duration);
        }

        addView(mOuterCircle);
        addView(mInnerCircle);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        setUpCircleSizes(w, h, mOuterCircle);
        setUpCircleSizes(w, h, mInnerCircle);

        mCircleAnimator.start();
    }

    private void setUpAnimators(int duration) {
        AnimatorSet outerAnimatorSet = createCircleAnimation(mOuterCircle, true);
        AnimatorSet innerAnimatorSet = createCircleAnimation(mInnerCircle, false);
        outerAnimatorSet.setDuration(duration);
        innerAnimatorSet.setDuration(duration);

        mCircleAnimator = new AnimatorSet();
        mCircleAnimator.playTogether(outerAnimatorSet, innerAnimatorSet);

        mCircleAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mCircleAnimator.start();
            }
        });
    }

    private AnimatorSet createCircleAnimation(ImageView circle, boolean outer) {
        Interpolator interpolator = new AccelerateDecelerateInterpolator();

        ObjectAnimator animatorX1 = ObjectAnimator.ofFloat(circle, View.SCALE_X, outer ? 1f : 0.7f, outer ? 0.7f : 1f);
        ObjectAnimator animatorY1 = ObjectAnimator.ofFloat(circle, View.SCALE_Y, outer ? 1f : 0.7f, outer ? 0.7f : 1f);

        animatorX1.setInterpolator(interpolator);
        animatorY1.setInterpolator(interpolator);

        ObjectAnimator animatorX2 = ObjectAnimator.ofFloat(circle, View.SCALE_X, outer ? 0.7f : 1f, outer ? 1f : 0.7f);
        ObjectAnimator animatorY2 = ObjectAnimator.ofFloat(circle, View.SCALE_Y, outer ? 0.7f : 1f, outer ? 1f : 0.7f);

        animatorX2.setInterpolator(interpolator);
        animatorY2.setInterpolator(interpolator);

        AnimatorSet animatorSet1 = new AnimatorSet();
        animatorSet1.playTogether(animatorX1, animatorY1);

        AnimatorSet animatorSet2 = new AnimatorSet();
        animatorSet2.playTogether(animatorX2, animatorY2);

        final AnimatorSet finalAnimatorSet = new AnimatorSet();
        finalAnimatorSet.playSequentially(animatorSet1, animatorSet2);

        return finalAnimatorSet;
    }

    private void setUpCircleSizes(int w, int h, ImageView circle) {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) circle.getLayoutParams();
        params.addRule(CENTER_IN_PARENT, TRUE);
        int minSize = Math.min(w, h);
        params.width = minSize;
        params.height = minSize;
        circle.setLayoutParams(params);
    }

    private void setUpCircleColors(ImageView circle, int color, int alpha) {
        GradientDrawable gradientDrawable = (GradientDrawable) circle.getDrawable();
        gradientDrawable.setColor(color);
        gradientDrawable.setAlpha(alpha);
    }
}
