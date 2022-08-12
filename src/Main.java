import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {

	static String fileName = null;
	static Library lib = new Library();
	static Scanner input = new Scanner(System.in);
	static Boolean running = true;

	public static void main(String[] args) {

		System.out.println("If you have an existing library account, type 'login'." +
				"\nIf you are a Librian, type 'librarian'."
				+ "\nOtherwise, if you'd like to create an account, type 'create'.");
		String option = input.next();
		if (option.compareTo("login") == 0) {
			login();
		}
		if (option.compareTo("librarian") == 0) {
			librarianAccountLogin();
		}
		if (option.compareTo("create") == 0) {
			createAnAccount();
		} else {
			System.err.println("Your input is invalid. Please try again.");
		}

		// functionality for Librarian. we don't want the library member to
		// be able to edit the library or add books to it.
		// we need to put all of this in a function that only gets called
		// if the person logs in as a librarian, and then we need to make
		// a separate menu for if the person logs in as a regular member
		while (running) {
			System.out.println("\nEnter 0 to load a library." +
					"\nEnter 1 to save and quit." +
					"\nEnter 2 for list all books in library" +
					"\nEnter 3 for add book to library.");
			int answer = input.nextInt();
			switch (answer) {
				case 0:
					System.out.println("Enter file name to load");
					loadScript(input.next());
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
		System.exit(0);
	}

	private static void createAnAccount() {
		System.out.println("\nEnter your full name:  ");
		String name = input.next();
		System.out.println("\nEnter your email:  ");
		String email = input.next();
		System.out.println("\nEnter your phone number:  ");
		String phonenumber = input.next();
		System.out.println("\nEnter your password:  ");
		String password = input.next();
		Member.registerMember(name, email, phonenumber, password);
	}

	private static void librarianAccountLogin() {
		// TODO Auto-generated method stub


	}

	//not 100% sure if this works
	private static void login() {
		System.out.println("\nPlease enter your email: ");
		String email = input.next();
		ArrayList<Member> members = Member.getMemberList();
		for (int i = 0; i<members.size(); i++){
			if (members.get(i).email == email){
				Member a = members.get(i);
				  System.out.println("\nPlease enter your password");
				  String pw = input.next();
				  if(a.password == pw){
					System.out.println("\nLogin was successful");
					//TODO: what to print/what to give access to
				  }

			}else{
				System.out.println("\nYour email is not registered. Please create an account");
				createAnAccount();
			}
		 }
	}

	private static void addBook() {
		String ISBN;
		String title;
		String subject;
		String publisher;
		String language;
		int numberOfPages;
		String author;
		double price;
		BookStatus status;

		System.out.println("\nEnter title: ");
		title = input.nextLine();

		System.out.println("\nEnter author: ");
		author = input.nextLine();

		System.out.println("\nEnter ISBN: ");
		ISBN = input.nextLine();

		System.out.println("\nEnter price: ");
		price = input.nextDouble();

		System.out.println("\nEnter subject: ");
		subject = input.nextLine();

		System.out.println("\nEnter publisher: ");
		publisher = input.nextLine();

		System.out.println("\nEnter language: ");
		language = input.nextLine();

		System.out.println("\nEnter number of pages: ");
		numberOfPages = input.nextInt();

		status = BookStatus.AVAILABLE;

		Book b = new Book(ISBN, title, author, subject, publisher,
				language, numberOfPages, price, status);
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
