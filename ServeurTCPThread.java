package Annales;

import java.io.*;
import java.net.*;
import java.util.*;

public class ServeurTCPThread implements Runnable {

	BufferedReader br = null;
	PrintWriter pw = null;
	SocketAddress adresseIP;

	public ServeurTCPThread(Socket s) {
		try {
			adresseIP = s.getRemoteSocketAddress();
			br = new BufferedReader(new InputStreamReader(s.getInputStream()));
			pw = new PrintWriter(s.getOutputStream(), true);
		} catch (IOException exp) {
			System.out.println("erreur de creation des flots");
		}

		new Thread(this).start();
	}

	public void run() {

		ArrayList<String> namesList = new ArrayList<>();
		ArrayList<String> messageNameList = new ArrayList<>();
		ArrayList<String> messageList = new ArrayList<>();
		ArrayList<SocketAddress> IPList = new ArrayList<>();
		String message, name;

		// Communication
		try {

			while (true) {
				message = br.readLine();
				if (message.startsWith("inscription")) {
					name = message.substring(12);
					System.out.println("inscription" + name);
					// Vérifier si le nom existe déjà dans la liste
					if (namesList.contains(name)) {
						pw.println("False");
					} else {
						// Ajouter le nom à la liste
						namesList.add(name);
						IPList.add(adresseIP);
						System.out.println(adresseIP);
						// Envoyer la réponse au client avec la liste des noms
						pw.println("True Liste_des_noms: " + namesList);
					}
				}

				if (message.startsWith("déinscription")) {
					name = message.substring(14);
					System.out.println("déinscription" + name);
					// Vérifier si le nom existe dans la liste
					if (!namesList.contains(name)) {
						pw.println("False");
					} else {
						// Supprimer le nom de la liste
						namesList.remove(name);
						// Envoyer la réponse au client avec la liste des noms
						pw.println("True Liste_des_noms: " + namesList);
					}
				}

				if (message.startsWith("envoi")) {
					name = message.substring(6);
					System.out.println("envoi" + name);
					message = br.readLine();
					System.out.println(message);
					// Vérifier si le nom existe dans la liste
					if (!namesList.contains(name)) {
						pw.println("False");
					} else {
						messageNameList.add(name);
						messageList.add(message);
						// Envoyer la réponse au client
						pw.println("True");
					}
				}

				if (message.startsWith("lecture")) {
					name = message.substring(8);
					System.out.println("lecture" + name);
					// Vérifier si le nom existe dans la liste
					if (!namesList.contains(name)) {
						pw.println("False");
					} else {

					}
					// envoyer le nombre de messages
					int nMessages = 0;
					for (int i = 0; i < messageNameList.size(); i++) {
						if (messageNameList.get(i).equals(name)) {
							nMessages++;
						}
					}
					pw.println(nMessages);
					// envoyer les messages
					for (int i = 0; i < messageNameList.size(); i++) {
						if (messageNameList.get(i).equals(name)) {
							pw.println(messageList.get(i));
						}

					}
				}

			}

		} catch (IOException exp) {
			System.out.println("erreur d'entee-sortie");
		}
	}

}
