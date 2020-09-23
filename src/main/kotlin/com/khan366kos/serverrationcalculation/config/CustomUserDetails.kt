package com.khan366kos.serverrationcalculation.config

import com.khan366kos.serverrationcalculation.models.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.*

class CustomUserDetails : UserDetails {

    private lateinit var login: String
    private lateinit var password: String
    private lateinit var grantedAuthorities: MutableCollection<GrantedAuthority?>

    companion object {
        fun fromUserEntityToCustomUserDetails(user: User): CustomUserDetails {
            val c = CustomUserDetails()
            c.login = user.userName
            c.password = user.userPassword
            c.grantedAuthorities = Collections.singletonList(SimpleGrantedAuthority(user.role?.nameRole))
            return c
        }
    }

    override fun getAuthorities(): MutableCollection<GrantedAuthority?> {
        return grantedAuthorities
    }

    override fun getPassword(): String {
        return password
    }

    override fun getUsername(): String {
        return login
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}