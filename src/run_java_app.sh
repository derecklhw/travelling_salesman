#!/bin/bash

# Check if file containing paths is provided
if [ "$#" -ne 1 ]; then
    echo "Usage: $0 path_to_file_containing_paths"
    exit 1
fi

# Read file paths from the provided file
FILE_PATHS=$1

# Compile the Java application
javac travelling_salesman/*.java 

# Check if compilation was successful
if [ $? -eq 0 ]; then
    echo "Compilation successful."
    echo ""
else
    echo "Compilation failed."
    exit 1
fi

# Iterate over each file path in the file
while IFS= read -r file_path
do
    for input in 1 2 3
    do
        # Run the Java application with the current file path
        echo "Running with file path: $file_path and input: $input"
        echo $input | java travelling_salesman.Main "$file_path"
        echo ""
    done
    echo "============================================================"
done < "$FILE_PATHS"
