package Fees;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

public class FeesManagementSystem extends JFrame {
    private Map<Integer, String> studentNames; // Simulated student names
    private Map<Integer, Double> studentFees; // Simulated student fees
    private Map<Integer, Double> studentPayments; // Simulated student payments

    private final double CONSTANT_FEES = 100000.0; // Constant fee amount (1 lakh)

    private JTable reportTable;

    public FeesManagementSystem() {
        setTitle("Fees Management System");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        studentNames = new HashMap<>();
        studentFees = new HashMap<>();
        studentPayments = new HashMap<>();

        // Components
        JButton addStudentButton = new JButton("Add Student");
        JButton makePaymentButton = new JButton("Make Payment");
        JButton generateReportButton = new JButton("Generate Report");
        reportTable = new JTable();

        // Add Student Button ActionListener
        addStudentButton.addActionListener(e -> {
            String studentIdStr = JOptionPane.showInputDialog("Enter Student ID:");
            try {
                int studentId = Integer.parseInt(studentIdStr);
                if (!studentFees.containsKey(studentId)) {
                    String studentName = JOptionPane.showInputDialog("Enter Student Name:");
                    if (studentName != null && !studentName.isEmpty()) {
                        studentNames.put(studentId, studentName);
                        studentFees.put(studentId, CONSTANT_FEES);
                        studentPayments.put(studentId, 0.0);
                        JOptionPane.showMessageDialog(null, "Student Added Successfully!");
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid Student Name!");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Student Already Exists!");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Invalid Input!");
            }
        });

        // Make Payment Button ActionListener
        makePaymentButton.addActionListener(e -> {
            String studentIdStr = JOptionPane.showInputDialog("Enter Student ID:");
            try {
                int studentId = Integer.parseInt(studentIdStr);
                if (studentFees.containsKey(studentId)) {
                    String paymentStr = JOptionPane.showInputDialog("Enter Payment Amount:");
                    try {
                        double paymentAmount = Double.parseDouble(paymentStr);
                        if (paymentAmount >= 0) {
                            double currentPayment = studentPayments.get(studentId);
                            currentPayment += paymentAmount;
                            studentPayments.put(studentId, currentPayment);

                            double currentFees = studentFees.get(studentId);
                            currentFees -= paymentAmount;
                            studentFees.put(studentId, currentFees);

                            JOptionPane.showMessageDialog(null, "Payment Recorded Successfully!");
                        } else {
                            JOptionPane.showMessageDialog(null, "Invalid Payment Amount!");
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Invalid Input!");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Student Not Found!");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Invalid Input!");
            }
        });

        // Generate Report Button ActionListener
        generateReportButton.addActionListener(e -> {
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("Student ID");
            model.addColumn("Student Name");
            model.addColumn("Total Fees");
            model.addColumn("Paid Amount");
            model.addColumn("Remaining Amount");

            for (int id : studentFees.keySet()) {
                String name = studentNames.get(id);
                double fees = studentFees.get(id);
                double paidAmount = studentPayments.getOrDefault(id, 0.0);
                double remainingAmount = fees - paidAmount;

                model.addRow(new Object[]{id, name, fees, paidAmount, remainingAmount});
            }

            reportTable.setModel(model);
        });

        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 0.2;
        gbc.anchor = GridBagConstraints.CENTER;

        gbc.insets = new Insets(10, 10, 10, 10); // Padding
        mainPanel.add(addStudentButton, gbc);

        gbc.gridx++;
        mainPanel.add(makePaymentButton, gbc);

        gbc.gridx++;
        mainPanel.add(generateReportButton, gbc);

        JScrollPane scrollPane = new JScrollPane(reportTable);

        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FeesManagementSystem system = new FeesManagementSystem();
            system.setVisible(true);
        });
    }
}
