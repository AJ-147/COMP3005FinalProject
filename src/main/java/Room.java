import jakarta.persistence.*;

@Entity
@Table(name="rooms")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false,unique = true)
    private int roomNumber;

    @Column(nullable = false)
    private int capacity;

    public Room(){}

    public Room(int roomNumber, int capacity) {
        this.roomNumber = roomNumber;
        this.capacity = capacity;
    }

    public int getId() {return id;}

    public int getRoomNumber() {return roomNumber;}
    public void setRoomNumber(int roomNumber) {this.roomNumber = roomNumber;}

    public int getCapacity() {return capacity;}
    public void setCapacity(int capacity) {this.capacity = capacity;}
}
