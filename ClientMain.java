import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.OutputStream;
import java.io.InputStreamReader;

public class ClientMain
{
	public static void main(String[] args)
	{
		try (Socket socket = new Socket("localhost", 5000))
		{

			BufferedReader input = new BufferedReader( new InputStreamReader(socket.getInputStream()));
			PrintWriter output = new PrintWriter(socket.getOutputStream(),true);
			Scanner scanner = new Scanner(System.in);
			String userInput;
			String response;
			String clientName = "empty";
			ClientThread clientThread = new ClientThread(socket);
			clientThread.start();
			do
			{
				if (clientName.equals("empty"))
				{
					System.out.println("Enter your name ");
					userInput = scanner.nextLine();
					clientName = userInput;
					output.println(userInput);
					if (userInput.equals("exit"))
					{
						break;
					}
				}

			else{
					String message = ( "(" + clientName + ")" + " message : " );
					System.out.println(message);
					userInput = scanner.nextLine();
					output.println(message + " " + userInput);
					if (userInput.equals("exit"))
					{

						break;	
					}
				}
			}
			while (!userInput.equals("exit"));
		}
		catch (Exception e)
		{
			System.out.println("Exception occured in client main: " + e.getStackTrace());
		}
	}
}
