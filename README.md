# chequealo-v2
Web marking system.\
Works on Spring (a Java Framework) and MySql as database.

Have an authentication in the API using JWT.

The project has three different roles for users:
* ROLE_USER
* ROLE_ADMIN
* ROLE_REPORTER

This project can generate reports using Jasper Reports, using API endpoints to generate them, and you can find the generated .pdf files in the `/reports ` directory. 

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
"username": "admin",
"password": "826455",
"role": "ROLE_ADMIN",
"bearer": "Bearer eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiJjaGVxdWVhbG9KV1QiLCJzdWIiOiJhZG1pbiIsImF1dGhvcml0aWVzIjpbIlJPTEVfVVNFUiJdLCJpYXQiOjE2MzY1MTUxMTUsImV4cCI6MTYzNjUxNTQxNX0.mprcpVn8LLroAxTMucN1RkVHsW9I8K_6rms6_lE-T3wV_FQWE6g-iw7r9-k1W8V97N9-BQbhA4htBTvEvfj-1g"
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
"role": "ROLE_USER",
"bearer": "Bearer eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiJjaGVxdWVhbG9KV1QiLCJzdWIiOiJwcnVlYmEiLCJhdXRob3JpdGllcyI6WyJST0xFX1VTRVIiXSwiaWF0IjoxNjM2NTE1NjEyLCJleHAiOjE2MzY1MTU5MTJ9.KuIHtkw_RkX9t6wMkPkdkhlQ0RkfYnTodmhEO1Kvt1AY93zPq_3pZG2uk8SQqVWOcSRO8TmqbQyqVyAx9q0TxQ"
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
"cuser": {
"username": "prueba",
"role": "ROLE_USER",
"password": "prueba",
"email": "prueba@prueba.com",
"enabled": true,
"createdAt": "2021-10-15T23:26:51.000+00:00",
"employees": null,
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
URL: `/api/v1/employees/{username}/markings`\
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
URL: `/api/v1/employees/{username}/markings/{marking_id}`\
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

### Reports endpoints
#### Employee markings report
URL: `/api/v1/reports/employee/{username}`\
Method: `GET`\
Response: `{
"status": 200,
"message": "Employee prueba2 markings",
"data": "reports/single-employee-marking/prueba2_employee_marking.pdf"
}`

#### Department markings report
URL: `/api/v1/reports/department/{departmentId}`\
Method: `GET`\
Response: `{
"status": 200,
"message": "Department IT markings",
"data": "reports/department-employee-marking/IT_employee_marking.pdf"
}`

#### All markings report
URL: `/api/v1/reports/markings`\
Method: `GET`\
Response: `{
"status": 200,
"message": "All markings",
"data": "reports/general-employee-marking/general_marking.pdf"
}`

#### Employees with delays report
URL: `/api/v1/reports/delays`\
Method: `GET`\
Response: `{
"status": 200,
"message": "All delays",
"data": "reports/employees-delay/delay_employees_marking.pdf"
}`

#### Employees with advances report
URL: `/api/v1/reports/advances`\
Method: `GET`\
Response: `{
"status": 200,
"message": "All advances",
"data": "reports/employees-advance/advance_employees_marking.pdf"
}`

NOTE: This project has a frontend project developed in Angular (v12), you can see the code of that project **[clicking here](https://gitlab.com/umg6/chequealo-v2)**.