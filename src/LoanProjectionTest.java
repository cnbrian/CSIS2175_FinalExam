import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import static org.junit.jupiter.api.Assertions.*;

class LoanProjectionTest {

    private int cols;

    @org.junit.jupiter.api.Test
    void prepare() {
        int data = 0;
        DefaultTableModel model = new DefaultTableModel(data, cols);
    }

    @org.junit.jupiter.api.Test
    void updateTable() {
        AbstractButton table1 = null;
        DefaultTableModel bf = (DefaultTableModel) table1.getModel();
    }
}