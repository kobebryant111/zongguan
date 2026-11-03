package com.work.ppm.commons.socket.util;

import org.dom4j.*;

import java.util.*;

/**
 * XmlUtils
 *
 */
public class XmlUtils {
	/**
	 * itemStr
	 */
	private static String itemStr = "Item";

	/**
	 * encode
	 * @param o object
	 * @param encode encode
	 * @param rootKey rootKey
	 * @return String
	 */
	public static String encode(Object o, String encode, String rootKey) {
		Document d = DocumentHelper.createDocument();
		d.setXMLEncoding(encode);
		Element root = encodeNode(rootKey, o);
		d.add(root);
		return d.asXML();
	}

	/**
	 * encodeNode
	 * @param strKey strKey
	 * @param o Object
	 * @return Element
	 */
	@SuppressWarnings("rawtypes")
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
	 * 解析xml
	 * 
	 * @param str xml字符串
	 * @param e 数组节点
	 * @param clz  返回类型
	 * @return
	 * @throws DocumentException
	 */
	@SuppressWarnings("rawtypes")
	public static <T> T decode(String str,String e,Class<T> clz) throws DocumentException { 
		Document doc = null;

		doc = DocumentHelper.parseText(str); // 将字符串转为XML

		Element rootElt = doc.getRootElement(); // 获取根节点

		/*if (allItem(rootElt,e)) {
			throw new DocumentException("根节点下不允许有item元素");
		}*/

		return (T) decodeNode(rootElt,e); 
	}

	
	/**
	 * decode
	 * @param str str
	 * @return Map
	 * @throws DocumentException DocumentException
	 */
	@SuppressWarnings("rawtypes")
	public static Map decode(String str) throws DocumentException { 
		Document doc = null;

		doc = DocumentHelper.parseText(str); // 将字符串转为XML

		Element rootElt = doc.getRootElement(); // 获取根节点

		if (allItem(rootElt,itemStr)) {
			throw new DocumentException("根节点下不允许有item元素");
		}

		return (Map) decodeNode(rootElt,itemStr); 
	}

	/**
	 * decodeNode
	 * @param e Element
	 * @return Object
	 * @throws DocumentException DocumentException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static Object decodeNode(Element e,String rootStr) throws DocumentException {
		if (!hasChild(e)) {
			return e.getTextTrim();
		}
		boolean bAllItem = allItem(e,rootStr);
 

		if (bAllItem) {
		 
			List list = new ArrayList();
			Iterator i = e.elementIterator();
			while (i.hasNext()) {
				list.add(decodeNode((Element) i.next(),rootStr));
			}
			return list;
		} else {
			Map tmpM = new HashMap();
			Iterator i = e.elementIterator();
			while (i.hasNext()) {
				Element tmpE = (Element) i.next();
				String tmpKey = tmpE.getName();
				 
				tmpM.put(tmpKey, decodeNode(tmpE,rootStr));
			}
			return tmpM;
		}
	}

	/**
	 * hasChild
	 * @param e Element
	 * @return boolean
	 */
	@SuppressWarnings("rawtypes")
	private static boolean hasChild(Element e) {
		Iterator i = e.elementIterator();
		if (i.hasNext()){
			return true;
		}
		return false;
	}
	
	/**
	 * allItem
	 * @param e Element
	 * @return boolean
	 * @throws DocumentException DocumentException
	 */
	@SuppressWarnings("rawtypes")
	private static boolean allItem(Element e,String rootStr) throws DocumentException {
		Iterator i = e.elementIterator();
		int total = e.elements().size();
		int item = 0;
		while (i.hasNext()) {
			Element tmpE = (Element) i.next();
			if (rootStr.equals(tmpE.getName())){
				item++;
			}
		}
		if (item == 0) {
			return false;
		}
		if (item < total) {
			throw new DocumentException("元素中即有ITEM元素，又有其他字段的属性");
		}
		return true;
	}

	/**
	 * test
	 * @param args args
	 * @throws Exception ex
	 */
	public static void main(String[] args) throws Exception {

		// m.put("code", "0000");
		// List list = new ArrayList();
		// Map m1 = new HashMap();
		// m1.put("InstId", "1234");
		// m1.put("text", "您有一条来自星星的你的上线审批流程需要您的审批，请尽快审批");
		// m1.put("dateTime", "2015-01-16 12:50:50");
		// Map m2 = new HashMap();
		// m2.put("InstId", "1235");
		// m2.put("text", "您有一条来自星星的你的上线审批流程需要您的审批，请尽快审批");
		// m2.put("dateTime", "2015-01-16 13:50:50");
		// list.add(m1);
		// list.add(m2);
		// m.put("unApproves", list);
		// String retStr = XmlUtil.encode(m);
		//  

		/**
		 * decode用法
		 */
		/*
		 * String retStr = "<root>"
		 * 
		 * + "        <Content>" + "                <Approver>" +
		 * "                     <Item>" +
		 * "							<BusinessNode>业务项目经理上线发起</BusinessNode><Date>2015-01-23</Date><SpResult>提交</SpResult>
		 * 								<SpSuggest>业务项目经理上线发起</SpSuggest><User>林英(111849)</User>"
		 * + "                    </Item>" + "       			 <Item>" +
		 * "							<BusinessNode>科技项目经理上线评估</BusinessNode><Date>2015-01-23</Date><SpResult>通过</SpResult>
		 * 								<SpSuggest>同意</SpSuggest><User>薛成峰(020042)</User>"
		 * + "					</Item>" + "                </Approver>"
		 * 
		 * + "           <Onlinenotes>asd</Onlinenotes>" + "</Content>" +
		 * "</root>";
		 */

		// String retStr = "<root>"
		//
		// + "        <Content>"
		// + "                <Approver>"
		// + "                     <Item>"
		// +
		// "							<BusinessNode>业务项目经理上线发起</BusinessNode><Date>2015-01-23</Date><SpResult>提交</SpResult>
		//								<SpSuggest>业务项目经理上线发起</SpSuggest><User>林英(111849)</User>"
		// + "                    </Item>" + "                </Approver>"
		//
		// + "           <Onlinenotes>asd</Onlinenotes>" + "</Content>"
		// + "</root>";
		// try {
		// Map mapRet = XmlUtil.decode(retStr);
		// Map tmpM = (Map) mapRet.get("Content");
		// List list = (List) tmpM.get("Approver");
		//  
		// 
		// // List list =(List) ((Map) mapRet.get("Content")).get("Approver");;
		// //  
		// // Map temp = (Map)mapRet.get("Content");
		// //  
		// //  
		// // List li = (List)mapRet.get("hello2");
		// //  
		// } catch (DocumentException e) {
		//
		// e.printStackTrace();
		// }

	}

}
