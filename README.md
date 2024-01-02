
# Socialmedia-server

Social media Spring Boot aplication that allows users to share texts/photos, discuss with other users, react and much more.


## Lessons Learned

Creating this project taught me practial use of modular monolith architecture and MongoDB.


## Tech Stack

**Server:** Java 17, Spring Boot 3.1.4, MongoDB


## Features

- Register/login to accounts;
- Post texts/photos;
- Comment posts;
- React to posts/comments;
- Report posts/comments/users;
- Ban temporary/permanently users;
- Censor words;
- Search users/posts/banned-words/reactions;
- Mangage users/banned-words/reactions/reports;
- View posts/comments/users/banned-words/reactions/reports/groups;


## Roadmap

- Customize api error message;
- Customize Page fields;
- Implement api authorization;



## API Reference

You can interact with the API through HTTP requests.

### Authentication

#### Register user

Creates new user and return access token.

`
  🟢 POST
`
`
    /api/auth/register
`

| Multipart Request Part | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `profileImage` | `File` | Profile picture |
| `request` | `RegisterRequest` | **Required**. Register request |

|Register Request Body | Type     | Validation | Description                |
| :-------- | :------- | :----- | :------------------------- |
| `email` | `String` | **Proper Email, Not Blank** | Login |
| `password` | `String` | **8-24 Characters, Not Blank** | Password |
| `firstname` | `String` | **2-16 Characters, Not Blank** | Firstname |
| `lastname` | `String` | **2-16 Characters, Not Blank** | Lastname |

Example response:
```json
{
    "accessToken": {
        "code": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwaW90cmdyeWZmMDk4QGVtYWlsLmNvbSIsImlhdCI6MTcwNDE5OTg5NiwiZXhwIjoxNzA0Mjg2Mjk2fQ.fqbtrMK4Wpbh9hmsVGDEum3zBmojuc5lu1n8XTCY7PQ",
        "active": true
    }
}
```

&nbsp;
#### Authenticate user

Creates and return new access token.

`
  🟢 POST
`
`
    /api/auth/authenticate
`

| Request Body | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `request` | `AuthenticationRequest` | **Required**. Authentication request |

| Authentication Request Field | Type     | Validation | Description                |
| :-------- | :------- | :----- | :------------------------- |
| `email` | `String` | **Proper Email, 5-32 Characters, Not Blank** | Login |
| `password` | `String` | **8-24 Characters, Not Blank** | Password |

Example response:
```json
{
    "accessToken": {
        "code": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwaW90cmdyeWZmMDk4QGVtYWlsLmNvbSIsImlhdCI6MTcwNDE5OTg5NiwiZXhwIjoxNzA0Mjg2Mjk2fQ.fqbtrMK4Wpbh9hmsVGDEum3zBmojuc5lu1n8XTCY7PQ",
        "active": true
    }
}
```

&nbsp;
### Users

#### Get logged user

Returns currently logged user.

`
  🔵 GET
`
`
    /api/users
`

Example response:
```json
{
    "userId": "657f0ee069f6415fe42404f9",
    "fullName": "John Example",
    "profilePicture": {
        "url": "http://localhost:8081/data/users/657f0ee069f6415fe42404f9/kXtEE6Sihz6dZBKG.jpg"
    }
}
```

&nbsp;
#### Get user by id

Returns user with id.

`
  🟢 POST
`
`
    /api/users/${id}
`

| Path Variable | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `id` | `String` | **Required**. User's id |

Example response:
```json
{
    "userId": "657f0ee069f6415fe42404f9",
    "fullName": "John Example",
    "profilePicture": {
        "url": "http://localhost:8081/data/users/657f0ee069f6415fe42404f9/kXtEE6Sihz6dZBKG.jpg"
    }
}
```

&nbsp;
#### Report user

Creates user report..

`
  🟢 POST
`
`
    /api/users/${id}/report
`

| Path Variable | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `id` | `String` | **Required**. User's id |

| Request Body | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `request` | `ReportRequest` | **Required**. Report request |

