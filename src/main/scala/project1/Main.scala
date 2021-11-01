package project1;

// All imports
import scala.io.StdIn.readLine
import java.io.IOException
import scala.util.Try
import java.io.PrintWriter

// Hive Imports
import java.sql.SQLException
import java.sql.Connection
import java.sql.ResultSet
import java.sql.Statement
import java.sql.DriverManager
import java.io.File


object Main {

    // Global variables
    var users = Seq[User]()
    var username = ""
    var loggedIn = false
    var programRunning = true


    // Main method
    def main (args: Array[String]): Unit = {
        
        // Create Users when app starts
        val admin = new User("Admin", "password", true)
        val user = new User("User", "password")
        users = Seq(admin, user)

        // Logged out
        while (programRunning) {
            
            // App CLI Presentation
            addLine()
            println("-----Movies Trends-----")
            addLine()

            loading(1)

            // Login
            login()            
            
            // Main menu
            displayMenu()
        }

        // Exit
        loading("Terminating program...", 1)

        println("Session ended!")

    } // end main


    // Displays Main Menu
    def displayMenu(): Unit = {
        
        var on = true

        while (loggedIn && on) {
            // display menu
            addLine()
            println("Main menu")
            addLine()
            println("1. Update username/password")
            if (checkIfAdmin) println("2.0 Get Data")   // Hide this command from regular users
            println("2. Analyze Data")
            println("3. Logout")
            println("0. Exit the program")
            print("COMMAND > ")
            val command = readLine()

            command match {
                case "1" => updateUserMenu()
                case "2.0" => {
                    if (checkIfAdmin()) {
                        fetchStoreLoad()
                    } else {
                        println("Access denied!")
                    }
                } // end case
                case "2" => displayAnalyzeMenu()
                case "3" => loggedIn = false
                case "0" =>  { 
                    on = false
                    programRunning = false
                }
                case _ => println("Command not found!!! Please enter a valid command!")
            }

        } // end while

    } // end displayMenu



    // Handles login
    def login() = {

        var usernameInput = ""
        var passwordInput = ""

        while (!loggedIn) {

            addLine()
            println("LOGIN")
            addLine()
            print("Enter your username: ")
            usernameInput = readLine()

            print("Enter your password: ")
            passwordInput = readLine()         

            // Verify user from users list
            if (usernameInput.length() > 0 && passwordInput.length() > 0) {
                for (user <- users) {
                    if (user.getUsername == usernameInput && user.getPassword == passwordInput) {
                        loggedIn = true
                        username = usernameInput
                    }
                }
            }  

            loading("Logging in...", 1)
            if (loggedIn != true) {
                loading("Login fail. Please try again", 1)
            }
        
        } // end while

        loading("Login success. Welcome " + username, 1)

    } // end login


    // Updates user information
    def updateUserMenu(): Unit = {
        
        var on = true

        while(on) {
            // display menu
            addLine()
            println("Update username/password")
            addLine()
            println("1. Update USERNAME")
            println("2. Update PASSWORD")
            println("0. Back to Main Menu")
            print("COMMAND > ")
            val command = readLine()

            command match {
                case "1" => {
                    loading(1)
                    println("Your CURRENT username: " + username)
                    print("Please provide NEW username: ")
                    val value = readLine()

                    loading("Updating username...", 1)
                    if (value.length() > 0) {
                        // update username here
                        for(user <- users) {
                            if(user.getUsername == username) {
                                user.setUsername(value)
                                username = value
                                println("Your username has been update to " + value)
                                loading(1)
                            }
                        } // end for
                    } else {
                        loading("Username cannot be empty. Update failed", 1)
                    } // end if else
                    
                } // end case 1
                case "2" => {
                    loading(1)
                    var passwordNotMatch = true
                    var value = ""

                    println("Change password for " +  username)
                    
                    while (passwordNotMatch) {
                        
                        print("Please provide new password: ")
                        value = readLine()
                        print("Please confirm new password: ")
                        val value2 = readLine()

                        if (value == value2) {
                            passwordNotMatch = false
                        } else {
                            println("Passwords do NOT match. Please retype...")
                        }

                    } // end while

                    if (value.length() > 0) {
                        loading("Passwords match. Updating password...", 1)
                        // Update password here
                        for(user <- users) {
                            if (user.getUsername == username) {
                                user.setPassword(value)
                                println("Password was successfully updated...")
                                loading(1)
                            }
                        }
                    } // end if
                    
                } // case 2
                case "0" => on = false
                case _ => println("Command not found!!! Please enter a valid command!")
            }


        } // end while

    } // end updateUserMEnu


