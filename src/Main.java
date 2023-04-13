import java.util.*;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("Jack", "Connor", "Harry", "George", "Samuel", "John");
        List<String> families = Arrays.asList("Evans", "Young", "Harris", "Wilson", "Davies", "Adamson", "Brown");
        Collection<Person> persons = new ArrayList<>();

        for (int i = 0; i < 10_000_000; i++) {
            persons.add(new Person(names.get(new Random().nextInt(names.size())), families.get(new Random().nextInt(families.size())), new Random().nextInt(100), Sex.values()[new Random().nextInt(Sex.values().length)], Education.values()[new Random().nextInt(Education.values().length)]));
        }

        Stream<Person> s1 = persons.stream();
        Long rs1 = s1.filter(p -> p.getAge() < 18).count();
        System.out.printf("Кол-во несовершеннолетних: %d\n", rs1);

        Stream<Person> s2 = persons.stream();
        List<String> rs2 = s2.filter(p -> p.getAge() >= 18).filter(p -> p.getAge() <= 27).filter(p -> p.getSex() == Sex.MAN).map(Person::getFamily).toList();
        System.out.printf("Кол-во призывников: %d\n", rs2.size());

        Stream<Person> s3 = persons.stream();
        List<Person> rs3 = s3.filter(p -> p.getAge() >= 18).filter(p -> {
            if (p.getSex() == Sex.MAN) {
                return p.getAge() <= 65;
            } else {
                return p.getAge() <= 60;
            }
        }).sorted((Person p1, Person p2) -> comp_fam(p1.getFamily(), p2.getFamily())).toList();
        System.out.printf("Кол-во потенциально работоспособных: %d\n", rs3.size());
    }

    private static int comp_fam(String f1, String f2) {
        if (Objects.equals(f1, f2)) {
            return 0;
        }
        for (int i = 0; i < f2.length(); i++) {
            if (f2.charAt(i) < f1.charAt(i)) {
                return 1;
            } else if (f2.charAt(i) > f1.charAt(i)) {
                return -1;
            }
        }
        return (f1.length() < f2.length()) ? 1 : -1;
    }
}