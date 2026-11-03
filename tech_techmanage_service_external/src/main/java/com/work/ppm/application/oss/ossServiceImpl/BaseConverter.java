package com.work.ppm.application.oss.ossServiceImpl;

import com.github.pagehelper.Page;

import java.util.List;

public interface BaseConverter<P, V> {

    /**
     * convertToPo
     *
     * @param vo vo对象
     * @return P
     */
    P convertToPo(V vo);

    /**
     * convertToVo
     *
     * @param po po对象
     * @return V
     */
    V convertToVo(P po);


    /**
     * convertToPoList
     *
     * @param voList vo集合
     * @return List<P>
     */
    List<P> convertToPoList(List<V> voList);

    /**
     * convertToVoList
     *
     * @param poList po集合
     * @return List<V>
     */
    List<V> convertToVoList(List<P> poList);

    /**
     * convertToPoPage
     * 用于Page类型的内部集合转换
     *
     * @param voList vo集合
     * @return Page<P>
     */
    default Page<P> convertToPoPage(List<V> voList) {
        List<P> convertedList = convertToPoList(voList);
        //参数为Page类型
        if (voList instanceof Page) {
            Page<P> page = (Page<P>) voList;
            if (null != convertedList) {
                //清除原集合
                page.clear();
                //替换转换后的集合
                page.addAll(convertedList);
            }
            return page;
        }
        //参数不是Page类型
        Page<P> page = new Page<>();
        page.addAll(convertedList);
        return page;
    }

    /**
     * convertToVoPage
     * 用于Page类型的内部集合转换
     *
     * @param poList po集合
     * @return Page<V>
     */
    default Page<V> convertToVoPage(List<P> poList) {
        List<V> convertedList = convertToVoList(poList);
        //参数为Page类型
        if (poList instanceof Page) {
            Page<V> page = (Page<V>) poList;
            if (null != convertedList) {
                //清除原集合
                page.clear();
                //替换转换后的集合
                page.addAll(convertedList);
            }
            return page;
        }
        //参数不是Page类型
        Page<V> page = new Page<>();
        page.addAll(convertedList);
        return page;
    }
}

