window.epUtil = {
    entity: {
        DTO: {},
        VO: {}
    },

    component: {},

    api: {
        service: {},

        websocket: {}
    },

    util: {}
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
document.write("<script src=\"/component/routeNavigationComponent.js\"></script>");
document.write("<script src=\"/component/configComponent.js\"></script>");

// 导入entity
document.write("<script src=\"/entity/VO/AbstractVO.js\"></script>");
document.write("<script src=\"/entity/DTO/AbstractDTO.js\"></script>");
document.write("<script src=\"/entity/DTO/CusResultDTO.js\"></script>");
document.write("<script src=\"/entity/Response.js\"></script>");
document.write("<script src=\"/entity/router.js\"></script>");
document.write("<script src=\"/entity/VO/SWGDConfigVO.js\"></script>");
document.write("<script src=\"/entity/DTO/SWGDConfigDTO.js\"></script>");
document.write("<script src=\"/entity/DTO/Sftp83ConfigDTO.js\"></script>");
document.write("<script src=\"/entity/VO/Sftp83ConfigVO.js\"></script>");
document.write("<script src=\"/entity/VO/DaKaConfigVO.js\"></script>");
document.write("<script src=\"/entity/DTO/DaKaConfigDTO.js\"></script>");
document.write("<script src=\"/entity/VO/CusMessageComparisonVO.js\"></script>");

// 导入api
document.write("<script src=\"/api/service/baseServiceApi.js\"></script>");
document.write("<script src=\"/api/service/cusResultServiceApi.js\"></script>");
document.write("<script src=\"/api/websocket/BaseWebsocketApi.js\"></script>");
document.write("<script src=\"/api/service/configServiceApi.js\"></script>");
document.write("<script src=\"/api/service/cusMessageServiceApi.js\"></script>");
document.write("<script src=\"/api/websocket/FormCusMessageComparisonWebsocketApi.js\"></script>");

// 导入util
document.write("<script src=\"/util/documentUtil.js\"></script>");