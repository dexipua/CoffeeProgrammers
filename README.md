# School

## Overview

The School is a Java-based application developed using the Spring framework. It provides functionalities to manage various aspects of a school environment, including grades, school news, students, subjects, schedules, teachers, and user-specific news.

## Key Roles and Functionalities

- Students:
   - View accessible information about grades and subjects.

- Teachers:
   - Add grades for students.
   - Manage students enrolled in their subjects.

- Chief-teacher:
   - All functionalities of teachers.
   - Create and delete users (including students and teachers).
   - Manage subjects, including creation, deletion, and assignment of teachers.

## Functionalities of the Project:

Mark:
- Manages student grades across different subjects.
- Allows creation, updating, and deletion of grades.
- Retrieves all grades for a specific student or subject.

SchoolNews:
- Manages school news, including creation, updating, and deletion of news articles.
- Retrieves all school news for viewing.

Student:
- Handles student management within the school.
- Allows creation, updating, and deletion of students.
- Retrieves detailed information about a student, including their average grade.
- Retrieves a list of students based on various criteria (name, teacher, etc.).

Subject:
- Manages the subjects taught in the school.
- Allows creation, updating, and deletion of subjects.
- Assigns and removes teachers for subjects.
- Adds and removes students from subjects.
- Retrieves a list of subjects based on various criteria (name, teacher, students).

SubjectDate:
- Manages class schedules for subjects.
- Creates and deletes class schedule entries.
- Retrieves class schedules for students and subjects.

Teacher:
- Handles teacher management within the school.
- Allows creation, updating, and deletion of teachers.
- Retrieves detailed information about a teacher.
- Retrieves a list of teachers based on various criteria (name, students, etc.).

UserNews
- Manages user-specific news tailored for users of the system (e.g., teachers or students).

## Usage

To run the School Management System:

1. Clone the repository:


## Technologies Used

- Java: 19
- Spring Boot: 3.2.5
- React: 18.0.0
- MUI: library of React components
- H2: database for our project

## Installation Instructions

1. Clone the repository:

   ```git clone https://github.com/dexipua/CoffeeProgrammers.git```

2. Navigate to the project directory:

   ```cd CoffeeProgrammers```

3. Build the project:

   ```mvn clean install```


## Usage Instructions

1. Start the backend server:

   ```mvn spring-boot:run```

2. Navigate to the frontend directory:

   ```cd frontend```

3. Before this if you have something on port 3000 use:

   ```npx kill-port 3000```
4. Install dependencies and start the frontend server:

   ```npm install```
   ```npm start```

4. Open your browser and go to http://localhost:3000 to access the application.

---

## Contribution Guidelines

1. Fork the repository.
2. Create a new branch for your feature or bugfix:

   ```git checkout -b feature-name```

3. Commit your changes:

   ```git commit -m "Description of your changes"```

4. Push to your branch:

   ```git push origin feature-name```

5. Create a pull request.

---

## Contact

For any questions or suggestions, feel free to open an issue.