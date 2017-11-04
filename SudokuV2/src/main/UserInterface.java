package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.GroupLayout.Group;

import util.Difficulty;

@SuppressWarnings("serial")
public class UserInterface extends JFrame {
	
    private Container mainPane = getContentPane();
    private JPanel menuPane = new JPanel();
    private JPanel diffPane = new JPanel();
    private JPanel titlePane = new JPanel();
    private JPanel gridPane = new JPanel();
    private JPanel gamePane = new JPanel();
    private JPanel inputPane = new JPanel();
    private JPanel footPane = new JPanel();
    
    
	private JLabel lblSudoku = new JLabel("Sudoku");
	private JLabel lblWin = new JLabel("You Won!");
	
	private List<JButton> gridBtns = new ArrayList<>(81);
	private List<JButton> menuBtns = new ArrayList<>(10);
	private List<JButton> numberBtns = new ArrayList<>(10);
	
	private JButton btnNew = new JButton("New Game");
	private JButton btnQuit = new JButton("Quit");
	private JButton btnQuit2 = new JButton("Quit Game");
	private JButton btnEasy = new JButton("Easy");
	private JButton btnMedium = new JButton("Medium");
	private JButton btnHard = new JButton("Hard");
	private JButton btnVeryHard = new JButton("Very Hard");
	private JButton btnReturn = new JButton("Return");
	
	private JButton btn1 = new JButton("1");
	private JButton btn2 = new JButton("2");
	private JButton btn3 = new JButton("3");
	private JButton btn4 = new JButton("4");
	private JButton btn5 = new JButton("5");
	private JButton btn6 = new JButton("6");
	private JButton btn7 = new JButton("7");
	private JButton btn8 = new JButton("8");
	private JButton btn9 = new JButton("9");
	
	private JButton btn0 = new JButton("D");
	
	private Dimension sizeWdw = new Dimension(500,500);
	private Dimension sizeGap = new Dimension(5,5);
	
	private Game g;
	private JButton selectedBtn = new JButton();
	
	private Font fontBtn = new Font(Font.SERIF,Font.PLAIN,20);
	private Font fontNumber = new Font(Font.SERIF,Font.PLAIN,25);
	private Font fontDefaultNumber = new Font(Font.SERIF,Font.BOLD,25);
	
	{
		lblSudoku.setPreferredSize(new Dimension(300,70));
		lblSudoku.setFont(new Font(Font.SERIF,Font.BOLD,50));
		lblSudoku.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		lblWin.setPreferredSize(new Dimension(200,40));
		lblWin.setFont(new Font(Font.SERIF,Font.BOLD,30));
		lblWin.setForeground(Color.RED);
		lblWin.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		menuBtns.add(btnNew);menuBtns.add(btnQuit);menuBtns.add(btnEasy);menuBtns.add(btnMedium);
		menuBtns.add(btnHard);menuBtns.add(btnVeryHard);menuBtns.add(btnReturn);
		menuBtns.add(btnQuit2);
		
		numberBtns.add(btn1);numberBtns.add(btn2);numberBtns.add(btn3);numberBtns.add(btn4);
		numberBtns.add(btn5);numberBtns.add(btn6);numberBtns.add(btn7);numberBtns.add(btn8);
		numberBtns.add(btn9);numberBtns.add(btn0);
		
		for (JButton button: menuBtns) {
			button.setAlignmentX(Component.CENTER_ALIGNMENT);
			button.setMnemonic(button.getText().charAt(0));
			button.setFont(fontBtn);
		}
		
		btn0.setName("0");
		
		for (JButton button: numberBtns) {
			button.setAlignmentX(Component.CENTER_ALIGNMENT);
			button.setMnemonic(button.getText().charAt(0));
			button.setFont(fontBtn);
			if (button.getName() != "0") {
				button.setName(button.getText());
			}
			inputPane.add(button);
			button.addActionListener(e -> {
				JButton b = (JButton)e.getSource();
				if (selectedBtn.isEnabled() == false) return;
				selectedBtn.setText((button.getName() != "0")?b.getText():"");
				g.setField(Integer.parseInt(selectedBtn.getName()),Integer.parseInt(b.getName()));
				checkWin();
			});
			String name = button.getName();
			if (name == "0") name = "D";
			button.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(name),name);
	        button.getActionMap().put(name,
	        	new AbstractAction() {public void actionPerformed(ActionEvent e) {
					JButton b = (JButton)e.getSource();
					if (selectedBtn.isEnabled() == false) return;
					selectedBtn.setText((button.getName() != "0")?b.getText():"");
					g.setField(Integer.parseInt(selectedBtn.getName()),Integer.parseInt(b.getName()));
					checkWin();}});
			
		}
		
