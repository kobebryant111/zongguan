package com.work.ppm.commons.utils;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.naming.NoNameCoder;
import com.thoughtworks.xstream.io.xml.Xpp3Driver;
import lombok.extern.slf4j.Slf4j;


/**
 * xml对象
 *
 */
@Slf4j
public class XmlUtil {

	/**
	 * 对象转xml
	 * @param bean
	 * @return String
	 */
	public static String beanToXml(Object bean){
		XStream xStream = new XStream(new Xpp3Driver(new NoNameCoder()));
		xStream.autodetectAnnotations(true);
		xStream.aliasSystemAttribute(null,"class");
		xStream.setMode(XStream.NO_REFERENCES);
		return xStream.toXML(bean);
	}


	/**
	 * xml转对象
	 * @param xml
	 * @param cls
	 * @param <T>
	 * @return T
	 */
	public static <T>  T xmlToBean(String xml, Class<?> cls){
		XStream xStream = new XStream(new Xpp3Driver(new NoNameCoder()));
		xStream.autodetectAnnotations(true);
		xStream.aliasSystemAttribute(null,"class");
		xStream.ignoreUnknownElements();//忽略未知节点
		xStream.processAnnotations(cls);
		return (T) xStream.fromXML(xml);
	}

	/**
	 * xml转对象
	 * @param xml
	 * @param cls
	 * @param <T>
	 * @param bodyCls
	 * @return T
	 */
	public static <T>  T xmlToBean(String xml, Class<T> cls,Class bodyCls){
		String bodyXml = String.format("<BODY class='%s'>",bodyCls.getName());
		xml = xml.replaceFirst("(?i)<BODY>",bodyXml);
		XStream xStream = new XStream();
		xStream.processAnnotations(cls);
		xStream.processAnnotations(bodyCls);
		xStream.autodetectAnnotations(true);
		xStream.ignoreUnknownElements();//忽略未知节点
		return (T) xStream.fromXML(xml);
	}

}