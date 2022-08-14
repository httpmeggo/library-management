import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Scanner;

import account.*;
import status.BookStatus;

public class Main {

    static String fileName = null;
    static Library lib = new Library();
    static Scanner input = new Scanner(System.in);
    static Boolean running = true;

    public static void main(String[] args) {
        System.out.println("\n\n-- Bobst Catalog 2.0 Alpha --\n");

        System.out.print("If you have an existing library account, type 'login'."
            + "\nIf you are a Librian, type 'librarian'."
            + "\nOtherwise, if you'd like to create an account, type 'create'."
            + "\n\nMode: ");
        String option = input.nextLine();
        if (option.equalsIgnoreCase("login")) {
            loginMember();
        } else if (option.equalsIgnoreCase("librarian")) {
            loginLibrarian();
        } else if (option.equalsIgnoreCase("create")) {
            createAnAccount();
        } else {
            System.err.println("Your input is invalid. Please try again.");
        }

        System.exit(0);
    }

    private static void createAnAccount() {
        System.out.print("Enter your name: ");
        String name = input.nextLine();
        System.out.print("Enter your email: ");
        String email = input.nextLine();
        System.out.print("Enter your phone number: ");
        String phonenumber = input.nextLine();
        System.out.print("Enter your password: ");
        String password = input.nextLine();
        Member.registerMember(name, email, phonenumber, password);
        System.out.println("Thank you for registering! You can now log in with your credentials.");
    }

    //not 100% sure if this works
    private static void loginMember() {
        while (true) {
            System.out.print("\nEmail: ");
            String email = input.nextLine();
            Member a = Member.getByEmail(email);
            if (a == null) {
                System.out.println("\nYour email is not registered. Please create an account.\n");
                createAnAccount();
                continue;
            }

            System.out.print("Password: ");
            String pw = input.nextLine();
            if (a.checkPassword(pw)) {
                System.out.println("\nWelcome, " + a.name + "!");
                showMemberMainMenu();
                return;
            } else {
                System.out.println("\nError: That password was incorrect.");
            }
        }
    }

    // TODO: what to print/what to give access to
    private static void showMemberMainMenu()
    {
        System.out.println("Nothing to do here yet. Quitting.");
    }

    // TODO: Add validation here.
    private static void loginLibrarian() {
        showLibrarianMainMenu();
    }


    // functionality for Librarian. we don't want the library member to
    // be able to edit the library or add books to it.
    private static void showLibrarianMainMenu()
    {
        while (running) {
            System.out.println("\nEnter 0 to load a library." +
                    "\nEnter 1 to save and quit." +
                    "\nEnter 2 for list all books in library" +
                    "\nEnter 3 for add book to library.");
            int answer = input.nextInt();
            switch (answer) {
                case 0:
                    System.out.print("Enter file name to load: ");
                    loadScript(input.nextLine());
                    break;
                case 1:
                    saveAndQuit();
                    break;
                case 2:
                    System.out.println(lib.toString());
                    break;
                case 3:
                    addBook();
                    break;
            }
        }
    }

    private static void addBook() {
        String isbn, title, author, subject, publisher, language;
        int numPages;
        double price;
        BookStatus status;

        System.out.println("\nEnter title: ");
        title = input.nextLine();

        System.out.println("\nEnter author: ");
        author = input.nextLine();

        System.out.println("\nEnter ISBN: ");
        isbn = input.nextLine();

        System.out.println("\nEnter price: ");
        price = input.nextDouble();

        System.out.println("\nEnter subject: ");
        subject = input.nextLine();

        System.out.println("\nEnter publisher: ");
        publisher = input.nextLine();

        System.out.println("\nEnter language: ");
        language = input.nextLine();

        System.out.println("\nEnter number of pages: ");
        numPages = input.nextInt();

        status = BookStatus.AVAILABLE;

        Book b = new Book(isbn, title, author, subject, publisher, language, numPages, price, status);
        lib.addBook(b);
    }

    private static void saveAndQuit() {
        System.out.println("Enter file name: ");
        fileName = input.next() + ".ser";
        running = false;
        FileOutputStream fos = null;
        ObjectOutputStream out = null;
        try {
            fos = new FileOutputStream(fileName);
            out = new ObjectOutputStream(fos);
            out.writeObject(lib);
            fos.close();
            out.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private static void loadScript(String fileName) {
        FileInputStream fis = null;
        ObjectInputStream in = null;
        File file = new File(fileName + ".ser");
        if (file.exists()) {
            try {
                fis = new FileInputStream(file);
                in = new ObjectInputStream(fis);
                lib = (Library) in.readObject();
                fis.close();
                in.close();
            } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            System.out.println("\nThe file does not exist!");
        }
    }
}
