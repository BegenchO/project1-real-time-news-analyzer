package project1

import scala.collection.mutable

object Analyze {

    def apiResult(url: String): String = scala.io.Source.fromURL(url).mkString

    // V- Most viewed movies of post-pandemic period
    var url = ""
    val table = ""

    var pageNum = 1

    def getData(): String = {
        // DATA TO FETCH
        // MOVIES - Popularity DESC, Year 2020
        url = "https://api.themoviedb.org/3/discover/movie?api_key=a8efcb3705ef6973f51b697d643a61b7&language=en-US&sort_by=popularity.desc&include_adult=false&include_video=false&page=" + pageNum + "&primary_release_year=2020&with_release_type=3%7C2&vote_count.gte=100&with_runtime.gte=60&with_original_language=en&with_watch_monetization_types=flatrate"
       
        var results = apiResult(url).split("adult")

        // Get a list of movie ids as Integers
        var movieIds = results.filter(item => item.contains("id\":")).map(item => {
            val startIndex = item.indexOf("id\":") + 4
            val endIndex = item.indexOf("original_") - 2
            if (startIndex > 0 && endIndex > 0) {
                item.substring(startIndex, endIndex)
            }
        })

        var data = ""

        // Loop over movie ids and get details for each
        for (id <- movieIds) {
            url = "https://api.themoviedb.org/3/movie/"+ id +"?api_key=a8efcb3705ef6973f51b697d643a61b7&language=en-US"
            data += apiResult(url) + "\n"
        }

        data = data.substring(0, data.length()-1)     // Remove extra new line at the end 
        
        data

    } // end getData()
   
    



    // splice id
    // send request for movie details by id with for loop
    // append result to a variable with \n

    // write all results to a file
    // save to relevant table
    



    // R- Highly rated movies of post-pandemic period
            // sort by vote desc
            // display title, profit = (revenue - budget)


    // R- Average rating of movies in 2019 vs 2020 vs 2021


    // L- How average length of movies changed over the past 5 years


    // I- Average gross income of each genre in 2020


    // I- Top 10 grossing movies of 2021


    // I- Total gross income of movies in 2019 vs 2020 vs 2021

}