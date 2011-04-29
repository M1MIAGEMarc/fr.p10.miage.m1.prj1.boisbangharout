/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.util.Scanner;

/**
 * Programme côté client.
 * Cette classe se charge de lancer l'IHM avec le noeud client courant comme
 * paramètre.
 * 
 * @author Marc Boisban
 * @author Kamel Gharout
 */
public class MainClient {

  /**
   * @param args
   *        Les arguments de la ligne de commande
   */
  public static void main(String[] args) {
    NoeudClient noeudClient = new NoeudClient();
    Scanner scanner = new Scanner(System.in);
    IHM ihm = new IHM(noeudClient, scanner);
    ihm.menu();
  }
}
