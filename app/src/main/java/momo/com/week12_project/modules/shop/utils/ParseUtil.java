package momo.com.week12_project.modules.shop.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import momo.com.week12_project.modules.shop.bean.ShopInfo;

/**
 * Created by Administrator on 2017/1/6 0006.
 */
public class ParseUtil {

    /*
    {
    "info":[
        {
            "id":"512",
            "name":"50元电信话费直冲",
            "consume":"51000",
            "icon":"http://i2.265g.com/images/201503/201503251059126602.jpg",
            "remain":"58"
        },
        {
            "id":"563",
            "name":"骏网10元游戏充值卡",
            "consume":"10000",
            "icon":"http://i5.265g.com/images/201503/201503301345024885.jpg",
            "remain":"20"
        },
        {
            "id":"583",
            "name":"爱奇艺会员年卡",
            "consume":"236000",
            "icon":"http://i6.265g.com/images/201603/201603211218597820.jpg",
            "remain":"48"
        },
        {
            "id":"582",
            "name":"爱奇艺会员半年",
            "consume":"118000",
            "icon":"http://i6.265g.com/images/201603/201603211218597820.jpg",
            "remain":"49"
        },
        {
            "id":"581",
            "name":"爱奇艺会员季卡",
            "consume":"59400",
            "icon":"http://i6.265g.com/images/201603/201603211218597820.jpg",
            "remain":"39"
        },
        {
            "id":"580",
            "name":"爱奇艺会员月卡",
            "consume":"19800",
            "icon":"http://i6.265g.com/images/201603/201603211218597820.jpg",
            "remain":"20"
        },
        {
            "id":"363",
            "name":"30元Q币直充",
            "consume":"30600",
            "icon":"http://i2.265g.com/images/201503/201503251056401698.jpg",
            "remain":"144"
        },
        {
            "id":"575",
            "name":"全国联通流量100M充值",
            "consume":"11000",
            "icon":"http://i5.265g.com/images/201510/201510281431568786.jpg",
            "remain":"75"
        },
        {
            "id":"577",
            "name":"全国电信流量100M充值",
            "consume":"11000",
            "icon":"http://i4.265g.com/images/201510/201510281431408959.jpg",
            "remain":"83"
        },
        {
            "id":"576",
            "name":"全国移动流量充值70M流量包",
            "consume":"11000",
            "icon":"http://i4.265g.com/images/201510/201510281431251486.jpg",
            "remain":"57"
        }
    ],
    "page":{
        "total":"18",
        "pagesize":10,
        "page":1,
        "page_total":2
    },
    "state":"success"
}
     */

    //只解析其id name icon
    public static List<ShopInfo> getShopList(String result) {
        List<ShopInfo> list = new ArrayList<>();
        ShopInfo info;
        try {
            JSONObject obj = new JSONObject(result);
            JSONArray infoArr = obj.optJSONArray("info");
            int len  = infoArr.length();
            for (int i = 0; i < len; i++) {
                JSONObject temp = infoArr.getJSONObject(i);
                info = new ShopInfo();
                info.setId(temp.optString("id"));
                info.setName(temp.optString("name"));
                info.setImgUrl(temp.optString("icon"));
                list.add(info);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

}
