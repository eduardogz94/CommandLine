package eagz.org;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread {
	public static final int PORT = 9006;
    public static ServerSocket ss = null; 
    public static Socket s = null;

	public static void main(String[] args) {
		try {
			ss = new ServerSocket(PORT);
			System.err.println("- -  Server - -");
				s = ss.accept();
				System.out.println("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
				System.out.println("NEW CONNECTION WORKING ON ADDRESS -> " + s.getInetAddress().getHostName());
				Thread conect = new ConexionServer(s);
				conect.start();
			
			
		} catch (IOException e) {	
			System.err.println("Port already in use.");
			e.printStackTrace();}
	}
}