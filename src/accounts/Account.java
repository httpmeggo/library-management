package accounts;

import status.AccountStatus;

// For simplicity, we are not defining getter and setter functions. The reader can
// assume that all class attributes are private and accessed through their respective
// public getter methods and modified only through their public methods function.

public abstract class Account {
    private String id;
    private String password;
    private AccountStatus status;

    public boolean resetPassword() {
        return false;
    }

    public boolean checkPassword(String p) {
        return this.password.equals(p);
    }
}