| Report Request Field | Type     | Validation | Description                |
| :-------- | :------- | :----- | :------------------------- |
| `reportType` | `String` | **Max 32 Characters, Not Blank** | Type of report |
| `content` | `String` | **8-24 Characters, Not Blank** | Content |

Report Types: `NUDITY`, `SPAM`, `FAKE_NEWS`, `TERRORISM`, `SELF_HARM`, `PRESECUTE`,
`DRASTIC_CONTENT`, `ILLEGAL_CONTENT`

&nbsp;
#### Ban user premanently

Creates permanent ban, updates user status and returns ban response.

`
  🟡 PUT
`
`
    /api/users/${id}/ban/permanent
`

| Path Variable | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `id` | `String` | **Required**. User's id |

| Request Body | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `userPemanentBanRequest` | `UserPemanentBanRequest` | **Required**. Ban request |

| Ban Request Field | Type     | Validation | Description                |
| :-------- | :------- | :----- | :------------------------- |
| `reason` | `String` | **Max 255 Characters, Not Blank** | Reason |

Example response:
```json
{
    "banId": "65940fe67b8df27c849d4f45",
    "type": "PERMANENT",
    "from": [
        2024,
        1,
        2,
        14,
        30,
        14,
        406372000
    ],
    "reason": "example reason"
}
```

&nbsp;
#### Ban user temporary

Creates temporary ban, updates user status and returns ban response.

`
  🟡 PUT
`
`
    /api/users/${id}/ban/temporary
`

| Path Variable | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `id` | `String` | **Required**. User's id |

| Request Body | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `userTemporaryBanRequest` | `UserTemporaryBanRequest` | **Required**. Ban request |

| Ban Request Field | Type     | Validation | Description                |
| :-------- | :------- | :----- | :------------------------- |
| `to` | `LocalDateTime` | **Not Null** | Date and time of ban rexpiration |
| `reason` | `String` | **Max 255 Characters, Not Blank** | Reason |

Example response:
```json
{
    "banId": "65940fe67b8df27c849d4f45",
    "type": "TEMPORARY",
    "from": [
        2024,
        1,
        2,
        14,
        30,
        14,
        406372000
    ],
    "to": [
        2025,
        1,
        2,
        14,
        30,
        14,
        406372000
    ],
    "reason": "example reason"
}
```

&nbsp;
#### Revoke user bans

`
  🟡 PUT
`
`
    /api/users/${id}/ban/revoke
`

| Path Variable | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `id` | `String` | **Required**. User's id |

&nbsp;
### Posts

#### Get basic post by id

Returns post with id.

`
  🔵 GET
`
`
    /api/posts/${id}
`

| Path Variable | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `id` | `String` | **Required**. Post's id |

Example response:
```json
{
    "discussionId": "657f0f2169f6415fe42404fc",
    "content": "example content",
    "creator": {
        "userId": "657f0ee069f6415fe42404f9",
        "fullName": "John Example",
        "profilePicture": {
            "url": "http://localhost:8081/data/users/657f0ee069f6415fe42404f9/kXtEE6Sihz6dZBKG.jpg"
        }
    },
    "image": {
        "url": "http://localhost:8081/data/users/657f0ee069f6415fe42404f9/657f0f2169f6415fe42404fc/J5jvJpcYWz5YilMk.jpg"
    },
    "elapsedDateTime": {
        "time": "16 days ago"
    },
    "reactionCounts": [
        "reaction": {
            "reactionId": "657f0f2169f6415fe42404fd",
            "name": "example name",
            "image": {
                "url": "http://localhost:8081/data/reactions/657f0f2169f6415fe42404fd/J5jvJpcYWz5YilMk.jpg"
            }
        },
        "count": 1
    ] 
}
```

&nbsp;
#### Get random basic posts

Returns 5 random posts marked as basic.

`
  🔵 GET
`
`
    /api/posts
`

