package lab5.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity(name = "Cell")
@Table(name = "cell")
public class Cell {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "alive", nullable = false)
    private boolean alive;

    @Column(name = "ship", nullable = false)
    private boolean ship;

    @ManyToOne
    @JoinColumn(name = "field_id")
    private Field field;

    public Cell() {
    }

    public Cell(Long id, boolean alive, boolean ship, Field field) {
        this.id = id;
        this.alive = alive;
        this.ship = ship;
        this.field = field;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public boolean isShip() {
        return ship;
    }

    public void setShip(boolean ship) {
        this.ship = ship;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }
}
