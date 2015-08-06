package db61b;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import static org.junit.Assert.*;

public class TestCondition {
    @Test
    public void testTable() {
        Table t1 = Table.readTable("students");
        Table t2 = Table.readTable("enrolled");
        Column c1 = new Column(t1.getTitle(t1.findColumn("SID")), t1, t2);
        Column c2 = new Column(t2.getTitle(t2.findColumn("SID")), t1, t2);
        Condition cond = new Condition(c1, "=", c2);
        Row r1 = new Row(new String[] { "101", "Knowles",
                                        "Jason", "F", "2003", "EECS" });
        Row r2 = new Row(new String[] { "101", "21228", "B" });
        assertEquals(true, cond.test(r1, r2));
        Column c3 = new Column(t1.getTitle(t1.findColumn("Lastname")), t1);
        Condition cond2 = new Condition(c3, "=", "Chan");
        Condition cond3 = new Condition(c3, "=", "Knowles");
        assertEquals(false, cond2.test(r1));
        assertEquals(true, cond3.test(r1));
        Column c4 = new Column(t1.getTitle(t1.findColumn("SemEnter")), t1);
        Row r3 = new Row(new String[] { "102", "Chan", "Valerie",
                                        "S", "2003", "Math" });
        Row r4 = new Row(new String[] { "106", "Chan", "Yangfan",
                                        "F", "2003", "LSUnd" });
        List<Condition> listCond1 = new ArrayList<Condition>();
        Condition cond4 = new Condition(c4, "<", "L");
        listCond1.add(cond2);
        listCond1.add(cond4);
        assertEquals(false, Condition.test(listCond1, r3));
        assertEquals(true, Condition.test(listCond1, r4));
    }

    public static void main(String[] args) {
        System.exit(ucb.junit.textui.runClasses(TestCondition.class));
    }
}
