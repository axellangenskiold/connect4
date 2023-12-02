package connect4.java;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class GUI {
	private static Color yellow = Color.YELLOW;
	private static Color red = Color.RED;
	Window[][] board;
	JFrame frame;
	
	public GUI(Game game) {
        SwingUtilities.invokeLater(() -> createWindow(game));
	}
	
	
	private void createWindow(Game game) {
		frame = new JFrame("Four In A Row");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JPanel buttons = new JPanel();
        JButton reset = new JButton("RESTART");
        buttons.add(reset);
        
        
        
        
        JPanel gridPanel = new JPanel(new GridLayout(6, 7));
        frame.setPreferredSize(new Dimension(700, 600));
        
        Object[] options = {"Super AI", "Computer", "2 vs 2"};
        
        int choice = JOptionPane.showOptionDialog(null, null, "Choose your mode", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, null);
        
        
        board = new Window[6][7];
        
        for (int i = 0; i < 6; i++) {
        	for (int j = 0; j < 7; j++) {
        		Window w = new Window(i, j, game, choice);
        		board[i][j] = w;
        		gridPanel.add(w);
        		
        	}
        }
        
        frame.add(gridPanel, BorderLayout.CENTER);
        frame.add(buttons, BorderLayout.SOUTH);
        frame.pack();
        frame.setVisible(true);
        
        
        System.out.println(choice);
        
        reset.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		game.reset();
        		
        		for (int i = 0; i < 6; i++) {
                	for (int j = 0; j < 7; j++) {
                		board[i][j].setBackground(Color.WHITE);
                	}
        		}
        		
        		frame.repaint();
        		
        		game.printMatrix();
        	}
        });
	}
	
	
	private class Window extends JButton {

		private Color color;
		private int x;
		private int y;
		JFrame f;
		Game g;
		int choice;
		
		public Window(int yc, int xc, Game game, int c) {
			color = Color.WHITE;
			x = xc;
			y = yc;
			f = frame;
			g = game;
			this.choice = c;
			
			setBorder(BorderFactory.createLineBorder(Color.BLUE, 20));
			setBackground(color);
			setOpaque(true);
			
				
			
			addActionListener(new ActionListener() { 
				  public void actionPerformed(ActionEvent e) {
					  
					  if (choice == 0) {
						  
						  boolean b = g.add(y, x);

						  
						  updateBoard(g);
						  
						  isGameWin(g);
						  
						  AI ai = new AI(g);
						  int[] move = ai.getBestMove(4);
						  g.add(move[0], move[1]);
						  
					  }
					  else if (choice == 1) {
						  
						  boolean b = g.add(y, x);

						  
						  updateBoard(g);
						  
						  isGameWin(g);
						  
						  AI ai = new AI(g);
						  int[] move = ai.getBestMove(2);
						  g.add(move[0], move[1]);
						  
					  }
					  else if (choice == 2) {
						  g.add(y, x);
						  
					  }
					  updateBoard(game);
					  
					  isGameWin(game);
					  
					  if (choice == -1) {
						  f.dispose();
						  f.repaint();
					  }
					  
					  
				  } 
				} );
			

			
			
		}
		public String toString() {
			return x + y + color.toString();
		}
		
		public void updateBoard(Game game) {
			for (Window[] w : board) {
				  for (Window p : w) {
					  if (game.board[p.y][p.x] == 1) {
						  board[p.y][p.x].setBackground(red);
					  }
					  else if (game.board[p.y][p.x] == 2) {
						  board[p.y][p.x].setBackground(yellow);
					  }
				  } 
			  }
			frame.repaint();
		}
		
		public void isGameWin(Game game) {
			if (game.win()) {
				  if (game.turn == 1) {
					  JOptionPane.showMessageDialog(frame, "Yellow won");
				  } else {
					  JOptionPane.showMessageDialog(frame, "Red won");
				  }

			  }
		}
		
	}
}


