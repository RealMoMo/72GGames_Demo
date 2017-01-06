package momo.com.week12_project.modules.game.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import momo.com.week12_project.modules.game.bean.GameInfo;

/**
 * Created by Administrator on 2016/12/30 0030.
 */

public class ParseUtil {

    public static List<GameInfo> getGameList(String result) {
        List<GameInfo> list = new ArrayList<>();
        GameInfo info;
        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("info");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject subJson = jsonArray.getJSONObject(i);
                info = new GameInfo();
                info.setId(subJson.optString("id"));
                info.setName(subJson.optString("name"));
                info.setIcon(subJson.optString("icon"));
                info.setScore((float) subJson.optDouble("score"));
                info.setCount(subJson.optString("count_dl"));
                info.setDownLoadUrl(subJson.optString("android_dl"));
                list.add(info);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }
}
