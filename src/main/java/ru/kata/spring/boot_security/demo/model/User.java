package ru.kata.spring.boot_security.demo.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Objects;

@Entity
@Table
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message = "Поле не может быть пустым")
    @Size(min = 2, max = 20, message = "Длина имени должна быть от 2 до 20 символов")
    private String name;
    @NotEmpty(message = "Поле не может быть пустым")
    @Size(min = 2, max = 20, message = "Длина фамилии должна быть от 2 до 20 символов")
    private String surname;
    @Min(value = 0, message = "Возраст должен быть больше 0")
    @Max(value = 100, message = "Возраст должен быть меньше 100")
    private int age;
    @NotEmpty(message = "Поле не может быть пустым")
    @Email
    @Column(unique = true)
    private String userName;
    @NotEmpty(message = "Поле не может быть пустым")
    private String password;
    @Column
    private String role;

    public User() {
    }

    public User(String name, String userName, String password) {
        this.name = name;
        this.userName = userName;
        this.password = password;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String email) {
        this.userName = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public @NotEmpty(message = "Поле не может быть пустым") @Size(min = 2, max = 20, message = "Длина фамилии должна быть от 2 до 20 символов") String getSurname() {
        return surname;
    }

    public void setSurname(@NotEmpty(message = "Поле не может быть пустым") @Size(min = 2, max = 20, message = "Длина фамилии должна быть от 2 до 20 символов") String surname) {
        this.surname = surname;
    }

    @Min(value = 0, message = "Возраст должен быть больше 0")
    @Max(value = 100, message = "Возраст должен быть меньше 100")
    public int getAge() {
        return age;
    }

    public void setAge(@Min(value = 0, message = "Возраст должен быть больше 0") @Max(value = 100, message = "Возраст должен быть меньше 100") int age) {
        this.age = age;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(name, user.name) &&
                Objects.equals(userName, user.userName) &&
                Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, userName, password);
    }
}
