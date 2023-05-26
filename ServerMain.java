import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class ServerMain
{


	public static void main(String[] args)
	{

		ArrayList<ServerThread> threadList = new ArrayList<>();

		try (ServerSocket serversocket = new ServerSocket(5000))
		{

			while(true)
			{

				Socket socket = serversocket.accept();
				ServerThread serverThread = new ServerThread(socket, threadList);
				//starting the thread
				threadList.add(serverThread);
				serverThread.start();


			}
		}
		catch (Exception e)
		{
			System.out.println("Error occured in main: " + e.getStackTrace());
		}
	}
}

