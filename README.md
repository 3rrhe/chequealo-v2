# chequealo-v2
Web marking system.\
Works on Spring (a Java Framework) and MySql as database.

Have an authentication in the API using JWT.

## API Docs
### Auth endpoints
#### Login
URL: `/api/v1/auth/login`\
Method: `POST`\
body: `{"username": "prueba", "password": "prueba"}`\
Response: `{
"status": 200,
"message": "User logged",
"data": {
"username": "prueba",
"password": "prueba",
"bearer": "Bearer eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiJjaGVxdWVhbG9KV1QiLCJzdWIiOiJycm9jYWUiLCJhdXRob3JpdGllcyI6WyJST0xFX1VTRVIiXSwiaWF0IjoxNjM0MzUzODk1LCJleHAiOjE2MzQzNTQxOTV9.Yj-NwS9bVBeNLfXpbC2wVa9Ux6M8AXUdYixZwGkTDHEG_9rXKn14tryZZqtq3snqO-qcmbFniqON9E4zwdjYMA"
}
}`

#### Register
URL: `/api/v1/auth/register`\
method: `POST`\
body: `{
"email": "prueba@prueba.com",
"password": "prueba",
"firstName": "Prueba",
"lastName": "Test",
"address": "Prueba"
}`\
Response: `{
"status": 200,
"message": "User created success",
"data": {
"username": "prueba",
"password": "prueba",
"bearer": "Bearer eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiJjaGVxdWVhbG9KV1QiLCJzdWIiOiJycm9jYWUiLCJhdXRob3JpdGllcyI6WyJST0xFX1VTRVIiXSwiaWF0IjoxNjM0MzQwNDExLCJleHAiOjE2MzQzNDA3MTF9.1vJrkjhn1IeghUmrRn2_9kZekLwL_OtE8JM2u3Q0SAFadT_k275ZHOxTRAV31dQhSpCUVMiCLFhzZdHZeM7t1w"
}
}`

### Users endpoints
#### Users list
URL: `/api/v1/users`\
Method: `GET`\
Response: `{
"status": 200,
"message": "Profiles list",
"data": [
{
"firstName": "Prueba",
"lastName": "Prueba",
"address": "Prueba",
"jobPosition": "job_office",
"schedule": {
"name": "Employee schedule",
"income": "06:00:00",
"lunchStart": "12:00:00",
"lunchEnd": "13:00:00",
"output": "18:00:00",
"id": 1
},
"user": {
"username": "prueba",
"role": "ROLE_USER",
"password": "prueba",
"email": "prueba@prueba.com",
"enabled": true,
"createdAt": "2021-10-15T23:26:51.000+00:00",
"profiles": null,
"id": 1,
"updateAt": "2021-10-15T23:26:51.000+00:00"
},
"markings": null,
"id": 2
}
]
}`

### Schedules endpoints
#### Schedules list
URL: `/api/v1/schedules`\
Method: `GET`\
Response: `{
"status": 200,
"message": "Schedules list",
"data": [
{
"name": "Employee schedule",
"income": "06:00:00",
"lunchStart": "12:00:00",
"lunchEnd": "13:00:00",
"output": "18:00:00",
"id": 1
},
{
"name": "Boos schedule",
"income": "08:00:00",
"lunchStart": "11:00:00",
"lunchEnd": "13:30:00",
"output": "16:00:00",
"id": 2
},
{
"name": "Security schedule",
"income": "05:00:00",
"lunchStart": "12:00:00",
"lunchEnd": "12:30:00",
"output": "20:00:00",
"id": 3
}
]
}`

### Markings endpoints
#### Markings list
URL: `/api/v1/markings`\
Method: `GET`\
Response: `{
"status": 200,
"message": "Markings list",
"data": [
{
"entryAt": "2021-10-16T03:12:34.000+00:00",
"departureAt": "2021-10-16T03:13:44.000+00:00",
"delays": null,
"id": 7
}
]
}`

#### Marking entry
URL: `/api/v1/profiles/{id}/markings`\
Method: `POST`\
Response: `{
"status": 200,
"message": "Entry marking created",
"data": {
"entryAt": "2021-10-16T04:01:10.513+00:00",
"departureAt": null,
"delays": [
{
"createdAt": "2021-10-16T04:01:10.700+00:00",
"id": 11
}
],
"id": 10
}
}`

#### Marking departure
URL: `/api/v1/profiles/{id}/markings/{marking_id}`\
Method: `PUT`\
Response: `{
"status": 200,
"message": "Departure marking created",
"data": {
"entryAt": "2021-10-16T04:01:11.000+00:00",
"departureAt": "2021-10-16T04:04:19.607+00:00",
"delays": [
{
"createdAt": "2021-10-16T04:01:11.000+00:00",
"id": 11
},
{
"createdAt": "2021-10-16T04:04:19.607+00:00",
"id": 12
}
],
"id": 10
}
}`

NOTE: This project has a frontend project worked in Angular (v12), you can see the code of that project **[clicking here](https://gitlab.com/umg6/chequealo-v2)**.