    // Displays sub menu Analyze
    def displayAnalyzeMenu(): Unit = {
        var on = true

        while (on) {
            // display Analyze Menu
            addLine()
            println("Analyze Data")
            addLine()
            println("1. Most popular 10 movies of post pandemic period")          
            println("2. Most expensive 10 movies of the past 5 years")
            println("3. Average income of movies in each of the past 5 years")
            println("4. Average length of movies in each of the past 5 years")
            println("5. Average income of movies in each month of 2020")
            println("6. Number of movies with revenues over 100 mln dollars in each of the past 5 years")
            println("0. Go Back to Main Menu")
            print("COMMAND > ")
            val command = readLine()

            command match {
                case "1" => { 
                    executeHiveCommand(List((
                        "SELECT get_json_object(json, '$.title'), CAST(get_json_object(json, '$.popularity') AS int) as popularity FROM movies SORT BY popularity DESC LIMIT 10", 
                        "query", 
                        "Movie Title \t | \t Popularity Rating"
                    ))) 
                }
                case "2" => { 
                    executeHiveCommand(List((
                        "SELECT get_json_object(json, '$.title'), CAST(get_json_object(json, '$.budget') AS int)/1000000 as budget FROM movies SORT BY budget DESC LIMIT 10", 
                        "query", 
                        "Movie Title \t | \t Budget"
                    )), customText = " mln") 
                }
                case "3" => { 
                    executeHiveCommand(List((
                        "SELECT CAST(SUBSTRING(get_json_object(json, '$.release_date'), 1, 4) AS int) AS year, ROUND(AVG(CAST(get_json_object(json, '$.revenue') AS int)/1000000),1) as revenue FROM movies WHERE get_json_object(json, '$.revenue') > 0 GROUP BY CAST(SUBSTRING(get_json_object(json, '$.release_date'), 1, 4) AS int)", 
                        "query", 
                        "Year \t | \t Average Revenue"
                    )), customText = " mln") 
                }
                case "4" => { 
                    executeHiveCommand(List((
                        "SELECT CAST(SUBSTRING(get_json_object(json, '$.release_date'), 1, 4) AS int) AS year, ROUND(AVG(CAST(get_json_object(json, '$.runtime') AS int)),0) as revenue FROM movies GROUP BY CAST(SUBSTRING(get_json_object(json, '$.release_date'), 1, 4) AS int)", 
                        "query", 
                        "Year \t | \t Average Runtime"
                    )), customText = " mins") 
                }
                case "5" => { 
                    executeHiveCommand(List((
                        "SELECT CAST(SUBSTRING(get_json_object(json, '$.release_date'), 6, 2) AS int), ROUND(AVG(CAST(get_json_object(json, '$.revenue') AS int)/1000000),1) FROM movies WHERE CAST(SUBSTRING(get_json_object(json, '$.release_date'), 1, 4) AS int) = 2020 AND get_json_object(json, '$.revenue') > 0 GROUP BY CAST(SUBSTRING(get_json_object(json, '$.release_date'), 6, 2) AS int)", 
                        "query", 
                        "Year \t | \t Average Revenue"
                    )), customText = " mln") 
                }
                case "6" => { 
                    executeHiveCommand(List((
                        "SELECT CAST(SUBSTRING(get_json_object(json, '$.release_date'), 1, 4) AS int), COUNT(CAST(get_json_object(json, '$.revenue') AS int)/1000000) FROM movies WHERE CAST(get_json_object(json, '$.revenue') AS int)/1000000 > 100 GROUP BY  CAST(SUBSTRING(get_json_object(json, '$.release_date'), 1, 4) AS int)", 
                        "query", 
                        "Year \t | \t Number of Movies"
                    )), customText = " movies with revenues over 100 mln") 
                }
                case "0" => on = false
                case _ => println("Command not found!!! Please enter a valid command!")
            }
        } // end while

    } // end display analyze menu


