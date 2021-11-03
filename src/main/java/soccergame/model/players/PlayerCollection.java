package soccergame.model.players;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * An iterable doubly linked list of {@code GamePlayer} objects.
 */
public class PlayerCollection implements Iterable<GamePlayer> {
    /**
     * The head and tail for this list.
     */
    protected Node head, tail;
    /**
     * The number of elements in this list.
     */
    protected int size = 0;

    /**
     * Sort the list using the {@code GamePlayer} comparator.
     */
    public void sort() {
        Node current, index;
        GamePlayer temp;
        if (head != null) {
            for (current = head; current.next != null; current = current.next) {
                for (index = current.next; index != null; index = index.next) {
                    if (current.data.compareTo(index.data) > 0) {
                        temp = current.data;
                        current.data = index.data;
                        index.data = temp;
                    }
                }
            }
        }
    }

    /**
     * Get a {@code GamePlayer} by their player name.
     *
     * @param playerName the player name to search for
     * @return the player with {@code playerName}
     */
    public GamePlayer get(String playerName) {
        for (GamePlayer player : this) {
            if (player.getPlayerName().equalsIgnoreCase(playerName))
                return player;
        }
        return null;
    }

    /**
     * Get a {@code GamePlayer} by index.
     *
     * @param index the index to search for
     * @return the player at {@code index}
     */
    public GamePlayer get(int index) {
        int i = 0;
        for (GamePlayer player : this) {
            if (i++ == index)
                return player;
        }
        return null;
    }

    /**
     * Add a collection of {@code GamePlayer} objects to this list.
     *
     * @param players the collection of players to add
     */
    public void addAll(Collection<GamePlayer> players) {
        for (GamePlayer player : players)
            add(player);
    }

    /**
     * Add a single {@code GamePlayer} to this list.
     *
     * @param player the player to add
     */
    public void add(GamePlayer player) {
        Node newNode = new Node(player);
        if (head == null) {
            head = tail = newNode;
            head.prev = null;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        tail.next = null;
        size++;
    }

    /**
     * The number of {@code GamePlayer} objects in this list.
     *
     * @return this size of this list
     */
    public int size() {
        return size;
    }

    /**
     * Get the head {@code Node}.
     *
     * @return the head {@code Node} of this list
     */
    private Node getHead() {
        return head;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterator<GamePlayer> iterator() {
        return new PlayerCollectionIterator(this);
    }

    /**
     * A custom doubly linked list node implementation.
     */
    private static class Node {
        private GamePlayer data;
        private Node next;
        private Node prev;

        /**
         * Construct a new {@code Node} for this list.
         *
         * @param data the {@code GamePlayer} of this node
         */
        public Node(GamePlayer data) {
            this.data = data;
        }
    }

    /**
     * A custom {@code Iterator} for this {@code GamePlayer} list. It simply
     * traverses this double linked list using a reference to the list.
     */
    static class PlayerCollectionIterator implements Iterator<GamePlayer> {
        /**
         * The current {@code Node} in the traversal.
         */
        private Node current;

        /**
         * Construct a {@code PlayerCollectionIterator} with a specified {@code PlayerCollection}.
         *
         * @param playerCollection the {@code PlayerCollection} to iterate
         */
        public PlayerCollectionIterator(PlayerCollection playerCollection) {
            this.current = playerCollection.getHead();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean hasNext() {
            return current != null;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public GamePlayer next() {
            if (!hasNext()) throw new NoSuchElementException();
            GamePlayer tmp = current.data;
            current = current.next;
            return tmp;
        }
    }
}
