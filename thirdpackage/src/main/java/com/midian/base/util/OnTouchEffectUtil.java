package com.midian.base.util;

import android.graphics.ColorMatrixColorFilter;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public final class OnTouchEffectUtil {
    public static final float[] ETBUTTON_NOT_SELECTED = {1.0F, 0.0F, 0.0F,
            0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F,
            0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F};
    public static final float[] ETBUTTON_SELECTED = {0.8F, 0.0F, 0.0F, 0.0F,
            0.0F, 0.0F, 0.8F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.8F, 0.0F, 0.0F,
            0.0F, 0.0F, 0.0F, 1.0F, 0.0F};
    public static final View.OnFocusChangeListener buttonOnFocusChangeListener = new View.OnFocusChangeListener() {
        public void onFocusChange(View paramAnonymousView,
                                  boolean paramAnonymousBoolean) {

            if (paramAnonymousView
                    .getBackground() == null) {
                return;
            }


            if (paramAnonymousBoolean) {

                paramAnonymousView.getBackground().setColorFilter(
                        new ColorMatrixColorFilter(OnTouchEffectUtil.ETBUTTON_SELECTED));

                paramAnonymousView.setBackgroundDrawable(paramAnonymousView
                        .getBackground());

                return;
            }
            paramAnonymousView.getBackground().setColorFilter(
                    new ColorMatrixColorFilter(OnTouchEffectUtil.ETBUTTON_NOT_SELECTED));

            paramAnonymousView.setBackgroundDrawable(paramAnonymousView
                    .getBackground());
        }
    };
    public static final View.OnTouchListener baseOnTouchListener = new View.OnTouchListener() {
        public boolean onTouch(View paramAnonymousView,
                               MotionEvent paramAnonymousMotionEvent) {

            if (paramAnonymousMotionEvent.getAction() == MotionEvent.ACTION_DOWN) {

                if (paramAnonymousView instanceof TextView) {
                    changeTextViewPressState((TextView) paramAnonymousView, ETBUTTON_SELECTED);
                } else {
                    changeDefaultViewPressState(paramAnonymousView, ETBUTTON_SELECTED);
                }
            }

            if (paramAnonymousMotionEvent.getAction() == MotionEvent.ACTION_CANCEL || paramAnonymousMotionEvent.getAction() == MotionEvent.ACTION_UP) {
                if (paramAnonymousView instanceof TextView) {

                    changeTextViewPressState((TextView) paramAnonymousView, ETBUTTON_NOT_SELECTED);

                } else {
                    changeDefaultViewPressState(paramAnonymousView, ETBUTTON_NOT_SELECTED);
                }
            }


            return false;
        }
    };


    public static void changeTextViewPressState(TextView tv, float[] press) {
        if (tv.getBackground() == null) {
            Drawable[] list = tv.getCompoundDrawables();
            if (list != null) {
                for (Drawable item : list) {
                    if (item != null) {
                        item.setColorFilter(new ColorMatrixColorFilter(press));
                    }
                }
                tv.setCompoundDrawables(list[0], list[1], list[2], list[3]);
            }
        } else {
            tv.getBackground().setColorFilter(new ColorMatrixColorFilter(press));
            tv.setBackgroundDrawable(tv
                    .getBackground());
        }

    }

    public static void changeDefaultViewPressState(View view, float[] press) {
        if (view.getBackground() == null) {

        } else {
            view.getBackground().setColorFilter(new ColorMatrixColorFilter(press));
            view.setBackgroundDrawable(view
                    .getBackground());
        }

    }


    public static final void setViewTouchEffectforBase(View paramView) {
        if (paramView.isClickable() && paramView.getTag() != null&&paramView.getTag() instanceof String) {
//            System.out.println("paramView.getTag():::::::"+paramView.getTag());
            paramView.setOnTouchListener(baseOnTouchListener);
        }
    }

    public static final void setViewTouchEffect(View paramView) {
        if (paramView.isClickable()) {
            paramView.setOnTouchListener(baseOnTouchListener);
        }
    }
}