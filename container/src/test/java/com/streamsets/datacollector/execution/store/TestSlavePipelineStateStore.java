/**
 * (c) 2015 StreamSets, Inc. All rights reserved. May not
 * be copied, modified, or distributed in whole or part without
 * written consent of StreamSets, Inc.
 */
package com.streamsets.datacollector.execution.store;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.HashMap;

import org.junit.Test;

import com.streamsets.datacollector.execution.PipelineStatus;
import com.streamsets.datacollector.execution.store.SlavePipelineStateStore;
import com.streamsets.datacollector.store.PipelineStoreException;
import com.streamsets.pipeline.api.ExecutionMode;

public class TestSlavePipelineStateStore {
  @Test
  public void testSaveState() throws Exception {
    SlavePipelineStateStore slavePipelineStateStore = new SlavePipelineStateStore();
    slavePipelineStateStore.saveState("user", "pipe1", "1", PipelineStatus.EDITED, "msg", new HashMap<String, Object>(),
      ExecutionMode.STANDALONE, null, 0, 0);
    assertEquals(PipelineStatus.EDITED, slavePipelineStateStore.getState("pipe1", "1").getStatus());
    try {
      slavePipelineStateStore.getState("blah", "blah");
      fail("Expected exception but didn't get any");
    } catch (PipelineStoreException ex) {
      // expected
    }

    try {
      slavePipelineStateStore.saveState("user", "pipe2", "1", PipelineStatus.EDITED, "msg", new HashMap<String, Object>(),
        ExecutionMode.STANDALONE, null, 0, 0);
      fail("Expected exception but didn't get any");
    } catch (PipelineStoreException ex) {
      // expected
    }
    try {
      slavePipelineStateStore.saveState("user", "pipe1", "2", PipelineStatus.EDITED, "msg", new HashMap<String, Object>(),
        ExecutionMode.STANDALONE, null, 0, 0);
      fail("Expected exception but didn't get any");
    } catch (PipelineStoreException ex) {
      // expected
    }
  }
}
