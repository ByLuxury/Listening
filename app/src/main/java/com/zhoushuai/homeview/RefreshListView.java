
package com.zhoushuai.homeview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.DecelerateInterpolator;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Scroller;
import android.widget.TextView;
import com.zhoushuai.listening.R;
/**
 * Created by zhoushuai on 18/04/2017.
 */
public class RefreshListView extends ListView implements OnScrollListener {

	private float mLastY = -1; 
	private Scroller mScroller; 
	private OnScrollListener mScrollListener; 

	private IXListViewListener mListViewListener;


	private RefreshListViewHeader mHeaderView;

	private RelativeLayout mHeaderViewContent;
	private TextView mHeaderTimeView;
	private int mHeaderViewHeight; 
	private boolean mEnablePullRefresh = true;
	private boolean mPullRefreshing = false; // 正在刷新.


	private RefreshListViewFooter mFooterView;
	private boolean mEnablePullLoad;
	private boolean mPullLoading;
	private boolean mIsFooterReady = false;

	private int mTotalItemCount;
	private int mScrollBack;
	private final static int SCROLLBACK_HEADER = 0;
	private final static int SCROLLBACK_FOOTER = 1;

	private final static int SCROLL_DURATION = 400; 
	private final static int PULL_LOAD_MORE_DELTA = 50; //下拉超过50px,加载更多
	private final static float OFFSET_RADIO = 1.8f; 

	/**
	 * @param context
	 */
	public RefreshListView(Context context) {
		super(context);
		initWithContext(context);
	}

