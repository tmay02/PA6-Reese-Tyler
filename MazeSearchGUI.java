
/** 
 * MazeSearchGUI.java
 * 
 * A program to search through a maze using DPS or BFS via a graphical user 
 * interface. The user can customize the maze using the main method.
 * 
 * Created by Christine Alvarado on April 19, 2014
 * Updated by Hannah Hui and Tim Jiang on February 11, 2022
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * A very basic version of a 2D Maze search program with GUI. The delay between
 * steps can be adjusted, as well as the order in which a cell visits its
 * neighbors.
 */
public class MazeSearchGUI extends JFrame implements ActionListener {
    /**
     * An array of the cells on the board. Cell is an inner class of
     * MazeSearchGUI
     */
    private Cell[][] cells;

    // The timer to control how fast the cells are animatedd
    private Timer animateTimer;

    // Is the game in the process of animating
    private boolean animating;

    // The search algorithm to use (BFS or DFS)
    private String searchAlg;

    // Whether the start cell has been selected
    private boolean startSet;

    // The queue of cells to animate via the animation
    QueueInterface<Cell> toAnimate; // The cells the animation is exposing

    // The reset button, also used to display instructions
    private JButton reset;

    // The dimensions of each cell
    private static final int CELL_SIZE = 50;

    // The animation delay
    private static final int DELAY = 150;

    // Button labels
    private static final String SELECT = "Select a starting cell";
    private static final String RESET = "Reset maze";

    // String encodings for the maze representation
    private static final String EMP = " ";
    private static final String WAL = "X";
    private static final String FIN = "F";
    // Not allowed in preset creation use, set by algorithm for GUI display
    private static final String PTH = "P";
    private static final String STA = "S";
    private static final String VIS = "V";

    // Offset to tell which order to explore neighbors (N, E, S, W) or
    // (up, right, down, left)
    private final int[][] NEIGHBOR_ORDER = { { -1, 0 }, { 0, 1 },
            { 1, 0 }, { 0, -1 } };

    // Colors used for the cells
    private final Color COLOR_EMPTY = Color.DARK_GRAY;
    private final Color COLOR_START = Color.YELLOW;
    private final Color COLOR_FINISH = Color.GREEN;
    private final Color COLOR_FOUND_FINISH = new Color(30, 90, 30);
    private final Color COLOR_WALL = Color.BLUE;
    private final Color COLOR_VISITED = Color.WHITE;
    private final Color COLOR_PATH = Color.RED;

    private static final int MIN_ARGS = 2;

