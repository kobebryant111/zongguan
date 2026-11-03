package com.tansun.wf.activiti.util;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

public class ProcessDefineLockUtil {
    private static ConcurrentHashMap<String,ReentrantLock> processLockMap = new  ConcurrentHashMap<String,ReentrantLock>();

    public  static void initLock(String processDefineId){
        ReentrantLock lock = null;
        synchronized (processLockMap){
            lock = processLockMap.get(processDefineId);
            if(lock == null){
                lock = new ReentrantLock();
                processLockMap.put(processDefineId,lock);
                System.out.println(0);
            }else {
                System.out.println(lock.hashCode());
            }
        }
    }

    public static  void lock(String processDefineId){

        processLockMap.get(processDefineId).lock();
        System.out.println( processLockMap.get(processDefineId).hashCode());
    }

    public static  void unLock(String processDefineId){
        processLockMap.get(processDefineId).unlock();
        System.out.println( processLockMap.get(processDefineId).hashCode());
    }
}
