package at.cpickl.graphql.graphqldemo

import com.coxautodev.graphql.tools.GraphQLMutationResolver
import com.coxautodev.graphql.tools.GraphQLQueryResolver
import com.coxautodev.graphql.tools.GraphQLResolver
import org.springframework.stereotype.Component
import java.util.UUID
import kotlin.streams.toList


data class Post(
        val id: String,
        val title: String,
        val category: String,
        val authorId: String
)

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

data class Author(
        val id: String,
        val name: String
)

@Component
class AuthorDao {
    private val authors = listOf(
            Author("1", "Christoph"),
            Author("2", "John")
    )

    fun getAuthorById(authorId: String): Author? = authors.firstOrNull { it.id == authorId }
}

@Component
class PostQuery(private val postDao: PostDao) : GraphQLQueryResolver {
    fun recentPosts(count: Int, offset: Int): List<Post> = postDao.getRecentPosts(count, offset)
}

@Component
class PostResolver(private val authorDao: AuthorDao) : GraphQLResolver<Post> {
    fun author(post: Post): Author? = authorDao.getAuthorById(post.authorId)
}

@Component
class PostMutation(private val postDao: PostDao) : GraphQLMutationResolver {
    fun writePost(title: String, category: String, authorId: String): Post = postDao.savePost(title, category, authorId)
}

@Component
class AuthorResolver(private val postDao: PostDao) : GraphQLResolver<Author> {
    fun posts(author: Author): List<Post> = postDao.getAuthorPosts(author.id)
}

/*
val CustomUUIDScalar = GraphQLScalarType("UUID", "UUID", object : Coercing<UUID, String> {

    override fun serialize(input: Any): String? = when (input) {
        is String -> input
        is UUID -> input.toString()
        else -> null
    }

    override fun parseValue(input: Any): UUID? = parseLiteral(input)

    override fun parseLiteral(input: Any): UUID? = when (input) {
        is StringValue -> UUID.fromString(input.value)
        else -> null
    }
})
 */
