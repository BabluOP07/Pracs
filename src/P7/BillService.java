package P7;


import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface BillService extends Remote {
    List<Bill> getAllBills() throws RemoteException;
    Bill getBillById(int consumerId) throws RemoteException;
}
