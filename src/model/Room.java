package model;

public class Room {
    private String roomId;
    private int capacity;
    private boolean isLab;

    public Room(String roomId, int capacity, boolean isLab) {
        this.roomId = roomId;
        this.capacity = capacity;
        this.isLab = isLab;
    }

    public String getRoomId() { return roomId; }
    public int getCapacity() { return capacity; }
    public boolean isLab() { return isLab; }
}
