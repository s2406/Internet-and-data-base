
package cours04;

import java.io.*;
import java.net.*;

public class DemandeConnexion {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		// creation de la socket
		Socket s = new Socket();
		InetAddress addr1 = null;
		int port1 = 32506, port2 = 32504;
		try {
			addr1 = InetAddress.getLocalHost();
		}
	    catch (UnknownHostException exp){
		System.out.println("machine inconnue");
		}
		
	    try {
	    	
		// attachement
		InetSocketAddress saddr1 = new InetSocketAddress(addr1, port1);
		s.bind(saddr1);
		
		//	adresse de la machine distante
		InetAddress addr2 = InetAddress.getByName("192.168.1.75");
		InetSocketAddress saddr2 = new InetSocketAddress(addr2, port2);
		
		// demande de connexion
		s.connect(saddr2);
		System.out.println("Connexion etablie entre " +
				s.getLocalSocketAddress() + " et " +
				s.getRemoteSocketAddress());
	    }
		catch (IOException exp){
			System.out.println("erreur d'ouverture");
			}
	   
	
	// Creation des flots
	BufferedReader br = null;
	PrintWriter pw = null;
	try {
	br = new BufferedReader(new InputStreamReader(s.getInputStream()));
	pw = new PrintWriter(s.getOutputStream(), true);
	}
	catch (IOException exp){
		System.out.println("erreur de creation des flots");
		}
	
	// Communication
	try {
	pw.println("début de la communication TCP");
	String ligne = br.readLine();
	System.out.println(ligne);
	s.close();
	}
	catch (IOException exp){
		System.out.println("erreur d'entee-sortie");
		}
	}

}
