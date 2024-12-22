import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.Test;
import tp.Person;
import tp.PersonService;

public class PersonServiceTest {

    @Test
    public void testFilterByAddress() {
        List<Person> peopleLivingIn123RueA = PersonService.filterByAddress("123 Rue A");
        List<Person> expectedPersonsLivingIn123RueA = Arrays.asList(
                Person.builder().firstName("Alice").familyName("Doe")
                        .birthDate(LocalDate.of(1990, 5, 12))
                        .address("123 Rue A").build(),
                Person.builder().firstName("Charlie").familyName("Brown")
                        .birthDate(LocalDate.of(1985, 3, 9))
                        .address("123 Rue A").build());

        assertThat(peopleLivingIn123RueA).containsExactlyInAnyOrderElementsOf(expectedPersonsLivingIn123RueA);
    }

    @Test
    public void testFilterAdults() {
        List<Person> adults = PersonService.filterAdults();

        List<Person> expectedAdultPersons = Arrays.asList(
                Person.builder().firstName("Alice").familyName("Doe")
                        .birthDate(LocalDate.of(1990, 5, 12))
                        .address("123 Rue A").build(),
                Person.builder().firstName("Bob").familyName("Smith")
                        .birthDate(LocalDate.of(2005, 10, 15))
                        .address("456 Rue B").build(),
                Person.builder().firstName("Charlie").familyName("Brown")
                        .birthDate(LocalDate.of(1985, 3, 9))
                        .address("123 Rue A").build());

        assertThat(adults).containsExactlyInAnyOrderElementsOf(expectedAdultPersons);
    }

    @Test
    public void testSortPerson() {
        List<Person> people = new ArrayList<>();
        people.add(Person.builder().firstName("Hamid").familyName("Jamila").build());
        people.add(Person.builder().firstName("Martin").familyName("Bob").build());
        people.add(Person.builder().firstName("Hamid").familyName("Charles").build());
        people.add(Person.builder().firstName("Bernard").familyName("Charles").build());

        Collections.sort(people);

        assertThat(people.get(0))
                .isEqualTo(Person.builder().firstName("Martin").familyName("Bob").build());
        assertThat(people.get(1))
                .isEqualTo(Person.builder().firstName("Bernard").familyName("Charles").build());
        assertThat(people.get(2))
                .isEqualTo(Person.builder().firstName("Hamid").familyName("Charles").build());
        assertThat(people.get(3))
                .isEqualTo(Person.builder().firstName("Hamid").familyName("Jamila").build());

    }

    @Test
    public void testRemoveBobWithoutIterator() {
        assertThatThrownBy(() -> PersonService.removeBobWithoutIterator())
                .isInstanceOf(ConcurrentModificationException.class);
    }
    @Test
    public void testRemoveBobUsingIterator() {
        Set<Person> people = new HashSet<>();
        people.add(Person.builder().firstName("imane").familyName("elabbadi").build());
        people.add(Person.builder().firstName("zouani").familyName("Charlie").build());

        Set<Person> peopleWithoutBob = PersonService.removeBobUsingIterator();

        assertThat(peopleWithoutBob).containsExactlyInAnyOrderElementsOf(people);
    }

}
