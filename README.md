# graphql-demo

* Start GraphiQL: `http://localhost:8080/graphiql` 

## Sample queries

Get the top 10 most recent posts:

```
query {
  recentPosts(count: 10, offset: 0) {
    id
    title
    category
    author {
      id
      name
    }
  }
}
```

Create new post:

```
mutation newPost {
  writePost(
    title: "title1"
    category: "category1"
    authorId: "1"
  ) {
    id
  }
}
```

## Resources

* http://www.baeldung.com/spring-graphql
* https://github.com/graphql-java/graphql-java-tools/blob/master/src/test/kotlin/com/coxautodev/graphql/tools/EndToEndSpec.kt
* https://github.com/graphql-java/graphql-spring-boot/tree/master/graphql-spring-boot-autoconfigure
