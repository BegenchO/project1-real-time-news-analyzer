package project1;

// Other imports
import scala.io.StdIn.readLine  // User CL Input
import java.io.IOException
import scala.util.Try

import project1.User

// Hive Imports
import java.sql.SQLException
import java.sql.Connection
import java.sql.ResultSet
import java.sql.Statement
import java.sql.DriverManager


object Main {

    // Global variables
    var users = Seq[User]()
    var username = ""
    var loggedIn = false
    var programRunning = true


    // Main method
    def main (args: Array[String]): Unit = {
        
        // Load Users
        val admin = new User("Admin", "password")
        val user = new User("User", "password")
        users = Seq(admin, user)

        while (programRunning) {
            
            // App CLI Presentation
            println("-----Movies Trends-----")

            loading(1)

            // Login
            login()

            // Login success
            loading("Login success. Welcome " + username, 1)
            
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
            println("Main menu")
            println("1. Update username/password")
            println("2. Get Data")
            println("3. Analyze Data")
            println("4. Logout")
            println("0. Exit the program")
            print("COMMAND > ")
            val command = readLine()

            command match {
                case "1" => updateUserMenu()
                case "2" => fetchAPI()
                case "3" => displayAnalyzeMenu()
                case "4" => loggedIn = false
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
            
            println("LOGIN")
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

    } // end login



    def updateUserMenu(): Unit = {
        
        var on = true

        while(on) {
            // display menu
            println("Update username/password")
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
            print("COMMAND > ")
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

    // Fetches data from TMDB
    def fetchAPI(): Unit = {
        val url = "https://api.themoviedb.org/3/discover/movie?api_key=a8efcb3705ef6973f51b697d643a61b7&language=en-US&sort_by=popularity.desc&include_adult=false&include_video=false&page=1&year=2021&with_watch_monetization_types=flatrate"
        loading("Fetching Data...")
        val result = scala.io.Source.fromURL(url).mkString
        loading("Loading Data into Hive...", 1)
        
        // Hive
        var connection: java.sql.Connection = null;

        try {
            var driverName = "org.apache.hive.jdbc.HiveDriver"
            val connectionString = "jdbc:hive2://sandbox-hdp.hortonworks.com:10000/default"

            Class.forName(driverName)
            connection = DriverManager.getConnection(connectionString, "", "")
            
            val statement = connection.createStatement();
            var hiveQuery = "SELECT * FROM testjson LIMIT 5"
            val result = statement.executeQuery(hiveQuery)

            while (result.next()) {
                println(s"Column 1: ${result.getString(1)}")
            }
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


        // End Hive

    } // end fetchAPI


} // end class