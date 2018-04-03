package com.example.com.popupmenu.baseView;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.com.popupmenu.R;
import com.example.com.popupmenu.listener.OnItemClickListener;
import com.example.com.popupmenu.utils.DisplayUtils;

import java.util.List;

/**
 * Created by grace on 2018/3/22.
 */

public class PopupMenu extends LinearLayout implements OnItemClickListener {
    private static final String TAG = "PopupMenu";

    public static final int DEF_BACKGROUND = 0xffffffff;
    public static final float DEF_TEXT_SIZE = 12f;
    public static final int DEF_TEXT_COLOR = 0xff000000;
    public static final int DEF_SHADE_COLOR = 0x88888888;

    private int background;
    private float textSize;
    private int textColor;

    private LinearLayout mHeadLLayout;
    private FrameLayout mBottomFLayout;
    private FrameLayout mContentFLayout;
    private FrameLayout mShadeFLayout;
    private FrameLayout mPopupViewFLayout;

    private OnItemClickListener mOnItemClickListener;

    private int currentPopupItem = -1;
    private static long downTime;

    public PopupMenu(Context context) {
        this(context, null);
    }

    public PopupMenu(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PopupMenu(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.popupMenu);
        background = typedArray.getColor(R.styleable.popupMenu_baseBackground, DEF_BACKGROUND);
        textSize = typedArray.getDimension(R.styleable.popupMenu_textSize, DEF_TEXT_SIZE);
        textColor = typedArray.getColor(R.styleable.popupMenu_textColor, DEF_TEXT_COLOR);
        typedArray.recycle();

        initView();
        initEvent();
    }

    private void initView() {
        setOrientation(VERTICAL);
        mHeadLLayout = new LinearLayout(getContext());
        LayoutParams headLayoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        mHeadLLayout.setLayoutParams(headLayoutParams);
        mHeadLLayout.setOrientation(HORIZONTAL);
        mHeadLLayout.setBackgroundColor(background);
        addView(mHeadLLayout);

        mBottomFLayout = new FrameLayout(getContext());
        FrameLayout.LayoutParams bottomLayoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        mBottomFLayout.setLayoutParams(bottomLayoutParams);
        mBottomFLayout.setBackgroundColor(background);
        addView(mBottomFLayout);

        mContentFLayout = new FrameLayout(getContext());
        FrameLayout.LayoutParams contentLayoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        mContentFLayout.setLayoutParams(contentLayoutParams);
        mBottomFLayout.addView(mContentFLayout);

        mShadeFLayout = new FrameLayout(getContext());
        FrameLayout.LayoutParams shadeLayoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        mShadeFLayout.setLayoutParams(shadeLayoutParams);
        mShadeFLayout.setBackgroundColor(DEF_SHADE_COLOR);
        mShadeFLayout.setVisibility(View.INVISIBLE);
        mBottomFLayout.addView(mShadeFLayout);

        mPopupViewFLayout = new FrameLayout(getContext());
        FrameLayout.LayoutParams popupViewLayoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupViewFLayout.setLayoutParams(popupViewLayoutParams);
        mBottomFLayout.addView(mPopupViewFLayout);
    }

