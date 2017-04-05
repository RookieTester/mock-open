package com.mock.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


/**
 * Created by Administrator on 2017/1/17.
 */
public class MockUtil {
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    private static String hostname;

    private static String username;

    private static String password;

    private MockUtil() throws IOException {
        Resource resource=new ClassPathResource("mock.properties");
        Properties props = PropertiesLoaderUtils.loadProperties(resource);
        this.hostname=props.getProperty("host");
        this.username=props.getProperty("user");
        this.password=props.getProperty("password");
    }

    public static void main(String[] args){

    }

    /**
     *
     * @author zdm
     * @param url Nginx location模块匹配的部分
     * @param json 需要模拟的json数据
     * @param protoType 协议类型，0代表http，1代表https
     * @param statusSwitch 状态码开关，0代表默认返回200状态码，1代表定制状态码
     * @param statusValue 状态码值
     * @return
     */
    public static Map<String,String> configNginx(String url, String json, String protoType, int statusSwitch, Integer statusValue){

        int proto=0;//协议默认是http
        String command="";
        String fileName = "";

        if (protoType.equals("http")||protoType.equals("HTTP")){

        }else if (protoType.equals("https")||protoType.equals("HTTPS")){
            proto=0;
        }else {
            throw new IllegalArgumentException("暂不支持该类型协议的URL，请使用http/https！");
        }

        fileName=produceFileName();
        command="cd /etc/nginx/conf.d/mock/shell;bash -x auto_mock.sh "+
                fileName+" " + url + " '" + json + "' "+ proto +" "+statusSwitch+" "+statusValue+
                ";service nginx reload";

        SSHUtil sshUtil=new SSHUtil(hostname,username,password,command);

        Map<String, String> mapRequest = new HashMap<String, String>();
        mapRequest.put("fileName",fileName);
        return mapRequest;
    }

    public static String produceFileName(){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        return sdf.format(new Date());
    }

    public static void updateJson(String fileName,String json){
        String command="cd /etc/nginx/conf.d/mock/shell;bash -x change_json.sh "+fileName+" '"+json+"'";
        SSHUtil sshUtil=new SSHUtil(hostname,username,password,command);
    }

    public static void updateDomain(String domain){
        String command="cd /etc/nginx/conf.d/mock/shell;bash -x auto_domain.sh "+domain;
        SSHUtil sshUtil=new SSHUtil(hostname,username,password,command);
    }

    public static void deleteMock(String proto,String fileName){
        String command="";

        if (proto.equals("http")){
            command="rm -f /etc/nginx/conf.d/mock/http/"+fileName+".conf;rm -f /usr/share/nginx/html/"+fileName+".json";
        }else if (proto.equals("https")){
            command="rm -f /etc/nginx/conf.d/mock/https/"+fileName+".conf;rm -f /usr/share/nginx/html/"+fileName+".json";
        }else {
            throw new IllegalArgumentException("参数错误");
        }

        SSHUtil sshUtil=new SSHUtil(hostname,username,password,command);
    }

}
