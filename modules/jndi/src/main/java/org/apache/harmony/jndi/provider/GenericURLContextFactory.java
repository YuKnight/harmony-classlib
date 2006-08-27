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
 * @version $Revision: 1.1.2.3 $
 */
package org.apache.harmony.jndi.provider;

import java.util.Hashtable;

import javax.naming.ConfigurationException;
import javax.naming.Context;
import javax.naming.Name;
import javax.naming.NamingException;

import javax.naming.spi.ObjectFactory;


/**
 * Base class for URL naming context factory implementations.
 *
 * In many cases, subclasses should only override
 * {@link #createURLContext(Hashtable)} method
 * and provide public no-args constructor.
 *
 * @author  Vasily Zakharov
 * @version $Revision: 1.1.2.3 $
 */
public abstract class GenericURLContextFactory implements ObjectFactory {

    /**
     * Default constructor for subclasses.
     */
    protected GenericURLContextFactory() {}

    /**
     * Lookups the specified object in the underlying context.
     * Underlying context instance is provided by
     * {@link #createURLContext(Hashtable)}.
     *
     * Follows the guidelines for URL context factories described in
     * {@link ObjectFactory#getObjectInstance(Object, Name, Context, Hashtable)}
     * specification.
     *
     * If <code>obj</code> is <code>null</code>,
     * just creates and returns an underlying context.
     *
     * If <code>obj</code> is a proper URL string,
     * lookups and returns an object specified by that string.
     *
     * If <code>obj</code> is an array of URL strings,
     * tries to lookup each of them sequentially until lookup succeeds,
     * then returns the result. If no lookup succeeds, throws
     * {@link NamingException} describing the fail of a last lookup.
     *
     * <code>name</code> and <code>nameCtx</code> parameters are ignored.
     *
     * @param   obj
     *          Object to lookup, can be <code>null</code>.
     *
     * @param   name
     *          Ignored.
     *
     * @param   nameCtx
     *          Ignored.
     *
     * @param   environment
     *          Environment to use in creating the underlying context,
     *          can be <code>null</code>.
     *
     * @return  The object created.
     *
     * @throws  ConfigurationException
     *          If <code>obj</code> is neither <code>null</code>
     *          nor a string, nor a string array, or is an empty string array.
     *
     * @throws  NamingException
     *          If lookup attempt failed.
     */
    public Object getObjectInstance(Object obj, Name name, Context nameCtx,
            Hashtable environment) throws NamingException {
        Context context = createURLContext(environment);

        if (obj == null) {
            // For null object - just return context.
            return context;
        }

        try {
            if (obj instanceof String) {
                // For string object - return the object it names.
                return context.lookup((String) obj);
            }

            if (obj instanceof String[]) {
                // For string array object - search it through.
                String[] strings = (String[]) obj;

                if (strings.length < 1) {
                    throw new ConfigurationException(
                            "obj is an empty string array");
                }

                NamingException exception = null;

                for (int i = 0; i < strings.length; i++) {
                    try {
                        // If the valid object is found - return it.
                        return context.lookup(strings[i]);
                    } catch (NamingException e) {
                        // Invalid object, store the exception
                        // to throw it later if no valid object is found.
                        exception = e;
                    }
                }

                // No valid object is found.
                throw exception;
            }

            // Unknown object type.
            throw new IllegalArgumentException("obj is neither null, "
                    + "nor a string, nor a string array: " + obj);
        } finally {
            context.close();
        }
    }

    /**
     * Returns new instance of the necessary context.
     * Used by {@link #getObjectInstance(Object, Name, Context, Hashtable)}.
     *
     * Must be overridden by particular URL context factory implementations.
     *
     * @param   environment
     *          Environment.
     *
     * @return  New context instance.
     */
    protected abstract Context createURLContext(Hashtable environment);
}
