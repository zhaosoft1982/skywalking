/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.apache.skywalking.apm.commons.datacarrier.common;

import org.junit.*;
import org.powermock.reflect.Whitebox;

public class FlexibleTimeTest {
    /**
     * This test case shows how retry time effect the sleep time.
     */
    @Test
    public void testSleepTimeCalculation() {
        FlexibleTime time = new FlexibleTime(512);
        Assert.assertEquals(1, time.value());
        Whitebox.setInternalState(time, "counter", 5); // 1 << 9
        Assert.assertEquals(1, time.value());

        Whitebox.setInternalState(time, "counter", 512); // 2 << 9
        Assert.assertEquals(25, time.value());

        Whitebox.setInternalState(time, "counter", 1024); // 3 << 9
        Assert.assertEquals(75, time.value());

        Whitebox.setInternalState(time, "counter", 1536);// 4 << 9
        Assert.assertEquals(150, time.value());

        Whitebox.setInternalState(time, "counter", 2048);// 5 << 9
        Assert.assertEquals(250, time.value());

        Whitebox.setInternalState(time, "counter", 2560); // 6 << 9
        Assert.assertEquals(350, time.value());

        Whitebox.setInternalState(time, "counter", 3072); // 7 << 9
        Assert.assertEquals(500, time.value());

        Whitebox.setInternalState(time, "counter", Integer.MAX_VALUE);
        Assert.assertEquals(500, time.value());

        int maxcounter = Whitebox.getInternalState(time, "counterMax");
        Assert.assertEquals(maxcounter, 7 << 9);
    }
}
