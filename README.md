# TQS-Sudoku

## Composition of the project team

  * Anaïs Débureaux
  * Yufeng Yang
  
## Objectives

  * Create a Sudoku generator and solver for Java, then test it with JUnit Testing in Eclipse
  * This library for Java and Android provides the basis for the project : [Sudoku](https://github.com/a11n/sudoku).
  * To keep in mind: we are using 0 to represent an element of the Sudoku grid that the player has not yet filled in.
  
## Test coverage
![Coverage](https://github.com/NIU1607107/TQS-Sudoku/blob/main/test_coverage.jpg)

## Launch the project in the terminal
- Clone repository : `git clone https://github.com/NIU1607107/TQS-Sudoku.git`
- `cd ./TQS-Sudoku/`
- Compile source code : `javac src/sudoku/Game.java`
- `cd ./src/`
- Starting the game : `java sudoku.Game`

## MVC software architectural pattern

  * *Model* (Data)
    * Saving of the Sudoku grid by level in a TXT file 
  * *Controller* (Logic)
    * Generate the sudoku, check the player's inputs and solve it
  * *View* (Display) 
    * Terminal, or the command line interface (CLI)
    
## Test-Driven Development - Requirements

* *Sudoku Generator* – creates Sudoku grids in different levels. 
1.	is a 9x9 list of lists
2.	contains, in each of its 81 elements, a number in the range 0..9
3.	each number in the range 1..9 occurs only once in each row
4.	each number in the range 1..9 occurs only once in each column
5.	each number the range 1..9 occurs only once in each of the nine 3x3 sub-grids, or “boxes”, that make up the board
* *Sudoku Solver* - solves any Sudoku grid.
* ...

## 4 mock objects

* MockDB: cancels the random function DB.query() which returns a random sudoku grid from a text file according to the level of complexity requested.
* MockDBEmpty: simulates the reading of an empty database thanks to the TestEmptyDB.txt file.
* MockSolver: cancels the random function SolverInterface.generateRandomList() that returns a list of numbers from 1 to 9 in random order to solve a sudoku grid.
* MockGenerator: cancels the random function GeneratorInterface.eraseCells() which randomly chooses the cells to be erased in a solvable grid to generate a grid according to a number of empty cells which defines the complexity.
