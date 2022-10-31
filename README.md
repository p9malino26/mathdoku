# Build

To compile, the main class is Mathdoku located in the package com.patryk.mathdoku. You must compile with JavaFX.

# Running the application

You will be presented with a screen with some buttons and a black canvas. To load a game, you must either:
	
	*click "Load game from file" and select the specially-formatted text file defining the cages. You can use the file 'example.txt' for this.
	
	or
	
	*click "Load game from text input" and enter the cage info in the same format as for the file
	
The program will refuse to load the game if it has found any errors in the cage definition string.
	
# Playing
	
When you have loaded a game, the game will be displayed on the canvas. You can change the digits for the red selected cell by pressing the number keys on your keyboard and pressing backspace to erase. You can move the red cell selector using the arrow keys or by clicking on the corresponding cells with the mouse. Note: if you click on a button outside of the canvas, you will no longer be able to move the red cell selector using the arrow keys because the canvas has lost focus. You must click on the canvas again if you wish to use arrow keys again.

You may click undo in order to revert if you have made a mistake. However, you will only be able to do that max five times! You can also redo if you want to as well or  clear the grid if you wish. You will be asked to confirm this via a dialog. You can undo/redo both single cell changes and clearings.


#Win and mistake detection

Once the game detects that you have won (i.e. have fully completed the grid and have made no mistakes), you will be automaticlly congratulated with a message. You will still be able to edit the game though. The winning dialog will not be displayed again if you re-edit the grid back to the winning state.

If you want to see your mistakes, click the "check button". This will highlight any row/columns with duplicate digits in orange and any cages that are both full and erroneous in red. The highlights will disappear once you edit the grid.

#closing the game

You can either close the window by pressing ESC or by closing it using the OS controls. If there is a game loaded and if there is some data entered, you will be asked to confirm the close action via a dialog.
 
#changing font size

You can change the font size using the combobox on the RHS of the window


