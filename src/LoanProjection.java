import java.awt.event.*;
import java.sql.*;
import java.util.Vector;
import javax.swing.*;
import javax.swing.GroupLayout;
import javax.swing.table.DefaultTableModel;
/*
 * Created by JFormDesigner on Tue Aug 11 12:23:48 PDT 2020
 */

// GitHub link: https://github.com/cnbrian/CSIS2175_FinalExam

/**
 * @author Brian Baldueza
 */
public class LoanProjection extends JFrame {
    public LoanProjection() {
        initComponents();
    }

    private void btnAddActionPerformed(ActionEvent e) throws ClassNotFoundException, SQLException {
        // TODO add your code here

        String clientNumber, clientName;
        double loanAmount;
        int years;

        clientNumber = txtClientNumber.getText();
        clientName = txtClientName.getText();
        loanAmount = new Double(txtLoanAmount.getText());
        years = Integer.parseInt(txtYears.getText());

        PreparedStatement query;

        Class.forName("com.mysql.cj.jdbc.Driver");

        Connection con1 = DriverManager.getConnection("jdbc:mysql://localhost/loan", "root", "");

        // create the sql statement that will add the data
        query = con1.prepareStatement("insert into loantable values (?,?,?,?,?)");
        query.setString(1, clientNumber);
        query.setString(2, clientName);
        query.setDouble(3, loanAmount);
        query.setInt(4, years);

        String selectedValue = (String) cbxLoanType.getSelectedItem();

        query.setString(5, selectedValue);

        query.executeUpdate();

        JOptionPane.showMessageDialog(null, "Record added");

        txtClientNumber.setText("");
        txtClientName.setText("");
        txtLoanAmount.setText("");
        txtYears.setText("");

        UpdateTable();
    }

    private void table1MouseClicked(MouseEvent e) {
        // TODO add your code here

        DefaultTableModel df = (DefaultTableModel) table1.getModel();

        int index1 = table1.getSelectedRow();

        txtClientNumber.setText(df.getValueAt(index1, 0).toString());
        txtClientName.setText(df.getValueAt(index1, 1).toString());
        txtLoanAmount.setText(df.getValueAt(index1, 2).toString());
        txtYears.setText(df.getValueAt(index1, 3).toString());
        if (df.getValueAt(index1, 4).equals("Business")) {
            cbxLoanType.setSelectedIndex(0);
        } else {
            cbxLoanType.setSelectedIndex(1);
        }


    }

    private void btnEditActionPerformed(ActionEvent e) throws ClassNotFoundException, SQLException {
        // TODO add your code here

        String clientNumber, clientName;
        double loanAmount;
        int years;

        clientNumber = txtClientNumber.getText();
        clientName = txtClientName.getText();
        loanAmount = new Double(txtLoanAmount.getText());
        years = Integer.parseInt(txtYears.getText());

        PreparedStatement query;

        // load the JDBC driver
        Class.forName("com.mysql.cj.jdbc.Driver");

        // connect to the database
        Connection con1 = DriverManager.getConnection("jdbc:mysql://localhost/loan", "root", "");
        // select all the records
        query = con1.prepareStatement("update loantable set clientno = ?, clientname = ?, loanamount = ?, years = ? where clientno = ?");

        query.setString(1, clientNumber);
        query.setString(2, clientName);
        query.setDouble(3, loanAmount);
        query.setInt(4, years);

        String selectedValue = (String) cbxLoanType.getSelectedItem();

        query.setString(5, selectedValue);

        query.executeUpdate();

        JOptionPane.showMessageDialog(null, "Record edited");

        txtClientNumber.setText("");
        txtClientName.setText("");
        txtLoanAmount.setText("");
        txtYears.setText("");

        UpdateTable();
    }

    private void btnDeleteActionPerformed(ActionEvent e) throws ClassNotFoundException, SQLException {
        // TODO add your code here


        PreparedStatement query;

        String clientNumber = "";


        // load the JDBC driver
        Class.forName("com.mysql.cj.jdbc.Driver");

        // connect to the database
        Connection con1 = DriverManager.getConnection("jdbc:mysql://localhost/loan", "root", "");

        clientNumber = txtClientNumber.getText();

        int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete?", "Delete",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        if (result == JOptionPane.YES_OPTION) {

            query = con1.prepareStatement("delete from loantable where clientno =?");

            query.setString(1, clientNumber);
            query.executeUpdate();

            JOptionPane.showMessageDialog(null, "Record deleted");

        }

        txtClientNumber.setText("");
        txtClientName.setText("");
        txtLoanAmount.setText("");
        txtYears.setText("");

