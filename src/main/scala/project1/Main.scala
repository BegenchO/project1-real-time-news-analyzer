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
        var on = true

        while (on) {
            // display menu
            println("Main menu")
            println("1. Update username/password")
            println("2. Get Data")
            println("3. Exit the program")

            val command = readLine()

            command match {
                case "1" => {
                    println("Updating password")
                }
                case "2" => fetchAPI()
                case "3" => on = false
                case _ => println("Command not found!!! Please enter a valid command!")
            }




        } // end whle

        // Exit
        println("Terminating program...")
        Thread.sleep(1000)

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

            println("Loading...")
            Thread.sleep(1000)
            
            if (users(username) != "Not found" && (users(username) == password)) {
                loginSuccess = true
            } else {
                Thread.sleep(1000)
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


} // end class