		mainPane.setLayout(new BorderLayout());
		menuPane.setLayout(new BoxLayout(menuPane, BoxLayout.PAGE_AXIS));
		diffPane.setLayout(new BoxLayout(diffPane, BoxLayout.PAGE_AXIS));
		titlePane.setLayout(new BoxLayout(titlePane, BoxLayout.PAGE_AXIS));
		inputPane.setLayout(new BoxLayout(inputPane, BoxLayout.LINE_AXIS));
		gamePane.setLayout(new BoxLayout(gamePane, BoxLayout.PAGE_AXIS));
		footPane.setLayout(new BoxLayout(footPane, BoxLayout.PAGE_AXIS));
		
		GroupLayout gridLayout = new GroupLayout(gridPane);
		gridPane.setLayout(gridLayout);
		
        Group verticalGroup = gridLayout.createSequentialGroup();
        Group horizontalGroup= gridLayout.createParallelGroup(GroupLayout.Alignment.CENTER);
        Group tmp;
        Group tmp2;
		
		for (int i = 0; i < 9; i++) {
	        tmp = gridLayout.createSequentialGroup();
	        tmp2 = gridLayout.createParallelGroup(GroupLayout.Alignment.CENTER);
			for (int j = 0; j < 9; j++) {
				JButton b = new JButton();
				gridBtns.add(b);
				b.setSize(32, 32);
				b.setOpaque(true);
				b.setBackground(Color.WHITE);
				b.setFont(fontNumber);
				b.setMargin(new Insets(0, 0, 0, 0));
				b.setName(i*9 + j +"");
				b.addActionListener(e -> {
					selectedBtn.setBackground(Color.WHITE);
					selectedBtn = (JButton)e.getSource();
					selectedBtn.setBackground(Color.CYAN);
				});
				tmp.addComponent(b, 32, 32, 32);
				tmp2.addComponent(b, 32, 32, 32);
				if (j == 2 || j == 5) {
					tmp.addGap(3);tmp2.addGap(3);
				}
			}
			verticalGroup.addGroup(tmp2);
			horizontalGroup.addGroup(tmp);
			if (i == 2 || i == 5) {
				verticalGroup.addGap(3);horizontalGroup.addGap(3);
			}
		}
		
		gridLayout.setHorizontalGroup(horizontalGroup);
		gridLayout.setVerticalGroup(verticalGroup);
		gridPane.setBackground(Color.BLACK);
		
		gridPane.setPreferredSize(new Dimension(300,300));
		titlePane.add(lblSudoku);
		
		menuPane.add(btnNew);menuPane.add(Box.createRigidArea(sizeGap));
		menuPane.add(btnQuit);
		
		diffPane.add(btnEasy);diffPane.add(Box.createRigidArea(sizeGap));
		diffPane.add(btnMedium);diffPane.add(Box.createRigidArea(sizeGap));
		diffPane.add(btnHard);diffPane.add(Box.createRigidArea(sizeGap));
		diffPane.add(btnVeryHard);diffPane.add(Box.createRigidArea(sizeGap));
		diffPane.add(btnReturn);
		
		gamePane.add(gridPane);gamePane.add(Box.createRigidArea(sizeGap));
		gamePane.add(inputPane);gamePane.add(Box.createRigidArea(sizeGap));
		gamePane.add(lblWin);
		
		footPane.add(btnQuit2);footPane.add(Box.createRigidArea(sizeGap));
		
		mainPane.add(titlePane, BorderLayout.NORTH);
		
