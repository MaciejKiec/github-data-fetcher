# GitHub Repositories API

## Overview

This project is a Java-based API that retrieves a list of non-fork GitHub repositories for a given user. It fetches repository details along with branch information and the latest commit SHA using the [GitHub API v3](https://developer.github.com/v3).

## Technologies Used

- **Java 17**
- **Quarkus 3** 

## Installation & Running

### Prerequisites

- Java 17
- Maven

### Build & Run

```sh
mvn clean install
mvn quarkus:dev
```

The API will be available at `http://localhost:8080`.

## API Endpoints

### Get Non-Fork Repositories

#### Request

```http
GET /github/repos/{user}
```

#### Response (Success 200)

```json
[
  {
    "branches": [
      {
        "branchName": "master",
        "commitSHA": "d5dff2a3503524dc0eaf2bec956c05f25229829c"
      }
    ],
    "ownerLogin": "Thomas",
    "repositoryName": "aws_tictactoe"
  }
]
```

#### Response (User Not Found 404)

```json
{
  "status": 404,
  "message": "Cannot find user with given name"
}
```

## Testing

The project includes an integration test to verify the happy path scenario with minimal mocking.