package vec

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.FullyQualifiedAnnotationBeanNameGenerator

@ComponentScan(nameGenerator = FullyQualifiedAnnotationBeanNameGenerator::class)
@SpringBootApplication
class VecApplication

fun main(args: Array<String>) {
    runApplication<VecApplication>(*args)
}
