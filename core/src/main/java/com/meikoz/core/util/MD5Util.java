package com.meikoz.core.util;

import android.text.TextUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;

/**
 * Created by zhangjie on 2017/9/21.
 * Email:githubgrr@163.com
 */

public class MD5Util {

    /**
     * 加密
     *
     * @param plaintext 明文
     * @return ciphertext 密文
     */
    public static String md5(String string) {
        if (TextUtils.isEmpty(string)) {
            return "";
        }
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(string.getBytes());
            String result = "";
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result += temp;
            }
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 按key的英文字母从小到大排列
     *
     * @param map 原来的map
     * @return 排序之后的map
     */
    public static String key_sort(TreeMap<String, String> map) {
        String key_sort = "";

        TreeMap<String, String> map2;
        map2 = map;

        Set<String> keySet = map2.keySet();
        Iterator<String> iter = keySet.iterator();
        while (iter.hasNext()) {
            String key = iter.next();
            key_sort = key_sort + key + "=" + map2.get(key) + "&";
        }
        return key_sort.substring(0, key_sort.length() - 1);
    }
}
