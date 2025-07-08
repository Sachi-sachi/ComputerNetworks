 import java.util.Random;
 import java.util.concurrent.TimeUnit;
 public class SlidingWindowProtocols {
 private static final int WINDOW_SIZE = 4;
 private static final int MAX_FRAME = 10;
 private static final int TIMEOUT = 2; // Timeout period in seconds
 public static void main(String[] args) {
 // Simulate Go-Back-N Protocol
 System.out.println("Simulating Go-Back-N Protocol...");
 goBackNProtocol();
 // Simulate Selective Repeat Protocol
 System.out.println("\nSimulating Selective Repeat Protocol...");
 selectiveRepeatProtocol();
 }
 private static void goBackNProtocol() {
 boolean[] ack = new boolean[MAX_FRAME];
 int base = 0;
 int nextFrameToSend = 0;
Random random = new Random();
 while (base < MAX_FRAME) {
 while (nextFrameToSend < base + WINDOW_SIZE && nextFrameToSend
 < MAX_FRAME) {
 System.out.println("Go-Back-N Sender: Sending frame: " +
 nextFrameToSend);
 // Simulate sending frame (you would have actual network
 code here)
 nextFrameToSend++;
 }
 // Simulate receiving an acknowledgment
 try {
 TimeUnit.SECONDS.sleep(TIMEOUT);
 int ackFrame = base + random.nextInt(WINDOW_SIZE); //
 Simulate random ack
 System.out.println("Go-Back-N Receiver: Received ack for
 frame: " + ackFrame);
 base = ackFrame + 1;
 } catch (InterruptedException e) {
 e.printStackTrace();
 }
 }
 }
 private static void selectiveRepeatProtocol() {
 boolean[] ack = new boolean[MAX_FRAME];
 int nextFrameToSend = 0;
 Random random = new Random();
 while (nextFrameToSend < MAX_FRAME) {
 // Send frames within the window
 for (int i = nextFrameToSend; i < nextFrameToSend +
 WINDOW_SIZE && i < MAX_FRAME; i++) {
 if (!ack[i]) {
 System.out.println("Selective Repeat Sender: Sending
 frame: " + i);
 // Simulate sending frame (you would have actual
 network code here)
}
 }
 // Simulate receiving an acknowledgment
 try {
 TimeUnit.SECONDS.sleep(TIMEOUT);
 for (int i = nextFrameToSend; i < nextFrameToSend +
 WINDOW_SIZE && i < MAX_FRAME; i++) {
 if (random.nextBoolean()) { // Simulate random ack
 ack[i] = true;
 System.out.println("Selective Repeat Receiver:
 Received ack for frame: " + i);
 }
 }
 // Move window forward
 while (nextFrameToSend < MAX_FRAME &&
 ack[nextFrameToSend]) {
 nextFrameToSend++;
 }
 } catch (InterruptedException e) {
 e.printStackTrace();
 }
 }
 }
 }
