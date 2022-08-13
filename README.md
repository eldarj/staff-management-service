# Staff Service
> A backend service for staff shift scheduling.

## Description

Users can have 2 roles with different permission levels.

##### Staff User:
* Can register
* Can view his/her schedule for any period of time (up to 1 year)
* Can see his/her coworker schedules

#### Admin:
* Can edit/delete all users,
* Can create/edit/delete schedule for users
* Can order users list by accumulated work hours per arbitrary period (up to 1
  year).

--- 

## Getting Started
### Running the service

Run the service as a docker container with

### Running tests
Unit tests can be found within test/java/com/ejahijagic/staff. Run each in
your IDE or simply with
---

## REST Endpoints
The set of endpoints this service provides:
### Authentication
* /api/auth/login PUT
* /api/auth/logout DELETE
* /api/auth/register POST

The above would be a candidate for moving out of this REST API, usually
into something that handles specifically authz.

### User Management & Staff Scheduling
#### Users
* Returns all users
    * /api/users GET
    * Query Params
        * page
        * orderBy
        * shiftFromDate, shiftToDate
* Edits a user
    * /api/users/user-id PUT
* Deletes a user
    * /api/users/user-id DELETE

#### Shifts (Schedules)
* Admin/User views shifts for any period of time (up to 1 year)
    * /api/users/user-id/shifts POST
* Admin creates a schedule for a given user
    * /api/users/user-id/shifts POST
* Admin updates a schedule for a given user
    * /api/users/user-id/shifts PUT
* Admin deletes a schedule for a given user
    * /api/users/user-id/shifts DELETE

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
* Deleted (soft)


## Todos

- [x] Git
- [x] Readme
- [ ] Open API
- [x] Controllers, Services & Repos
  - [x] Users CRUD
  - [x] Shifts CRUD
  - [x] Working hours
  - [x] Aggregation of shifts per user
  - [ ] Order by accumulated working hours
- [ ] Auth
  - [x] Register
  - [ ] Authorization on endpoints
- [x] Unit tests
- [ ] Integration, End to end tests
- [ ] Run app in docker
- [ ] Database
  - [ ] Feed data on init
  - [ ] Run db in docker