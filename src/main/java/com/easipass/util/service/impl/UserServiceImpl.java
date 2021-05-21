package com.easipass.util.service.impl;

import com.easipass.util.service.UserService;
import com.easipass.util.util.ChromeDriverUtil;
import com.zj0724.common.exception.InfoException;
import com.zj0724.common.util.MapUtil;
import com.zj0724.common.util.StringUtil;
import com.zj0724.uiAuto.Selector;
import com.zj0724.uiAuto.WebDriver;
import com.zj0724.uiAuto.WebElement;
import org.openqa.selenium.Keys;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public final class UserServiceImpl implements UserService {

    @Override
    public void addUser(Map<String, Object> data) {
        if (data == null) {
            throw new InfoException("参数缺失");
        }
        // 用户名
        String username = MapUtil.getValue(data, "username", String.class);
        // 十位编码
        String tradeCode = MapUtil.getValue(data, "tradeCode", String.class);

        if (StringUtil.isEmpty(username)) {
            throw new InfoException("用户名不能为空");
        }
        if (StringUtil.isEmpty(tradeCode)) {
            throw new InfoException("十位编码不能为空");
        }

        WebDriver chromeDriver = ChromeDriverUtil.getChromeDriver();

        try {
            chromeDriver.open("http://192.168.120.40/uumm/#/login");
            chromeDriver.findElement(Selector.byCssSelector("#app > div.home > section.home-banner > div > div.login-block > div > form > div:nth-child(1) > div > div > input")).sendKey("rnlin");
            chromeDriver.findElement(Selector.byCssSelector("#app > div.home > section.home-banner > div > div.login-block > div > form > div:nth-child(2) > div > div > input")).sendKey("888888a");
            chromeDriver.findElement(Selector.byCssSelector("#app > div.home > section.home-banner > div > div.login-block > div > form > div:nth-child(3) > div > div > div:nth-child(1) > div > input")).sendKey("a");
            chromeDriver.findElement(Selector.byCssSelector("#app > div.home > section.home-banner > div > div.login-block > div > div:nth-child(3) > button")).click();
            chromeDriver.display(Selector.byCssSelector("#app > div:nth-child(1) > div.panel-main > div.panel-main-content > div.ep-row > div.resetw-col7.ep-col.ep-col-7 > div > div.title"));
            chromeDriver.open("http://192.168.120.40/uumm/#/chunk/ossUser/%E6%96%B0%E5%A2%9E%E7%BD%91%E7%AB%99%E7%94%A8%E6%88%B7?id=%E6%96%B0%E5%A2%9E%E7%BD%91%E7%AB%99%E7%94%A8%E6%88%B7");
            WebElement usernameE = chromeDriver.findElement(Selector.byCssSelector("#app > div:nth-child(1) > div.panel-main > div.panel-main-content > div > div > div.uumm-base-cv-main > div > div:nth-child(4) > form > div:nth-child(1) > div:nth-child(1) > div > div > div > div.ep-input.ep-input--mini > input"));
            usernameE.sendKeyByJs(username);
            usernameE.event("input");
            WebElement nameE = chromeDriver.findElement(Selector.byCssSelector("#app > div:nth-child(1) > div.panel-main > div.panel-main-content > div > div > div.uumm-base-cv-main > div > div:nth-child(4) > form > div:nth-child(1) > div:nth-child(2) > div > div > div > div.ep-input.ep-input--mini > input"));
            nameE.sendKeyByJs(username);
            nameE.event("input");
            chromeDriver.findElement(Selector.byCssSelector("#app > div:nth-child(1) > div.panel-main > div.panel-main-content > div > div > div.uumm-base-cv-main > div > div:nth-child(4) > form > div:nth-child(1) > div:nth-child(4) > div > div > div > div > input")).click();
            chromeDriver.display(Selector.byText("Oper [操作员]")).click();
            chromeDriver.findElement(Selector.byCssSelector("#app > div:nth-child(1) > div.panel-main > div.panel-main-content > div > div > div.uumm-base-cv-main > div > div:nth-child(4) > form > div:nth-child(1) > div:nth-child(5) > div > div > div.ep-col.ep-col-22 > div > div > input")).sendKey("729391077");
            chromeDriver.await(3000);
            chromeDriver.findElement(Selector.byCssSelector("#app > div:nth-child(1) > div.panel-main > div.panel-main-content > div > div > div.uumm-base-cv-main > div > div:nth-child(4) > form > div:nth-child(1) > div:nth-child(5) > div > div > div.ep-col.ep-col-22 > div > div > input")).sendKey(Keys.ENTER);
            chromeDriver.findElement(Selector.byCssSelector("#app > div:nth-child(1) > div.panel-main > div.panel-main-content > div > div > div.uumm-base-cv-main > div > button:nth-child(1)")).click();
            chromeDriver.findElement(Selector.byCssSelector("#app > div:nth-child(1) > div.panel-main > div.panel-main-content > div > div > div:nth-child(3) > div > div.uumm-base-cv-main > div > div > div.ep-tabs-contents > div > div > div > div.ep-row > div > button.ep-button.ep-button--primary.ep-button--small")).click();
            WebElement keyE = chromeDriver.findElement(Selector.byCssSelector("#app > div:nth-child(1) > div.panel-main > div.panel-main-content > div > div > div:nth-child(3) > div > div.uumm-base-cv-main > div > div > div.ep-tabs-contents > div > div > div > div.ep-model-wrapper.ep-modal-wrapper > div > div.ep-model-body.ep-modal-body > div.panel-main-content > div > div > div.uumm-base-cv-main > div.block > form > div > div > div > div > div > div.ep-input.ep-input--mini > input"));
            keyE.click();
            keyE.sendKey("SWGD[信天翁]");
            chromeDriver.await(1000);
            keyE.sendKey(Keys.ENTER);
            chromeDriver.findElement(Selector.byCssSelector("#app > div:nth-child(1) > div.panel-main > div.panel-main-content > div > div > div:nth-child(3) > div > div.uumm-base-cv-main > div > div > div.ep-tabs-contents > div > div > div > div.ep-model-wrapper.ep-modal-wrapper > div > div.ep-model-body.ep-modal-body > div.panel-main-content > div > div > div.uumm-base-cv-main > div:nth-child(5) > div > div.jsoneditor-vue > div > div.jsoneditor-menu > div > button")).click();
            chromeDriver.findElement(Selector.byCssSelector("#app > div:nth-child(1) > div.panel-main > div.panel-main-content > div > div > div:nth-child(3) > div > div.uumm-base-cv-main > div > div > div.ep-tabs-contents > div > div > div > div.ep-model-wrapper.ep-modal-wrapper > div > div.ep-model-body.ep-modal-body > div.panel-main-content > div > div > div.uumm-base-cv-main > div:nth-child(5) > div > div.jsoneditor-vue > div > div.jsoneditor-menu > div > div > div > ul > li:nth-child(5) > button")).click();
            WebElement element = chromeDriver.findElement(Selector.byCssSelector("#app > div:nth-child(1) > div.panel-main > div.panel-main-content > div > div > div:nth-child(3) > div > div.uumm-base-cv-main > div > div > div.ep-tabs-contents > div > div > div > div.ep-model-wrapper.ep-modal-wrapper > div > div.ep-model-body.ep-modal-body > div.panel-main-content > div > div > div.uumm-base-cv-main > div:nth-child(5) > div > div.jsoneditor-vue > div > div.jsoneditor-outer > textarea"));
            element.sendKeyByJs("");
            element.sendKeyByJs("{\"SWGD_WEB_TYPE\": \"1\",\"SWGD_cus_UserName\": \"" + username + "\",\"CustomersCode10\": \"" + tradeCode + "\",\"SWGD_cus_VersionType\": \"1\"}");
            element.event("input");
            chromeDriver.findElement(Selector.byCssSelector("#app > div:nth-child(1) > div.panel-main > div.panel-main-content > div > div > div:nth-child(3) > div > div.uumm-base-cv-main > div > div > div.ep-tabs-contents > div > div > div > div.ep-model-wrapper.ep-modal-wrapper > div > div.ep-model-body.ep-modal-body > div.panel-main-content > div > div > div.uumm-base-cv-main > div:nth-child(5) > div > div.jsoneditor-btns > button")).click();
            chromeDriver.display(Selector.byText("添加成功"));
            chromeDriver.open("http://192.168.120.40/uumm/#/chunk/ossUserSearch");
            chromeDriver.findElement(Selector.byCssSelector("#app > div:nth-child(1) > div.panel-main > div.panel-main-content > div.search-card.contents-card.card-margin > div:nth-child(1) > div.uumm-base-cv-main > form > div > div:nth-child(2) > div > div > div > input")).sendKey(username);
            chromeDriver.findElement(Selector.byCssSelector("#app > div:nth-child(1) > div.panel-main > div.panel-main-content > div.search-card.contents-card.card-margin > div:nth-child(1) > div.uumm-base-cv-main > form > div > div:nth-child(9) > div > div > button.ep-button.ep-button--primary.ep-button--small")).click();
            chromeDriver.await(2000);
            try {
                chromeDriver.findElement(Selector.byCssSelector("#app > div:nth-child(1) > div.panel-main > div.panel-main-content > div.search-card.contents-card.card-margin > div:nth-child(2) > div.uumm-base-cv-main > div:nth-child(2) > div > div.ep-table-fixed.ep-table-fixed-right > div.ep-table-body > table > tbody > tr > td.eptb_2_column_18 > button.ep-button.ep-button--warning.ep-button--mini")).click();
            } catch (Exception e) {
                chromeDriver.findElement(Selector.byCssSelector("#app > div:nth-child(1) > div.panel-main > div.panel-main-content > div.search-card.contents-card.card-margin > div:nth-child(2) > div.uumm-base-cv-main > div:nth-child(2) > div > div.ep-table-fixed.ep-table-fixed-right > div.ep-table-body > table > tbody > tr > td.eptb_1_column_11 > button.ep-button.ep-button--warning.ep-button--mini")).click();
            }
            chromeDriver.findElement(Selector.byCssSelector("#app > div:nth-child(1) > div.panel-main > div.panel-main-content > div.ep-model-wrapper.ep-modal-wrapper > div > div.ep-model-body.ep-modal-body > div > div > form > div.ep-form--item.is-required.ep-form--size--mini > div > div > div > input")).sendKey("888888a");
            chromeDriver.findElement(Selector.byCssSelector("#app > div:nth-child(1) > div.panel-main > div.panel-main-content > div.ep-model-wrapper.ep-modal-wrapper > div > div.ep-model-body.ep-modal-body > div > div > form > div:nth-child(4) > div > div > div > input")).sendKey("888888a");
            chromeDriver.findElement(Selector.byCssSelector("#app > div:nth-child(1) > div.panel-main > div.panel-main-content > div.ep-model-wrapper.ep-modal-wrapper > div > div.ep-model-footer.ep-modal-footer > div > button.ep-button.ep-button--primary.ep-button--mini")).click();
        } finally {
            chromeDriver.close();
        }
    }

}