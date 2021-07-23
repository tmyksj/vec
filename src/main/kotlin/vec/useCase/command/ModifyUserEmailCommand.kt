package vec.useCase.command

import reactor.core.publisher.Mono
import vec.domain.entity.User

/**
 * ユーザのメールアドレスを変更します。
 */
interface ModifyUserEmailCommand {

    /**
     * @param principal principal
     * @param email メールアドレス
     * @return ユーザ
     * @throws EmailIsAlreadyInUseException
     */
    fun perform(
        principal: User,
        email: String,
    ): Mono<User>

    class EmailIsAlreadyInUseException : RuntimeException()

}
