import connectK.CKPlayer;
import connectK.BoardModel;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;

public class SalsaAndBachataAI extends CKPlayer {
	
	private int twos = 0; 
	private byte player; 
	private byte otherPlayer; 
	private int depth = 0; 
	
	private final int MAX = 214748;
	private final int MIN = -214748;

	public SalsaAndBachataAI(byte player, BoardModel state) {
		super(player, state);
		
		teamName = "SalsaAndBachataAI";
		this.player = player; 
		determineOtherPlayer();
	}

	@Override
	public Point getMove(BoardModel state) {
//		System.out.println("****Spaces left: " + state.spacesLeft);
//		test(state);
		
//		int randomX = (int )(Math.random() * state.getWidth());
//		int randomY = (int )(Math.random() * state.getHeight());
//		while (state.getSpace(randomX,randomY) == 1 || state.getSpace(randomX,randomY) == 2) {
//			randomX = (int )(Math.random() * state.getWidth());
//			randomY = (int )(Math.random() * state.getHeight());		
//		}
//		Point p = new Point(randomX, randomY);
//		int twos; 
//		BoardModel tempState = state.clone();
//		byte player = 2; 
//		tempState = tempState.placePiece(p, player);
//		
//		int p1score, p2score; 
//		p1score = evaluationFunction(tempState, this.player);
//		
//		p2score = evaluationFunction(tempState, this.otherPlayer);
//		
//		System.out.println("p2's move is " + tempState.lastMove.toString());
//		System.out.println("p1score : " + p1score);
//		System.out.println("p2score : " + p2score);
//		System.out.println("********************");
		
		
//		System.out.println("******");
//		System.out.println("Player 1 Stats");
//		//countDimensionalRows(tempState,4);
//		int total = 0; 
//		for (int i = tempState.kLength; i > 1; --i) {
//			total += countDimensionalRowsTest(tempState,i,1) * i;
//			System.out.println("******");
//		}
		
//		System.out.println("total = " + total);
		
//		System.out.println("*****************************");
//		System.out.println("Player 2 Stats");
//		for (int i = tempState.kLength; i > 1; --i) {
//			countDimensionalRowsTest(tempState,i,2);
//			System.out.println("******");
//		}
		
		//System.out.println("****rows of twos is " + this.twos);
//		System.out.println("***********before************");
//		System.out.println(state.toString());
//		state = state.placePiece(new Point(1,5), this.otherPlayer);
//		System.out.println("***********after************");
//		System.out.println(state.toString());
//		System.out.println("***********************");
		
		int[] move = mmSearch(state);
//		
////		System.out.println("x: " + move[1] + " , y: " + move[2]);
		
		Point p = new Point(move[1], move[2]);
		
		///System.out.println("****newAI****");
//		BoardModel temp = state.placePiece(point, this.player);
//		
//		System.out.println("heuristic");
//		int ai = numberOfWinningLines(temp, this.otherPlayer);
//		int me = numberOfWinningLines(temp, this.player);
//		System.out.println("Blue: " + ai + " - " + me + " : Me " );
		//state.pieces[1][0] = 2;
		//state.pieces[0][2] = 2; 
	
		// quick testing 
		/*System.out.print(state.toString());
		byte b = 2; 
		int total = 0; 
		int eval1 = 0;
		int eval2 = 0; 
		eval1 = evaluationFunction(state,2,b);
		
		b = 1; 
		eval2 = evaluationFunction(state,2,b);
		System.out.println("red: " + eval2 + " - " + eval1 + " :blue");
		total = eval2 - eval1; 
		System.out.print("eval: " + total); 
		Point p = new Point(2,2);*/
		
//		BoardModel temp = state.placePiece(new Point(2,0), this.player);
//		
//		System.out.println(temp.toString());
//
//		int blue = evaluationFunction(temp, 2, this.player);
//		int red = evaluationFunction(temp, 2, this.otherPlayer);
//		
//		System.out.println("blue: " + blue + " - " + red + " :red");
//		
//		Point p = new Point(2,0);
		return p;
	}

