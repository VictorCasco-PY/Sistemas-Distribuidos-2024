package com.api.apireservas.utils;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CacheLoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(CacheLoggingAspect.class);

    @Before("@annotation(cacheable)")
    public void beforeCacheable(Cacheable cacheable){
        logger.info("Operacion GET de cache '{}'", cacheable.value());
    }

    @AfterReturning("@annotation(cachePut)")
    public void afterCachePut(CachePut cachePut){
        logger.info("Operacion PUT de cache '{}'", cachePut.value());
    }

    @AfterReturning("@annotation(cacheEvict)")
    public void afterCachePut(CacheEvict cacheEvict){
        logger.info("Operacion DELETE de cache '{}'", cacheEvict.value());
    }
}
