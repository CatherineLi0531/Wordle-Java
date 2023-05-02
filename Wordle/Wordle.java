// Catherine Li

//Word list does include some "inappropriate" languages <3

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;


public class Wordle  extends JFrame implements ActionListener, ItemListener, KeyListener {

  private JLabel title;
  private static JLabel[][] setup;
  private static JPanel setupPanel;
  private static int rand;
  private static JPanel panel;
  private JTextField text;
  private JButton enter;
  private JPanel entryPanel;
  private static String word;
  private static int row;
  private static boolean isWord;
  private static Scanner sc;
  private static Scanner fin;
  private JComboBox<String> letterNum;
  private static String file;
  private static int num;
  private int count;
  private JButton start2;
  private String answer;
  private JButton reset;
  private JComboBox<String> letterList;

  // i want to jump off a cliff, why did i use so many variables owell.

  Wordle() throws FileNotFoundException {
    super("Wordle");

    file = "5-letter.txt";

    panel = new JPanel();
    panel.setLayout(new BorderLayout(10, 10));

    {entryPanel = new JPanel();
    entryPanel.setPreferredSize(new Dimension(400, 100));

    text = new JTextField();
    text.setPreferredSize(new Dimension(300, 100));

    enter = new JButton("ENTER");
    enter.setFont(new Font("sans-serif", 1, 14));
    enter.setPreferredSize(new Dimension(100, 100));


    entryPanel.add(text);
    entryPanel.add(enter);

    panel.add(entryPanel, BorderLayout.PAGE_END);
    }

    letterList = new JComboBox<String>();

    String[] letters = { "4", "5", "6", "7", "8"};

    letterNum = new JComboBox<String>(letters);
    JPanel numLetters = new JPanel();
    numLetters.add(letterNum);
    numLetters.setSize(300, 70);

    start2 = new JButton("START GAME");
    JPanel startPanel = new JPanel();
    startPanel.add(start2);
    startPanel.setSize(300, 70);

    reset = new JButton("NEW GAME");
    JPanel resetPanel = new JPanel();
    resetPanel.add(reset);
    resetPanel.setSize(300, 70);

		title = new JLabel("  Wordle");
    title.setAlignmentX(CENTER_ALIGNMENT);
    title.setFont(new Font("sans-serif", 1, 50));
    JPanel start = new JPanel();
    start.setLayout(new BoxLayout(start, BoxLayout.Y_AXIS));
    start.add(title);
    start.add(numLetters);
    start.add(startPanel);
    start.add(resetPanel);
		panel.add(start, BorderLayout.PAGE_START);

    JPanel blank = new JPanel();
    blank.setPreferredSize(new Dimension(200, 1000));
    panel.add(blank, BorderLayout.LINE_START);
    JPanel blank2 = new JPanel();
    blank2.setPreferredSize(new Dimension(200, 1000));
    panel.add(blank2, BorderLayout.LINE_END);

    letterNum.setSelectedItem("5");
    num = 5;
    setupPanel = new JPanel();
    setupPanel.setVisible(false);
    generate();
    letterNum.addItemListener(this);
    enter.addActionListener(this);
    start2.addActionListener(this);
    reset.addActionListener(this);
    panel.addKeyListener(this);
    text.addKeyListener(this);
    text.setEnabled(false);
    text.setFocusTraversalKeysEnabled(false);
    reset.setEnabled(false);

    setContentPane(panel);
    row = 0;
    isWord = false;
    count = 0;

    sc = new Scanner(new File(file));
    enter.setEnabled(false);
  }

	public static void main(String[] args) throws FileNotFoundException, IOException{
    JFrame frame = new Wordle();
    frame.setPreferredSize(new Dimension(1000,1000));
		frame.pack();
		frame.setVisible(true);
	}

  public void itemStateChanged(ItemEvent e){
    if(e.getStateChange() == ItemEvent.SELECTED) {
      Object source = e.getSource();
      if (source instanceof JComboBox) {
        letterList = (JComboBox<String>)source;
        Object selectedItem = letterList.getSelectedItem();
        if (selectedItem.equals("4")) {
          file = "4-letter.txt";
          num = 4;
        }
        if (selectedItem.equals("5")) {
          file = "5-letter.txt";
          num = 5;
        }
        if (selectedItem.equals("6")) {
          file = "6-letter.txt";
          num = 6;
        }
        if (selectedItem.equals("7")) {
          file = "7-letter.txt";
          num = 7;
        }
        if (selectedItem.equals("8")) {
          file = "8-letter.txt";
          num = 8;
        }
      }
      else {
        file = "5-letter.txt";
        num = 5;
      }
    }
  }

