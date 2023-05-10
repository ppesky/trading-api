package ai.trading4u.api.config;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.benmanes.caffeine.cache.Caffeine;

@EnableCaching
@Configuration
public class CacheConfig {
	
	@Bean
	CacheManager cacheManager() {

		CaffeineCache allowedCache = new CaffeineCache(
				DomainCacheType.allowed_account.name(), 
				Caffeine.newBuilder().expireAfterWrite(1, TimeUnit.HOURS).build()
		);

		CaffeineCache authkeyCache = new CaffeineCache(
				DomainCacheType.auth_key.name(), 
				Caffeine.newBuilder().expireAfterAccess(1, TimeUnit.DAYS).build()
		);

		CaffeineCache exchangekeyCache = new CaffeineCache(
				DomainCacheType.exchange_key.name(), 
				Caffeine.newBuilder().expireAfterAccess(1, TimeUnit.DAYS).build()
		);

		CaffeineCache oneMinCache = new CaffeineCache(
				DomainCacheType.one_min.name(), 
				Caffeine.newBuilder().expireAfterWrite(1, TimeUnit.MINUTES).build()
		);

	    SimpleCacheManager manager = new SimpleCacheManager();
	    manager.setCaches(Arrays.asList(
	    		allowedCache, 
	    		authkeyCache,
	    		exchangekeyCache,
	    		oneMinCache
	    		)
	    );
	    
	    return manager;
	}

//  @Autowired CacheManager cacheManager;
//  public void removeAllCaches() {
//      for (String cacheName : cacheManager.getCacheNames()) {
//          cacheManager.getCache(cacheName).clear();
//      }
//  }
	
}
