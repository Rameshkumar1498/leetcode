import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.SocketException;
public class ClientThread extends Thread 
{

	private Socket socket;
	private BufferedReader input;

	public ClientThread(Socket s) throws IOException
	{
		this. socket=s;

		 this. input = new BufferedReader( new InputStreamReader(socket. getInputStream()));

	}
	public void run() 
	{
	String  response="";
			
		try 
		{
			while(true)
			{
				 
				if(input.readLine()!=null){
				response = input.readLine();
				System.out.println(response);
	
				}
					
				
			}
		}
		catch(SocketException e)
		{
			System.out.println("you are left to the chat");
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		finally
		{
			try
			{
				input.close();
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
		}
	}
}
