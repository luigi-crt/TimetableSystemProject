package model;

/**
 * A teaching room in the University's campus
 */
public class Room {
    private String roomId;
    private int capacity;
    private boolean isLab;

    /**
     * Constructs a Room
     * @param roomId the identifier for the room
     * @param capacity the number of students the room can hold
     * @param isLab if true room is a lab if false room is not a lab
     */
    public Room(String roomId, int capacity, boolean isLab) {
        this.roomId = roomId;
        this.capacity = capacity;
        this.isLab = isLab;
    }

    /**
     * Getter method for the room's unique identifier
     * @return the room's id
     */
    public String getRoomId() { return roomId; }

    /**
     * Getter for the capcity of the room
     * @return room's capacity
     */
    public int getCapacity() { return capacity; }

    /**
     * Indicates whether this rooms is a lab
     * @return if a room is a lab return true otherwise return false
     */
    public boolean isLab() { return isLab; }
}
