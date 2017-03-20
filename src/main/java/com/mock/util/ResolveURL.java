package com.mock.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/1/17.
 */
public class ResolveURL {
    public static Map<String, String> read(String url) {

        Map<String, String> mapRequest = new HashMap<String, String>();
        int firSplit = url.indexOf("//");
        String proto="";
        try{
            proto = url.substring(0, firSplit - 1);
        }catch (Exception e){
            proto = "http";
            firSplit=-2;
        }

        int webSplit = url.indexOf("/", firSplit + 2);
        int portIndex = url.indexOf(":", firSplit);
        String webUrl = url.substring(firSplit + 2, webSplit);
        String port = "";
        if (portIndex >= 0) {
            webUrl = webUrl.substring(0, webUrl.indexOf(":"));
            port = url.substring(portIndex + 1, webSplit);
            mapRequest.put("port", port);
        }
        String context = url.substring(webSplit);

        if (context.indexOf("?") == -1) {

        } else {
            context = context.substring(0, context.indexOf("?"));
        }

        mapRequest.put("proto", proto);//协议
        mapRequest.put("domain", webUrl);//域名
        mapRequest.put("context", context);//内容（参数等）
        return mapRequest;
    }

    public static void main(String[] args){
        String domain=read("www.x.com/get-a1-b2.json").get("domain");
        System.out.println(domain);
    }
}