	@Override
	public Point getMove(BoardModel state, int deadline) {
		return getMove(state);
	}
	
	private void determineOtherPlayer() {
		if (this.player == 1)
			this.otherPlayer = 2;
		else
			this.otherPlayer = 1; 
	}
	
	private int numberOfWinningLines(BoardModel state, byte opponent) {
		int horizontalLines, verticalLines, diagonalUpLines, diagonalDownLines;
		horizontalLines = verticalLines = diagonalUpLines = diagonalDownLines = 0; 
		
		horizontalLines = horizontalLines(state, opponent); 
		verticalLines = verticalLines(state, opponent); 
		diagonalUpLines = diagonalUpLines(state, opponent);
		diagonalDownLines = diagonalDownLines(state, opponent); 
		
		return horizontalLines + verticalLines + diagonalUpLines + diagonalDownLines; 
	}
	
	private int horizontalLines(BoardModel state, byte opponent) {
//		System.out.println("Inside horizo()");
		byte[][] pieces = state.pieces;
		int lineCount = 0;
		boolean flag = false; 
		
		for (int i = 0; i < state.width; ++i) 
			for (int j = 0; j < state.height; ++j) 
				
				if (pieces[i][j] != opponent) {
					// horizontal 
					for (int count = 1; count < state.kLength; ++count) {
						if ( i + (state.kLength - 1) < state.width && opponent != pieces[i + count][j]) {
							flag = true; 
							continue; 
						} else {
							flag = false; 
							break;
						}
					}
					
					if (flag)
						++lineCount; 
				}
		
		return lineCount; 
	}
	
	private int verticalLines(BoardModel state, byte opponent) {
			byte[][] pieces = state.pieces;
			int lineCount = 0;
			boolean flag = false; 
			
			for (int i = 0; i < state.width; ++i) 
				for (int j = 0; j < state.height; ++j) 
					
					if (pieces[i][j] != opponent) {
						// vertical
						for (int count = 1; count < state.kLength; ++count) {
							if ( j + (state.kLength - 1) < state.height && opponent != pieces[i][j+count]) {
								flag = true; 
								continue; 
							} else {
								flag = false; 
								break;
							}
						}
						
						if (flag)
							++lineCount; 
					}
			
			return lineCount; 
	}
	
	private int diagonalUpLines(BoardModel state, byte opponent) {
		byte[][] pieces = state.pieces;
		int lineCount = 0;
		boolean flag = false; 
		
		for (int i = 0; i < state.width; ++i) 
			for (int j = 0; j < state.height; ++j) 
				
				if (pieces[i][j] != opponent) {
					// diagonal up
					for (int count = 1; count < state.kLength; ++count) {
						if ( i + (state.kLength - 1) < state.width && j + (state.kLength- 1) < state.height 
								&& opponent != pieces[i+count][j+count]) {
							flag = true; 
							continue; 
						} else {
							flag = false; 
							break;
						}
					}
					
					if (flag)
						++lineCount; 
				}
		
		return lineCount; 
	}
	
	private int diagonalDownLines(BoardModel state, byte opponent) {
		byte[][] pieces = state.pieces;
		int lineCount = 0;
		boolean flag = false; 
		
		for (int i = 0; i < state.width; ++i) 
			for (int j = 0; j < state.height; ++j) 
				
				if (pieces[i][j] != opponent) {
					// diagonal up
					for (int count = 1; count < state.kLength; ++count) {
						if ( i - (state.kLength - 1) >= 0 && j + (state.kLength - 1) < state.height 
								&& opponent != pieces[i - count][j + count]) {
							flag = true; 
							continue; 
						} else {
							flag = false; 
							break;
						}
					}
					
					if (flag)
						++lineCount; 
				}
		
		return lineCount; 
	}
	
	
	private byte opponent(byte player) {
		if (player == 1) 
			return 2;
		else
			return 1; 
	}
	
