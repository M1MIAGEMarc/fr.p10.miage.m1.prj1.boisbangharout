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

/**
 * Programme côté serveur.
 * Cette classe se charge de lancer le serveur et d'attendre les requêtes
 * des programmes clients.
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
      Naming.rebind("rmi://" + adresse + "/Noeud", noeud);
      System.out.println("Noeud serveur opérationnel.");
      Thread.sleep(300);
    } catch (AccessException ae) {
      ae.printStackTrace();
    } catch (RemoteException re) {
      re.printStackTrace();
    } catch (UnknownHostException uhe) {
      uhe.printStackTrace();
    }
  }
}
