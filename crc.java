 import java.util.Scanner;
 public class CRC {
 public static void main(String[] args) {
 Scanner scanner = new Scanner(System.in);
 // Input frame size and frame
 System.out.println("Enter frame size:");
 int frameSize = scanner.nextInt();
 System.out.println("Enter the frame:");
 int[] frame = new int[frameSize];
 for (int i = 0; i < frameSize; i++) {
 frame[i] = scanner.nextInt();
 }
 // Input generator size and generator polynomial
 System.out.println("Enter generator size:");
 int generatorSize = scanner.nextInt();
 System.out.println("Enter the generator:");
 int[] generator = new int[generatorSize];
 for (int i = 0; i < generatorSize; i++) {
 generator[i] = scanner.nextInt();
 }
 // Calculate the number of zeros to be appended
 int[] appendedFrame = new int[frameSize + generatorSize-1];
System.arraycopy(frame, 0, appendedFrame, 0, frameSize);
 System.out.println("Message after appending " + (generatorSize
1) + " zeros:");
 displayFrame(appendedFrame);
 // Calculate CRC
 int[] crc = calculateCRC(appendedFrame, generator);
 System.out.println("CRC bits:");
 displayFrame(crc);
 // Transmitted frame (original frame + CRC bits)
 int[] transmittedFrame = new int[frameSize + crc.length];
 System.arraycopy(frame, 0, transmittedFrame, 0, frameSize);
 System.arraycopy(crc, 0, transmittedFrame, frameSize, crc.length);
 System.out.println("Transmitted frame:");
 displayFrame(transmittedFrame);
 // Simulate receiver side
 System.out.println("Receiver side:");
 System.out.println("Enter the received frame:");
 int[] receivedFrame = new int[frameSize + crc.length];
 for (int i = 0; i < receivedFrame.length; i++) {
 receivedFrame[i] = scanner.nextInt();
 }
 // Check the received frame
 int[] remainder = calculateCRC(receivedFrame, generator);
 System.out.println("Remainder after division:");
 displayFrame(remainder);
 boolean isCorrect = true;
 for (int value : remainder) {
 if (value != 0) {
 isCorrect = false;
 break;
 }
 }
 if (isCorrect) {
System.out.println("The received message is correct.");
 } else {
 System.out.println("The received message is incorrect.");
 }
 }
 // Method to calculate CRC
 public static int[] calculateCRC(int[] frame, int[] generator) {
 int[] tempFrame = frame.clone();
 for (int i = 0; i < frame.length-generator.length + 1; i++) {
 if (tempFrame[i] == 1) {
 for (int j = 0; j < generator.length; j++) {
 tempFrame[i + j] ^= generator[j];
 }
 }
 }
 int[] crc = new int[generator.length-1];
 System.arraycopy(tempFrame, tempFrame.length-crc.length, crc, 0,
 crc.length);
 return crc;
 }
 // Method to display frame
 public static void displayFrame(int[] frame) {
 for (int bit : frame) {
 System.out.print(bit);
 }
 System.out.println();
 }
 
