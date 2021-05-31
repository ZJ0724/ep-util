package com.easipass.util.service;

import com.easipass.util.entity.po.ThirdPartyUrlPO;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface ThirdPartyUrlService {

    /**
     * 获取所有
     *
     * @return ist<ThirdPartyUrlPO>
     * */
    List<ThirdPartyUrlPO> getAll();

    /**
     * 添加
     *
     * @param thirdPartyUrlPO thirdPartyUrlPO
     * */
    void save(ThirdPartyUrlPO thirdPartyUrlPO);

    /**
     * 删除
     *
     * @param id id
     * */
    void delete(Long id);

}