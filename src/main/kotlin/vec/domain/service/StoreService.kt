package vec.domain.service

import reactor.core.publisher.Mono
import vec.domain.entity.Clerk
import vec.domain.entity.Store
import vec.domain.entity.User

/**
 * 店舗に関するサービスです。
 */
interface StoreService {

    /**
     * 店舗に店員を追加します
     * @param store 店舗
     * @param user ユーザ
     * @return 店員
     */
    fun addClerk(
        store: Store,
        user: User,
    ): Mono<Clerk>

    /**
     * 店舗を登録します。
     * @param name 店舗名
     * @param address 所在地
     * @return 店舗
     */
    fun register(
        name: String,
        address: String,
    ): Mono<Store>

    class StoreMustExistException : RuntimeException()

    class UserMustBeUniqueException : RuntimeException()

    class UserMustExistException : RuntimeException()

}
