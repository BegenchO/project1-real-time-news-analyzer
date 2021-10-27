package project1

// imports
import scala.io.StdIn.readLine
import scalaj.http._


object Main {
    def main (args: Array[String]): Unit = {

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



    
    def login(): String = {
        val users = Map("Dani"->"password", "George"->"password").withDefaultValue("Not Found")

        var username = ""
        var loginSuccess = false;

        while (!loginSuccess) {
            // First readLine is skipped for some reason
            print("Enter your username: ")
            username = readLine()


            print("Enter your password: ")
            val password = readLine()

            loading("Loading...", 1)
            
            if (users(username) != "Not found" && (users(username) == password)) {
                loginSuccess = true
            } else {
                loading(1)
                println("Login fail. Please try again")
            } 
        
        } // end while

        username

    } // end login


    def fetchAPI(): Unit = {
        val url = "https://api.themoviedb.org/3/movie/550?api_key=a8efcb3705ef6973f51b697d643a61b7"
        val response: HttpResponse[String] = Http(url).asString
        println(response.body)
    } // end fetchAPI

    
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
                    println("Updating password")
                }
                case "2" => fetchAPI()
                case "3" => displayAnalyzeMenu()
                case "0" => on = false
                case _ => println("Command not found!!! Please enter a valid command!")
            }

        } // end while

    } // end displayMenu


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
    }

    def loading(delay: Float): Unit = {
        Thread.sleep((delay * 1000).toInt)
    }

    
    def loading(text: String, delay: Float): Unit = {
        println(text)
        Thread.sleep((delay * 1000).toInt)
    }


} // end class