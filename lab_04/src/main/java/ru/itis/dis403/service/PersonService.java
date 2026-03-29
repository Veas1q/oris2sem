package ru.itis.dis403.service;

import org.springframework.stereotype.Service;
import ru.itis.dis403.model.Person;
import ru.itis.dis403.repository.PersonRepository;
import java.util.List;

@Service
public class PersonService {
    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Person save(Person person) {
        return personRepository.save(person);
    }

    public List<Person> findAll() {
        return personRepository.findAll();
    }
}