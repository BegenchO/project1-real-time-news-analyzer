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
* Awesome feature 1
* Awesome feature 2
* Awesome feature 3

To-do list:
* Wow improvement to be done 1
* Wow improvement to be done 2

## Getting Started

Following commands to get started
> git clone https://github.com/BegenchO/project1.git
> sbt
> sbt package

Copy the jar file to a Hadoop environment such as Hortonworks VM.
If using Hortonworks VM,
> spark-submit <packageName.jar>

## Usage

> Run in Hadoop environment. Login screen will pop up once program is run. Refer to User.scala and Main.scala files for login credentials. Admin login has the ability to load data to be analyzed into Hive thus login as admin first. Once data is loaded into Hive, use the analysis menu to perform various queries and display them in the terminal. You may also opt to change the username or password of currently logged in user in the menu which will be reset to default credentials once program is rerun.

## License

This project uses the following license: [MIT](https://opensource.org/licenses/MIT).
