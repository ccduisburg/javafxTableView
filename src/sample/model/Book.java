package sample.model;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;


public class Book {
    private SimpleStringProperty title;
    private SimpleStringProperty author;
    private LocalDate datum;

    public Book(){

    }
    public Book(String s1, String s2, Date value){
        title=new SimpleStringProperty(s1);
        author=new SimpleStringProperty(s2);


    }


    public LocalDate getDatum() {
        return datum;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }

    public Book(SimpleStringProperty title, SimpleStringProperty author, LocalDate datum) {
        this.title = title;
        this.author = author;
        this.datum = datum;
    }

    public String getTitle() {
        return title.get();
    }

    public SimpleStringProperty titleProperty() {
        return title;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public String getAuthor() {
        return author.get();
    }

    public SimpleStringProperty authorProperty() {
        return author;
    }

    public void setAuthor(String author) {
        this.author.set(author);
    }

    @Override
    public String toString() {
        return "Book{" +
                "title=" + title +",by"+
                ", author=" + author +
                '}';
    }
}
