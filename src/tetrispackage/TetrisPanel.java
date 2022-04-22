package tetrispackage;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import javax.swing.JPanel;

import tetromino.*;

public class TetrisPanel extends JPanel 
{
	private  int gridRows ;
	private  int gridColumns;
	private  int cellSize;
	private Color[][] background;
	
	private Tetromino block;
	
	private Tetromino[] blocks;

	public TetrisPanel() {
		
		this.setBounds(50,100,100,100);
		this.setBackground(Color.red);
		
		gridRows=20;
		gridColumns=10;
		cellSize=20;
		
		background = new Color[gridRows][gridColumns];
		
		blocks = new Tetromino[] {new IShape(), new LShape(), new TShape(), new JShape(), new SShape(), new ZShape(), new SquareShape()};
	}
	
	public void createTetromino()
	{
		Random r = new Random();
		
		block = blocks[r.nextInt(blocks.length)];
		block.spawn(gridColumns);
	}
	
	public boolean isBlockOutOfBounds()
	{
		if(block.getY() < 0)
		{
			block = null;
			return true;
		}
		return false;
	}
	
	private boolean bottomReach()
	{
		if(block.bottomGrid() == gridRows)
		{
			return false;
		}
		
		int[][]shape = block.getShape();
		int w = block.getWidth();
		int h = block.getHeight();
		
		for(int c = 0; c < w; c++) {
			for(int r = h - 1; r >= 0; r--) {
				if (shape[r][c] != 0)
				{
					int x = c + block.getX();
					int y = r + block.getY()+1;
					if(y < 0) break;
					if(background[y][x] != null) return false;
					break;
				}
			}
		}
		return true;
	}
	
	public void backgroundReach()
	{
		int [][] shape = block.getShape();
		int w = block.getWidth();
		int h = block.getHeight();
		
		int xPos = block.getX();
		int yPos = block.getY();
		
		Color color = block.getColor();
		
		for(int r = 0; r < h; r++)
		{
			for(int c = 0; c < w; c++)
			{
				if(shape[r][c] == 1)
				{
					background[r + yPos][c + xPos] = color;
				}
			}
		}
	}
	
	private void defineTetromino(Graphics g)
	{
		for(int r = 0; r < block.getHeight() ; r++) {
			for(int c = 0; c < block.getWidth() ; c++) {							
				if (block.getShape()[r][c] == 1)
				{
					int x = cellSize * (block.getX() + c);
					int y = cellSize * (block.getY() + r);
					
					g.setColor(block.getColor());
					g.fillRect(x, y, cellSize, cellSize);	//Draw and give a color to the tétromino
					g.setColor(Color.black);
					g.drawRect(x, y, cellSize, cellSize);//Thanks to drawRect() we draw cells by cells the grid
				}
			}
		}
	}
	
	private void defineBackground(Graphics g)
	{
		Color color;
		
		for(int r = 0; r < gridRows ; r++) {
			for(int c = 0; c < gridColumns ; c++) {							
				color = background[r][c];
				
				if (color != null)
				{
					int x = c * cellSize;
					int y = r * cellSize;
					
					g.setColor(color);
					g.fillRect(x, y, cellSize, cellSize);	//Draw and give a color to the tétromino
					g.setColor(Color.black);
					g.drawRect(x, y, cellSize, cellSize);//Thanks to drawRect() we draw cells by cells the grid
				
				}
			}
		}
	}
	
	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		defineBackground(g);
		defineTetromino(g);
	}
	
	public boolean fallOfTheTetromino()
	{
		if(bottomReach() == false)
		{
			return false;
		}
		
		block.fallingBlock();
		repaint();
		
		return true;
	}
	
	public void moveBlockRight()
	{
		if(block == null)return;
		if(!checkRight())return;
		
		block.moveRight();
		repaint();
	}
	
	public void moveBlockLeft()
	{
		if(block == null)return;
		if(!checkLeft())return;

		
		block.moveLeft();
		repaint();
	}
	
	public void dropBlock()
	{
		if(block == null)return;
		while(bottomReach())
		{
			block.fallingBlock();
		}
		repaint();
	}
	
	public void rotateBlock()
	{
		if(block == null)return;
		block.rotate();
		
		if(block.getLeftBorder() < 0) block.setX(0);
		if(block.getRightBorder() >= gridColumns) block.setX(gridColumns - block.getWidth());
		if(block.bottomGrid() >= gridRows) block.setY(gridRows - block.getHeight());
		
		repaint();
	}
	
	private boolean checkLeft()
	{
		if(block.getLeftBorder() == 0)return false;
		
		int[][] shape = block.getShape();
		int w = block.getWidth();
		int h = block.getHeight();
		
		for(int row = 0; row < h; row++) 
		{
			for(int col = 0; col<w; col++) 
			{
				if (shape[row][col] != 0)
				{
					int x = col + block.getX()-1;
					int y = row + block.getY();
					if(y < 0) break;
					if(background[y][x] != null) return false;
					break;
				}
			}
		}
		
		return true;
	}
	
	private boolean checkRight()
	{
		if(block.getRightBorder() == gridColumns)return false;
		
		int[][] shape = block.getShape();
		int w = block.getWidth();
		int h = block.getHeight();
		
		for(int row = 0; row < h; row++) 
		{
			for(int col = w-1; col>=0; col--) 
			{
				if (shape[row][col] != 0)
				{
					int x = col + block.getX()+1;
					int y = row + block.getY();
					if(y < 0) break;
					if(background[y][x] != null) return false;
					break;
				}
			}
		}
		
		return true;
	}
	
	public int fullLine()
	{
		boolean completeLine;
		int linesRemoved = 0;
		
		for(int r = gridRows - 1; r >= 0; r--)
		{
			completeLine = true;
			for(int c = 0; c < gridColumns; c++)
			{
				if(background[r][c] == null)
				{
					completeLine = false;
					break;							//Line is not complete
				}
			}
			if(completeLine == true)
			{
				linesRemoved++;
				removeLine(r);
				shiftDown(r);
				removeLine(0);
				
				r++;
				
				repaint();
			}
		}
		return linesRemoved;
	}
	
	private void removeLine(int r)
	{
		for(int i = 0; i < gridColumns; i++)
		{
			background[r][i] = null;
		}
	}
	
	private void shiftDown(int row)
	{
		for(int r = row; r > 0; r--)
		{
			for(int c = 0; c < gridColumns; c++)
			{
				background[r][c] = background[r - 1][c];
			}
		}
	}
}