Example response:
```json
[
    {
        "discussionId": "657f0f2169f6415fe42404fc",
        "content": "example content",
        "creator": {
            "userId": "657f0ee069f6415fe42404f9",
            "fullName": "John Example",
            "profilePicture": {
                "url": "http://localhost:8081/data/users/657f0ee069f6415fe42404f9/kXtEE6Sihz6dZBKG.jpg"
            }
        },
        "image": {
            "url": "http://localhost:8081/data/users/657f0ee069f6415fe42404f9/657f0f2169f6415fe42404fc/J5jvJpcYWz5YilMk.jpg"
        },
        "elapsedDateTime": {
            "time": "16 days ago"
        },
        "reactionCounts": [
            "reaction": {
                "reactionId": "657f0f2169f6415fe42404fd",
                "name": "example name",
                "image": {
                    "url": "http://localhost:8081/data/reactions/657f0f2169f6415fe42404fd/J5jvJpcYWz5YilMk.jpg"
                }
            },
            "count": 1
        ] 
    }
]
```

&nbsp;
#### Get basic posts by user id

Returns page of posts by creator (user) id.

`
  🔵 GET
`
`
    /api/posts/users/${userId}
`

| Path Variable | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `userId` | `String` | **Required**. User's id |

| Request Param | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `pageNumber` | `int` | **Required**. Page number of returned posts |
| `pageSize` | `int` | **Required**. Page size of returned posts |

Example response:
```json
{
    "content": [
        {
            "discussionId": "657f0f2169f6415fe42404fc",
            "content": "example content",
            "creator": {
                "userId": "657f0ee069f6415fe42404f9",
                "fullName": "John Example",
                "profilePicture": {
                    "url": "http://localhost:8081/data/users/657f0ee069f6415fe42404f9/kXtEE6Sihz6dZBKG.jpg"
                }
            },
            "image": {
                "url": "http://localhost:8081/data/users/657f0ee069f6415fe42404f9/657f0f2169f6415fe42404fc/J5jvJpcYWz5YilMk.jpg"
            },
            "elapsedDateTime": {
                "time": "16 days ago"
            },
            "reactionCounts": [
                "reaction": {
                    "reactionId": "657f0f2169f6415fe42404fd",
                    "name": "example name",
                    "image": {
                        "url": "http://localhost:8081/data/reactions/657f0f2169f6415fe42404fd/J5jvJpcYWz5YilMk.jpg"
                    }
                },
                "count": 1
            ] 
        }
    ],
    "empty": false,
    "first": true,
    "last": true,
    "number": 0,
    "numberOfElements": 1,
    "pageable": {
        "offset": 0,
        "pageNumber": 0,
        "pageSize": 5,
        "paged": true,
        "sort": {
            "empty": false,
            "sorted": false,
            "unsorted" true
        },
        "unpaged": false
    },
    "size": 5,
    "sort": {
        "empty": false,
        "sorted": false,
        "unsorted" true
    },
    "totalElements": 1,
    "totalPages": 1
}
```

&nbsp;
#### Get comments by basic post id

Returns comments of post with given id limited by comments quantity.

`
  🔵 GET
`
`
    /api/posts/${id}/comments
`

| Path Variable | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `id` | `String` | **Required**. Post's id |

| Request Param | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `commentQuantity` | `int` | **Required**. Quantity of returned comments |

Example response:
```json
[
    {
        "discussionId": "657f0f2169f6415fe42404fc",
        "content": "example content",
        "creator": {
            "userId": "657f0ee069f6415fe42404f9",
            "fullName": "John Example",
            "profilePicture": {
                "url": "http://localhost:8081/data/users/657f0ee069f6415fe42404f9/kXtEE6Sihz6dZBKG.jpg"
            }
        },
        "image": {
            "url": "http://localhost:8081/data/users/657f0ee069f6415fe42404f9/657f0f2169f6415fe42404fc/J5jvJpcYWz5YilMk.jpg"
        },
        "elapsedDateTime": {
            "time": "16 days ago"
        },
        "reactionCounts": [
            "reaction": {
                "reactionId": "657f0f2169f6415fe42404fd",
                "name": "example name",
                "image": {
                    "url": "http://localhost:8081/data/reactions/657f0f2169f6415fe42404fd/J5jvJpcYWz5YilMk.jpg"
                }
            },
            "count": 1
        ] 
    }
]
```

&nbsp;
#### Create basic post

Creates and returns basic post.

