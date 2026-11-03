package com.work.ppm.commons.lang.xml;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentFactory;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * xml 工具类
 */
public class XmlUtil {
    /**
     * 变量
     */
    private static String itemStr = "Item";

    /**
     * object转换成string
     * @param o 对象
     * @return string
     */
    public static String encode(Object o) {
        Document d = DocumentHelper.createDocument();
        Element root = encodeNode("xml", o);
        d.add(root);
        return d.asXML();
    }

    /**
     * object转换成string
     * @param o 对象
     * @param encode 编码
     * @param rootKey 根节点key
     * @return string
     */
    public static String encode(Object o, String encode, String rootKey) {
        Document d = DocumentHelper.createDocument();
        d.setXMLEncoding(encode);
        Element root = encodeNode(rootKey, o);
        d.add(root);
        return d.asXML();
    }

    /**
     * object转 Element
     * @param strKey 节点的key
     * @param o 对象
     * @return Element
     */
    private static Element encodeNode(String strKey, Object o) {
        DocumentFactory factory = DocumentFactory.getInstance();
        Element e = factory.createElement(strKey);

        if (o instanceof Map) {
            Map m = (Map) o;
            Iterator i = m.entrySet().iterator();
            while (i.hasNext()) {
                Map.Entry en = (Map.Entry) i.next();
                String key = (String) en.getKey();
                Object value = (Object) en.getValue();
                e.add(encodeNode(key, value));
            }
            return e;
        } else if (o instanceof List) {
            List list = (List) o;
            for (int i = 0; i < list.size(); i++) {
                e.add(encodeNode(itemStr, list.get(i)));
            }
            return e;
        } else if (o instanceof Integer) {
            e.setText(String.valueOf(o));
            return e;
        } else if (o instanceof String) {
            e.setText(String.valueOf(o));
            // e.addCDATA((String) o);
            return e;
        } else {
            return null;
        }
    }

    /**
     * xml字符串转map
     * @param str xml字符串
     * @return map
     * @throws DocumentException
     */
    public static Map decode(String str) throws DocumentException {
        Document doc = null;
        doc = DocumentHelper.parseText(str); // 将字符串转为XML
        Element rootElt = doc.getRootElement(); // 获取根节�?
        if (allItem(rootElt)) {
            throw new DocumentException("根节点下不允许有item元素");
        }
        return (Map) decodeNode(rootElt);
    }

    /**
     * elment 转对象
     * @param e 对象
     * @return Object
     * @throws DocumentException
     */
    private static Object decodeNode(Element e) throws DocumentException {
        if (!hasChild(e)) {
            return e.getTextTrim();
        }
        boolean bAllItem = allItem(e);
        String key = e.getName();
        if (bAllItem) {
            List list = new ArrayList();
            Iterator i = e.elementIterator();
            while (i.hasNext()) {
                list.add(decodeNode((Element) i.next()));
            }
            return list;
        } else {
            Map tmpM = new HashMap();
            Iterator i = e.elementIterator();
            while (i.hasNext()) {
                Element tmpE = (Element) i.next();
                String tmpKey = tmpE.getName();
                tmpM.put(tmpKey, decodeNode(tmpE));
            }
            return tmpM;
        }
    }

    /**
     * 是否存在节点
     * @param e Element
     * @return boolean
     */
    private static boolean hasChild(Element e) {
        Iterator i = e.elementIterator();
        if (i.hasNext())
            return true;
        return false;
    }

    /**
     * 是否存在 item 节点
     * @param e Element
     * @return boolean
     * @throws DocumentException
     */
    private static boolean allItem(Element e) throws DocumentException {
        Iterator i = e.elementIterator();
        int total = e.elements().size();
        int item = 0;
        while (i.hasNext()) {
            Element tmpE = (Element) i.next();
            if (tmpE.getName().equals(itemStr))
                item++;
        }
        if (item == 0) {
            return false;
        }
        if (item < total) {
            throw new DocumentException("元素中即有ITEM元素，又有其他字段的属�?");
        }
        return true;
    }
}
