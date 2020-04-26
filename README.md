# EpUtilServer

Ep工具服务

## 端口设置

默认端口为：`8001`

修改端口：修改  `config` 文件下的 `application.properties` 文件

```java
# 端口号
server.port=8001
```

## 响应参数说明

**样例：**

```json
{
	"falg": "",
	"errorCode": "",
	"errorMsg": "",
	"data": ""
}
```

> **参数说明：**
>
> | 节点名 | 说明             |
> | ------ | ---------------- |
> | falg | 响应类型<br /><br />`T`：请求成功<br />`F`：请求失败 |
> | errorCode | 错误代码<br /><br />`500`：后台错误<br />`400`：请求参数有误或缺失 |
> | errorMsg | 错误提示 |
> | data | 返回的数据 |

### 接口说明

#### 上传报关单通讯回执

> **url：**`/formResult/upload/tongXun?ediNo=?`

> **type：**`POST`

>**请求样例：**
> ```json
> {
> 	"channel": "",
> 	"note": ""
> }
> ```

> **参数说明：**
> |节点名|是否必填|说明|
> |----|----|----|
> |ediNo|是|ediNo编号|
> |channel|是|回执类型|
> |note|是|备注|

#### 上传报关单业务回执

> **url：**`/formResult/upload/yeWu?ediNo=?`

> **type：**`POST`

> **请求样例：**
> ```json
> {
> 	"channel": "",
> 	"note": ""
> }
> ```

> **参数说明：**
> |节点名|是否必填|说明|
> |----|----|----|
> |ediNo|是|ediNo编号|
> |channel|是|回执类型|
> |note|是|备注|

#### 一次性上传通讯、业务回执

> 通讯回执默认channel为：0，note为：“通讯回执上传成功”

> **url：**`/formResult/disposableUpload?ediNo=?`

> **type：**`POST`

> **请求样例：**
> ```json
> {
> 	"channel": "",
> 	"note": ""
> }
> ```

> **参数说明：**
> |节点名|是否必填|说明|
> |----|----|----|
> |ediNo|是|ediNo编号|
> |channel|是|业务回执类型|
> |note|是|业务回执备注|