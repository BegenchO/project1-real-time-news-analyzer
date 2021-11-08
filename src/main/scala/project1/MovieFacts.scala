package project1

package project1

object MovieFacts {

    val facts = List(
        "The code in The Matrix comes from sushi recipes. A production designer scanned symbols from his wife’s sushi cookbooks, then manipulated them to create the iconic code.",
        "In Now You See Me, Isla Fisher almost drowned in front of the entire cast and crew while filming a scene with an underwater magic trick.",
        "In Joker, Joaquin Phoenix lost 52 pounds to play the role of Arthur Fleck.",
        "Sylvester Stallone wanted to make sure the boxing scenes looked so real in Rocky IV that he instructed Dolph Lundgren to actually hit him. A punch to the chest left Stallone in the ICU for nine days.",
        "In Spider-Man: Far From Home, Samuel L. Jackson improvised the iconic '*****, please. You've been to space' rebuttal after Peter claimed he was just a neighborhood Spider-Man.",
        "In Thor: Ragnarok, the 'He's a friend from work!' line was actually improvised by a kid from Make-a-Wish who was visiting the set that day.",
        "In Home Alone, that picture of Buzz's girlfriend was actually a picture of the art director's son wearing a wig.",
        "In Avengers: Endgame, Tony Stark's entire death scene was completely made up on the spot.",
        "In Get Out, most of that iconic 'Give me the keys, Rose!'' scene was made up that day while shooting.",
        "Due to a miscommunication on the set of The Hateful Eight, Kurt Russell accidentally smashed a 140-year-old antique guitar instead of the prop.",
        "Dallas Buyers Club only had a $250 makeup budget, but went on to win an Oscar in 2014 for Best Makeup and Hairstyling.",
        "Production for Gone Girl shut down for four days because Ben Affleck — a diehard Red Sox fan — refused to wear a New York Yankees baseball cap. Eventually, the Bostonian actor and director David Fincher agreed to let him wear a Mets cap.",
        "Sean Bean, who played Boromir in The Lord of the Rings: The Fellowship of the Ring, was so afraid of flying that he would spend two hours every morning climbing from the base of the mountain to the top, where they were shooting."
    )

    def getRandomFact(): String = {
        val random = scala.util.Random
        facts(random.nextInt(facts.length))
    }
    
}