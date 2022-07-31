# Survey
<p align="center">
  <img alt="Survey" src="https://user-images.githubusercontent.com/64004682/180666035-45b3a09c-153c-4554-9e09-52e60cf974c0.jpg" width="300"\>
</p>


## Description
Survey is an open platform where anyone can participate in surveys on various topics. 
Registreted users can create and start their own surveys and collect statistics.

The platform supports languages: English and Russian.

## Project stack
Java EE / MySQL / HTML5 / CSS3 / Bootstrap 4 / JavaScript

## Functional roles

The platform supports the following roles and their corresponded functionality.

|                                       | ADMIN |  USER | GUEST |
|                    :-                 |  :-:  |  :-:  |  :-:  |
|Sign in/up                             |   +   |   +   |   +   |
|Request change password                |   +   |   +   |   +   |
|View available surveys                 |   +   |   +   |   +   |
|Participate in surveys                 |   +   |   +   |   +   |
|Log out                                |   +   |   +   |   -   |
|Change credentials                     |   +   |   +   |   -   |
|Survey CRUD                            |   +   |   +   |   -   |
|View survey results (created by user)  |   +   |   +   |   -   |
|Request theme                          |   -   |   +   |   -   |
|Add theme                              |   +   |   -   |   -   |
|User CRUD                              |   +   |   -   |   -   |
 
## Database schema

MySQL database is used to store data.

</p>
<p align="center">
  <kbd> <img alt="Database" src="https://user-images.githubusercontent.com/64004682/180768897-9c5fcb45-e647-4ffe-9b71-04e9ef402240.png" width="100%" style="border-radius:10px"\></kbd> 
</p>
<p align="center">Database schema</p>

## Installation

1. Clone the project.
2. Create a new MySQL database using database.sql from the data folder.
3. Change the app.properties file, located in the resources/properties/ folder, based on your database configurations.
4. Fill up any valid email service's user and password in the mail.properties file, located in the resources/properties/ folder, for password change functionality.
5. Build the project using maven.
6. Add new Tomcat 9.0.62 configuration to the project.
7. Run Tomcat and open http://localhost:8089/ on the browser.
8. Log in as admin, change password and create new users. Admin default account is admin@admin.com, password - 12345678.

## Usage

<p align="center">
  <kbd> <img alt="Edit survey" src="https://user-images.githubusercontent.com/64004682/180663466-59c3809d-6870-4e19-9d1c-a131d13dc482.gif" width="100%" style="border-radius:10px"\></kbd> 
</p>
<p align="center">Sign in and edit survey</p>
<br>

<p align="center">
  <kbd> <img alt="Survey attempt" src="https://user-images.githubusercontent.com/64004682/180663470-5ce92f47-aba5-45bd-a19c-eb308b620a42.gif" width="100^" style="border-radius:10px"\></kbd> 
</p>
<p align="center">Survey attempt</p>
<br>

<p align="center">
  <kbd> <img alt="View survey result and theme CRUD" src="https://user-images.githubusercontent.com/64004682/180663476-4ab570e0-40ae-4e96-8131-12013bb17fd5.gif" width="100%" style="border-radius:10px"\></kbd> 
</p>
<p align="center">View survey result and work with themes</p>
