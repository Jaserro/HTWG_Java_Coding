package javaAcht;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class Person {

    private String vorname;
    private String nachname;
    private LocalDate geburt;

    public Person(String pVorname, String pNachname, LocalDate pGeburt){
        this.vorname = pVorname;
        this.nachname = pNachname;
        this.geburt = pGeburt;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    public LocalDate getGeburt() {
        return geburt;
    }

    public void setGeburt(LocalDate geburt) {
        this.geburt = geburt;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.vorname);
        sb.append(" ");
        sb.append(this.nachname);
        sb.append(" geb. am: ");
        sb.append(this.geburt.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)));
        return sb.toString();
    }
}
