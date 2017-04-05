#mock开源工具(SSM+Nginx+shell）

##开发背景
工作中经常出现前端开发和后端开发并行的情况，为了帮助前端开发早些进行调试，我们测试有时会协助提供接口的mock服务。

那么接下来就是mock框架的选择，之前选用的是moco，但是moco的缺点是每个mock服务都需要新起一个服务，也就占用了一个端口，一次性使用还好，如果一次需要制作很多，就会存在不少问题（例如mock机器本身由于启动过多moco带来的性能问题、数据维护不便等）。

于是我就做了这个。

##使用说明
demo：http://139.224.165.210:8080/mock-open/page/mock

github：https://github.com/RookieTester/mock-open

新增mock在web界面进行操作：

##注意事项



##设计与编码
前端展示使用bootstrap，后端是SSM（SpringMVC+Spring+mybatis）。代码不复杂，前端页面调用ajax向后端发送请求，后端会对mock服务端（一台Linux机器）建立连接并发送命令，该命令会调用Linux主机上的shell脚本，对Nginx的配置文件进行操作，成功后重启Nginx，最后将记录写入数据库。

###**架构图：**
![架构.png](http://upload-images.jianshu.io/upload_images/3071749-4dc83f5dd8519e68.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)