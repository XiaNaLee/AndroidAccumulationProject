package com.accumulation.lee.utils.common;

import java.util.List;

/**
 * Created by liyong on 15/5/6.
 * list集合工具类
 */
public class ListUtil {

    /**
     * 判断集合是否为空
     *
     * @param list
     * @return
     */
    public static <T> boolean isBlank(List<T> list) {

        if (list == null || list.size() == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断集合是否不为空
     *
     * @param list
     * @return
     */
    public static <T> boolean isNoBlank(List<T> list) {
        if (list != null && list.size() > 0) {
            return true;
        } else {
            return false;
        }
    }


}
