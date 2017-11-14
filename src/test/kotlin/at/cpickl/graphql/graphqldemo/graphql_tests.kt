package at.cpickl.graphql.graphqldemo

import org.assertj.core.api.Assertions.assertThat
import org.json.JSONObject
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.RequestEntity
import org.springframework.http.ResponseEntity
import org.springframework.test.context.junit4.SpringRunner
import java.net.URI

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GraphqlTests {

    @Autowired private lateinit var rest: TestRestTemplate

    @Test fun writeAndQueryPosts() {
        // http://graphql.org/learn/serving-over-http/
        val query = """query { recentPosts(count: 10, offset: 0) { id title category author { id name } } }"""
        val request = RequestEntity("""
            {
             "query": "$query"
            }
            """,
                HttpHeaders().apply { add("content-type", "application/json") },
                HttpMethod.POST, URI.create("/graphql"))
        val response = rest.exchange(request, object : ParameterizedTypeReference<Any>() {}) as ResponseEntity

        assertThat(response.statusCodeValue).isEqualTo(200)

        val json = JSONObject(response.body.toString())
        assertThat(json.has("data")).isTrue()
        val data = json.getJSONObject("data")
        assertThat(data.has("recentPosts")).isTrue()
        val posts = data.getJSONArray("recentPosts")
        assertThat(posts.length()).isEqualTo(0)
    }

}
