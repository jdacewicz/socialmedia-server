
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
- Implement api authorization;



## API Reference

### Authentication

#### Register user

`
  🟢 POST
`
`
    /api/auth/register
`

| Request Part | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `profileImage` | `File` | Profile picture |
| `request` | `RegisterRequest` | **Required**. Register request |

| Register Request Field | Type     | Validation | Description                |
| :-------- | :------- | :----- | :------------------------- |
| `email` | `String` | **Proper Email, Not Blank** | Login |
| `password` | `String` | **8-24 Characters, Not Blank** | Password |
| `firstname` | `String` | **2-16 Characters, Not Blank** | Firstname |
| `lastname` | `String` | **2-16 Characters, Not Blank** | Lastname |

#### Authenticate user

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

### Users

#### Get logged user

`
  🔵 GET
`
`
    /api/users
`

#### Get user by id

`
  🟢 POST
`
`
    /api/users/${id}
`

| Path Variable | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `id` | `String` | **Required**. User's id |

#### Report user

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

#### Ban user premanently

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

#### Ban user temporary

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


### Posts

#### Get basic post by id

`
  🔵 GET
`
`
    /api/posts/${id}
`

| Path Variable | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `id` | `String` | **Required**. Post's id |

#### Get random basic posts

`
  🔵 GET
`
`
    /api/posts
`

#### Get basic posts by user id

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

#### Get comments by basic post id

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

#### Create basic post

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

| Creation Request Field | Type     | Validation | Description                |
| :-------- | :------- | :----- | :------------------------- |
| `content` | `String` | **Max 255 characters, Not Blank** | Content |

#### Report basic post

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

#### React to basic post

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

#### Delete basic post

`
  🔴 DELETE
`
`
    /api/posts/${id}
`

| Path Variable | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `id` | `String` | **Required**. Post's id |

## Contributing

Contributions are always welcome!

## Authors

- [@jdacewicz](https://www.github.com/jdacewicz)

