package at.cpickl.graphql.graphqldemo

import com.coxautodev.graphql.tools.GraphQLMutationResolver
import com.coxautodev.graphql.tools.GraphQLQueryResolver
import com.coxautodev.graphql.tools.GraphQLResolver
import org.springframework.stereotype.Component

@Component
class PostQuery(private val postDao: PostDao) : GraphQLQueryResolver {
    fun recentPosts(count: Int, offset: Int): List<Post> = postDao.getRecentPosts(count, offset)
}

@Component
class PostResolver(private val authorDao: AuthorDao) : GraphQLResolver<Post> {
    fun author(post: Post): Author? = authorDao.getAuthorById(post.authorId)
}

@Component
class PostMutation(private val postDao: PostDao, private val authorDao: AuthorDao) : GraphQLMutationResolver {
    fun writePost(title: String, category: String, authorId: String): Post {
        authorDao.getAuthorById(authorId) ?: throw IllegalArgumentException("Unknown author ID '$authorId'!")
        return postDao.savePost(Post(newUUID(), title, category, authorId))
    }
}

data class AuthorInput(
        val name: String,
        val posts: List<PostInput>
)

data class PostInput(
        val title: String,
        val category: String
)

@Component
class AuthorMutation(private val authorService: AuthorService) : GraphQLMutationResolver {
    fun writeAuthorDeep(author: AuthorInput): Author = authorService.saveAuthor(author)
}

@Component
class AuthorResolver(private val postDao: PostDao) : GraphQLResolver<Author> {
    fun posts(author: Author): List<Post> = postDao.getAuthorPosts(author.id)
}
