package account;

import status.AccountStatus;

// For simplicity, we are not defining getter and setter functions. The reader can
// assume that all class attributes are private and accessed through their respective
// public getter methods and modified only through their public methods function.

public abstract class Account {
    private boolean admin = false;
    private String id;
    private String password;
    private AccountStatus status;

    protected void initAccountInfo(String id, String pass, AccountStatus status, boolean admin) {
        this.id = id;
        this.password = pass;
        this.status = status;
        this.admin = admin;
    }

    public boolean isAdmin() {
        return this.admin;
    }

    public AccountStatus getStatus() {
        return this.status;
    }

    public boolean checkPassword(String p) {
        System.out.println(this.password);
        System.out.println(p);
        return this.password.equals(p);
    }
}
