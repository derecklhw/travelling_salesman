# Travelling Salesman Project

This project contains the implementation of several algorithms for solving the Travelling Salesman Problem (TSP) in Java. It includes a Bash script for compiling and running the Java application against a set of input files.

## Getting Started

### Prerequisites

Ensure that you have the following requirements:

- Java Development Kit (JDK)
- Java Runtime Environment (JRE)
- Unix machine with Bash shell (e.g. Linux, macOS, etc.)

### Installing

Clone the repository to your local machine:

```bash
git clone https://github.com/derecklhw/travelling_salesman.git
```

## Running the Application

The Java application can be run for a single input file or for a directory of input files. The Bash script `run_java_app.sh` is provided to automate the process of compiling and running the Java application.

### Compiling

Navigate to the `src` directory and compile the Java application:

```bash
javac travelling_salesman/*.java
```

### Running

```bash
java travelling_salesman.Main <input_file>
```

### Running with Bash Script

```bash
./run_java_app.sh <input_file>
```

You can run the provided directory `file_path_list.txt`