	private int evaluationFunction(BoardModel state, int depth, byte player) {		
		// not considering ties !!!!! need to account for TIES
//		if (state.winner() == player){
//			return this.MAX;
//		} else if (state.winner() == opponent(player)) {
//			return this.MIN; 
//		}
		
//		return  kMinusOneConnectedCount(state, player) + numberOfWinningLines(state, opponent(player)); 
		return numberOfWinningLines(state, opponent(player));
	}
	
//	private int kMinusOneConnectedCount(BoardModel state, byte player) {
//		
//		int vertical, horizontal, diagonalUp, diagonalDown; 
//		vertical = verticalCount(state,state.kLength - 1, player); 
//		horizontal = horizontalCount(state,state.kLength - 1, player); 
//		diagonalUp = diagonalUpCount(state,state.kLength - 1, player); 
//		diagonalDown = diagonalDownCount(state,state.kLength - 1, player); 
//		
//		return (vertical + horizontal + diagonalUp + diagonalDown) * 100;  
//	}
	
	private Point randomMove(BoardModel state) {
		int randomX = (int )(Math.random() * state.getWidth());
		int randomY = (int )(Math.random() * state.getHeight());
		
		while (state.getSpace(randomX,randomY) == 1 || state.getSpace(randomX,randomY) == 2) {
			randomX = (int )(Math.random() * state.getWidth());
			randomY = (int )(Math.random() * state.getHeight());	
		}
		
		return new Point(randomX, randomY);
	}
	
	private int[] mmSearch(BoardModel state) {
		
//		System.out.println("inside mmSearch()");
		int depth = 3; 
		
		ArrayList<BoardModel> children = generateChildren(state, this.player); 
		
		int[] v = {this.MIN, -1, -1};
		
		boolean flag = true; 
		
		for (BoardModel child: children)  {
			Point move = child.getLastMove();
//			System.out.println("childs move is " + move.toString());
			int min[]  = minValue(child, depth - 1, move.x, move.y);
			
			if (v[0] < min[0]) {
//				v[0] = min[0];
//				v[1] = min[1];
//				v[2] = min[2];
				
				// try this 
				v = min;
				flag = false; 
			}
		}
		
		if (flag) {
			Point p  = randomMove(state);
			v [1] = p.x;
			v[2] = p.y;
		}

		return v;
	}
	
	
	private ArrayList<BoardModel> generateChildren(BoardModel state, byte playersTurn) {
		ArrayList<BoardModel> children = new ArrayList<BoardModel>();
		actions(state,children, playersTurn);
		return children; 
	}
	
	// this will only work for a small board and when GRAVITY is off!!!!
	private void actions(BoardModel state, ArrayList<BoardModel> children, byte playersTurn) {
		for (int i = 0; i < state.width; ++i) {
			for (int j = 0; j < state.height; ++j) {
//			BoardModel temp = state.clone();
//			
//			temp.placePiece(new Point(i,0), this.player);
//			System.out.println(temp.toString());
			
//			Point p = state.placePiece(new Point(i,state.height - 1), this.player).getLastMove();
//			
//			children.add(state.placePiece(p, this.player));
			Point p = new Point(i,j);
			if (state.getSpace(p) == 0)
				children.add(state.placePiece(p, playersTurn));
			}
		}
	}
	
	private int[] maxValue(BoardModel state, int depth, int x, int y) {
//		System.out.println("inside maxValue() " + "depth is :" + this.depth);
		
		if (depth == 0)
			return new int[] {utility(state, depth), x, y};
		if (terminal(state)) {
			System.out.println("**terminal state reached**");
			return new int[] {utility(state, depth), x, y};
		}
				
		ArrayList<BoardModel> children = generateChildren(state, this.player);
		
		int[] v = {this.MIN, x, y};
		
		for (BoardModel child: children)  {
			int min  = minValue(child, depth - 1, x, y)[0];
			
			if (v[0] < min) 
				v[0] = min;
		}
		
		return v; 
	}
	
