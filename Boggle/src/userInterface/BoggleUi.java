package userInterface;

import static boggle.Boggle.boggleDice;
import static boggle.Boggle.boggleDict;
import core.Board;
import java.awt.*;
import java.awt.BorderLayout;
import static java.awt.Component.LEFT_ALIGNMENT;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.Timer;
import java.util.Random;
import javax.swing.text.StyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;

public class BoggleUi extends JFrame {

    //Initializations
    JFrame frame = new JFrame("Boggle");

    private static JPanel firstPanel;
    private static JPanel buttonPanel;
    private static JPanel nextPanel;
    private static JMenu boggleUi;
    private static JMenuBar menuBar;
    private static JMenuItem newGame;
    private static JMenuItem exit;
    private static JTextPane txtArea;
    private static JScrollPane scrollPane;
    private static JLabel time;
    public JButton taylorSwift;
    public static ArrayList<String> wordKeep = new ArrayList<>();
    public JButton spot[] = new JButton[16];
    public JButton button[] = new JButton[16];
    JLabel currWord;
    String wordString = "";
    JButton submit;
    JLabel score;
    boolean shakeCheck = false;
    boolean[] pressTrack = new boolean[16];
    boolean[] letterCount = new boolean[16];
    private static JPanel bottomPanel;
    private static int timeSecs = 30;
    int sec = timeSecs % 60;
    int min = timeSecs / 60;
    private static int scoreAmount = 20;
    Timer timer;
    TimerListener clocky = new TimerListener();
    Board diceBoard = new Board(boggleDice);
   // boggleDice
    //public static JDialog dubplicate; 

