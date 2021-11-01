package project1

import scala.collection.mutable

object Api {

    // Gets data from API and loads into Hive
    def getData(): String = {
        
        var allData = ""

        val years = List("2017", "2018", "2019", "2020", "2021")

        // Fetch movies by year
        for (year <- years) {
            allData += getMoviesByYear(year)
        }

        allData = allData.substring(0, allData.length()-1)     // Remove extra new line at the ver end 
        
        allData

    } // end getData()


    
    // Fetches Movie data by year
    def getMoviesByYear(year: String): String = {

        var movieDataByYear = ""

        // Gets data for 100 movies per year; 20 movies per page from API
        for (pageNum <- 1 to 5) {    
            val url = "https://api.themoviedb.org/3/discover/movie?api_key=a8efcb3705ef6973f51b697d643a61b7&language=en-US&sort_by=popularity.desc&include_adult=false&include_video=false&page=" + pageNum + "&primary_release_year=" + year + "&with_release_type=3%7C2&vote_count.gte=100&with_runtime.gte=60&with_original_language=en&with_watch_monetization_types=flatrate"
            val movieDataPerPage = getMovieDetails(url)
            movieDataByYear += movieDataPerPage
        }

        movieDataByYear
    } // end getMoviesByYear()



    // Gets Movies by ID
    def getMovieDetails(url: String): String = {

        // Fetch the list of Movies
        val apiResult = scala.io.Source.fromURL(url).mkString

        var results = apiResult.split("adult")     // split by movies

        // Get a list of movie ids as Integers
        var movieIds = results.filter(item => item.contains("id\":")).map(item => {
            val startIndex = item.indexOf("id\":") + 4
            val endIndex = item.indexOf("original_") - 2
            if (startIndex > 0 && endIndex > 0) {
                item.substring(startIndex, endIndex)
            }
        })

        var movieDataPerPage = ""

        // Loop over movie ids and get details for each
        for (id <- movieIds) {
            val url = "https://api.themoviedb.org/3/movie/"+ id +"?api_key=a8efcb3705ef6973f51b697d643a61b7&language=en-US"
            // Fetch the list of Movies
            val movieDetails = scala.io.Source.fromURL(url).mkString
            movieDataPerPage += movieDetails + "\n"
        }

        movieDataPerPage
    } // end getMovieDetails()

} // end class