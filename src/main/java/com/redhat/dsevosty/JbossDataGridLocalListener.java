package com.redhat.dsevosty;

import org.infinispan.Cache;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.notifications.Listener;
import org.infinispan.notifications.cachelistener.annotation.CacheEntryCreated;
import org.infinispan.notifications.cachelistener.annotation.CacheEntryModified;
import org.infinispan.notifications.cachelistener.event.CacheEntryCreatedEvent;
import org.infinispan.notifications.cachelistener.event.CacheEntryModifiedEvent;

public class JbossDataGridLocalListener {

   public static void main(String[] args) {
      DefaultCacheManager cacheManager = new DefaultCacheManager();
      Cache<String, String> cache = cacheManager.getCache();

      cache.addListener(new MyListener());

      cache.put("key1", "value1");
      cache.put("key2", "value2");
      cache.put("key1", "newValue");
      cacheManager.stop();
   }
 
   @Listener
   public static class MyListener {
 
      @CacheEntryCreated
      public void entryCreated(CacheEntryCreatedEvent<String, String> event) {
        System.out.printf("Created %s\n", event.getKey());
      }
 
      @CacheEntryModified
      public void entryModified(CacheEntryModifiedEvent<String, String> event) {
        System.out.printf("About to modify %s\n", event.getKey());
      }
   }
}
 