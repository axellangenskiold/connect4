package connect4.java;

public class Game {
	int[][] board;
	int turn;
	
	public Game() {
		board = new int[6][7];
		
		reset();
	}
	
	public int[][] getBoard(){
		return board;
	}
	
	public int getColor(int y, int x) {
		return board[y][x];
	}
	
	public boolean changeTurn() {
		if (turn == 1) {
			turn = 2;
		}
		else {
			turn = 1;
		}
		return true;
	}
	
	public int oppositeTurn() {
		if (turn == 1) {
			return 2;
		}
		else {
			return 1;
		}
	}
	
	public void printMatrix() {
		for (int[] i : board) {
			System.out.println();
			for (int j : i) {
				System.out.print(j);
			}
		}
	}
	
	public void reset() {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				board[i][j] = 0;
			}
		}
		turn = 1;
	}
	
	
	public boolean add(int y, int x) {
		if (y < 0) {
			System.out.println("OVER");
			return false;
		}
		if (y == 5 && board[y][x] == 0) {
			board[y][x] = turn;
			return changeTurn();
		} else {
			if (board[y][x] == 0) {
				if (board[y+1][x] != 0) {
					board[y][x] = turn;
					return changeTurn();
				}
				else {
					add(y+1, x);
				}
				
			}
			else {
				add(y-1, x);
			}
		}
		System.out.println("UNDER");
		return false;
		
			
	}
	//Checks if win
	
	public boolean win() {
	    for (int i = 0; i < board.length; i++) {
	    	for (int j = 0; j < 4; j++) {
	    		if (
	    				board[i][j] == oppositeTurn() && 
	    				board[i][j+1] == oppositeTurn() && 
	    				board[i][j+2] == oppositeTurn() && 
	    				board[i][j+3] == oppositeTurn()) {
	    			
	    			return true;
	    		}
	    	}
	    }
	    
	    for (int i = 0; i < 3; i++) {
	    	for (int j = 0; j < board[0].length; j++) {
	    		if (
	    				board[i][j] == oppositeTurn() && 
	    				board[i+1][j] == oppositeTurn() && 
	    				board[i+2][j] == oppositeTurn() && 
	    				board[i+3][j] == oppositeTurn()) {
	    			
	    			return true;
	    		}
	    	}
	    }
	    
	    for (int i = 0; i < 3; i++) {
	    	for (int j = 0; j < 4; j++) {
	    		if (
	    				board[i][j] == oppositeTurn() && 
	    				board[i+1][j+1] == oppositeTurn() && 
	    				board[i+2][j+2] == oppositeTurn() && 
	    				board[i+3][j+3] == oppositeTurn()) {
	    			
	    			return true;
	    		}
	    	}
	    }
	    
	    for (int i = 5; i > 3; i--) {
	    	for (int j = 6; j > 3; j--) {
	    		if (
	    				board[i][j] == oppositeTurn() && 
	    				board[i-1][j-1] == oppositeTurn() && 
	    				board[i-2][j-2] == oppositeTurn() && 
	    				board[i-3][j-3] == oppositeTurn()) {
	    			
	    			return true;
	    		}
	    	}
	    }
	    
	    
	    
	    return false;
	}
}
