package com.midian.base.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;


import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ObjectAnimator;

import com.midian.base.adapter.AnimatorAdapter;

public class PullToZoomListView extends ListView implements AbsListView.OnScrollListener {
    public static final int ANIM_TYPE_TRANSLATE = 0;
    public static final int ANIM_TYPE_ALPHA = 1;

    private static final int INVALID_VALUE = -1;
    private static final String TAG = "PullToZoomListView";
    private static final Interpolator sInterpolator = new Interpolator() {
        public float getInterpolation(float paramAnonymousFloat) {
            float f = paramAnonymousFloat - 1.0F;
            return 1.0F + f * (f * (f * (f * f)));
        }
    };
    private static final long TIME_ANIMATION = 200;
    int mActivePointerId = -1;
    private FrameLayout mHeaderContainer;
    private int mHeaderHeight;
    private ImageView mHeaderImage;
    float mLastMotionY = -1.0F;
    float mLastScale = -1.0F;
    float mMaxScale = -1.0F;
    private OnScrollListener mOnScrollListener;
    private ScalingRunnalable mScalingRunnalable;
    private int mScreenHeight;

    private boolean mScrollable = true;
    private boolean mShowHeaderImage = true;
    private boolean mZoomable = true;

    private float viewSlop;
    private float lastY;
    private boolean isUpSlide;
    private View toolBar;
    private boolean isToolHide;

    private CallBack callBack;

    private int animType = ANIM_TYPE_TRANSLATE;

    public interface CallBack {
        void handZoom(float mScale);
    }

    public interface onScrollDirectionChangedListener {
        void onScrollDirectionChanged(boolean isScrollToUp);
    }

    public PullToZoomListView(Context paramContext) {
        super(paramContext);
        init(paramContext);
    }

    public PullToZoomListView(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
        init(paramContext);
    }

    public PullToZoomListView(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
        super(paramContext, paramAttributeSet, paramInt);
        init(paramContext);
    }

    private void endScraling() {
        if (this.mHeaderContainer.getBottom() >= this.mHeaderHeight)
            this.mScalingRunnalable.startAnimation(200L);
    }

    private void init(Context paramContext) {
        viewSlop = ViewConfiguration.get(paramContext).getScaledTouchSlop();
        DisplayMetrics localDisplayMetrics = new DisplayMetrics();
        ((Activity) paramContext).getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
        this.mScreenHeight = localDisplayMetrics.heightPixels;
        this.mHeaderContainer = new FrameLayout(paramContext);
        this.mHeaderImage = new ImageView(paramContext);
        this.mHeaderImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
        int i = localDisplayMetrics.widthPixels;
        setHeaderViewSize(i, (int) (10.0F * (i / 16.0F)));
        FrameLayout.LayoutParams localLayoutParams = new FrameLayout.LayoutParams(-1, -2);
        localLayoutParams.gravity = 80;
        this.mHeaderContainer.addView(this.mHeaderImage);
        addHeaderView(this.mHeaderContainer);
        this.mScalingRunnalable = new ScalingRunnalable();
        super.setOnScrollListener(this);
    }

    private void onSecondaryPointerUp(MotionEvent paramMotionEvent) {
        int i = (paramMotionEvent.getAction()) >> 8;
        if (paramMotionEvent.getPointerId(i) == this.mActivePointerId)
            if (i != 0) {
                this.mLastMotionY = paramMotionEvent.getY(1);
                this.mActivePointerId = paramMotionEvent.getPointerId(0);
                return;
            }
    }

    private void reset() {
        this.mActivePointerId = -1;
        this.mLastMotionY = -1.0F;
        this.mMaxScale = -1.0F;
        this.mLastScale = -1.0F;
    }

    public ImageView getHeaderView() {
        return this.mHeaderImage;
    }

    public void hideHeaderImage() {
        this.mShowHeaderImage = false;
        this.mZoomable = false;
        this.mScrollable = false;
        removeHeaderView(this.mHeaderContainer);
    }

