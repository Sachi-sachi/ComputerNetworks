 Server:
 import java.io.*;
 import java.net.*;
 import java.util.Scanner;
 public class TCPServer {
 public static void main(String[] args) {
 try (ServerSocket serverSocket = new ServerSocket(1234)) {
 System.out.println("Server is listening on port 1234");
 while (true) {
 Socket socket = serverSocket.accept();
 System.out.println("New client connected");
 new ServerThread(socket).start();
 }
 } catch (IOException ex) {
 System.out.println("Server error: " + ex.getMessage());
 }
 }
 }
class ServerThread extends Thread {
 private Socket socket;
 public ServerThread(Socket socket) {
 this.socket = socket;
 }
 @Override
 public void run() {
 try (
 InputStream input = socket.getInputStream();
 BufferedReader reader = new BufferedReader(new
 InputStreamReader(input));
 OutputStream output = socket.getOutputStream();
 PrintWriter writer = new PrintWriter(output, true)
 ) {
 String command = reader.readLine();
 System.out.println("Command received: " + command);
 switch (command) {
 case "HELLO":
 writer.println("Hello, Client!");
 break;
 case "FILE_TRANSFER":
 sendFile(socket, writer);
 break;
 case "CALCULATOR_ARITHMETIC":
 performArithmeticCalculation(reader, writer);
 break;
 case "CALCULATOR_TRIGONOMETRY":
 performTrigonometricCalculation(reader, writer);
 break;
 default:
 writer.println("Invalid command");
 break;
}
 socket.close();
 } catch (IOException e) {
 System.out.println("Server exception: " + e.getMessage());
 }
 }
 private void sendFile(Socket socket, PrintWriter writer) throws
 IOException {
 File file = new File("sample.txt"); // example file
 writer.println("File size: " + file.length());
 try (FileInputStream fileIn = new FileInputStream(file);
 BufferedOutputStream out = new
 BufferedOutputStream(socket.getOutputStream())) {
 byte[] buffer = new byte[4096];
 int bytesRead;
 while ((bytesRead = fileIn.read(buffer)) > 0) {
 out.write(buffer, 0, bytesRead);
 }
 out.flush();
 }
 }
 private void performArithmeticCalculation(BufferedReader reader,
 PrintWriter writer) throws IOException {
 String operation = reader.readLine();
 double num1 = Double.parseDouble(reader.readLine());
 double num2 = Double.parseDouble(reader.readLine());
 double result;
 switch (operation) {
 case "ADD":
 result = num1 + num2;
 break;
 case "SUBTRACT":
 result = num1-num2;
 break;
 case "MULTIPLY":
result = num1 * num2;
 break;
 case "DIVIDE":
 result = num1 / num2;
 break;
 default:
 writer.println("Invalid operation");
 return;
 }
 writer.println("Result: " + result);
 }
 private void performTrigonometricCalculation(BufferedReader reader,
 PrintWriter writer) throws IOException {
 String operation = reader.readLine();
 double angle = Double.parseDouble(reader.readLine());
 double result;
 switch (operation) {
 case "SIN":
 result = Math.sin(Math.toRadians(angle));
 break;
 case "COS":
 result = Math.cos(Math.toRadians(angle));
 break;
 case "TAN":
 result = Math.tan(Math.toRadians(angle));
 break;
 default:
 writer.println("Invalid operation");
 return;
 }
 writer.println("Result: " + result);
 }
 }
 Client:
import java.io.*;
 import java.net.*;
 import java.util.Scanner;
 public class TCPClient {
 public static void main(String[] args) {
 try (Socket socket = new Socket("localhost", 1234)) {
 OutputStream output = socket.getOutputStream();
 PrintWriter writer = new PrintWriter(output, true);
 InputStream input = socket.getInputStream();
 BufferedReader reader = new BufferedReader(new
 InputStreamReader(input));
 Scanner scanner = new Scanner(System.in);
 System.out.println("Choose a command: 1) HELLO 2)
 FILE_TRANSFER 3) CALCULATOR_ARITHMETIC 4) CALCULATOR_TRIGONOMETRY");
 String command = scanner.nextLine();
 writer.println(command);
 switch (command) {
 case "HELLO":
 System.out.println("Server response: " +
 reader.readLine());
 break;
 case "FILE_TRANSFER":
 receiveFile(socket, reader);
 break;
 case "CALCULATOR_ARITHMETIC":
 System.out.print("Enter operation (ADD, SUBTRACT,
 MULTIPLY, DIVIDE): ");
 writer.println(scanner.nextLine());
 System.out.print("Enter first number: ");
 writer.println(scanner.nextLine());
 System.out.print("Enter second number: ");
 writer.println(scanner.nextLine());
 System.out.println("Server response: " +
 reader.readLine());
break;
 case "CALCULATOR_TRIGONOMETRY":
 System.out.print("Enter operation (SIN, COS, TAN): ");
 writer.println(scanner.nextLine());
 System.out.print("Enter angle: ");
 writer.println(scanner.nextLine());
 System.out.println("Server response: " +
 reader.readLine());
 break;
 default:
 System.out.println("Invalid command");
 break;
 }
 } catch (IOException ex) {
 System.out.println("Client error: " + ex.getMessage());
 }
 }
 private static void receiveFile(Socket socket, BufferedReader reader)
 throws IOException {
 System.out.println(reader.readLine()); // file size info
 try (BufferedOutputStream fileOut = new BufferedOutputStream(new
 FileOutputStream("received_sample.txt"))) {
 byte[] buffer = new byte[4096];
 int bytesRead;
 InputStream in = socket.getInputStream();
 while ((bytesRead = in.read(buffer)) > 0) {
 fileOut.write(buffer, 0, bytesRead);
 }
 }
 System.out.println("File received successfully.");
 }
 }
