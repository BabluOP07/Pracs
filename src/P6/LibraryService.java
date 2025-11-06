package P6;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface LibraryService extends Remote {
    List<Book> getAllBooks() throws RemoteException;
    Book getBookById(int bookId) throws RemoteException;
}


