# CMPE 202 Gladiators Team Project

## Introduction
This is the repository containing the CMPE 202 team project from Team Gladiators. It entails an end2end learning management application similar to Canvas. 
It is intended for three user groups: 
* Students
* Faculty
* Administrators

The project is developed as an Android application and the tech stack includes Spring Boot (Java) for the backend and Kotlin (with composables) for the frontend. It is developed using
Android Studio and IntelliJ IDEA. It also includes JUnit tests for each of the components for both frontend and backend. Additionally, a non-relational (MongoDB) database is used to store all 
critical data such as assignments, courses, grades, notifications, semester, and users. In the app backend, there are API calls defined as HTTP Get, Post, and Put requests to have the backend 
interact with the database. 

This project has been developed using the model-view-viewModel (MVVM) and Clean design patterns which are typical for mobile applications. The idea is to have clear separation
of concerns and abstraction of the presentation logic to make the code look presentable. More on that later. 

## Team members and contributions
* **Ajinkya Mate:** Designed wireframes, worked on user profile page, course content page, connected both to backend, and wrote this README file. 
* **Gerardo Moreno Jovel:** Designed wireframes, worked on notification page, connected MongoDB Atlas to the database, worked on assigning grades for the faculty, and grades for specific assignments. 
* **Vivek Ponnala:** Set up the backend, worked on UML diagrams, database, deployed backend to AWS, and worked on a list of students for a specific course. 
* **Napoleon Salazar:** Set up the codebase for frontend, worked on UML diagrams for MVVM + Clean, set up the course landing page and sidebar, navigation, login, course details, and post and list assignments, list courses by userType, and announcement. 

## Feature Set
From the user perspective, this application includes the following main features: 

* **Authentication:** All users are able to log into the application and interact with it based on their respective roles. Logins use JWT tokens for each user.
* **Homepage:** Displays the current and previous courses. Students and faculty can see the courses they are enrolled in. Administrators can see the list of courses that the faculty has taught in current and past semesters. These courses are displayed in a list and sorted by semester. 
* **Courses:** Allows faculty to view and edit the course content, view the list of students enrolled in the course, and create assignments. Students can view the course content and the assignments posted by faculty. Admins can view the list of students in each course but will not see the grades. 
* **Notifications:** For each class, notifications are displayed in a separate section in the sidebar. Both faculty and students can view these. 
* **Profile:** Users, faculty, and admins can view their profiles in the system. On the same page, they each also have an option to log out of the system. Additionally, clicking on a student in the list of students in the course will also link to that user’s profile page. 

 ## Design Decision
 **Backend:** The team decided to build a Spring Boot application in Java for the backend, with gradle as the build tool. This aligned well with the team's overall experience and comfort. Spring Boot is a popular framework to define endpoints and connect to our database. Gradle also allowed the team to use a configuration-based approach and have more control over the development process. It also proved to be a great choice for a build tool considering Gradle's support for Kotlin, which we used for our frontend.

 **Frontend:** The team decided to use Kotlin along with Jetpack Compose for the frontend. This is because Kotlin is directly compatible with Java which we are using for the backend, and Jetpack Compose allowed the team to write all the frontend code in Kotlin instead of having it split between Kotlin for presentation and retrieval logic (model and viewModel) and XML for the display. Jetpack Compose simplified and sped up UI design as it uses a single code base architecture to reduce boilerplate code and lines of code.

 **Database:** The team used a MongoDB database to store the necessary data in a non-relational format. The data stored includes assignments, courses, grades, notifications, semester, and users. The choice was beneficial owing to MongoDB's ability to map objects from any programming language as well as easy storage and retrieval of data. 

 **Authentication:** The team generated JWT tokens for each user in the database for successful authentication, owing to their stateless and self-contained nature which reduced the backend storage requirements. JWT tokens also provide security as they are signed by a key that's only known to the server. 

 **Testing:** The team tested their components using JUnit tests. These are built into Android Studio by default. That's why they are very popular in Android apps because they provide developers with the simplicity of writing precise tests and identify bugs early on. These tests can also serve as documentation for the code and that is useful in large codebases and systems. 

 **API:** The APIs were RESTfully designed using the Spring Boot Java backend which is hosted on AWS, and the designed endpoints allowed easy access to all parts of the database. The input and output of each API is in the JSON format. 

 **Cloud:** The backend application was later hosted on an AWS instance in the cloud, where it works and can be accessed using the appropriate hostname. 

## Cloud Configuration
Our configuration employs the Nginx web server software to set up web servers. It includes instructions for managing HTTPS traffic with SSL/TLS encryption and for directing HTTP traffic to HTTPS. In the initial server block, SSL is configured for the server hosting at vivekcmpe.csproject.org, utilizing certificates administered by Certbot—an automated tool for managing Let’s Encrypt SSL certificates. Additionally, this block establishes a reverse proxy to route requests to a local server operating on port 8080, managing both static content and proxy requests. The second server block is responsible for managing traffic for cmpe202.csproject.org. Here, it redirects all HTTP requests to HTTPS for heightened security. In cases where the host does not match cmpe202.csproject.org, a 404 error is served, indicating that the requested resource was not found. This setup is standard for environments necessitating secure, encrypted communications, such as live production servers.

