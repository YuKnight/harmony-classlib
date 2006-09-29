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
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * @author  Victor A. Martynov
 * @version $Revision: 1.6.4.3 $
 */
package java.rmi.activation;


/**
 * @com.intel.drl.spec_ref
 *
 * @author  Victor A. Martynov
 * @version $Revision: 1.6.4.3 $
 */
public class UnknownGroupException extends ActivationException {

    private static final long serialVersionUID = 7056094974750002460L;

    /**
     * @com.intel.drl.spec_ref
     */
    public UnknownGroupException(String message) {
        super(message);
    }
}
