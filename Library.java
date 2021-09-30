package kpo;
import java.util.ArrayList;
public class Library 
{
          String setPathBook = u.getPathSave;

          private ArrayList<Book> LocalBooks = new ArrayList<Book>();
          private ArrayList<Book> NetBooks = new ArrayList<Book>();
          //добавление книги в локальную библиотеку из сетевой
          public void AddBook(Book b) {
                    Book temp = b;
                    temp.setPathBook = setPathBook;
                    LocalBooks.add(temp);
          }
          //удаление книги из локальной бибилиотеки
          public void DeleteBook(Book b){
                    LocalBooks.delete(b);
                    
          }
          //подгрузка книг из базы данных в сетевую бибилиотеку
          public void AddNetBook(){
                    
          }
          
 }
