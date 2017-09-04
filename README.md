# kalah
Kalah game using spring boot with REST endpoints.

The backend services are implemented and tested having REST endpoints.

To initialize the game - localhost:8080/initialize
To reset the game - localhost:8080/reset

Game's core logic in the class PitService.java (process method) 

Currently the game supports two players ("PlayerA" and "PlayerB") - current code can be extended to use post-requests for player creation but as of now the names are hardcoded.

To make a move(click on the pit on front-end) rest call is - /players/{player}/pitts/{pitt}
thus /players/PlayerA/pitts/2 - is for 3rd pit of the playerA as pit count starts from 0. This will return the result of the new size of player's pits along with size of Kalah.

The domain objects - Kalah, Player, Pit are used. The instance "size" denotes the number of balls in the Pit. 

An in-memory Cache based on weakHashMap is used to maintain the state of the Game.

Unit testing - The chunk of scenarios are tested in the class - PitServiceTest.Java , this class covers lot of backend scenarios.
Each scenario is well documented.

Controller testing - RequestControllerTest is used for testing the controller layer using new spring boot testing features.(very helpful in testing microservice like scenarios)

Going through PitService's process method outlines the whole flow of the game. 
