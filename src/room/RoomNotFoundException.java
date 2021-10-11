package room;

/**
 * An exception which indicates that there is no <code>Room</code> with the given ID.
 * This is a runtime exception since its occurrence predictable and should be avoided.
 */
class RoomNotFoundException extends RuntimeException {
}
