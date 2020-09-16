package com.graduation.bbs.cache;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class RamCache {

    // 阅读缓存
    public static Map<String, Set<Integer>> VIEW_MAP = new HashMap<>();

    // 点赞缓存
    public static Map<String, Set<Integer>> LIKE_MAP = new HashMap<>();

    // 验证码
    public static Map<Integer, String> USER_VEF_CODE_MAP = new HashMap<>();



    public static Boolean getLikeAndPut(String bid, Integer userId) {
        Set<Integer> set = LIKE_MAP.get(bid);
        if (set == null || set.size() <= 0) {
            set = new HashSet<>();
            set.add(userId);
            LIKE_MAP.put(bid, set);
            return false;
        }

        if (set.contains(userId)) {
            return true;
        } else {
            set.add(userId);
//            VIEW_MAP.put(qid, set);
            return false;
        }

    }
}
