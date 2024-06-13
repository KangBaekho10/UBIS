package org.ubis.ubis.security.oauthlogin.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.ubis.ubis.security.oauthlogin.model.SocialMember

interface SocialRepository : JpaRepository<SocialMember, Long> {
    fun findByName(name: String) : SocialMember?
}