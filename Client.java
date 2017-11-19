
import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.function.Consumer;
import java.awt.event.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Client extends NetworkConnection{

	private String ip;
	private int  port;
	
	public Client(String ip, int port, Consumer<Serializable> onReceiveCallback) {
		super(onReceiveCallback);
	this.ip = ip;
	this.port = port;
	}

	@Override
	protected boolean isServer() {
	
		return false;
	}

	@Override
	protected String getIP() {
		
		return ip;
	}

	@Override
	protected int getPort() {
		
		return port;
	}

	
	
	
	 
	
}