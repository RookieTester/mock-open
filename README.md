# mock开源工具(SSM+Nginx+shell）

## 开发背景
工作中经常出现前端开发和后端开发并行的情况，为了帮助前端开发早些进行调试，我们测试有时会协助提供接口的mock服务。

那么接下来就是mock框架的选择，之前选用的是moco，但是moco的缺点是每个mock服务都需要新起一个服务，也就占用了一个端口，一次性使用还好，如果一次需要制作很多，就会存在不少问题（例如mock机器本身由于启动过多moco带来的性能问题、数据维护不便等）。

于是我就做了这个。

## 使用说明
demo（由于使用的阿里云主机且没有备案，可能会存在问题，建议大家使用自己的服务器）：http://139.224.165.210:8080/mock-open/page/mock

github：https://github.com/RookieTester/mock-open

新增mock在web界面进行操作：

![图片.png](http://upload-images.jianshu.io/upload_images/3071749-01d1887baa2b62d3.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
填写完信息后，点击URL填写框旁边的“确认添加”按钮即可完成。

成功后会弹窗提示，失败也会有相应提示。

成功例子：

![图片.png](http://upload-images.jianshu.io/upload_images/3071749-d9599c48bd8bb38b.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
失败例子：
（何为重复在注意事项里会提到）
![图片.png](http://upload-images.jianshu.io/upload_images/3071749-c5c971336f42b8a6.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

也支持状态码的定制，可以设计该mock接口只返回指定的状态码：
![图片.png](http://upload-images.jianshu.io/upload_images/3071749-f2f0e7ce9638250e.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
成功添加后，可以看到状态码确实是400
![图片.png](http://upload-images.jianshu.io/upload_images/3071749-cb4f3d3577817d77.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

页面的下方是搜索框，用的是jqgrid表格框架，支持按URL和名称查询两种查询方式：
![图片.png](http://upload-images.jianshu.io/upload_images/3071749-80626a7babbf543e.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

## 配置方法
### 环境要求
Linux主机一台（Windows版正在开发，时间嘛就不提了），需要安装Nginx，MySQL和tomcat。MySQL和tomcat可以安装在Windows。

**服务端配置方法：**
<pre>
yum/apt-get install nginx #安装Nginx
cd /etc/nginx/conf.d #使用上述方式安装Nginx的话，这里就是Nginx server模块的配置文件目录
###将下面的内容粘贴进去
###begin
vi default.conf
#
# The default server
#


server {
    listen       80;
    server_name  app.api.com;
    root         /usr/share/nginx/html;
    index        index.html;
    charset      utf-8;
    include /etc/nginx/conf.d/mock/http/*.conf;
}
###end
:wq  #保存并退出

mdkir mock
cd mock
mkdir http
mkdir https
mkdir shell
cd shell
rz auto_domain.sh #rz是上传文件，也可以用别的方式上传，只要是这个位置就行。shell，用于添加虚拟域名
rz auto_mock.sh #shell，用于添加location
rz change_json.sh #shell，用于某个mock更新Json数据
service nginx reload #因为使用上述方法安装Nginx后会自动启动服务，这里只需要reload一下服务即可
</pre>

上面用到的shell可以在源代码下找到，路径为src/main/resouces/shell。

**MySQL配置**

新建一个数据库，名为server，当然你也可以改名字。然后执行src/main/resources/sql/schema.sql里的语句即可创建好需要的表结构。最后修改源代码的数据库连接配置文件jdbc.properties。

**Linux连接设置**

web服务需要连接Nginx所在的Linux服务器，配置文件在src/main/resources/mock.properties

**客户端配置**

把源代码中的配置文件修改好以后，使用IDE（推荐eclipse或idea）将服务部署在tomcat即可。
或者编译完源代码，自己放在tomcat的服务目录（webapps）下启动。

## 注意事项

- 只支持标准RESTful api格式的URL，即形如http://domain.com/v/method?a=2&b=1其它形式的URL可能也支持，也可能不支持，出现奇怪的现象我不负责背锅（捂脸，但是可以帮忙看）；
- 选择状态码模式，就不能填写Json了，这是因为这个工具的架构目前做不到，感觉也不是很有必要就先这样了；
- 去重逻辑:例如http://domain1.com/v/method?a=2&b=1和http://domain2.com/v/method?a=2&b=1这两个接口，由于URL的content部分一样，所以会认为是重复数据（因为Nginx的location语法的限制，我只能这样做）；
- 把接口的域名指向Nginx所在服务器的IP，不是tomcat的，这个要搞清楚。
- 不支持https（因为没有可信的证书做了也是白做，我不可能让开发在代码里加入强制信任证书的逻辑）


## 设计与编码
前端展示使用bootstrap，后端是SSM（SpringMVC+Spring+mybatis）。代码不复杂，前端页面调用ajax向后端发送请求，后端会对mock服务端（一台Linux机器）建立连接并发送命令，该命令会调用Linux主机上的shell脚本，对Nginx的配置文件进行操作，成功后重启Nginx，最后将记录写入数据库。

### **架构图：**
![架构.png](http://upload-images.jianshu.io/upload_images/3071749-4dc83f5dd8519e68.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)