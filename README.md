# AWS-IAM-RolePolicy-validator

Given path to Json file returns false if an input JSON Resource field contains a single
asterisk and true in any other case.

If file is not in format of `AWS::IAM::Role Policy` then give user information and return ```true```;

### Installation

1. Clone the repo
   ```sh
    git clone https://github.com/dominiks01/AWS-IAM-RolePolicy-validator.git
    ```

2. Navigate to your Folder
    ```sh
    cd AWS-IAM-RolePolicy-validator/
    ```

3. Execute Gradle script to validate file
    ```sh
    # On Linux    
    ./gradlew runWithJavaExec -PpathToFile="<PathToYourFile>"
   
    # On Windows    
    gradlew.bat runWithJavaExec -PpathToFile="<PathToYourFile>"
    ```
