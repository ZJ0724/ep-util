package com.easipass.util.core;

import com.easipass.util.api.websocket.DaKaLogWebsocketApi;
import com.easipass.util.core.Config.DaKaProperties;
import com.easipass.util.core.util.DateUtil;
import com.easipass.util.core.util.ThreadUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.List;

/**
 * 打卡器
 *
 * @author ZJ
 * */
public final class DaKa {

    /**
     * 单例
     * */
    private static DaKa DA_KA;

    /**
     * 打卡配置
     * */
    private static final DaKaProperties DA_KA_PROPERTIES = DaKaProperties.getInstance();

    /**
     * 日志
     * */
    private static final Logger log = LoggerFactory.getLogger(DaKa.class);

    /**
     * 自动打卡状态
     * */
    private boolean status = false;

    /**
     * 打卡日志
     * */
    private final List<String> logs = new ArrayList<>();

    /**
     * 打卡日志websocket集合
     * */
    private final List<DaKaLogWebsocketApi> daKaLogWebsocketApiList = new ArrayList<>();

    /**
     * 构造函数
     * */
    private DaKa() {
        if (getDaKaSign()) {
            OpenAutoDaKa();
        } else {
            this.addLog("关闭自动打卡");
        }
    }

    /**
     * 获取单例
     * */
    public static DaKa getInstance() {
        if (DA_KA == null) {
            DA_KA = new DaKa();
        }

        return DA_KA;
    }

    /**
     * 开启自动打卡
     *
     * @return 响应
     * */
    public Response OpenAutoDaKa() {
        // 如果已开启自动打卡，不再重复开启
        if (this.status) {
            return Response.returnFalse("已开启自动打卡");
        }

        this.setDaKaSign(true);
        this.addLog("开启自动打卡");

        new Thread(() -> {
            while (getDaKaSign()) {
                // 检查日期是否符合
                boolean dateOk = false;
                for (String date : DA_KA_PROPERTIES.getDate()) {
                    if (DateUtil.getDate("yyyy-MM-dd").equals(date)) {
                        dateOk = true;
                    }
                    if (dateOk) {
                        break;
                    }
                }

                // 检查星期是否符合
                boolean weekOk = false;
                for (String week : DA_KA_PROPERTIES.getWeek()) {
                    if (DateUtil.getWeek().equals(week)) {
                        weekOk = true;
                    }
                    if (weekOk) {
                        break;
                    }
                }

                // 日期符合或者星期符合都进行打卡
                if (!(dateOk || weekOk)) {
                    continue;
                }

                // 匹配时间进行打卡
                if (DateUtil.getNowTime().equals(DA_KA_PROPERTIES.getToWorkTime())) {
                    this.addLog("开始上班打卡...");
                } else if (DateUtil.getNowTime().equals(DA_KA_PROPERTIES.getOffWorkTime())) {
                    this.addLog("开始下班打卡...");
                } else {
                    continue;
                }

                ChromeDriver chromeDriver = null;

                try {
                    chromeDriver = ChromeDriver.get();
                    chromeDriver.daKa();
                    this.addLog("打卡完成！");
                } catch (BaseException e) {
                    this.addLog(e.getMessage());
                } finally {
                    if (chromeDriver != null) {
                        chromeDriver.close();
                    }
                }

                // 打卡完等待1分钟
                ThreadUtil.sleep(60000);
            }

            this.addLog("关闭自动打卡");
        }).start();

        return Response.returnTrue();
    }

    /**
     * 获取打卡标识
     *
     * @return true: 开始打卡, false: 关闭打卡
     * */
    private boolean getDaKaSign() {
        return DA_KA_PROPERTIES.getSign().equals("1");
    }

    /**
     * 设置打卡标识
     *
     * @param daKaSign true: 开启打卡, false: 关闭打卡
     * */
    private void setDaKaSign(boolean daKaSign) {
        if (daKaSign) {
            DA_KA_PROPERTIES.setSign("1");
        } else {
            DA_KA_PROPERTIES.setSign("0");
        }

        this.status = daKaSign;
    }

    /**
     * 关闭自动打卡
     *
     * @return 响应
     * */
    public Response closeAutoDaKa() {
        if (!this.status) {
            return Response.returnFalse("自动打卡未开启");
        }

        setDaKaSign(false);

        return Response.returnTrue();
    }

    /**
     * 获取自动打卡状态
     *
     * @return 响应
     * */
    public Response getStatus() {
        return Response.returnTrue(this.status);
    }

    /**
     * 添加日志
     *
     * @param log 日志信息
     * */
    private void addLog(String log) {
        DaKa.log.info(log);

        String l = "[" + DateUtil.getDate("yyyy-MM-dd HH:mm:ss") + "] - " + log;

        this.logs.add(l);

        // 发送websocket
        for (DaKaLogWebsocketApi daKaLogWebsocketApi : this.daKaLogWebsocketApiList) {
            daKaLogWebsocketApi.sendMessage(l);
        }
    }

    /**
     * 获取日志信息
     *
     * @return 日志
     * */
    public List<String> getLog() {
        return this.logs;
    }

    /**
     * 清空日志
     *
     * @return 响应
     * */
    public Response cleanLog() {
        this.logs.clear();

        return Response.returnTrue();
    }

    /**
     * 手动打卡
     *
     * @return 响应
     * */
    public Response manualKaKa() {
        ChromeDriver chromeDriver = null;
        Response response;

        try {
            this.addLog("手动打卡...");
            chromeDriver = ChromeDriver.get();
            chromeDriver.daKa();
            response = Response.returnTrue();
            this.addLog("打卡完成！");
        } catch (BaseException e) {
            response = Response.returnFalse(e.getMessage());
            this.addLog(e.getMessage());
        } finally {
            if (chromeDriver != null) {
                chromeDriver.close();
            }
        }

        return response;
    }

    /**
     * 添加打卡日志websocket
     *
     * @param daKaLogWebsocketApi 打卡日志websocket
     */
    public void addDaKaLogWebsocket(DaKaLogWebsocketApi daKaLogWebsocketApi) {
        this.daKaLogWebsocketApiList.add(daKaLogWebsocketApi);
    }

    /**
     * 删除打卡日志websocket
     *
     * @param daKaLogWebsocketApi 打卡日志websocket
     * */
    public synchronized void deleteDaKaLogWebsocket(DaKaLogWebsocketApi daKaLogWebsocketApi) {
        int size = this.daKaLogWebsocketApiList.size();

        for (int i = 0;i < size; i++) {
            if (daKaLogWebsocketApi.getId().equals(this.daKaLogWebsocketApiList.get(i).getId())) {
                this.daKaLogWebsocketApiList.remove(i);
                log.info("daKaLogWebsocketApiList: " + this.daKaLogWebsocketApiList.size());
                return;
            }
        }
    }

}