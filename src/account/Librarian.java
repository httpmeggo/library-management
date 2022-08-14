package account;

import status.AccountStatus;

public class Librarian extends Account {
    public Librarian(String id, String pw, AccountStatus status) {
        super.initAccountInfo(id, pw, status, true);
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
