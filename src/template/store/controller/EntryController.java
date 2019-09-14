package template.store.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import template.store.config.ConfigApp;
import template.store.models.Person;

@RestController
@RequestMapping("/crud-nosql")
@EnableNeo4jRepositories
public class EntryController {

	private final static Logger LOG = LoggerFactory.getLogger(EntryController.class);

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private PersonRepository personRepository;

	@Autowired
	private ConfigApp configuration;

	@RequestMapping(method = RequestMethod.GET, value = "/test-crud")
	public String crudTest() {
		LOG.info("Doing a test request");
		return "Here is a NoSQL CRUD test with date " + LocalDateTime.now().getNano();
	}

	@RequestMapping(method = RequestMethod.GET, value = "/count")
	public String getCount() {
		LOG.info("Return count of entities");
		return "Count is " + personRepository.count();
	}

	@RequestMapping(method = RequestMethod.GET, value = "/test-data-conf")
	public String configTest() {
		LOG.info("Get port for NoSQL server");

		String uriConf = configuration.getUri();
		String[] uriParts = uriConf.split(":");
		if (uriParts.length < 2 || uriParts.length > 3) {
			return "isn't fixed a port in uri";
		}
		return "Port is " + uriParts[uriParts.length - 1];
	}

	@RequestMapping(method = RequestMethod.POST, value = "/save-person", consumes = MediaType.APPLICATION_JSON_VALUE)
	public String savePerson(@RequestBody String personJson) {
		if (personJson == null || personJson.isEmpty()) {
			LOG.error("Json body request missing");
			return "Error in request";
		}

		LOG.info("Try to add a new record");

		try {
			Person person = objectMapper.readValue(personJson, Person.class);

			LOG.info("Person value fields : " + person);

			Person savedPerson = personRepository.save(person);

			LOG.info("Save person with name " + savedPerson);

			return "Data saved " + savedPerson;
		} catch (IOException e) {
			LOG.error("Error in request " + e.getMessage());
			return "Error in request " + e.getMessage();
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/all-persons")
	public String getAllPersons() {
		LOG.info("Get all persons registered");

		Iterable<Person> iterPerson = personRepository.findAll();

		try {
			List<Person> list = StreamSupport.stream(iterPerson.spliterator(), false).collect(Collectors.toList());

			LOG.info(list.toString());

			return list.toString();
		} catch (Exception e) {
			return "Error " + e.getMessage();
		}
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/delete-by-id")
	public boolean deleteById(@RequestParam("id") Long id) {
		if (id == null) {
			throw new RuntimeException("id missing");
		}

		if (personRepository.existsById(id)) {
			personRepository.deleteById(id);
			LOG.info("Person with id " + id + " was deleted");
			return true;
		} else {
			LOG.error("Person with id " + id + " not exists");
			return false;
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "/relationship")
	public boolean establishRelationship(@RequestParam("id1") Long id1, @RequestParam("id2") Long id2) {
		if (id1 == null || id2 == null) {
			throw new RuntimeException("one id missing");
		}
		Optional<Person> personOptional1 = personRepository.findById(id1);
		Optional<Person> personOptional2 = personRepository.findById(id2);

		Person person1 = personOptional1.get();
		if (person1 == null) {
			LOG.error("person with id " + id1 + " not exists");
			return false;
		}

		Person person2 = personOptional2.get();
		if (person2 == null) {
			LOG.error("person with id " + id2 + " not exists");
			return false;
		}

		person1.relationWith(person2);
		
		Person savedPerson = personRepository.save(person1);

		LOG.info("Save person with name " + savedPerson);

		return true;
	}
}
