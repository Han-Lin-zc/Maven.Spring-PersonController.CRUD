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

    public Iterable<Person> index() {
        return repository.findAll();
    }

    public Person createPerson(Person baker) {
        return repository.save(baker);
    }

    public Person getPerson(Long id) { return repository.findOne(id); }

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