		btnNew.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("N"),"do");
        btnNew.getActionMap().put("do",
        	new AbstractAction() {public void actionPerformed(ActionEvent e) {
            	mainPane.remove(menuPane);displayDiffMenu();}});
		btnQuit.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("Q"),"do");
        btnQuit.getActionMap().put("do",
        	new AbstractAction() {public void actionPerformed(ActionEvent e) {
            	System.exit(0);}});
		btnQuit2.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("Q"),"do");
        btnQuit2.getActionMap().put("do",
        	new AbstractAction() {public void actionPerformed(ActionEvent e) {
        		lblWin.setVisible(false);mainPane.remove(gamePane);mainPane.remove(footPane);displayMainMenu();}});
		btnReturn.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("R"),"do");
        btnReturn.getActionMap().put("do",
        	new AbstractAction() {public void actionPerformed(ActionEvent e) {
        		mainPane.remove(diffPane);displayMainMenu();}});
		btnEasy.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("E"),"do");
        btnEasy.getActionMap().put("do",
        	new AbstractAction() {public void actionPerformed(ActionEvent e) {
        		play(Difficulty.EASY);}});
		btnMedium.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("M"),"do");
        btnMedium.getActionMap().put("do",
        	new AbstractAction() {public void actionPerformed(ActionEvent e) {
        		play(Difficulty.MEDIUM);}});
		btnHard.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("H"),"do");
        btnHard.getActionMap().put("do",
        	new AbstractAction() {public void actionPerformed(ActionEvent e) {
        		play(Difficulty.HARD);}});
		btnVeryHard.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("V"),"do");
        btnVeryHard.getActionMap().put("do",
        	new AbstractAction() {public void actionPerformed(ActionEvent e) {
        		play(Difficulty.VERY_HARD);}});
        
		gridPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("RIGHT"),"right");
        gridPane.getActionMap().put("right",
        	new AbstractAction() {public void actionPerformed(ActionEvent e) {
        		moveSelected(1);}});
		gridPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("LEFT"),"left");
        gridPane.getActionMap().put("left",
        	new AbstractAction() {public void actionPerformed(ActionEvent e) {
        		moveSelected(-1);}});
		gridPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("UP"),"up");
        gridPane.getActionMap().put("up",
        	new AbstractAction() {public void actionPerformed(ActionEvent e) {
        		moveSelected(-9);}});
		gridPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("DOWN"),"down");
        gridPane.getActionMap().put("down",
        	new AbstractAction() {public void actionPerformed(ActionEvent e) {
        		moveSelected(9);}});
        
        
        
		
        btnNew.addActionListener((ActionEvent event) -> {
        	mainPane.remove(menuPane);
        	displayDiffMenu();
        });
        btnReturn.addActionListener((ActionEvent event) -> {
        	mainPane.remove(diffPane);
        	displayMainMenu();
        });
        btnQuit.addActionListener((ActionEvent event) -> System.exit(0));
        btnQuit2.addActionListener((ActionEvent event) -> {
        	lblWin.setVisible(false);
            mainPane.remove(gamePane);
            mainPane.remove(footPane);
            displayMainMenu();});
        btnEasy.addActionListener((ActionEvent event) -> play(Difficulty.EASY));
        btnMedium.addActionListener((ActionEvent event) -> play(Difficulty.MEDIUM));
        btnHard.addActionListener((ActionEvent event) -> play(Difficulty.HARD));
        btnVeryHard.addActionListener((ActionEvent event) -> play(Difficulty.VERY_HARD));
		
		titlePane.setVisible(true);
		menuPane.setVisible(true);
		diffPane.setVisible(true);
		gridPane.setVisible(true);
		inputPane.setVisible(true);
		footPane.setVisible(true);
		lblWin.setVisible(false);
	}
	
	public UserInterface() {
        setTitle("Sudoku");
        setMinimumSize(sizeWdw);
        setMaximumSize(sizeWdw);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	
	private void displayMainMenu() {
		mainPane.add(menuPane, BorderLayout.CENTER);
		mainPane.revalidate();
		mainPane.repaint();
	}
	
	private void displayDiffMenu() {
		mainPane.add(diffPane, BorderLayout.CENTER);
		mainPane.revalidate();
		mainPane.repaint();		
	}
	
	private void displayGrid() {
		mainPane.add(gamePane, BorderLayout.CENTER);
		mainPane.add(footPane, BorderLayout.SOUTH);
		mainPane.revalidate();
		mainPane.repaint();		
	}
	
	private void updateGrid() {
		int[] grid = g.getGrid();
		JButton button;
		for (int i = 0; i < 81; i++) {
			button = gridBtns.get(i);
			if (grid[i] != 0) {
				button.setText(grid[i]+"");
				button.setEnabled(false);
				button.setFont(fontDefaultNumber);
			}else {
				button.setText("");
				button.setEnabled(true);
				button.setFont(fontNumber);
			}
		}
	}
		
	private void play(Difficulty diff) {
		g = new Game(diff);
		updateGrid();
		mainPane.remove(diffPane);
		displayGrid();

	}
	
	private void checkWin() {
		if (g.isOver()) lblWin.setVisible(true);;
	}
	
	private void moveSelected(int cells) {
		
		int index = gridBtns.indexOf(selectedBtn);
		index = (index + cells)%81;
		if (index < 0) index += 81;
		selectedBtn.setBackground(Color.WHITE);
		selectedBtn = gridBtns.get(index);
		selectedBtn.setBackground(selectedBtn.isEnabled()?Color.CYAN:Color.RED);
	}
	
	public static void main(String[] args) {
		
		UserInterface ui = new UserInterface();
		ui.setIconImage(new ImageIcon("images/sudoku64.png").getImage());
		ui.pack();
		ui.setVisible(true);
		ui.displayMainMenu();
		
	}

}
