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


package org.apache.skywalking.apm.agent.core.conf;

import org.apache.skywalking.apm.agent.core.dictionary.DictionaryUtil;

/**
 * The <code>RemoteDownstreamConfig</code> includes configurations from collector side.
 * All of them initialized null, Null-Value or empty collection.
 * 远程配置类
 * @author wusheng
 */
public class RemoteDownstreamConfig {
    public static class Agent {
        public volatile static int SERVICE_ID = DictionaryUtil.nullValue();

        public volatile static int SERVICE_INSTANCE_ID = DictionaryUtil.nullValue();

        public volatile static long INSTANCE_REGISTERED_TIME = DictionaryUtil.nullValue();
    }
}
