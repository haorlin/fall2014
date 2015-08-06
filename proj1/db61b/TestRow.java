package db61b;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import static org.junit.Assert.*;

public class TestRow {

    @Test
    public void testRow() {
        Row r = new Row(new String[] { "Rompers", "on", "3" });
        Row r2 = new Row(new String[] { "Rompers", "on", "3" });
        Row r3 = new Row(new String[] { "Rompers", "onn", "3" });
        assertEquals(3, r.size());
        assertEquals("Rompers", r.get(0));
        assertEquals("3", r.get(2));
        assertEquals(true, r.equals(r2));
        assertEquals(false, r.equals(r3));
    }

    @Test
    public void testRowConstructor() {
        Table t1 = Table.readTable("students");
        List<Column> colList = new ArrayList<Column>();
        Column c1 = new Column(t1.getTitle(0), t1);
        Column c2 = new Column(t1.getTitle(1), t1);
        Column c3 = new Column(t1.getTitle(2), t1);
        colList.add(c1);
        colList.add(c2);
        colList.add(c3);
        Row r1 = new Row(new String[] { "101", "Knowles",
                                        "Jason", "F", "2003", "EECS" });
        Row newRow = new Row(colList, r1);
        Row newRowReal = new Row(new String[] { "101", "Knowles", "Jason" });
        assertEquals(newRowReal, newRow);
    }

    public static void main(String[] args) {
        System.exit(ucb.junit.textui.runClasses(TestRow.class));
    }
}
