package streamtask2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class StreamTask2 {
    
    public static void main(String[] args) {
        // TODO code application logic here
        List<String> names = Arrays.asList("Jack", "Connor", "Harry", "George", 
                "Samuel", "John");
        List<String> families = Arrays.asList("Evans", "Young", "Harris", 
                "Wilson", "Davies", "Adamson", "Brown");
        Collection<Person> persons = new ArrayList<>();
        for (int i = 0; i < 10_000_000; i++) {
            persons.add(new Person(
                names.get(new Random().nextInt(names.size())),
                families.get(new Random().nextInt(families.size())),
                new Random().nextInt(100),
                Sex.values()[new Random().nextInt(Sex.values().length)],
                Education.values()[new Random().nextInt(Education.values().length)])
            );
        }
        System.out.println("Количество несовершеннолетних:\n" + persons.stream()
                .filter(x -> x.getAge() < 18)
                .count());
        List<String> сonscripts = persons.stream()
                .filter(x -> x.getSex() == Sex.MAN)
                .filter(x -> x.getAge() >= 18)
                .filter(x -> x.getAge() < 27)
                .map(x -> x.getFamily())
                .collect(Collectors.toList());
        System.out.println("Список призывников:\n" + сonscripts);
        List<Person> operable = persons.stream()
                .filter(x -> x.getAge() >= 18)
                .filter(x -> x.getAge() <= 65)
                .filter(x -> !((x.getAge() > 60) && (x.getSex() == Sex.WOMAN)))
                .filter(x -> x.getEducation() == Education.HIGHER)
                .sorted(Comparator.comparing(Person::getFamily))
                .collect(Collectors.toList());
        System.out.println(operable);
    }
    
}