	private int[] minValue(BoardModel state, int depth, int x, int y) {
//		System.out.println("inside minValue() " + "depth is :" + this.depth);
		
		if (depth == 0)
			return new int[] {utility(state, depth), x, y};
		if (terminal(state)) {
			System.out.println("**terminal state reached**");
			return new int[] {utility(state, depth), x, y};
		}
		
		
		ArrayList<BoardModel> children = generateChildren(state, this.otherPlayer);
		
		int[] v = {this.MAX, x, y};
		
		for (BoardModel child: children)  {
			int max  = maxValue(child, depth - 1,  x, y)[0];
			
			if (v[0] > max)
				v[0] = max;
		}
		return v; 
	}
	
	private int utility(BoardModel state, int depth) {			
		if (state.winner() == this.player)
			return this.MAX;
		else if (state.winner() == this.otherPlayer) 
			return this.MIN;

		return evaluationFunction(state, depth, this.player) - evaluationFunction(state, depth, this.otherPlayer); 
	}
	
	private boolean terminal(BoardModel state) {
		// need to implement this
		// if no more moves left its a tie return 0 figure out what to do from here
		// if utility returns a high number somebody has won
		
		if (! state.hasMovesLeft()) 
			return true;
		if (state.winner() != -1 )
			return true; 
		return false;
	}
	
	
	
	private int horizontalCount(BoardModel state, int n, byte player) {
		byte[][] pieces = state.pieces;
		int rowCount = 0;
		boolean flag = false; 
		
		for (int i = 0; i < state.width; ++i) 
			for (int j = 0; j < state.height; ++j) 
				
				if (pieces[i][j] == player) {
					// horizontal 
					for (int count = 1; count < n; ++count) {
						if ( i + (n - 1) < state.width && pieces[i][j] == pieces[i + count][j]) {
							flag = true; 
							continue; 
						} else {
							flag = false; 
							break;
						}
					}
					
					if (flag)
						++rowCount; 
				}
		
		return rowCount; 
	}
	
	private int verticalCount(BoardModel state, int n, int player) {
		byte[][] pieces = state.pieces;
		int rowCount = 0;
		boolean flag = false; 
		
		for (int i = 0; i < state.width; ++i) 
			for (int j = 0; j < state.height; ++j) 
				
				if (pieces[i][j] == player) {
					// vertical
					for (int count = 1; count < n; ++count) {
						if ( j + (n - 1) < state.height && pieces[i][j] == pieces[i][j+count]) {
							flag = true; 
							continue; 
						} else {
							flag = false; 
							break;
						}
					}
					
					if (flag)
						++rowCount; 
				}
		
		return rowCount; 
	}
	
	private int diagonalUpCount(BoardModel state, int n, int player) {
		byte[][] pieces = state.pieces;
		int rowCount = 0;
		boolean flag = false; 
		
		for (int i = 0; i < state.width; ++i) 
			for (int j = 0; j < state.height; ++j) 
				
				if (pieces[i][j] == player) {
					// diagonal up
					for (int count = 1; count < n; ++count) {
						if ( i + (n - 1) < state.width && j + (n- 1) < state.height 
								&& pieces[i][j] == pieces[i+count][j+count]) {
							flag = true; 
							continue; 
						} else {
							flag = false; 
							break;
						}
					}
					
					if (flag)
						++rowCount; 
				}
		
		return rowCount; 
	}
	
