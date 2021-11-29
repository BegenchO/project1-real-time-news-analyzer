# Scala - Hive: Real time news analyzer

## Project Description

Project 1 is a Scala CLI application intended to be built to bolster what was learned during the third and fourth week of Big Data batch training among which are Hadoop Ecocystem tools such as Apache Hive, HDFS, MapReduce 2.0, YARN.
Project 1 has a login functionality and will load data by fetching it from TMDB API and store it in Hive. It will then use HiveQL or HQL commands to analyze data using the predefined analysis questions presented as a menu when the program is run.

## Technologies Used

* Hive - version 3.1.2
* Scala - version 2.11.8
* Hadoop - version 3.2.2
* HDFS

## Features

List of features ready and TODOs for future development
* Retrieves data from TMDB API using predefined urls and number of pages dynamically
* Data is trimmed and separated for query efficiency
* Login credentials can be updated
* Fetch API is ONLY accessible to the admin user

To-do list:
* Easily could integration user input to set the limit on the amount of data that is imported
* Queries could be added or updated to allow the user to pass in different constraints such as years, genres to be analyzed
* User credentials could be persisted to an OLTP database such as mongoDB Atlas

## Getting Started

Clone the project
> git clone https://github.com/BegenchO/project1.git
Install all packages by running sbt build
> sbt
Compile into a jar file
> sbt package

Copy the jar file to a Hadoop environment such as Hortonworks VM. If using Hortonworks VM, run it via spark-submit
> spark-submit <packageName.jar>

## Usage

> Run in Hadoop environment. Login screen will pop up once program is run. Refer to User.scala and Main.scala files for login credentials. Admin login has the ability to load data to be analyzed into Hive thus login as admin first. Once data is loaded into Hive, use the analysis menu to perform various queries and display them in the terminal. You may also opt to change the username or password of currently logged in user in the menu which will be reset to default credentials once program is rerun.

## License

This project uses the following license: [MIT](https://opensource.org/licenses/MIT).