`
  🟢 POST
`
`
    /api/posts/${id}/comments
`

| Request Part | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `postImage` | `File` | **Required**. Post's image |
| `discussionCreationRequest` | `DiscussionCreationRequest` | **Required**. Request body |

| Request Field | Type     | Validation | Description                |
| :-------- | :------- | :----- | :------------------------- |
| `content` | `String` | **Max 255 characters, Not Blank** | Content |

Example response:
```json
[
    {
        "discussionId": "657f0f2169f6415fe42404fc",
        "content": "example content",
        "creator": {
            "userId": "657f0ee069f6415fe42404f9",
            "fullName": "John Example",
            "profilePicture": {
                "url": "http://localhost:8081/data/users/657f0ee069f6415fe42404f9/kXtEE6Sihz6dZBKG.jpg"
            }
        },
        "image": {
            "url": "http://localhost:8081/data/users/657f0ee069f6415fe42404f9/657f0f2169f6415fe42404fc/J5jvJpcYWz5YilMk.jpg"
        },
        "elapsedDateTime": {
            "time": "just now"
        },
        "reactionCounts": [] 
    }
]
```

&nbsp;
#### Report basic post

Creates report of basic post with id.

`
  🟢 POST
`
`
    /api/posts/${id}/report
`

| Path Variable | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `id` | `String` | **Required**. Post's id |

| Request Body | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `request` | `ReportRequest` | **Required**. Report request |

| Report Request Field | Type     | Validation | Description                |
| :-------- | :------- | :----- | :------------------------- |
| `reportType` | `String` | **Max 32 Characters, Not Blank** | Type of report |
| `content` | `String` | **8-24 Characters, Not Blank** | Content |

Report Types: `NUDITY`, `SPAM`, `FAKE_NEWS`, `TERRORISM`, `SELF_HARM`, `PRESECUTE`,
`DRASTIC_CONTENT`, `ILLEGAL_CONTENT`

&nbsp;
#### React to basic post

Adds reaction to post.

`
  🟡 PUT
`
`
    /api/posts/${postId}/react/${reactionId}
`

| Path Variable | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `postId` | `String` | **Required**. Post's id |
| `reactionId` | `String` | **Required**. Reaction's id |

Example response:
```json
[
    {
        "discussionId": "657f0f2169f6415fe42404fc",
        "content": "example content",
        "creator": {
            "userId": "657f0ee069f6415fe42404f9",
            "fullName": "John Example",
            "profilePicture": {
                "url": "http://localhost:8081/data/users/657f0ee069f6415fe42404f9/kXtEE6Sihz6dZBKG.jpg"
            }
        },
        "image": {
            "url": "http://localhost:8081/data/users/657f0ee069f6415fe42404f9/657f0f2169f6415fe42404fc/J5jvJpcYWz5YilMk.jpg"
        },
        "elapsedDateTime": {
            "time": "16 days ago"
        },
        "reactionCounts": [
            "reaction": {
                "reactionId": "657f0f2169f6415fe42404fd",
                "name": "example name",
                "image": {
                    "url": "http://localhost:8081/data/reactions/657f0f2169f6415fe42404fd/J5jvJpcYWz5YilMk.jpg"
                }
            },
            "count": 2
        ] 
    }
]
```

&nbsp;
#### Delete basic post

Deletes basic post with id

`
  🔴 DELETE
`
`
    /api/posts/${id}
`

| Path Variable | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `id` | `String` | **Required**. Post's id |

&nbsp;
### Comments

#### Get comment by id

Returns comment with id.

`
  🔵 GET
`
`
    /api/comments/${id}
`

| Path Variable | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `id` | `String` | **Required**. Comment's id |