  public JPanel generate() {
    if (num != 0) {
      setup = new JLabel[6][num];
      setupPanel = new JPanel(new GridLayout(6, num, 10, 10));
      setupPanel.setPreferredSize(new Dimension(200,200));

      for (int r = 0; r < 6; r++) {
        for (int c = 0; c < num; c ++) {
          setup[r][c] = new JLabel();
          setup[r][c].setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
          setup[r][c].setPreferredSize(new Dimension(96,96));
          setupPanel.add(setup[r][c]);
        }
      }
    }
    return setupPanel;
  }

  public static String theWord() {
    if (file.equals("") != true) {
      int lines;
      if (num == 8) {
        lines = 51627;
        try {
          fin = new Scanner(new File("8-letter.txt"));
        } catch (FileNotFoundException e1) {
          // TODO Auto-generated catch block
          e1.printStackTrace();
        }
      }
      else if (num == 5) {
        lines = 12947;
        try {
          fin = new Scanner(new File("5-letter.txt"));
        } catch (FileNotFoundException e1) {
          // TODO Auto-generated catch block
          e1.printStackTrace();
        }
      }
      else if (num == 6) {
        lines = 29874;
        try {
          fin = new Scanner(new File("6-letter.txt"));
        } catch (FileNotFoundException e1) {
          // TODO Auto-generated catch block
          e1.printStackTrace();
        }
      }
      else if (num == 7) {
        lines = 41998;
        try {
          fin = new Scanner(new File("7-letter.txt"));
        } catch (FileNotFoundException e1) {
          // TODO Auto-generated catch block
          e1.printStackTrace();
        }
      }
      else {
        lines = 7186;
        try {
          fin = new Scanner(new File("4-letter.txt"));
        } catch (FileNotFoundException e1) {
          // TODO Auto-generated catch block
          e1.printStackTrace();
        }
      }
      rand = (int) Math.floor(Math.random()*(lines-1+1)+1);

      for (int i = 0; i < rand-1; i ++) {
        fin.next();
      }

      word = fin.next();
    }
    System.out.println(word);
    return word; 
  }

