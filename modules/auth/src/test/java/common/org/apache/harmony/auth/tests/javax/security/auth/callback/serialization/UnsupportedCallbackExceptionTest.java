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

/**
* @author Maxim V. Makarov
* @version $Revision$
*/

package org.apache.harmony.auth.tests.javax.security.auth.callback.serialization;

import javax.security.auth.callback.LanguageCallback;
import javax.security.auth.callback.UnsupportedCallbackException;

import org.apache.harmony.testframework.serialization.SerializationTest;


/**
 * Serialization test for UnsupportedCallbackException class
 */

public class UnsupportedCallbackExceptionTest extends SerializationTest {

    static LanguageCallback nc = new LanguageCallback();

    public static void main(String[] args) {
        junit.textui.TestRunner.run(UnsupportedCallbackExceptionTest.class);
    }

    @Override
    protected Object[] getData() {
        return new Object[] {new UnsupportedCallbackException(nc)};
    }
}