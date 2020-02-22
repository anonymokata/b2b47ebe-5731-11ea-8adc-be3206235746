# Pencil Durability Kata
#### This project demonstrates how a pencil interacts with paper.

####Dependencies:
#### The following are dependencies that are required for running the unit test suite for this project:
- Gradle 6.2
- Java 1.8
- JUnit 4.12

####How To Run in OS X
- First, open up command line and navigate to the ~/PencilDurabilityKata directory.
- If Gradle 6.2 is installed, run the following command:
```
gradle test
```
- If 6.2 is not installed, however, the following command should suffice:
```
./gradlew test
```
- It should be noted that, in order to run the second command, 
you need to be on the directory level that houses the gradlew.sh file.
This file is found in the ~/PencilDurabilityKata directory.

####Additional Information
- After building and running the test suite, associated test reports
can be found in ~/PencilDurabilityKata/build/reports/test/test/classes/PencilTest.html
- Gradle should also be able to run on Windows by executing the gradle.bat file also located
in ~/PencilDurabilityKata. 
- To rebuild the project (if needed), the following can be run via command line:
```
gradle clean build
```