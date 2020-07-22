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
            },
            {
                path: "disposableUpload.html",
                name: "一次性上传回执"
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
            },
            {
                path: "disposableUpload.html",
                name: "一次性上传回执"
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
            }
        ]
    },

    {
        path: "config.html",
        name: "配置"
    }

]