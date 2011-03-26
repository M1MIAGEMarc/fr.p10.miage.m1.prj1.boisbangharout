/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package serveur;

import java.io.BufferedReader;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author Marc Boisban
 * @author Kamel Gharout
 */
public interface Duplication extends Remote{

  public List<String> getListeNomsFichiers() throws RemoteException;

  public void ecrireFichier(String adresse, BufferedReader donnees, String nomFichier) throws RemoteException;

  public void supprimerFichier(String nomFichier) throws RemoteException;

  public BufferedReader extraireDonnees(String nomFichier) throws RemoteException;

}
