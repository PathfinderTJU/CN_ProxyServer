### 测试方法

使用浏览器配置代理服务器，并访问站点（GET方法）配制方法：打开浏览器的代理服务器配置，配置本地IP地址以及设定的代理服务器端口号。

![1556068581583](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\1556068581583.png)

因为一些网站采用了SSL加密的HTTP协议，而代码中没有实现，因此无法访问一些	使用了HTTPS加密的站点进行测试。如下图所示

![1556068590007](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\1556068590007.png)

- 目前已知的可行测试网站：
  - 天大内网：物理实验选课系统（211.81.54.2）
  - 外网：www.baidu.com