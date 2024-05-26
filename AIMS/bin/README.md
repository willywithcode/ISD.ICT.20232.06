# An Internet Media Store


## Getting Started
Welcome to the AIMS project by group 6 . Here is a guideline to help you get started.

## Folder Structure

The workspace contains the following folders, where:

- `src`: the folder to maintain sources
- `lib`: the folder to maintain dependencies
- `assets`: the folder to maintain static resources
- `test`: the folder for testing purpose

## Dependency Management

### Working with Eclipse

Import the root directory of this repository after cloning under `Eclipse` -> `Open Projects from File System...` or by
using EGit.

### MySQL

Import `mysql-connector-j-8.4.0` in `lib`
under `Eclipse` -> `Project` -> `Properties` -> `Java Build Path` -> `Classpath` -> `Add JARs...`.

### JUnit

Import `JUnit5` library
under `Eclipse` -> `Project` -> `Properties` -> `Java Build Path` -> `Modulepath` -> `Add Library...` -> `JUnit` -> `Next`.

### JavaFX

**Note:** At first, please try to run the project once, and then follow these steps.

1. Create a new `User Library`
   under `Eclipse` -> `Window` -> `Preferences` -> `Java` -> `Build Path` -> `User Libraries` -> `New`
2. Name it anything you want, e.g., `JavaFX21`, and include the **_jars_** under either the `lib/linux/`
   directory for Linux distro or the `lib/windoze` directory for Windows in the project.
3. Include the library, e.g., `JavaFX21`, into the classpath.

### Add VM arguments

Click on `Run` -> `Run Configurations...` -> `Java Application`, create a new launch configuration for your project and
add these VM arguments:

- For Linux distro:

> `--module-path lib/linux --add-modules=javafx.controls,javafx.fxml,javafx.base,javafx.web`

- For Windows:

> `--module-path lib/windoze --add-modules=javafx.controls,javafx.fxml,javafx.base,javafx.web`
