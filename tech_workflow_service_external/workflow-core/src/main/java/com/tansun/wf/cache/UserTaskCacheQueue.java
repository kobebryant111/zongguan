package com.tansun.wf.cache;

import java.util.LinkedList;

public class UserTaskCacheQueue extends LinkedList<UserTaskCache> {

    private static final UserTaskCacheQueue userTaskCacheQueue = new UserTaskCacheQueue();

    private UserTaskCacheQueue(){}

    public static UserTaskCacheQueue getInstance() {
        return userTaskCacheQueue;
    }
}