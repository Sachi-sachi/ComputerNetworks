 import javax.swing.*;
 import java.awt.*;
 import java.awt.event.ActionEvent;
 import java.awt.event.ActionListener;
 public class DifferentialManchesterEncoding extends JFrame {
 private JTextField inputField;
 private JPanel graphPanel;
 public DifferentialManchesterEncoding() {
 setTitle("Differential Manchester Encoding");
 setSize(800, 500);
 setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 setLocationRelativeTo(null);
// Input Panel
 JPanel inputPanel = new JPanel();
 inputPanel.add(new JLabel("Enter binary sequence:"));
 inputField = new JTextField(20);
 inputPanel.add(inputField);
 JButton drawButton = new JButton("Draw Differential Manchester");
 inputPanel.add(drawButton);
 // Graph Panel
 graphPanel = new JPanel() {
 @Override
 protected void paintComponent(Graphics g) {
 super.paintComponent(g);
 drawAxes(g);
 drawDifferentialManchesterSignal(g);
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
 int centerY = height / 2;
 // Draw X and Y axis
 g.drawLine(50, centerY, width-50, centerY); // X-axis (0 level)
 g.drawLine(50, 50, 50, height-50); // Y-axis
// Label X-axis
 g.setColor(Color.BLACK);
 g.drawString("Time", width / 2, height-20);
 }
 private void drawDifferentialManchesterSignal(Graphics g) {
 String binaryInput = inputField.getText();
 if (binaryInput == null || binaryInput.isEmpty()) {
 return;
 }
 int x = 50;
 int y = graphPanel.getHeight() / 2;
 int step = 40; // Each bit is represented by two transitions, so
 the step is halved for each part
 boolean lastLevelHigh = true; // Assume starting at a high level
 for the first transition
 // Set colors and stroke for better visibility
 g.setColor(Color.BLUE);
 ((Graphics2D) g).setStroke(new BasicStroke(2));
 for (char bit : binaryInput.toCharArray()) {
 if (bit == '0') {
 // Transition at the start of the bit period
 lastLevelHigh = !lastLevelHigh;
 }
 int startY = lastLevelHigh ? y-50 : y + 50;
 g.drawLine(x, startY, x + step, startY);
 // Transition in the middle of the bit period
 lastLevelHigh = !lastLevelHigh;
 int midY = lastLevelHigh ? y-50 : y + 50;
 g.drawLine(x + step, startY, x + step, midY);
 g.drawLine(x + step, midY, x + 2 * step, midY);
 x += 2 * step; // Move to the next bit
 }
// Draw grid lines for clarity
 g.setColor(Color.LIGHT_GRAY);
 for (int i = 50; i < graphPanel.getWidth()- 50; i += step * 2) {
 g.drawLine(i, 50, i, graphPanel.getHeight()- 50);
 }
 }
 public static void main(String[] args) {
 SwingUtilities.invokeLater(new Runnable() {
 @Override
 public void run() {
 new DifferentialManchesterEncoding().setVisible(true);
 }
 });
 }
}
