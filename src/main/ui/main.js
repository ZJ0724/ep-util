window.epUtil = {
    entity: {
        DTO: {}
    },

    component: {},

    api: {
        service: {},

        websocket: {}
    }
};

// 基础样式
document.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"/style/base.css\" />");

// layui
document.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"/frame/layui/css/layui.css\" />");
document.write("<script src=\"/frame/layui/layui.all.js\"></script>");

// jquery
document.write("<script src=\"/frame/jquery-3.5.1.min.js\"></script>");

// bootstrap
document.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"/frame/bootstrap-3.3.7-dist/css/bootstrap.css\" />");
document.write("<script src=\"/frame/bootstrap-3.3.7-dist/js/bootstrap.js\"></script>");

// vue
document.write("<script src=\"/frame/vue.min.js\"></script>");

// 导入component
document.write("<script src=\"/component/navigationComponent.js\"></script>");
document.write("<script src=\"/component/alertComponent.js\"></script>");
document.write("<script src=\"/component/cusResultUploadComponent.js\"></script>");

// 导入entity
document.write("<script src=\"/entity/DTO/CusResultDTO.js\"></script>");
document.write("<script src=\"/entity/Response.js\"></script>");

// 导入api
document.write("<script src=\"/api/service/baseServiceApi.js\"></script>");
document.write("<script src=\"/api/service/cusResultServiceApi.js\"></script>");
document.write("<script src=\"/api/websocket/BaseWebsocketApi.js\"></script>");