# EpUtilServer

Ep工具服务



## 端口设置

> 默认端口为：`8001`

> 修改端口：修改  `config` 文件下的 `application.properties` 文件
>
> ```properties
> # 端口号
> server.port=8001
> ```
>



## 配置文件设置

默认配置文件路径

```bash
~/.EpUtilServer
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

  双击 `bin` 文件夹下的 `EpUtilServer.run.bat` 即可启动

+ **linux**

  ```sh
  ./bin/EpUtilServer.run.sh
  ```



## 接口文档

### 响应参数说明

样例：

```json
{
	"flag": "",
	"errorCode": "",
	"errorMsg": "",
	"data": ""
}
```

参数说明：

|    节点名     |                      说明                      |
| :----------- | :-------------------------------------------- |
|   flag   |        `T`：请求成功<br />`F`：请求失败        |
| errorCode | `500`：后台错误<br />`400`：请求参数有误或缺失 |
| errorMsg  |                    错误提示                    |
|   data    |                   返回的数据                   |



### 上传报关单通讯回执

**url**：/formResult/upload/tongXun?ediNo=?

**type**：POST

**请求样例：**

```json
{
	"channel": "",
	"note": ""
}
```

**参数说明：**

| 节点名  | 是否必填 | 说明      |
| ------- | -------- | --------- |
| ediNo   | 是       | ediNo编号 |
| channel | 是       | 回执类型  |
| note    | 是       | 备注      |



### 上传报关单业务回执

**url**：/formResult/upload/yeWu?ediNo=?

**type**：POST

请求样例：

```json
{
	"channel": "",
	"note": ""
}
```

**参数说明：**
| 节点名  | 是否必填 | 说明      |
| ------- | -------- | --------- |
| ediNo   | 是       | ediNo编号 |
| channel | 是       | 回执类型  |
| note    | 是       | 备注      |



### 一次性上传通讯、业务回执

> 一次上传的通讯回执默认channel为：0，note为：通讯回执上传成功。

**url**：/formResult/disposableUpload?ediNo=?

**type**：POST

请求样例：

```json
{
	"channel": "",
	"note": ""
}
```

**参数说明：**
| 节点名  | 是否必填 | 说明      |
| ------- | -------- | --------- |
| ediNo   | 是       | ediNo编号 |
| channel | 是       | 回执类型  |
| note    | 是       | 备注      |



### 上传修撤单QP回执

**url**：/decModResult/upload/QP?ediNo=?

**type**：POST

请求样例：

```json
{
	"channel": "",
	"note": ""
}
```

**参数说明：**

| 节点名  | 是否必填 | 说明      |
| ------- | -------- | --------- |
| ediNo   | 是       | ediNo编号 |
| channel | 是       | 回执类型  |
| note    | 是       | 备注      |



### 上传修撤单业务回执

**url**：/decModResult/upload/yeWu?ediNo=?

**type**：POST

请求样例：

```json
{
	"channel": "",
	"note": ""
}
```

**参数说明：**

| 节点名  | 是否必填 | 说明      |
| ------- | -------- | --------- |
| ediNo   | 是       | ediNo编号 |
| channel | 是       | 回执类型  |
| note    | 是       | 备注      |



### 上传代理委托回执

> **url**：/agentResult/upload?ediNo=?
>
> **type**：POST
>
> 请求样例：
>
> ```json
> {
> 	"channel": "",
> 	"note": ""
> }
> ```
>
> 参数说明：
>
> | 节点名  | 是否必填 | 说明      |
> | ------- | -------- | --------- |
> | ediNo   | 是       | ediNo编号 |
> | channel | 是       | 回执类型  |
> | note    | 是       | 备注      |