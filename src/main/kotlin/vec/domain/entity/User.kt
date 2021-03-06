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
     * 管理者ユーザの役割を持つかどうか
     */
    val hasRoleAdmin: Boolean,

    /**
     * 店員ユーザの役割を持つかどうか
     */
    val hasRoleClerk: Boolean,

    /**
     * 消費者ユーザの役割を持つかどうか
     */
    val hasRoleConsumer: Boolean,

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
            if (hasRoleAdmin) {
                add(SimpleGrantedAuthority("ROLE_ADMIN"))
            }

            if (hasRoleClerk) {
                add(SimpleGrantedAuthority("ROLE_CLERK"))
            }

            if (hasRoleConsumer) {
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

    /**
     * メールアドレスを変更します。
     * @param email メールアドレス
     * @return user
     */
    fun modifyEmail(email: String): User {
        return copy(email = email)
    }

    /**
     * パスワードを変更します。
     * @param passwordEncrypted パスワード
     * @return user
     */
    fun modifyPasswordEncrypted(passwordEncrypted: String): User {
        return copy(passwordEncrypted = passwordEncrypted)
    }

    /**
     * ユーザを無効化します。
     * @return user
     */
    fun modifyToDisabled(): User {
        return copy(isEnabled = false)
    }

}
