package at.cpickl.graphql.graphqldemo

import org.springframework.stereotype.Component
import java.util.*
import kotlin.streams.toList

fun newUUID() = UUID.randomUUID().toString()

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

    fun savePost(post: Post): Post {
        posts.add(0, post)
        return post
    }
}

@Component
class AuthorDao {
    private val authors = mutableListOf(
            Author("1", "Christoph"),
            Author("2", "John")
    )

    fun getAuthorById(authorId: String): Author? = authors.firstOrNull { it.id == authorId }

    fun saveAuthor(author: Author) {
        authors.add(0, author)
    }
}

@Component
class AuthorService(
        private val authors: AuthorDao,
        private val posts: PostDao
) {
    fun saveAuthor(authorInp: AuthorInput): Author {
        val author = Author(newUUID(), authorInp.name)
        authors.saveAuthor(author)
        authorInp.posts.map { Post(id = newUUID(), title = it.title, category = it.category, authorId = author.id) }.forEach {
            posts.savePost(it)
        }
        return author
    }

}
