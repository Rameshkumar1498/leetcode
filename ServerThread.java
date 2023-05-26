import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ServerThread extends Thread
{
	private Socket socket;
	private ArrayList<ServerThread> threadList;
	private PrintWriter output;

	public ServerThread(Socket socket, ArrayList<ServerThread> threads)
	{
		this.socket = socket;
		this.threadList = threads;
	}
	public void run()
	{
		try
		{
			BufferedReader input = new BufferedReader( new InputStreamReader(socket.getInputStream()));

			output = new PrintWriter(socket.getOutputStream(),true);

			while(true)
			{
				String outputString = input. readLine();
				if(outputString.equals("exit"))
				{
					break;
				}
				printToAL1Clients(outputString);
				System.out.println("Server received " + outputString);

			}
		}
		catch (Exception e)
		{
		System.out.println("exited");
	 		//System.out.println("Error occured " +e.getStackTrace());

		}
	}
	private void printToAL1Clients(String outputString)
	{
		for( ServerThread sT: threadList)
		{
			sT.output.println(outputString);
		}
	}
}
