package db61b;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import static org.junit.Assert.*;

public class TestTable {

    @Test
    public void testTable() {
        Row r = new Row(new String[] {"Sushi", "28", "392"});
        Row r2 = new Row(new String[] {"Frank", "30", "213"});
        Row r3 = new Row(new String[] {"Fangblade", "200", "1"});
        Table t = new Table(new String[] {"Username", "Level", "Rank"});
        t.add(r);
        t.add(r2);
        t.add(r3);
        assertEquals(3, t.columns());
        assertEquals(1, t.findColumn("Level"));
        assertEquals(-1, t.findColumn("Weed"));
        assertEquals(3, t.size());
        t.print();
        Table t2 = Table.readTable("enrolled");
        t2.print();
        t.writeTable("Testing");
    }

    @Test
    public void testEquijoin() {
        Table t1 = Table.readTable("students");
        Table t2 = Table.readTable("enrolled");
        List<Column> colList = new ArrayList<Column>();
        List<Column> colList2 = new ArrayList<Column>();
        Column c1 = new Column(t1.getTitle(0), t1, t2);
        Column c2 = new Column(t2.getTitle(0), t1, t2);
        colList.add(c1);
        colList2.add(c2);
        Row r1 = new Row(new String[] { "101", "Knowles",
                                        "Jason", "F", "2003", "EECS" });
        Row r2 = new Row(new String[] { "101", "21228", "B" });
    }

    public static void main(String[] args) {
        System.exit(ucb.junit.textui.runClasses(TestTable.class));
    }
}
