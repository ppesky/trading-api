package ai.trading4u.api.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

@EnableCaching
@Configuration
public class CacheConfig {

//	@Bean
//    Caffeine caffeineConfig() {
//        return Caffeine
//                .newBuilder()
////                .maximumSize(10_000)
//                .expireAfterWrite(60, TimeUnit.MINUTES);
//    }
//
//    @Bean
//    CacheManager cacheManager(Caffeine caffeine) {
//        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
//        cacheManager.setCaffeine(caffeine);
//        return cacheManager;
//    }
    
//	@Bean
//    public CacheManager cacheManager() {
//        SimpleCacheManager cacheManager = new SimpleCacheManager();
//
//        List<CaffeineCache> caches = Arrays.stream(CacheType.values())
//            .map(cache -> new CaffeineCache(
//                cache.getName(),
//                Caffeine.newBuilder()
//                    .expireAfterWrite(cache.getExpireAfterWrite(), TimeUnit.SECONDS)
//                    .maximumSize(cache.getMaximumSize())
//                    .recordStats()
//                    .build()
//            ))
//            .collect(Collectors.toList());
//
//        cacheManager.setCaches(caches);
//        return cacheManager;
//    }
}
