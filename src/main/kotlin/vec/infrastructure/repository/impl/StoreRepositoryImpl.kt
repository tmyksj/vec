package vec.infrastructure.repository.impl

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import vec.domain.repository.StoreRepository

@Component
@Transactional
interface StoreRepositoryImpl : StoreRepository
