package template.store.models;

public class Relationship {

	private final long id1;
	private final long id2;
	private String description;

	public Relationship(long id1, long id2) {
		this.id1 = id1;
		this.id2 = id2;

	}

	public Relationship(long id1, long id2, String description) {
		this.id1 = id1;
		this.id2 = id2;
		this.description = description;
	}

	@Override
	public String toString() {
		return "Relationship [id1=" + id1 + ", id2=" + id2 +
				(description != null ? ", description=" + description : "")
				+ "]";
	}

}
