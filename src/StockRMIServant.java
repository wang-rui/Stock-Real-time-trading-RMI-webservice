
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.TreeMap;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author ruiwang
 */
public class StockRMIServant extends UnicastRemoteObject implements StockRMI {
    

protected StockRMIServant() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	/* Given a stock, get a list of users that are interested in that stock. */ 
    private static Map stocks = new TreeMap();
    /* Given a user, get the remote object reference to its callback method. */ 
    private static Map users = new TreeMap();

    public boolean subscribe(String user, String stockSym) throws RemoteException {
        ArrayList <String> u = new ArrayList();
            if(stocks.get(stockSym)==null){
                u.add(user);
                stocks.put(stockSym, u);
                 return true;
            } else {
                u = (ArrayList<String>)stocks.get(stockSym);
                u.add(user);
                stocks.replace(stockSym, u);
                 return true;
            }
           
    }

    public boolean unSubscribe(String user, String stockSym) throws RemoteException {
               ArrayList <String> u = new ArrayList();
            if(stocks.get(stockSym)==null){
                 return true;
            } else {
                u = (ArrayList<String>)stocks.get(stockSym);
                u.remove(user);
                stocks.replace(stockSym, u);
                 return true;
            }
    }

    public void stockUpdate(String stockSym, double price) throws RemoteException {
                 ArrayList <String> u = new ArrayList();
                 u = (ArrayList <String>) stocks.get(stockSym);
                 for(int i=0; i<u.size();i++){
                     Notifiable remoteClient = (Notifiable) users.get(u.get(i));
                     remoteClient.notify(stockSym, price);
                 }
    }

    public void registerCallBack(Notifiable remoteClient, String user) throws
            RemoteException {
            if(users.get(user)==null){
                users.put(user, remoteClient);
            }else{
                users.replace(user, remoteClient);
            }
    }

    public void deRegisterCallBack(String user) throws RemoteException {
        
            if(users.get(user)==null){
               return;
            }else{
                users.remove(user);
            }
    }

   

}