    //Constructor
    public BoggleUi(Board diceBoard, ArrayList boggleDict) {
        initComponents(diceBoard);
    }

    
    private void initComponents(Board diceBoard) {

        Dimension textLength = new Dimension(250, 230); //text area size 
        Dimension buttSize = new Dimension(350, 300); //letters dimension size
        Dimension timerSize = new Dimension(230, 100); //timer size
        Dimension shakeButt = new Dimension(240, 100); //shaker button size
        Dimension wordDim = new Dimension(450, 50);
        Dimension scoreDim = new Dimension(100, 50);
        Dimension currDim = new Dimension(275, 50); //current word size
        Dimension submitDim = new Dimension(220, 50);

        frame.setSize(650, 600);//Frame size
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Operation for closing the UI

        menuBar = new JMenuBar();
        boggleUi = new JMenu("Boggle");
        newGame = new JMenuItem("New Game");
        exit = new JMenuItem("Exit");

        currWord = new JLabel();
        score = new JLabel();

        boggleUi.add(newGame);
        boggleUi.add(exit);
        menuBar.add(boggleUi);

        firstPanel = new JPanel();
        firstPanel.setLayout(new BorderLayout());
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 4));

        nextPanel = new JPanel();
        nextPanel.setLayout(new GridBagLayout());
        GridBagConstraints gridBag = new GridBagConstraints();

        txtArea = new JTextPane();
        txtArea.setEditable(false);
        scrollPane = new JScrollPane(txtArea);

        //Setting up the timer
        time = new JLabel(min + ":" + sec);
        time.setFont(new Font("Times New Roman", 1, 36));
        time.setHorizontalAlignment(SwingConstants.CENTER);

        //Set up for the "shake" button
        JButton button[] = new JButton[16];
        taylorSwift = new JButton("Shake Dice"); //taylorSwift means shakeDice button
        taylorSwift.addActionListener(new shaker());
        taylorSwift.setPreferredSize(shakeButt);
        diceBoard.shakeDice(button, spot, buttonPanel);

        //Setting up the current word, submit, and score
        bottomPanel = new JPanel();
        bottomPanel.add(currWord);
        currWord.setPreferredSize(wordDim);
        score.setText(String.valueOf(scoreAmount));
        score.setPreferredSize(scoreDim);
        submit = new JButton("Submit Word");
        submit.addActionListener(new submit());
        currWord.setText(wordString);
        bottomPanel.add(submit);
        bottomPanel.add(score);

        //Position for each panel
        buttonPanel.setPreferredSize(buttSize);
        firstPanel.add(BorderLayout.LINE_START, buttonPanel);
        firstPanel.add(BorderLayout.PAGE_START, menuBar);
        firstPanel.add(BorderLayout.SOUTH, bottomPanel);

        //Rearranging the right side of the UI
        //Position for text area
        gridBag.gridx = 0;
        gridBag.gridy = 0;
        scrollPane.setPreferredSize(textLength);
        nextPanel.add(scrollPane, gridBag);

        //Position for timer
        gridBag.gridx = 0;
        gridBag.gridy = 1;
        nextPanel.add(time, gridBag);
        time.setPreferredSize(timerSize);
        

        //Position for shake button
        gridBag.gridx = 0;
        gridBag.gridy = 2;
        nextPanel.add(taylorSwift, gridBag);
        firstPanel.add(BorderLayout.CENTER, nextPanel);

        //Labels
        buttonPanel.setBorder(BorderFactory.createTitledBorder("Boggle Board"));
        bottomPanel.setBorder(BorderFactory.createTitledBorder("Current Word"));
        scrollPane.setBorder(BorderFactory.createTitledBorder("Enter Words Found"));
        time.setBorder(BorderFactory.createTitledBorder("Time Left"));
        currWord.setBorder(BorderFactory.createTitledBorder("Current Word"));
        score.setBorder(BorderFactory.createTitledBorder("Score"));
        currWord.setAlignmentX(LEFT_ALIGNMENT);
        currWord.setPreferredSize(currDim);
        submit.setPreferredSize(submitDim);
        frame.add(firstPanel);
        frame.setVisible(true);

        //Action Listener for exit
        exit.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });

        //Action Listenter for new game
        newGame.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {

                txtArea.setText("");
                currWord.setText("");
                taylorSwift.setEnabled(true);
                wordKeep.clear();
                scoreAmount = 20;
                score.setText(String.valueOf(scoreAmount));
                if (shakeCheck == true) {
                    timer.stop();
                }
                timeSecs = 60;
                sec = timeSecs % 60;
                min = timeSecs / 60;
                if (sec < 10) {
                    time.setText(min + ":0" + sec);
                } else {
                    time.setText(min + ":" + sec);
                }
                submit.setEnabled(true);

                for (int k = 0; k < 16; k++) {
                    spot[k].setEnabled(true);
                    pressTrack[k] = false;
                }
            }
        });

        //reenable all buttons
        spot[0].addActionListener(new press0());
        spot[1].addActionListener(new press1());
        spot[2].addActionListener(new press2());
        spot[3].addActionListener(new press3());
        spot[4].addActionListener(new press4());
        spot[5].addActionListener(new press5());
        spot[6].addActionListener(new press6());
        spot[7].addActionListener(new press7());
        spot[8].addActionListener(new press8());
        spot[9].addActionListener(new press9());
        spot[10].addActionListener(new press10());
        spot[11].addActionListener(new press11());
        spot[12].addActionListener(new press12());
        spot[13].addActionListener(new press13());
        spot[14].addActionListener(new press14());
        spot[15].addActionListener(new press15());
    }

    //Action Listeners for button 0-15
    public class press0 implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int i = 0;
            pressTrack[0] = true;
            for (i = 0; i < 16; i++) {
                spot[i].setEnabled(false);
            }
            spot[1].setEnabled(true);
            spot[4].setEnabled(true);
            spot[5].setEnabled(true);
            String holder;
            spot[0].revalidate();
            holder = spot[0].getText();
            wordString = wordString + holder;
            currWord.setText(wordString);
            letterCount[0] = true;
            for (int n = 0; n < letterCount.length; n++) {
                if (letterCount[n] == true) {
                    spot[n].setEnabled(false);
                }
            }
        }

    }

    public class press1 implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int i = 0;
            pressTrack[1] = true;
            for (i = 0; i < 16; i++) {
                spot[i].setEnabled(false);
            }
            spot[0].setEnabled(true);
            spot[2].setEnabled(true);
            spot[4].setEnabled(true);
            spot[5].setEnabled(true);
            spot[6].setEnabled(true);
            String holder;
            spot[1].revalidate();
            holder = spot[1].getText();
            wordString = wordString + holder;
            currWord.setText(wordString);
            letterCount[1] = true;
            for (int n = 0; n < letterCount.length; n++) {
                if (letterCount[n] == true) {
                    spot[n].setEnabled(false);
                }
            }
        }

    }

    public class press2 implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int i = 0;
            pressTrack[2] = true;
            for (i = 0; i < 16; i++) {
                spot[i].setEnabled(false);
            }
            spot[1].setEnabled(true);
            spot[3].setEnabled(true);
            spot[5].setEnabled(true);
            spot[6].setEnabled(true);
            spot[7].setEnabled(true);
            String holder;
            spot[2].revalidate();
            holder = spot[2].getText();
            wordString = wordString + holder;
            currWord.setText(wordString);
            letterCount[2] = true;
            for (int n = 0; n < letterCount.length; n++) {
                if (letterCount[n] == true) {
                    spot[n].setEnabled(false);
                }
            }
        }

    }

    public class press3 implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int i = 0;
            pressTrack[3] = true;
            for (i = 0; i < 16; i++) {
                spot[i].setEnabled(false);
            }
            spot[2].setEnabled(true);
            spot[6].setEnabled(true);
            spot[7].setEnabled(true);
            String holder;
            spot[3].revalidate();
            holder = spot[3].getText();
            wordString = wordString + holder;
            currWord.setText(wordString);
            letterCount[3] = true;
            for (int n = 0; n < letterCount.length; n++) {
                if (letterCount[n] == true) {
                    spot[n].setEnabled(false);
                }
            }
        }

    }

    public class press4 implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int i = 0;
            pressTrack[4] = true;
            for (i = 0; i < 16; i++) {
                spot[i].setEnabled(false);
            }
            spot[0].setEnabled(true);
            spot[1].setEnabled(true);
            spot[5].setEnabled(true);
            spot[8].setEnabled(true);
            spot[9].setEnabled(true);
            String holder;
            spot[4].revalidate();
            holder = spot[4].getText();
            wordString = wordString + holder;
            currWord.setText(wordString);
            letterCount[4] = true;
            for (int n = 0; n < letterCount.length; n++) {
                if (letterCount[n] == true) {
                    spot[n].setEnabled(false);
                }
            }
        }

    }

    public class press5 implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int i = 0;
            pressTrack[5] = true;
            for (i = 0; i < 16; i++) {
                spot[i].setEnabled(false);
            }
            spot[0].setEnabled(true);
            spot[1].setEnabled(true);
            spot[2].setEnabled(true);
            spot[4].setEnabled(true);
            spot[6].setEnabled(true);
            spot[8].setEnabled(true);
            spot[9].setEnabled(true);
            spot[10].setEnabled(true);
            String holder;
            spot[5].revalidate();
            holder = spot[5].getText();
            wordString = wordString + holder;
            currWord.setText(wordString);
            letterCount[5] = true;
            for (int n = 0; n < letterCount.length; n++) {
                if (letterCount[n] == true) {
                    spot[n].setEnabled(false);
                }
            }
        }

    }

    public class press6 implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int i = 0;
            pressTrack[6] = true;
            for (i = 0; i < 16; i++) {
                spot[i].setEnabled(false);
            }
            spot[1].setEnabled(true);
            spot[2].setEnabled(true);
            spot[3].setEnabled(true);
            spot[5].setEnabled(true);
            spot[7].setEnabled(true);
            spot[9].setEnabled(true);
            spot[10].setEnabled(true);
            spot[11].setEnabled(true);
            String holder;
            spot[6].revalidate();
            holder = spot[6].getText();
            wordString = wordString + holder;
            currWord.setText(wordString);
            letterCount[6] = true;
            for (int n = 0; n < letterCount.length; n++) {
                if (letterCount[n] == true) {
                    spot[n].setEnabled(false);
                }
            }
        }

    }

    public class press7 implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int i = 0;
            pressTrack[7] = true;
            for (i = 0; i < 16; i++) {
                spot[i].setEnabled(false);
            }
            spot[2].setEnabled(true);
            spot[3].setEnabled(true);
            spot[6].setEnabled(true);
            spot[10].setEnabled(true);
            spot[11].setEnabled(true);
            String holder;
            spot[7].revalidate();
            holder = spot[7].getText();
            wordString = wordString + holder;
            currWord.setText(wordString);
            letterCount[7] = true;
            for (int n = 0; n < letterCount.length; n++) {
                if (letterCount[n] == true) {
                    spot[n].setEnabled(false);
                }
            }
        }

    }

    public class press8 implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int i = 0;
            pressTrack[8] = true;
            for (i = 0; i < 16; i++) {
                spot[i].setEnabled(false);
            }
            spot[4].setEnabled(true);
            spot[5].setEnabled(true);
            spot[9].setEnabled(true);
            spot[12].setEnabled(true);
            spot[13].setEnabled(true);
            String holder;
            spot[8].revalidate();
            holder = spot[8].getText();
            wordString = wordString + holder;
            currWord.setText(wordString);
            letterCount[8] = true;
            for (int n = 0; n < letterCount.length; n++) {
                if (letterCount[n] == true) {
                    spot[n].setEnabled(false);
                }
            }
        }

    }

    public class press9 implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int i = 0;
            pressTrack[9] = true;
            for (i = 0; i < 16; i++) {
                spot[i].setEnabled(false);
            }
            spot[4].setEnabled(true);
            spot[5].setEnabled(true);
            spot[6].setEnabled(true);
            spot[8].setEnabled(true);
            spot[10].setEnabled(true);
            spot[12].setEnabled(true);
            spot[13].setEnabled(true);
            spot[14].setEnabled(true);
            String holder;
            spot[9].revalidate();
            holder = spot[9].getText();
            wordString = wordString + holder;
            currWord.setText(wordString);
            letterCount[9] = true;
            for (int n = 0; n < letterCount.length; n++) {
                if (letterCount[n] == true) {
                    spot[n].setEnabled(false);
                }
            }
        }

    }

    public class press10 implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int i = 0;
            pressTrack[10] = true;
            for (i = 0; i < 16; i++) {
                spot[i].setEnabled(false);
            }
            spot[5].setEnabled(true);
            spot[6].setEnabled(true);
            spot[7].setEnabled(true);
            spot[9].setEnabled(true);
            spot[11].setEnabled(true);
            spot[13].setEnabled(true);
            spot[14].setEnabled(true);
            spot[15].setEnabled(true);
            String holder;
            spot[10].revalidate();
            holder = spot[10].getText();
            wordString = wordString + holder;
            currWord.setText(wordString);
            letterCount[10] = true;
            for (int n = 0; n < letterCount.length; n++) {
                if (letterCount[n] == true) {
                    spot[n].setEnabled(false);
                }
            }
        }

    }

    public class press11 implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int i = 0;
            pressTrack[11] = true;
            for (i = 0; i < 16; i++) {
                spot[i].setEnabled(false);
            }
            spot[6].setEnabled(true);
            spot[7].setEnabled(true);
            spot[10].setEnabled(true);
            spot[14].setEnabled(true);
            spot[15].setEnabled(true);
            String holder;
            spot[11].revalidate();
            holder = spot[11].getText();
            wordString = wordString + holder;
            currWord.setText(wordString);
            letterCount[11] = true;
            for (int n = 0; n < letterCount.length; n++) {
                if (letterCount[n] == true) {
                    spot[n].setEnabled(false);
                }
            }
        }

    }

    public class press12 implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int i = 0;
            pressTrack[12] = true;
            for (i = 0; i < 16; i++) {
                spot[i].setEnabled(false);
            }
            spot[8].setEnabled(true);
            spot[9].setEnabled(true);
            spot[13].setEnabled(true);
            String holder;
            spot[12].revalidate();
            holder = spot[12].getText();
            wordString = wordString + holder;
            currWord.setText(wordString);
            letterCount[12] = true;
            for (int n = 0; n < letterCount.length; n++) {
                if (letterCount[n] == true) {
                    spot[n].setEnabled(false);
                }
            }
        }

    }

    public class press13 implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int i = 0;
            pressTrack[13] = true;
            for (i = 0; i < 16; i++) {
                spot[i].setEnabled(false);
            }

            spot[8].setEnabled(true);
            spot[9].setEnabled(true);
            spot[10].setEnabled(true);
            spot[12].setEnabled(true);
            spot[14].setEnabled(true);
            String holder;
            spot[13].revalidate();
            holder = spot[13].getText();
            wordString = wordString + holder;
            currWord.setText(wordString);
            letterCount[13] = true;
            for (int n = 0; n < letterCount.length; n++) {
                if (letterCount[n] == true) {
                    spot[n].setEnabled(false);
                }
            }
        }

    }

    public class press14 implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int i = 0;
            pressTrack[14] = true;
            for (i = 0; i < 16; i++) {
                spot[i].setEnabled(false);
            }
            spot[9].setEnabled(true);
            spot[10].setEnabled(true);
            spot[11].setEnabled(true);
            spot[13].setEnabled(true);
            spot[15].setEnabled(true);
            String holder;
            spot[14].revalidate();
            holder = spot[14].getText();
            wordString = wordString + holder;
            currWord.setText(wordString);
            letterCount[14] = true;
            for (int n = 0; n < letterCount.length; n++) {
                if (letterCount[n] == true) {
                    spot[n].setEnabled(false);
                }
            }
        }

    }

    public class press15 implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int i = 0;
            pressTrack[15] = true;
            for (i = 0; i < 16; i++) {
                spot[i].setEnabled(false);
            }
            spot[10].setEnabled(true);
            spot[11].setEnabled(true);
            spot[14].setEnabled(true);
            String holder;
            spot[15].revalidate();
            holder = spot[15].getText();
            wordString = wordString + holder;
            currWord.setText(wordString);
            letterCount[15] = true;
            for (int n = 0; n < letterCount.length; n++) {
                if (letterCount[n] == true) {
                    spot[n].setEnabled(false);
                }
            }
        }

    }

    //Action Listener for Shake Dice
    class shaker implements ActionListener {

        public shaker() {

        }

        @Override
        public void actionPerformed(ActionEvent e) {
            shakeCheck = true;
            diceBoard.shakeDice(button, spot, buttonPanel);
            wordKeep.clear();
            scoreAmount = 20;
            score.setText(String.valueOf(scoreAmount));
            txtArea.setText("");
            wordString = "";
            currWord.setText(wordString);
            taylorSwift.setEnabled(false);
            clocky.clocker();
            spot[0].addActionListener(new press0()); //reenable all buttons
            spot[1].addActionListener(new press1());
            spot[2].addActionListener(new press2());
            spot[3].addActionListener(new press3());
            spot[4].addActionListener(new press4());
            spot[5].addActionListener(new press5());
            spot[6].addActionListener(new press6());
            spot[7].addActionListener(new press7());
            spot[8].addActionListener(new press8());
            spot[9].addActionListener(new press9());
            spot[10].addActionListener(new press10());
            spot[11].addActionListener(new press11());
            spot[12].addActionListener(new press12());
            spot[13].addActionListener(new press13());
            spot[14].addActionListener(new press14());
            spot[15].addActionListener(new press15());
            for (int k = 0; k < 16; k++) {
                spot[k].setEnabled(true);
                pressTrack[k] = false;
            }

        }

    }

    //Action Lister for timer
    public class TimerListener { //Setting how the time runs

        int delay = 1000;
        public JDialog endMessage;
        ActionListener clockAct = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                sec = timeSecs % 60;
                min = timeSecs / 60;
                if (sec < 10) {
                    time.setText(min + ":0" + sec);
                } else {
                    time.setText(min + ":" + sec);
                }
                timeSecs--;
                sec = timeSecs % 60;
                min = timeSecs / 60;
                if (sec < 10) {
                    time.setText(min + ":0" + sec);
                } else {
                    time.setText(min + ":" + sec);
                }

                if (timeSecs == 0) {
                    timer.stop();
                    submit.setEnabled(false);

                    for (int x = 0; x < 16; x++) {
                        spot[x].setEnabled(false);
                    }


                    JOptionPane.showMessageDialog(frame, "Congratulations <Player Name> you are the Winner!", "Boggle", JOptionPane.INFORMATION_MESSAGE);     

                   /* int helper;
                    ArrayList<Integer> cpuWordInt = new ArrayList<>();
                    Random rand = new Random();
                    int i = rand.nextInt(wordKeep.size()) + 1;
                    JTextArea cpuFound = new JTextArea();

                    //Strikethrough Font
                    StyledDocument docUpdated = txtArea.getStyledDocument();
                    ArrayList<String> cpuWords = new ArrayList<>();
                    
                    Style strikeStyle = docUpdated.addStyle("Strikethrough", null);
                    StyleConstants.setStrikeThrough(strikeStyle, true);
                    
                    cpuFound.setText("");
                    cpuFound.append("Computer found words: \n\n");
                    
                    for (int x = 0; x < i; x++) {
                    
                        helper = rand.nextInt(wordKeep.size());

                        while (cpuWordInt.contains(helper)) {
                            helper = rand.nextInt(wordKeep.size());
                        }
                        
                        cpuWordInt.add(helper);
                        cpuWords.add(wordKeep.get(cpuWordInt.get(x)));
                    
                    }

                    for (int x = 0; x < cpuWords.size(); x++) {
                        cpuFound.append(cpuWords.get(x) + "\n");
                    }
                    
                    JOptionPane.showMessageDialog(frame, cpuFound, "Boggle", JOptionPane.INFORMATION_MESSAGE);
                    txtArea.setText("");

                    for (int n = 0; n < wordKeep.size(); n++) {
                        
                        if (cpuWords.contains(wordKeep.get(n))) {
                            try {
                                docUpdated.insertString(docUpdated.getLength(), wordKeep.get(n) + "\n", strikeStyle);
                            } catch (Exception ex) {
                                System.out.println(ex);
                            }
                            
                            if (wordKeep.get(n).length() < 5) {
                                scoreAmount = scoreAmount - 1;
                            }
                            if (wordKeep.get(n).length() == 5) {
                                scoreAmount = scoreAmount - 2;
                            }
                            if (wordKeep.get(n).length() == 6) {
                                scoreAmount = scoreAmount - 3;
                            }
                            if (wordKeep.get(n).length() == 7) {
                                scoreAmount = scoreAmount - 4;
                            }
                            if (wordKeep.get(n).length() > 7) {
                                scoreAmount = scoreAmount - 11;
                            }
                            score.setText(String.valueOf(scoreAmount));
                        }
                        else {
                            
                            try
                            {
                                docUpdated.insertString(docUpdated.getLength(), wordKeep.get(n) + "\n", null);
                            }
                            catch(Exception ex)
                            {
                                System.out.println(ex);
                            }
                        }
                    }
                   
                    JOptionPane.showMessageDialog(frame, "            GAME OVER", "Boggle", JOptionPane.WARNING_MESSAGE);
                */}
            }

        };

        void clocker() {
            timer = new Timer(delay, clockAct);
            timer.start();
        }

    }

    //Action Listener for Submit
    class submit implements ActionListener {

        public JDialog duplicate;

        @Override
        public void actionPerformed(ActionEvent e) {
            boolean result = false;
          //  String finder = wordString;

          /*  for (String string : boggleDict) { //Checking if word is in Dictionary
                if (finder.equalsIgnoreCase(string)) {
                    result = true;
                }
            }*/
           /* boolean result1 = true;//Checking if word is already registered
            for (String track : wordKeep) {
                if (finder.equalsIgnoreCase(track)) {
                    result1 = false;
                }
            }
            if (result1 == false) {
                JOptionPane.showMessageDialog(frame, "This word has already been entered!", "DUPLICATE", JOptionPane.ERROR_MESSAGE);
            }
            
            if (result == true && result1 == true && wordString.length() > 2) {//Evaluate the score
                wordKeep.add(finder);
                
                StyledDocument doc = txtArea.getStyledDocument();

                try {
                    doc.insertString(doc.getLength(), wordString + "\n", null);
                } catch (Exception ex) {
                    System.out.println(ex);
                }

                if (wordString.length() < 5) {
                    scoreAmount = scoreAmount + 1;
                }
                if (wordString.length() == 5) {
                    scoreAmount = scoreAmount + 2;
                }
                if (wordString.length() == 6) {
                    scoreAmount = scoreAmount + 3;
                }
                if (wordString.length() == 7) {
                    scoreAmount = scoreAmount + 4;
                }
                if (wordString.length() > 7) {
                    scoreAmount = scoreAmount + 11;
                }
                score.setText(String.valueOf(scoreAmount));
            }*/
           // result = false;
            int x = 0;
            for (x = 0; x < 16; x++) {
                spot[x].setEnabled(true);
            }
            wordString = "";
            currWord.setText(wordString);
            currWord.validate();
            for (int n = 0; n < letterCount.length; n++) {
                letterCount[n] = false;
            }

        }
    }

}
