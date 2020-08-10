package com.easipass.util.api.service;

import com.easipass.util.entity.Response;
import com.easipass.util.entity.http.SWGDSupply.SendCusFileSWGDSupplyHttp;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 第三方发送api
 *
 * @author ZJ
 * */
@RestController
@RequestMapping(BaseServiceApi.URL + "supplySend")
public class SupplySendServiceApi {

    /**
     * 发送报文
     * */
    @PostMapping("sendCusFile")
    public Response sendCusFile(@RequestBody SendCusFileDTO sendCusFileDTO) {
        SendCusFileSWGDSupplyHttp sendCusFileSWGDSupplyHttp = new SendCusFileSWGDSupplyHttp(sendCusFileDTO.getSender());

        sendCusFileSWGDSupplyHttp.sendCusFile(sendCusFileDTO.getData());

        return Response.returnTrue();
    }

    /**
     * SendCusFileDTO
     *
     * @author ZJ
     * */
    private static final class SendCusFileDTO {

        /**
         * sender
         * */
        private String sender;

        /**
         * data
         * */
        private String data;

        public String getSender() {
            return sender;
        }

        public void setSender(String sender) {
            this.sender = sender;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }

        @Override
        public String toString() {
            return "SendCusFileDTO{" +
                    "sender='" + sender + '\'' +
                    ", data='" + data + '\'' +
                    '}';
        }

    }

}