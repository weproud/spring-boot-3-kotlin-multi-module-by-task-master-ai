package com.weproud.framework.redis.service

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit

/**
 * Redis 서비스
 */
@Service
class RedisService(
    private val redisTemplate: RedisTemplate<String, Any>
) {
    /**
     * 값 저장
     */
    fun set(key: String, value: Any) {
        redisTemplate.opsForValue().set(key, value)
    }
    
    /**
     * 값 저장 (만료 시간 설정)
     */
    fun set(key: String, value: Any, timeout: Long, unit: TimeUnit) {
        redisTemplate.opsForValue().set(key, value, timeout, unit)
    }
    
    /**
     * 값 조회
     */
    fun get(key: String): Any? {
        return redisTemplate.opsForValue().get(key)
    }
    
    /**
     * 값 삭제
     */
    fun delete(key: String) {
        redisTemplate.delete(key)
    }
    
    /**
     * 키 존재 여부 확인
     */
    fun hasKey(key: String): Boolean {
        return redisTemplate.hasKey(key) ?: false
    }
    
    /**
     * 만료 시간 설정
     */
    fun expire(key: String, timeout: Long, unit: TimeUnit): Boolean {
        return redisTemplate.expire(key, timeout, unit) ?: false
    }
}