	public RefreshListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initWithContext(context);
	}

	public RefreshListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initWithContext(context);
	}

	private void initWithContext(Context context) {
		mScroller = new Scroller(context, new DecelerateInterpolator());
		super.setOnScrollListener(this);

		// 初始化头部视图
		mHeaderView = new RefreshListViewHeader(context);
		mHeaderViewContent = (RelativeLayout) mHeaderView.findViewById(R.id.xlistview_header_content);
		mHeaderTimeView = (TextView) mHeaderView
				.findViewById(R.id.xlistview_header_time);
		addHeaderView(mHeaderView);

		// 初始化底部视图
		mFooterView = new RefreshListViewFooter(context);

		// 初始化头部高度
		mHeaderView.getViewTreeObserver().addOnGlobalLayoutListener(
				new OnGlobalLayoutListener() {
					@Override
					public void onGlobalLayout() {
						mHeaderViewHeight = mHeaderViewContent.getHeight();
						getViewTreeObserver()
								.removeGlobalOnLayoutListener(this);
					}
				});
	}

	@Override
	public void setAdapter(ListAdapter adapter) {
		// 确保已经到底部，只加载一次
		if (mIsFooterReady == false) {
			mIsFooterReady = true;
			addFooterView(mFooterView);
		}
		super.setAdapter(adapter);
	}

	/**
	 *启用或禁用下拉刷新
	 * 
	 * @param enable
	 */
	public void setPullRefreshEnable(boolean enable) {
		mEnablePullRefresh = enable;
		if (!mEnablePullRefresh) { // 隐藏内容
			mHeaderViewContent.setVisibility(View.INVISIBLE);
		} else {
			mHeaderViewContent.setVisibility(View.VISIBLE);
		}
	}

	/**
	 *启用或禁用加载更多
	 * 
	 * @param enable
	 */
	public void setPullLoadEnable(boolean enable) {
		mEnablePullLoad = enable;
		if (!mEnablePullLoad) {
			mFooterView.hide();
			mFooterView.setOnClickListener(null);
		} else {
			mPullLoading = false;
			mFooterView.show();
			mFooterView.setState(RefreshListViewFooter.STATE_NORMAL);
			mFooterView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					startLoadMore();
				}
			});
		}
	}

	/**
	 * 停止加载，重置头部
	 */
	public void stopRefresh() {
		if (mPullRefreshing == true) {
			mPullRefreshing = false;
			resetHeaderHeight();
		}
	}

	/**
	 * 停止加载更多，重置底部
	 */
	public void stopLoadMore() {
		if (mPullLoading == true) {
			mPullLoading = false;
			mFooterView.setState(RefreshListViewFooter.STATE_NORMAL);
		}
	}

	/**
	 * 设置最后一次刷新时间
	 * 
	 * @param time
	 */
	public void setRefreshTime(String time) {
		mHeaderTimeView.setText(time);
	}

	private void invokeOnScrolling() {
		if (mScrollListener instanceof OnXScrollListener) {
			OnXScrollListener l = (OnXScrollListener) mScrollListener;
			l.onXScrolling(this);
		}
	}

	private void updateHeaderHeight(float delta) {
		mHeaderView.setVisiableHeight((int) delta
				+ mHeaderView.getVisiableHeight());
		if (mEnablePullRefresh && !mPullRefreshing) { // 未处于刷新状态，更新箭头
			if (mHeaderView.getVisiableHeight() > mHeaderViewHeight) {
				mHeaderView.setState(RefreshListViewHeader.STATE_READY);
			} else {
				mHeaderView.setState(RefreshListViewHeader.STATE_NORMAL);
			}
		}
		setSelection(0); 
	}

	/**
	 * 重置顶部高度
	 */
	private void resetHeaderHeight() {
		int height = mHeaderView.getVisiableHeight();
		if (height == 0) 
			return;

		if (mPullRefreshing && height <= mHeaderViewHeight) {
			return;
		}
		int finalHeight = 0; 
	
		if (mPullRefreshing && height > mHeaderViewHeight) {
			finalHeight = mHeaderViewHeight;
		}
		mScrollBack = SCROLLBACK_HEADER;
		mScroller.startScroll(0, height, 0, finalHeight - height,
				SCROLL_DURATION);
		
		invalidate();
	}

	private void updateFooterHeight(float delta) {
		int height = mFooterView.getBottomMargin() + (int) delta;
		if (mEnablePullLoad && !mPullLoading) {
			if (height > PULL_LOAD_MORE_DELTA) { 
				mFooterView.setState(RefreshListViewFooter.STATE_READY);
			} else {
				mFooterView.setState(RefreshListViewFooter.STATE_NORMAL);
			}
		}
		mFooterView.setBottomMargin(height);

		setSelection(mTotalItemCount - 1); 
	}

	private void resetFooterHeight() {
		int bottomMargin = mFooterView.getBottomMargin();
		if (bottomMargin > 0) {
			mScrollBack = SCROLLBACK_FOOTER;
			mScroller.startScroll(0, bottomMargin, 0, -bottomMargin,
					SCROLL_DURATION);
			invalidate();
		}
	}

	private void startLoadMore() {
		mPullLoading = true;
		mFooterView.setState(RefreshListViewFooter.STATE_LOADING);
		if (mListViewListener != null) {
			mListViewListener.onLoadMore();
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		if (mLastY == -1) {
			mLastY = ev.getRawY();
		}

		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			mLastY = ev.getRawY();
			break;
		case MotionEvent.ACTION_MOVE:
			final float deltaY = ev.getRawY() - mLastY;
			mLastY = ev.getRawY();
			if (getFirstVisiblePosition() == 0
					&& (mHeaderView.getVisiableHeight() > 0 || deltaY > 0)) {
			
				updateHeaderHeight(deltaY / OFFSET_RADIO);
				invokeOnScrolling();
			} 
			else if (getLastVisiblePosition() == mTotalItemCount - 1
					&& (mFooterView.getBottomMargin() > 0 || deltaY < 0)) {
			
				updateFooterHeight(-deltaY / OFFSET_RADIO);
			}
			break;
		default:
			mLastY = -1; 
			if (getFirstVisiblePosition() == 0) {
		
				if (mEnablePullRefresh
						&& mHeaderView.getVisiableHeight() > mHeaderViewHeight) {
					mPullRefreshing = true;
					mHeaderView.setState(RefreshListViewHeader.STATE_REFRESHING);
					if (mListViewListener != null) {
						mListViewListener.onRefresh();
					}
				}
				resetHeaderHeight();
			}
				else if (getLastVisiblePosition() == mTotalItemCount - 1) {
				
				if (mEnablePullLoad
				    && mFooterView.getBottomMargin() > PULL_LOAD_MORE_DELTA
				    && !mPullLoading) {
					startLoadMore();
				}
				resetFooterHeight();
			}
			break;
		}
		return super.onTouchEvent(ev);
	}

	@Override
	public void computeScroll() {
		if (mScroller.computeScrollOffset()) {
			if (mScrollBack == SCROLLBACK_HEADER) {
				mHeaderView.setVisiableHeight(mScroller.getCurrY());
			} 
			else {
				mFooterView.setBottomMargin(mScroller.getCurrY());
			}
			postInvalidate();
			invokeOnScrolling();
		}
		super.computeScroll();
	}

	@Override
	public void setOnScrollListener(OnScrollListener l) {
		mScrollListener = l;
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		if (mScrollListener != null) {
			mScrollListener.onScrollStateChanged(view, scrollState);
		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		
		mTotalItemCount = totalItemCount;
		if (mScrollListener != null) {
			mScrollListener.onScroll(view, firstVisibleItem, visibleItemCount,
					totalItemCount);
		}
	}

	public void setXListViewListener(IXListViewListener l) {
		mListViewListener = l;
	}

	
	public interface OnXScrollListener extends OnScrollListener {
		void onXScrolling(View view);
	}
//回调接口
	public interface IXListViewListener {
		 void onRefresh();

		 void onLoadMore();
	}
}
