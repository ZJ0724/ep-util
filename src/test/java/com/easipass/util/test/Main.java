package com.easipass.util.test;

import com.easipass.util.core.http.SWGDSupply.SignSWGDSupplyHttp;

public class Main {

    public static void main(String[] args) {


        SignSWGDSupplyHttp signSWGDSupplyHttp = new SignSWGDSupplyHttp("KSHJ");


        String a = signSWGDSupplyHttp.getSign("{\n" +
                "\t\"T_DEC_USER\": [],\n" +
                "\t\"T_SWGD_FORM_ECO\": [],\n" +
                "\t\"T_DEC_MARK_LOB\": [],\n" +
                "\t\"T_DEC_OTHER_PACK\": [],\n" +
                "\t\"T_DEC_REQUEST_CERT\": [],\n" +
                "\t\"T_DEC_COP_LIMIT\": [],\n" +
                "\t\"T_SWGD_SDDTAX_LIST\": [],\n" +
                "\t\"T_SWGD_FORM_CONTAINER\": [{\n" +
                "\t\t\"containerNo\": 0,\n" +
                "\t\t\"containerId\": \"1\",\n" +
                "\t\t\"containerMdStd\": \"13\",\n" +
                "\t\t\"goodsNo\": \"1\",\n" +
                "\t\t\"lclFlag\": \"1\",\n" +
                "\t\t\"containerWt\": \"1\"\n" +
                "\t}],\n" +
                "\t\"T_SWGD_FORM_CERTIFICATE\": [],\n" +
                "\t\"T_SWGD_FORM_EDOC\": [],\n" +
                "\t\"T_DEC_ROYALTY_FEE\": [],\n" +
                "\t\"LIST_LIMIT_SUP_LIST\": [{\n" +
                "\t\t\"T_SWGD_FORM_ORIGINALSUP\": [],\n" +
                "\t\t\"LIMIT_VIN_PAIR\": [],\n" +
                "\t\t\"T_SWGD_FORM_CLASSIFYSUP\": [],\n" +
                "\t\t\"T_SWGD_FORM_PRICESUP\": [],\n" +
                "\t\t\"T_SWGD_FORM_LIST\": {\n" +
                "\t\t\t\"guid\": \"a363a3fa-4ce0-428c-af64-6582832aefcf\",\n" +
                "\t\t\t\"gNo\": 0,\n" +
                "\t\t\t\"codeT\": \"03036600\",\n" +
                "\t\t\t\"codeS\": \"00\",\n" +
                "\t\t\t\"contrItem\": \"新贸序号\",\n" +
                "\t\t\t\"declPrice\": \"1.0000\",\n" +
                "\t\t\t\"declTotal\": \"1.00\",\n" +
                "\t\t\t\"dutyMode\": \"1\",\n" +
                "\t\t\t\"goodsId\": \"货号\",\n" +
                "\t\t\t\"prdtNo\": \"成品单耗版本号\",\n" +
                "\t\t\t\"firstUnitStd\": \"035\",\n" +
                "\t\t\t\"qtyConv\": \"1.0000\",\n" +
                "\t\t\t\"gUnitStd\": \"035\",\n" +
                "\t\t\t\"gModel\": \"规格型号\",\n" +
                "\t\t\t\"gName\": \"冻狗鳕鱼(无须鳕属、长鳍鳕属)_x000d_\",\n" +
                "\t\t\t\"gQty\": \"1.0000\",\n" +
                "\t\t\t\"originCountryStd\": \"AFG\",\n" +
                "\t\t\t\"tradeCurrStd\": \"AUD\",\n" +
                "\t\t\t\"workUsd\": \"1.0000\",\n" +
                "\t\t\t\"destinationCountryStd\": \"AFG\",\n" +
                "\t\t\t\"declGoodsEname\": \"商品英文名称\",\n" +
                "\t\t\t\"origPlaceCode\": \"296001\",\n" +
                "\t\t\t\"purpose\": \"99\",\n" +
                "\t\t\t\"prodValidDt\": \"20190225\",\n" +
                "\t\t\t\"prodQgp\": \"20150101\",\n" +
                "\t\t\t\"goodsAttr\": \"11,19\",\n" +
                "\t\t\t\"stuff\": \"成份/原料/组份\",\n" +
                "\t\t\t\"unCode\": \"UN编码\",\n" +
                "\t\t\t\"dangName\": \"危险货物名称\",\n" +
                "\t\t\t\"dangPackType\": \"1\",\n" +
                "\t\t\t\"dangPackSpec\": \"危包规格\",\n" +
                "\t\t\t\"engManEntCnm\": \"境外生产企业名称\",\n" +
                "\t\t\t\"noDangFlag\": \"1\",\n" +
                "\t\t\t\"destCode\": \"110000\",\n" +
                "\t\t\t\"goodsSpec\": \"检验检疫货物规格\",\n" +
                "\t\t\t\"goodsModel\": \"货物型号\",\n" +
                "\t\t\t\"goodsBrand\": \"货物品牌\",\n" +
                "\t\t\t\"produceDate\": \"2015-01-01\",\n" +
                "\t\t\t\"prodBatchNo\": \"生产批号\",\n" +
                "\t\t\t\"districtCode\": \"32043\",\n" +
                "\t\t\t\"ciqName\": \"检验检疫名称\",\n" +
                "\t\t\t\"mnufctrRegno\": \"生产单位注册号\",\n" +
                "\t\t\t\"mnufctrRegName\": \"生产单位名称\"\n" +
                "\t\t}\n" +
                "\t}],\n" +
                "\t\"T_SWGD_SDDTAX_DETAILS\": [],\n" +
                "\t\"T_DEC_COP_PROMISE\": [],\n" +
                "\t\"T_SWGD_FORM_HEAD\": {\n" +
                "\t\t\"decVersion\": \"3.1\",\n" +
                "\t\t\"ediNo\": \"EDI200000001747982\",\n" +
                "\t\t\"agentCode\": \"1234567890\",\n" +
                "\t\t\"agentName\": \"企业中文名称\",\n" +
                "\t\t\"billNo\": \"提运单号\",\n" +
                "\t\t\"contrNo\": \"合同协议\",\n" +
                "\t\t\"declPort\": \"2217\",\n" +
                "\t\t\"cutMode\": \"101\",\n" +
                "\t\t\"datasource\": \"2\",\n" +
                "\t\t\"distinatePortStd\": \"MAR054\",\n" +
                "\t\t\"ediId\": \"1\",\n" +
                "\t\t\"ieFlag\": \"0\",\n" +
                "\t\t\"ieType\": \"0\",\n" +
                "\t\t\"feeCurr\": \"AUD\",\n" +
                "\t\t\"feeCurrStd\": \"AUD\",\n" +
                "\t\t\"feeMark\": \"0\",\n" +
                "\t\t\"feeRate\": \"1\",\n" +
                "\t\t\"grossWt\": \"1\",\n" +
                "\t\t\"iEPort\": \"0000\",\n" +
                "\t\t\"insurCurr\": \"AUD\",\n" +
                "\t\t\"insurCurrStd\": \"AUD\",\n" +
                "\t\t\"insurMark\": \"0\",\n" +
                "\t\t\"insurRate\": \"1\",\n" +
                "\t\t\"licenseNo\": \"许可证号\",\n" +
                "\t\t\"manualNo\": \"111111111111\",\n" +
                "\t\t\"netWt\": \"1\",\n" +
                "\t\t\"noteS\": \"备注\",\n" +
                "\t\t\"otherCurr\": \"AUD\",\n" +
                "\t\t\"otherCurrStd\": \"AUD\",\n" +
                "\t\t\"otherMark\": \"0\",\n" +
                "\t\t\"otherRate\": \"1\",\n" +
                "\t\t\"ownerCode\": \"3101965379\",\n" +
                "\t\t\"ownerName\": \"上海豫园黄金珠宝集团有限公司\",\n" +
                "\t\t\"packNo\": \"1\",\n" +
                "\t\t\"tradeCountryStd\": \"AFG\",\n" +
                "\t\t\"tradeModeStd\": \"0110\",\n" +
                "\t\t\"tradeCo\": \"3101965379\",\n" +
                "\t\t\"trafModeStd\": \"0\",\n" +
                "\t\t\"trafName\": \"运输工具名称\",\n" +
                "\t\t\"tradeName\": \"上海豫园黄金珠宝集团有限公司\",\n" +
                "\t\t\"transMode\": \"7\",\n" +
                "\t\t\"wrapTypeStd\": \"00\",\n" +
                "\t\t\"tradeCoScc\": \"913100007622206000\",\n" +
                "\t\t\"agentCodeScc\": \"123456789012345678\",\n" +
                "\t\t\"ownerCodeScc\": \"913100007622206000\",\n" +
                "\t\t\"promiseItmes\": \"1110\",\n" +
                "\t\t\"tradeAreaCodeStd\": \"AFG\",\n" +
                "\t\t\"markNo\": \"标记唛码\",\n" +
                "\t\t\"entyPortCode\": \"110001\",\n" +
                "\t\t\"goodsPlace\": \"货物存放地点\",\n" +
                "\t\t\"bLNo\": \"B/L号\",\n" +
                "\t\t\"inspOrgCode\": \"140100\",\n" +
                "\t\t\"specDeclFlag\": \"1111111\",\n" +
                "\t\t\"purpOrgCode\": \"140100\",\n" +
                "\t\t\"despDate\": \"20190225\",\n" +
                "\t\t\"cmplDschrgDt\": \"20190225\",\n" +
                "\t\t\"correlationReasonFlag\": \"1\",\n" +
                "\t\t\"vsaOrgCode\": \"140100\",\n" +
                "\t\t\"origBoxFlag\": \"1\",\n" +
                "\t\t\"declareName\": \"报关员姓名\",\n" +
                "\t\t\"noOtherPack\": \"0\",\n" +
                "\t\t\"orgCode\": \"140100\",\n" +
                "\t\t\"overseasConsignorCname\": \"境外收发货人名称\",\n" +
                "\t\t\"overseasConsignorAddr\": \"境外收发货人地址\",\n" +
                "\t\t\"overseasConsigneeCode\": \"境外收发货人代码\",\n" +
                "\t\t\"overseasConsigneeEname\": \"境外收发货人名称（外文）\",\n" +
                "\t\t\"domesticConsigneeEname\": \"境内收发货人企业名称(外文)\",\n" +
                "\t\t\"correlationNo\": \"关联号码\",\n" +
                "\t\t\"typeEr\": \"1\",\n" +
                "\t\t\"collecttax\": \"1\",\n" +
                "\t\t\"taxNo\": \"保函号\",\n" +
                "\t\t\"ramanualno\": \"关联备案\",\n" +
                "\t\t\"radeclno\": \"111111111111111111\",\n" +
                "\t\t\"storeno\": \"保税监管场所\",\n" +
                "\t\t\"voyageNo\": \"航次号\",\n" +
                "\t\t\"prdtid\": \"货场代码\",\n" +
                "\t\t\"status\": \"7\",\n" +
                "\t\t\"tradeCiqCode\": \"境内收发货人检验检疫\",\n" +
                "\t\t\"ownerCiqCode\": \"生产销售单位检验检疫\",\n" +
                "\t\t\"declCiqCode\": \"申报单位检验检疫编码\",\n" +
                "\t\t\"certificate\": \"0107d927\",\n" +
                "\t\t\"declNo\": \"12345895\",\n" +
                "\t\t\"userAccount\": \"2019022200\",\n" +
                "\t\t\"userName\": \"zj01\",\n" +
                "\t\t\"type\": \"0\",\n" +
                "\t\t\"typeLbsbOnce\": \"0\",\n" +
                "\t\t\"icCode\": \"12345643\"\n" +
                "\t}\n" +
                "}");


//        AesSWGDSupplyHttp aesSWGDSupplyHttp = new AesSWGDSupplyHttp("KSHJ");
//
//        String a = aesSWGDSupplyHttp.aes("");



        System.out.println(a);

    }

}