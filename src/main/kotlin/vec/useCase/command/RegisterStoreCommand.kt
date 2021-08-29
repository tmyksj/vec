package vec.useCase.command

import reactor.core.publisher.Mono
import vec.domain.entity.User

/**
 * 店舗を登録します。
 */
interface RegisterStoreCommand {

    /**
     * @param principal principal
     * @param name 店舗名
     * @param address 所在地
     * @param email メールアドレス
     * @param passwordRaw パスワード
     * @return ユーザ
     * @throws EmailIsAlreadyInUseException
     */
    fun perform(
        principal: User?,
        name: String,
        address: String,
        email: String,
        passwordRaw: String,
    ): Mono<User>

    class EmailIsAlreadyInUseException : RuntimeException()

}
