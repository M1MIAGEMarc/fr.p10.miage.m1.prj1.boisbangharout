/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package client;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.AccessException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.Scanner;

/**
 * Programme principal.
 * Cette classe se charge de lancer le serveur et l'IHM.
 *
 * @author Marc Boisban
 * @author Kamel Gharout
 */
public class Main {

  
  /**
   * @param args
   *        Les arguments de la ligne de commande
   */
  public static void main(String[] args) throws InterruptedException, MalformedURLException{
    try {
      InetAddress adress = Inet4Address.getLocalHost();
      String adresse = adress.getHostAddress();
      Noeud noeud = new Noeud();

      // Démarrage du serveur
      Naming.rebind("rmi://" + adresse + "/Noeud", noeud);
      System.out.println("Serveur opérationnel.\n");

      
      // Démarrage de l'application cliente
      Scanner scanner = new Scanner(System.in);
      IHM ihm = new IHM(noeud, scanner);
      ihm.menu();
      System.exit(0);
    }catch (AccessException ae) {
      ae.printStackTrace();
    } catch (RemoteException re) {
      re.printStackTrace();
    } catch (UnknownHostException uhe) {
      uhe.printStackTrace();
    }
  }
}