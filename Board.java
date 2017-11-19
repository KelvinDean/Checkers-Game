import javax.swing.*;
import java.awt.*;
import java.util.*;
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
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class Board extends Application 
{
   public static final int TILE_SIZE = 100;
   public static final int ROWS = 8;
   public static final int COLUMNS = 8;
   private static Tile[][] board = new Tile[ROWS][COLUMNS];
   private Group tileGroup = new Group();
   private Group pieceGroup = new Group();
   public static int sendX = 0;
   public static int sendY = 0;
   public static int recX = 0;
   public static int recY = 0;
   static Scanner scan;
   public static int temp1 = 0;
   public static int temp2 = 0;
    static Serializable sendO;
   static  Checker recO;
   public static boolean moved;
  public static boolean sendturn = true;
  public static boolean recTurn;
  private boolean isServer = false;
  private NetworkConnection connection = isServer ? createServer() : createClient();
  
   
   
   
   
   public Parent createContent() throws IOException{
       Pane root = new Pane();
       root.setPrefSize(ROWS * TILE_SIZE, COLUMNS * TILE_SIZE);
       root.getChildren().addAll(tileGroup, pieceGroup);
       

       for (int y = 0; y < COLUMNS; y++) {
           for (int x = 0; x < ROWS; x++) {
               Tile tile = new Tile((x + y) % 2 == 0, x, y);
               board[x][y] = tile;

               tileGroup.getChildren().add(tile);

               Checker check = null;

               if (y <= 2 && (x + y) % 2 != 0) {
                   check = makeCheck(CheckerType.GREEN, x, y);
               }

               if (y >= 5 && (x + y) % 2 != 0) {
                   check = makeCheck(CheckerType.YELLOW, x, y);
               }

               if (check != null) {
                   tile.setChecker(check);
                   pieceGroup.getChildren().add(check);
               }
           }
       }

       return root;
   }
   
   
   private static Move tryMove( Checker check, int newX, int newY )
   {
      //if the tile is occupied or is a white tile (unoccupiable)
      //invalid move
      if( board[newX][newY].hasChecker() || (newX+newY)%2 == 0 )
         return new Move(MoveType.NONE);
      
      int x = toBoard(check.getOldX());
      int y = toBoard(check.getOldY());
      
      if( Math.abs( newX-x )==1 && ( newY-y ) == check.getType().direction )
         return new Move(MoveType.REGULAR);
      else if( Math.abs( newX-x )==2 && ( newY-y ) == check.getType().direction*2 )
      {
         int xx = x+(newX-x)/2;
         int yy = y+(newY-y)/2;
         
         if( board[xx][yy].hasChecker() && board[xx][yy].getChecker().getType() != check.getType())
            return new Move(MoveType.JUMP, board[xx][yy].getChecker());
      }
      
      return new Move(MoveType.NONE);
   }
   
   private static int toBoard(double pixel) {
       return (int)(pixel + TILE_SIZE / 2) / TILE_SIZE;
   }
   
   @Override
   public void init() throws Exception
   {
	   connection.startConnection();
   }
   @Override
   public void start(Stage primaryStage1) throws Exception, IOException,ClassNotFoundException,UnknownHostException {
    
	   Scene scene = new Scene(createContent());
	 
       primaryStage1.setTitle("Client Side");
       primaryStage1.setScene(scene);
       primaryStage1.show();   
       
   }
   
   
   
   
   
   private Checker makeCheck( CheckerType type, int x, int y) throws IOException
   {
	   
      Checker check = new Checker( type, x, y );
      check.setOnMouseReleased(e->{
    	  	  
     
      int x1 = ((int)check.getLayoutX() + TILE_SIZE/2)/TILE_SIZE;
      int y1 = ((int)check.getLayoutY() + TILE_SIZE/2)/TILE_SIZE;

      Move move;
      
      if( x1 < 0 || y1 < 0 || x1 >= COLUMNS || y1 >= ROWS )
         move = new Move(MoveType.NONE);
      else
         move = tryMove( check, x1, y1 );
         
      int xx = ((int)check.getOldX() + TILE_SIZE/2)/TILE_SIZE;
      int yy = ((int)check.getOldY() + TILE_SIZE/2)/TILE_SIZE;
      
      if(move.getType().equals("REGULAR") || move.getType().equals("JUMP"))
      {
    	  moved = true;
    	 
      }else
      {
    	  
    	  moved = false;
    	  
      }
      
      switch( move.getType() )
      {
         case NONE:
            check.abortMove();
            break;
         case REGULAR:
            check.move( x1, y1 );
            board[xx][yy].setChecker(null);
            board[x1][y1].setChecker(check);
            break;
         case JUMP:
            check.move( x1, y1 );
            board[xx][yy].setChecker(null);
            board[x1][y1].setChecker(check);
            
            Checker otherChecker = move.getChecker();
            board[toBoard(otherChecker.getOldX())][toBoard(otherChecker.getOldY())].setChecker(null);
            pieceGroup.getChildren().remove(otherChecker);
            break;
           
            
            
      }
      
      sendX = x1;
      sendY = y1;
     
      
     
      
    });sendO = check; 
    
    
	
	
      return check;
      
   }   
   
   public Server createServer()
   {
	   return new Server(1455, data ->
	   {
		   Platform.runLater(()->
		   {
			sendO = data;
		   });
	   });
   }
   
   private Client createClient()
   {
	   return new Client("127.0.0.1", 1445, data ->
	   {
		   Platform.runLater(() ->
		   {
			   
			   
		   });
	   });
   
   }
   public static void main(String [] args)
   {
	   launch(args);
   }
	 
   
   
  


}
