package com.work.ppm.commons.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 地址解析工具
 */
public class RemoteAddrType {
    /**
     *分析ip是否属于所属规则函数,属于返回true，否哦返回false
     * @param ip ip地址
     * @param ruleDict 正则 地址
     * @return boolean
     */
    public static boolean analysisIp(String ip, String ruleDict) {
        List<Object> list = new ArrayList<>();
        if(ruleDict.contains("(")) {
            String[] temps = ruleDict.split("&");
            for (int i = temps.length - 1; i >= 0; i--) {
                temps[i] = temps[i].replace("(", "");
                temps[i] = temps[i].replace(")", "");
                list.add(ipDictAna1(temps[i]));
            }
        } else {
            list.add(ipDictAna1(ruleDict));
        }
        if(compareIpDict(ip,list,0)) {
            return true;
        }
        return false;
    }

    /**
     * IP与解析后的dict进行比对
     * @param ip 地址
     * @param list 一个ip地址的数组
     * @param no 没有任何意义的参数，方法多态而已
     * @return boolean
     */
    private static boolean compareIpDict(String ip, List<Object> list,int no) {
        String[] ip_list = ip.split("\\.");
        int[] IpList = new int[4]; //整形的ip数组
        for (int i = 0; i < ip_list.length; i++){
            IpList[i] = Integer.parseInt(ip_list[i]);
        }

        for (int i = 0; i < list.size(); i++){
            List<Object> tmp = (List<Object>) list.get(i);
            boolean flag = true; //全局比较标识位，如果ip包含在规则内则为true，反之为false
            for (int j = 0; j < 4; j++){
                if (tmp.get(j).getClass().getName().equals("java.util.ArrayList")){ //判断如果此段ip规则为数组则进行数组比较
                   List<Object> tmp1 = (List<Object>) tmp.get(j);
                    boolean flag1 = false; //数组比较标识位
                    for (int m = 0; m < tmp1.size(); m++){
                        if (Integer.parseInt(tmp1.get(m).toString()) == IpList[j]){
                            flag1 = true; //比较相当置标志位为1，跳出循环
                            break;
                        }
                    }

                    if (flag1 == false){ //标志位为0则表示匹配失败，置全局标志位为0跳出循环
                        flag = false;
                        break;
                    }
                } else {
                    if (Integer.parseInt(tmp.get(j).toString()) == IpList[j]){ //匹配段为整数
                        continue;
                    } else {
                        flag = false;
                        break;
                    }
                }
            }

            if (flag == true){ //判断全局标志位为1则表示该段规则内匹配成功，函数返回true
                return true;
            }
        }
        return false;
    }

    /**
     * 对()内的表达式进行分析
     * @param ruleDict  ip地址
     * @return List<Object>
     */
    private static List<Object> ipDictAna1(String ruleDict) {
        String[] a1 = ruleDict.split("\\."); //以点分割为4组进行分析
        List<Object> re_dict1 = new ArrayList();
        List<Object> tmps;
        for (int i = 0; i <= 3; i++){
            if (a1[i].contains("[")){ //如果地址中包含[]对其中的内容做分析
                a1[i] = a1[i].replace("[", "");
                a1[i] = a1[i].replace("]", "");
                tmps = ipDictAna2(a1[i]);
                re_dict1.add(tmps);
            }  else {
                re_dict1.add(Integer.parseInt(a1[i]));//没有[]就是一个数字直接添加
            }
        }
        return re_dict1;
    }

    /**
     * 对[]内的表达式进行分析
     * @param ruleDict IP地址
     * @return List<Object>
     */
    private static List<Object> ipDictAna2(String ruleDict) {
        String[] a2 = ruleDict.split("\\|");//[]中内容以“|”符号分割
        List<Object> re_dict2 = new ArrayList();
        List<Object> tmps;
        for (int i = 0; i < a2.length; i++){
            if (a2[i].contains("{")){//如果含有{}对其中的内容做分分析
                tmps = ipDictAna3(a2[i]);
                for (int j = 0; j < tmps.size(); j++){
                    re_dict2.add(tmps.get(j));
                }
            } else if (a2[i].contains("-")){ //如果含有-的补全连续数据
                int startip = Integer.parseInt(a2[i].split("-")[0]);
                int endip = Integer.parseInt(a2[i].split("-")[1]);
                for (int j = startip; j <= endip; j++){
                    re_dict2.add(j);
                }
            } else {
                re_dict2.add(Integer.parseInt(a2[i]));
            }
        }
        return re_dict2;
    }

    /**
     * 对{}内的表达式进行分析
     * {}内容为递增数列
     * @param ruleDict ip地址
     * @return List<Object>
     */
    private static List<Object> ipDictAna3(String ruleDict) {
        List<Object> re_dict3 = new ArrayList<>();
        String new_dict3 = (ruleDict.replace("{", "")).replace("}", "");
        int startip = Integer.parseInt((new_dict3.split(";")[0]).split("~")[0]);
        int endip = Integer.parseInt((new_dict3.split(";")[0]).split("~")[1]);
        int step = Integer.parseInt(new_dict3.split(";")[1]);
        for (int i = startip; i <= endip; i=i+step){
            re_dict3.add(i);
        }
        return re_dict3;
    }

    /**
     * IP与解析后的dict进行比对
     * @param ip ip地址
     * @param ruleDicts IP地址集合
     * @return boolean
     */
    public static boolean compareIpDict(String ip, List<Object> ruleDicts) {
        String[] ip_list = ip.split("\\.");
        boolean falg = true;
        for (int i = 0; i < ip_list.length; i++){
            int ipNun = Integer.parseInt(ip_list[i]);
            // 如果不存在 则直接跳出循环
            if(!ruleDicts.contains(ipNun)) {
                falg = false;
                break;
            }
        }
        return falg;
    }
}
