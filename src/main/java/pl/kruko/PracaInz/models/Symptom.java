package pl.kruko.PracaInz.models;
import javax.persistence.*;

@Entity (name = "Symptom")
@Table (name = "symptoms")
public class Symptom {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(unique = true)
	private String name;
	
	public Symptom() {
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Symptom [id=" + id + ", name=" + name + "]";
	}
	
	
	
}
