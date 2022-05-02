import java.util.*;
import java.io.*;

class Book{
    private int bookId;
    private String bookName;
    private String writerName;
    private boolean borrowed;

    public Book() {
        bookId = -1;
        bookName = "";
        writerName = "";
        borrowed = false;
    }
    public Book(int id, String bN, String wN, boolean isBr){
        bookId = id;
        bookName = bN;
        writerName = wN;
        borrowed = isBr;
    }

    public void setID(int id) {
        bookId = id;
    }
    public void setBookname(String bN) {
        bookName = bN;
    }
    public void setWritername(String wN) {
        writerName = wN;
    }
    public void setBorrowed(boolean T) {
        borrowed = T;
    }

    public int getID(){
        return bookId;
    }
    public String getName(){
        return bookName;
    }
    public String getwriterName(){
        return writerName;
    }
    public boolean isBorrowed() {
        return borrowed;
    }

}

class UserBookLent{

    private int bookID; 
    private String userName; 
    private String Date;
    public UserBookLent(){
        bookID = -1;
        userName = "";
        Date = "";
    }
    public UserBookLent(int id_u, String name_u, String date){
        this.bookID = id_u;
        this.userName = name_u;
        this.Date = date;
    }

    public void setId(int id){
        this.bookID = id;
    }
    public void setName(String user){
        this.userName = user;
    }
    public void setDate(String date){
        this.Date = date;
    } 

    public int getID(){
        return bookID;
    }
    public String getName_user(){
        return userName;
    }
    public String getDate(){
        return Date;
    }
}

class Admin{
    private String name;
    private String pass;    
    public Admin() {
        this.name = "";
        this.pass = "";
    }
    public Admin(String name, String pass) {
        this.name = name;
        this.pass = pass;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setPass(String pass){
        this.pass = pass;
    }
    public String getName(){
        return this.name;
    }
    public String getPass(){
        return this.pass;
    }
}

class Library{
    private final int SOCOT_CUA_BOOK = 4;
    private final int SOCOT_CUA_ADMIN = 2;
    private final int SOCOT_CUA_USERS = 3;
    private Vector<Book> books;
    private Vector<UserBookLent> list_ULents;
    private int quantity;
    private int capacity;
    private Vector<Admin> admins;
    public Library(){   
        books = new Vector<Book>();
        admins = new Vector<Admin>();
        list_ULents = new Vector<UserBookLent>();
        quantity = 0;
        capacity = 1000;
    }

    public Library(Vector<Book> books, Vector<UserBookLent> listUser, Vector<Admin> admins, int quan, int capa){
        this.admins = admins;
        this.books = books;
        this.list_ULents = listUser;
        this.quantity = quan;
        this.capacity = capa;
    }

    void setQuan(int quantity){
        this.quantity = quantity;
    }

    void setCap(int capacity){
        this.capacity = capacity;
    }

    void setAdmin(Vector<Admin> admins){
        this.admins = admins;
    }

    public int getCap() {
        return capacity;
    }

    public int getQuan() {
        return quantity;
    }


    public void add_UserBookLent(UserBookLent userbooklent){
        list_ULents.add(userbooklent);
    }

    public Vector<UserBookLent> getlist_ULents(){
        return list_ULents;
    }

    public void add_Book(Book bookNeedAdd){
        books.add(bookNeedAdd);
        quantity++;
    }

    public boolean isAdmins(Admin admin) {
        for (int i = 0; i < this.admins.size(); i++) {
            if (admins.get(i).getName().equals(admin.getName())) {
                if (admins.get(i).getPass().equals(admin.getPass())) return true;
                return false;
            }
        }
        return false;
    }
    public boolean readData() {
        try {
            FileInputStream booksF = new FileInputStream("books.txt");
            FileInputStream usersF = new FileInputStream("users.txt");
            FileInputStream adminsF = new FileInputStream("admins.txt");
            Scanner booksScanner = new Scanner(booksF);
            Scanner adminsScanner = new Scanner(adminsF);
            Scanner usersScanner = new Scanner(usersF);
            while (adminsScanner.hasNextLine()) {
                Admin curAdmin = new Admin();
                String line = adminsScanner.nextLine();
                String[] tokens = line.split("@");
                for (int i = 0; i < SOCOT_CUA_ADMIN; i++) {
                    if (i == 0) curAdmin.setName(tokens[i]);
                    if (i == 1) curAdmin.setPass(tokens[i]);
                }
                admins.add(curAdmin);
            }
            while (usersScanner.hasNextLine()) {
                UserBookLent curUsers = new UserBookLent();
                String line = usersScanner.nextLine();
                String[] tokens = line.split("@");
                for (int i = 0; i < SOCOT_CUA_USERS; i++) {
                    if (i == 0) curUsers.setName(tokens[i]);
                    if (i == 1) curUsers.setDate(tokens[i]);
                    if (i == 2) curUsers.setId(Integer.parseInt(tokens[i]));
                }
                list_ULents.add(curUsers);
            }

            while(booksScanner.hasNextLine()) {
                Book curBook = new Book();
                String line = booksScanner.nextLine();
                String[] tokens = line.split("@");
                for (int i = 0; i < SOCOT_CUA_BOOK; i++) {
                    if (i == 0) curBook.setID(Integer.parseInt(tokens[i]));
                    if (i == 1) curBook.setBookname(tokens[i]);
                    if (i == 2) curBook.setWritername(tokens[i]);
                    if (i == 3) {
                            curBook.setBorrowed(Integer.parseInt(tokens[i]) == 0 ? false : true);
                    }
                }
                add_Book(curBook);
                this.quantity = this.books.size();
            }
            booksF.close();
            usersF.close();
            adminsF.close();
            booksScanner.close();
            usersScanner.close();
            adminsScanner.close();
            System.out.println("Read files successfully");
            return true;

        } catch (IOException e) {
            System.out.println("Files not found");
            e.printStackTrace(); 
            return false;
        }

    }

