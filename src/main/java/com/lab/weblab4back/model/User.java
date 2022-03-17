package com.lab.weblab4back.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id", nullable = false)
    private Long id;
    @Column(unique = true)
    private String username;
    private String password;

    @ManyToMany(cascade = {CascadeType.ALL})
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Role> roles = new ArrayList<>();

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    @ToString.Exclude
    private List<Result> resultList = new ArrayList<>();

    public User(Long id, String username, String password, List<Result> resultList, List<Role> roles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.resultList = resultList;
        this.roles = roles;
    }

    public User(String username, String password) {
        this.username = username;
        this.password= password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return id != null && Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }


}
