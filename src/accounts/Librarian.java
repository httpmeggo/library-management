package accounts;

import status.AccountStatus;

public class Librarian {
    String id;
    String password;
    AccountStatus status;

    public Librarian(String id, String pw, AccountStatus status) {
        this.id = id;
        this.password = pw;
        this.status = status;
    }
    
    public boolean addBook(Book book) {
        return false;
    }

    public boolean blockMember(Member member) {
        return false;
    }

    public boolean unBlockMember(Member member) {
        return false;
    }
}
