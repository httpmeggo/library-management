import java.io.Serializable;

public class Person implements Serializable {
    private String name;
    private String email;
    private String phone;

    public Person(String newName, String newEmail, String newPhone){
        this.name = newName;
        this.email = newEmail;
        this.phone = newPhone;
    }
}
