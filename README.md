# Microservices Java Library

This repository contains a simple microservices project as a tech task

## Table of Contents

- [Technologies Used](#technologies-used)
- [Getting Started](#getting-started)
    - [Prerequisites](#prerequisites)
    - [Installation](#installation)
- [SWAGGER-Documentation](#swagger-documentation)
- [Usage](#usage)

## Technologies Used

1. Spring, Boot.
2. ORM: Spring Data Jpa.
3. RDBMS: PostgreSQL.
4. MapStruct;
5. Authentication via bearer token (Spring Security, KeyCloak);
6. Swagger

## Getting Started

### Prerequisites

Make sure you have the following installed:

- Java (version 21 or higher)
- PostgreSQL

## Installation

1. Clone the repository:

    ```bash
    git clone https://github.com/Willygodx/Library-Microservices.git
    ```

2. Build the project:

[Note] You should do this stuff in every service!

Linux or MacOS:

 ```bash
./mvnw clean install
  ```

3. Run the application:

    ```bash
    java -jar target/discovery-service-0.0.1-SNAPSHOT.jar
    ```
   ```bash
    java -jar target/gateway-service-0.0.1-SNAPSHOT.jar
    ```
   ```bash
    java -jar target/library-api-service-0.0.1-SNAPSHOT.jar
    ```
   ```bash
    java -jar target/free-books-service-0.0.1-SNAPSHOT.jar
    ```

Gateway will start on `http://localhost:8081`.

Any endpoint should be called by this url!

## SWAGGER-Documentation

Port 5001 is for free-books-service

  ```http
  http://localhost:5001/swagger-ui.html
  ```

Port 5002 is for library-api-service

 ```http
 http://localhost:5002/swagger-ui.html
  ```

## Usage

### Endpoints

1. To begin, you need to perform Keycloak server from docker

2. Next, you need to create user on Keycloak server and give him role "ROLE_USER"

3. Then you can make http request and get bearer token for authentication. Check the example below:

[Note] Without these steps you will not be able to make requests due to Spring Security!!!


```http
POST http://localhost:8080/realms/willy/protocol/openid-connect/token
Content-Type: application/x-www-form-urlencoded

client_id = springsecurity &
client_secret = IvNWVXseF7YydbQlbJsFxl8B3nOfrNon &
username = j.daniels &
password = password &
grant_type = password
 ```

And you will get answer. For example:

```http
{
  "access_token": "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJXMUE5Z2syMGNoNUJvQzBUbF9VZU1rODlxTkFFZDFrVW1LQ1oyX0JpNGtFIn0.eyJleHAiOjE3MjY1MjUwMTUsImlhdCI6MTcyNjUyMzIxNSwianRpIjoiMTYyZDIzNjYtNjYyOC00NTE0LTllNTQtYWViZjdjZWRiMThmIiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgwL3JlYWxtcy93aWxseSIsImF1ZCI6ImFjY291bnQiLCJzdWIiOiI3NmExMTIzZS02ODdlLTQ4MDItYTA0ZS1kZTQ5ZTQ1ZjBiZTQiLCJ0eXAiOiJCZWFyZXIiLCJhenAiOiJzcHJpbmdzZWN1cml0eSIsInNlc3Npb25fc3RhdGUiOiJkY2I3ZjNmNi1hZDdlLTRhYWMtYjczYy0yMTk5MWZiZDk0MTEiLCJhY3IiOiIxIiwiYWxsb3dlZC1vcmlnaW5zIjpbImh0dHA6Ly9sb2NhbGhvc3Q6ODA3OSJdLCJyZWFsbV9hY2Nlc3MiOnsicm9sZXMiOlsiZGVmYXVsdC1yb2xlcy13aWxseSIsIlJPTEVfVVNFUiIsIm9mZmxpbmVfYWNjZXNzIiwidW1hX2F1dGhvcml6YXRpb24iXX0sInJlc291cmNlX2FjY2VzcyI6eyJhY2NvdW50Ijp7InJvbGVzIjpbIm1hbmFnZS1hY2NvdW50IiwibWFuYWdlLWFjY291bnQtbGlua3MiLCJ2aWV3LXByb2ZpbGUiXX19LCJzY29wZSI6InByb2ZpbGUgZW1haWwiLCJzaWQiOiJkY2I3ZjNmNi1hZDdlLTRhYWMtYjczYy0yMTk5MWZiZDk0MTEiLCJlbWFpbF92ZXJpZmllZCI6dHJ1ZSwibmFtZSI6IkphY2sgRGFuaWVscyIsInByZWZlcnJlZF91c2VybmFtZSI6ImouZGFuaWVscyIsImdpdmVuX25hbWUiOiJKYWNrIiwiZmFtaWx5X25hbWUiOiJEYW5pZWxzIiwiZW1haWwiOiJqLmRhbmllbHNAZ21haWwuY29tIn0.Jwz2Dq-bdPlQPwMBiBbulk9Nase90Foko-JEhx3Fzbp2ZitQdrDARI3IKLGJjwkdODprIhARa_3N5a0IjV53TuUIjSBjemOFVwvWjI0ZpAWHfaKzim6BsQxd3p9dfvzrMS0_3MkPzgQtD1UJHHWd2GxOYGN48JaG9IbxPhZ8HWJtwLVkFB7EKxhiq6cjjseNciCm5W9Z9iQwPyWwLeoUtCZtl7EBfSjp54q_p8KYRgX4zL8ceAXE29BnFagRnVSqe1Aa1hNWryfUBn0WcCbruYtDSz5y3dasVM5XmfgtWpVS132wy-LWim7NfegmogBhwN40c48MqPdYYaHqIhUF5g",
  "expires_in": 1800,
  "refresh_expires_in": 1800,
  "refresh_token": "eyJhbGciOiJIUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICIyNDEyMDk2My0yZWExLTQyOTEtYjgxZS02YjA5ZTM5ZjI3NWUifQ.eyJleHAiOjE3MjY1MjUwMTUsImlhdCI6MTcyNjUyMzIxNSwianRpIjoiYzRjZDIzNGUtY2ZjNy00ODA3LWJmNzYtYjlhZDAzOGExNTM1IiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgwL3JlYWxtcy93aWxseSIsImF1ZCI6Imh0dHA6Ly9sb2NhbGhvc3Q6ODA4MC9yZWFsbXMvd2lsbHkiLCJzdWIiOiI3NmExMTIzZS02ODdlLTQ4MDItYTA0ZS1kZTQ5ZTQ1ZjBiZTQiLCJ0eXAiOiJSZWZyZXNoIiwiYXpwIjoic3ByaW5nc2VjdXJpdHkiLCJzZXNzaW9uX3N0YXRlIjoiZGNiN2YzZjYtYWQ3ZS00YWFjLWI3M2MtMjE5OTFmYmQ5NDExIiwic2NvcGUiOiJwcm9maWxlIGVtYWlsIiwic2lkIjoiZGNiN2YzZjYtYWQ3ZS00YWFjLWI3M2MtMjE5OTFmYmQ5NDExIn0.ChRyTZ0m3_TvRJljLz6W5vJvFmVJUUxkUWT-Idj2Dnc",
  "token_type": "Bearer",
  "not-before-policy": 0,
  "session_state": "dcb7f3f6-ad7e-4aac-b73c-21991fbd9411",
  "scope": "profile email"
}
```

Here you should copy "access_token" and use it in http header "Authorization".

You can view all the other endpoints in the SWAGGER documentation.
![img](readme-images/swagger-screen1.png)
![img](readme-images/swagger-screen2.png)



