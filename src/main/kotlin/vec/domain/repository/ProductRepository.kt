package vec.domain.repository

import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import vec.domain.entity.Product
import java.util.*

@Repository
@Transactional
interface ProductRepository : ReactiveCrudRepository<Product, UUID>
