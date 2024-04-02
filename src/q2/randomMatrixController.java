package q2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class randomMatrixController {

    private static final int BORDER_WIDTH = 2;
    private static final int SQUARE_SIZE = 10;

    // CUSTOMIZABLE
    private static final int SQUARE_COUNT_ROW = 50;
    private static final int SQUARE_COUNT_COL = 30;
    private static final int BUTTON_HEIGHT = 30;

    @FXML
    private VBox container;

    @FXML
    private Button fillButton;

    @FXML
    private Canvas matrixCanvas;

    private GraphicsContext gc;

    public void initialize() {
        setComponentsHeights();

        gc = matrixCanvas.getGraphicsContext2D();
        drawGrid();
    }
    private void setComponentsHeights() {
        // each square has a left border, and the last square also has a right border
        matrixCanvas.setHeight(SQUARE_COUNT_COL * (SQUARE_SIZE + BORDER_WIDTH) + BORDER_WIDTH);
        // each square has a top border, and the last square has a bottom border
        matrixCanvas.setWidth(SQUARE_COUNT_ROW * (SQUARE_SIZE + BORDER_WIDTH) + BORDER_WIDTH);

        fillButton.setPrefHeight(BUTTON_HEIGHT);
        container.setPrefHeight(BUTTON_HEIGHT + matrixCanvas.getHeight());
        container.setPrefWidth(matrixCanvas.getWidth());
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

    @FXML
    void onRandomFill(ActionEvent event) {
        gc.fillRect(100, 100, 200, 200);
    }

}
