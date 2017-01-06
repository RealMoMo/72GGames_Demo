package momo.com.week12_project.modules.shop.dao;

import java.util.HashMap;
import java.util.List;

import momo.com.week12_project.i.BaseCallBack;
import momo.com.week12_project.modules.shop.bean.ShopInfo;
import momo.com.week12_project.modules.shop.utils.ParseUtil;
import momo.com.week12_project.utils.HttpUtil;

/**
 * Created by Administrator on 2017/1/6 0006.
 */

public class ShopDao {

    public static void requestShopList(int page, final BaseCallBack callBack){
        HashMap<String,String> params = new HashMap<>();
        params.put("type","1");
        params.put("page",""+page);
        HttpUtil.doHttpReqeust("POST", "http://www.yuu1.com/app_api/prize_list/", params, new BaseCallBack() {
            @Override
            public void success(Object data) {
                List<ShopInfo> list = ParseUtil.getShopList(data.toString());
                callBack.success(list);
            }

            @Override
            public void failed(int errorCode, Object data) {
                callBack.failed(errorCode,data);
            }
        });

    }
}
