package co.com.mrcompany.r2dbc.Entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.util.ArrayList;
import java.util.List;

@Data
@Table("Role")
public class RoleEntity {

    @Id
    @Column("id")
    public Integer id;
    @Column("name")
    public String name;
    @Column("description")
    public String description;

    @Transient
    private List<UserEntity> users = new ArrayList<>();

    public List<UserEntity> getUsers() {
        return  users == null ? new ArrayList<>() : users;
    }
}
