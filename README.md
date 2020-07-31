# ep-util



## 端口设置

默认端口为：`8002`

修改端口：修改  `config` 文件下的 `application.properties` 文件

```properties
 # 端口号
 server.port = 8002
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
  



## 访问

`地址`:`端口号`
例：http://localhost:8002