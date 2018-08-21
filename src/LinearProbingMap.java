import java.util.Queue;
import java.util.LinkedList;

/**
 * @author Chiemela Nwoke
 * @version 1.1
 *
 * */


public class LinearProbingMap<Key, Value> implements Map<Key, Value> {

    public int N;
    public int M;
    public Entry<Key, Value>[] entries;

    public LinearProbingMap() {
        this(997);
    }

    public LinearProbingMap(int M) {
        this.N = 0;
        this.M = M;
        entries = new Entry[M];
    }


    public int hash(Key key) {
        return (key.hashCode() & 0x7fffffff) % M;
    }



    @Override
    public void put(Key key, Value val) {
        if (!contains(key))
            N++;

        if (contains(key)) {
            remove(key);
            N++;
        }
        int i = hash(key);
        while (true) {
            if (entries[i] == null) {
                entries[i] = new Entry(key, val);
                break;
            } else
                i = probeOffset(i);
        }
    }


    @Override
    public Value get(Key key) {
        for (int i = hash(key); entries[i] != null; i = (i + 1) % M) {
            if (entries[hash(key)].key.equals(key))
                return (Value) entries[hash(key)].value;
        }
        return null;
    }


    @Override
    public void remove(Key key) {
        if (!contains(key)) return;
        int i = hash(key);
        while (!key.equals(entries[i].key))
            i = probeOffset(i);
        entries[i] = null;
        i = probeOffset(i);
        while (entries[i] != null) {
            put(entries[i].key, entries[i].value);
            i = probeOffset(i);
        }
        N--;
    }


    @Override
    public boolean contains(Key key) {
        return get(key) != null ? true : false;
    }


    @Override
    public boolean isEmpty() {
        return size() == 0;
    }


    @Override
    public int size() {
        return this.N;
    }


    @Override
    public Iterable<Key> keys() {
        Queue<Key> queue = new LinkedList<Key>();
        for (int i = 0; i < M; i++)
            if (entries[i] != null) queue.add(entries[i].key);
        return queue;
    }



    protected int probeOffset(int i) {
        return i + 1 % M;
    }

    private class Entry<Key, Value> {
        public Key key;
        public Value value;

        public Entry(Key key, Value value) {
            this.key = key;
            this.value = value;
        }
    }



}