package vec.domain.repository

import org.springframework.data.repository.NoRepositoryBean
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Mono
import vec.domain.entity.User

@NoRepositoryBean
interface UserRepository : ReactiveCrudRepository<User, String> {

    /**
     * 管理者ユーザの役割を持つ（ないし、持たない）ユーザの数を返します。
     * @param hasRoleAdmin 管理者ユーザの役割を持つかどうか
     * @return ユーザの数
     */
    fun countByHasRoleAdmin(hasRoleAdmin: Boolean): Mono<Long>

    /**
     * 消費者ユーザの役割を持つ（ないし、持たない）ユーザの数を返します。
     * @param hasRoleConsumer 消費者ユーザの役割を持つかどうか
     * @return ユーザの数
     */
    fun countByHasRoleConsumer(hasRoleConsumer: Boolean): Mono<Long>

    /**
     * ユーザを返します。
     * @param email メールアドレス
     * @return ユーザ
     */
    fun findByEmail(email: String): Mono<User>

}
