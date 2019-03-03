package com.lcw.library.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * 实现Material Design水波纹效果帮助类
 * Create by: chenWei.li
 * Date: 2019/2/24
 * Time: 1:23 AM
 * Email: lichenwei.me@foxmail.com
 */
public class RevealAnimationHelper {

    /**
     * 水波纹显示View
     *
     * @param view
     * @param duration
     */
    public static void showView(final View view, long duration) {
        show(view, duration, null);
    }

    /**
     * 水波纹显示View，带动画监听
     *
     * @param view
     * @param duration
     */
    public static void showView(final View view, long duration, AnimationListener animationListener) {
        show(view, duration, animationListener);
    }

    /**
     * 执行水波纹显示View的具体实现代码
     *
     * @param view
     * @param duration
     */
    public static void show(final View view, long duration, final AnimationListener animationListener) {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.LOLLIPOP) {
            view.setVisibility(View.VISIBLE);
        } else {
            Point point = getViewCenterPoint(view);
            float radius = (float) Math.hypot(view.getWidth(), view.getHeight());
            Animator animator = ViewAnimationUtils.createCircularReveal(view, point.x, point.y, 0, radius);
            animator.setDuration(duration);
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(Animator animation) {
                    view.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    if (animationListener != null) {
                        animationListener.onAnimationEnd();
                    }
                }
            });
            animator.start();
        }
    }


    /**
     * 水波纹隐藏View
     *
     * @param view
     * @param duration
     */
    public static void hideView(View view, long duration) {
        hide(view, duration, null);
    }

    /**
     * 水波纹隐藏View，带动画监听
     *
     * @param view
     * @param duration
     * @param animationListener
     */
    public static void hideView(View view, long duration, AnimationListener animationListener) {
        hide(view, duration, animationListener);
    }

    /**
     * 执行水波纹隐藏View的具体实现代码
     *
     * @param view
     * @param duration
     * @param animationListener
     */
    private static void hide(final View view, long duration, final AnimationListener animationListener) {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.LOLLIPOP) {
            view.setVisibility(View.INVISIBLE);
        } else {

            Point point = getViewCenterPoint(view);
            float radius = (float) Math.hypot(view.getWidth(), view.getHeight());
            Animator animator = ViewAnimationUtils.createCircularReveal(view, point.x, point.y, radius, 0);
            animator.setDuration(duration);
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(Animator animation) {
                    view.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    view.setVisibility(View.INVISIBLE);
                    if (animationListener != null) {
                        animationListener.onAnimationEnd();
                    }
                }
            });
            animator.start();
        }
    }


    /**
     * 水波纹切换Activity
     *
     * @param activity
     * @param intent
     * @param view
     * @param duration
     */
    public static void startActivity(Activity activity, Intent intent, View view, int color, long duration) {
        start(activity, intent, 0, view, color, duration);
    }

    /**
     * 水波纹切换Activity
     *
     * @param activity
     * @param intent
     * @param requestCode
     * @param view
     * @param duration
     */
    public static void startActivityForResult(Activity activity, Intent intent, int requestCode, View view, int color, long duration) {
        start(activity, intent, requestCode, view, color, duration);
    }

    /**
     * 水波纹切换Activity具体实现代码
     *
     * @param activity
     * @param intent
     * @param requestCode
     * @param view
     * @param duration
     */
    private static void start(final Activity activity, final Intent intent, final int requestCode, final View view, int color, long duration) {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.LOLLIPOP) {
            activity.startActivity(intent);
        } else {
            int[] location = new int[2];
            view.getLocationInWindow(location);
            Point point = new Point(location[0] + view.getWidth() / 2, location[1] + view.getHeight() / 2);

            final ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
            int dWidth = decorView.getWidth();
            int dHeight = decorView.getHeight();
            final ImageView imageView = new ImageView(activity);
            imageView.setBackgroundColor(color);
            decorView.addView(imageView, dWidth, dHeight);

            float radius = (float) Math.hypot(Math.max(point.x, dWidth - point.x), Math.max(point.y, dHeight - point.y));
            Animator animator = ViewAnimationUtils.createCircularReveal(imageView, point.x, point.y, 0, radius);
            animator.setDuration(duration);
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(Animator animation) {
                    view.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    if (requestCode != 0) {
                        activity.startActivityForResult(intent, requestCode);
                    } else {
                        activity.startActivity(intent);
                    }
                    activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    view.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            decorView.removeView(imageView);
                        }
                    }, 500);
                }
            });
            animator.start();

        }
    }


    /**
     * 获取view的中心点
     *
     * @param view
     * @return
     */
    private static Point getViewCenterPoint(View view) {
        int[] location = new int[2];
        view.getLocationInWindow(location);
        Point point = new Point((int) view.getX() + view.getWidth() / 2, (int) view.getY() + view.getHeight() / 2);
        return point;
    }

    /**
     * 动画结束回调
     */
    public interface AnimationListener {
        void onAnimationEnd();
    }
}
