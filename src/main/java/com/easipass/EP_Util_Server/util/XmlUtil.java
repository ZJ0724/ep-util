package com.easipass.EP_Util_Server.util;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import java.io.*;

public class XmlUtil {

    /**
     * 获取document by InputStream
     */
    public static Document getDocument(InputStream InputStream) throws DocumentException{

        SAXReader saxReader = new SAXReader();
        Document document = saxReader.read(InputStream);
        return document;

    }

    /**
     * 重写xml
     */
    public static void setXml(File xml,Document document) throws IOException{

        OutputFormat format = OutputFormat.createPrettyPrint();
        format.setEncoding("UTF-8");
        XMLWriter writer = new XMLWriter(new OutputStreamWriter(new FileOutputStream(xml)), format);
        writer.write(document);
        writer.close();

    }

}
