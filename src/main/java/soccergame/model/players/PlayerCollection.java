package soccergame.model.players;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class PlayerCollection implements  Iterable<GamePlayer> {

    private static class Node {
        private GamePlayer data;
        private Node next;
        private Node prev;

        public Node(GamePlayer data) {
            this.data = data;
        }
    }

    protected Node head, tail;
    protected int size = 0;

    public void sort() {
        Node current, index;
        GamePlayer temp;
        if (head != null) {
            for(current = head; current.next != null; current = current.next) {
                for(index = current.next; index != null; index = index.next) {
                    if(current.data.compareTo(index.data) > 0) {
                        temp = current.data;
                        current.data = index.data;
                        index.data = temp;
                    }
                }
            }
        }
    }

    public GamePlayer get(String playerName) {
        for (GamePlayer player: this) {
            if (player.getPlayerName().equalsIgnoreCase(playerName))
                return player;
        }
        return null;
    }

    public GamePlayer get(int index) {
        int i = 0;
        for (GamePlayer player: this) {
            if (i++ == index)
                return player;
        }
        return null;
    }

    public void addAll(Collection<GamePlayer> players) {
        for(GamePlayer player: players)
            add(player);
    }

    public void add(GamePlayer player) {
        Node newNode = new Node(player);
        if(head == null) {
            head = tail = newNode;
            head.prev = null;
        }
        else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        tail.next = null;
        size++;
    }

    public int size() {
        return size;
    }

    private Node getHead() {
        return head;
    }

    @Override
    public Iterator<GamePlayer> iterator() {
        return new PlayerCollectionIterator(this);
    }

    static class PlayerCollectionIterator implements Iterator<GamePlayer> {
        private Node current;

        public PlayerCollectionIterator(PlayerCollection playerCollection) {
            this.current = playerCollection.getHead();
        }
        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public GamePlayer next() {
            if (!hasNext()) throw new NoSuchElementException();
            GamePlayer tmp = current.data;
            current = current.next;
            return tmp;
        }
    }
}
