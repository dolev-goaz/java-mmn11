package q2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
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

    @FXML
    private Button fillButton;

    @FXML
    private Canvas matrixCanvas;

    private GraphicsContext gc;

    public void initialize() {
        setComponentsHeights();

        gc = matrixCanvas.getGraphicsContext2D();
        drawCanvas();
    }

    private void setComponentsHeights() {
        // each square has a left border, and the last square also has a right border
        matrixCanvas.setHeight(SQUARE_COUNT_COL * (SQUARE_SIZE + BORDER_WIDTH) + BORDER_WIDTH);
        // each square has a top border, and the last square has a bottom border
        matrixCanvas.setWidth(SQUARE_COUNT_ROW * (SQUARE_SIZE + BORDER_WIDTH) + BORDER_WIDTH);
    }

    private void clear() {
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, matrixCanvas.getWidth(), matrixCanvas.getHeight());
    }

    private void drawGrid() {
        gc.setStroke(Color.GRAY);
        gc.setLineWidth(BORDER_WIDTH);

        double borderOffset = BORDER_WIDTH / 2.0;

        for (int colIndex = 0; colIndex <= SQUARE_COUNT_ROW; colIndex++) {
            double colX = colIndex * (SQUARE_SIZE + BORDER_WIDTH) + borderOffset;
            gc.moveTo(colX, 0);
            gc.lineTo(colX, matrixCanvas.getHeight());
            gc.stroke();
        }
        for (int rowIndex = 0; rowIndex <= SQUARE_COUNT_COL; rowIndex++) {
            double rowY = rowIndex * (SQUARE_SIZE + BORDER_WIDTH) + borderOffset;
            gc.moveTo(0, rowY);
            gc.lineTo(matrixCanvas.getWidth(), rowY);
            gc.stroke();
        }
    }

    private void fillRandomly()  {
        Random random = new Random();
        HashSet<Integer> filledSquares = new HashSet<>();
        int fillCount = (int)(SQUARE_COUNT_ROW * SQUARE_COUNT_COL * FILL_RATIO);
        while (filledSquares.size() < fillCount) {
            int position = random.nextInt(SQUARE_COUNT_COL * SQUARE_COUNT_ROW);
            if (filledSquares.contains(position)) {
                continue;
            }
            filledSquares.add(position);
            this.fillSquare(position);
        }
    }

    private void fillSquare(int position) {
        int col = position % SQUARE_COUNT_ROW;
        int row = (int)(position / SQUARE_COUNT_ROW);

        int x = col * (SQUARE_SIZE + BORDER_WIDTH) + BORDER_WIDTH;
        int y = row * (SQUARE_SIZE + BORDER_WIDTH) + BORDER_WIDTH;
        gc.setFill(Color.LIGHTBLUE);
        gc.fillRect(x, y, SQUARE_SIZE, SQUARE_SIZE);
    }

    private void drawCanvas() {
        this.clear();
        this.drawGrid();
        this.fillRandomly();
    }

    @FXML
    void onRandomFill(ActionEvent event) {
        drawCanvas();
    }

}