        UpdateTable();

    }

    private void table1MouseClicked2(MouseEvent e) throws ClassNotFoundException, SQLException {
        // TODO add your code here

        String[] cols = {"Payment", "Principal", "Interest", "Monthly Payment", "Balance"};

        String[][] data = {{"b1", "b2", "b3", "b4", "b5"}};

        DefaultTableModel model = new DefaultTableModel(data, cols);

        table2.setModel(model);

        PreparedStatement query;

        // load the JDBC driver
        Class.forName("com.mysql.cj.jdbc.Driver");

        // connect to the database
        Connection con1 = DriverManager.getConnection("jdbc:mysql://localhost/loan", "root", "");

        // select all the records
        query = con1.prepareStatement("select loanamount from loantable");

        ResultSet rs = query.executeQuery();

        int c;

        ResultSetMetaData res = rs.getMetaData();

        c = res.getColumnCount();

        DefaultTableModel bf = (DefaultTableModel) table2.getModel();

        bf.setRowCount(0);

        double loanAmount = Double.parseDouble(String.valueOf(txtLoanAmount));
        double rate;
        if (cbxLoanType.equals("Business")) {
            rate = 0.09;
        } else {
            rate = 0.06;
        }
        int years = Integer.parseInt(String.valueOf(txtYears));
        double monthlyPayment = loanAmount / (years * 12);
        double balance = loanAmount - monthlyPayment;
        double interest = balance * rate;
        double principal = monthlyPayment - interest;


        while (rs.next()) {

            Vector v2 = new Vector();

            for (int i = 1; i <= c; i++) {
                double days = 0;
                v2.add(days++);
                v2.add(principal);
                v2.add(interest);
                v2.add(loanAmount);
                v2.add(balance);

            }

            bf.addRow(v2);
        }

    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Brian Baldueza
        lblClientNumber = new JLabel();
        lblClientName = new JLabel();
        lblLoanAmount = new JLabel();
        lblYears = new JLabel();
        lblLoanType = new JLabel();
        txtClientNumber = new JTextField();
        txtClientName = new JTextField();
        txtLoanAmount = new JTextField();
        txtYears = new JTextField();
        cbxLoanType = new JComboBox<>();
        scrollPane1 = new JScrollPane();
        table1 = new JTable();
        scrollPane2 = new JScrollPane();
        table2 = new JTable();
        btnAdd = new JButton();
        btnEdit = new JButton();
        btnDelete = new JButton();
        lblPayment = new JLabel();
        txtPayment = new JTextField();

        //======== this ========
        var contentPane = getContentPane();

        //---- lblClientNumber ----
        lblClientNumber.setText("Enter the client number:");

        //---- lblClientName ----
        lblClientName.setText("Enter the client name:");

        //---- lblLoanAmount ----
        lblLoanAmount.setText("Enter the customer loan amount:");

        //---- lblYears ----
        lblYears.setText("Enter the number of years to pay:");

        //---- lblLoanType ----
        lblLoanType.setText("Enter the loan type:");

        //---- cbxLoanType ----
        cbxLoanType.setMaximumRowCount(2);
        cbxLoanType.setModel(new DefaultComboBoxModel<>(new String[]{
                "Business",
                "Personal"
        }));

        //======== scrollPane1 ========
        {

            //---- table1 ----
            table1.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    table1MouseClicked(e);
                    try {
                        table1MouseClicked2(e);
                    } catch (ClassNotFoundException classNotFoundException) {
                        classNotFoundException.printStackTrace();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            });
            scrollPane1.setViewportView(table1);
        }

        //======== scrollPane2 ========
        {
            scrollPane2.setViewportView(table2);
        }

        //---- btnAdd ----
        btnAdd.setText("Add");
        btnAdd.addActionListener(e -> {
            try {
                btnAddActionPerformed(e);
            } catch (ClassNotFoundException classNotFoundException) {
                classNotFoundException.printStackTrace();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });

        //---- btnEdit ----
        btnEdit.setText("Edit");
        btnEdit.addActionListener(e -> {
            try {
                btnEditActionPerformed(e);
            } catch (ClassNotFoundException classNotFoundException) {
                classNotFoundException.printStackTrace();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });

        //---- btnDelete ----
        btnDelete.setText("Delete");
        btnDelete.addActionListener(e -> {
            try {
                btnDeleteActionPerformed(e);
            } catch (ClassNotFoundException classNotFoundException) {
                classNotFoundException.printStackTrace();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });

        //---- lblPayment ----
        lblPayment.setText("Monthly Payment");

        //---- txtPayment ----
        txtPayment.setEditable(false);

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
                contentPaneLayout.createParallelGroup()
                        .addGroup(contentPaneLayout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addGroup(contentPaneLayout.createParallelGroup()
                                        .addGroup(contentPaneLayout.createSequentialGroup()
                                                .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                                        .addComponent(lblYears)
                                                        .addGroup(contentPaneLayout.createParallelGroup()
                                                                .addComponent(lblLoanAmount)
                                                                .addComponent(lblClientName)
                                                                .addComponent(lblClientNumber))
                                                        .addComponent(lblLoanType, GroupLayout.Alignment.LEADING))
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGroup(contentPaneLayout.createParallelGroup()
                                                        .addComponent(cbxLoanType, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 427, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(txtYears, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 427, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(txtLoanAmount, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 427, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(txtClientName, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 427, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(txtClientNumber, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 427, GroupLayout.PREFERRED_SIZE)))
                                        .addGroup(contentPaneLayout.createSequentialGroup()
                                                .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                                        .addGroup(contentPaneLayout.createSequentialGroup()
                                                                .addComponent(btnAdd, GroupLayout.PREFERRED_SIZE, 121, GroupLayout.PREFERRED_SIZE)
                                                                .addGap(26, 26, 26)
                                                                .addComponent(btnEdit, GroupLayout.PREFERRED_SIZE, 121, GroupLayout.PREFERRED_SIZE)
                                                                .addGap(27, 27, 27)
                                                                .addComponent(btnDelete, GroupLayout.PREFERRED_SIZE, 121, GroupLayout.PREFERRED_SIZE))
                                                        .addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, 416, GroupLayout.PREFERRED_SIZE))
                                                .addGap(18, 18, 18)
                                                .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(scrollPane2, GroupLayout.PREFERRED_SIZE, 421, GroupLayout.PREFERRED_SIZE)
                                                        .addGroup(contentPaneLayout.createSequentialGroup()
                                                                .addComponent(lblPayment)
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addComponent(txtPayment, GroupLayout.PREFERRED_SIZE, 240, GroupLayout.PREFERRED_SIZE)))))
                                .addContainerGap(27, Short.MAX_VALUE))
        );
        contentPaneLayout.setVerticalGroup(
                contentPaneLayout.createParallelGroup()
                        .addGroup(contentPaneLayout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblClientNumber, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtClientNumber, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblClientName)
                                        .addComponent(txtClientName, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE))
                                .addGap(10, 10, 10)
                                .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblLoanAmount)
                                        .addComponent(txtLoanAmount, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblYears)
                                        .addComponent(txtYears, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblLoanType)
                                        .addComponent(cbxLoanType, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addComponent(scrollPane2, GroupLayout.DEFAULT_SIZE, 332, Short.MAX_VALUE)
                                        .addComponent(scrollPane1, GroupLayout.DEFAULT_SIZE, 332, Short.MAX_VALUE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnAdd)
                                        .addComponent(btnDelete)
                                        .addComponent(btnEdit)
                                        .addComponent(lblPayment)
                                        .addComponent(txtPayment, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(16, Short.MAX_VALUE))
        );
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Brian Baldueza
    private JLabel lblClientNumber;
    private JLabel lblClientName;
    private JLabel lblLoanAmount;
    private JLabel lblYears;
    private JLabel lblLoanType;
    private JTextField txtClientNumber;
    private JTextField txtClientName;
    private JTextField txtLoanAmount;
    private JTextField txtYears;
    private JComboBox<String> cbxLoanType;
    private JScrollPane scrollPane1;
    private JTable table1;
    private JScrollPane scrollPane2;
    private JTable table2;
    private JButton btnAdd;
    private JButton btnEdit;
    private JButton btnDelete;
    private JLabel lblPayment;
    private JTextField txtPayment;
    // JFormDesigner - End of variables declaration  //GEN-END:variables

    public void Prepare() {

        String[] cols = {"Number", "Name", "Amount", "Years", "Type of Loan"};

        String[][] data = {{"b1", "b2", "b3", "b4", "b5"}};

        DefaultTableModel model = new DefaultTableModel(data, cols);

        table1.setModel(model);

    }

    public void UpdateTable() throws ClassNotFoundException, SQLException {

        PreparedStatement query;

        // load the JDBC driver
        Class.forName("com.mysql.cj.jdbc.Driver");

        // connect to the database
        Connection con1 = DriverManager.getConnection("jdbc:mysql://localhost/loan", "root", "");

        // select all the records
        query = con1.prepareStatement("select * from loantable");


        ResultSet rs = query.executeQuery();

        int c;

        ResultSetMetaData res = rs.getMetaData();

        c = res.getColumnCount();

        DefaultTableModel bf = (DefaultTableModel) table1.getModel();

        bf.setRowCount(0);

        while (rs.next()) {

            Vector v2 = new Vector();

            for (int i = 1; i <= c; i++) {
                v2.add(rs.getString("clientno"));
                v2.add(rs.getString("clientname"));
                v2.add(rs.getDouble("loanamount"));
                v2.add(rs.getInt("years"));
                v2.add(rs.getString("loantype"));
            }

            bf.addRow(v2);
        }

    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        // write your code here

        LoanProjection form = new LoanProjection();
        form.setTitle("Loan Projection");
        form.setVisible(true);
        form.Prepare();
        form.UpdateTable();
        form.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
}
