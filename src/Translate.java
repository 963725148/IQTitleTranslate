import com.baidu.translate.TransApi;

public class Translate {

    // 在平台申请的APP_ID 详见 http://api.fanyi.baidu.com/api/trans/product/desktop?req=developer
    private static final String APP_ID = "";
    private static final String SECURITY_KEY = "";

    public static  String getRes(String query) {
        TransApi api = new TransApi(APP_ID, SECURITY_KEY);
        String res = api.getTransResult(query, "auto", "en");
        res = res.substring(res.indexOf("dst\":\"")+6,res.indexOf("\"}]}"));
        return res;
    }

}
