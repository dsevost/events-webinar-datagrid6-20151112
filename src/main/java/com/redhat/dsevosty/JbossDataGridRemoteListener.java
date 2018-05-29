package com.redhat.dsevosty;

import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.client.hotrod.annotation.ClientCacheEntryCreated;
import org.infinispan.client.hotrod.annotation.ClientCacheEntryModified;
import org.infinispan.client.hotrod.annotation.ClientListener;
import org.infinispan.client.hotrod.configuration.ConfigurationBuilder;
import org.infinispan.client.hotrod.event.ClientCacheEntryCreatedEvent;
import org.infinispan.client.hotrod.event.ClientCacheEntryModifiedEvent;
import org.infinispan.client.hotrod.impl.ConfigurationProperties;
 
public class JbossDataGridRemoteListener {
 
   public static void main(String[] args) throws InterruptedException {
      ConfigurationBuilder builder = new ConfigurationBuilder();
      builder.addServer().host("127.0.0.1").port(ConfigurationProperties.DEFAULT_HOTROD_PORT);
      RemoteCacheManager cacheManager = new RemoteCacheManager(builder.build());
      RemoteCache<String, String> cache = cacheManager.getCache();

      MyListener listener = new MyListener();
      cache.addClientListener(listener);

      cache.put("key1", "value1");
      cache.put("key2", "value2");
      cache.put("key1", "newValue");
      Thread.sleep(1000);

      cache.removeClientListener(listener);
      Thread.sleep(1000);
      cacheManager.stop();
   }

   @ClientListener
   public static class MyListener {

      @ClientCacheEntryCreated
      public void entryCreated(ClientCacheEntryCreatedEvent<String> event) {
         System.out.printf("Created %s%n", event.getKey());
      }

      @ClientCacheEntryModified
      public void entryModified(ClientCacheEntryModifiedEvent<String> event) {
         System.out.printf("About to modify %s%n", event.getKey());
      }
   }
}
