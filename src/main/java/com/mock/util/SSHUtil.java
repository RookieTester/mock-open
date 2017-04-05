package com.mock.util;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by Administrator on 2017/3/20.
 */
public class SSHUtil {
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    private String host;

    private String user;

    private String password;

    private String command;

    public SSHUtil(String command) throws IOException {
        Resource resource=new ClassPathResource("mock.properties");
        Properties props = PropertiesLoaderUtils.loadProperties(resource);
        this.host=props.getProperty("host");
        this.user=props.getProperty("user");
        this.password=props.getProperty("password");
        this.command=command;
        connect();
    }

    public void connect(){
        Connection conn = new Connection(host);
        LOG.info("登录Linux："+host+"建立连接中...");
        Session ssh = null;
        try {
            //连接到主机
            conn.connect();
            //使用用户名和密码校验
            boolean isconn = conn.authenticateWithPassword(user, password);
            if (!isconn) {
                LOG.error("登录Linux："+host+"用户名或密码不正确！");
            } else {
                LOG.info("登录Linux："+host+"连接成功！");
                ssh = conn.openSession();
                ssh.execCommand(command);
                LOG.info(command);

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



    }
}
