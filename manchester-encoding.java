 import javax.swing.*;
 import java.awt.*;
 import java.awt.event.ActionEvent;
 import java.awt.event.ActionListener;
 public class ManchesterEncoding extends JFrame {
 private JTextField inputField;
 private JPanel graphPanel;
 public ManchesterEncoding() {
 setTitle("Manchester Encoding");
 setSize(800, 500);
 setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 setLocationRelativeTo(null);
 // Input Panel
 JPanel inputPanel = new JPanel();
 inputPanel.add(new JLabel("Enter binary sequence:"));
 inputField = new JTextField(20);
 inputPanel.add(inputField);
 JButton drawButton = new JButton("Draw Manchester");
 inputPanel.add(drawButton);
 // Graph Panel
 graphPanel = new JPanel() {
 @Override
 protected void paintComponent(Graphics g) {
 super.paintComponent(g);
 drawAxes(g);
 drawManchesterSignal(g);
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
 private void drawManchesterSignal(Graphics g) {
 String binaryInput = inputField.getText();
 if (binaryInput == null || binaryInput.isEmpty()) {
 return;
 }
 int x = 50;
 int y = graphPanel.getHeight() / 2;
 int step = 40; // Each bit is represented by two transitions, so
 the step is halved for each part
 // Set colors and stroke for better visibility
 g.setColor(Color.BLUE);
 ((Graphics2D) g).setStroke(new BasicStroke(2));
for (char bit : binaryInput.toCharArray()) {
 if (bit == '0') {
 // Low to High transition for '0'
 g.drawLine(x, y + 50, x + step, y + 50);
 g.drawLine(x + step, y + 50, x + step, y-50);
 g.drawLine(x + step, y-50, x + 2 * step, y-50);
 } else if (bit == '1') {
 // High to Low transition for '1'
 g.drawLine(x, y-50, x + step, y-50);
 g.drawLine(x + step, y-50, x + step, y + 50);
 g.drawLine(x + step, y + 50, x + 2 * step, y + 50);
 }
 x += 2 * step; // Move to the next bit
 }
 // Draw grid lines for clarity
 g.setColor(Color.LIGHT_GRAY);
 for (int i = 50; i < graphPanel.getWidth()-50; i += step * 2) {
 g.drawLine(i, 50, i, graphPanel.getHeight()-50);
 }
 }
 public static void main(String[] args) {
 SwingUtilities.invokeLater(new Runnable() {
 @Override
 public void run() {
 new ManchesterEncoding().setVisible(true);
 }
 });
 }
 }
