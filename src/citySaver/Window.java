package citySaver;

import java.awt.Canvas;
import java.awt.Dimension;

import javax.swing.JFrame;

public class Window {

	private Canvas canvas ;   // Canvas basically allow us to draw 
	private JFrame frame ; 
	private String title ; 
	private int width , height ; 
	
	public Window(String title  , int height , int width ){
		this.title = title ; 
		this.height = height ;   
		this.width  = width ;  
		createDisplay(); 
	}
	 
	private void createDisplay() { 
		
		frame = new JFrame(); 
		frame.setTitle(title); 
		frame.setSize(width , height);
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		canvas = new Canvas (); 
		canvas.setPreferredSize(new Dimension(width ,height));
		canvas.setMaximumSize(new Dimension(width , height) );
		canvas.setMinimumSize(new Dimension(width,height));
		canvas.setFocusable(false);
		frame.add(canvas); 
		frame.pack();
	 	
		
		 
	}
	
	
	public Canvas getCanvas() {
		return canvas;
	}
	
	public JFrame getFrame() {
		return  frame ;
	}
}
