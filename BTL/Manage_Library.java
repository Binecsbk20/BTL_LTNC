import java.util.*;
import java.io.*;
class Book{
    private int bookId;
    private String bookName;
    private String writerName;
    private boolean borrowed;

    public String getName(){
        return bookName;
    }
    public String getwriterName(){
        return writerName;
    }
    public int getID(){
        return bookId;
    }
    public boolean isBorrowed() {
        return borrowed;
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
}

class User_Book_Lent{
    private int Id_user;
    private String Name_user;
    public User_Book_Lent(int id_u, String name_u){
        Id_user = id_u;
        Name_user = name_u;
    }
    public int getId_user(){
        return Id_user;
    }
    public String getName_user(){
        return Name_user;
    }
}

class Admin{
    String Name;
    String Passwork;
    Hashtable<Integer, String> User_Book_Lent; // Lưu id, và tên của user mượn sách.
}
class User{
    String Name;
    String Passwork;
    Vector<Integer> Book_Borrowed; // Tên sách mà user mượn. Tối đa 5 quyển sách.
}
class Library{
    private Vector<Book> books;
    private Vector<Admin> admins;
    // viết thêm 1 véc tơ kiểu class User_Book_Lent
    private Vector<User_Book_Lent> UBookLents;

    private int quantity;
    private int capacity;

    public Library(){
        books = new Vector<Book>();
        admins = new Vector<Admin>();
        UBookLents = new Vector<User_Book_Lent>();
        quantity = 0;
        capacity = 1000;
    }




    public void add_UserBookLent(User_Book_Lent userbooklent){
        UBookLents.add(userbooklent);
    }

    public Vector<User_Book_Lent> getUBookLents(){
        return UBookLents;
    }

    public void add_Book(Book bookNeedAdd){
        books.add(bookNeedAdd);
        quantity++;
    }

    public boolean readBook(){
        try {
            FileInputStream booksF = new FileInputStream("books.txt");
            // FileInputStream usersF = new FileInputStream("users.txt");
            // FileInputStream adminsF = new FileInputStream("admins.txt");
            Scanner booksScanner = new Scanner(booksF);
            while(booksScanner.hasNextLine()) {
                Book bookCur = new Book();
                String line = booksScanner.nextLine();
                String[] tokens = line.split("@");
                for (int i = 0; i <= 3; i++) {
                    if (i == 0) bookCur.setID(Integer.parseInt(tokens[i]));
                    if (i == 1) bookCur.setBookname(tokens[i]);
                    if (i == 2) bookCur.setWritername(tokens[i]);
                    if (i == 3) {
                            bookCur.setBorrowed(Integer.parseInt(tokens[i]) == 0 ? false : true);
                    }
                    // i = i + 1;
                }
                add_Book(bookCur);
            }
            booksF.close();
            booksScanner.close();
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
    public boolean saveBook(){
        try {
            FileOutputStream booksF = new FileOutputStream("books.txt");
            booksF.write(("").getBytes());
            // FileInputStream usersF = new FileInputStream("users.txt");
            // FileInputStream adminsF = new FileInputStream("admins.txt");
            for (int i = 0; i < quantity; i++) {
                String line = books.get(i).getID() + "@" 
                            + books.get(i).getName() + "@" 
                            + books.get(i).getwriterName() + "@"
                            + (books.get(i).isBorrowed() ? "1" : "0");
                booksF.write(line.getBytes());
                booksF.write("\n".getBytes());
            }
            
            System.out.println("Saved file successfully!");
            return true;
        } catch (IOException e) {
            System.out.println("File not found!");
            e.printStackTrace(); 
            return false;
        }

    }
    
}

public class Main {
    public static void main(String[] args){
        Library newLib = new Library();
        newLib.readBook();
        Book book1 = new Book(1,"a","A",false);
        Book book2 = new Book(2,"b","B",false);
        Book book3 = new Book(3,"c","C",false);
        // newLib.add_Book(book1);
        // newLib.add_Book(book2);
        newLib.delete_Book(book2);

        newLib.saveBook();



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