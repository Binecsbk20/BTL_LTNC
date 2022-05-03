import java.util.*;
import java.io.*;
import java.text.*;
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

    public void add_Book(Book needToAdd){
        books.add(needToAdd);

    }
    //add book use name
    public void add_Book(String nameNewBook, String nameNewWriter){
        Book needToAdd = new Book(this.books.lastElement().getID() + 1, nameNewBook, nameNewWriter, false);
        books.add(needToAdd);
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
    public boolean changePass (Admin admin, String newpass) {
        if (isAdmins(admin)) {
            for (int i = 0; i < this.admins.size(); i++) {
                if (admins.get(i).getName().equals(admin.getName())) {
                    admins.get(i).setPass(newpass);
                    return true;
                }
            }
        }
        return false;
    }
    public boolean delete_Book(Book bookNeedDelete){
        int length = books.size();
        boolean checkAns = false;
        for(int indexOfBook = length - 1; indexOfBook >= 0; indexOfBook--){
            Book current = books.get(indexOfBook);
            if(current.getID() == bookNeedDelete.getID() &&
                current.getName().equals(bookNeedDelete.getName()) &&
                    current.getwriterName().equals(bookNeedDelete.getwriterName()) &&
                        current.isBorrowed() == bookNeedDelete.isBorrowed()){
                books.remove(indexOfBook);
                quantity--;
                checkAns = true;
            }
        }
        return checkAns;
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
    public boolean borrowBook(String UserName, Book bookNeededBorrow){
        if(bookNeededBorrow.isBorrowed() == true) return false;

        //getDate
        SimpleDateFormat formatDate = new SimpleDateFormat(
            "dd/MM/yyyy  HH:mm:ss z"); 
        Date date = new Date();
        formatDate.setTimeZone(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));

        UserBookLent user = new UserBookLent(-1, UserName, formatDate.format(date));
        int length = books.size();
        boolean checkAns = false;
        for(int indexOfBook = length - 1; indexOfBook >= 0; indexOfBook--){
            Book current = books.get(indexOfBook);
            if(current.getID() == bookNeededBorrow.getID() &&
                    current.getName().equals(bookNeededBorrow.getName()) &&
                    current.getwriterName().equals(bookNeededBorrow.getwriterName()) &&
                    current.isBorrowed() == bookNeededBorrow.isBorrowed()){
                current.setBorrowed(true);
                books.set(indexOfBook, current);
                user.setId(current.getID());
                list_ULents.add(user);
                checkAns = true;
            }
        }
        return checkAns;
    }
    public boolean borrowBook(String UserName, int idOfBook){
        SimpleDateFormat formatDate = new SimpleDateFormat(
            "dd/MM/yyyy  HH:mm:ss z"); 
        Date date = new Date();
        formatDate.setTimeZone(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));
        
        int length = books.size();
        boolean checkAns = false;
        UserBookLent user = new UserBookLent(-1, UserName, "");
        for(int indexOfBook = length - 1; indexOfBook >= 0; indexOfBook--){
            Book current = books.get(indexOfBook);
            if(current.getID() == idOfBook){
                current.setBorrowed(true);
                books.set(indexOfBook, current);
                user.setId(current.getID());
                user.setDate(formatDate.format(date));
                list_ULents.add(user);
                checkAns = true;
                System.out.println("Borrowed!");
                return checkAns;
            }
        }
        System.out.println("Can't borrow!");
        return checkAns;
    }
    public boolean saveData(){
        try {
            FileOutputStream booksF = new FileOutputStream("books.txt");
            FileOutputStream adminsF = new FileOutputStream("admins.txt");
            FileOutputStream usersF = new FileOutputStream("users.txt");
            booksF.write(("").getBytes());
            adminsF.write(("").getBytes());
            usersF.write(("").getBytes());
            for (int i = 0; i < books.size(); i++) {
                String line = books.get(i).getID() + "@" 
                            + books.get(i).getName() + "@" 
                            + books.get(i).getwriterName() + "@"
                            + (books.get(i).isBorrowed() ? "1" : "0");
                booksF.write(line.getBytes());
                if (i != books.size() - 1) booksF.write("\n".getBytes());
            }
            for (int i = 0; i < this.admins.size(); i++) {
                String line = admins.get(i).getName() + "@" 
                            + admins.get(i).getPass();
                adminsF.write(line.getBytes());
                // adminsF.write("\n".getBytes());
                if (i != this.admins.size() - 1) adminsF.write("\n".getBytes());
            }
            for (int i = 0; i < this.list_ULents.size(); i++) {
                String line = list_ULents.get(i).getID() + "@" 
                            + list_ULents.get(i).getName_user() + "@" 
                            + list_ULents.get(i).getDate();
                usersF.write(line.getBytes());
                if (i != this.list_ULents.size() - 1) usersF.write("\n".getBytes());
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
        // newLib.add_Book("GT2", "thanhdx");
        newLib.borrowBook("Thanh", 3);
        newLib.saveData();
    }
}
