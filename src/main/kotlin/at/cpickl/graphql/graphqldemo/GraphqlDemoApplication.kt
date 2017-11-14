package at.cpickl.graphql.graphqldemo

import org.springframework.boot.Banner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class GraphqlDemoApplication

fun main(args: Array<String>) {
//    SpringApplication.run(GraphqlDemoApplication::class.java, *args)
    SpringApplication(GraphqlDemoApplication::class.java)
            .apply {
                setBannerMode(Banner.Mode.OFF)
                run(*args)
            }

//    SpringBoot2: runApplication<GraphqlDemoApplication>(*args)
}

//@Configuration
//class MyConfig {
//    @Bean
//    fun schema(): GraphQLSchema =
//            GraphQLSchema.newSchema().query(GraphQLObjectType.newObject().name("Query").build()).build()
//}
