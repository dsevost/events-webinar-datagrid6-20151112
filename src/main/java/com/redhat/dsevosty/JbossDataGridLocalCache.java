package com.redhat.dsevosty;

import org.infinispan.Cache;
import org.infinispan.manager.DefaultCacheManager;

public class JbossDataGridLocalCache {

   public static void main(String[] args) {
      DefaultCacheManager cacheManager = new DefaultCacheManager();
      Cache<String, String> cache = cacheManager.getCache();
      cache.put("key", "value");
      System.out.printf("\n\n\nkey = %s\n\n\n", cache.get("key"));
      cacheManager.stop();
   }
}
