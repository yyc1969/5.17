//print the panel, initial, make concise method regarding key event, make mouse click method

package view.game;

import controller.GameController;
import model.Direction;
import model.MapModel;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * It is the subclass of ListenerPanel, so that it should implement those four methods: do move left, up, down ,right.
 * The class contains a grids, which is the corresponding GUI view of the matrix variable in MapMatrix.
 */
// if I change code directly on gitHub
public class GamePanel extends ListenerPanel {
    private List<BoxComponent> boxes;
    private MapModel model;
    private GameController controller;
    private JLabel stepLabel;
    private int steps;
    private final int GRID_SIZE = 50;
    private BoxComponent selectedBox;
    private VictoryInterface victoryInterface;
    // to record every state after every move
    private int[][] gameState ;
    // store the state (two-dimensional list)
    private ArrayList<int [][]> states;

    // constructor, new GamePanel(mapModel)
    public GamePanel(MapModel model) {
        boxes = new ArrayList<>();
        this.setVisible(true);
        this.setFocusable(true); // can be controlled by the keyboard
        this.setLayout(null);
        this.setSize(model.getWidth() * GRID_SIZE + 4, model.getHeight() * GRID_SIZE + 4); // GRID_SIZE is a unit length
        this.model = model;
        this.selectedBox = null;
        this.victoryInterface = new VictoryInterface(this);
        this.steps = 0;

        gameState = new int[model.getMatrix().length][model.getMatrix()[0].length];
        states = new ArrayList<>();

        initialGame();
    }

    /*
                        {1, 2, 2, 1, 1},
                        {3, 4, 4, 2, 2},
                        {3, 4, 4, 1, 0},
                        {1, 2, 2, 1, 0},
                        {1, 1, 1, 1, 1}
     */

    // mark the 4 * 4 box
    BoxComponent CaoCaoBox = new BoxComponent(Color.GREEN, 0, 0);
    boolean isCaoCaoBox = false;

