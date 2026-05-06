<div align="center">

# 💳 Transaction Analyzer

### Multi-Thread Edition

*A parallel CSV transaction processing engine built in Java*

![Java](https://img.shields.io/badge/Java-8%2B-ED8B00?style=for-the-badge\&logo=openjdk\&logoColor=white)
![NetBeans](https://img.shields.io/badge/NetBeans-IDE-1B6AC6?style=for-the-badge\&logo=apache-netbeans-ide\&logoColor=white)
![Swing](https://img.shields.io/badge/GUI-Java%20Swing-4A90D9?style=for-the-badge)
![Threads](https://img.shields.io/badge/Threads-Up%20to%2032-brightgreen?style=for-the-badge)

</div>

---

## 📌 Table of Contents

* [Overview](#-overview)
* [Features](#-features)
* [Project Structure](#-project-structure)
* [How It Works](#-how-it-works)
* [Performance](#-performance)
* [Getting Started](#-getting-started)
* [CSV Format](#-csv-format)
* [Technologies Used](#-technologies-used)
* [Key Concepts](#-key-concepts)

---

## 🧠 Overview

This project processes large financial transaction datasets in **parallel** using Java's `ExecutorService` and `Callable`.

It splits the CSV file into chunks, assigns each chunk to a separate thread, and merges all results at the end — reducing execution time significantly compared to single-threaded processing.

A **Swing GUI** is included for easy interaction.

---

## ✨ Features

* ⚡ Parallel Processing (multi-threaded CSV handling)
* 📊 Statistics (total, average, max, min)
* 🟢 Deposits vs 🔴 Withdrawals
* 🏆 Top Users Ranking
* 🖥️ GUI using Java Swing
* ⏱️ Execution Time measurement
* 🛡️ Fault Tolerance (skip invalid rows)
* 🔢 Configurable Threads (1–32)

---

## 📁 Project Structure

```
project/
│
├── Result.java             # Holds statistics + merge logic
├── TransactionTask.java    # Processes a chunk (Callable)
├── ParallelProcessor.java  # Core processing logic
└── ProcessorGUI.java       # GUI
```

---

## ⚙️ How It Works

1. Read CSV file
2. Split data into chunks
3. Each chunk → separate thread
4. Each thread processes data
5. Results merged into final result
6. Output shown in GUI

---

## 🚀 Performance

| Mode          | Threads | Time   |
| ------------- | ------- | ------ |
| Single Thread | 1       | 900 ms |
| Multi-thread  | 4       | 300 ms |
| Multi-thread  | 8       | 150 ms |

> Parallel processing reduces execution time significantly.

---

## 🚀 Getting Started

### Requirements

* Java 8+
* NetBeans (recommended)

### Run

```
Run ProcessorGUI.java
```

---

## 📄 CSV Format

```csv
transactionId,userId,amount,type
1,101,250.00,deposit
2,102,80.50,withdraw
```

---

## 🛠️ Technologies Used

* Java
* ExecutorService
* Callable & Future
* Java Swing

---

## 💡 Key Concepts

### 🔹 Multi-threading

Each thread processes part of the dataset → faster execution.

### 🔹 Thread Pool

Using `ExecutorService` to manage threads efficiently.

### 🔹 Architecture Pattern

**Master-Worker Pattern**

* Main thread distributes tasks
* Worker threads process data
* Results merged

### 🔹 Design Patterns

* Thread Pool Pattern
* Map-Reduce Style
* Separation of Concerns

### 🔹 Thread Safety

* No shared data between threads
* Each thread has independent result
* Merging done in main thread

---

<div align="center">

**Developed by Mohamed Khaled Khedr**

</div>
<div align="center">

**Developed by Mohamed Reda AbdElfatah**

</div>
<div align="center">

**Developed by Mohamed Gamal Belal**

</div>
