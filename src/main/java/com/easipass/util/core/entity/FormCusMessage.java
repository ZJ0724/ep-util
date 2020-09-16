package com.easipass.util.core.entity;

import com.easipass.util.core.exception.CusMessageException;
import com.easipass.util.core.util.StringUtil;
import org.dom4j.Element;
import org.springframework.web.multipart.MultipartFile;

/**
 * 报关单报文
 *
 * @author ZJ
 * */
public final class FormCusMessage extends AbstractCusMessage {

    /**
     * <DecSign><DecSign/>
     * */
    private final Element DecSign;

    /**
     * ediNo
     * */
    private final String ediNo;

    /**
     * constructor
     *
     * @param multipartFile multipartFile
     */
    public FormCusMessage(MultipartFile multipartFile) {
        super(multipartFile);

        this.DecSign = this.getRootElement().element("DecSign");
        if (this.DecSign == null) {
            throw new CusMessageException("缺少<DecSign>节点");
        }

        Element BillSeqNo = this.DecSign.element("BillSeqNo");
        if (BillSeqNo == null) {
            throw new CusMessageException("缺少<BillSeqNo>节点");
        }
        this.ediNo = BillSeqNo.getText();
        if (StringUtil.isEmpty(this.ediNo)) {
            throw new CusMessageException("报文ediNo为空");
        }
    }

    /**
     * getDecSign
     *
     * @return DecSign
     * */
    public Element getDecSign() {
        return this.DecSign;
    }

    /**
     * getEdiNo
     *
     * @return EdiNo
     * */
    public String getEdiNo() {
        return this.ediNo;
    }

}