//Justin Penkalski - May 30, 2018

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

public class TileBuilder 
{
	//Public Frame Members
	public static JFrame frame;
	public static JButton selector;
	public static JButton draw;
	public static JButton fonts;
	public static JButton clear;
	public static JButton fill;
	public static JButton confirmDesign;
	public static JCheckBox fillRow;
	public static JCheckBox fillCol;
	public static JCheckBox replaceColor;
	public static JComboBox<String> designPicker;
	
	//Public Constants
	public static final int GRID_SIZE = 50;
	public static final int CANVAS_WIDTH = 10;
	public static final int CANVAS_HEIGHT = 10;
	public static final int RIGHT_MENU_PADDING = 525;
	public static final int RIGHT_MENU_DEFAULT_WIDTH = 150;
	public static final int RIGHT_MENU_DEFAULT_HEIGHT = 25;
	public static final int RIGHT_MENU_SMALL_WIDTH = 75;
	public static final Color DEFAULT_SELECT_BORDER_COLOR = new Color(0, 0, 0);
	public static final Color SELECT_DEFAULT_SELECT_BORDER_COLOR = new Color(255, 255, 255);
	public static final Color DEFAULT_CANVAS_COLOR = new Color(200, 200, 200);
	public static final Color DEFAULT_CANVAS_BORDER_COLOR = new Color(25, 25, 25);
	public static final Border DEFAULT_CANVAS_BORDER = BorderFactory.createLineBorder(DEFAULT_CANVAS_BORDER_COLOR, 1);
	public static final Border DEFAULT_SELECT_BORDER = BorderFactory.createLineBorder(DEFAULT_SELECT_BORDER_COLOR, 3);
	public static final Border SELECT_SELECT_BORDER = BorderFactory.createLineBorder(SELECT_DEFAULT_SELECT_BORDER_COLOR, 3);
	
	//Public Variables
	public static ArrayList<JButton> selectionButtons = new ArrayList<JButton>();
	public static ArrayList<JButton> canvasButtons = new ArrayList<JButton>();
	public static ArrayList<JCheckBox> menuCheckBoxes = new ArrayList<JCheckBox>();
	public static Color[] selectorColors = {Color.MAGENTA, Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.BLUE, Color.WHITE, Color.DARK_GRAY, Color.BLACK, DEFAULT_CANVAS_COLOR};
	public static Color selectedColor = Color.MAGENTA;
	public static boolean hasMenuInteraction = false;
	
