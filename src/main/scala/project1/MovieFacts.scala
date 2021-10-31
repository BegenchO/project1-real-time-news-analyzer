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
        "In Avengers: Endgame, Tony Stark's entire death scene was completely made up on the spot."
    )

    def getRandomFact(): String = {
        val random = scala.util.Random
        facts(random.nextInt(facts.length))
    }
    
}