package fr.inria.jtravis.helpers;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BuildSortingTest {

    @Test
    public void testMultipleCallsOnBuildSortingReturnsDifferentResult() {
        assertEquals("finished_at:desc", new BuildsSorting().byFinishedAtDesc().build());
        assertEquals("finished_at", new BuildsSorting().byFinishedAt().build());
    }
}
