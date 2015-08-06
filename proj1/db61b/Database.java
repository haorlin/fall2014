package db61b;
import java.util.HashMap;

/** A collection of Tables, indexed by name.
 *  @author Hao Ran Raymond Lin */
class Database {
    /** An empty database. */
    public Database() {
        collection = new HashMap<String, Table>();
    }

    /** Return the Table whose name is NAME stored in this database, or null
     *  if there is no such table. */
    public Table get(String name) {
        return collection.get(name);
    }

    /** Set or replace the table named NAME in THIS to TABLE.  TABLE and
     *  NAME must not be null, and NAME must be a valid name for a table. */
    public void put(String name, Table table) {
        if (name == null || table == null) {
            throw new IllegalArgumentException("null argument");
        }
        collection.put(name, table);
    }
    /** collection is a hashmap that holds onto a number of table. */
    private HashMap<String, Table> collection;
}
