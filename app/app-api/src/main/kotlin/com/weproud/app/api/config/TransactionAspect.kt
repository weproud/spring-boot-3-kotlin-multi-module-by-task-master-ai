package com.weproud.app.api.config

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

/**
 * 트랜잭션 관련 AOP 설정
 */
@Aspect
@Component
class TransactionAspect {

    private val logger = LoggerFactory.getLogger(TransactionAspect::class.java)

    /**
     * @Transactional 어노테이션이 적용된 메서드에 대한 로깅
     */
    @Around("@annotation(transactional)")
    fun logTransactionExecution(joinPoint: ProceedingJoinPoint, transactional: Transactional): Any {
        val methodName = joinPoint.signature.name
        val className = joinPoint.target.javaClass.simpleName
        val readOnly = transactional.readOnly

        logger.info("트랜잭션 시작: {}.{} (readOnly={})", className, methodName, readOnly)
        val startTime = System.currentTimeMillis()

        try {
            val result = joinPoint.proceed()
            val executionTime = System.currentTimeMillis() - startTime
            logger.info("트랜잭션 완료: {}.{} ({}ms)", className, methodName, executionTime)
            return result
        } catch (e: Exception) {
            val executionTime = System.currentTimeMillis() - startTime
            logger.error("트랜잭션 실패: {}.{} ({}ms) - {}", className, methodName, executionTime, e.message)
            throw e
        }
    }
}
