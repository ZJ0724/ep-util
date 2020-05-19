# epUtil



## 端口设置

默认端口为：`8001`

修改端口：修改  `config` 文件下的 `application.properties` 文件

```properties
 # 端口号
 server.port=8002
```



## 配置文件设置

默认配置文件路径

```bash
~/.epUtil/config
```


config默认配置

```properties
# SWGD数据库配置
 SWGD.url=192.168.130.216
SWGD.port=1521
SWGD.sid=testeport
SWGD.username=devtester
SWGD.password=easytester

# KSDDB数据库配置
KSDDB.url=192.168.131.50
KSDDB.port=1521
KSDDB.sid=testksd
KSDDB.username=devtester
KSDDB.password=easytester

# SFTP-83配置
sftp83.url=192.168.120.83
sftp83.port=22
sftp83.username=gccoper
sftp83.password=gccoper
sftp83.uploadPath=/gcchome/winx/cus/cfg_c2e
```



## 启动

+ **windows**

  双击 `bin` 文件夹下的 `run.bat` 即可启动。

+ **linux**

  启动
  
  ```sh
  cd bin
  ./run.sh
  ```
  
  停止
  
  ```sh
  cd bin
  ./stop.sh
  ```
  
  