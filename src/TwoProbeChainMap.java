import java.util.LinkedList;

/**
 * @author Chiemela Nwoke
 * @version 1.1
 *
 * */

public class TwoProbeChainMap<Key, Value>  implements Map<Key, Value>  {

    public int N;
    public int M;
    public LinkedList<Entry>[] entries;

    public TwoProbeChainMap(){

        this(997);
    }


    //constructor
    public TwoProbeChainMap(int M){
        this.N = 0;
        this.M = M;
        entries = (LinkedList<Entry>[]) new LinkedList[M];
        for (int i = 0; i < M; i++){
            entries[i] = new LinkedList<>();
        }
    }

    //first hash function
    public int hash(Key key){
        return (key.hashCode() & 0x7fffffff) % M;
    }

    //Second hash function
    public int hash2(Key key){
        return (((key.hashCode() & 0x7fffffff) % M) * 31) % M;
    }

    @Override
    public Value get(Key key){
        int i = 0;
        while (i < entries[hash(key)].size()){
            if (entries[hash(key)].get(i).key.equals(key))  return (Value) entries[hash(key)].get(i).value;{

                i++;
            }

        }
        return null;
    }

    @Override
    public void put(Key key, Value val) {
        if (!contains(key)) {
            N++;
            if (contains(key)) {
                remove(key);
                N++;
            }

            Entry temp = new Entry(key, val);
            if (entries[hash(key)].size() <= entries[hash2(key)].size())
                entries[hash(key)].add(temp);

        }
    }


    @Override
    public void remove(Key key) {
        if(!contains(key)) return;
        N--;
        int i = 0;
        Entry temp = null;
        while (i < entries[hash(key)].size()) {
            if (entries[hash(key)].get(i).key.equals(key)) {
                entries[hash(key)].remove();
                i++;
            }
        }
        i++;

        while (i < entries[hash2(key)].size()) {
            if (entries[hash2(key)].get(i).key.equals(key)) {
                entries[hash(key)].remove();
                i++;
            }

        }

    }

    @Override
    public boolean contains(Key key) {
        return get(key) != null? true : false;
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
        LinkedList<Key> queue = new LinkedList<Key>();
        for (int i = 0; i < M; i++){
            if (entries[i] != null)
                if (entries[i].size() > 0)
                    queue.add((Key) entries[i].get(0).key);
        }
        return queue;
    }



    private class Entry{
        public Key key;
        public Value value;

        public Entry(Key key, Value value) {
            this.key = key;
            this.value = value;
        }
    }


}
