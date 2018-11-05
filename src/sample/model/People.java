package sample.model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

import java.text.SimpleDateFormat;

public class People {

        private SimpleStringProperty name;
        private SimpleStringProperty vorname;
        private SimpleDateFormat geburstDatum;
        private SimpleDoubleProperty salary;

    public People() {
    }

    public People(SimpleStringProperty name, SimpleStringProperty vorname, SimpleDateFormat geburstDatum, SimpleDoubleProperty salary) {
        this.name = name;
        this.vorname = vorname;
        this.geburstDatum = geburstDatum;
        this.salary = salary;
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getVorname() {
        return vorname.get();
    }

    public SimpleStringProperty vornameProperty() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname.set(vorname);
    }

    public SimpleDateFormat getGeburstDatum() {
        return geburstDatum;
    }

    public void setGeburstDatum(SimpleDateFormat geburstDatum) {
        this.geburstDatum = geburstDatum;
    }

    public double getSalary() {
        return salary.get();
    }

    public SimpleDoubleProperty salaryProperty() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary.set(salary);
    }

    @Override
    public String toString() {
        return "People{" +
                "name=" + name +
                ", vorname=" + vorname +
                ", geburstDatum=" + geburstDatum +
                ", salary=" + salary +
                '}';
    }
}
