package at.cpickl.graphql.graphqldemo

data class Post(
        val id: String,
        val title: String,
        val category: String,
        val authorId: String
)

data class Author(
        val id: String,
        val name: String
)
