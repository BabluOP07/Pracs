package P5;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface EquationSolver extends Remote {
    double solve(String expression) throws RemoteException;
}
