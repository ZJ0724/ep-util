package com.easipass.util.entity.resources.cusResult;

import com.easipass.util.entity.Resource;
/**
 * 代理委托回执资源
 *
 * @author ZJ
 * */
public final class AgentCusResultResource extends Resource {

    /**
     * 单例
     * */
    private static final AgentCusResultResource AGENT_CUS_RESULT_RESOURCE = new AgentCusResultResource();

    /**
     * 构造函数
     */
    private AgentCusResultResource() {
        super(CUS_RESULT_PATH + "agentCusResult.wts");
    }

    /**
     * 获取单例
     *
     * @return 单例
     * */
    public static AgentCusResultResource getInstance() {
        return AGENT_CUS_RESULT_RESOURCE;
    }

}