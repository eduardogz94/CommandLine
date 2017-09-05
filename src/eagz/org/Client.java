package eagz.org;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {
	public static String path = "C:\\Users\\Eduardo\\Desktop\\Eduardo\\Uru\\Clases Programacion\\POO\\CLI\\cliente\\";
	private static Socket s;
	static int PORT = 9006;
	static String IP = "localhost"; 
	public static Scanner input = new Scanner (System.in);

	    	public static void main(String[] args) throws IOException {
	    	    try {
	    	        s = new Socket(IP ,PORT);
	    		while(s != null){
	    	        System.err.println("-- Client  --");
	    			System.out.println("Connecting to Server ->" +  IP  + "/" + PORT);
	    			System.out.println("Commands: "+" -> Create "+" -> Delete "+" -> Download "+" -> Upload");
	    			System.out.println("C:>");
	    			String inp = input.nextLine();
	    			ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
	    			oos.writeObject(inp);
	    			String[] cmds = inp.split(" ");
	    				switch (cmds[0]) {
	    						case "upload":
	    							upload(cmds[1]);
	    							break;
	    						case "download":
	    							download(cmds[1]);
	    							break;
	    					}
	    		}
	    			} catch (Exception e) { 
	    				e.printStackTrace();
	    		}
	    	}
	    	
	    	public   static void upload(String fileName) {
	    	    try {
	    	        File myFile = new File(path + fileName);
	    	        byte[] buffer = new byte[1024];
	    	        InputStream is = new FileInputStream(myFile);
	    	        OutputStream os = s.getOutputStream();
	    	        int count;
	    	        while ((count = is.read(buffer)) > 0){
	    	        	os.write(buffer, 0, count);
	    	        }
	    	        System.out.println("File "+fileName+" sent to Server.");
	    	        System.out.println(" ");
	    	        os.close();
	    	        is.close();
	    	    } catch (Exception e) {
	    	        System.err.println("File does not exist!");	
	    	        System.out.println(" ");
	    	    }
	    	}

	    	public  static void download(String fileName) {
	    	    try {
	    	    	File f = new File(path + fileName);
	    			byte[] buffer = new byte[1024];
	    	    	InputStream is = s.getInputStream();
	    	    	OutputStream fos = new FileOutputStream(f);
	    	    	int count;
	    	    	while ((count = is.read(buffer)) > 0){
	    	    		fos.write(buffer, 0, count);
	    	    	}
	    	    	fos.close();
	    	    	is.close();
	    	        System.out.println("File "+fileName+" received from Server.");	
	    	        System.out.println(" ");
	    	    } catch (Exception e) {   
	    	    	e.printStackTrace();
	    	    }
	    	}
	    }