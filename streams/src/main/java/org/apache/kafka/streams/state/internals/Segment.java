/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.kafka.streams.state.internals;

import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.common.utils.Utils;
import org.apache.kafka.streams.processor.ProcessorContext;

import java.io.IOException;

// Use the Bytes wrapper for underlying rocksDB keys since they are used for hashing data structures
class Segment extends RocksDBStore<Bytes, byte[]> implements Comparable<Segment> {
    public final long id;

    Segment(String segmentName, String windowName, long id) {
        super(segmentName, windowName, WindowStoreUtils.INNER_KEY_SERDE, WindowStoreUtils.INNER_VALUE_SERDE);
        this.id = id;
    }

    void destroy() throws IOException {
        Utils.delete(dbDir);
    }

    @Override
    public int compareTo(Segment segment) {
        return Long.compare(id, segment.id);
    }

    @Override
    public void openDB(final ProcessorContext context) {
        super.openDB(context);

        // skip the registering step
    }

    @Override
    public String toString() {
        return "Segment(id=" + id + ", name=" + name() + ")";
    }
}
