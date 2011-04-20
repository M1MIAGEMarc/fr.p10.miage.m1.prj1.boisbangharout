/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package serveur;

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
public class MainServeur {

  /**
   * @param args
   *        Les arguments de la ligne de commande
   */
  public static void main(String[] args) throws InterruptedException, MalformedURLException{
    try {
      InetAddress adress = Inet4Address.getLocalHost();
      String adresse = adress.getHostAddress();
      NoeudServeur noeudServeur = new NoeudServeur();
      Naming.rebind("rmi://" + adresse + "/NoeudServeur", noeudServeur);
      System.out.println("Noeud serveur opérationnel.");
      Thread.sleep(300);
    } catch (AccessException ex) {
      ex.printStackTrace();
    } catch (RemoteException ex) {
      ex.printStackTrace();
    } catch (UnknownHostException ex) {
      ex.printStackTrace();
    }
  }
}
