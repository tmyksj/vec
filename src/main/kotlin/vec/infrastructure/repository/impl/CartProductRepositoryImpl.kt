package vec.infrastructure.repository.impl

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import vec.domain.repository.CartProductRepository

@Component
@Transactional
interface CartProductRepositoryImpl : CartProductRepository
