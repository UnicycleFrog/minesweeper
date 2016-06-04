# minesweeper
A simple, Microsoft-esque Minesweeper game written in Java using the Swing toolkit.

## Screenshot

![Screenshot](https://raw.githubusercontent.com/UnicycleFrog/minesweeper/scrot/scrot.png "A screenshot of minesweeper")

## Features

* Three difficulties: beginner (9x9, 10 mines), intermediate(16x16, 40 mines), expert(30x16, 99 mines)
* Flag counter
* Stopwatch
* Status face and new game button

## Important concepts
* Recursive method when clicking a blank square (`expandempty`)
* Swing components
  * `GridLayout` for cells
  * Message Dialogs (`JOptionPane`)
  * Timer
  * Button icons
  * Right click monitoring (for placing flags) using `MouseListener`
* Static methods (so button objects can trigger events which access MineGUI data i.e. other buttons)
* Extending interfaces (Button -> Spot)
  * Rewrite with Button extending `JButton`?
