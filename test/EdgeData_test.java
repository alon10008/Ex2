package test;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import api.*;

public class EdgeData_test {

    @Test
    void getDest() {
        EdgeData edge = new EdgeData(1,2,10.123456);
        assertEquals(2,edge.getDest());
    }

    @Test
    void getSrc() {
        EdgeData edge = new EdgeData(1,2,10.123456);
        assertEquals(1,edge.getSrc());
    }
    @Test
    void getWeight() {
        EdgeData edge = new EdgeData(1,2,10.123456);
        assertEquals(10.123456,edge.getWeight());
    }


}