Example response:
```json
{
    "discussionId": "657f0f2169f6415fe42404fc",
    "content": "example content",
    "creator": {
        "userId": "657f0ee069f6415fe42404f9",
        "fullName": "John Example",
        "profilePicture": {
            "url": "http://localhost:8081/data/users/657f0ee069f6415fe42404f9/kXtEE6Sihz6dZBKG.jpg"
        }
    },
    "image": {
        "url": "http://localhost:8081/data/users/657f0ee069f6415fe42404f9/657f0f2169f6415fe42404fc/J5jvJpcYWz5YilMk.jpg"
    },
    "elapsedDateTime": {
        "time": "16 days ago"
    },
    "reactionCounts": [
        "reaction": {
            "reactionId": "657f0f2169f6415fe42404fd",
            "name": "example name",
            "image": {
                "url": "http://localhost:8081/data/reactions/657f0f2169f6415fe42404fd/J5jvJpcYWz5YilMk.jpg"
            }
        },
        "count": 1
    ] 
}
```

&nbsp;
#### Create comment

Comments post.

`
  🟢 POST
`
`
    /api/comments
`

| Request Body | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `commentImage` | `File` | **Required**. Comment's image |
| `commentCreationRequest` | `CommentCreationRequest` | **Required**. Comment request |

| Report Request Field | Type     | Validation | Description                |
| :-------- | :------- | :----- | :------------------------- |
| `postId` | `String` | **Not Blank** | Id of post |

Example response:
```json
{
    "discussionId": "657f0f2169f6415fe42404fc",
    "content": "example content",
    "creator": {
        "userId": "657f0ee069f6415fe42404f9",
        "fullName": "John Example",
        "profilePicture": {
            "url": "http://localhost:8081/data/users/657f0ee069f6415fe42404f9/kXtEE6Sihz6dZBKG.jpg"
        }
    },
    "image": {
        "url": "http://localhost:8081/data/users/657f0ee069f6415fe42404f9/657f0f2169f6415fe42404fc/J5jvJpcYWz5YilMk.jpg"
    },
    "elapsedDateTime": {
        "time": "just now"
    },
    "reactionCounts": [] 
}
```

&nbsp;
#### Report basic post

Creates report of basic post with id.

`
  🟢 POST
`
`
    /api/commentss/${id}/report
`

| Path Variable | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `id` | `String` | **Required**. Comment's id |

| Request Body | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `request` | `ReportRequest` | **Required**. Report request |

| Report Request Field | Type     | Validation | Description                |
| :-------- | :------- | :----- | :------------------------- |
| `reportType` | `String` | **Max 32 Characters, Not Blank** | Type of report |
| `content` | `String` | **8-24 Characters, Not Blank** | Content |

Report Types: `NUDITY`, `SPAM`, `FAKE_NEWS`, `TERRORISM`, `SELF_HARM`, `PRESECUTE`,
`DRASTIC_CONTENT`, `ILLEGAL_CONTENT`

&nbsp;
#### React to comment

Adds reaction to comment.

`
  🟡 PUT
`
`
    /api/comments/${commentId}/react/${reactionId}
`

| Path Variable | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `commentId` | `String` | **Required**. Comment's id |
| `reactionId` | `String` | **Required**. Reaction's id |

Example response:
```json
{
    "discussionId": "657f0f2169f6415fe42404fc",
    "content": "example content",
    "creator": {
        "userId": "657f0ee069f6415fe42404f9",
        "fullName": "John Example",
        "profilePicture": {
            "url": "http://localhost:8081/data/users/657f0ee069f6415fe42404f9/kXtEE6Sihz6dZBKG.jpg"
        }
    },
    "image": {
        "url": "http://localhost:8081/data/users/657f0ee069f6415fe42404f9/657f0f2169f6415fe42404fc/J5jvJpcYWz5YilMk.jpg"
    },
    "elapsedDateTime": {
        "time": "just now"
    },
    "reactionCounts": [
        "reaction": {
            "reactionId": "657f0f2169f6415fe42404fd",
            "name": "example name",
            "image": {
                "url": "http://localhost:8081/data/reactions/657f0f2169f6415fe42404fd/J5jvJpcYWz5YilMk.jpg"
            }
        },
        "count": 2
    ] 
}
```

&nbsp;
#### Delete comment

Deletes comment with id.

`
  🔴 DELETE
`
`
    /api/comments/${id}
`

| Path Variable | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `id` | `String` | **Required**. Comment's id |

## Contributing

Contributions are always welcome!

## Authors

- [@jdacewicz](https://www.github.com/jdacewicz)

