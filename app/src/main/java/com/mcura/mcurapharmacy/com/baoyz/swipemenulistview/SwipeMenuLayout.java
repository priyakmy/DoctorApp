package com.mcura.mcurapharmacy.com.baoyz.swipemenulistview;

import android.content.Context;
import androidx.core.view.GestureDetectorCompat;
import androidx.core.widget.ScrollerCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.GestureDetector.OnGestureListener;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.AbsListView;
import android.widget.FrameLayout;


/**
 * 
 * @author baoyz
 * @date 2014-8-23
 * 
 */
public class SwipeMenuLayout extends FrameLayout {

    private static final int CONTENT_VIEW_ID = 1;
    private static final int MENU_VIEW_ID = 2;

    private static final int STATE_CLOSE = 0;
    private static final int STATE_OPEN = 1;

    private View mContentView;
    private SwipeMenuView mMenuView;
    private int state = STATE_CLOSE;
    private GestureDetectorCompat mGestureDetector;
    private OnGestureListener mGestureListener;
    private boolean isFling;
    private int MIN_FLING = dp2px(15);
    private int MAX_VELOCITYX = -dp2px(500);
    private ScrollerCompat mOpenScroller;
    private ScrollerCompat mCloseScroller;
    private int mBaseX;
    private int position;
    private Interpolator mCloseInterpolator;
    private Interpolator mOpenInterpolator;
    /** where does the menu item stick with **/
    private int mMenuStickTo = SwipeMenuListView.STICK_TO_SCREEN;
    private int mDownX;
    public SwipeMenuLayout(View contentView, com.mcura.mcurapharmacy.com.baoyz.swipemenulistview.SwipeMenuView menuView) {
        this(contentView, menuView, null, null, SwipeMenuListView.STICK_TO_ITEM_RIGHT_SIDE);
    }

    public SwipeMenuLayout(View contentView, com.mcura.mcurapharmacy.com.baoyz.swipemenulistview.SwipeMenuView menuView, int stickMode) {
        this(contentView, menuView, null, null, stickMode);
    }

    public SwipeMenuLayout(View contentView, com.mcura.mcurapharmacy.com.baoyz.swipemenulistview.SwipeMenuView menuView, Interpolator closeInterpolator,
                           Interpolator openInterpolator) {
        this(contentView, menuView, closeInterpolator, openInterpolator, SwipeMenuListView.STICK_TO_ITEM_RIGHT_SIDE);
    }

    public SwipeMenuLayout(View contentView, com.mcura.mcurapharmacy.com.baoyz.swipemenulistview.SwipeMenuView menuView, Interpolator closeInterpolator,
                           Interpolator openInterpolator, int stickMode) {
        super(contentView.getContext());
        mCloseInterpolator = closeInterpolator;
        mOpenInterpolator = openInterpolator;
        mContentView = contentView;
        mMenuView = menuView;
        mMenuView.setLayout(this);
        mMenuStickTo = stickMode;
        init();
    }

    // private SwipeMenuLayout(Context context, AttributeSet attrs, int
    // defStyle) {
    // super(context, attrs, defStyle);
    // }

    private SwipeMenuLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private SwipeMenuLayout(Context context) {
        super(context);
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
        mMenuView.setPosition(position);
    }

    private void init() {
        setLayoutParams(new AbsListView.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        mGestureListener = new SimpleOnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                isFling = false;
                return true;
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                // TODO
                if (e1 != null && e2 != null && (e1.getX() - e2.getX()) > MIN_FLING && velocityX < MAX_VELOCITYX) {
                    isFling = true;
                }
                // Log.i("byz", MAX_VELOCITYX + ", velocityX = " + velocityX);
                return super.onFling(e1, e2, velocityX, velocityY);
            }
        };
        mGestureDetector = new GestureDetectorCompat(getContext(), mGestureListener);

        if (mCloseInterpolator != null) {
            mCloseScroller = ScrollerCompat.create(getContext(), mCloseInterpolator);
        } else {
            mCloseScroller = ScrollerCompat.create(getContext());
        }
        if (mOpenInterpolator != null) {
            mOpenScroller = ScrollerCompat.create(getContext(), mOpenInterpolator);
        } else {
            mOpenScroller = ScrollerCompat.create(getContext());
        }

        LayoutParams contentParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        mContentView.setLayoutParams(contentParams);
        if (mContentView.getId() < 1) {
            mContentView.setId(CONTENT_VIEW_ID);
        }

        mMenuView.setId(MENU_VIEW_ID);
        mMenuView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

        addView(mMenuView);
        addView(mContentView);

        // if (mContentView.getBackground() == null) {
        // mContentView.setBackgroundColor(Color.WHITE);
        // }

