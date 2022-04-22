package tetrispackage;

import java.awt.Color;
import java.awt.Label;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.KeyStroke;
import javax.swing.SpringLayout;
import javax.swing.border.LineBorder;

public class TetrisFrame extends JFrame{
	
	private TetrisPanel tf;
	
	public TetrisFrame()
	{
		new JFrame();
		JLabel scoreDisplay = new JLabel("Score : 0");	/**/
		JLabel levelDisplay = new JLabel("Level : 1");	/**/
		this.setSize(552, 534);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.add(scoreDisplay);							/**/
		this.add(levelDisplay);							/**/
		SpringLayout springLayout = new SpringLayout();
		getContentPane().setLayout(springLayout);
		
		//initComponents();
		tf = new TetrisPanel();
		springLayout.putConstraint(SpringLayout.NORTH, tf, 20, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, tf, 175, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, tf,420 , SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, tf, -163, SpringLayout.EAST, getContentPane());
		tf.setBorder(new LineBorder(new Color(0, 0, 0)));
		tf.setBackground(Color.LIGHT_GRAY);
		getContentPane().add(tf);
		
		initControls();
		
		startGame();
	}
	
	private void initControls()
	{
		InputMap im = this.getRootPane().getInputMap();
		ActionMap am = this.getRootPane().getActionMap();
		
		im.put(KeyStroke.getKeyStroke("RIGHT"), "right");
		im.put(KeyStroke.getKeyStroke("LEFT"), "left");
		im.put(KeyStroke.getKeyStroke("UP"), "up");
		im.put(KeyStroke.getKeyStroke("DOWN"), "down");

		
		am.put("right", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				tf.moveBlockRight();
				}
			});	
		
		am.put("left", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				tf.moveBlockLeft();
				}
			});	
		
		am.put("up", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				tf.rotateBlock();
				}
			});	
		
		am.put("down", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				tf.dropBlock();
				}
			});	
	}
	
	public void startGame()
	{
		new TetrisThread(tf, this).start();
	}
	
	public void updateScore(int score)
	{
		System.out.println("Score : " + score);		/**/
	}
	
	public void updateLevel(int level)
	{
		System.out.println("Level : " + level);		/**/
	}

	public static void main(String args[]) {
		
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				
				new TetrisFrame().setVisible(true);
			}
		});
	}
}