    // Mimics loading between commands
    def loading(delay: Double): Unit = {
        Thread.sleep((delay * 1000).toInt)
    }

    def loading(text: String): Unit = {
        println(text)
    }

    def loading(text: String, delay: Double): Unit = {
        println(text)
        Thread.sleep((delay * 1000).toInt)
    }

    def addLine(): Unit = {
        println("-------------------------")
    }

    def checkIfAdmin(): Boolean = {
        var isAdmin = false
        for (user <- users) {
            if (username == user.getUsername && user.getIsAdmin == true) {
                isAdmin = true
            }
        } // end for
        isAdmin
    }


    // First time app run will load data into Hive
    def fetchStoreLoad(): Unit = {

        // 1. Fetch data from TMDB API
        loading("Fetching Data. Please be patient...", 1)
        addLine()
        loading("DID YOU KNOW: " + project1.MovieFacts.getRandomFact())
        addLine()
        val apiResult = Api.getData()
        
        // 2. Saves json data into a file locally
        loading("Saving data to file. Please be patient...", 1)
        val filepath = storeData(apiResult) 

        // 3. Load data from file to Hive
        loading("Loading Data into Hive. Please be patient...", 1)
        loadData(filepath)

    } // end fetch store load()



    // Stores data in a file on VM
    def storeData(jsonData: String): String = {
      
        val filepath = "/tmp/data.json" // Save your file in tmp to avoid permission issues
        val writer = new PrintWriter(new File(filepath))
        writer.write(jsonData)
        writer.close()

        println(s"File successfully created")

        filepath // return filepath to load into hive
     
    } // end storeData()


    // Loads data from file into Hive
    def loadData(filepath: String): Unit = {

        // Create table
        val query1 = "CREATE TABLE IF NOT EXISTS movies(json String)"
        val queryType1 = "execute"

        // Load data into created table
        val query2 = "LOAD DATA LOCAL INPATH '" + filepath + "' INTO TABLE movies"
        val queryType2 = "execute"

        executeHiveCommand(List((query1, queryType1, ""), (query2, queryType2, "")))

    } // end loadData()



    // Make Hive code reusable
    def executeHiveCommand(queries: List[(String, String, String)], customText: String = "") {
        var connection: java.sql.Connection = null;

        try {
            var driverName = "org.apache.hive.jdbc.HiveDriver"
            val connectionString = "jdbc:hive2://sandbox-hdp.hortonworks.com:10000/project1"

            Class.forName(driverName)
            connection = DriverManager.getConnection(connectionString, "", "")
            val statement = connection.createStatement();

            // query tuple = (query string, query type)
            for (query <- queries) {
                if (query._2 == "execute") {
                    statement.execute(query._1)
                } else {
                    loading("Hive query in process. Please be patient...", 1)
                    addLine()
                    loading("DID YOU KNOW: " + project1.MovieFacts.getRandomFact())
                    addLine()
                    var response = statement.executeQuery(query._1)

                    loading("Success! Displaying results...")
                    addLine()
                    println(query._3)
                    addLine()

                    while (response.next()) {
                        loading(1)
                        println(s"${response.getString(1)}  \t | \t ${response.getString(2)} ${customText}")
                    }
                    addLine()
                    loading(3)
                    loading("Redirecting to Menu...", 2)
                }
            } // end for 

        } catch {
            case ex: Throwable => {
                ex.printStackTrace();
                throw new Exception(s"${ex.getMessage}")
            }
        } finally {
            try {
                if (connection != null) connection.close()
            } catch {
                case ex: Throwable => {
                    ex.printStackTrace()
                    throw new Exception(s"${ex.getMessage}")
                }
            }
        } // end try catch finally
    } // end hiveQuery()


} // end class