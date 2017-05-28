package bis.project.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class WorkType {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@Column(nullable=false)
	private Integer code;
	
	@Column(nullable=false, length=60)
	private String name;
	
	@OneToMany(mappedBy="workType")
	@JsonIgnore
	private Set<LegalPersonDetails> clients;
	
	public WorkType() {}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<LegalPersonDetails> getClients() {
		return clients;
	}

	public void setClients(Set<LegalPersonDetails> clients) {
		this.clients = clients;
	}
}
