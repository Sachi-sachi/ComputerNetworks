import javax.swing.*;
 import java.awt.*;
 import java.awt.event.ActionEvent;
 import java.awt.event.ActionListener;
 public class NRZI extends JFrame {
 private JTextField inputField;
 private JPanel graphPanel;
 public NRZI() {
 setTitle("Inverted NRZ");
 setSize(800, 500);
 setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 setLocationRelativeTo(null);
 // Input Panel
 JPanel inputPanel = new JPanel();
 inputPanel.add(new JLabel("Enter binary sequence:"));
 inputField = new JTextField(20);
 inputPanel.add(inputField);
 JButton drawButton = new JButton("Draw NRZ");
 inputPanel.add(drawButton);
 // Graph Panel
 graphPanel = new JPanel() {
 @Override
 protected void paintComponent(Graphics g) {
 super.paintComponent(g);
 drawAxes(g);
 drawNRZSignal(g);
 }
 };
 graphPanel.setBackground(Color.WHITE);
 // Add panels to frame
 add(inputPanel, BorderLayout.NORTH);
 add(graphPanel, BorderLayout.CENTER);
// Button Action
 drawButton.addActionListener(new ActionListener() {
 @Override
 public void actionPerformed(ActionEvent e) {
 graphPanel.repaint();
 }
 });
 }
 private void drawAxes(Graphics g) {
 int width = graphPanel.getWidth();
 int height = graphPanel.getHeight();
 // Draw X and Y axis
 g.drawLine(50, height / 2, width-50, height / 2); // X-axis
 g.drawLine(50, 50, 50, height-50); // Y-axis
 // Label X-axis
 g.drawString("Time", width / 2, height-20);
 // Label Y-axis
 g.drawString("Amplitude", 10, height / 2-60);
 g.drawString("0", 30, height / 2-50);
 g.drawString("1", 30, height / 2 + 50);
 }
 private void drawNRZSignal(Graphics g) {
 String binaryInput = inputField.getText();
 if (binaryInput == null || binaryInput.isEmpty()) {
 return;
 }
 int x = 50;
 int y = graphPanel.getHeight() / 2;
 int step = 40;
 // Set colors and stroke for better visibility
 g.setColor(Color.BLUE);
 ((Graphics2D) g).setStroke(new BasicStroke(2));
for (char bit : binaryInput.toCharArray()) {
 int yLevel = bit == '0' ? y-50 : y + 50;
 g.drawLine(x, yLevel, x + step, yLevel);
 g.drawLine(x + step, yLevel, x + step, y);
 x += step;
 }
 // Draw grid lines for clarity
 g.setColor(Color.LIGHT_GRAY);
 for (int i = 50; i < graphPanel.getWidth()-50; i += step) {
 g.drawLine(i, 50, i, graphPanel.getHeight()-50);
 }
 }
 public static void main(String[] args) {
 SwingUtilities.invokeLater(new Runnable() {
 @Override
 public void run() {
 new NRZI().setVisible(true);
 }
 });
 }
 }
