package vec.infrastructure.repository

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import vec.domain.repository.CartProductRepository

@Component
@Transactional
interface CartProductRepository : CartProductRepository
