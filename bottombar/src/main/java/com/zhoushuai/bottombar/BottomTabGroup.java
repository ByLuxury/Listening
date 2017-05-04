package com.zhoushuai.bottombar;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


public class BottomTabGroup extends LinearLayout {

    //选中id
    private int mCheckedId = -1;

    private BottomTabImpl.OnCheckedChangeListener mChildOnCheckedChangeListener;

    private boolean mProtectFromCheckedChange = false;

    private OnCheckedChangeListener mOnCheckedChangeListener;

    private PassThroughHierarchyChangeListener mPassThroughListener;



    public BottomTabGroup(Context context) {
        super(context);
        setOrientation(HORIZONTAL);
        init();
    }


    public BottomTabGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(HORIZONTAL);
        init();
    }

    private void init() {
        mChildOnCheckedChangeListener = new CheckedStateTracker();
        mPassThroughListener = new PassThroughHierarchyChangeListener();
        super.setOnHierarchyChangeListener(mPassThroughListener);
    }


    @Override
    public void setOnHierarchyChangeListener(OnHierarchyChangeListener listener) {
        // the user listener is delegated to our pass-through listener
        mPassThroughListener.mOnHierarchyChangeListener = listener;
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        if (mCheckedId != -1) {
            mProtectFromCheckedChange = true;
            setCheckedStateForView(mCheckedId, true);
            mProtectFromCheckedChange = false;
            setCheckedId(mCheckedId);
        }
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        if (child instanceof BottomTabImpl) {
            final BottomTabImpl button = (BottomTabImpl) child;
            if (button.isChecked()) {
                mProtectFromCheckedChange = true;
                if (mCheckedId != -1) {
                    setCheckedStateForView(mCheckedId, false);
                }
                mProtectFromCheckedChange = false;
                setCheckedId(button.getId());
            }
        }
        super.addView(child, index, params);
    }


    public void check(int id) {

        if (id != -1 && (id == mCheckedId)) {
            return;
        }

        if (mCheckedId != -1) {
            setCheckedStateForView(mCheckedId, false);
        }

        if (id != -1) {
            setCheckedStateForView(id, true);
        }

        setCheckedId(id);
    }
    
    private void setCheckedId(int id) {
        mCheckedId = id;
        if (mOnCheckedChangeListener != null) {
            mOnCheckedChangeListener.onCheckedChanged(this, mCheckedId);
        }
    }
    
    private void setCheckedStateForView(int viewId, boolean checked) {
        View checkedView = findViewById(viewId);
        if (checkedView != null && checkedView instanceof BottomTabImpl) {
            ((BottomTabImpl) checkedView).setChecked(checked);
        }
    }

    public int getCheckedChildId() {
        return mCheckedId;
    }


    public void clearCheck() {
        check(-1);
    }



    public void setOnCheckedChangeListener(OnCheckedChangeListener listener) {
        mOnCheckedChangeListener = listener;
    }
    

    @Override
    public LinearLayout.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new BottomTabGroup.LayoutParams(getContext(), attrs);
    }

    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof BottomTabGroup.LayoutParams;
    }

    @Override
    protected LinearLayout.LayoutParams generateDefaultLayoutParams() {
        return new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    public static class LayoutParams extends LinearLayout.LayoutParams {


        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }


        public LayoutParams(int w, int h) {
            super(w, h);
        }



        public LayoutParams(int w, int h, float initWeight) {
            super(w, h, initWeight);
        }


        public LayoutParams(ViewGroup.LayoutParams p) {
            super(p);
        }


        public LayoutParams(MarginLayoutParams source) {
            super(source);
        }


        @Override
        protected void setBaseAttributes(TypedArray a,
                int widthAttr, int heightAttr) {

            if (a.hasValue(widthAttr)) {
                width = a.getLayoutDimension(widthAttr, "layout_width");
            } else {
                width = WRAP_CONTENT;
            }

            if (a.hasValue(heightAttr)) {
                height = a.getLayoutDimension(heightAttr, "layout_height");
            } else {
                height = WRAP_CONTENT;
            }
        }
    }


    /**
     * 回调接口
     */
    public interface OnCheckedChangeListener {

         void onCheckedChanged(BottomTabGroup root, int checkedId);
    }

    private class CheckedStateTracker implements BottomTabImpl.OnCheckedChangeListener {

        public void onCheckedChanged(BottomTabImpl buttonView, boolean isChecked) {
            if (mProtectFromCheckedChange) {
                return;
            }

            mProtectFromCheckedChange = true;
            if (mCheckedId != -1) {
                setCheckedStateForView(mCheckedId, false);
            }
            mProtectFromCheckedChange = false;

            int id = buttonView.getId();
            setCheckedId(id);
        }
    }



    private class PassThroughHierarchyChangeListener implements
            ViewGroup.OnHierarchyChangeListener {
        private ViewGroup.OnHierarchyChangeListener mOnHierarchyChangeListener;


        public void onChildViewAdded(View parent, View child) {
            if (parent == BottomTabGroup.this && child instanceof BottomTabImpl) {
                int id = child.getId();
                if (id == View.NO_ID) {
                    id = View.generateViewId();
                    child.setId(id);
                }
                ((BottomTabImpl) child).setOnCheckedChangeWidgetListener(
                        mChildOnCheckedChangeListener);
            }

            if (mOnHierarchyChangeListener != null) {
                mOnHierarchyChangeListener.onChildViewAdded(parent, child);
            }
        }


        public void onChildViewRemoved(View parent, View child) {
            if (parent == BottomTabGroup.this && child instanceof BottomTabImpl) {
                ((BottomTabImpl) child).setOnCheckedChangeWidgetListener(null);
            }

            if (mOnHierarchyChangeListener != null) {
                mOnHierarchyChangeListener.onChildViewRemoved(parent, child);
            }
        }
    }
}
