#!/bin/bash

# Check if file containing paths is provided
if [ "$#" -ne 1 ]; then
    echo "Usage: $0 path_to_file_containing_paths"
    exit 1
fi

# Read file paths from the provided file
FILE_PATHS=$1

# Compile the Java application
# Replace 'Main.java' with the path to your Java file
javac travelling_salesman/Main.java travelling_salesman/City.java

# Check if compilation was successful
if [ $? -eq 0 ]; then
    echo "Compilation successful."
else
    echo "Compilation failed."
    exit 1
fi

# Iterate over each file path in the file
while IFS= read -r file_path
do
    # Run the Java application with the current file path
    # Replace 'Main' with your main class name and adjust arguments as needed
    echo $file_path
    java travelling_salesman.Main "$file_path"
    echo ""
done < "$FILE_PATHS"