    /**
     * @param titles 弹出菜单的标题
     * @param views 弹出菜单所显示的view
     */
    public void setPopupView(List<String> titles, View[] views) {
        for (int i = 0; i < titles.size(); i++) {
            TextView textView = new TextView(getContext());
            LayoutParams textLayoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            textView.setLayoutParams(textLayoutParams);
            textView.setPadding(0, DisplayUtils.dp2px(getContext(), 15f), 0, DisplayUtils.dp2px(getContext(), 15f));
            textView.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(getContext(), R.drawable.story_xq_icon_jt_down), null);
            textView.setCompoundDrawablePadding(DisplayUtils.dp2px(getContext(), 5f));
            textView.setTextColor(textColor);
            textView.setTextSize(textSize);
            textView.setText(titles.get(i));
            LinearLayout linearLayout = new LinearLayout(getContext());
            LayoutParams linearLayoutParams = new LayoutParams(0, LayoutParams.WRAP_CONTENT);
            linearLayoutParams.weight = 1;
            linearLayout.setLayoutParams(linearLayoutParams);
            linearLayout.setGravity(Gravity.CENTER);
            linearLayout.setTag(i);
            linearLayout.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(v, (int) v.getTag());
                    }
                }
            });
            linearLayout.addView(textView);
            mHeadLLayout.addView(linearLayout);
        }

        for (int i = 0; i < views.length; i++) {
            views[i].setVisibility(View.INVISIBLE);
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            views[i].setLayoutParams(layoutParams);
            mPopupViewFLayout.addView(views[i]);
        }
    }

    /**
     * @param view 操作弹出菜单后，所要显示的内容view
     */
    public void setContentView(View view) {
        if (view != null) {
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            view.setLayoutParams(layoutParams);
            int addIndex = -1;
            for (int i = 0; i < mContentFLayout.getChildCount(); i++) {
                View childView = mContentFLayout.getChildAt(i);
                if (view.getId() == mContentFLayout.getChildAt(i).getId()) {
                    addIndex = i;
                }
                if (childView.getVisibility() == View.VISIBLE) {
                    childView.setVisibility(View.INVISIBLE);
                }
            }
            if (addIndex == -1) {
                mContentFLayout.addView(view);
            } else {
                mContentFLayout.getChildAt(addIndex).setVisibility(View.VISIBLE);
            }
        }
    }

    private void initEvent() {
        mOnItemClickListener = this;
        mShadeFLayout.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (currentPopupItem != -1) {
                    View popupView = mPopupViewFLayout.getChildAt(currentPopupItem);
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            downTime = System.currentTimeMillis();
                            break;
                        case MotionEvent.ACTION_UP:
                            if ((System.currentTimeMillis() - downTime) < 300) {
                                int height = popupView.getHeight();
                                float y = event.getY();
                                if (y > height) {
                                    closeView(true);
                                }
                            }
                            break;
                    }
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {
        popupView(position);
    }

    private void resetPopupView() {
        for (int i = 0; i < mPopupViewFLayout.getChildCount(); i++) {
            final View childView = mPopupViewFLayout.getChildAt(i);
            if (childView.getVisibility() == View.VISIBLE) {
                closeView(true);
            }
        }
    }

    private void popupView(int position) {
        boolean isClose = false;//检测是否重复点击当前popupView
        if (currentPopupItem == position) {
            isClose = true;
        }
        resetPopupView();
        if (!isClose) {
            openView(position);
        }
    }

    private void openView(int position) {
        currentPopupItem = position;
        View childView = mPopupViewFLayout.getChildAt(position);
        downAnim(childView);
        mShadeFLayout.setVisibility(View.VISIBLE);
        TextView titleTextView = (TextView) ((LinearLayout) mHeadLLayout.getChildAt(position)).getChildAt(0);
        titleTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.story_xq_icon_jt_up, 0);
    }

    private void closeView(boolean isAnim) {
        synchronized (PopupMenu.this) {
            if (currentPopupItem != -1) {
                View childView = mPopupViewFLayout.getChildAt(currentPopupItem);
                if (isAnim) {
                    upAnim(childView);
                } else {
                    childView.setVisibility(View.INVISIBLE);
                }
                mShadeFLayout.setVisibility(View.INVISIBLE);
                TextView titleTextView = (TextView) ((LinearLayout) mHeadLLayout.getChildAt(currentPopupItem)).getChildAt(0);
                titleTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.story_xq_icon_jt_down, 0);
                currentPopupItem = -1;
            }
        }
    }

    public void closeMenu() {
        closeView(true);
    }

    private void downAnim(final View view) {
        view.setVisibility(View.VISIBLE);
        final int height = view.getHeight();
        if (height <= 0) {
            return;
        }
        ValueAnimator animator = ValueAnimator.ofInt(0, height);
        animator.setDuration(height/100*25);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                layoutParams.height = value;
                view.setLayoutParams(layoutParams);
            }
        });
        animator.start();
    }

    private void upAnim(final View view) {
        final int height = view.getHeight();
        if (height <= 0) {
            return;
        }
        ValueAnimator animator = ValueAnimator.ofInt(height, 0);
        animator.setDuration(height/100*25);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                layoutParams.height = value;
                view.setLayoutParams(layoutParams);
            }
        });
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                view.setVisibility(View.INVISIBLE);
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                layoutParams.height = height;
                view.setLayoutParams(layoutParams);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.start();
    }
}
