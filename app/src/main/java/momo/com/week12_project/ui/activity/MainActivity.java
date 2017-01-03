package momo.com.week12_project.ui.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import momo.com.week12_project.R;
import momo.com.week12_project.modules.game.ui.fragment.GameFragment;
import momo.com.week12_project.modules.home.ui.fragment.HomeFragment;
import momo.com.week12_project.modules.me.ui.fragment.MeFragment;
import momo.com.week12_project.modules.shop.ui.fragment.ShopFragment;
import momo.com.week12_project.widget.BottomMenu;

public class MainActivity extends BaseActivity implements View.OnClickListener {


    //bmLast记录上次点击的按钮
    private BottomMenu bmHome,bmGame,bmShop,bmMe,bmLast;
    private Fragment homeFragment,gameFragment,shopFragment,meFragment;


    @Override
    protected int setLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void findViews() {
        bmHome = (BottomMenu) findViewById(R.id.home_menu);
        bmGame = (BottomMenu) findViewById(R.id.game_menu);
        bmShop = (BottomMenu) findViewById(R.id.shop_menu);
        bmMe = (BottomMenu) findViewById(R.id.me_menu);
    }

    @Override
    protected void initEvent() {
        bmHome.setOnClickListener(this);
        bmGame.setOnClickListener(this);
        bmShop.setOnClickListener(this);
        bmMe.setOnClickListener(this);

    }

    @Override
    protected void init() {
        homeFragment = new HomeFragment();
        gameFragment = new GameFragment();
        shopFragment = new ShopFragment();
        meFragment  = new MeFragment();
        //各menu与对应的fragment绑定在一起
        bmHome.setFragment(homeFragment);
        bmGame.setFragment(gameFragment);
        bmShop.setFragment(shopFragment);
        bmMe.setFragment(meFragment);

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.fragment_containter_main,homeFragment);
        transaction.add(R.id.fragment_containter_main,gameFragment);
        transaction.add(R.id.fragment_containter_main,shopFragment);
        transaction.add(R.id.fragment_containter_main,meFragment);
        transaction.hide(homeFragment);
        transaction.hide(gameFragment);
        transaction.hide(shopFragment);
        transaction.hide(meFragment);
        transaction.commit();

        bmHome.performClick();

    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onClick(View v) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        BottomMenu bm = (BottomMenu) v;
        //展示本次点击按钮的动画和fragment
        bm.selectMenu();
        transaction.show(bm.getFragment());

        if(bmLast!=null){
            //上次点击按钮的取消动画
            bmLast.unselectMenu();
            //隐藏上次展示的fragment
            transaction.hide(bmLast.getFragment());
        }

        transaction.commit();
        bmLast = bm;

    }


    public void jumpToGameFragment(){
        bmGame.performClick();
    }
}
