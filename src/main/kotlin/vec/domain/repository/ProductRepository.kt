package vec.domain.repository

import org.springframework.data.repository.NoRepositoryBean
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import vec.domain.entity.Product

@NoRepositoryBean
interface ProductRepository : ReactiveCrudRepository<Product, String>
