package Java8.src.javaAcht;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

public class Test {
    public static void main(String[] args) {
        List<Person> persList = new LinkedList<>();
        persList.add(new Person("Hans", "Maier", LocalDate.of(2000, 10, 15)));
        persList.add(new Person("Dieter", "Mueller", LocalDate.of(1960, 10, 15)));
        persList.add(new Person("Ulrike", "Dietrich", LocalDate.of(1850, 5, 1)));
        persList.add(new Person("Ferdinand", "Hurhold", LocalDate.of(1783, 7, 20)));
        persList.add(new Person("Kevin", "Reuther", LocalDate.of(2010, 12, 9)));
        persList.add(new Person("Susanne", "Schiebeck", LocalDate.of(1983, 5, 20)));
        persList.add(new Person("Agathe", "Bauer", LocalDate.of(1973, 3, 3)));
        persList.add(new Person("Arnold", "Schwarzenegger", LocalDate.of(1947, 7, 30)));
        persList.add(new Person("Alfred", "Hitchcock", LocalDate.of(1899, 8, 13)));
        persList.add(new Person("Amadeus", "Mozart", LocalDate.of(1756, 1, 27)));

        Predicate<Person> over18 = person -> (person.getGeburt().isBefore(LocalDate.now().minusYears(18)));
        Predicate<Person> startsA = person -> (person.getVorname().startsWith("A"));
        System.out.println(istVolljaehrig(persList, over18));
        persList.sort((x,y) -> x.getGeburt().compareTo(y.getGeburt()));
        System.out.println(persList);
        persList.sort(Comparator.comparing(Person::getGeburt).reversed());
        System.out.println(persList);
        persList.stream()
                .filter(over18)
                .sorted(Comparator.comparing(Person::getGeburt))
                .forEach(person -> System.out.println(person.getGeburt().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG))));

        persList.stream()
                .filter(startsA)
                .sorted(Comparator.comparing(Person::getGeburt))
                .limit(3)
                .forEach(person -> System.out.println(person));
    }

    public static boolean istVolljaehrig(Collection<? extends Person> h, Predicate<? super Person> pred){
        for (Person p: h) {
            if (!pred.test(p)){
                return false;
            }
        }
        return true;
    }
}
