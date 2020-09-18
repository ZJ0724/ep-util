export default [

    {
        path: "/",
        name: "首页"
    },
    {
        path: "formCusResult/",
        name: "报关单回执",
        children: [
            {
                path: "uploadTongXun.html",
                name: "上传通讯回执"
            },
            {
                path: "uploadYeWu.html",
                name: "上传业务回执"
            }
        ]
    },

    {
        path: "decModCusResult/",
        name: "修撤单回执",
        children: [
            {
                path: "uploadQP.html",
                name: "上传QP回执"
            },
            {
                path: "uploadYeWu.html",
                name: "上传业务回执"
            }
        ]
    },

    {
        path: "agentCusResult/",
        name: "代理委托回执",
        children: [
            {
                path: "upload.html",
                name: "上传回执"
            }
        ]
    },

    {
        path: "cusMessageComparison/",
        name: "报文比对",
        children: [
            {
                path: "formCusMessage.html",
                name: "报关单报文"
            },
            {
                path: "decModCusMessage.html",
                name: "修撤单报文"
            }
        ]
    },

    {
        path: "supplySend/",
        name: "第三方发送",
        children: [
            {
                path: "sendCusFile.html",
                name: "发送报文"
            }
        ]
    },

    {
        path: "paramDb/",
        name: "参数库(调试中，暂不可用)",
        children: [
            {
                path: "importParamDbComparison.html",
                name: "导入比对"
            },
            {
                path: "exportParamDbComparison.html",
                name: "导出比对"
            },

            {
                path: "excelImportParamDbComparison.html",
                name: "excel导入比对"
            }
        ]
    },

    {
        path: "config.html",
        name: "配置"
    }

]