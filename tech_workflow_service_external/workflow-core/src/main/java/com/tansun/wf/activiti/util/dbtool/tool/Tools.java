package com.tansun.wf.activiti.util.dbtool.tool;

import com.tansun.wf.activiti.util.dbtool.entity.Data;
import com.tansun.wf.activiti.util.dbtool.entity.Tables;
import com.tansun.wf.activiti.util.dbtool.exception.DbToolException;

import java.io.*;

/**
 * 简易文本数据库工具类
 * @author 罗道宏
 */
public class Tools {

	public static String path;

	private static final String name="talbes";

	public static String delSpaceEnter(String str){
		if(str==null) return null;
		str=str.replaceAll("[\\s]+", " ");
		str=str.replaceAll("\n"," ");
		return str.trim();
	}

	public static Tables readTables(){
		try {
			FileInputStream fis = new FileInputStream(path + name);
			ObjectInputStream ois = new ObjectInputStream(fis);
			Tables tb = (Tables) ois.readObject();
			ois.close();
			fis.close();
			return tb;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DbToolException("readTables异常");
		}
		return null;
	}

	public static void writeTables(Tables tb){
		try {
			FileOutputStream fos = new FileOutputStream(path + name);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(tb);
			oos.flush();
			oos.close();
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DbToolException("writeTables异常");
		}
	}

	public static Data readData(String tname){
		try {
			FileInputStream fis = new FileInputStream(path + tname);
			ObjectInputStream ois = new ObjectInputStream(fis);
			Data data = (Data) ois.readObject();
			ois.close();
			fis.close();
			return data;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DbToolException("readData异常");
		}
		return null;
	}

	public static void writeData(Data tb, String tname){
		try {
			FileOutputStream fos = new FileOutputStream(path + tname);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(tb);
			oos.flush();
			oos.close();
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DbToolException("writeTables异常");
		}
	}

	public static boolean removeFile(String fileName){
		String detailPath = path + fileName;
		File file = new File(detailPath);
		if(file.exists()) {
			return file.delete();
		}
		return true;
	}
}
