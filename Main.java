import java.awt.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.*;

public class Main extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1;
	JButton topRight, topMid, topLeft,
            centerRight, center, centerLeft,
            bottomRight, bottomMid, bottomLeft; // 9 button keypad 
    JButton undo, resetSeed, newSeed;
	JPanel keypad, utilities, container, movecontainer, newSeedContainer;
    JLabel movesLabel, seedLabel;
    String moves = "Moves: ";
    int movesNum = 0;

    Icon barsIcon   = new ImageIcon("C:\\Users\\ykozh\\Documents\\GitHub\\Hive-Puzzle-Sim\\bars.png");
    Icon AcrossIcon = new ImageIcon("C:\\Users\\ykozh\\Documents\\GitHub\\Hive-Puzzle-Sim\\Across.png");
    Icon radioIcon  = new ImageIcon("C:\\Users\\ykozh\\Documents\\GitHub\\Hive-Puzzle-Sim\\radio.png");
    Icon bugIcon    = new ImageIcon("C:\\Users\\ykozh\\Documents\\GitHub\\Hive-Puzzle-Sim\\bug.png");

    HivePuzzle puz = new HivePuzzle(barsIcon, AcrossIcon, radioIcon, bugIcon);

    JPanel optionsContainer;
    String[] options = {"2 steps", "3 steps", "4 steps", "5 steps"};
    JButton setRandomButton, loadSeedButton;
    JTextField customSeedField;
    JComboBox<String> stepOptionsBox;
    
    javax.swing.Timer timer;
    int timerCounter = 0;
    
    private void updateBoard(HivePuzzle p){ // update the 9 button keypad and seed label to what each tile says
        topRight.setIcon(p.board[0].viewIcon());
        topMid.setIcon(p.board[1].viewIcon());
        topLeft.setIcon(p.board[2].viewIcon());
        centerRight.setIcon(p.board[3].viewIcon());
        center.setIcon(p.board[4].viewIcon());
        centerLeft.setIcon(p.board[5].viewIcon());
        bottomRight.setIcon(p.board[6].viewIcon());
        bottomMid.setIcon(p.board[7].viewIcon());
        bottomLeft.setIcon(p.board[8].viewIcon());
        seedLabel.setText("Seed: " + readifySeed(p.seed));
    }

	public Main(javax.swing.Timer timer) { // create window with timer object
		super("Hive Puzzle");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2 - 125, dim.height / 2 - this.getSize().height / 2 - 210);
        
        this.timer = timer;
        
        container = new JPanel();
        container.setLayout(new FlowLayout());
        
        movesLabel = new JLabel(moves + movesNum, SwingConstants.LEFT); // container for moves and seed labels
        movecontainer = new JPanel();
        movecontainer.setLayout(new FlowLayout());
        movecontainer.add(movesLabel);
        seedLabel = new JLabel("Seed: " + readifySeed(puz.seed), SwingConstants.RIGHT);
        movecontainer.add(seedLabel);
        
        // <-- setting up keypad buttons
		topRight = new JButton(barsIcon); // initialize with icon
        topRight.setPreferredSize(new Dimension(60,60)); // setup forced square dimensions
        topRight.addActionListener(this); // add AL
        
        topMid = new JButton(barsIcon);
        topMid.setPreferredSize(new Dimension(60,60));
        topMid.addActionListener(this);
        
        topLeft = new JButton(barsIcon);
        topLeft.setPreferredSize(new Dimension(60,60));
        topLeft.addActionListener(this);
        
        centerRight = new JButton(barsIcon);
        centerRight.setPreferredSize(new Dimension(60,60));
        centerRight.addActionListener(this);
        
        center = new JButton(barsIcon);
        center.setPreferredSize(new Dimension(60,60));
        center.addActionListener(this);
        
        centerLeft = new JButton(barsIcon);
        centerLeft.setPreferredSize(new Dimension(60,60));
        centerLeft.addActionListener(this);
        
        bottomRight = new JButton(barsIcon);
        bottomRight.setPreferredSize(new Dimension(60,60));
        bottomRight.addActionListener(this);
        
        bottomMid = new JButton(barsIcon);
        bottomMid.setPreferredSize(new Dimension(60,60));
        bottomMid.addActionListener(this);
        
        bottomLeft = new JButton(barsIcon);
        bottomLeft.setPreferredSize(new Dimension(60,60));
        bottomLeft.addActionListener(this);
        // keypad buttons end -->
        
        // utility buttons
        undo = new JButton("Undo");
        undo.addActionListener(this);
        resetSeed = new JButton("Reset");
        resetSeed.addActionListener(this);
        newSeed = new JButton("New");
        newSeed.addActionListener(this);
        
        // keypad and utility containers
        keypad = new JPanel();
        keypad.setLayout(new GridLayout(3,3));
        utilities = new JPanel();
        
        // add label container to global container
        container.add(movecontainer);  
        
        // add keypad buttons to keypad container
        keypad.add(topRight);
        keypad.add(topMid);
        keypad.add(topLeft);
        keypad.add(centerRight);
        keypad.add(center);
        keypad.add(centerLeft);
        keypad.add(bottomRight);
        keypad.add(bottomMid);
        keypad.add(bottomLeft);
        
        // add keypad container to global container
        container.add(keypad);
        
        // add utility buttons to utility container
        utilities.add(undo);
        utilities.add(resetSeed);
        utilities.add(newSeed);
        
        // add utility container to global container
        container.add(utilities);

        // container for seed generation tools
        optionsContainer = new JPanel();
        optionsContainer.setLayout(new FlowLayout());
        optionsContainer.setPreferredSize(new Dimension(250,300));
        
        // seed generation objects
        setRandomButton = new JButton("RNDM");
        setRandomButton.setPreferredSize(new Dimension(100,25));
        setRandomButton.addActionListener(this);
        stepOptionsBox = new JComboBox<String>(options);    
        stepOptionsBox.setPreferredSize(new Dimension(100,25));
        stepOptionsBox.addActionListener(this);
        customSeedField = new JTextField("000000000", 18);
        customSeedField.setPreferredSize(new Dimension(205,25));
        customSeedField.addActionListener(this);
        loadSeedButton = new JButton("LOAD");
        loadSeedButton.setPreferredSize(new Dimension(205,25));
        loadSeedButton.addActionListener(this);
        
        optionsContainer.add(setRandomButton);
        optionsContainer.add(stepOptionsBox);
        optionsContainer.add(customSeedField);
        optionsContainer.add(loadSeedButton);
        
        // add seed generation tools into global container
        container.add(optionsContainer);

        add(container);
        setResizable(false); // cant change size
        setSize(250, 420);
		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == undo) { // UNDO
            if (movesNum > 0){
                movesNum--;
                movesLabel.setText(moves + movesNum);   
                puz.undo();
                updateBoard(puz);
            }
		} else if (e.getSource() == topRight){ // TOP RIGHT
            puz.makeMove(0,0);
            movesNum++;            
            movesLabel.setText(moves + movesNum);   
            updateBoard(puz);
        } else if (e.getSource() == topMid){ // TOP MID
            puz.makeMove(0,1);            
            movesNum++;
            movesLabel.setText(moves + movesNum);   
            updateBoard(puz);            
        } else if (e.getSource() == topLeft){ // TOP LEFT
            puz.makeMove(0,2);
            movesNum++;
            movesLabel.setText(moves + movesNum);       
            updateBoard(puz);
        } else if (e.getSource() == centerRight){ // CENTER RIGHT
            puz.makeMove(1,0);
            movesNum++;
            movesLabel.setText(moves + movesNum);       
            updateBoard(puz);
        } else if (e.getSource() == center){ // CENTER
            puz.makeMove(1,1);
            movesNum++;
            movesLabel.setText(moves + movesNum);       
            updateBoard(puz);
        } else if (e.getSource() == centerLeft){ // CENTER LEFT
            puz.makeMove(1,2);
            movesNum++;
            movesLabel.setText(moves + movesNum);       
            updateBoard(puz);
        } else if (e.getSource() == bottomRight){ // BOTTOM RIGHT
            puz.makeMove(2,0);
            movesNum++;
            movesLabel.setText(moves + movesNum);       
            updateBoard(puz);
        } else if (e.getSource() == bottomMid){ // BOTTOM MID
            puz.makeMove(2,1);
            movesNum++;
            movesLabel.setText(moves + movesNum);       
            updateBoard(puz);
        } else if (e.getSource() == bottomLeft){ // BOTTOM LEFT
            puz.makeMove(2,2);
            movesNum++;
            movesLabel.setText(moves + movesNum);       
            updateBoard(puz);
        } else if (e.getSource() == resetSeed){ // RESET SEED
            puz.resetSeed();
            movesNum = 0;
            movesLabel.setText(moves + movesNum);       
            updateBoard(puz);
        } else if (e.getSource() == newSeed){ // NEW SEED  
            setEnabledAll(false);
            timer.start();
            timerCounter++;
            if (timerCounter > 5){
                timerCounter = 0;
                timer.stop();
                setEnabledAll(true);
            }
            puz.newSeed();
            movesNum = 0;
            movesLabel.setText(moves + movesNum);       
            seedLabel.setText("Seed: " + readifySeed(puz.seed));
            updateBoard(puz);
        }
        if (e.getSource() == setRandomButton) { // RNDM 
            customSeedField.setText(readifySeed(puz.generateSeed()));
        } else if (e.getSource() == loadSeedButton) { // LOAD
            movesNum = 0;
            movesLabel.setText(moves + movesNum);
            puz.setSeed(unredifySeed(customSeedField.getText()));
            updateBoard(puz);
        } else if (e.getSource() == stepOptionsBox){ // STEPS BOX
            String s = (String) stepOptionsBox.getSelectedItem();
            switch (s){
                case "2 steps":
                    customSeedField.setText(readifySeed(puz.generateStepSeed(2)));
                    break;
                case "3 steps":
                    customSeedField.setText(readifySeed(puz.generateStepSeed(3)));                    
                    break;
                case "4 steps":
                    customSeedField.setText(readifySeed(puz.generateStepSeed(4)));
                    break;
                case "5 steps":
                    customSeedField.setText(readifySeed(puz.generateStepSeed(5)));
                    break;                    
            }
        } else if (e.getSource() == timer){
            actionPerformed(new ActionEvent(newSeed, 69, "asd"));
            try {
                Thread.sleep(10);
            } catch (InterruptedException E){
                // lol
            }
        }
	}

    private void setEnabledAll(boolean b){
        newSeed.setEnabled(b);
        // undo.setEnabled(b);
        // resetSeed.setEnabled(b);
        // setRandomButton.setEnabled(b);
        // loadSeedButton.setEnabled(b);
        // customSeedField.setEnabled(b);
        // stepOptionsBox.setEnabled(b);
    }
    
    private String readifySeed(String seed){
        char[] c = seed.toCharArray();
        for (int ii = 0; ii < c.length; ii++){
            c[ii] = (char)((int)c[ii] + 48);
        }
        return String.valueOf(c);
    }

    private String unredifySeed(String seed){
        char[] c = seed.toCharArray();
        for (int ii = 0; ii < c.length; ii++){
            c[ii] = (char)((int)c[ii] - 48);
        }
        return String.valueOf(c);              
    }

	public static void main(String args[]) {   
        javax.swing.Timer timer = new javax.swing.Timer(100, null);
        //timer.setInitialDelay(0);    
		Main coo = new Main(timer);
        timer.addActionListener(coo);          
        //timer.start();
	}
}
