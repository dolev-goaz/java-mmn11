package q2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.HashSet;
import java.util.Random;

public class randomMatrixController {

    private static final int BORDER_WIDTH = 2;
    private static final int SQUARE_SIZE = 10;
    private static final double FILL_RATIO = 0.1; // 10%

    // CUSTOMIZABLE
    private static final int SQUARE_COUNT_ROW = 30;
    private static final int SQUARE_COUNT_COL = 30;

    private static final Color SQUARE_COLOR = Color.BLACK;
    private static final Color GRID_COLOR = Color.BLACK;
    private static final Color BACKGROUND_COLOR = Color.WHITE;

    @FXML
    private Canvas matrixCanvas;

    private GraphicsContext gc;

    public void initialize() {
        setComponentsHeights();

        gc = matrixCanvas.getGraphicsContext2D();
        drawCanvas(); // initial draw
    }

    private void setComponentsHeights() {
        // each square has a left border, and the last square also has a right border
        matrixCanvas.setHeight(SQUARE_COUNT_COL * (SQUARE_SIZE + BORDER_WIDTH) + BORDER_WIDTH);
        // each square has a top border, and the last square has a bottom border
        matrixCanvas.setWidth(SQUARE_COUNT_ROW * (SQUARE_SIZE + BORDER_WIDTH) + BORDER_WIDTH);
    }

    private void clear() {
        // paint the entire screen
        gc.setFill(BACKGROUND_COLOR);
        gc.fillRect(0, 0, matrixCanvas.getWidth(), matrixCanvas.getHeight());
    }

    private void drawGrid() {
        gc.setStroke(GRID_COLOR);
        gc.setLineWidth(BORDER_WIDTH);

        double borderOffset = BORDER_WIDTH / 2.0;

        // vertical lines
        for (int colIndex = 0; colIndex <= SQUARE_COUNT_ROW; colIndex++) {
            double colX = colIndex * (SQUARE_SIZE + BORDER_WIDTH) + borderOffset;
            gc.moveTo(colX, 0);
            gc.lineTo(colX, matrixCanvas.getHeight());
            gc.stroke();
        }
        // horizontal lines
        for (int rowIndex = 0; rowIndex <= SQUARE_COUNT_COL; rowIndex++) {
            double rowY = rowIndex * (SQUARE_SIZE + BORDER_WIDTH) + borderOffset;
            gc.moveTo(0, rowY);
            gc.lineTo(matrixCanvas.getWidth(), rowY);
            gc.stroke();
        }
    }

    private void fillRandomly()  {
        Random random = new Random();
        // set containing all positions we randomly chose to fill
        HashSet<Integer> filledSquares = new HashSet<>();

        // the amount of squares we need to fill
        int fillCount = (int)(SQUARE_COUNT_ROW * SQUARE_COUNT_COL * FILL_RATIO);

        // while we haven't met the quota
        while (filledSquares.size() < fillCount) {
            // generate a new random position
            int position = random.nextInt(SQUARE_COUNT_COL * SQUARE_COUNT_ROW);
            if (filledSquares.contains(position)) {
                // if it's a position we already encountered- pass
                continue;
            }
            // add the position to the set, draw the square in that position
            filledSquares.add(position);
            this.fillSquare(position);
        }
    }

    private void fillSquare(int position) {
        // calculate grid coordinates based on the provided position
        int col = position % SQUARE_COUNT_ROW;
        int row = position / SQUARE_COUNT_ROW; // Rounded down(truncated)

        // convert from grid coordinates to canvas coordinates
        int x = col * (SQUARE_SIZE + BORDER_WIDTH) + BORDER_WIDTH;
        int y = row * (SQUARE_SIZE + BORDER_WIDTH) + BORDER_WIDTH;

        // draw
        gc.setFill(SQUARE_COLOR);
        gc.fillRect(x, y, SQUARE_SIZE, SQUARE_SIZE);
    }

    private void drawCanvas() {
        this.clear();
        this.drawGrid();
        this.fillRandomly();
    }

    @FXML
    void onRandomFill() {
        // no need for the event parameter since it isn't used
        drawCanvas();
    }

}
