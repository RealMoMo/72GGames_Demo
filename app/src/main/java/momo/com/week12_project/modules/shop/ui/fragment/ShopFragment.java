package momo.com.week12_project.modules.shop.ui.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import momo.com.week12_project.R;
import momo.com.week12_project.adapter.CommonAdapter;
import momo.com.week12_project.adapter.ViewHolder;
import momo.com.week12_project.i.BaseCallBack;
import momo.com.week12_project.modules.shop.bean.ShopInfo;
import momo.com.week12_project.modules.shop.dao.ShopDao;
import momo.com.week12_project.ui.fragment.BaseFragment;

/**
 * Created by Administrator on 2016/12/30 0030.
 */
public class ShopFragment extends BaseFragment implements AbsListView.OnScrollListener {

    private SwipeRefreshLayout refresh;
    private ListView lv;
    private List<ShopInfo> list;
    private CommonAdapter<ShopInfo> adapter;

    private int page = 1;
    private boolean isLoading;


    @Override
    protected int setLayoutId() {
        return R.layout.fragment_shop;
    }

    @Override
    protected void findViews(View view) {
        refresh = (SwipeRefreshLayout) view.findViewById(R.id.refresh_shop);
        lv = (ListView) view.findViewById(R.id.lv_shop);
    }

    @Override
    protected void initEvent() {
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                loadData();
            }
        });

        lv.setOnScrollListener(this);
    }

    @Override
    protected void init() {
        list = new ArrayList<>();
        adapter = new CommonAdapter<ShopInfo>(getActivity(), list, R.layout.layout_shop_listview) {
            @Override
            public void convert(ViewHolder helper, int position, ShopInfo item) {
                //TODO 绑定控件
                //左边item
                ShopInfo leftInfo = list.get(position * 2);
                helper.setText(R.id.tv_name_left_shop, leftInfo.getName());
                helper.setImageByUrl(R.id.iv_icon_left_shop, leftInfo.getImgUrl());
                RelativeLayout rlLeft = helper.getView(R.id.rl_left_layout_shop);
                //实现其点击事件
                rlLeft.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //TODO 跳转到详情界面
                    }
                });

                //右边item
                RelativeLayout rlRight = helper.getView(R.id.rl_rightLayout_shop);
                if ((position * 2 + 1) <= list.size() - 1) {

                    rlRight.setVisibility(View.VISIBLE);
                    rlRight.setClickable(true);

                    ShopInfo rightInfo = list.get(position * 2 + 1);
                    helper.setText(R.id.tv_name_right_shop, rightInfo.getName());
                    helper.setImageByUrl(R.id.iv_icon_right_shop, rightInfo.getImgUrl());
                    //实现其点击事件
                    rlLeft.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //TODO 跳转到详情界面
                        }
                    });
                } else {
                    //这是针对最后一行，右边的item没有内容。设置不可点击以及不可见。（若是可见，可能展示之前的数据内容）
                    rlRight.setClickable(false);
                    rlRight.setVisibility(View.INVISIBLE);
                }
            }
        };

        lv.setAdapter(adapter);
    }

    @Override
    protected void loadData() {
        if (isLoading) {
            return;
        }
        isLoading = true;
        ShopDao.requestShopList(page, new BaseCallBack() {
            @Override
            public void success(Object data) {
                isLoading = false;
                refresh.setRefreshing(false);
                List<ShopInfo> tempList = (List<ShopInfo>) data;
                if (page == 1) {
                    list.clear();
                }

                if (tempList != null) {
                    list.addAll(tempList);
                    //因为listview 一行有2个item。所以，要重新给adapter设置行数。
                    // 不能直接adapter.notifyDataSetChanged(),否则listview滑到底，肯定会下标越界。
                    adapter.setCount((list.size() + 1) / 2);
//                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void failed(int errorCode, Object data) {
                page--;
                refresh.setRefreshing(false);
                Toast.makeText(getActivity(), data.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

        //listview滑动状态为停止
        if (scrollState == SCROLL_STATE_IDLE) {
            //最后一个可见的item的Postion位置
            int lastPosition = lv.getLastVisiblePosition();
            //listview滑动到最后一个可见item
            if (lastPosition == lv.getCount() - 1) {
                int count = lv.getCount();
                //listview 最后一个item
                View lastChild = lv.getChildAt(count - lv.getFirstVisiblePosition()-1);//getChildAt(index)的取值，只能是当前可见区域（列表可滚动）的子项！
                //两者底部重合代表到底最底部
                if (lastChild.getBottom() == lv.getBottom()) {
                    page++;
                    loadData();
                }

            }
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }
}
