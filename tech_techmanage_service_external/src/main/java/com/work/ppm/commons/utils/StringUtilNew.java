package com.work.ppm.commons.utils;


import org.apache.commons.codec.digest.DigestUtils;

import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


/**
 * 此类主要处理有关String类型的一些操作，如：替换字符串、查找字符串的个数、将一个String[]
 * 转化成一个int[]、将一个String[]中的某种字符串过滤掉、将一个String[]中的某种字符串移到
 * 数组的末尾、将String转化成gb2312的编码格式、将一个String转化成String[].
 */

/**
 * 字符串工具类
 *
 */
public class StringUtilNew
{
    
	/**
	 * 判断NULL
	 * @param value	String
	 * @return	boolean
	 */
	public static boolean isNull(String value) {
		try {
			if (value == null) {
				return true;
			}
			if ("null".equals(value)){
				return true;
			}
			if (value.trim().length() == 0){
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * 判断对象是否为NULL，如为Null转化为空字符串
	 * @param val	String
	 * @return		String
	 */
	public static String repNullToStr(String val) {
		return val == null ? "" : val;
	}
	
	/**
	 * 处理非法字符（定义0-31的ASCII码为非法字符）
	 * @param s	String
	 * @return	boolean
	 */
	public static boolean isIllegalChar(String s) {
		boolean flag = false;
		char[] dest = s.trim().toCharArray();
		for (int i = 0; i < dest.length; i++) {
			if (((int) dest[i] > 0 && (int) dest[i] < 32)) {
				//Debug("含有非法字符!" + (int) dest[i]);
				flag = true;
				break;
			}
		}
		return flag;
	} 


    /**
     * 替换String中的字符串.
     * 在标准的String类中只有将String中的某一个字符替换成另一个字符，该函数可以将String
     * 中的某一字符串替换成另一个字符串.
	 * 
	 * @param str		String
	 * @param tag		String
	 * @param news		String
	 * @return	String
	 */
    public static String replace(String str,String tag,String news)
    {
    	if(str==null||str.length()==0||tag==null||tag.length()==0){
    		return(str);
    	}
    	StringBuilder ret=new StringBuilder(str);
        StringBuilder temp=new StringBuilder();
    	String str1;
    	int strLen=str.length();
    	int tagLen=tag.length();
    	int pos1=0,pos2=0;
    	int i=0;
    	while(i<strLen)
    	{
    		if(i>=(strLen-tagLen)){
    			str1=str.substring(i);
    		}else{
    			str1=str.substring(i,i+tagLen);
    		}
    		if(str1.equals(tag))
    		{
    			pos2=i;
    			temp=temp.append(str.substring(pos1,pos2)).append(news);
    			i=i+tagLen;
    			pos1=i;
    		}
    		else{
    			i++;
    		}
    	}
    	if(pos1<strLen){
    		temp=temp.append(str.substring(pos1));
    	}

    	if(temp.length()>0){
    		ret=temp;
    	}

    	return ret.toString();
    }
    /**
     * 查找某一字符串包含另一字符串的个数.
     * 此处规定已匹配过的字符
     * 	
     * @param str1	String
     * @param str2	String
     * @return	int
     */
    public static int hasStr(String str1,String str2)
    {
        int sum=0;
        String sTemp=new String(str1);
        while(sTemp.indexOf(str2)!=-1)
        {
            sum++;
            sTemp=sTemp.substring(sTemp.indexOf(str2)+str2.length());
        }
        return sum;
    }
    
    /**
     * 将字符串转化成gb2312的编码格式
     * @param strChinese		String
     * @param soureceEncoding	String
     * @param targetEncoding	String
     * @return	String
     */
    public static String changeEncoding(String strChinese, String soureceEncoding, String targetEncoding)
    {
    	if(strChinese == null){
    		return "";
    	}
        try
        {
            return new String(strChinese.trim().getBytes(soureceEncoding),targetEncoding);
        }catch( Exception e)
        {
            return "";
        }
    }
    
    /**
     * 将一个字符串根据一个标示拆分成一个字符串数组.例如",1,2,3,"如果使用“,”分解，将被分成长度为5的字符串数组
     * 
     * @param source	String
     * @param str		String
     * @return	String[]
     */
    public static String[] toArray(String source,String str)
    {
    	Vector vResult = new Vector();
        if(source==null || "".equals(source)){
            return new String[0];
        }
        if(str==null||"".equals(str)){
        	return new String[]{source};
        }
        int pos1 = 0;
        int pos2 = 0;
        pos2 = source.indexOf(str);
        String strItem = "";
        if(pos2<0){
        	pos2=source.length();
        	strItem = source;
        }
        while(pos2 >= pos1 && pos1 < source.length())
        {
    		strItem = source.substring(pos1,pos2);
        	vResult.add(strItem);
        	pos1 = pos2 + 1;
    		pos2 = source.indexOf(str,pos1);
    		if(pos2<=0){
    			pos2=source.length();
    		}
        	
        }
        return toArray(vResult);
    }
    
    /**
     * 分割
     * @param source		String
     * @param token			String
     * @return	String[]
     */
    public static String[] split(String source,String token){
    	return stringToArray(source,token);
    }
    
    /**
     * splitString	
     * @param source	String
     * @param token		String
     * @return	String[]
     */
    public static String[] splitString(String source,String token){
    	return stringToArray(source,token);
    }
    
    /**
     * 字符串转数组
     * @param source		String
     * @param str			String
     * @return String[]
     */
    public static String[] stringToArray(String source,String str)
    {
    	
    	Vector vResult = new Vector();
        if(source==null || "".equals(source)){
        	String[] tmp = {""};
        	return tmp;
        }
            
        if(str==null||"".equals(str)){
        	return new String[]{source};
        }
        int pos1 = 0;
        int pos2 = 0;
        pos2 = source.indexOf(str);
        String strItem = "";
        if(pos2<0){
        	pos2=source.length();
        	strItem = source;
        }
        while(pos2 >= pos1 && pos1 <= source.length())
        {
        	if(pos2==source.length()&&pos1==source.length()){
        		strItem = source.substring(pos2);
        		vResult.add(strItem);
        		break;
        	}
    		strItem = source.substring(pos1,pos2);
        	vResult.add(strItem);
        	pos1 = pos2 + 1;
    		pos2 = source.indexOf(str,pos1);
    		if(pos2<=0){
    			pos2=source.length();
    		}
        }
        return toArray(vResult);
    }
    
    /**
     * toArray
     * @param vec	Vector
     * @return	String[]
     */
    public static String[] toArray(Vector vec)
    {
        int intSize = vec.size();
        String[] str = new String[intSize];
        for(int i=0;i<intSize;i++){
            str[i]=vec.elementAt(i).toString();
        }
        return str;
    }
    
    /**
     * clearElement
     * @param sources	String[]
     * @param str		String
     * @return		String[]
     */
    public static String[] clearElement(String[] sources,String str)
    {
        StringUtilNew.moveToEnd(sources,str);
        int size=0;
        for(;size<sources.length;size++)
        {
            if(sources[size].equals(str)){
                break;
            }
        }
        String[] temp=new String[size];
        for(int i=0;i<size;i++){
            temp[i]=sources[i];
        }
        return temp;
    }
    /**
     * 将一个String[]中的某种字符串移到数组的末尾.
     * @param sources	String[]
     * @param str		String
     * @return	String[]
     */
    public static String[] moveToEnd(String[] sources,String str)
    {
        int head=0;
        int end=sources.length-1;
        while(head!=end)
        {
            while(head!=end)
            {
                if(sources[head].equals(str)){
                    break;
                }
                head++;
            }
            while(head!=end)
            {
                if(!sources[end].equals(str)){
                    break;
                }
                end--;
            }
            if(head!=end)
            {
                String temp=sources[head];
                sources[head]=sources[end];
                sources[end]=temp;
            }
        }
        return sources;
    }
    /**
     * 将字符串数组转化成int型数组.
     * @param sources String[]
     * @return int[]
     */
    public static int[] pareInts(String[] sources)
    {
        int[] temp=new int[sources.length];
        for(int i=0;i<sources.length;i++)
        {
            temp[i]=Integer.parseInt(sources[i]);
        }
        return temp;
    }
    
    /**
     * 将数组vector转化为字符串
     * @param vec	Vector
     * @param str	String
     * @return	String
     */
    public static String toString(Vector vec,String str)
    {
        StringBuilder s=new StringBuilder();
        for(int i=0;i<vec.size();i++)
        {
            if(i!=0){
                s.append(str);
            }
            s=s.append(vec.elementAt(i).toString().trim());
        }
        return s.toString();
    }
    
    /**
     * 将数组[]转化为字符串
     * @param scr		String[]
     * @param str		String
     * @return	String
     */
    public static String toString(String[] scr,String str)
    {
        StringBuilder s=new StringBuilder();
        for(int i=0;i<scr.length;i++)
        {
            if(i!=0){
                s.append(str);
            }
            s=s.append(scr[i]);
        }
        return s.toString();
    }
    
    /**
     * 从字符串中取出一个单词
     * @param str		String
     * @param index		int
     * @return	String
     * @throws Exception	Exception
     */
    public static String getWord(String str,int index) throws Exception
    {
        if(index < 1){
            throw new Exception("getWord error,index less than 1");
        }
        String word=null;
        for(int i = 0;i < index;i++)
        {
            word = StringUtilNew.getFirstWord(str,true);
            str = str.substring(word.length());
        }
        return word;
    }
    
    /**
     * 从字符串中取出第一个单词,并且将分隔符（'，'、‘ ’）也作为一个单词
     * @param str			String
     * @param hasSeparator	boolean
     * @return				String
     * @throws Exception	Exception
     */
    public static String getFirstWord(String str,boolean hasSeparator) throws Exception
    {
        if("".equals(str)){
            return "";
        }
        char ch = str.charAt(0);
        if(ch == '(' || ch == '[' || ch == '{'){
            return StringUtilNew.getBracket(str,ch);
        }
        if(ch == '\''){
            return StringUtilNew.getString(str,ch);
        }
        int offs = str.indexOf(" ")==-1?str.length():str.indexOf(" ");
        String separator=" ";
        if(str.indexOf(",") != -1 && str.indexOf(",") < offs )
        {
            offs = str.indexOf(",");
            separator = ",";
        }
        if(str.indexOf("-") != -1 && str.indexOf("-") < offs )
        {
            offs = str.indexOf("-");
            separator = "-";
        }
        if(str.indexOf("+") != -1 && str.indexOf("+") < offs )
        {
            offs = str.indexOf("+");
            separator = "+";
        }
        if(str.indexOf("*") != -1 && str.indexOf("*") < offs )
        {
            offs = str.indexOf("*");
            separator = "*";
        }
        if(str.indexOf("=") != -1 && str.indexOf("=") < offs )
        {
            offs = str.indexOf("=");
            separator = "=";
        }
        if(str.indexOf("||") != -1 && str.indexOf("||") < offs)
        {
            offs = str.indexOf("||");
            separator = "||";
        }
        if(str.indexOf("(") != -1 && str.indexOf("(") < offs)
        {
            offs = str.indexOf("(");
            return str.substring(0,offs)+StringUtilNew.getBracket(str.substring(offs),'(');
        }
        if(offs == -1){
            return str;
        }
        if(hasSeparator && offs == 0){
            return separator;
        }
        return str.substring(0,offs);
    }
    /**
     * 从字符串中取出第一个单词,但将分隔符（'，'、‘ ’）也作为一个单词
     * 
     * @param str			String
     * @return	String
     * @throws Exception	Exception
     */
    public static String getFirstWord(String str) throws Exception
    {
        return StringUtilNew.getFirstWord(str,false);
    }
    /**
     * 匹配一个字符串中的第一个刮号.
     * 这里假定该字符串的的刮号是正确的
     * 
     * @param str			String
     * @param bracketType	char
     * @return	String
     * @throws Exception	Exception
     */
    public static String getBracket(String str,char bracketType) throws Exception
    {
        //确定刮号的种类
        char endType=')';
        if(bracketType == '['){
            endType=']';
        }
        else if(bracketType == '{'){
            endType = '}';
        }
        else if(bracketType != '('){
            throw new Exception(bracketType + " not a type of bracket");
        }

        char[] c = str.toCharArray();
        int sum=1;
        String s=null;
        for(int i=1;i<c.length;i++)
        {
            if(c[i] == bracketType)
            {
                sum++;
                continue;
            }
            if( c[i] == endType)
            {
                if(--sum == 0)
                {
                    s = String.valueOf(c,0,i+1);
                    break;
                }
            }
        }
        if(s == null){
            throw new Exception("bracket not suited");
        }
        return s;
    }
    
    /**
     * ltrim
     * @param str	String
     * @return	String
     */
    public static String ltrim(String str)
    {
        char[] c = str.toCharArray();
        for(int i=0;i<c.length;i++)
        {
            if(c[i] != ' ')
            {
                str = String.valueOf(c,i,c.length-i);
                break;
            }
        }
        return str;
    }
    /**
     * 根据标示符取一个字符串
     * @param str		String
     * @param c			char
     * @return	String
     */
    public static String getString(String str,char c)
    {
        char[] ch = str.toCharArray();
        for(int i=1;i<ch.length;i++)
        {
            if(ch[i] == c)
            {
                if(i==ch.length-1)
                {
                    str = String.valueOf(ch,0,i+1);
                    break;
                }
                if(ch[i+1] != c)
                {
                    str = String.valueOf(ch,0,i+1);
                    break;
                }
                else
                {
                    i++;
                }
            }
        }
        return str;
    }
    
    /**
     * 转编码 	
     * @param oldStr	String
     * @return	String
     */
	public static String changeCnCode(String oldStr) {
		String value = null;
		try {
			value = new String(oldStr.getBytes("ISO-8859-1"), "GBK");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return value;
	}

	/**
	 * 多个空格替换为一个
	 * @param sourceStr	String
	 * @return	String
	 */
	public static String repSingleSpace(String sourceStr) {
		String regEx = "['   ']+";

		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(sourceStr);
		return m.replaceAll(" ");
	}
	


    /**
     * 字符串为空补0.00
     * @param str	String
     * @return	String
     */
	public static String toBd0(String str) {
		return str != null ? str : "0.00" ;
	}
	
	/**
	 * 获得主键
	 * @return	String
	 */
	public static String getPrimarykey(){
		String result = UUID.randomUUID().toString().replace("-", "");
		return result;
	}
	
	/**
	 * 获得token
	 * @param key	String
	 * @return		String
	 */
	public static String getToken(String key){
		
		String passMd5 = DigestUtils.md5Hex(key);
		
		return passMd5;
	}

	/**
	 * 驼峰转下划线
	 * 
	 * @param str
	 *            str
	 * @return String
	 */
	public static String humpToUnderline(String str) {
		if (org.apache.commons.lang3.StringUtils.isEmpty(str)) {
			return "";
		}

		StringBuffer buff = new StringBuffer("");

		for (int i = 0; i < str.length(); i++) {
			if (Character.isUpperCase(str.charAt(i))) {
				buff.append("_");
				buff.append(str.charAt(i));
				continue;
			}

			buff.append(str.charAt(i));
		}

		return buff.toString().toUpperCase();
	}


	/**
	 * 正则表达式
	 * @param sourceStr
	 * @param regex
	 * @return List<String>
	 */
	public static List<String> find(String sourceStr,String regex){
		List<String> resultList = new ArrayList<String>();
		if(sourceStr==null||"".equals(sourceStr)){
			return resultList;
		}
		Pattern pattern = Pattern.compile(regex);
		Matcher m = pattern.matcher(sourceStr);
		while(m.find()){
			String tableName = m.group();
			resultList.add(tableName);
		}
		
		return resultList;
	}

    /**
     * 是否空对象
     * @param o
     * @return boolean
     */
	public static boolean isEmpty(Object o){
		if(o==null || "".equals(toString(o))){
			return true;
		}
		return false;
	}

    /**
     * 转字符串
     * @param o
     * @return String
     */
	public static String toString(Object o){
		if(o==null){
			return "";
		}
		return String.valueOf(o);
	}

    /**
     * 转字符串
     *
     * @param values
     * @param split
     * @return String
     */
    public static String toString(List<String> values, String split){
        if(values==null||values.size()==0){
            return "";
        }
        return values.stream().collect(Collectors.joining(split));
    }

	/**
	 * 去重
	 * 
	 * @param value
	 * @param splitStr
	 * @return String
	 */
	public static String distinctStr(String value ,String splitStr){
		String[] array = StringUtilNew.split(value, splitStr);
		Set<String> set = new HashSet<String>();
		for(String str:array){
			set.add(str);
		}
		String[] array2 = set.toArray(new String[set.size()]);
		return join(array2,splitStr);
	}
	
	/**
	 * 连接字符串
	 * @param array
	 * @param joinChar 连接符
	 * @return String
	 */
	public static String join(String[] array,String joinChar){
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<array.length;i++){
			sb.append(array[i]);
			if(i<array.length-1){
				sb.append(joinChar);
			}
		}
		return sb.toString();
	}
	
	/**
	 * 
	 * 首字母大写
	 * @param str
	 * @return String
	 */
	public static String firstToUpper(String str){
		return str.substring(0, 1).toUpperCase()+str.substring(1);
	}
	
	/**
	 * 驼峰命名
	 * @param str
	 * @return String
	 */
	public static String toHump(String str){
		String result = str.toLowerCase();
		String[] strs = result.split("_");
		StringBuffer buf = new StringBuffer();
		for(int i=0;i<strs.length;i++){
			String tmp = strs[i];
			if(StringUtilNew.isNull(tmp)){
				continue;
			}
			if(i==0){
				buf.append(tmp);
			}else{
				String firstStr = tmp.substring(0,1).toUpperCase();
				if(tmp.length()>1){
					tmp=firstStr+tmp.substring(1);
				}else{
					tmp = firstStr;
				}
				buf.append(tmp);
			}
			
		}
		return buf.toString();
	}

    /**
     * 转成in sql
     * @param strs
     * @return String
     */
	public static String splitWhereIn(String strs){
        StringBuilder keys = new StringBuilder();
		if(StringUtilNew.isNull(strs)){
			return null;
		}
		String[] tmps = strs.split(",");
		
		for(String str : tmps){
			if(StringUtilNew.isNull(str)){
				continue;
			}
			if(StringUtilNew.isNull(keys.toString())){
				keys =keys.append("'").append(str).append("'");
			}else{
				keys = keys.append(",'").append(str).append("'");
			}
		}
		return keys.toString();
	}

    /**
     * 数值转字符串
     * @param strs
     * @return String
     */
	public static String listToStr(List<String> strs){
		StringBuffer result = new StringBuffer();
		if(strs == null || strs.size()==0){
			return result.toString();
		}
		
		for(String str : strs){
			if(StringUtilNew.isNull(str)){
				continue;
			}
			if(StringUtilNew.isNull(result.toString())){
				result.append(""+str+"");
			}else{
				result.append(","+str+"");
			}
		}
		return result.toString();
	}

//    /**
//     * 创建图片
//     * @param str
//     * @param width
//     * @param height
//     * @param path
//     * @return String
//     */
//	public static String createImage(String str,Integer width,Integer height,String path)
//			 {
//		String result = null;
//		Font font = new Font("宋体", Font.BOLD,30);
//		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
//		Graphics g = image.getGraphics();
//		g.setClip(0, 0, width, height);
//		g.setColor(Color.WHITE);
//		g.fillRect(0, 0, width, height);
//		g.setColor(Color.BLACK);
//		g.setFont(font);
//		Rectangle clip = g.getClipBounds();
//		FontMetrics fm = g.getFontMetrics(font);
//		int ascent = fm.getAscent();
//		int descent = fm.getDescent();
//		int y = (clip.height-(ascent+descent))/2+ascent;
//		for(int i=0;i<6;i++){
//			g.drawString(str, i*680, y);
//		}
//		g.dispose();
//		FileInputStream input = null;
//		try{
//			ImageIO.write(image, "png", new FileOutputStream(new File(path)));
//			input = new FileInputStream(new File(path));
//			byte[] byts = new byte[input.available()];
//			input.read(byts);
//			input.close();
//			result = new String(Base64.encodeBase64(byts));
//		}catch(Exception e){
//			e.getMessage();
//		}finally{
//			try {
//				input.close();
//			} catch (IOException e) {
//				input = null;
//			}
//		}
//		return result;
//	}

    /**
     * 获得24长度UUID
     * @return	String
     */
    public static String get24UUID(){
       UUID id = UUID.randomUUID();
       String[] ids=id.toString().split("-");
       return ids[0]+ids[1]+ids[4];
    }



}