    public void delete_Book(Book bookNeedDelete){
        int length = books.size();
        for(int indexOfBook = 0; indexOfBook < length; indexOfBook++){
            if(books.get(indexOfBook) == bookNeedDelete){
                books.remove(indexOfBook);
                quantity--;
                return;
            }
        }
    }

    public Vector<Book> search_Book(String bookNeedSearch){
        Vector<Book> stringOfBook = new Vector<Book>();
        bookNeedSearch = bookNeedSearch.toLowerCase();
        bookNeedSearch = bookNeedSearch.replaceAll("\\s+","");
        int length = books.size();
        for(int indexOfBook = 0; indexOfBook < length; indexOfBook++){
            Book nameOfBook = books.get(indexOfBook);
            if(nameOfBook.getName().contains(bookNeedSearch) 
            || nameOfBook.getwriterName().contains(bookNeedSearch)){
                stringOfBook.add(books.get(indexOfBook));
            }
        }
        return stringOfBook;
    }

    public boolean saveData(){
        try {
            FileOutputStream booksF = new FileOutputStream("books.txt");
            FileOutputStream adminsF = new FileOutputStream("admins.txt");
            FileOutputStream usersF = new FileOutputStream("users.txt");
            booksF.write(("").getBytes());
            adminsF.write(("").getBytes());
            usersF.write(("").getBytes());
            for (int i = 0; i < quantity; i++) {
                String line = books.get(i).getID() + "@" 
                            + books.get(i).getName() + "@" 
                            + books.get(i).getwriterName() + "@"
                            + (books.get(i).isBorrowed() ? "1" : "0");
                booksF.write(line.getBytes());
                if (i != quantity - 1) booksF.write("\n".getBytes());
            }
            for (int i = 0; i < this.admins.size(); i++) {
                String line = admins.get(i).getName() + "@" 
                            + admins.get(i).getPass();
                adminsF.write(line.getBytes());
                adminsF.write("\n".getBytes());
                if (i != this.admins.size() - 1) adminsF.write("\n".getBytes());
            }
            for (int i = 0; i < this.list_ULents.size(); i++) {
                String line = list_ULents.get(i).getID() + "@" 
                            + list_ULents.get(i).getDate() + "@" 
                            + list_ULents.get(i).getID();
                usersF.write(line.getBytes());
                if (i != this.admins.size() - 1) usersF.write("\n".getBytes());
            }
            booksF.close();
            adminsF.close();
            usersF.close();
            System.out.println("Saved file successfully!");
            return true;
        } catch (IOException e) {
            System.out.println("File not found!");
            e.printStackTrace(); 
            return false;
        }

    }
    
}

public class Manage_Library {
    public static void main(String[] args){
        Library newLib = new Library();
        newLib.readData();
        Admin newAdmin = new Admin("admin", "admin");
        if (newLib.isAdmins(newAdmin)) {
            System.out.println("is Admin!");
        } else  {
            System.out.println("isn't Admin!");
            return;
        }
        Book book1 = new Book(100, "book100", "tdx", false);
        // newLib.add_Book(book1);
        newLib.delete_Book(book1);
        newLib.saveData();



        // Manage_Library K = new Manage_Library();
        // K.readBook();
        // Win.saveBook();

        // add_Book(book1);

        // Vector<Book> result = search_Book("b");
        // System.out.println("Find \"b\": ");
        // if(result.isEmpty()) System.out.println("Not found.");
        // else{
        //     for(Book i : result){
        //         System.out.println(i.bookId + " " + i.bookName + " " + i.writerName);
        //     }
        // }
        // add_Book(book2);
        // add_Book(book3);
        // result = search_Book("c");
        // System.out.println("Find \"c\": ");
        // if(result.isEmpty()) System.out.println("Not found.");
        // else{
        //     for(Book i : result){
        //         System.out.println(i.bookId + " " + i.bookName + " " + i.writerName);
        //     }
        // }
    }
}
