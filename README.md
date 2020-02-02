Simple console TicTacToe written in Java.

Each player (X and O) can be controlled be either an user or one of three difficulty levels of computer (easy, medium, hard).
At easy difficulty the computer make random moves. 
The "medium" level difficulty makes a move using the following process:
    If it can win in one move (if it has two in a row), it places a third to get three in a row and win.
    If the opponent can win in one move, it plays the third itself to block the opponent to win.
    Otherwise, it makes a random move. 
The hard difficulty uses the Minimax algorithm, which can see all possible outcomes till the end of the game and choose the best of them considering his opponent also would play perfectly.    

Game start in menu where you need to choose who is going to control both players. The valid instructions are "exit" and "start"
with desired options for example a game of user against hard difficulty will start with instruction "start user hard"

Insert the coordinates as "column row". The default gaming board is shown bellow, each point is labeled with its column and row:    
(1, 3) (2, 3) (3, 3)  
(1, 2) (2, 2) (3, 2)  
(1, 1) (2, 1) (3, 1)  
