package at.cpickl.graphql.graphqldemo

import org.springframework.stereotype.Component
import java.util.UUID
import kotlin.streams.toList

@Component
class PostDao {
    private val posts = mutableListOf<Post>()

    fun getRecentPosts(count: Int, offset: Int): List<Post> {
        return posts.stream()
                .skip(offset.toLong())
                .limit(count.toLong())
                .toList()
    }

    fun getAuthorPosts(authorId: String): List<Post> {
        return posts.stream()
                .filter { authorId == it.authorId }
                .toList()
    }

    fun savePost(title: String, category: String, authorId: String): Post {
        return Post(UUID.randomUUID().toString(), title, category, authorId).apply {
            posts.add(0, this)
        }
    }
}

@Component
class AuthorDao {
    private val authors = listOf(
            Author("1", "Christoph"),
            Author("2", "John")
    )

    fun getAuthorById(authorId: String): Author? = authors.firstOrNull { it.id == authorId }
}
