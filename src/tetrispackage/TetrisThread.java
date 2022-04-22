package tetrispackage;

import java.util.logging.Level;
import java.util.logging.Logger;

public class TetrisThread extends Thread {
	
	private TetrisPanel tp;
	private TetrisFrame tf;
	private int score;
	private int level = 1;
	private int scorePerLevel = 3;
	
	private int pause = 500;
	private int speedLevel = 100;
	
	public TetrisThread(TetrisPanel tp, TetrisFrame tf)
	{
		this.tp = tp;
		this.tf = tf;
	}
	
	@Override
	public void run()
	{
		while(true)
		{
			tp.createTetromino();
			
			while(tp.fallOfTheTetromino())
			{
				try {
					Thread.sleep(pause);
				} catch (InterruptedException e) {
					Logger.getLogger(TetrisThread.class.getName()).log(Level.SEVERE, null, e);
				}
			}
			
			if(tp.isBlockOutOfBounds())
			{
				System.out.println("Game Over !");
				break;
			}
			
			tp.backgroundReach();
			score += tp.fullLine();
			tf.updateScore(score);
			
			int lvl = score / scorePerLevel +1;
			if(lvl > level)
			{
				level = lvl;
				tf.updateLevel(level);
				pause -= speedLevel;
			}
		}
	}
}