        // in android 2.x, MenuView height is MATCH_PARENT is not work.
        // getViewTreeObserver().addOnGlobalLayoutListener(
        // new OnGlobalLayoutListener() {
        // @Override
        // public void onGlobalLayout() {
        // setMenuHeight(mContentView.getHeight());
        // // getViewTreeObserver()
        // // .removeGlobalOnLayoutListener(this);
        // }
        // });

    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    public boolean onSwipe(MotionEvent event) {
        mGestureDetector.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownX = (int) event.getX();
                isFling = false;
                break;
            case MotionEvent.ACTION_MOVE:
                // Log.i("byz", "downX = " + mDownX + ", moveX = " +
                // event.getX());
                int dis = (int) (mDownX - event.getX());
                if (state == STATE_OPEN) {
                    dis += mMenuView.getWidth();
                }
                swipe(dis);
                break;
            case MotionEvent.ACTION_UP:
                if (isFling || (mDownX - event.getX()) > (mMenuView.getWidth() / 2)) {
                    // open
                    smoothOpenMenu();
                } else {
                    // close
                    smoothCloseMenu();
                    return false;
                }
                break;
        }
        return true;
    }

    public boolean isOpen() {
        return state == STATE_OPEN;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (inRangeOfView(mMenuView, ev) && isOpen())
            return super.dispatchTouchEvent(ev);
        boolean res = mContentView.dispatchTouchEvent(ev);
        Log.i("swipe", "layout dispatch:" + res);
        return res;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean res = super.onInterceptTouchEvent(ev);
        Log.i("swipe", "layout intercept:" + res);
        return res;
    }
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        boolean res = super.onTouchEvent(ev);
        Log.i("swipe", "layout ontouch:" + res);
        return res;
    }

    private boolean inRangeOfView(View view, MotionEvent ev) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int x = location[0];
        int y = location[1];
        if (ev.getRawX() < x || ev.getRawX() > (x + view.getWidth()) || ev.getRawY() < y
                || ev.getRawY() > (y + view.getHeight())) {
            return false;
        }
        return true;
    }

    private void swipe(int dis) {
        if (dis > mMenuView.getWidth()) {
            dis = mMenuView.getWidth();
        }
        if (dis < 0) {
            dis = 0;
        }
        mContentView.layout(-dis, mContentView.getTop(), mContentView.getWidth() - dis, getMeasuredHeight());
        if (mMenuStickTo == SwipeMenuListView.STICK_TO_ITEM_RIGHT_SIDE) {
            mMenuView.layout(mContentView.getWidth() - dis, mMenuView.getTop(),
                    mContentView.getWidth() + mMenuView.getWidth() - dis, mMenuView.getBottom());
        }
    }

    @Override
    public void computeScroll() {
        if (state == STATE_OPEN) {
            if (mOpenScroller.computeScrollOffset()) {
                swipe(mOpenScroller.getCurrX());
                postInvalidate();
            }
        } else {
            if (mCloseScroller.computeScrollOffset()) {
                swipe(mBaseX - mCloseScroller.getCurrX());
                postInvalidate();
            }
        }
    }

    public void smoothCloseMenu() {
        state = STATE_CLOSE;
        mBaseX = -mContentView.getLeft();
        mCloseScroller.startScroll(0, 0, mBaseX, 0, 350);
        postInvalidate();
    }

    public void smoothOpenMenu() {
        state = STATE_OPEN;
        mOpenScroller.startScroll(-mContentView.getLeft(), 0, mMenuView.getWidth(), 0, 350);
        postInvalidate();
    }

    public void closeMenu() {
        if (mCloseScroller.computeScrollOffset()) {
            mCloseScroller.abortAnimation();
        }
        if (state == STATE_OPEN) {
            state = STATE_CLOSE;
            swipe(0);
        }
    }

    public void openMenu() {
        if (state == STATE_CLOSE) {
            state = STATE_OPEN;
            swipe(mMenuView.getWidth());
            postInvalidate();
        }
    }

    public View getContentView() {
        return mContentView;
    }

    public SwipeMenuView getMenuView() {
        return mMenuView;
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getContext().getResources()
                .getDisplayMetrics());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mMenuView.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
                MeasureSpec.makeMeasureSpec(getMeasuredHeight(), MeasureSpec.EXACTLY));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mContentView.layout(0, 0, getMeasuredWidth(), mContentView.getMeasuredHeight());
        if (mMenuStickTo == SwipeMenuListView.STICK_TO_ITEM_RIGHT_SIDE) {
            mMenuView.layout(getMeasuredWidth(), 0, getMeasuredWidth() + mMenuView.getMeasuredWidth(),
                    mContentView.getMeasuredHeight());
        } else {
            mMenuView.layout(getMeasuredWidth() - mMenuView.getMeasuredWidth(), 0, getMeasuredWidth(),
                    mContentView.getMeasuredHeight());
        }
        // setMenuHeight(mContentView.getMeasuredHeight());
        // bringChildToFront(mContentView);
    }

    public void setMenuHeight(int measuredHeight) {
        Log.i("byz", "pos = " + position + ", height = " + measuredHeight);
        LayoutParams params = (LayoutParams) mMenuView.getLayoutParams();
        if (params.height != measuredHeight) {
            params.height = measuredHeight;
            mMenuView.setLayoutParams(mMenuView.getLayoutParams());
        }
    }
}
