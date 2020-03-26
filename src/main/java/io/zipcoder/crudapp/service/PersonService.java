package io.zipcoder.crudapp.service;

import io.zipcoder.crudapp.models.Person;
import io.zipcoder.crudapp.repositories.PersonRepository;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

    private PersonRepository repository;

    public PersonService(PersonRepository repository) {
        this.repository = repository;
    }

    public Person getPerson(Long id) { return repository.findOne(id); }

    public Iterable<Person> getPersonList() { return repository.findAll(); }

    public Person createPerson(Person person) {
        return repository.save(person);
    }

    public Person updatePerson(Long id, Person newPersonData) {
        Person originalPerson = repository.findOne(id);
        originalPerson.setFirstName(newPersonData.getFirstName());
        originalPerson.setLastName(newPersonData.getLastName());
        return repository.save(originalPerson);
    }

    public Boolean deletePerson(Long id) {
        repository.delete(id);
        return true;
    }
}
