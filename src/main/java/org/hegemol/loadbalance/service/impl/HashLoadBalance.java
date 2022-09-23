package org.hegemol.loadbalance.service.impl;

import org.hegemol.loadbalance.model.Instance;
import org.hegemol.loadbalance.service.AbstractLoadBalance;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public class HashLoadBalance extends AbstractLoadBalance<Instance> {

    private static final String INSTANCE = "-INSTANCE";

    @Override
    protected Instance doLoad(List<Instance> instances, String ip) {
        TreeMap<Long, Instance> treeMap = new TreeMap<>();
        instances.forEach(each -> {
            Long hashKey = hash(each.getAddress() + INSTANCE);
            treeMap.put(hashKey, each);
        });
        long clientHash = hash(ip);
        SortedMap<Long, Instance> sortedMap = treeMap.tailMap(clientHash);
        if (!sortedMap.isEmpty()) {
            return sortedMap.get(sortedMap.firstKey());
        }
        return treeMap.firstEntry().getValue();
    }

    private static long hash(final String key) {
        // md5 byte
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 not supported", e);
        }
        md5.reset();
        md5.update(key.getBytes(StandardCharsets.UTF_8));
        byte[] digest = md5.digest();
        // hash code, Truncate to 32-bits
        long h = 0;
        for (int i = 0; i < 4; i++) {
            h <<= 8;
            h |= ((int) digest[i]) & 0xFF;
        }
        return h;
    }
}
