package momo.com.week12_project.modules.game.dao;

import java.util.HashMap;
import java.util.List;

import momo.com.week12_project.i.BaseCallBack;
import momo.com.week12_project.modules.game.bean.GameInfo;
import momo.com.week12_project.modules.game.utils.ParseUtil;
import momo.com.week12_project.utils.HttpUtil;

/**
 * Created by Administrator on 2016/12/30 0030.
 */

public class GameDao {

    public static void requestGameList(int page, final BaseCallBack callBack){
        HashMap<String,String> params=new HashMap<>();
        //平台固定 ios为1 android为2
        params.put("platform","2");
        params.put("page",page+"");
        HttpUtil.doHttpReqeust("POST", "http://zhushou.72g.com/app/game/game_list/", params, new BaseCallBack() {
            @Override
            public void success(Object data) {
                //解析数据
                List<GameInfo> list= ParseUtil.getGameList(data.toString());
                callBack.success(list);
            }

            @Override
            public void failed(int errorCode, Object data) {
                callBack.failed(errorCode,data);
            }
        });
    }
}
