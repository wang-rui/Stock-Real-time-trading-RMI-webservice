/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 *
 * @author ruiwang
 */
public class StockRMIServer {

    public static void main(String[] args) throws MalformedURLException, RemoteException {
        StockRMIServant stockService = new StockRMIServant();
        try {
            Registry registry = LocateRegistry.createRegistry(1099);

            Naming.rebind("stockService", stockService);
            System.out.println("StockRMI server ready");
        } catch (RemoteException e) {
                 System.out.println("StockRMI server main " + e.getMessage());
        }

    }
}
