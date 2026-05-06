💳 Transaction Analyzer — Multi-Thread Edition

A parallel CSV transaction processing engine built in Java, featuring a full Swing GUI and real-time analytics powered by multi-threading.


📌 Table of Contents

Overview
Features
Project Structure
How It Works
GUI Preview
Getting Started
CSV Format
Technologies Used
Key Concepts


🧠 Overview
This project processes large financial transaction datasets in parallel using Java's ExecutorService and Callable framework. It splits the CSV file into chunks, assigns each chunk to a separate thread, and merges all results at the end — significantly reducing processing time compared to a single-threaded approach.
A full Swing GUI is included for interactive file selection, thread configuration, and live result visualization.

✨ Features
FeatureDescription⚡ Parallel ProcessingSplits CSV rows across N threads simultaneously📊 Live StatisticsTotal amount, average, max/min, deposits vs withdrawals🏆 Top 10 UsersRanked by total transaction volume with share percentage🖥️ Dark-Themed GUIBuilt with Java Swing — no external UI libraries⏱️ Execution TimerMeasures and displays total processing time in ms🛡️ Fault TolerantSilently skips malformed or corrupt rows🔢 Configurable ThreadsChoose 1–32 threads via the GUI spinner

📁 Project Structure
multithreadingg/
│
├── Result.java            # Data container; holds stats and merge logic
├── TransactionTask.java   # Callable<Result> — processes one CSV chunk
├── Multithreadingg.java   # Console entry point (original CLI version)
└── TransactionGUI.java    # Full Swing GUI with SwingWorker integration

⚙️ How It Works
┌─────────────────────────────────────────────┐
│              transactions.csv                │
│  id, userId, amount, type                   │
└───────────────────┬─────────────────────────┘
                    │ split into N chunks
          ┌─────────┼──────────┐
          ▼         ▼          ▼
     [Thread 1] [Thread 2] [Thread N]
     Callable   Callable   Callable
          │         │          │
          └────┬────┘──────────┘
               ▼
        Result.merge()
               ▼
      ┌─────────────────┐
      │  Final Results  │
      │  Stats + Top 10 │
      └─────────────────┘

Read — The entire CSV is loaded into memory (header skipped).
Split — Rows are divided into equal chunks, one per thread.
Process — Each TransactionTask (Callable) computes local stats independently.
Merge — The main thread collects all Future<Result> values and merges them.
Display — Results are shown in the GUI (or printed to console in CLI mode).


🖥️ GUI Preview
┌─────────────────────────────────────────────────────────────┐
│  Transaction Analyzer  //  Multi-Thread Edition             │
├──────────────────┬──────────────────────┬───────────────────┤
│  [File Path   ]  │  Threads: [4]        │  [ ▶ Run Analysis]│
├──────────────────┴──────────────────────┴───────────────────┤
│                                                             │
│  🧾 Total Transactions    12,500   │  Top 10 Users          │
│  💰 Total Amount      $630,421.00  │  ┌──────┬───────────┐  │
│  📊 Average             $50.43     │  │ Rank │  Amount   │  │
│  📈 Max                $999.95     │  ├──────┼───────────┤  │
│  📉 Min                  $0.50     │  │  #1  │ $8,234.10 │  │
│  🟢 Deposits             7,300     │  │  #2  │ $7,891.20 │  │
│  🔴 Withdrawals          5,200     │  │  ... │    ...    │  │
│  ⏱️ Execution Time        42 ms    │  └──────┴───────────┘  │
│                                                             │
├─────────────────────────────────────────────────────────────┤
│  ✔ Analysis complete.                     [████████████]    │
└─────────────────────────────────────────────────────────────┘

🚀 Getting Started
Prerequisites

Java JDK 8 or higher
NetBeans IDE (recommended) or any Java IDE

Run in NetBeans

Clone or download the project.
Open NetBeans → File → Open Project.
Place your transactions.csv file anywhere accessible.
Right-click TransactionGUI.java → Run File.
Click Browse to select the CSV, set thread count, and press ▶ Run Analysis.

Run via Terminal
bashjavac multithreadingg/*.java
java multithreadingg.TransactionGUI

📄 CSV Format
The input file must follow this structure:
csvtransactionId,userId,amount,type
1,101,250.00,deposit
2,102,80.50,withdraw
3,101,415.75,deposit
...
ColumnTypeDescriptiontransactionIdintUnique transaction IDuserIdintID of the useramountdoubleTransaction valuetypestringdeposit or withdraw (case-insensitive)

Rows with missing or invalid fields are silently skipped.


🛠️ Technologies Used

Java SE — Core language
java.util.concurrent — ExecutorService, Callable, Future
Java Swing — GUI framework (JFrame, JTable, JProgressBar)
SwingWorker — Background task execution without freezing the UI
HashMap + streams — User aggregation and sorting


💡 Key Concepts
Why Multi-Threading?
Processing millions of CSV rows sequentially takes time proportional to the dataset size. By splitting the work across N threads, we achieve near linear speedup on multi-core machines.
Why Callable over Runnable?
Callable<Result> allows each thread to return a value (its partial Result), which is then retrieved via Future.get(). Runnable cannot return values.
Thread Safety
Each thread works on its own isolated chunk of data and produces an independent Result object — there is no shared mutable state between threads. Merging happens only in the main thread after all futures complete, making the design inherently thread-safe without needing synchronized blocks.

👤 Author
Mohamed Khaled
Software Engineering Student
Cairo, Egypt


Built as part of a Multi-Threading & Concurrency course project.
