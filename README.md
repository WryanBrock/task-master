# Task Management System

## Overview

This project is a **Java-based task management system** that provides functionalities to create, manage, and process tasks efficiently. The system includes database integration, task processing logic, and a user interface for interaction.

## Features

- **Task Creation & Management**: Define and store tasks in a database.
- **Database Integration**: Uses a DAO (Data Access Object) pattern for seamless database interactions.
- **User Interface**: A graphical interface for managing tasks.
- **Task Processing**: Implements logic to process tasks efficiently.
- **Workload Distribution**: Balances task execution workload.

## Project Structure

```
├── src/
│   ├── Application.java           # Main entry point of the application
│   ├── controllers/
│   │   ├── MainController.java    # Handles main UI interactions
│   │   ├── TaskWindowController.java # Handles task window UI logic
│   ├── database/
│   │   ├── DBConnect.java         # Manages database connections
│   │   ├── DAO.java               # Data Access Object for database operations
│   ├── models/
│   │   ├── Task.java              # Defines task structure
│   │   ├── TaskImpl.java          # Implements task logic
│   │   ├── WorkLoad.java          # Manages workload distribution
│   ├── processing/
│   │   ├── Processing.java        # Handles task processing
```

## Technologies Used

- **Java 17+**
- **JavaFX** (For UI)
- **JDBC/MySQL** (For database connectivity)
- **Maven/Gradle** (For dependency management)

## Setup & Installation

1. Clone the repository:

   ```bash
   git clone https://github.com/your-username/your-repository.git
   cd your-repository
   ```

2. Ensure you have **Java 17+** installed.

3. Configure the database:
   - Update `DBConnect.java` with your database credentials.
   - Run the provided SQL script to set up the tables.

4. Compile and run:

   ```bash
   mvn clean install
   java -jar target/task-manager.jar
   ```

## Contribution

Feel free to contribute by forking the repository, making changes, and submitting a pull request.

## License

This project is licensed under the MIT License. See `LICENSE` for more details.
