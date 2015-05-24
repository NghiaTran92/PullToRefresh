package com.handmark.pulltorefresh.samples;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import swipelistview.BaseSwipeListViewListener;
import swipelistview.SwipeListView;

/**
 * Created by ngh on 5/19/2015.
 */
public class PullToRefreshSwipeListViewActivity extends Activity {

    private List<ItemWoises> mList;
    private com.handmark.pulltorefresh.library.PullToRefreshSwipeListView mPullToRefreshSwipeListView;
    private PackageAdapter mAdapter;
    private SwipeListView mSwipeListView;
    private Activity mActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ptr_swipe_list);
        mActivity=this;
        mPullToRefreshSwipeListView=(com.handmark.pulltorefresh.library.PullToRefreshSwipeListView)findViewById(R.id.pull_refresh_swipe_list_view);
        mPullToRefreshSwipeListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<SwipeListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<SwipeListView> refreshView) {
                Toast.makeText(mActivity,"onPullDownToRefresh",Toast.LENGTH_SHORT).show();
                final Handler hand= new Handler();
                hand.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPullToRefreshSwipeListView.onRefreshComplete();
                        hand.removeCallbacks(this);
                    }
                },2000);

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<SwipeListView> refreshView) {
                Toast.makeText(mActivity,"onPullUpToRefresh",Toast.LENGTH_SHORT).show();
                final Handler hand= new Handler();
                hand.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPullToRefreshSwipeListView.onRefreshComplete();
                        hand.removeCallbacks(this);
                    }
                }, 2000);

            }
        });

        mSwipeListView=mPullToRefreshSwipeListView.getRefreshableView();
        mList=new ArrayList<>();
        ItemWoises item;
        for(int i=0;i<10;i++){
            item=new ItemWoises();
            item.IdeaTitle=" item "+i;
            mList.add(item);
        }
        mAdapter=new PackageAdapter(this,mList);
        Resources res=getResources();
        float defaultOffset=200;
        mSwipeListView.setOffsetLeft(convertDp2Px(defaultOffset, res));
        mSwipeListView.setOffsetRight(convertDp2Px(defaultOffset, res));
        mSwipeListView.setAdapter(mAdapter);

        mSwipeListView.setSwipeListViewListener(new BaseSwipeListViewListener() {
            @Override
            public void onOpened(int position, boolean toRight) {
            }

            @Override
            public void onClosed(int position, boolean fromRight) {
            }

            @Override
            public void onListChanged() {
            }

            @Override
            public void onMove(int position, float x) {
            }

            @Override
            public void onStartOpen(int position, int action, boolean right) {
                Log.d("swipe", String.format("onStartOpen %d - action %d", position, action));
            }

            @Override
            public void onStartClose(int position, boolean right) {
                Log.d("swipe", String.format("onStartClose %d", position));
            }

            @Override
            public void onClickFrontView(int position) {
                Log.d("swipe", String.format("onClickFrontView %d", position));
            }

            @Override
            public void onClickBackView(int position) {
                Log.d("swipe", String.format("onClickBackView %d", position));
            }

            @Override
            public void onDismiss(int[] reverseSortedPositions) {
                //  Log.d("swipe", String.format("onClickBackView %d", position));
            }

        });

    }

    public static int convertDp2Px(float dp,Resources res){
        DisplayMetrics metrics = res.getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return (int) px;
    }

    public class ItemWoises implements Serializable {

        public String IdeaTitle ;

    }


    public class PackageAdapter extends BaseAdapter {

        private Context mContext;
        private List<ItemWoises> mList;

        public PackageAdapter(Context context, List<ItemWoises> list) {
            mContext = context;
            mList = list;
        }

        @Override
        public int getCount() {
            return mList == null ? 0 : mList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            Context context = parent.getContext();
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.package_row, parent, false);

                holder = new ViewHolder();
                holder.mTxtTitle = (TextView) convertView.findViewById(R.id.txt_title);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            ItemWoises item = mList.get(position);

            holder.mTxtTitle.setText(item.IdeaTitle == null ? "" : item.IdeaTitle);


            return convertView;
        }



        private class ViewHolder {
            TextView mTxtTitle;

        }
    }

}
