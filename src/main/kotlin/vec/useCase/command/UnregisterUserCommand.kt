package vec.useCase.command

import reactor.core.publisher.Mono
import vec.domain.entity.User

/**
 * ユーザの利用停止処理をします。
 */
interface UnregisterUserCommand {

    /**
     * @param principal principal
     * @return ユーザ
     */
    fun perform(
        principal: User,
    ): Mono<User>

}
