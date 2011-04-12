/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package serveur;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.AccessException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author Marc Boisban
 * @author Kamel Gharout
 */
public class MainServeur {

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) throws InterruptedException{
    try {
      InetAddress adress = Inet4Address.getLocalHost();
      String adresse = adress.getHostAddress();
      NoeudServeur noeudServeur = new NoeudServeur();
      Duplication duplicationStub = (Duplication) UnicastRemoteObject.exportObject(noeudServeur, 0);
      Registry registry = LocateRegistry.createRegistry(1099);
      registry.rebind("rmi://" + adresse + "/NoeudServeur", duplicationStub);
      System.out.println("Noeud serveur opérationnel.");
      Thread.sleep(5);
    } catch (AccessException ex) {
      ex.printStackTrace();
    } catch (RemoteException ex) {
      ex.printStackTrace();
    } catch (UnknownHostException ex) {
      ex.printStackTrace();
    }
  }
}
