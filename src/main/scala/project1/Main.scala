package project1

// Other imports
import scala.io.StdIn.readLine  // User CL Input
import java.io.IOException
import scala.util.Try


// Hive Imports
import java.sql.SQLException
import java.sql.Connection
import java.sql.ResultSet
import java.sql.Statement
import java.sql.DriverManager


object Main {
    def main (args: Array[String]): Unit = {

        // App CLI Presentation
        println("-----Movies Trends-----")

        loading(1)

        // Login
        val username = login()

        // Login success
        println("Login success. Welcome " + username)

        // Main menu
        displayMenu()

        // Exit
        loading("Terminating program...", 1)

        println("Session ended!")

    } // end main

    
    def connectToHive(): Unit = {
        
    }



    // Handles login
    def login(): String = {

        var usernameInput = ""
        var passwordInput = ""

        var usernameFromDB = ""
        var passwordFromDB = ""

        var loginSuccess = false;

        // Hive connection
        var connection: java.sql.Connection = null;

        while (!loginSuccess) {
            // First readLine is skipped for some reason
            print("Enter your username: ")
            usernameInput = readLine()


            print("Enter your password: ")
            passwordInput = readLine()

            loading("Loading...", 1)

            if (usernameInput.length > 0) {
                try {
                    var driverName = "org.apache.hive.jdbc.HiveDriver"
                    val connectionString = "jdbc:hive2://sandbox-hdp.hortonworks.com:10000/project1"

                    Class.forName(driverName)

                    connection = DriverManager.getConnection(connectionString, "", "")
                    val statement = connection.createStatement()

                    var hiveQuery = s"SELECT username, password FROM users WHERE username='${usernameInput}'"
                    var hiveResponse = statement.executeQuery(hiveQuery)

                    while(hiveResponse.next()) {
                        usernameFromDB = hiveResponse.getString(1)
                        passwordFromDB = hiveResponse.getString(2)
                    }

                } catch {
                    case exception: Throwable => {
                        exception.printStackTrace();
                        throw new Exception(s"${exception.getMessage()}")
                    }
                } finally {
                    try {
                        if (connection != null) connection.close()
                    } catch {
                        case exception: Throwable => {
                            exception.printStackTrace();
                            throw new Exception(s"${exception.getMessage()}")
                        }
                    }
                } // end try catch hive
            }
            

            if (usernameFromDB != "" && passwordFromDB != "") {

                if (usernameInput == usernameFromDB && passwordInput == passwordFromDB) {
                    loginSuccess = true
                } else {
                    loading(1)
                    println("Login fail. Please try again")
                } 
            } // end if
            
        
        } // end while

        usernameFromDB

    } // end login


    // Fetches data from TMDB
    def fetchAPI(): Unit = {
        val url = "https://api.themoviedb.org/3/movie/550?api_key=a8efcb3705ef6973f51b697d643a61b7"
        loading("Fetching Data...")
        val result = scala.io.Source.fromURL(url).mkString
        loading("Printing Data...", 1)
        print(result)
        
    } // end fetchAPI

    
    // Displays Main Menu
    def displayMenu(): Unit = {
        
        var on = true

        while (on) {
            // display menu
            println("Main menu")
            println("1. Update username/password")
            println("2. Get Data")
            println("3. Analyze Data")
            println("0. Exit the program")

            val command = readLine()

            command match {
                case "1" => {
                    loading(1)
                    println("Updating password")
                }
                case "2" => fetchAPI()
                case "3" => displayAnalyzeMenu()
                case "0" => on = false
                case _ => println("Command not found!!! Please enter a valid command!")
            }

        } // end while

    } // end displayMenu


    // Display sub menu Analyze
    def displayAnalyzeMenu(): Unit = {
        var on = true

        while (on) {
            // display Analyze Menu
            println("Analyze Data")
            println("1. View - What are the most viewed 10 movies of post pandemic period?")
            println("2. Rating - What are the top 10 highly rated movies of post pandemic period?")
            println("3. Rating - What are the average ratings of top 50 movies in 2019, 2020 and 2021 so far?")
            println("4. Length - How average length of movies changed over the past 5 years?")
            println("5. Income - What is the average gross income of each movie genre in 2020?")
            println("6. Income - What are the top 10 grossing movies of 2021 so far?")
            println("7. Income - What is the total gross income of movies in 2019, 2020, and 2021 so far?")
            println("0. Go Back to Main Menu")

            val command = readLine()

            command match {
                case "1" => println("Top 10 viewed movies are...")
                case "2" => println("Top 10 rated movies are...")
                case "3" => println("Top 50 movies average rating is...")
                case "4" => println("Average length of movies...")
                case "5" => println("Aveage gross income of each genre is...")
                case "6" => println("Top 10 grossing movies are...")
                case "7" => println("Total gross income of movies are...")
                case "0" => on = false
                case _ => println("Command not found!!! Please enter a valid command!")
            }
        }
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


} // end class