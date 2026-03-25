# FisherMate

FisherMate is a Java-based application designed to help manage and streamline day-to-day activities related to fishing operations—such as organizing records, tracking key details, and supporting simple workflows that reduce manual effort.


---

## Project Overview 

**FisherMate** helps users to:
- Keep structured records instead of scattered notes/spreadsheets
- Quickly view and update information through a simple interface
- Improve consistency and reduce time spent on repetitive tracking tasks

Typical use-cases may include managing:
- Fisher / user information
- Trips or daily activity logs
- Catch / inventory records
- Basic reports or summaries


---

## Tech Stack

- **Language:** Java
- **UI / Styling:** CSS
- **Build Tool:**  Maven
- **Database:**  MySQL 
- **Framework:** JavaFX

---

## Key Features

- CRUD operations (create, view, update, delete) for core entities
- Clean UI with basic styling (CSS)
- Input validation and user-friendly messages
- Search / filter 
- Simple reporting
- Organized code structure 
- Reusable components / helper utilities
- Easy-to-extend design for new features

---

## How to Run (Local Setup)

### 1) Prerequisites
Make sure you have:
- **Java JDK** (recommended: 17+ )
-  **Maven**
- **Database** (MySQL)

### 2) Clone the repository
```bash
git clone https://github.com/IndikaMadhushan/FisherMate.git
cd FisherMate
```

### 3) Configure (if applicable)
If your project uses a database or config file:
- Update database URL/username/password in the configuration file  
  *(Example: `application.properties`, `db.properties`, or similar)*

### 4) Run the project


```bash
mvn clean install
mvn spring-boot:run
```




