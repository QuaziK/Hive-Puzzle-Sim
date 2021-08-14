import java.awt.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.*;

public class Main extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1;
	JButton topRight, topMid, topLeft,
            centerRight, center, centerLeft,
            bottomRight, bottomMid, bottomLeft;
    JButton undo, resetSeed, newSeed;
	JPanel keypad, utilities, container, movecontainer;
    JLabel movesLabel, seedLabel;
    String moves = "Moves: ";
    int movesNum = 0;

    Icon barsIcon = new ImageIcon("C:\\Users\\ykozh\\Documents\\GitHub\\Hive-Puzzle-Sim\\bars.png");
    Icon AcrossIcon = new ImageIcon("C:\\Users\\ykozh\\Documents\\GitHub\\Hive-Puzzle-Sim\\Across.png");
    Icon radioIcon = new ImageIcon("C:\\Users\\ykozh\\Documents\\GitHub\\Hive-Puzzle-Sim\\radio.png");
    Icon bugIcon = new ImageIcon("C:\\Users\\ykozh\\Documents\\GitHub\\Hive-Puzzle-Sim\\bug.png");

    HivePuzzle puz = new HivePuzzle(barsIcon, AcrossIcon, radioIcon, bugIcon);

    private void updateBoard(HivePuzzle p){
        topRight.setIcon(p.board[0].viewIcon());
        topMid.setIcon(p.board[1].viewIcon());
        topLeft.setIcon(p.board[2].viewIcon());
        centerRight.setIcon(p.board[3].viewIcon());
        center.setIcon(p.board[4].viewIcon());
        centerLeft.setIcon(p.board[5].viewIcon());
        bottomRight.setIcon(p.board[6].viewIcon());
        bottomMid.setIcon(p.board[7].viewIcon());
        bottomLeft.setIcon(p.board[8].viewIcon());
    }

	public Main() {
		super("Hive Puzzle");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        
        container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        
        movesLabel = new JLabel(moves + movesNum, SwingConstants.LEFT);
        movecontainer = new JPanel();
        movecontainer.setLayout(new FlowLayout());
        movecontainer.add(movesLabel);
        
        seedLabel = new JLabel("Seed: " + puz.seed, SwingConstants.RIGHT);
        movecontainer.add(seedLabel);
        
		topRight = new JButton(barsIcon);
        topRight.setPreferredSize(new Dimension(60,60));
        topRight.setFont(new Font("Arial", Font.PLAIN, 20));
        topRight.addActionListener(this);
        
        topMid = new JButton(barsIcon);
        topMid.setPreferredSize(new Dimension(60,60));
        topMid.setFont(new Font("Arial", Font.PLAIN, 20));
        topMid.addActionListener(this);
        
        topLeft = new JButton(barsIcon);
        topLeft.setPreferredSize(new Dimension(60,60));
        topLeft.setFont(new Font("Arial", Font.PLAIN, 20));
        topLeft.addActionListener(this);
        
        centerRight = new JButton(barsIcon);
        centerRight.setPreferredSize(new Dimension(60,60));
        centerRight.setFont(new Font("Arial", Font.PLAIN, 20));
        centerRight.addActionListener(this);
        
        center = new JButton(barsIcon);
        center.setPreferredSize(new Dimension(60,60));
        center.setFont(new Font("Arial", Font.PLAIN, 20));
        center.addActionListener(this);
        
        centerLeft = new JButton(barsIcon);
        centerLeft.setPreferredSize(new Dimension(60,60));
        centerLeft.setFont(new Font("Arial", Font.PLAIN, 20));
        centerLeft.addActionListener(this);
        
        bottomRight = new JButton(barsIcon);
        bottomRight.setPreferredSize(new Dimension(60,60));
        bottomRight.setFont(new Font("Arial", Font.PLAIN, 20));
        bottomRight.addActionListener(this);
        
        bottomMid = new JButton(barsIcon);
        bottomMid.setPreferredSize(new Dimension(60,60));
        bottomMid.setFont(new Font("Arial", Font.PLAIN, 20));
        bottomMid.addActionListener(this);
        
        bottomLeft = new JButton(barsIcon);
        bottomLeft.setPreferredSize(new Dimension(60,60));
        bottomLeft.setFont(new Font("Arial", Font.PLAIN, 20));
        bottomLeft.addActionListener(this);
        
        undo = new JButton("Undo");
        undo.addActionListener(this);
        resetSeed = new JButton("Reset");
        resetSeed.addActionListener(this);
        newSeed = new JButton("New");
        newSeed.addActionListener(this);
        
        keypad = new JPanel();
        keypad.setLayout(new GridLayout(3,3));
        utilities = new JPanel();
        
        container.add(movecontainer);  
        
        keypad.add(topRight);
        keypad.add(topMid);
        keypad.add(topLeft);
        keypad.add(centerRight);
        keypad.add(center);
        keypad.add(centerLeft);
        keypad.add(bottomRight);
        keypad.add(bottomMid);
        keypad.add(bottomLeft);
        
        container.add(keypad);
        
        utilities.add(undo);
        utilities.add(resetSeed);
        utilities.add(newSeed);
        
        container.add(utilities);

        add(container);
        setResizable(false);
        setSize(250, 300);
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
            puz.newSeed();
            movesNum = 0;
            movesLabel.setText(moves + movesNum);       
            seedLabel.setText("Seed: " + readifySeed(puz.seed));
            updateBoard(puz);
        }
	}

    private String readifySeed(String seed){
        char[] c = seed.toCharArray();
        for (int ii = 0; ii < c.length; ii++){
            c[ii] = (char)((int)c[ii] + 48);
        }
        return String.valueOf(c);
    }

	public static void main(String args[]) {
		Main coo = new Main();
	}
}
