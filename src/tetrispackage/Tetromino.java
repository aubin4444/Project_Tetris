package tetrispackage;

import java.awt.Color;
import java.util.Random;

public class Tetromino 
{
	private int[][] shape;
	private Color color;
	private int x, y;
	private int[][][] shapes;
	private int rotation;
	
	private Color[] availableColors = {Color.red, Color.blue, Color.green, Color.pink, Color.orange, Color.yellow, Color.black};
	
	public Tetromino(int [][] shape)
	{
		this.shape = shape;
	
		defineShapes();
	}
	
	private void defineShapes()
	{
		shapes = new int[4][][];
		
		for(int i = 0; i < 4; i++) 
		{
			int r = shape[0].length;
			int c = shape.length;
			
			shapes[i] = new int[r][c];
			
			for(int y = 0; y < r; y++) 
			{
				for(int x = 0; x < c; x++)
				{
					shapes[i][y][x] = shape[c - x - 1][y];
				}
			}
			
			shape = shapes[i];
		}
	}
	
	public void spawn(int gridWidth)
	{
		Random r = new Random();
		
		rotation = r.nextInt(shapes.length);
		shape = shapes[rotation];
		
		x = (gridWidth - getWidth()) / 2;
		x = r.nextInt(gridWidth - getWidth());
		
		color = availableColors[r.nextInt(availableColors.length)];
	}
	
	public int[][] getShape()		//getter
	{
		return this.shape;
	}
	
	public Color getColor()			//getter
	{
		return this.color;
	}
	
	public int getX()				//getter
	{
		return this.x;
	}
	
	public void setX(int x)			//setter
	{
		this.x = x;
	}
	
	public int getY()				//getter
	{
		return this.y;
	}
	
	public void setY(int y)			//setter
	{
		this.y = y;
	}
	
	public int getHeight()
	{
		return shape.length;
	}
	
	public int getWidth()
	{
		return shape[0].length;
	}
	
	public void fallingBlock()
	{
		y++;
	}
	
	public void moveLeft()
	{
		x--;
	}
	
	public void moveRight()
	{
		x++;
	}
	
	public int bottomGrid()
	{
		return y + getHeight();
	}
	
	public void rotate()
	{
		rotation++;
		if (rotation > 3) rotation = 0;
		shape= shapes[rotation];
	}
	
	public int getLeftBorder()
	{
		return x;
	}
	
	public int getRightBorder()
	{
		return x + getWidth();
	}
}
