package com.airland.marqueeview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author AirLand
 * @time on 2019-11-05 23:31
 * @email lish_air@163.com
 * @jianshu https://www.jianshu.com/u/816932948905
 * @gitHub https://github.com/LiShiHui24740
 * @describe: 水平滚动跑马灯
 */
public class HorizontalMarqueeView extends FrameLayout {

    private AbstractMarqueeAdapter abstractMarqueeAdapter;
    private int mWidth;
    private int mItemWidth;
    private int mShowWidth;
    private int mItemCount;
    private int mLimitIndex;
    private LinkedList<View> viewCache;
    private int speed = 2;
    private boolean ltr;

    public HorizontalMarqueeView(Context context) {
        this(context, null);
    }

    public HorizontalMarqueeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HorizontalMarqueeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.HorizontalMarqueeView);
        speed = a.getInteger(R.styleable.HorizontalMarqueeView_speed, 2);
        ltr = a.getBoolean(R.styleable.HorizontalMarqueeView_ltr, false);
        a.recycle();
        viewCache = new LinkedList<>();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
    }

    public void setAdapter(final AbstractMarqueeAdapter adapter) {
        this.abstractMarqueeAdapter = adapter;
        viewCache.clear();
        mShowWidth = 0;
        getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                getViewTreeObserver().removeOnPreDrawListener(this);
                mItemCount = abstractMarqueeAdapter.getItemCount();
                for (int i = 0; i < mItemCount; i++) {
                    View view = adapter.onCreateView(HorizontalMarqueeView.this, i);
                    ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                    mItemWidth = layoutParams.width;
                    int height = layoutParams.height;
                    int translationX;
                    if (ltr) {
                        translationX = mWidth - (i + 1) * mItemWidth;
                    } else {
                        translationX = i * mItemWidth;
                    }
                    view.setTranslationX(translationX);
                    addView(view, new LayoutParams(mItemWidth, height));
                    abstractMarqueeAdapter.onBindViewHolder(HorizontalMarqueeView.this, view, i);
                    mShowWidth += mItemWidth;
                    viewCache.add(view);
                    if (mShowWidth >= mWidth + mItemWidth) {
                        mLimitIndex = i;
                        break;
                    }
                }
                if (mLimitIndex > 0)
                    startScrollAnimation();
                return false;
            }
        });
    }

    private ScrollRunnable scrollRunnable;
    private Timer timer;

    private void startScrollAnimation() {
        if (timer != null)
            timer.cancel();
        if (scrollRunnable == null)
            scrollRunnable = new ScrollRunnable();
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                post(scrollRunnable);
            }
        }, 0, 16);
    }


    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setLtr(boolean ltr) {
        this.ltr = ltr;
    }

    public AbstractMarqueeAdapter getAdapter() {
        return abstractMarqueeAdapter;
    }

    public void pause() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    public void resume() {
        startScrollAnimation();
    }

    public void destory() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        scrollRunnable = null;
        viewCache.clear();
    }


    private class ScrollRunnable implements Runnable {
        private int currentScrollTo;
        private int nextScrollIndex = 1;

        @Override
        public void run() {
            currentScrollTo += speed;
            if (ltr) {
                scrollTo(-currentScrollTo, 0);
            } else {
                scrollTo(currentScrollTo, 0);
            }
            if (currentScrollTo >= mItemWidth * nextScrollIndex) {
                appendView(nextScrollIndex);
                nextScrollIndex++;
            }
        }

        private void appendView(int nextScrollIndex) {
            View view = viewCache.removeFirst();
            if (ltr) {
                view.setTranslationX(-(nextScrollIndex * mItemWidth) + (mWidth - mShowWidth));
            } else {
                view.setTranslationX((nextScrollIndex - 1) * mItemWidth + mShowWidth);
            }
            viewCache.add(view);
            mLimitIndex++;
            if (mLimitIndex == mItemCount)
                mLimitIndex = 0;
            abstractMarqueeAdapter.onBindViewHolder(HorizontalMarqueeView.this, view, mLimitIndex);

        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        destory();
    }
}
