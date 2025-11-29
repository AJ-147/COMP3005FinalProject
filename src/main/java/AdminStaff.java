import jakarta.persistence.*;

@Entity
@Table(name="admin_staff")
public class AdminStaff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    public AdminStaff() {}

    public AdminStaff(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public int getId() {return id;}

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}
}
