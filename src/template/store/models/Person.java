package template.store.models;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity
public class Person {

	@Id
	@GeneratedValue
	private Long id;

	private String name;

	private String data;

	@Relationship(type = "RELATIONSHIP", direction = Relationship.UNDIRECTED)
	public Set<Person> relationships = new HashSet<>();
	
	public Person(String name, String data) {
		this.name = name;
		this.data = data;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public void relationWith(Person person) {
		relationships.add(person);
	}
	
	@Override
	public String toString() {
		return "Person [id="
				+ id + ", name=" + name + ", data=" + data + ", relationship=" + Optional.ofNullable(this.relationships)
						.orElse(Collections.emptySet()).stream().flatMap(model->Stream.of(model.getName(), model.getData())).collect(Collectors.toList())
				+ "]";
	}

}
