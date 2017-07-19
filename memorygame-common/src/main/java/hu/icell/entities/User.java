package hu.icell.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the USERS database table.
 * 
 */
@Entity
@Table(name="USERS")
@NamedQueries({
    @NamedQuery(name="User.findAll", query="SELECT u FROM User u"),
    @NamedQuery(name="User.findByEmail", query="SELECT u FROM User u WHERE u.email=:email"),
    @NamedQuery(name="User.findByUsername", query="SELECT u FROM User u WHERE u.username=:username")
})
@SequenceGenerator(name = "USERS_SEQ", sequenceName = "USERS_SEQ", initialValue = 1, allocationSize = 0)
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USERS_SEQ")
	private long id;

	private String email;

	private String password;

	private String username;

	//bi-directional many-to-one association to Result
	@OneToMany(mappedBy="user", cascade = CascadeType.ALL)
	private List<Result> results;

	public User() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<Result> getResults() {
		return this.results;
	}

	public void setResults(List<Result> results) {
		this.results = results;
	}

	public Result addResult(Result result) {
		getResults().add(result);
		result.setUser(this);

		return result;
	}

	public Result removeResult(Result result) {
		getResults().remove(result);
		result.setUser(null);

		return result;
	}

    @Override
    public String toString() {
        return username;
    }

}