	private int diagonalDownCount(BoardModel state, int n, int player) {
		byte[][] pieces = state.pieces;
		int rowCount = 0;
		boolean flag = false; 
		
		for (int i = 0; i < state.width; ++i) 
			for (int j = 0; j < state.height; ++j) 
				
				if (pieces[i][j] == player) {
					// diagonal down
					for (int count = 1; count < n; ++count) {
						if ( i - (n - 1) >= 0 && j + (n - 1) < state.height 
								&& pieces[i][j] == pieces[i-count][j+count]) {
							flag = true; 
							continue; 
						} else {
							flag = false; 
							break;
						}
					}
					
					if (flag)
						++rowCount; 
				}
		
		return rowCount; 
	}
	
	
	/*
	 	public void countDimensionalRows(BoardModel state, int dimension) {
		Point p = state.getLastMove();
		byte[][] pieces = state.pieces;
		int counts = 0;
		boolean flag = false; 
		for (int i = 0; i < state.width; ++i) {
			for (int j = 0; j < state.height; ++j) {
				
				if (pieces[i][j] == 1) {
					// horizontal 
					for (int count = 1; count < dimension; ++count) {
						if ( i + (dimension - 1) < state.width && pieces[i][j] == pieces[i + count][j]) {
							flag = true; 
							continue; 
						} else {
							flag = false; 
							break;
						}
					}
					
					if (flag) {
						++counts; 
					}
					
					flag = false; 
					
					
					// vertical 
					for (int count = 1; count < dimension; ++count) {
						if ( j + (dimension - 1) < state.height && pieces[i][j] == pieces[i][j+count]) {
							flag = true; 
							continue; 
						} else {
							flag = false; 
							break;
						}
					}
					
					if (flag) {
						++counts; 
					}
					
					flag = false; 
					
					
					// diagonal up /
					for (int count = 1; count < dimension; ++count) {
						if ( i + (dimension - 1) < state.width && j + (dimension - 1) < state.height 
								&& pieces[i][j] == pieces[i+count][j+count]) {
							flag = true; 
							continue; 
						} else {
							flag = false; 
							break;
						}
					}
					
					if (flag) {
						++counts; 
					}
					
					flag = false; 
					
					
					// diagonal down \
					for (int count = 1; count < dimension; ++count) {
						if ( i - (dimension - 1) >= 0 && j + (dimension - 1) < state.height 
								&& pieces[i][j] == pieces[i-count][j+count]) {
							flag = true; 
							continue; 
						} else {
							flag = false; 
							break;
						}
					}
					
					if (flag) {
						++counts; 
					}
					
					flag = false; 
					
			
				}
			}
		}
		
		System.out.println("counts is : " + counts);
	}
	
	
	public int countDimensionalRowsTest(BoardModel state, int dimension, int player) {
		Point p = state.getLastMove();
		byte[][] pieces = state.pieces;
		int counts = 0;
		boolean flag = false; 
		for (int i = 0; i < state.width; ++i) {
			for (int j = 0; j < state.height; ++j) {
				
				if (pieces[i][j] == player) {
					// horizontal 
					for (int count = 1; count < dimension; ++count) {
						if ( i + (dimension - 1) < state.width && pieces[i][j] == pieces[i + count][j]) {
							flag = true; 
							continue; 
						} else {
							flag = false; 
							break;
						}
					}
					
					if (flag) {
						++counts; 
//						System.out.println("**before**");
//						System.out.println(state.toString());
//						for (int col = 0; col < dimension ; ++col) {
//							System.out.println("debug");
//							state.pieces[i + col][j] = 0;
//						}
//						System.out.println("**after**");
//						System.out.println(state.toString());
					}
					
					flag = false; 
					

					// vertical 
					for (int count = 1; count < dimension; ++count) {
						if ( j + (dimension - 1) < state.height && pieces[i][j] == pieces[i][j+count]) {
							flag = true; 
							continue; 
						} else {
							flag = false; 
							break;
						}
					}
					
					if (flag) {
						++counts; 						
					}
					
					flag = false; 
					
					// diagonal up /
					for (int count = 1; count < dimension; ++count) {
						if ( i + (dimension - 1) < state.width && j + (dimension - 1) < state.height 
								&& pieces[i][j] == pieces[i+count][j+count]) {
							flag = true; 
							continue; 
						} else {
							flag = false; 
							break;
						}
					}
					
					if (flag) {
						++counts; 
					}
					
					flag = false; 
					
					
					// diagonal down \
					for (int count = 1; count < dimension; ++count) {
						if ( i - (dimension - 1) >= 0 && j + (dimension - 1) < state.height 
								&& pieces[i][j] == pieces[i-count][j+count]) {
							flag = true; 
							continue; 
						} else {
							flag = false; 
							break;
						}
					}
					
					if (flag) {
						++counts; 
					}
					
					flag = false; 
					
				}
			}
		}
		
		System.out.println("counts is : " + counts);
		return counts; 
	}
	 */
}