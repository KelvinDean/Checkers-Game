import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.function.Consumer;

import javafx.scene.Node;

public abstract class NetworkConnection {
	private ConnectionThread connThread = new ConnectionThread();
	private Consumer<Serializable> onReceiveCallback;
	private int x;
	private int y;
	
	
	public NetworkConnection(Consumer<Serializable> onReceiveCallback)
	{
		this.x = x;
		this.y = y;
		this.onReceiveCallback = onReceiveCallback;
		Checker check;
		
		 
		connThread.setDaemon(true);
	}
	
	public void startConnection() throws Exception
	{
		connThread.start();
	}

	public void send(Serializable data,int x, int y) throws Exception
	{
		connThread.out.writeObject(data);
	}
	
	public void closeConnection() throws Exception
	{
		connThread.socket.close();
	}
	
	protected abstract boolean isServer();
	protected abstract String getIP();
	protected abstract int getPort();
	
	private class ConnectionThread extends Thread
	{
		private Socket socket;
		private ObjectOutputStream out;
		@Override
		public void run()
		{
			try(ServerSocket server = isServer() ? new ServerSocket(getPort()) : null;
					Socket socket = isServer() ? server.accept() : new Socket(getIP(),getPort());
					ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
					ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
					PrintStream numOut = new PrintStream(socket.getOutputStream());
					Scanner num1In = new Scanner(socket.getInputStream());
					Scanner num2In = new Scanner(socket.getInputStream()))
			{
				this.socket = socket;
				this.out = out;
				
				while(true)
				{
					Serializable data = (Serializable) in.readObject();
					x = num1In.nextInt();
					y = num2In.nextInt();
					onReceiveCallback.accept(data);
				}
			
				
			} catch(Exception e) 
				{
				onReceiveCallback.accept("Connection Closed");
				}
			
		}
	}

	
	
}
