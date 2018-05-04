package online.kheops.auth_server.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("unused")
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "pk")
    private long pk;

    @Basic(optional = false)
    private String username;

    @Basic(optional = false)
    private String email;

    @ManyToMany
    @JoinTable (name = "user_series",
            joinColumns = @JoinColumn(name = "user_fk"),
            inverseJoinColumns = @JoinColumn(name = "series_fk"))
    private Set<Series> series = new HashSet<>();

    public User() {}

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public long getPk() {
        return pk;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Set<Series> getSeries() {
        return series;
    }
}
