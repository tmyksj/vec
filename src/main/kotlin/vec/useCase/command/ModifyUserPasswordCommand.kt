package vec.useCase.command

import reactor.core.publisher.Mono
import vec.domain.entity.User

/**
 * ユーザのパスワードを変更します。
 */
interface ModifyUserPasswordCommand {

    /**
     * @param principal principal
     * @param currentPasswordRaw 現在のパスワード
     * @param newPasswordRaw 新しいパスワード
     * @return ユーザ
     * @throws PasswordMismatchesException
     */
    fun perform(
        principal: User,
        currentPasswordRaw: String,
        newPasswordRaw: String,
    ): Mono<User>

    class PasswordMismatchesException : RuntimeException()

}
