package vec.domain.entity

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.annotation.Version
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.time.LocalDateTime
import java.util.*

/**
 * ユーザ
 */
data class User(

    /**
     * ID
     */
    @field:Id
    val id: String = UUID.randomUUID().toString(),

    /**
     * メールアドレス
     */
    val email: String,

    /**
     * パスワード
     */
    val passwordEncrypted: String,

    /**
     * ユーザのアカウントの有効期限が切れているかどうか
     */
    val isAccountExpired: Boolean,

    /**
     * ユーザがロックされているかどうか
     */
    val isAccountLocked: Boolean,

    /**
     * ユーザの資格情報の有効期限が切れているかどうか
     */
    val isCredentialsExpired: Boolean,

    /**
     * ユーザーが有効かどうか
     */
    private val isEnabled: Boolean,

    /**
     * 管理者ユーザ権限を持つかどうか
     */
    val hasAuthorityAdmin: Boolean,

    /**
     * 消費者ユーザ権限を持つかどうか
     */
    val hasAuthorityConsumer: Boolean,

    /**
     * 作成日時
     */
    @field:CreatedDate
    val createdDate: LocalDateTime? = null,

    /**
     * 更新日時
     */
    @field:LastModifiedDate
    val lastModifiedDate: LocalDateTime? = null,

    /**
     * バージョン
     */
    @field:Version
    val version: Long? = null,

    ) : UserDetails {

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return mutableListOf<GrantedAuthority>().apply {
            if (hasAuthorityAdmin) {
                add(SimpleGrantedAuthority("ROLE_ADMIN"))
            }

            if (hasAuthorityConsumer) {
                add(SimpleGrantedAuthority("ROLE_CONSUMER"))
            }
        }
    }

    override fun getPassword(): String {
        return passwordEncrypted
    }

    override fun getUsername(): String {
        return email
    }

    override fun isAccountNonExpired(): Boolean {
        return !isAccountExpired
    }

    override fun isAccountNonLocked(): Boolean {
        return !isAccountLocked
    }

    override fun isCredentialsNonExpired(): Boolean {
        return !isCredentialsExpired
    }

    override fun isEnabled(): Boolean {
        return isEnabled
    }

}
