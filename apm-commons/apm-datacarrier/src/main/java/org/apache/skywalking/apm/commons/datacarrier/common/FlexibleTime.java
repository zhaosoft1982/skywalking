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

/**
 * FlexibleTime is used in the some thread sleep scenarios.
 *
 * When threads interactive model doesn't want to use notify mechanism to coordinate, and purely depend on sleep time,
 * this could help on sleep time decision.
 *
 * With the times of failure try, the FlexibleTime would choose longer in given time buckets.
 */
public class FlexibleTime {
    private static final int[] DEFAULT = {1, 25, 75, 150, 250, 350, 500};
    private final int[] buckets;
    private volatile int counter = 0;
    private int counterMax;
    private final int factor = 9;

    public FlexibleTime() {
        buckets = DEFAULT;
        counterMax = buckets.length << factor;
    }

    /**
     * Tried to finish the target work, but fail. Be potential make time longer.
     */
    public void triedOnce() {
        if (counter < counterMax) {
            counter++;
        }
    }

    /**
     * Tried and success. Then the wait time would be shorter.
     */
    public void success() {
        if (counter > 0) {
            counter--;
            if (counter < 0) {
                // to avoid minus concurrently.
                counter = 0;
            }
        }
    }

    public long value() {
        int i = counter >> factor;
        if (i > buckets.length - 1) {
            i = buckets.length - 1;
        }
        // Avoid at this moment, when success trigger the counter to be negative value.
        if (i < 0) {
            i = 0;
        }
        return buckets[i];
    }
}
