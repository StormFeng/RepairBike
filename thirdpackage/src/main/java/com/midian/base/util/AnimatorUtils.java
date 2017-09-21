package com.midian.base.util;

import android.view.View;
import android.view.animation.CycleInterpolator;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.animation.ValueAnimator.AnimatorUpdateListener;


public class AnimatorUtils {

	/** 震荡View左右 */
	public static void onVibrationView(View v) {
		ObjectAnimator mObjectAnimator = ObjectAnimator.ofFloat(v, "TranslationX", 0.0F, 15.0F);
		mObjectAnimator.setInterpolator(new CycleInterpolator(5f));
		mObjectAnimator.setDuration(300);
		mObjectAnimator.start();
	}

	/** 旋转View180度 */
	public static void onRotationView(View v, float rotation) {
		ObjectAnimator mObjectAnimator = ObjectAnimator.ofFloat(v, "Rotation",
				v.getRotation() == 0.0F ? 0.0F : v.getRotation(), v.getRotation() == rotation ? 0.0F : rotation);
		mObjectAnimator.setDuration(300);
		mObjectAnimator.start();
	}

	/** 移动ViewY是移动的距离 */
	public static ObjectAnimator onTranslationYView(View v, float translationY) {
		ObjectAnimator anim = ObjectAnimator.ofFloat(v, "TranslationY",
				v.getTranslationY() == 0.0F ? 0.0F : v.getTranslationY(),
				v.getTranslationY() == translationY ? 0.0F : translationY);
		anim.setDuration(300);
		anim.start();
		return anim;
	}

	/** 缩放ViewX到Y比例 */
	public static ObjectAnimator onScaleXtoscaleYView(final View v, float scaleX, float scaleY) {
		ObjectAnimator anim = ObjectAnimator.ofFloat(v, "zhy", scaleX, scaleY);
		anim.setInterpolator(new CycleInterpolator(1f));
		anim.setDuration(400);
		anim.start();
		anim.addUpdateListener(new AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				float cVal = (Float) animation.getAnimatedValue();
				v.setScaleX(cVal);
				v.setScaleY(cVal);
			}
		});
		return anim;
	}

}
