package com.work.ppm.application.dto.commonmanager;

import java.util.ArrayList;
import java.util.List;

/**
 * dto
 */
public class CommTreeDto {
    /**
     * 编号
     */
    private String id;
    /**
     * 文本
     */
    private String text;
    /**
     * 父id
     */
    private String pid;
    /**
     * 父编号
     */
    private String pcode;
    /**
     * 对象
     */
    private String attr;
    /**
     * 排序
     */
    private Integer ord;
    /**
     * 子节点
     */
    private List<CommTreeDto> children;
    /**
     * 搜索条件 工号/名称/拼音
     */
    private String searchUser;
    /**
     * 下个节点
     */
    private String next;
    /**
     * 当前节点
     */
    private String current;
    /**
     * 将集合转换成组织树
     * @param nodes 节点
     * @return List<CommTreeDto>
     */
    public static List<CommTreeDto> recursiveListToTree(List<CommTreeDto> nodes) {
        if(null == nodes || nodes.isEmpty()) {
            return null;
        }
        List<CommTreeDto> treeList = new ArrayList<>();
        for (CommTreeDto item: nodes) {
            // 判断是否有根节点
            if(extisRootNode(item, nodes)) {
                // 查找子节点
                CommTreeDto childrenNode = findChildernNode(item,nodes);
                treeList.add(childrenNode);
            }
        }
        return treeList;
    }

    /**
     * 递归查找子节点
     * @param parent 父节点
     * @param nodes 所有节点
     * @return CommTreeDto
     */
    private static CommTreeDto findChildernNode(CommTreeDto parent, List<CommTreeDto> nodes) {
        List<CommTreeDto> childernNode = new ArrayList<>();
        for (CommTreeDto node: nodes) {
            if(parent.getId().equals(node.getPid())) {
                childernNode.add(findChildernNode(node,nodes));
                parent.setChildren(childernNode);
            }
        }
        return parent;
    }

    /**
     * 判断是否有根节点
     * @param parant 父节点
     * @param nodes 所有节点
     * @return boolean
     */
    private static boolean extisRootNode(CommTreeDto parant, List<CommTreeDto> nodes) {
        for (CommTreeDto node: nodes) {
            if(parant.getPid().equals(node.getId())) {
                return false;
            }
        }
        return true;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPcode() {
        return pcode;
    }

    public void setPcode(String pcode) {
        this.pcode = pcode;
    }

    public String getAttr() {
        return attr;
    }

    public void setAttr(String attr) {
        this.attr = attr;
    }

    public Integer getOrd() {
        return ord;
    }

    public void setOrd(Integer ord) {
        this.ord = ord;
    }

    public List<CommTreeDto> getChildren() {
        return children;
    }

    public void setChildren(List<CommTreeDto> children) {
        this.children = children;
    }

    public String getSearchUser() {
        return searchUser;
    }

    public void setSearchUser(String searchUser) {
        this.searchUser = searchUser;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public String getCurrent() {
        return current;
    }

    public void setCurrent(String current) {
        this.current = current;
    }
}
