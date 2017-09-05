package eagz.org;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ConexionServer extends Thread{
	public static Socket s = null;
    public static String path = "C:\\Users\\Eduardo\\Desktop\\Eduardo\\Uru\\Clases Programacion\\POO\\CLI\\server\\";
    static Scanner input = new Scanner (System.in);
    
		ConexionServer(Socket s){
			ConexionServer.s = s; }
		
		public void run(){
			try{
				menu();
			} catch (Exception e) {
				e.printStackTrace();
				}	
		}

		public static void menu() {
			try {
				while(true){
				ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
				ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
				String msg = (String) ois.readObject();
				String[] cmds = msg.split(" ");
				switch(cmds[0]){
				
				case "create":
				File f = new File(cmds[1]);
					if(!f.exists()){
						create(cmds[1]);
						oos.writeObject(">> File Created");	}
					else{
						create(cmds[1]);
						oos.writeObject(">> File not created");	}
					break;
					
				case "delete":
					File f1 = new File(cmds[1]);
						delete(cmds[1]);
						if(!f1.exists()){
							oos.writeObject(" File Deleted ");	}	
						else{
							oos.writeObject(" File not found");	}
					break;

				case "download":
					download(cmds[1]);
					break;
					
				case "upload":
					upload(cmds[1]);
					break;
					
				default:
					System.out.println("Undefined Operation");
	    	        System.out.println(" ");
					oos.writeObject("Undefined Operation");
					break;
				}//case
				}
			}
			 catch (Exception e) {
				 e.printStackTrace();	}
		}
		
		
		public static void create (String filename){
			File f = new File(path + filename);
			try{if(!f.exists()){
				f.createNewFile();
				System.out.println(">> File Created");	
    	        System.out.println(" ");
			}
			else {
				System.err.println(">> File Already Exists");
    	        System.out.println(" ");
    	        }
			} 
			catch(Exception e){
				e.printStackTrace();}
		}//create
		
		
		public static void delete (String filename){
			File f = new File(path + filename);
			try{if(f.exists()){
				f.delete();
				System.out.println(">>File Deleted");	}
			else{
				System.err.println(">>Error, File doesnt exist's");
    	        System.out.println(" ");
    	        }
			}
			catch(Exception e){e.printStackTrace();}
		}
		
		public static void upload (String filename){
			try{
				File f = new File(path + filename);
				byte[] buffer = new byte[1024];
				InputStream is = s.getInputStream();
				OutputStream fos = new FileOutputStream(f);
				int count;
					while ((count = is.read(buffer)) > 0){
						fos.write(buffer, 0, count);
					}
				System.out.println("File "+filename+" received from client.");
				System.out.println(" ");
				fos.close();
				is.close();
	    
		}	    catch (Exception e) {
					e.printStackTrace(); }
		}
				
		public static void download (String filename){
			 try {
				 File myFile = new File(path + filename);
			        byte[] buffer = new byte[1024];
			        InputStream is = new FileInputStream(myFile);
			        OutputStream os = s.getOutputStream(); 
			        int count;
			        while ((count = is.read(buffer)) > 0) {
			        	os.write(buffer, 0, count);	
			        }
			        System.out.println("File "+ filename +" sent to client.");	
	    	        System.out.println(" ");
				    os.close();
				    is.close();
			       
			   } catch (Exception e) {
			       System.err.println("File does not exist!");
			   }	
		}
}