    public boolean isScrollable() {
        return this.mScrollable;
    }

    public boolean isZoomable() {
        return this.mZoomable;
    }

    public boolean onInterceptTouchEvent(MotionEvent paramMotionEvent) {
        if (!this.mZoomable) {
            return super.onInterceptTouchEvent(paramMotionEvent);
        }
        switch (paramMotionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:

                this.mActivePointerId = paramMotionEvent.getPointerId(0);
                this.mMaxScale = (this.mScreenHeight / this.mHeaderHeight);
                break;

            case MotionEvent.ACTION_UP:
                reset();
                break;

            case MotionEvent.ACTION_POINTER_DOWN:
                this.mActivePointerId = paramMotionEvent.getPointerId(paramMotionEvent.getActionIndex());
                break;

            case MotionEvent.ACTION_POINTER_UP:
                onSecondaryPointerUp(paramMotionEvent);
                break;
        }
        return super.onInterceptTouchEvent(paramMotionEvent);
    }

    protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
        super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
        if (this.mHeaderHeight == 0)
            this.mHeaderHeight = this.mHeaderContainer.getHeight();
    }

    @Override
    public void onScroll(AbsListView paramAbsListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (this.mScrollable) {
            float f = this.mHeaderHeight - this.mHeaderContainer.getBottom();
            if ((f > 0.0F) && (f < this.mHeaderHeight)) {
                int i = (int) (0.65D * f);
                this.mHeaderImage.scrollTo(0, -i);
            } else if (this.mHeaderImage.getScrollY() != 0) {
                this.mHeaderImage.scrollTo(0, 0);
            }
        }
        if (onScrollCallback != null) {
            onScrollCallback.onScrollCallback(mHeaderContainer.getBottom());
        }


        if (this.mOnScrollListener != null) {
            this.mOnScrollListener.onScroll(paramAbsListView, firstVisibleItem, visibleItemCount, totalItemCount);
        }
    }

    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (this.mOnScrollListener != null) {
            this.mOnScrollListener.onScrollStateChanged(view, scrollState);
        }
    }

    public void setToolBar(View toolBar) {
        this.toolBar = toolBar;
    }

    public boolean onTouchEvent(MotionEvent ev) {
        if (!this.mZoomable) {
            return super.onTouchEvent(ev);
        }
        switch (ev.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_OUTSIDE:
            case MotionEvent.ACTION_DOWN:
                if (!this.mScalingRunnalable.mIsFinished) {
                    this.mScalingRunnalable.abortAnimation();
                }
                this.mLastMotionY = ev.getY();
                this.mActivePointerId = ev.getPointerId(0);
                this.mMaxScale = (this.mScreenHeight / this.mHeaderHeight);
                this.mLastScale = (this.mHeaderContainer.getBottom() / this.mHeaderHeight);
                break;
            case MotionEvent.ACTION_MOVE:
                int j = ev.findPointerIndex(this.mActivePointerId);
                if (j == -1) {
                } else {
                    if (this.mLastMotionY == -1.0F)
                        this.mLastMotionY = ev.getY(j);
                    if (this.mHeaderContainer.getBottom() >= this.mHeaderHeight) {
                        ViewGroup.LayoutParams localLayoutParams = this.mHeaderContainer.getLayoutParams();
                        float f = ((ev.getY(j) - this.mLastMotionY + this.mHeaderContainer.getBottom()) / this.mHeaderHeight - this.mLastScale) / 2.0F + this.mLastScale;
                        if ((this.mLastScale <= 1.0D) && (f < this.mLastScale)) {
                            localLayoutParams.height = this.mHeaderHeight;
                            this.mHeaderContainer.setLayoutParams(localLayoutParams);
                        }
                        this.mLastScale = Math.min(Math.max(f, 1.0F), this.mMaxScale);
                        localLayoutParams.height = ((int) (this.mHeaderHeight * this.mLastScale));
                        if (localLayoutParams.height < this.mScreenHeight)
                            this.mHeaderContainer.setLayoutParams(localLayoutParams);
                        this.mLastMotionY = ev.getY(j);
                    }
                    this.mLastMotionY = ev.getY(j);
                }
                break;
            case MotionEvent.ACTION_UP:
                reset();
                endScraling();
                break;
            case MotionEvent.ACTION_CANCEL:

                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                int i = ev.getActionIndex();
                this.mLastMotionY = ev.getY(i);
                this.mActivePointerId = ev.getPointerId(i);
                break;
            case MotionEvent.ACTION_POINTER_UP:
                onSecondaryPointerUp(ev);
                this.mLastMotionY = ev.getY(ev.findPointerIndex(this.mActivePointerId));
                break;
        }

        // ***************

        switch (ev.getAction()) {

            case MotionEvent.ACTION_DOWN:
                lastY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:

                float disY = ev.getY() - lastY;
                // 垂直方向滑动
                if (Math.abs(disY) > viewSlop) {
                    // 设置了TextView的点击事件之后，会导致这里的disY的数值出现跳号现象，最终导致的效果就是
                    // 下面的tool布局在手指往下滑动的时候，先显示一个，然后再隐藏，这是完全没必要的
                    // 是否向上滑动
                    isUpSlide = disY < 0;
                    // 实现底部tools的显示与隐藏
                    if (isUpSlide) {
                        if (!isToolHide)
                            hideTool();
                    } else {
                        if (isToolHide)
                            showTool();
                    }
                }

                lastY = ev.getY();
                break;
        }

        return super.onTouchEvent(ev);
    }

    public void setHeaderViewSize(int paramInt1, int paramInt2) {
        if (!this.mShowHeaderImage) {
            return;
        }
        Object localObject = this.mHeaderContainer.getLayoutParams();
        if (localObject == null)
            localObject = new LayoutParams(paramInt1, paramInt2);
        ((ViewGroup.LayoutParams) localObject).width = paramInt1;
        ((ViewGroup.LayoutParams) localObject).height = paramInt2;
        this.mHeaderContainer.setLayoutParams((ViewGroup.LayoutParams) localObject);
        this.mHeaderHeight = paramInt2;
    }

    public void setOnScrollListener(OnScrollListener paramOnScrollListener) {
        this.mOnScrollListener = paramOnScrollListener;
    }

    public void setScrollable(boolean paramBoolean) {
        if (!this.mShowHeaderImage) {
            return;
        }
        this.mScrollable = paramBoolean;
    }

    public void setZoomable(boolean paramBoolean) {
        if (!this.mShowHeaderImage) {
            return;
        }
        this.mZoomable = paramBoolean;
    }

    class ScalingRunnalable implements Runnable {
        long mDuration;
        boolean mIsFinished = true;
        float mScale;
        long mStartTime;

        ScalingRunnalable() {
        }

        public void abortAnimation() {
            this.mIsFinished = true;
        }

        public boolean isFinished() {
            return this.mIsFinished;
        }

        public void run() {
            float f2;
            ViewGroup.LayoutParams localLayoutParams;
            if ((!this.mIsFinished) && (this.mScale > 1.0D)) {
                float f1 = ((float) SystemClock.currentThreadTimeMillis() - (float) this.mStartTime) / (float) this.mDuration;
                f2 = this.mScale - (this.mScale - 1.0F) * PullToZoomListView.sInterpolator.getInterpolation(f1);
                localLayoutParams = PullToZoomListView.this.mHeaderContainer.getLayoutParams();
                if (f2 > 1.0F) {
                    localLayoutParams.height = PullToZoomListView.this.mHeaderHeight;
                    localLayoutParams.height = ((int) (f2 * PullToZoomListView.this.mHeaderHeight));
                    PullToZoomListView.this.mHeaderContainer.setLayoutParams(localLayoutParams);
                    PullToZoomListView.this.post(this);
                    return;
                }
                this.mIsFinished = true;
                if (callBack != null) {
                    callBack.handZoom(mScale);
                }
            }
        }

        public void startAnimation(long paramLong) {
            this.mStartTime = SystemClock.currentThreadTimeMillis();
            this.mDuration = paramLong;
            this.mScale = ((float) (PullToZoomListView.this.mHeaderContainer.getBottom()) / PullToZoomListView.this.mHeaderHeight);
            this.mIsFinished = false;
            PullToZoomListView.this.post(this);
        }
    }

    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }

    public CallBack getCallBack() {
        return callBack;
    }

    /**
     * 显示工具栏
     */
    private void showTool() {
        if (toolBar == null) {
            return;
        }
        switch (animType) {
            case ANIM_TYPE_TRANSLATE:
                int startY = ((Activity) getContext()).getWindow().getDecorView().getHeight() - getStatusHeight((Activity) getContext());
                ObjectAnimator anim = ObjectAnimator.ofFloat(toolBar, "y", startY, startY - toolBar.getHeight());
                anim.setDuration(TIME_ANIMATION);
                anim.start();
                break;
            case ANIM_TYPE_ALPHA:
                ObjectAnimator anim2 = ObjectAnimator.ofFloat(toolBar, "alpha", 0f, 1f);
                anim2.setDuration(TIME_ANIMATION);
                anim2.addListener(new AnimatorAdapter() {
                    @Override
                    public void onAnimationStart(Animator arg0) {
                        super.onAnimationStart(arg0);
                        toolBar.setVisibility(VISIBLE);
                    }
                });
                anim2.start();
                break;
        }
        isToolHide = false;

    }



    /**
     * 隐藏工具栏
     */
    private void hideTool() {
        if (toolBar == null) {
            return;
        }
        switch (animType) {
            case ANIM_TYPE_TRANSLATE:
                int startY = ((Activity) getContext()).getWindow().getDecorView().getHeight() - getStatusHeight((Activity) getContext());
                ObjectAnimator anim = ObjectAnimator.ofFloat(toolBar, "y", startY - toolBar.getHeight(), startY);
                anim.setDuration(TIME_ANIMATION);
                anim.start();
                break;
            case ANIM_TYPE_ALPHA:
                ObjectAnimator anim2 = ObjectAnimator.ofFloat(toolBar, "alpha", 1.0f, 0f);
                anim2.setDuration(TIME_ANIMATION);
                anim2.addListener(new AnimatorAdapter() {
                    @Override
                    public void onAnimationEnd(Animator arg0) {
                        super.onAnimationEnd(arg0);
                        toolBar.setVisibility(GONE);
                    }
                });
                anim2.start();
                break;
        }
        isToolHide = true;

    }

    public static int getStatusHeight(Activity activity) {
        int statusHeight = 0;
        Rect localRect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(localRect);
        statusHeight = localRect.top;
        if (0 == statusHeight) {
            Class<?> localClass;
            try {
                localClass = Class.forName("com.android.internal.R$dimen");
                Object localObject = localClass.newInstance();
                int i5 = Integer.parseInt(localClass.getField("status_bar_height").get(localObject).toString());
                statusHeight = activity.getResources().getDimensionPixelSize(i5);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return statusHeight;
    }

    public FrameLayout getHeaderContainer() {
        return mHeaderContainer;
    }

    public void setAnimType(int animType) {
        this.animType = animType;
    }

    private OnScrollCallback onScrollCallback;

    public OnScrollCallback getOnScrollCallback() {
        return onScrollCallback;
    }

    public void setOnScrollCallback(OnScrollCallback onScrollCallback) {
        this.onScrollCallback = onScrollCallback;
    }

    public interface OnScrollCallback {
        void onScrollCallback(int headerContainerBottom);
    }
}
