package bmi;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BMICalculator extends JFrame {
    private JTextField nameField, weightField, heightField;

    public BMICalculator() {
        setTitle("BMI Calculator");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(4, 2, 5, 10)); // Added one row for the name

        JLabel nameLabel = new JLabel("Name:");
        nameField = new JTextField();
        JLabel weightLabel = new JLabel("Weight (kg):");
        weightField = new JTextField();
        JLabel heightLabel = new JLabel("Height (cm):");
        heightField = new JTextField();

        inputPanel.add(nameLabel);
        inputPanel.add(nameField);
        inputPanel.add(weightLabel);
        inputPanel.add(weightField);
        inputPanel.add(heightLabel);
        inputPanel.add(heightField);

        JButton calculateButton = new JButton("Calculate");
        JButton resetButton = new JButton("Reset");
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.add(calculateButton);
        buttonPanel.add(resetButton);

        add(inputPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateBMI();
            }
        });

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetFields();
            }
        });
    }

    private void calculateBMI() {
        try {
            String name = nameField.getText();
            double weight = Double.parseDouble(weightField.getText());
            double heightInCM = Double.parseDouble(heightField.getText());

            if (name.isEmpty() || weight <= 0 || heightInCM <= 0) {
                showError("Please enter valid information.");
                return;
            }

            double heightInMeters = heightInCM / 100;

            double bmi = weight / (heightInMeters * heightInMeters);

            String category;
            if (bmi < 18.5) {
                category = "Underweight";
            } else if (bmi < 25) {
                category = "Normal weight";
            } else if (bmi < 30) {
                category = "Overweight";
            } else {
                category = "Obese";
            }

            String result = String.format("%s, your BMI index is %.2f (%s)", name, bmi, category);
            showResultAsMessageBox(result);
        } catch (NumberFormatException ex) {
            showError("Please enter valid numbers.");
        }
    }

    private void resetFields() {
        nameField.setText("");
        weightField.setText("");
        heightField.setText("");
    }

    private void showResultAsMessageBox(String result) {
        JOptionPane.showMessageDialog(this, result, "BMI Result", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showError(String error) {
        JOptionPane.showMessageDialog(this, error, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
                    e.printStackTrace();
                }
                new BMICalculator().setVisible(true);
            }
        });
    }
}