  public void keyPressed(KeyEvent e) {
    if (e.getKeyCode()==KeyEvent.VK_ENTER){
      if (count == 0) {
        theWord();
      }
      count++;
      answer = text.getText();
      text.setText("");
      if (answer.length() == num) {
        try {
          sc = new Scanner(new File(file));
        } catch (FileNotFoundException e1) {
          e1.printStackTrace();
        }
        while (sc.hasNextLine() && isWord == false) {
          if (answer.equals(sc.nextLine())) {
            isWord = true;
          }
        }
        if (isWord == true) {
          for (int j = 0; j < num; j++) {
            if (word.contains(answer.substring(j,j + 1))) {
              if (answer.substring(j,j + 1).equals(word.substring(j,j + 1))) {// green
                setup[row][j].setBackground(new Color(84, 204, 73));
                setup[row][j].setOpaque(true);
                setup[row][j].setText(answer.substring(j,j+1).toUpperCase());
                setup[row][j].setHorizontalAlignment(SwingConstants.CENTER);
                setup[row][j].setVerticalAlignment(SwingConstants.CENTER);                
                setup[row][j].setFont(new Font("sans-serif", 1, 24));
                setup[row][j].setForeground(Color.white);
              }
              else { //yellow
                if (word.indexOf(answer.charAt(j)) == word.lastIndexOf(answer.charAt(j)) && answer.indexOf(answer.charAt(j)) != answer.lastIndexOf(answer.charAt(j)) ) {
                  int last = answer.lastIndexOf(answer.charAt(j));

                  if (answer.charAt(last) == word.charAt(last)) {
                    setup[row][last].setBackground(new Color(84, 204, 73));
                    setup[row][last].setOpaque(true);
                    setup[row][last].setText(answer.substring(j,j+1).toUpperCase());
                    setup[row][last].setHorizontalAlignment(SwingConstants.CENTER);
                    setup[row][last].setVerticalAlignment(SwingConstants.CENTER);                
                    setup[row][last].setFont(new Font("sans-serif", 1, 24));
                    setup[row][last].setForeground(Color.white);
                  }
                  else {
                    setup[row][last].setBackground(Color.GRAY);
                    setup[row][last].setOpaque(true);
                    setup[row][last].setText(answer.substring(j,j+1).toUpperCase());
                    setup[row][last].setHorizontalAlignment(SwingConstants.CENTER);
                    setup[row][last].setVerticalAlignment(SwingConstants.CENTER);
                    setup[row][last].setFont(new Font("sans-serif", 1, 24));
                    setup[row][last].setForeground(Color.white);
                  }
                  if (j != last && setup[row][last].getBackground() == Color.GRAY) {
                    setup[row][j].setBackground(new Color(204, 182, 73));
                    setup[row][j].setOpaque(true);
                    setup[row][j].setText(answer.substring(j,j+1).toUpperCase());
                    setup[row][j].setHorizontalAlignment(SwingConstants.CENTER);
                    setup[row][j].setVerticalAlignment(SwingConstants.CENTER);
                    setup[row][j].setFont(new Font("sans-serif", 1, 24));
                    setup[row][j].setForeground(Color.white);
                  }
                  else {
                    setup[row][j].setBackground(Color.GRAY);
                    setup[row][j].setOpaque(true);
                    setup[row][j].setText(answer.substring(j,j+1).toUpperCase());
                    setup[row][j].setHorizontalAlignment(SwingConstants.CENTER);
                    setup[row][j].setVerticalAlignment(SwingConstants.CENTER);
                    setup[row][j].setFont(new Font("sans-serif", 1, 24));
                    setup[row][j].setForeground(Color.white);
                  }
                }
                else {
                  setup[row][j].setBackground(new Color(204, 182, 73));
                  setup[row][j].setOpaque(true);
                  setup[row][j].setText(answer.substring(j,j+1).toUpperCase());
                  setup[row][j].setHorizontalAlignment(SwingConstants.CENTER);
                  setup[row][j].setVerticalAlignment(SwingConstants.CENTER);
                  setup[row][j].setFont(new Font("sans-serif", 1, 24));
                  setup[row][j].setForeground(Color.white);
                }
              }
            }
            else { // not in word (gray)
              setup[row][j].setBackground(Color.GRAY);
              setup[row][j].setOpaque(true);
              setup[row][j].setText(answer.substring(j,j+1).toUpperCase());
              setup[row][j].setHorizontalAlignment(SwingConstants.CENTER);
              setup[row][j].setVerticalAlignment(SwingConstants.CENTER);
              setup[row][j].setFont(new Font("sans-serif", 1, 24));
              setup[row][j].setForeground(Color.white);
            }
          }
          row ++;
        }
        else {
          JOptionPane.showMessageDialog(null,"NOT WORD","NOT WORD",JOptionPane.INFORMATION_MESSAGE);
        }
        isWord = false;
    }
    if (answer.length() != num){
      JOptionPane.showMessageDialog(null,"WRONG LENGTH","WRONG LENGTH",JOptionPane.INFORMATION_MESSAGE);
    }
    if (row > 5 && answer != word ) {
      enter.setEnabled(false);
      JOptionPane.showMessageDialog(null,word,"Answer",JOptionPane.INFORMATION_MESSAGE);
    }
    if (answer.equals(word)) {
      enter.setEnabled(false);
      JOptionPane.showMessageDialog(null,"CONGRATS","CONGRATS",JOptionPane.INFORMATION_MESSAGE);
    }
    }
  }

  @Override
  public void keyReleased(KeyEvent arg){
  }

  @Override
  public void keyTyped(KeyEvent arg){

  }


