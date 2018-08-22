package com.example.demo1.controller;

import com.example.demo1.utils.HttpUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * 对接阿里云物流接口
 *
 * <p>
 * Created by xiangjiangcheng on 2018/8/21 14:48.
 */

@RestController
@RequestMapping("/kdwl")
public class KdwlController {

    /**
     快递物流查询接口
     商品购买地址：https://market.aliyun.com/products/56928004/cmapi022273.html
     String host = "http://kdwlcxf.market.alicloudapi.com"; //服务器
     String path = "/kdwlcx"; //接口地址
     */
    @GetMapping("/q_wl_info")
    public String queryWlInfo() {

        // String host = "http://kdwlcxf.market.alicloudapi.com";
        // String path = "/kdwlcx";

        // String host = "https://wdexpress.market.alicloudapi.com";
        // String path = "/gxali";

        String host = "https://wuliu.market.alicloudapi.com";
        String path = "/kdi";

        String method = "GET";
        System.out.println("请先替换成自己的AppCode");
        String appcode = "fadde964aead4dcabfbb7dfac600c44f";  // !!! 替换这里填写你自己的AppCode 请在买家中心查看
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Authorization", "APPCODE " + appcode); //格式为:Authorization:APPCODE 83359fd73fe11248385f570e3c139xxx
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("no", "801132164062135036");  // !!! 请求参数
        // querys.put("type", "zto");// !!! 请求参数

        String returnStr = "物流信息：";
        try {
            HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
            //System.out.println(response.toString()); //输出头部

            returnStr = EntityUtils.toString(response.getEntity());
            System.out.println(returnStr); //输出json
        } catch (Exception e) {
            e.printStackTrace();
        }

        return returnStr;
    }
}
