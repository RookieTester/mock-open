package com.mock.util;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/1/17.
 */
public class MockUtil {
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    public static void main(String[] args){
        //produceFileName();
        //configNginx("/test","www","http",1,1,404);
        boolean result=portIsFree(12320);
        System.out.println(result);
    }

    /**
     *
     * @author zdm
     * @param url Nginx location模块匹配的部分
     * @param json 需要模拟的json数据
     * @param protoType 协议类型，0代表http，1代表https
     * @param bind 参数连接类型，0代表标准的&符号连接，1代表公司内部使用的-符号连接
     * @param statusSwitch 状态码开关，0代表默认返回200状态码，1代表定制状态码
     * @param statusValue 状态码值
     * @return
     */
    public static Map<String,String> configNginx(String url, String json, String protoType, int bind, int statusSwitch, Integer statusValue){
        int proto=0;//协议默认是http
        String command="";
        String hostname = "10.168.66.226";
        String username = "root";
        String password = "qichezhijia)(*&";
        String fileName = "";

        if (protoType.equals("http")||protoType.equals("HTTP")){

        }else if (protoType.equals("https")||protoType.equals("HTTPS")){
            proto=1;
        }else {
            throw new IllegalArgumentException("暂不支持该类型协议的URL，请使用http/https！");
        }

        Connection conn = new Connection(hostname);
        Session ssh = null;
        try {
            //连接到主机
            conn.connect();
            //使用用户名和密码校验
            boolean isconn = conn.authenticateWithPassword(username, password);
            if (!isconn) {
                System.out.println("用户名或密码不正确");
            } else {
                System.out.println("连接OK");

                //mock的json内容存储在一个新文件中，需要文件名唯一
                fileName=produceFileName();

                ssh = conn.openSession();
                //使用多个命令用分号隔开
                //只允许使用一行命令，即ssh对象只能使用一次execCommand这个方法，多次使用则会出现异常
                if (bind==0){
                    command="cd /etc/nginx/conf.d/mock/shell;bash -x auto_mock.sh "+
                            fileName+" " + url + " '" + json + "' "+ proto +" "+ bind +" "+statusSwitch+" "+statusValue+
                            ";service nginx reload";
                }else if (bind==1){
                    command="cd /etc/nginx/conf.d/mock/shell;bash -x auto_mock.sh "+
                            fileName+" " + url + " '" + json + "' "+ proto +" "+ bind +" "+statusSwitch+" "+statusValue+
                            ";service nginx reload";
                }else {
                    throw new IllegalArgumentException("bind参数值异常："+bind);
                }
                ssh.execCommand(command);
                System.out.println(command);
                //将屏幕上的文字全部打印出来
                InputStream is = new StreamGobbler(ssh.getStdout());
                BufferedReader brs = new BufferedReader(new InputStreamReader(is));
                while (true) {
                    String line = brs.readLine();
                    if (line == null) {
                        break;
                    }
                    System.out.println(line);
                }
            }
            //连接的Session和Connection对象都需要关闭
            ssh.close();
            conn.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Map<String, String> mapRequest = new HashMap<String, String>();
        mapRequest.put("fileName",fileName);
        return mapRequest;
    }

    public static String produceFileName(){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        //System.out.println(sdf.format(new Date()));
        return sdf.format(new Date());
    }

    public static boolean portIsFree(int port){
        boolean result=false;
        String hostname = "10.168.66.226";
        String username = "root";
        String password = "qichezhijia)(*&";

        Connection conn = new Connection(hostname);
        Session ssh = null;
        try {
            //连接到主机
            conn.connect();
            //使用用户名和密码校验
            boolean isconn = conn.authenticateWithPassword(username, password);
            if (!isconn) {
                System.out.println("用户名或密码不正确");
            } else {
                System.out.println("连接OK");

                ssh = conn.openSession();
                //使用多个命令用分号隔开
                //只允许使用一行命令，即ssh对象只能使用一次execCommand这个方法，多次使用则会出现异常
                String command="netstat -apn | grep "+port;
                ssh.execCommand(command);
                System.out.println("命令："+command);

                //将屏幕上的文字全部打印出来
                InputStream is = new StreamGobbler(ssh.getStdout());
                BufferedReader brs = new BufferedReader(new InputStreamReader(is));

                String ret="";
                while (true) {
                    String line = brs.readLine();
                    if (line == null) {
                        break;
                    }
                    ret=ret+line;
                }
                if (ret.length()==0)
                    result=true;
                System.out.println("结果："+ret);
            }
            //连接的Session和Connection对象都需要关闭
            ssh.close();
            conn.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void updateJson(String fileName,String json){

        String hostname = "10.168.66.226";
        String username = "root";
        String password = "qichezhijia)(*&";

        Connection conn = new Connection(hostname);
        Session ssh = null;
        try {
            //连接到主机
            conn.connect();
            //使用用户名和密码校验
            boolean isconn = conn.authenticateWithPassword(username, password);
            if (!isconn) {
                System.out.println("用户名或密码不正确");
            } else {
                System.out.println("连接OK");

                ssh = conn.openSession();
                //使用多个命令用分号隔开
                //只允许使用一行命令，即ssh对象只能使用一次execCommand这个方法，多次使用则会出现异常
                String command="cd /etc/nginx/conf.d/mock/shell;bash -x change_json.sh "+fileName+" '"+json+"'";
                ssh.execCommand(command);
                System.out.println("命令："+command);

                //将屏幕上的文字全部打印出来
                InputStream is = new StreamGobbler(ssh.getStdout());
                BufferedReader brs = new BufferedReader(new InputStreamReader(is));
                while (true) {
                    String line = brs.readLine();
                    if (line == null) {
                        break;
                    }
                    System.out.println("结果："+line);
                }
            }
            //连接的Session和Connection对象都需要关闭
            ssh.close();
            conn.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void updateDomain(String domain){

        String hostname = "10.168.66.226";
        String username = "root";
        String password = "qichezhijia)(*&";

        Connection conn = new Connection(hostname);
        Session ssh = null;
        try {
            //连接到主机
            conn.connect();
            //使用用户名和密码校验
            boolean isconn = conn.authenticateWithPassword(username, password);
            if (!isconn) {
                System.out.println("用户名或密码不正确");
            } else {
                System.out.println("连接OK");

                ssh = conn.openSession();
                //使用多个命令用分号隔开
                //只允许使用一行命令，即ssh对象只能使用一次execCommand这个方法，多次使用则会出现异常
                String command="cd /etc/nginx/conf.d/mock/shell;bash -x auto_domain.sh "+domain;
                ssh.execCommand(command);
                System.out.println("命令："+command);
            }
            //连接的Session和Connection对象都需要关闭
            ssh.close();
            conn.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deleteMock(String proto,String fileName){

        String hostname = "10.168.66.226";
        String username = "root";
        String password = "qichezhijia)(*&";
        String command="";

        Connection conn = new Connection(hostname);
        Session ssh = null;
        try {
            //连接到主机
            conn.connect();
            //使用用户名和密码校验
            boolean isconn = conn.authenticateWithPassword(username, password);
            if (!isconn) {
                System.out.println("用户名或密码不正确");
            } else {
                System.out.println("连接OK");

                ssh = conn.openSession();
                //使用多个命令用分号隔开
                //只允许使用一行命令，即ssh对象只能使用一次execCommand这个方法，多次使用则会出现异常
                if (proto.equals("http")){
                    command="rm -f /etc/nginx/conf.d/mock/http/"+fileName+".conf;rm -f /usr/share/nginx/html/"+fileName+".json";
                }else if (proto.equals("https")){
                    command="rm -f /etc/nginx/conf.d/mock/https/"+fileName+".conf;rm -f /usr/share/nginx/html/"+fileName+".json";
                }else {
                    throw new IllegalArgumentException("参数错误");
                }

                ssh.execCommand(command);
                System.out.println("命令："+command);
            }
            //连接的Session和Connection对象都需要关闭
            ssh.close();
            conn.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