  public void actionPerformed(ActionEvent e) {
      if (e.getSource() == start2) {
          enter.setEnabled(true);
          generate();
          panel.add(setupPanel, BorderLayout.CENTER);
          setContentPane(panel);
          setupPanel.setVisible(true);
          start2.setEnabled(false);
          letterList.setEnabled(false);
          letterNum.setEnabled(false);
          text.setText("");
          text.setEnabled(true);
          reset.setEnabled(true);
      }
      if (e.getSource() == reset) {
        reset.setEnabled(false);
        setupPanel.removeAll();
        letterNum.setSelectedItem("5");
        setupPanel.setVisible(false);
        letterList.setEnabled(true);
        letterNum.setEnabled(true);
        text.setText("");
        text.setEnabled(false);
        start2.setEnabled(true);
        enter.setEnabled(false);
        count = 0;
        row = 0;
        num = 5;
        setContentPane(panel);
      }
      if (e.getSource() == enter) {
        if (count == 0) {
          theWord();
        }
        count++;
        answer = "";
        answer = text.getText();
        text.setText("");
        if (answer.length() == num && word.length() == num) {
          try {
            sc = new Scanner(new File(file));
          } catch (FileNotFoundException e1) {
            e1.printStackTrace();
          }
          while (sc.hasNextLine() && isWord == false) {
            if (answer.equals(sc.nextLine())) {
              isWord = true;
            }
          }
          if (isWord == true) {
            for (int j = 0; j < num; j++) {
              if (word.contains(answer.substring(j,j + 1))) {
                if (answer.substring(j,j + 1).equals(word.substring(j,j + 1))) {// green
                  setup[row][j].setBackground(new Color(84, 204, 73));
                  setup[row][j].setOpaque(true);
                  setup[row][j].setText(answer.substring(j,j+1).toUpperCase());
                  setup[row][j].setHorizontalAlignment(SwingConstants.CENTER);
                  setup[row][j].setVerticalAlignment(SwingConstants.CENTER);                
                  setup[row][j].setFont(new Font("sans-serif", 1, 24));
                  setup[row][j].setForeground(Color.white);
                }
                else { //yellow
                  if (word.indexOf(answer.charAt(j)) == word.lastIndexOf(answer.charAt(j)) && answer.indexOf(answer.charAt(j)) != answer.lastIndexOf(answer.charAt(j)) ) {
                    int last = answer.lastIndexOf(answer.charAt(j));

                    if (answer.charAt(last) == word.charAt(last)) { // proably make all this stuff a method
                      setup[row][last].setBackground(new Color(84, 204, 73));
                      setup[row][last].setOpaque(true);
                      setup[row][last].setText(answer.substring(j,j+1).toUpperCase());
                      setup[row][last].setHorizontalAlignment(SwingConstants.CENTER);
                      setup[row][last].setVerticalAlignment(SwingConstants.CENTER);                
                      setup[row][last].setFont(new Font("sans-serif", 1, 24));
                      setup[row][last].setForeground(Color.white);
                    }
                    else {
                      setup[row][last].setBackground(Color.GRAY);
                      setup[row][last].setOpaque(true);
                      setup[row][last].setText(answer.substring(j,j+1).toUpperCase());
                      setup[row][last].setHorizontalAlignment(SwingConstants.CENTER);
                      setup[row][last].setVerticalAlignment(SwingConstants.CENTER);
                      setup[row][last].setFont(new Font("sans-serif", 1, 24));
                      setup[row][last].setForeground(Color.white);
                    }
                    if (j != last && setup[row][last].getBackground() == Color.GRAY) {
                      setup[row][j].setBackground(new Color(204, 182, 73));
                      setup[row][j].setOpaque(true);
                      setup[row][j].setText(answer.substring(j,j+1).toUpperCase());
                      setup[row][j].setHorizontalAlignment(SwingConstants.CENTER);
                      setup[row][j].setVerticalAlignment(SwingConstants.CENTER);
                      setup[row][j].setFont(new Font("sans-serif", 1, 24));
                      setup[row][j].setForeground(Color.white);
                    }
                    else {
                      setup[row][j].setBackground(Color.GRAY);
                      setup[row][j].setOpaque(true);
                      setup[row][j].setText(answer.substring(j,j+1).toUpperCase());
                      setup[row][j].setHorizontalAlignment(SwingConstants.CENTER);
                      setup[row][j].setVerticalAlignment(SwingConstants.CENTER);
                      setup[row][j].setFont(new Font("sans-serif", 1, 24));
                      setup[row][j].setForeground(Color.white);
                    }
                  }
                  else {
                    setup[row][j].setBackground(new Color(204, 182, 73));
                    setup[row][j].setOpaque(true);
                    setup[row][j].setText(answer.substring(j,j+1).toUpperCase());
                    setup[row][j].setHorizontalAlignment(SwingConstants.CENTER);
                    setup[row][j].setVerticalAlignment(SwingConstants.CENTER);
                    setup[row][j].setFont(new Font("sans-serif", 1, 24));
                    setup[row][j].setForeground(Color.white);
                  }
                }
              }
              else { // not in word (gray)
                setup[row][j].setBackground(Color.GRAY);
                setup[row][j].setOpaque(true);
                setup[row][j].setText(answer.substring(j,j+1).toUpperCase());
                setup[row][j].setHorizontalAlignment(SwingConstants.CENTER);
                setup[row][j].setVerticalAlignment(SwingConstants.CENTER);
                setup[row][j].setFont(new Font("sans-serif", 1, 24));
                setup[row][j].setForeground(Color.white);
              }
              if (answer.equals(word)) {
                enter.setEnabled(false);
              }
            }
            row ++;
          }
          else {
            JOptionPane.showMessageDialog(null,"NOT WORD","NOT WORD",JOptionPane.INFORMATION_MESSAGE);
          }
          isWord = false;
      }
      if (answer.length() != num){
        JOptionPane.showMessageDialog(null,"WRONG LENGTH","WRONG LENGTH",JOptionPane.INFORMATION_MESSAGE);
      }
      if (row > 5 && answer != word ) {
        enter.setEnabled(false);
        text.setEnabled(false);
        JOptionPane.showMessageDialog(null,word,"Answer",JOptionPane.INFORMATION_MESSAGE);
      }
    }
  }
}