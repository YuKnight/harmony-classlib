/*
 * Copyright 2005-2006 The Apache Software Foundation or its licensors, as applicable
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
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
 * @author  Vasily Zakharov
 * @version $Revision: 1.1.2.2 $
 */
package org.apache.harmony.jndi.provider.rmi.registry;

import java.rmi.Remote;
import java.rmi.RemoteException;

import java.rmi.server.UnicastRemoteObject;

import javax.naming.Reference;


/**
 * This class stores a {@link Reference}
 * and provides {@link Remote} access to it.
 *
 * @author  Vasily Zakharov
 * @version $Revision: 1.1.2.2 $
 */
public class RemoteReferenceWrapper extends UnicastRemoteObject
        implements RemoteReference {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -4565312021092681908L;

    /**
     * Wrapped reference.
     */
    protected Reference reference;

    /**
     * Creates wrapper for the specified reference.
     *
     * @param   reference
     *          Reference to wrap.
     *
     * @throws  RemoteException
     *          If object export failed.
     */
    public RemoteReferenceWrapper(Reference reference) throws RemoteException {
        this.reference = reference;
    }

    /**
     * Returns the wrapped reference.
     *
     * @return  Wrapped reference.
     */
    public Reference getReference() {
        return reference;
    }
}
