package vec

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class VecApplication

fun main(args: Array<String>) {
    runApplication<VecApplication>(*args)
}