	public static void main(String[] args) 
	{
		//Initialize Members
		frame = new JFrame("Tile Builder");
		fonts = new JButton("Print Fonts");
		clear = new JButton("Clear Canvas");
		fill = new JButton("Fill Canvas");
		confirmDesign = new JButton("Confirm Design");
		fillRow = new JCheckBox("Fill Row");
		fillCol = new JCheckBox("Fill Column");
		replaceColor = new JCheckBox("Replace");
		designPicker = new JComboBox<String>();
		
		//Customize Members
		UIManager.put("Button.select", "");
		
		frame.setLayout(null);
		frame.getContentPane().setPreferredSize(new Dimension(700, 550));
		frame.pack();
		frame.setResizable(false);
		
		fonts.setBounds(RIGHT_MENU_PADDING, 25, RIGHT_MENU_DEFAULT_WIDTH, RIGHT_MENU_DEFAULT_HEIGHT);
		fonts.setFocusPainted(false);
		fonts.setEnabled(false);
		
		clear.setBounds(RIGHT_MENU_PADDING, 75, RIGHT_MENU_DEFAULT_WIDTH, RIGHT_MENU_DEFAULT_HEIGHT);
		clear.setFocusPainted(false);
		
		fill.setBounds(RIGHT_MENU_PADDING, 125, RIGHT_MENU_DEFAULT_WIDTH, RIGHT_MENU_DEFAULT_HEIGHT);
		fill.setFocusPainted(false);
		
		confirmDesign.setBounds(RIGHT_MENU_PADDING, 225, RIGHT_MENU_DEFAULT_WIDTH, RIGHT_MENU_DEFAULT_HEIGHT);
		confirmDesign.setFocusPainted(false);
		
		fillRow.setBounds(RIGHT_MENU_PADDING, 150, RIGHT_MENU_SMALL_WIDTH, RIGHT_MENU_DEFAULT_HEIGHT);
		fillRow.setFocusPainted(false);
		
		fillCol.setBounds(RIGHT_MENU_PADDING + RIGHT_MENU_SMALL_WIDTH, 150, RIGHT_MENU_DEFAULT_WIDTH, RIGHT_MENU_DEFAULT_HEIGHT);
		fillCol.setFocusPainted(false);
		fillCol.setMargin(new Insets(0, 0, 0, 0));
		
		replaceColor.setBounds(RIGHT_MENU_PADDING, 175, RIGHT_MENU_SMALL_WIDTH, RIGHT_MENU_DEFAULT_HEIGHT);
		replaceColor.setFocusPainted(false);
		replaceColor.setMargin(new Insets(0, 0, 0, 0));
		
		designPicker.setBounds(RIGHT_MENU_PADDING, 250, RIGHT_MENU_DEFAULT_WIDTH, RIGHT_MENU_DEFAULT_HEIGHT);
		designPicker.addItem("Choose a design...");
		designPicker.addItem("Checkers");
		designPicker.addItem("Cross");
		designPicker.addItem("Box");
		designPicker.addItem("Scramble");
		designPicker.addItem("Diagonal");
		designPicker.addItem("Dots");
		
		//Add Members to Frame
		frame.add(fonts);
		frame.add(clear);
		frame.add(fill);
		frame.add(fillRow);
		frame.add(fillCol);
		frame.add(replaceColor);
		frame.add(designPicker);
		frame.add(confirmDesign);
		
		//Get All Menu CheckBox Items
		menuCheckBoxes.add(fillRow);
		menuCheckBoxes.add(fillCol);
		menuCheckBoxes.add(replaceColor);
		
		//Create Drawing Grid
		int ID = 0;
		for(int col = 0; col < CANVAS_HEIGHT; col++)
		{
			for(int row = 0; row < CANVAS_WIDTH; row++)
			{
				draw = new JButton();
				
				draw.setFocusPainted(false);
				draw.setBounds(GRID_SIZE * row, GRID_SIZE * col, GRID_SIZE, GRID_SIZE);
				draw.setBorder(DEFAULT_CANVAS_BORDER);
				draw.setBackground(DEFAULT_CANVAS_COLOR);
				draw.setName((ID) + "");
				//draw.setText(getID(draw) + "");
				
				canvasButtons.add(draw);
				frame.add(draw);
				ID++;
			}
		}
		
		//Create Selection Buttons
		for(int i = 0; i < CANVAS_WIDTH; i++)
		{
			selector = new JButton();
			
			selector.setBounds(i * GRID_SIZE, 500, GRID_SIZE, GRID_SIZE);
			selector.setBorder(DEFAULT_SELECT_BORDER);
			selector.setBackground(selectorColors[i]);
			selector.setText(i + "");
			selector.setFont(new Font("Impact", Font.PLAIN, 12));
			selector.setForeground(new Color(255 - selector.getBackground().getRed(), 255 - selector.getBackground().getGreen(), 255 - selector.getBackground().getBlue()));
			selector.setFocusPainted(false);
			
			if(i == 0)
				selector.setBorder(SELECT_SELECT_BORDER);
			
			selectionButtons.add(selector);
			frame.add(selector);
		}
		
		//Get Input From Selections
		for(JButton j : selectionButtons)
			j.addActionListener(new ActionListener()
	        {
	            @Override
	            public void actionPerformed(ActionEvent e)
	            {
	            	for(JButton clear : selectionButtons)
	            	{
	            		clear.setBorder(DEFAULT_SELECT_BORDER);
	            	}
	            	
	                JButton source = (JButton)e.getSource();
	                source.setBorder(SELECT_SELECT_BORDER);
	                selectedColor = source.getBackground();
	            }
	        });		
		
		//Place Color
		for(JButton j : canvasButtons)
			j.addActionListener(new ActionListener()
	        {
	            @Override
	            public void actionPerformed(ActionEvent e)
	            {
	            	JButton source = (JButton)e.getSource();
	            	
	            	for(JCheckBox c : menuCheckBoxes)
	            	{
	            		if(c.isSelected())
	            			hasMenuInteraction = true;
	            		else
	            			hasMenuInteraction = false;
	            	}
	            	
	            	if(fillRow.isSelected())
	            		fillRow(source);

	            	if(fillCol.isSelected()) 
	            		fillColumn(source);
	            	
	            	if(replaceColor.isSelected())
	            		replaceColor(source);
	            	
	            	if(!hasMenuInteraction) //Default Draw
		                source.setBackground(selectedColor);
	            }
	        });
		
		//Print All Fonts
		fonts.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                printFonts();
            }
        });
		
		//Clear Canvas
		clear.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                clearCanvas();
            }
        });
		
		//Fill Canvas
		fill.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                fillCanvas();
            }
        });
		
		//Display Designs
		confirmDesign.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                int chosen = designPicker.getSelectedIndex();
                
                switch(chosen)
                {
                case 1:
                	
                	boolean evenRow = true;
                	
                	for(JButton checker : canvasButtons)
            		{
            				if(getID(checker) % 10 == 0)
            					evenRow = !evenRow;
            				
            				if(evenRow)
            				{
            					if(getID(checker) % 2 == 0)
            						checker.setBackground(selectedColor);
            				}
            				else
            				{
            					if(getID(checker) % 2 != 0)
            						checker.setBackground(selectedColor);
            				}
            		}
                	
                	break;
                	
                case 2:
                	
                	int point1 = -11;
                	int point2 = 0;
                	
                	for(JButton cross : canvasButtons)
            		{                		
                		if(getID(cross) % 10 == 0)
            			{
        					point1 += 11;
        					point2 += 9;
            			}
                		
                		if(getID(cross) == point1 || getID(cross) == point2)
            				cross.setBackground(selectedColor);
            		}
                	
                	break;
                	
                case 3:
                	
                	int rightEdgeCheck = 9;
                	
                	for(JButton box : canvasButtons)
            		{                		
                		if(getID(box) < 9 || getID(box) % 10 == 0 || getID(box) > 90)
                		{
                			box.setBackground(selectedColor);
                		}
                		else if(getID(box) == rightEdgeCheck)
                		{
                			box.setBackground(selectedColor);
                			rightEdgeCheck += 10;
                		}
            		}
                	
                	break;
                	
                case 4:
                	
                	Random rnd = new Random(); 
                	int newColor;
                	
                	for(JButton scramble : canvasButtons)
            		{          
                		newColor = rnd.nextInt(selectorColors.length);
                		scramble.setBackground(selectorColors[newColor]);
            		}
                	
                	break;
                	
                case 5:
                	
                	for(JButton diagonal : canvasButtons)
            		{          
                		if(getID(diagonal) % 3 == 0 || getID(diagonal) % 9 == 0)
                			diagonal.setBackground(selectedColor);
            		}
                	
                	break;
                	
                case 6:
                	
                	for(JButton dots : canvasButtons)
            		{          
                		if(getID(dots) % 4 == 0 || getID(dots) % 8 == 0)
                			dots.setBackground(selectedColor);
            		}
                	
                	break;
                }
                
            }
        });

		//Set the Frame Location
        frame.setLocationRelativeTo(null);
		
		//Show Frame
		frame.setVisible(true);
		
		//Close Frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static void printFonts()
	{
		String fonts[] = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();

	    for ( int i = 0; i < fonts.length; i++ )
	    {
	      System.out.println(fonts[i]);
	    }
	}
	
	public static void clearCanvas()
	{
		for(JButton j : canvasButtons)
		{
			j.setBackground(DEFAULT_CANVAS_COLOR);
		}
	}
	
	public static void fillCanvas()
	{
		for(JButton j : canvasButtons)
		{
			j.setBackground(selectedColor);
		}
	}
	
	public static void fillRow(JButton j)
	{
		int leftDistance = -1;
		int rightDistance = -1;
		int startPos;
		int endPos;
		
		for(int i = getID(j); i > getID(j) - CANVAS_WIDTH; i--)
		{
			leftDistance++;
			if(i % 10 == 0)
				break;
		}
		
		rightDistance = CANVAS_WIDTH - leftDistance - 1;
		
		startPos = getID(j) - leftDistance;
		endPos = getID(j) + rightDistance;
		
		for(int i = startPos; i <= endPos; i++)
		{
			canvasButtons.get(i).setBackground(selectedColor);
		}
	}
	
	public static void fillColumn(JButton j)
	{
		int topDistance = -1;
		int bottomDistance = -1;
		int startPos;
		int endPos;
		
		for(int i = getID(j); i > getID(j) - (CANVAS_WIDTH * CANVAS_HEIGHT); i-=10)
		{
			topDistance++;
			if(i <= 10)
				break;
		}
		
		bottomDistance = CANVAS_HEIGHT - topDistance - 1;
		
		startPos = getID(j) - topDistance * CANVAS_HEIGHT;
		endPos = getID(j) + bottomDistance * CANVAS_HEIGHT;
		
		for(int i = startPos; i <= endPos; i+=10)
		{
			canvasButtons.get(i).setBackground(selectedColor);
		}
	}
	
	public static void replaceColor(JButton j)
	{
		Color toReplace = j.getBackground();
		
		for(JButton change : canvasButtons)
		{
			if(change.getBackground().equals(toReplace))
				change.setBackground(selectedColor);
		}
		
	}
	
	public static int getID(JButton j)
	{
		return Integer.parseInt(j.getName());
	}
}