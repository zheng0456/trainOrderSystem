package com.etc.trainordersys.utils;

import com.baidu.aip.face.AipFace;
import org.json.JSONObject;

import java.util.HashMap;

public class FaceUtils {
    //设置APPID/AK/SK
    public static final String APP_ID = "118909378";
    public static final String API_KEY = "AlnMREtYx8mis3YMJuvo6pOc";
    public static final String SECRET_KEY = "ktf85fMeHP9dqvTKO6MkoiHVuGkEcZkB";

    static AipFace client = null;
    //静态块，类加载的时候只会执行一次
    static {
        // 初始化一个AipFace
        client = new AipFace(APP_ID, API_KEY, SECRET_KEY);

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);
    }
    //人脸注册
    public static String faceRegister(String image,String userInfo,String userId,String groupId) {
        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("user_info", userInfo);
        options.put("quality_control", "NORMAL");
        options.put("liveness_control", "LOW");
        options.put("action_type", "REPLACE");

        String imageType = "BASE64";

        // 人脸注册
        JSONObject res = client.addUser(image, imageType, groupId, userId, options);
        return res.toString(2);
    }
    //人脸登录
    public static String faceLogin(String image,String groupIdList) {
        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("match_threshold", "90");
        options.put("quality_control", "NORMAL");
        options.put("liveness_control", "LOW");


        String imageType = "BASE64";

        // 人脸注册
        JSONObject res = client.search(image, imageType, groupIdList, options);
        return res.toString(2);
    }
}
