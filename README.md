
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

Creates user report.

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

Updates user status and revokes all bans.

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
            "unsorted": true
        },
        "unpaged": false
    },
    "size": 5,
    "sort": {
        "empty": false,
        "sorted": false,
        "unsorted": true
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

Creates report of basic post with given id.

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

Deletes basic post with given id

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

Returns comment with given id.

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

Creates report of basic post with given id.

`
  🟢 POST
`
`
    /api/comments/${id}/report
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

Deletes comment with given id.

`
  🔴 DELETE
`
`
    /api/comments/${id}
`

| Path Variable | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `id` | `String` | **Required**. Comment's id |

&nbsp;
### Reactions

#### Get reaction by id

Returns reaction with given id.

`
  🔵 GET
`
`
    /api/reactions/${id}
`

| Path Variable | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `id` | `String` | **Required**. Reaction's id |

Example response:
```json
{
    "reactionId": "657f0ee069f6415fe42404f9",
    "name": "example name",
    "image": {
        "url": "http://localhost:8081/data/reactions/657f0ee069f6415fe42404f9/kXtEE6Sihz6dZBKG.jpg"
    }
}
```

&nbsp;
#### Get reactions by active

Returns page of reactions by active.

`
  🔵 GET
`
`
    /api/reactions
`

| Request Param | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `active` | `boolean` | **Required**. Is reaction active |
| `pageNumber` | `int` | **Required**. Page number of returned reactions |
| `pageSize` | `int` | **Required**. Page size of returned reactions |

Example response:
```json
{
    "content": [
        {
            "reactionId": "657f0ee069f6415fe42404f9",
            "name": "example name",
            "image": {
                "url": "http://localhost:8081/data/reactions/657f0ee069f6415fe42404f9/kXtEE6Sihz6dZBKG.jpg"
            }
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
            "unsorted": true
        },
        "unpaged": false
    },
    "size": 5,
    "sort": {
        "empty": false,
        "sorted": false,
        "unsorted": true
    },
    "totalElements": 1,
    "totalPages": 1
}
```

&nbsp;
#### Get archived reactions

Returns page of archived reactions.

`
  🔵 GET
`
`
    /api/reactions/archived
`
| Request Param | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `pageNumber` | `int` | **Required**. Page number of returned reactions |
| `pageSize` | `int` | **Required**. Page size of returned reactions |

Example response:
```json
{
    "content": [
        {
            "reactionId": "657f0ee069f6415fe42404f9",
            "name": "example name",
            "image": {
                "url": "http://localhost:8081/data/reactions/657f0ee069f6415fe42404f9/kXtEE6Sihz6dZBKG.jpg"
            }
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
            "unsorted": true
        },
        "unpaged": false
    },
    "size": 5,
    "sort": {
        "empty": false,
        "sorted": false,
        "unsorted": true
    },
    "totalElements": 1,
    "totalPages": 1
}
```

&nbsp;
#### Create reactions

Creates and returns new reaction.

`
  🟢 POST
`
`
    /api/reactions
`

| Request Body | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `reactionImage` | `File` | **Required**. Reaction's image |
| `reactionRequest` | `ReactionRequest` | **Required**. Reaction creation request |

| Reaction Request Field | Type     | Validation | Description                |
| :-------- | :------- | :----- | :------------------------- |
| `name` | `String` | **2-32 Characters, Not Blank** | Name of reaction |

Example response:
```json
{
    "reactionId": "657f0ee069f6415fe42404f9",
    "name": "example name",
    "image": {
        "url": "http://localhost:8081/data/reactions/657f0ee069f6415fe42404f9/kXtEE6Sihz6dZBKG.jpg"
    }
}
```

&nbsp;
#### Update reaction

Updates reaction with given id.

`
  🟡 PUT
`
`
    /api/reactions/${id}
`

| Path Variable | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `id` | `String` | **Required**. Reaction's id |

| Request Body | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `reactionImage` | `File` | **Required**. New reaction's image |
| `reactionUpdateRequest` | `ReactionUpdateRequest` | **Required**. Reaction update request |

| Reaction Request Field | Type     | Validation | Description                |
| :-------- | :------- | :----- | :------------------------- |
| `name` | `String` | **2-32 Characters, Not Blank** | New name of reaction |

&nbsp;
#### Activate reaction by id

Marks reaction with given id as active.

`
  🟡 PUT
`
`
    /api/reactions/${id}/activate
`

| Path Variable | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `id` | `String` | **Required**. Reaction's id |

&nbsp;
#### Deactivate reaction by id

Marks reaction with given id as deactive.

`
  🟡 PUT
`
`
    /api/reactions/${id}/deactivate
`

| Path Variable | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `id` | `String` | **Required**. Reaction's id |

&nbsp;
#### Archive reaction by id

Marks reaction with given id as archived.

`
  🟡 PUT
`
`
    /api/reactions/${id}/archive
`

| Path Variable | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `id` | `String` | **Required**. Reaction's id |

&nbsp;
#### Unarchive reaction by id

Marks reaction with given id as not archived.

`
  🟡 PUT
`
`
    /api/reactions/${id}/unarchive
`

| Path Variable | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `id` | `String` | **Required**. Reaction's id |

&nbsp;
### Searchs

#### Search Data

Returns result of search based on provided data.

`
  🔵 GET
`
`
    /api/search
`
| Request Param | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `scope` | `String` | **Required**. Scoped of searched data |
| `pageNumber` | `int` | **Required**. Page number of returned data |
| `pageSize` | `int` | **Required**. Page size of returned data |
| `phrase` | `String` | **Required**. Searched phrase |

Available scopes: `ALL`, `USERS`, `POSTS`, `BANNED_WORDS`, `REACTIONS`

Example response:
```json
{
    "reactions": {
        "content": [
            {
                "reactionId": "657f0ee069f6415fe42404f9",
                "name": "example name",
                "image": {
                    "url": "http://localhost:8081/data/reactions/657f0ee069f6415fe42404f9/kXtEE6Sihz6dZBKG.jpg"
                }
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
                "unsorted": true
            },
            "unpaged": false
        },
        "size": 5,
        "sort": {
            "empty": false,
            "sorted": false,
            "unsorted": true
        },
        "totalElements": 1,
        "totalPages": 1
    }  
}
```

&nbsp;
### Reports

#### Get reports

Returns reports based on provied data.

`
  🔵 GET
`
`
    /api/reports
`
| Request Param | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `dataType` | `String` | **Required**. Reported type of data |
| `pageNumber` | `int` | **Required**. Page number of returned data |
| `pageSize` | `int` | **Required**. Page size of returned data |

Data types: `USER`, `POST` and `COMMENT`.

Example response:
```json
{
    "reactions": {
        "content": [
            {
                "reportId": "657f0ee069f6415fe42404f9",
                "reportType": "SPAM",
                "reportDataType": "POST",
                "reportedDataId": "657f0f2169f6415fe42404fc",
                "content": "example content"
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
                "unsorted": true
            },
            "unpaged": false
        },
        "size": 5,
        "sort": {
            "empty": false,
            "sorted": false,
            "unsorted": true
        },
        "totalElements": 1,
        "totalPages": 1
    }  
}
```

&nbsp;
#### Delete report

Deletes report with given id.

`
  🔴 DELETE
`
`
    /api/reports/${id}
`

| Path Variable | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `id` | `String` | **Required**. Report's id |

&nbsp;
### Banned Words

#### Get banned words

Returns banned words based on provied data.

`
  🔵 GET
`
`
    /api/banned-words
`
| Request Param | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `pageNumber` | `int` | **Required**. Page number of returned data |
| `pageSize` | `int` | **Required**. Page size of returned data |

Example response:
```json
{
    "reactions": {
        "content": [
            {
                "wordId": "657f0ee069f6415fe42404f9",
                "word": "example word"
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
                "unsorted": true
            },
            "unpaged": false
        },
        "size": 5,
        "sort": {
            "empty": false,
            "sorted": false,
            "unsorted": true
        },
        "totalElements": 1,
        "totalPages": 1
    }  
}
```

&nbsp;
#### Ban word

Creates and returns banned word.

`
  🟢 POST
`
`
    /api/banned-words
`

| Request Body | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `BanWordRequest` | `BanWordRequest` | **Required**. Ban word request |

| Reaction Request Field | Type     | Validation | Description                |
| :-------- | :------- | :----- | :------------------------- |
| `word` | `String` | **2-22 Characters, Not Blank** | Banned word |

Example response:
```json
{
    "wordId": "657f0ee069f6415fe42404f9",
    "word": "example word"
}
```

&nbsp;
#### Delete banned word

Deletes banned word with given id.

`
  🔴 DELETE
`
`
    /api/banned-words/${id}
`

| Path Variable | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `id` | `String` | **Required**. Word's id |

&nbsp;
### Bans

#### Check Temporary Bans Expiration

Checks expiration and updates status of all users with temporary bans.

`
  🟡 PUT
`
`
    /api/bans/check
`

## Contributing

Contributions are always welcome!

## Authors

- [@jdacewicz](https://www.github.com/jdacewicz)

