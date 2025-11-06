package P4;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ProductService extends Remote {
    List<Product> getProducts() throws RemoteException;
    
    Product getProductById(int id) throws RemoteException;
}

