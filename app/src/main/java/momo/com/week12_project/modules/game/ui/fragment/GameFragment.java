package momo.com.week12_project.modules.game.ui.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import momo.com.week12_project.R;
import momo.com.week12_project.adapter.CommonAdapter;
import momo.com.week12_project.adapter.ViewHolder;
import momo.com.week12_project.i.BaseCallBack;
import momo.com.week12_project.modules.game.bean.GameInfo;
import momo.com.week12_project.modules.game.dao.GameDao;
import momo.com.week12_project.ui.fragment.BaseFragment;

/**
 * Created by Administrator on 2016/12/30 0030.
 */

public class GameFragment extends BaseFragment implements AbsListView.OnScrollListener {

    private SwipeRefreshLayout refresh;
    private ListView lv;
    private CommonAdapter<GameInfo> adapter;

    private List<GameInfo> list;

    private int page = 1 ;

    private boolean isLoading;


    @Override
    protected int setLayoutId() {
        return R.layout.fragment_game;
    }

    @Override
    protected void findViews(View view) {
        refresh = (SwipeRefreshLayout) view.findViewById(R.id.refresh_game);
        lv = (ListView) view.findViewById(R.id.lv_game);
    }

    @Override
    protected void initEvent() {
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page =1 ;
                loadData();
            }
        });
        lv.setOnScrollListener(this);
    }

    @Override
    protected void init() {
        list = new ArrayList<>();

        adapter = new CommonAdapter<GameInfo>(getActivity(),list,R.layout.layout_game_listview) {
            @Override
            public void convert(ViewHolder helper, int position, GameInfo item) {
                //TODO 控件设置内容
                helper.setText(R.id.tv_name_gameList,item.getName());
                helper.setText(R.id.tv_count_gameList,"下载总数："+item.getCount());
                helper.setImageByUrl(R.id.iv_icon_gameList,item.getIcon());
                RatingBar ratingBar = helper.getView(R.id.rating_bar_gameList);
                //设置进度，四舍五入
                ratingBar.setProgress( Math.round(item.getScore()));
            }
        };
        lv.setAdapter(adapter);

    }

    @Override
    protected void loadData() {
        //避免正在加载内容的时候，用户误操作或重复操作再加载其它的内容
        if(isLoading){
            return;
        }
        isLoading = true;
        GameDao.requestGameList(page, new BaseCallBack() {
            @Override
            public void success(Object data) {
                isLoading = false;
                refresh.setRefreshing(false);
                List<GameInfo> tempList = (List<GameInfo>) data;
                if(page==1){
                    list.clear();
                }
                if (tempList!=null) {
                    list.addAll(tempList);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void failed(int errorCode, Object data) {
                isLoading = false;
                page--;
                refresh.setRefreshing(false);
                Toast.makeText(getActivity(), "加载失败", Toast.LENGTH_SHORT).show();
            }
        });


    }



    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {

    }

    @Override
    public void onScroll(AbsListView absListView, int i, int i1, int i2) {

    }
}