    //copy a map
    //from MapModel model
    public void initialGame() {
        int[][] map = new int[model.getHeight()][model.getWidth()];
        for (int i = 0; i < map.length; i++)
        {
            for (int j = 0; j < map[0].length; j++)
            {
                map[i][j] = model.getId(i, j);
            }
        }
        //build Component
        // paint the boxes regarding the mapModel
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                BoxComponent box = null;
                if (map[i][j] / 10 == 1) {
                    box = new BoxComponent(Color.ORANGE, i, j);
                    box.setSize(GRID_SIZE, GRID_SIZE);
                    map[i][j] = 0;
                    isCaoCaoBox = false;
                } else if (map[i][j] / 10 == 2) {
                    box = new BoxComponent(Color.PINK, i, j);
                    box.setSize(GRID_SIZE * 2, GRID_SIZE);
                    map[i][j] = 0;
                    map[i][j + 1] = 0;
                    isCaoCaoBox = false;
                } else if (map[i][j] / 10 == 3) {
                    box = new BoxComponent(Color.BLUE, i, j);
                    box.setSize(GRID_SIZE, GRID_SIZE * 2);
                    map[i][j] = 0;
                    map[i + 1][j] = 0;
                    isCaoCaoBox = false;
                } else if (map[i][j] / 10 == 4) {
                    box = new BoxComponent(Color.GREEN, i, j);
                    box.setSize(GRID_SIZE * 2, GRID_SIZE * 2);
                    map[i][j] = 0;
                    map[i + 1][j] = 0;
                    map[i][j + 1] = 0;
                    map[i + 1][j + 1] = 0;
                    isCaoCaoBox = true;
                }
                if (box != null) // map[i][j] is one of 1, 2, 3, 4
                {
                    box.setLocation(j * GRID_SIZE + 2, i * GRID_SIZE + 2);
                    boxes.add(box); // add the specified box into the boxes array
                    this.add(box); // put the box component to the panel

                    if (isCaoCaoBox)
                    {
                        // CaoCoaBox tracts the location of the 4 * 4 box
                        // CaoCaoBox points to the same memory location asa the 4 * 4 box
                        CaoCaoBox = boxes.getLast();
                    }
                }
            }
        }
        this.repaint();
    }


    // draw the background of the panel
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g); // clear the background
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, this.getWidth(), this.getHeight()); // fill the panel with grey color
        Border border = BorderFactory.createLineBorder(Color.DARK_GRAY, 2); //create an outline
        this.setBorder(border); // draw an outline
    }

    // if the mouse clicks at a box -> change the "selected situation" of the box
    @Override
    public void doMouseClick(Point point)
    {
        this.requestFocusInWindow();
        Component component = this.getComponentAt(point); // component is what the mouse clicks at
        if (component instanceof BoxComponent clickedComponent) // if (component instanceof BoxComponent) { BoxComponent clickedComponent = component);}
        {
            if (selectedBox == null) {
                selectedBox = clickedComponent;
                selectedBox.setSelected(true);
            } else if (selectedBox != clickedComponent) {
                selectedBox.setSelected(false);
                clickedComponent.setSelected(true);
                selectedBox = clickedComponent;
            } else // click one box for two times
            {
                clickedComponent.setSelected(false);
                selectedBox = null;
            }
        }
    }

    public void updateGameModel(MapModel model) {
        for (BoxComponent box : boxes) {
            this.remove(box);
        }
        boxes.clear();
        this.model = model;
    }

    @Override
    public void doMoveRight() {
        System.out.println("Click VK_RIGHT"); // on the terminal
        if (selectedBox != null)
        {
            if (controller.doMove(selectedBox.getRow(), selectedBox.getCol(), Direction.RIGHT)) // within the boundary
            {
                afterMove();
            }
        }
    }

    @Override
    public void doMoveLeft() {
        System.out.println("Click VK_LEFT");
        if (selectedBox != null) {
            if (controller.doMove(selectedBox.getRow(), selectedBox.getCol(), Direction.LEFT)) {
                afterMove();
            }
        }
    }

    @Override
    public void doMoveUp() {
        System.out.println("Click VK_Up");
        if (selectedBox != null) {
            if (controller.doMove(selectedBox.getRow(), selectedBox.getCol(), Direction.UP)) {
                afterMove();
            }
        }
    }

    // move the boxes and change the step counter
    @Override
    public void doMoveDown() {
        System.out.println("Click VK_DOWN");
        if (selectedBox != null) {
            if (controller.doMove(selectedBox.getRow(), selectedBox.getCol(), Direction.DOWN)) // invoke the doMove method
            {
                afterMove();
            }
        }
    }

    //count the step and show it one the window
    public void afterMove() {
        this.steps++;
        this.stepLabel.setText(String.format("Step: %d", this.steps));
        checkVictory();
        // record the state
        MapModel.copyMatrix(model.getMatrix(), gameState);
        states.add(gameState);
        gameState = new int[model.getMatrix().length][model.getMatrix()[0].length];
    }

    public void checkVictory()
    {
        int targetLocationX = getWidth() - 2 * getGRID_SIZE() - 2; // padding
        int targetLocationY = getHeight() / 2 - getGRID_SIZE();
        int currentX = CaoCaoBox.getCol() * getGRID_SIZE() + 2;
        int currentY = CaoCaoBox.getRow() * getGRID_SIZE() + 2;
        if (currentX == targetLocationX && currentY == targetLocationY)
        {
            System.out.println("Game win");
            CaoCaoBox.setColor(Color.RED);
            repaint();
            victoryInterface.getLabelGameWin().setText(String.format("Step: %d", this.getSteps()));
            victoryInterface.setVisible(true);
        }
    }

    //add
    // 添加界面刷新方法
    public void refresh() {
        this.revalidate();
        this.repaint();
    }


    // change the step counter
    public void setStepLabel(JLabel stepLabel) {
        this.stepLabel = stepLabel;
    }


    public void setController(GameController controller) {
        this.controller = controller;
    }

    public BoxComponent getSelectedBox() {
        return selectedBox;
    }

    public void setSteps(int steps)
    {
        this.steps = steps;
    }

    public int getSteps()
    {
        return steps;
    }

    public int getGRID_SIZE() {
        return GRID_SIZE;
    }

    public List<BoxComponent> getBoxes()
    {
        return boxes;
    }

    public VictoryInterface getVictoryInterface()
    {
        return victoryInterface;
    }

    public MapModel getModel()
    {
        return model;
    }

    public ArrayList<int[][]> getStates()
    {
        return states;
    }

    public JLabel getStepLabel()
    {
        return stepLabel;
    }
}
