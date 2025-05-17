package com.etc.trainordersys.utils;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;
import org.springframework.stereotype.Component;

/**
 * 支付宝沙箱工具类
 */
@Component
public class AlipayUtils {

    private String  serverUrl ="https://openapi-sandbox.dl.alipaydev.com/gateway.do";  //支付宝网关地址
    private String appId="2021000147687004";  //   支付宝分配给开发者的应用ID
    //应用私钥
    private String privateKey = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCE+HWu6Bg3OEHnssXX2zvaBn02JNJchfkobP/8YIboW/a+NIEhmFIN66BobnoQ//w5Di3+CM9pG3ja9ZAYy+d9AVmp0NXlf+IgVFcuZjv5xoWN6Peh+4EFYVug66s2l30J3jmYARC8iQSKAogLhcekjoZR2wDTvnOhX0iO4yVQZT5UgBUdGMIO6TNb1BFcFw/sU247MJWZuTg95fyAeoXs3hPa7xm8raf9AmioC6bBNcV3nrvQhc2EhdGwPF4yvRzFUgQEA35/Sqy6Qb1if1bfWidQar8OVrK3rUiHBWECg2TlvCF6fs3cLrGIo/ZVBLTe4CfUqwwh4qz0prt3c9iTAgMBAAECggEAATN9XKccEOOhNMaYouqID+AfIW4TuGI9bfAOH+l/TnoGMJGo2PTXSOjn+gmFcMHfNWS7ZaAOv5RP8ypkstSksqxgcvg/9mLBWClq4DBsOkVgz3dbRUtQTwwiF8IJOeUEbCJlwsm/rPRP5i8/q1r6ArKG5BZmW4NM6BhEP4YtwBwmpCYznTt1wpyVQqGnsjzz1MTKiFlhlveraFwogPTUnaQ2ux46DY2tsx+lzeXTfjD47tdsQEYEOG04BU9LNC5Z53P0+lYzBU7gW++8fsLUgGRacNpWCnuXzswmMVZR9aoGGTRVH6Q+HNcGD8zKcBvkHhLs8gZGoW2a/ShGjdcLAQKBgQDX1x7LPNEoslcFwZP/ak+oisZsk9pZhQS9dd6iXK03iBGh8HCP3bnQKz8k61TzFvmf5lhMFIxwiJOZXoVLPEldYzM2GnWCKkYJqsrUGrKPoUYFYcK3EKKboURVSAxyIBZCAslUfi+tkRDXeQ/AXzuE32nhu2SPF6pWI2RZ29b5GQKBgQCdthi+A0ZOFl5XdKpOh/whcpQ6PdxQr9YMEN13EDKuMIxIsP/DDAqnCkLdA8rbcPPgdXISdc3oCSlgvUE6+T05afbVkbG5Fe1t879fjUfocZnTkqUM03u/kT1HXCfwnvlY14TSL5w0lQDr3em6wp3YqWOrve+3+ozqYSUZxipYiwKBgCkQx84ZSx44GDCDLRpaar+Bb9E2rEN9+HHnnC09/sLjA5/Q9EmbAQBnSgNHNwltJJ9UnxYSjsnWYma6wheXZ8n51sODczoJuvuZ4C9MyQ5ku8LIX9ietKFxpGOpapzIGBQFHbMSsxWN1St54BJUzCkyrJ2DHtXOxdzPrQcfuZ35AoGAKLb1BkDkKo6dxmgo+2Mh1SS2XZ7QvMfYM387HVzCKLQB01ZWgO0f5uXAQzySZzpuy1imnVtqT+fllcLmDyp/As53WcVmI0kk5YjStAFOOhTxyGwIThpezSLatgx1hyoTZzskX/rIkc6R0VKV35AUnfWE9LZxJ4b4UB3KLTA6iGcCgYB5c6+SBgt0ddbxMPbMippzkyUe6neE86e9ZTkNKS0Zb+px8WjTpkIK4UPJPNaqHc6XoFAzeOEH4c28tXUHrk/fZVm/ydiRRpMixjqYinlOxWquVGfySInY4v77k/gklvgFbFbYcn2WUg8n+FxICoc2giOWrfjOMbsoUsgZlYj4Cw==";
    //支付宝公钥
    private String alipayPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA1mUuQpVrZVY6cjCovgmNIgRdK2bW6Yer6fc/Gps6vgOu9ESl4pLf6u6KIgXCxwhgXcsBxFS9rxiIL0oOOVrtJehT0o/kNjwCqFvDOZyel7DPvF2/kQSQ0cRh9GpCpE/4O7bWtj5aZwvLtmvTpTHBBkI0Z/KdhB708vVew4YpKER0mJNNCnkgQBZX4yll+RGlMhx/eVBviNstuj3WdSJNJRcign0zkdUi1GFrBHwKh5qrGWguyrMQVgB5ORFYNCl7VaEyF8lIt5o/CakKXjJUjVVhofip8U7enqDipSdpAtFr6dkcfBvMqbslf26lAbdwUbO6NMfAhlOOF3BZwI9TSwIDAQAB";

    //买家账号：rsxoec3767@sandbox.com
    //登录密码：111111
    //支付密码：111111


    //支付方法
    public String pay(String orderNum,double totalPrice,String food_name){
        //初始化SDK
        AlipayClient alipayClient = new DefaultAlipayClient(serverUrl, appId, privateKey,
                "json","UTF-8", alipayPublicKey,"RSA2");
        //构造请求参数以调用接口
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        request.setNotifyUrl("");
        //回调地址，支付成功后跳转到哪
        request.setReturnUrl("http://localhost:8080/return");
        JSONObject bizContent = new JSONObject();
        bizContent.put("out_trade_no", orderNum);  //订单编号
        bizContent.put("total_amount", totalPrice); //订单总金额
        bizContent.put("subject", food_name); //商品标题，自定义可以使用商品名
        //销售产品码，与支付宝签约的产品码名称。注：目前电脑支付场景下仅支持FAST_INSTANT_TRADE_PAY
        bizContent.put("product_code", "FAST_INSTANT_TRADE_PAY");

        request.setBizContent(bizContent.toString());
        AlipayTradePagePayResponse response = null;
        String form = null;  //响应的结果是一个表单
        try {
            //调用接口
            response = alipayClient.pageExecute(request);
            form = response.getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        if(response.isSuccess()){
            System.out.println("调用成功");
        } else {
            System.out.println("调用失败");
        }
        return form;
    }
}