package project1;

class User {

    private var username = "User"
    private var password = "password"
    private var isAdmin = false

    def this(username: String, password: String, isAdmin: Boolean = false) {
        this()
        this.username = username
        this.password = password
        this.isAdmin = isAdmin
    }

    def getUsername(): String = username
    def getPassword(): String = password
    def getIsAdmin(): Boolean = isAdmin

    def setUsername(username: String) { this.username = username }
    def setPassword(password: String) { this.password = password }
  
} // end class