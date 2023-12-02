package connect4.java;

public class AI {
	int[][] board; 
	Game game;
	
	// a board with zeros, ones and twos
	// zeros are blank spaces, ones are the player and twos are the ai

	public AI(Game g) {
		board = g.board;
		game = g;
		
	}
	
	
	//uses the board and returns a tuple with y, x coordinates for the best move that is on the board
	    
    public int[] getBestMove(int depth) {
        int bestScore = Integer.MIN_VALUE;
        int[] bestMove = new int[] {-1, -1};

        for (int col = 0; col < board[0].length; col++) {
            if (isValidMove(col)) {
                int row = getValidRow(col);
                board[row][col] = 2; // Assume AI's move
                int score = minimax(depth, Integer.MIN_VALUE, Integer.MAX_VALUE, false);
                board[row][col] = 0; // Undo the move

                if (score > bestScore) {
                    bestScore = score;
                    bestMove[0] = row;
                    bestMove[1] = col;
                }
            }
        }

        return bestMove;
    }

    private int minimax(int depth, int alpha, int beta, boolean isMaximizingPlayer) {
        if (depth == 0 || game.win()) {
            return evaluateBoard();
        }

        if (isMaximizingPlayer) {
            int maxScore = Integer.MIN_VALUE;
            for (int col = 0; col < board[0].length; col++) {
                if (isValidMove(col)) {
                    int row = getValidRow(col);
                    board[row][col] = 2; // Assume AI's move
                    int score = minimax(depth - 1, alpha, beta, false);
                    board[row][col] = 0; // Undo the move
                    maxScore = Math.max(maxScore, score);
                    alpha = Math.max(alpha, score);
                    if (beta <= alpha) {
                        break; // Beta cutoff
                    }
                }
            }
            return maxScore;
        } else {
            int minScore = Integer.MAX_VALUE;
            for (int col = 0; col < board[0].length; col++) {
                if (isValidMove(col)) {
                    int row = getValidRow(col);
                    board[row][col] = 1; // Assume player's move
                    int score = minimax(depth - 1, alpha, beta, true);
                    board[row][col] = 0; // Undo the move
                    minScore = Math.min(minScore, score);
                    beta = Math.min(beta, score);
                    if (beta <= alpha) {
                        break; // Alpha cutoff
                    }
                }
            }
            return minScore;
        }
    }

    private boolean isValidMove(int col) {
        return board[0][col] == 0;
    }

    private int getValidRow(int col) {
        for (int row = board.length - 1; row >= 0; row--) {
            if (board[row][col] == 0) {
                return row;
            }
        }
        return -1; // Invalid move
    }

    private int evaluateBoard() {
        int aiScore = 0;
        int playerScore = 0;

        // Evaluate rows
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col <= board[row].length - 4; col++) {
                int score = evaluateLine(board[row][col], board[row][col + 1], board[row][col + 2], board[row][col + 3]);
                aiScore += score;
                playerScore -= score;
            }
        }

        // Evaluate columns
        for (int col = 0; col < board[0].length; col++) {
            for (int row = 0; row <= board.length - 4; row++) {
                int score = evaluateLine(board[row][col], board[row + 1][col], board[row + 2][col], board[row + 3][col]);
                aiScore += score;
                playerScore -= score;
            }
        }

        // Evaluate diagonal (top-left to bottom-right)
        for (int row = 0; row <= board.length - 4; row++) {
            for (int col = 0; col <= board[row].length - 4; col++) {
                int score = evaluateLine(board[row][col], board[row + 1][col + 1], board[row + 2][col + 2], board[row + 3][col + 3]);
                aiScore += score;
                playerScore -= score;
            }
        }

        // Evaluate diagonal (top-right to bottom-left)
        for (int row = 0; row <= board.length - 4; row++) {
            for (int col = board[row].length - 1; col >= 3; col--) {
                int score = evaluateLine(board[row][col], board[row + 1][col - 1], board[row + 2][col - 2], board[row + 3][col - 3]);
                aiScore += score;
                playerScore -= score;
            }
        }

        return aiScore - playerScore;
    }

    private int evaluateLine(int cell1, int cell2, int cell3, int cell4) {
        int aiCount = 0;
        int playerCount = 0;

        if (cell1 == 2) {
            aiCount++;
        } else if (cell1 == 1) {
            playerCount++;
        }

        if (cell2 == 2) {
            aiCount++;
        } else if (cell2 == 1) {
            playerCount++;
        }

        if (cell3 == 2) {
            aiCount++;
        } else if (cell3 == 1) {
            playerCount++;
        }

        if (cell4 == 2) {
            aiCount++;
        } else if (cell4 == 1) {
            playerCount++;
        }

        if (aiCount == 4) {
            return 1000; // AI wins
        } else if (playerCount == 4) {
            return -1000; // Player wins
        } else if (aiCount == 3 && playerCount == 0) {
            return 100; // AI has 3 in a row
        } else if (aiCount == 2 && playerCount == 0) {
            return 10; // AI has 2 in a row
        } else if (aiCount == 1 && playerCount == 0) {
            return 1; // AI has 1 in a row
        } else if (aiCount == 0 && playerCount == 3) {
            return -100; // Player has 3 in a row
        } else if (aiCount == 0 && playerCount == 2) {
            return -10; // Player has 2 in a row
        } else if (aiCount == 0 && playerCount == 1) {
            return -1; // Player has 1 in a row
        }

        return 0; // No winning or scoring pattern
    }
    
	
}

