package io.zipcoder.crudapp.controller;

import io.zipcoder.crudapp.models.Person;
import io.zipcoder.crudapp.repositories.PersonRepository;
import io.zipcoder.crudapp.service.PersonService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class PersonControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PersonService personService;

    @MockBean
    private PersonRepository repository;

    @Test
    public void getPersonTest() throws Exception {
        Long givenId = 1L;
        BDDMockito
                .given(repository.findOne(givenId))
                .willReturn(new Person("Han", "Lin"));

        String expectedContent = "{\"id\":null,\"firstName\":\"Han\",\"lastName\":\"Lin\"}";
        this.mvc.perform(MockMvcRequestBuilders
                .get("/people/" + givenId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedContent));
    }

    @Test
    public void testShowCommentNotFound() throws Exception {

        doReturn(Optional.empty()).when(personService).getPerson(1L);

        Person testComment = personService.getPerson(1L);

        mvc.perform(get("/people/{id}", 2))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreate() throws Exception {
        Person person = new Person("Han", "Lin");
        BDDMockito
                .given(repository.save(person))
                .willReturn(person);

        String expectedContent="{\"id\":null,\"firstName\":\"Han\",\"lastName\":\"Lin\"}";
        this.mvc.perform(MockMvcRequestBuilders
                .post("/people/")
                .content(expectedContent)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().string(expectedContent));
    }

}
