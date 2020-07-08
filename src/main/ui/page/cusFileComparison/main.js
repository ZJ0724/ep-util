window.epUtil = {
    api: {
        service: {},
        websocket: {}
    },
    component: {},
    entity: {}
};

// 基础样式
document.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"/style/base.css\" />");

// layui
document.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"/frame/layui/css/layui.css\" />");
document.write("<script src=\"/frame/layui/layui.all.js\"></script>");

// api
// service
document.write("<script src=\"/page/cusFileComparison/api/service/baseServiceApi.js\"></script>");
document.write("<script src=\"/page/cusFileComparison/api/service/cusFileServiceApi.js\"></script>");
// websocket
document.write("<script src=\"/page/cusFileComparison/api/websocket/BaseWebsocketApi.js\"></script>");
document.write("<script src=\"/page/cusFileComparison/api/websocket/CusFileComparisonWebsocketApi.js\"></script>");

// component
document.write("<script src=\"/page/cusFileComparison/component/alert.js\"></script>");

// entity
document.write("<script src=\"/page/cusFileComparison/entity/Response.js\"></script>");