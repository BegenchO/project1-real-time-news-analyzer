package project1;

class User {

    private var username = "Admin"
    private var password = "password"

    def this(username: String, password: String) {
        this()
        this.username = username
        this.password = password
    }

    def getUsername(): String = username
    def getPassword(): String = password

    def setUsername(username: String) { this.username = username }
    def setPassword(password: String) { this.password = password }
  
}