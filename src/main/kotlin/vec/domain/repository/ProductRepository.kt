package vec.domain.repository

import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import vec.domain.entity.Product
import java.util.*

@Component
@Transactional
interface ProductRepository : ReactiveCrudRepository<Product, UUID>
