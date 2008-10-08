/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.apache.harmony.pack200;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class IcBands extends BandSet {

    private final List innerClasses = new ArrayList();
    private final SegmentHeader segmentHeader;

    public IcBands(SegmentHeader segmentHeader) {
        this.segmentHeader = segmentHeader;
    }

    public void finaliseBands() {
        segmentHeader.setIc_count(innerClasses.size());
    }

    public void pack(OutputStream out) {
        // TODO Auto-generated method stub

    }

    public void addInnerClass(String name, String outerName, String innerName,
            int flags) {
        // TODO Auto-generated method stub

    }

}