![Cloud Config](https://github.com/gopinathsjsu/team-project-gladiators/blob/main/docs/cloud/Cloud.png)

## Authentication
Our app is authenticated using a basic Jwt Token flow 

#### Login Flow
Clients need to authenticate to get **jwtToken** used by authenticated calls
![Login Flow](https://github.com/gopinathsjsu/team-project-gladiators/blob/main/docs/auth/AuthFlow.png)

#### Authenticated Routes
Clients need to add jwtToken to request headers to get **jwtToken** used by authenticated calls
![Authenticated Routes](https://github.com/gopinathsjsu/team-project-gladiators/blob/main/docs/auth/AuthenticatedCall.png)

#### Auth Architecture
Authenticated calls follow a **Chain Of Responsibility**  pattern where Handlers (known as interceptors for this library) are registered for a singleton instance of the http client used for the entire app
![Authenticated Routes](https://github.com/gopinathsjsu/team-project-gladiators/blob/main/docs/auth/AuthClassDiagram.png)

 ## Diagrams
 **MVVM**
 ![MVVM](https://github.com/gopinathsjsu/team-project-gladiators/blob/main/docs/front_end/MVVM.png)

**Clean**
![CLEAN](https://github.com/gopinathsjsu/team-project-gladiators/blob/main/docs/front_end/CLEAN.png)

**UI State**
![STATE](https://github.com/gopinathsjsu/team-project-gladiators/blob/main/docs/front_end/StateDiagram.png)

**Backend component diagram**
![Backend Component Diagram](https://github.com/gopinathsjsu/team-project-gladiators/blob/main/docs/uml/backend_component_diagram.png)

**Frontend component diagram**
![Frontend Component Diagram](https://github.com/gopinathsjsu/team-project-gladiators/blob/main/docs/uml/frontend_component_diagram.png)

**System deployment diagram**
![image](https://github.com/gopinathsjsu/team-project-gladiators/assets/93010008/5420854e-fd6b-4813-9da4-dc1cc0716db8)

## Wireframes
<img width="433" alt="image" src="https://github.com/gopinathsjsu/team-project-gladiators/assets/93010008/7bcd01f9-e712-447b-8055-a4f2a122a8d0">

<img width="572" alt="image" src="https://github.com/gopinathsjsu/team-project-gladiators/assets/93010008/ddd3d4eb-dcf9-4ec1-98fa-bf5e41350e70">

<img width="432" alt="image" src="https://github.com/gopinathsjsu/team-project-gladiators/assets/93010008/3c268ced-c7c1-462d-8a92-e4511fe3fa26">

## Team workflow
The team worked in an agile setting with scrum meetings every week, and sprints that each lasted 2 weeks. All team members were responsible for at least one component throughout the project and were also assigned tasks for each sprint. 

### XP Values
Here are two XP values that the team followed throughout the project implementation: 
* **Communication:** All members in the team used Discord to communicate and host weekly meetings. Throughout each sprint, the team collaborated closely and the members updated one another of any progress, changes, and issues. Team members also helped one another in resolving issues to ensure success of the whole team and each individual member. In case of any unexpected changes to the actual code, personal schedules, and meeting times, all members were given fair notice.
* **Feedback:** Given the varying levels of experience that each member brought to the table, feedback was crucial for each team member and for the success of the overall team. All pull requests made were reviewed thoroughly and feedback was given immediately, both in the pull requests as well as on Discord. The progress of each team member was also discussed in each scrum meeting and at the end of each meeting, the team closely analyzed the sprint burndown charts and retrospectives. That way, both on the individual and the team level, everyone knew what they must improve upon for the upcoming sprint.

## Agile reports
=======

## Agile reports
- [Weekly Scrum Report](https://github.com/gopinathsjsu/team-project-gladiators/blob/main/docs/agile/Weekly_Scrum_Report.pdf)

- [Sprint Report](https://github.com/gopinathsjsu/team-project-gladiators/blob/main/docs/agile/Sprint_Report.pdf)

## Important links
* **Project repository:** https://github.com/gopinathsjsu/team-project-gladiators
* **Jira board:** https://sjsu-gladiators.atlassian.net/jira/software/projects/GC/boards/3

## Component diagrams

### Backend component diagram

![Backend Component Diagram](https://github.com/gopinathsjsu/team-project-gladiators/blob/main/docs/uml/backend_component_diagram.png)

### Frontend component diagram

![Frontend Component Diagram](https://github.com/gopinathsjsu/team-project-gladiators/blob/main/docs/uml/frontend_component_diagram.png)
