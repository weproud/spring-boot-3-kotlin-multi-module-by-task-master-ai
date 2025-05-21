package com.weproud.domain.rds.entity.order

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

/**
 * 주소 값 객체
 */
@Embeddable
data class Address(
    @Column(name = "zipcode", nullable = false)
    val zipcode: String,
    
    @Column(name = "address1", nullable = false)
    val address1: String,
    
    @Column(name = "address2", nullable = true)
    val address2: String? = null,
    
    @Column(name = "recipient_name", nullable = false)
    val recipientName: String,
    
    @Column(name = "recipient_phone", nullable = false)
    val recipientPhone: String
) {
    protected constructor() : this("", "", null, "", "")
}
