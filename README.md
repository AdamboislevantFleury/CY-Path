# CYPATH-JAVA

This project is based on board game for 2 or 4 players. This is a strategy game where the goal is the be first place don the opposite line of beginning.

The project was done as part of our 1st year final project at CY Tech engineering school.

Done by group 15.

# Features

- 2 or 4 players
- Possibility to save the game and continue it later
- Clickable placement of barriers
- Possibility to go diagonally in case of a barrier right in front of the pawn and anoter pawn right behind it.

# How to play

Each player can choose to either move its pawn or place a barrier.

The player can move its pawn on the left, right, top or bottom, using the arrow on the keyboard.

If the player chooses to place a barrier, they have to click twice on the space between 2 tiles and on the space right next to the barrier they just placed. To make sure the barrier blocks 2 tiles the player have to place the 2 barriers either vertically or horizontally, they can't form an angle or just place 2 barriers that are not next to each other.

In case of a barrier in front of the pawn and another pawn behing it, a message is displayed asking where to go diagonally. The user can also choose to go left or right if possible.

# How to launch the game

You claunch the game in a termianl with the following command : 
```bash
java --module-path "%PATH_JAVAFX%" --add-modules javafx.controls,javafx.fxml, (...) -jar cy-path.jar 
``` 
where %PATH_JAVAFX% is the path to the JavaFX module in your machine.


You can also launch the game through Eclipse or IntellijIDEA by running the QuoridorFX.java class. 

# Improvements

- Using a global servor so people can play on different machines
- Interface more user friendly and more spaced
- Giving the possibility to the player to choose the color of their pawn and their player's name
