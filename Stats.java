package paket; 
import java.util.Date; 
import java.util.ArrayList; 
 
public class Stats { 
  
 private String favgenre; // Любимый жанр 
 private int pages, books, hours; // Собственно: страницы, книги и часы 
 private double avg; // Average - средняя скорость чтения 
 
 public Stats(String favgenre, int pages, int books, int hours, double avg) { 
 this.favgenre = favgenre; 
 this.pages = 0; 
 this.books = 0; 
 this.hours = 0; 
 this.avg = 0; 
 } 
 //геттеры и вся фигня 
 
 public String getfavgenre(){ 
 return favgenre; 
 } 
 
 public void setfavgenre(String a){ 
  favgenre = a; 
 } 
 
 
 public int getpages(){ 
 return pages; 
 } 
 
 public void setpages(int pages){ 
 this.pages = pages; 
 } 
 
 
 public int getbooks(){ 
 return books; 
 } 
 
 public void setbooks(int books){ 
 this.books = books; 
 } 
 
 
 public int gethours(){ 
 return hours; 
 } 
 
 public void sethours(int hours){ 
 this.hours = hours; 
 } 
 
 public double avg(){ 
 return avg; 
} 
 
public void setavg(double avg){ 
 this.avg = avg; 
} 
 
 
//А дальше методы 
 
 
public void counthours () 
{ 
  
 Date start = new Date(); 
} 
 
 
// Метод будет активироваться по нажатию кнопки "Следующая страница" 
public void countpages () 
{ 
 setpages(getpages()+1); 
} 
 
 
// Метод будет активироваться по нажатию кнопки "Завершить" в конце книги 
public void countbooks () 
{ 
 setbooks(getbooks()+1); 
} 
 
// Этот метод будет активироваться только при выводе статистики 
public void countavg() 
{ 
 setavg(getpages()/gethours()); 
} 
 
// Этот метод также будет запускаться при выводе статистики 
public int countfavgenre(Library library) 
{ 
 // Я пока не придумал, как сделать эту хрень логичнее, но пусть пока будет 3 
 // элемента, где 0 - плохо, 1 - норм, 2 - хорошо (это все про жанры) 

	int count[] = new int[3];
	for (int i : count)
	{
		count[i] = 0;
	}
	
	for (Book i : library.bookLibrary) 
	{
		int genre = library.bookLibrary[i].getGenre();
		count[genre] = count[genre] + 1;
	}
	int max = 0;
	for (int i : count)
	{
		if (count[i] > max)
		{
			max = count[i];
		}
	}
	return max;
}
  
   
}
