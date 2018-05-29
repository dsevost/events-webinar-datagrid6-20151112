package com.redhat.dsevosty;

import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.client.hotrod.configuration.ConfigurationBuilder;
import org.infinispan.client.hotrod.impl.ConfigurationProperties;
 
public class JbossDataGridRemoteCache {

  public static void main(String[] args) {
    final String KEY="key";

    ConfigurationBuilder builder = new ConfigurationBuilder();
    builder.addServer().host("127.0.0.1").port(ConfigurationProperties.DEFAULT_HOTROD_PORT);
    RemoteCacheManager cacheManager = new RemoteCacheManager(builder.build());

    RemoteCache<String, String> cache = cacheManager.getCache();

    if (cache.get(KEY) != null) {
      System.out.printf("Value '%s' for key '%s' is already contained by Cache\n", cache.get(KEY), KEY);
    } else {
      cache.put(KEY, "value");
      System.out.printf("%s = %s\n", KEY, cache.get(KEY));
    }

    cacheManager.stop();
  }
}
