/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package serveur;

import java.net.InetAddress;
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
  public static void main(String[] args) throws Exception {
    NoeudServeur noeudServeur = new NoeudServeur();
    Duplication duplicationStub = (Duplication) UnicastRemoteObject.exportObject(noeudServeur, 0);

    Registry registry = LocateRegistry.createRegistry(1099);

    registry.rebind("NoeudServeur", registry);
    System.out.println("Noeud serveur opérationnel.");
  }
}