    /**
     * The constructor for MazeSearchGUI. It creates the main JPanel for the
     * user interface.
     * 
     * @param maze      a 2D String array that represents the maze
     * @param searchAlg either "BFS" or "DFS"
     */
    public MazeSearchGUI(String[][] maze, String searchAlg) {
        this.searchAlg = searchAlg;
        animating = false;

        // Create a button that both shows the instruction and used as reset
        reset = new JButton(SELECT);
        reset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!animating) {
                    clearCells();
                }
            }
        });

        // Calling the helper function to create a JPanel of cells
        JPanel cellPanel = createCellPanel(maze);

        setLayout(new BorderLayout());
        add(cellPanel, BorderLayout.CENTER);
        add(reset, BorderLayout.SOUTH);
        pack();
        setVisible(true);
    }

    /**
     * Create GUI grid layout and populate with Cells.
     * 
     * @param maze String representation of maze layout
     * @return GUI element of entire maze
     */
    private JPanel createCellPanel(String[][] maze) {
        int height = maze.length;
        int width = maze[0].length;
        cells = new Cell[height][width];

        // create a new GUI with a grid of cells
        JPanel cellPanel = new JPanel();
        cellPanel.setPreferredSize(new Dimension(CELL_SIZE * width,
                CELL_SIZE * height));
        cellPanel.setLayout(new GridLayout(height, width));

        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) {
                // Initialize cells based on the string passed in
                if (maze[r][c].equals(EMP)) {
                    cells[r][c] = new Cell(r, c, 1);
                } else if (maze[r][c].toUpperCase().equals(FIN)) {
                    cells[r][c] = new Cell(r, c, 3);
                } else if (maze[r][c].toUpperCase().equals(WAL)) {
                    cells[r][c] = new Cell(r, c, 4);
                } else { // Default case for unrecognized string: empty cell
                    cells[r][c] = new Cell(r, c, 1);
                }
                cellPanel.add(cells[r][c]);
            }
        }
        return cellPanel;
    }

    /**
     * Private helper to set off an animation where
     * each change is delayed for DELAY milliseconds
     */
    private void animateSlowly() {
        if (toAnimate == null)
            return;
        animateTimer = new Timer(DELAY, this);
        animating = true;
        animateTimer.start();
    }

    /**
     * Animate one cell from the toAnimate queue and
     * stop the timer when toAnimate is empty
     * 
     * @param e the action event triggered
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Cell next = toAnimate.dequeue();
        if (toAnimate.size() == 0) {
            // No more cells to animate, stop timer and set animating boolean
            animating = false;
            animateTimer.stop();

            // If the path successfully reached the finish
            if (next.isFinish()) {
                // Color the finish to be found
                next.setBackground(COLOR_FOUND_FINISH);
                // Draw the path found from start to finish
                next = next.getPrevious();
                while (next.getPrevious() != null) {
                    next.setBackground(COLOR_PATH);
                    next.setText(PTH);
                    next = next.getPrevious();
                }
            } else {
                next.setAnimated();
            }

            // Activate reset text
            reset.setText(RESET);
        } else {
            next.setAnimated();
        }
    }

    /**
     * Update (animate) the visited cells on the maze GUI board based on
     * the selected start cell.
     * 
     * @param row row of the selected start cell
     * @param col column of the selected start cell
     */
    public void animateCells(int row, int col) {
        // If animation is going on, don't do anything.
        if (animating)
            return;

        // Run the appropriate search algorithm
        if (searchAlg.equals("BFS")) {
            toAnimate = animateCellsBFS(row, col);
        } else {
            toAnimate = animateCellsDFS(row, col);
        }
        toAnimate.dequeue(); // Remove start cell to maintain color

        if (toAnimate.size() > 0) {
            // Animate the cells slowly
            animateSlowly();
        }
    }

    /**
     * Checks if a cell is within the bounds of the cell grid.
     * 
     * @param row row of the cell
     * @param col column of the cell
     * @return true if the cell is within the bounds, otherwise false
     */
    private boolean validCell(int row, int col) {
        return (row >= 0) && (row < cells.length) && (col >= 0) &&
                (col < cells[row].length);
    }

    /**
     * Use breadth first search (BFS) to visit cells in the maze based on the
     * selected start cell. The order of exploration of cells will be used for
     * the animation.
     * 
     * @param row row of the start cell
     * @param col col of the start cell
     * @return queue of explored cells (in the order of exploration). First
     *         cell should be the start cell and last cell should be the
     *         finish cell.
     */
    public QueueInterface<Cell> animateCellsBFS(int row, int col) {
        // Queue to store explored cells in the order of exploration
        QueueInterface<Cell> exploredCells = new MyQueue<Cell>(cells.length *
                cells[0].length);
        // Queue for BFS algorithm
        QueueInterface<Cell> theQueue = new MyQueue<Cell>(cells.length *
                cells[0].length);
        // TODO: add the cell at row, col to the queue

        // BFS algorithm
        while (theQueue.size() > 0) {
            // TODO: remove the cell from the queue and store it as currCell

            exploredCells.enqueue(currCell);
            // If the visited cell is the finish cell, we stop searching
            if (currCell.isFinish()) {
                return exploredCells;
            }

            int cellRow = currCell.getRow();
            int cellCol = currCell.getColumn();
            // Traverse possible neighbor cells in N, E, S, W order
            for (int[] offset : NEIGHBOR_ORDER) {
                if (validCell(cellRow + offset[0], cellCol + offset[1])) {
                    Cell nextCell = cells[cellRow + offset[0]][cellCol +
                            offset[1]];
                    // Find valid, unvisited neighbors and add to queue
                    if (nextCell.isFinish() || (nextCell.isEmpty()
                            && !nextCell.isVisited())) {
                        nextCell.setVisited();
                        nextCell.setPrevious(currCell);
                        // TODO: add the next cell to the queue
                    }
                }
            }
        }
        // Finish cell not found, but explored cells will be animated
        return exploredCells;
    }

    /**
     * Use depth first search (DFS) to visit cells in the maze based on the
     * selected start cell. The order of exploration of cells will be used for
     * the animation.
     * 
     * @param row row of the start cell
     * @param col col of the start cell
     * @return queue of explored cells (in the order of exploration). First
     *         cell should be the start cell and last cell should be the
     *         finish cell.
     */
    public QueueInterface<Cell> animateCellsDFS(int row, int col) {
        // Queue to store explored cells in the order of exploration
        QueueInterface<Cell> exploredCells = new MyQueue<Cell>(cells.length *
                cells[0].length);
        // Stack for DFS algorithm
        StackInterface<Cell> theStack = new MyStack<Cell>(cells.length *
                cells[0].length);
        // TODO: add the cell at row, col to the stack

        // DFS algorithm
        while (theStack.size() > 0) {
            // TODO: remove the cell from the stack and store it as currCell

            exploredCells.enqueue(currCell);
            currCell.setVisited();
            // If the visited cell is the finish cell, we stop searching
            if (currCell.isFinish()) {
                return exploredCells;
            }

            int cellRow = currCell.getRow();
            int cellCol = currCell.getColumn();
            // Traverse possible neighbor cells in N, E, S, W order
            for (int[] offset : NEIGHBOR_ORDER) {
                if (validCell(cellRow + offset[0], cellCol + offset[1])) {
                    Cell nextCell = cells[cellRow + offset[0]][cellCol +
                            offset[1]];
                    // Find valid, unvisited neighbors and add to stack
                    if (nextCell.isFinish() || (nextCell.isEmpty()
                            && !nextCell.isVisited())) {
                        nextCell.setPrevious(currCell);
                        // TODO: add the next cell to the stack
                    }
                }
            }
        }
        // Finish cell not found, but explored cells will be animated
        return exploredCells;
    }

    /**
     * Clear all the cells, reset the text back to SELECT
     */
    private void clearCells() {
        for (int r = 0; r < cells.length; r++) {
            for (int c = 0; c < cells[0].length; c++) {
                cells[r][c].clear();
            }
        }
        reset.setText(SELECT);
    }

    /**
     * Inner class button that also serves as the state object for the cells in
     * the grid. It keeps track of whether the cell is a wall/start/finish cell
     * and whether it has been visited in the search algorithm.
     */
    class Cell extends JButton implements MouseListener {
        // Keep track of the row and the column
        private int row;
        private int column;

        private boolean animated; // Is the cell animated?
        private Cell previous; // previous cell in the search path
        private boolean isVisited; // did the search algorithm visit this cell?

        // Types of cells, only empty cell can be visited
        private static final int EMPTY = 1;
        private static final int START = 2; // Set by user click
        private static final int FINISH = 3; // Set by maze preset
        private static final int WALL = 4; // Set by maze preset
        private int cellType;

        /**
         * Instantiates cell with its grid position and cell type (empty, start
         * finish, or wall)
         * 
         * @param row      row of the cell
         * @param col      column of the cell
         * @param cellType empty (1), start(2), finish (3), or wall (4)
         */
        public Cell(int row, int col, int cellType) {
            this.row = row;
            this.column = col;
            this.isVisited = false;
            this.animated = false;
            this.cellType = cellType;
            this.previous = null;

            // Set the cell color according to the cell type
            switch (cellType) {
                case EMPTY:
                    setBackground(COLOR_EMPTY);
                    break;
                case START:
                    setBackground(COLOR_START);
                    setText(STA);
                    break;
                case FINISH:
                    setBackground(COLOR_FINISH);
                    setText(FIN);
                    break;
                case WALL:
                    setBackground(COLOR_WALL);
                    setText(WAL);
                    break;
            }
            setOpaque(true);
            addMouseListener(this); // Buttons listen for their own actions
        }

        /**
         * Get the cell's row in the Maze board
         * 
         * @return row index of this cell
         */
        public int getRow() {
            return this.row;
        }

        /**
         * Get the cell's column in the Maze board
         * 
         * @return column index of this cell
         */
        public int getColumn() {
            return this.column;
        }

        /**
         * Get the cell type
         * 
         * @return empty (1), start(2), finish (3), or wall (4)
         */
        public int getCellType() {
            return this.cellType;
        }

        /**
         * Clear the cell. Reset the cell types, previous, and cell color.
         */
        public void clear() {
            this.isVisited = false;
            if (this.getCellType() == START) {
                this.cellType = EMPTY;
                startSet = false;
                setText("");
            }
            if (this.getCellType() == EMPTY) {
                this.setBackground(COLOR_EMPTY);
                setText("");
            }
            if (this.getCellType() == FINISH) {
                this.setBackground(COLOR_FINISH);
                this.setText(FIN);
            }
            this.previous = null;
        }

        /**
         * Set the cell as animated. Change the color of the cell.
         */
        public void setAnimated() {
            this.animated = true;
            setBackground(COLOR_VISITED); // change the color
            this.setText(VIS);
        }

        /**
         * Set the visited status of the cell to visited
         */
        public void setVisited() {
            this.isVisited = true;
        }

        /**
         * Return whether or not the cell has been visited
         * 
         * @return true if visisted, otherwise false
         */
        public boolean isVisited() {
            return this.isVisited;
        }

        /**
         * Set the previous cell to the cell argument. The previous cell is the
         * neighbor cell that caused the visit to this cell.
         * 
         * @param previousCell previous cell in path
         */
        public void setPrevious(Cell previousCell) {
            this.previous = previousCell;
        }

        /**
         * Return the previous cell
         * 
         * @return previous cell in path
         */
        public Cell getPrevious() {
            return this.previous;
        }

        /**
         * Returns whether the cell is empty
         * 
         * @return true if cell type is empty, false otherwise
         */
        public boolean isEmpty() {
            return this.cellType == EMPTY;
        }

        /**
         * Returns whether the cell is the finish cell
         * 
         * @return true if cell type is the finish cell, false otherwise
         */
        public boolean isFinish() {
            return this.cellType == FINISH;
        }

        /**
         * When a left-click occurs, check if the cell can be click on.
         * If so, set it to START and trigger the search algorithm + animation
         * 
         * @param e the MouseEvent triggered
         */
        @Override
        public void mouseReleased(MouseEvent e) {
            // Left-click
            if (e.getButton() == MouseEvent.BUTTON1) {
                // If this is an empty cell that can be selected
                if (!startSet && this.cellType == EMPTY) {
                    // Set the selected cell to be the start cell
                    this.cellType = START;
                    startSet = true;
                    setBackground(COLOR_START);
                    setText(STA);
                    // Start search algorithm
                    MazeSearchGUI.this.animateCells(this.row, this.column);
                }
            }
        }

        /**
         * Unused behavior, required for implementing MouseListener
         * 
         * @param e the MouseEvent triggered
         */
        @Override
        public void mousePressed(MouseEvent e) {
        }

        /**
         * Unused behavior, required for implementing MouseListener
         * 
         * @param e the MouseEvent triggered
         */
        @Override
        public void mouseClicked(MouseEvent e) {
        }

        /**
         * Unused behavior, required for implementing MouseListener
         * 
         * @param e the MouseEvent triggered
         */
        @Override
        public void mouseEntered(MouseEvent e) {
        }

        /**
         * Unused behavior, required for implementing MouseListener
         * 
         * @param e the MouseEvent triggered
         */
        @Override
        public void mouseExited(MouseEvent e) {
        }
    }

    /**
     * Initializes the maze and search algorithm based on arguments. Left-click
     * will select a start cell and the GUI simulation will the chosen search
     * algorithm to search for a path to the finish cell.
     * 
     * @param args args[0] - 1, 2, or 3 to select the corresponding maze preset
     *             args[1] - "BFS" or "DFS" to select the search algorithm
     */
    public static void main(String[] args) {
        // Create the mazes. You can experiment with creating your own maze
        // based on the presets defined here.
        String[][] preset1 = {
                { EMP, EMP, EMP, EMP },
                { EMP, WAL, WAL, WAL },
                { EMP, EMP, EMP, EMP },
                { EMP, WAL, EMP, FIN }
        };
        String[][] preset2 = {
                { EMP, EMP, EMP, EMP, EMP },
                { EMP, EMP, EMP, WAL, EMP },
                { EMP, WAL, WAL, EMP, EMP },
                { EMP, WAL, EMP, EMP, WAL },
                { EMP, WAL, EMP, EMP, FIN }
        };
        String[][] preset3 = {
                { EMP, EMP, EMP, EMP, EMP, EMP, EMP, EMP, EMP, EMP },
                { EMP, EMP, EMP, EMP, EMP, EMP, EMP, EMP, EMP, EMP },
                { EMP, EMP, EMP, EMP, EMP, EMP, EMP, EMP, EMP, EMP },
                { EMP, EMP, EMP, EMP, EMP, EMP, EMP, EMP, EMP, EMP },
                { EMP, EMP, EMP, EMP, EMP, EMP, EMP, EMP, EMP, EMP },
                { EMP, EMP, EMP, EMP, EMP, EMP, EMP, EMP, EMP, EMP },
                { EMP, EMP, EMP, EMP, EMP, EMP, EMP, EMP, EMP, EMP },
                { EMP, EMP, EMP, EMP, EMP, EMP, EMP, EMP, EMP, EMP },
                { EMP, EMP, EMP, EMP, EMP, EMP, EMP, EMP, FIN, EMP },
                { EMP, EMP, EMP, EMP, EMP, EMP, EMP, EMP, EMP, EMP },
        };

        // Check for correct number of valid arguments
        if (args.length < MIN_ARGS) {
            System.out.println("Invalid arguments.");
            System.out.println("Usage: java MazeSearchGUI" +
                    " <preset maze number: 1-3> <\"BFS\" or \"DFS\">");
            System.out.println("Example: java MazeSearchGUI \"DFS\"");

            System.exit(1);
        }

        String[][] maze; // maze to visualize and search

        // Select the maze preset based on the argument
        switch (Integer.parseInt(args[0])) {
            case 1:
                maze = preset1;
                break;
            case 2:
                maze = preset2;
                break;
            case 3:
                maze = preset3;
                break;
            default:
                maze = preset1;
        }
        new MazeSearchGUI(
                maze, // preset maze number
                args[1]); // "BFS" or "DFS"
    }
}
