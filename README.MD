# 📋 User and Event Management System (Java CLI)

## 🧩 Overview

This is a **Command-Line Interface (CLI) system** developed in Java for managing users and events. It allows you to:
- Register users
- Register events
- Link users to events
- List events by category
- Search users by name

The program is designed using a **state machine architecture** that controls user navigation through different commands interactively.


## 🚀 Features

### 🔁 Command Menu (State: `initialmenu`)
The program starts in an interactive menu waiting for a user command. You can list all valid commands by typing `listcommands`.

### 📜 Command Listing (State: `listcommands`)
Prints all available commands defined by:
```java
EnumSet.allOf(State.class)
```
Excludes the `none` state, which indicates invalid commands.

### 👤 User Registration (State: `userregister`)
Registers a new user with the following details:
- **Name**
- **CPF** (Brazilian personal document - parsed and validated)
- **Date of Birth** (format: `yyyy-MM-dd`)

The `Person` object is created and stored in memory.

### 🔍 Search User by Name (State: `searchuserbyname`)
Searches for users by name (partial match). Displays:
- Name
- CPF
- Date of Birth
- Registered Event (if any)

### 🗓️ Event Registration (State: `eventregister`)
Allows registering a new event with the following fields:
- Name
- Description
- Address
- Category ID (from 0 to 2 based on predefined array)
- Date and Time (`yyyy-MM-dd HH:mm`)

The event is added to the global `asEventList`.

### 🗂️ List Events by Category (State: `listeventsbycategory`)
Searches for events by category name and displays:
- Name
- Description
- Date and Time
- Address
- Category
- Participant names
- Event occurrence status (past, present, or future)

### 🏷️ List Events by Tag (State: `listeventsbytag`)
⚠️ **Not implemented yet.** The system displays an informational message instead.

### 🔗 Link User to Event (State: `attach_user_to_event`)
Associates a previously registered user to an existing event via:
- Exact event name
- User’s CPF

The link is bidirectional: the event stores the user CPF, and the user references the event.

### ❌ Exit Program (State: `exit`)
Immediately terminates the program using `System.exit(0)`.


## 🧠 Technical Analysis

### State-Driven Architecture
The program uses an enum-based state machine (`State`) to control user interaction and command flow.

### In-Memory Storage
Data is stored in static lists within `Data_Read_Write`:
- `asPersonaList` — list of users
- `asEventList` — list of events

⚠️ Persistence is not implemented in this version.

### External Classes (Required)
- `Person`: Represents a user with fields like name, CPF, and birthdate.
- `EventUnique`: Represents an event with details like name, category, and participant list.
- `Data_Read_Write`: Provides static access to user and event lists.
- `State`: Enum defining all available states/commands.

### Input and Parsing
- User input is read using `Scanner`
- Dates are parsed using Java’s `LocalDateTime`
- Integer and string inputs are validated

### Event Occurrence Logic
The helper method `getString()` determines the temporal status of an event:
- Future event: `"The event has not happened yet."`
- Same day: `"The event is happening now."`
- Past: `"The event has already occurred."`

## 🛠️ How to Use

### 📦 Requirements
- JDK 11 or higher
- Java-compatible terminal

### ▶️ Compile and Run
```bash
javac -d out src/org/example/*.java
java -cp out org.example.Main
```

### 💬 Available Commands

| Command                | Description                                   |
|------------------------|-----------------------------------------------|
| `listcommands`         | Lists all available commands                  |
| `userregister`         | Registers a new user                          |
| `searchuserbyname`     | Searches for users by name                    |
| `eventregister`        | Registers a new event                         |
| `listeventsbycategory` | Lists events by category                      |
| `listeventsbytag`      | (Not implemented)                             |
| `attach_user_to_event` | Links a user to a selected event              |
| `exit`                 | Exits the program                             |


## 🧪 Future Improvements

- Add file or database persistence
- Implement `listeventsbytag`
- Add internationalization and language support


## 📌 Conclusion

This project is a solid foundation for a basic CLI-based user and event management system. Its state-driven design makes it easy to expand and maintain, and it's well-suited for educational purposes. Future improvements could turn it into a complete and robust system.
