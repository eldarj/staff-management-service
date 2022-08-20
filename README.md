# Staff Service
> A backend service for staff shift scheduling.

## Description

Users can have 2 roles with different permission levels.

##### Staff User:
* [x] Can register
* [x] Can view his/her schedule for any period of time (up to 1 year)
* [x] Can see his/her coworker schedules

#### Admin:
* [x] Can edit/delete all users,
* [x] Can create/edit/delete schedule for users
* [x] Can order users list by accumulated work hours per arbitrary period (up to 1
  year).

--- 

## Getting Started
### Running the service

To run the service as well as a MySQL container, simply use the `run.sh` script,
or simply execute the following:
```
mvn clean package -DskipTests
docker build -t local/staff-management-service:latest .

docker-compose up
```

The above will compile the service into it's target folder, and then build the image.

Either use `docker-compose up` to run both the db and app containers, or run each
respectively with `docker run`.

If you want to start the app with `docker run` make sure you provide the needed
params, network, env vars, etc. You can find these in `docker-compose.yaml`

### Init Data

Whatever way you run the service, it will feed initial data into the db. See
`resources/data.sql`

These can also be used when invoking the API - e.g.
`ejahijagic:123456`

The SQL file contains the hashed password which is `123456` for all users.

### Running tests
Unit tests can be found within `test/java/com/ejahijagic/staffmanagementservice`. 

Run each in your IDE or with `mvn test`

---

## REST Endpoints
The set of endpoints this service provides are the following

```
> **Note**
> Registration endpoint is public, all others require an Authorization header with
> credentials in basic format username:password
```



### Registration
* /api/auth/register POST

Considering its basic auth required only, the above provides just
an endpoint for registering as a new user.

### User Management & Staff Scheduling
#### Users
* Returns all users
    * /api/users GET
* Return single user
  * /api/users/user-id GET
* Edits a user
    * /api/users/user-id PUT
* Deletes a user
    * /api/users/user-id DELETE

#### Shifts (Schedules)
* Admin/User views shifts for any period of time (up to 1 year)
    * /api/shifts GET
    * Query Params:
      * user-id
      * from
      * to
* Admin creates a schedule
    * /api/shifts POST
* Admin updates a schedule
    * /api/shifts PUT
* Admin deletes a schedule
    * /api/shifts DELETE

#### Accumulated Working Hours
* Get list of users ordered by accumulated working hours
  * /api/hours
  * Query params:
    * order
    * from
    * to

```
> **Note**
> All from and to date parameters have to be supplied in yyyy-MM-dd
```

---
## Data and Schema
#### Shift (Schedule)
* Work date
* User
* Shift length in hours

#### User
* Username
* Password
* Role
* Working hours aggregation

---
## Auth Roles & Permissions on Endpoints Matrix
##### Staff User:
* Can register 
  * **_public_**
* Can view his/her schedule for any period of time (up to 1 year) 
  * **SHIFTS_SEE**
* Can see his/her coworker schedules 
  * **SHIFTS_SEE**

#### Admin:
* Can edit/delete all users
  * **USERS_SEE, USERS_CREATE, USERS_EDIT, USERS_DELETE**
* Can create/edit/delete schedule for users 
  * **SHIFTS_SEE, SHIFTS_CREATE, SHIFTS_EDIT, SHIFTS_DELETE**
* Can order users list by accumulated work hours per arbitrary period (up to 1
  year) 
  * **SHIFT_HOURS_SEE**
---
### Todo Items
- [x] Git
- [x] Readme
- [ ] Open API
- [x] Controllers, Services & Repos
  - [x] Users CRUD
  - [x] Shifts CRUD
  - [x] Working hours
  - [x] Aggregation of shifts per user
  - [x] Order by accumulated working hours
- [x] Auth
  - [x] Register
  - [x] Roles
  - [x] Permissions
  - [x] Authorization on endpoints
- [x] Unit tests
- [ ] Integration, End to end tests
- [x] http request scratches in `test/resources`
- [x] Run app in docker
- [x] Database
  - [x] Feed data on init
  - [x] Run db in docker