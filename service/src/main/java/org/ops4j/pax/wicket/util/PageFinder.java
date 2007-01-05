/*
 * Copyright 2006 Niclas Hedhman.
 *
 * you may not use  this file  except in  compliance with the License.
 * Licensed  under the  Apache License,  Version 2.0  (the "License");
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed  under the  License is distributed on an "AS IS" BASIS,
 * WITHOUT  WARRANTIES OR CONDITIONS  OF ANY KIND, either  express  or
 * implied.
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.ops4j.pax.wicket.util;

import org.apache.log4j.Logger;
import org.ops4j.lang.NullArgumentException;
import org.ops4j.pax.wicket.api.ContentSource;
import org.ops4j.pax.wicket.api.PageContentSource;
import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;

import wicket.Page;


public class PageFinder
{
    private static final Logger LOGGER = Logger.getLogger( PageFinder.class );

    /**
     * Returns the page content from the specified {@code context} for the specified {@code applicationName} and
     * {@code pageName}.
     *
     * @param <T> The page subclass.
     * @param context The bundle context. This argument must not be {@code null}.
     * @param applicationName The application name. This argument must not be {@code null} or empty.
     * @param pageName The page name. This argument must not be {@code null} or empty.
     *
     * @return The page of the specified {@code applicationName} and {@code pageName}.
     * @throws IllegalArgumentException Thrown if one or some or all arguments are {@code null} or empty.
     * @since 1.0.0
     */
    public final static <T extends Page> PageContentSource<T>[] findPages(
            BundleContext context, String applicationName, String pageName )
        throws IllegalArgumentException
    {
        NullArgumentException.validateNotNull( context, "context" );
        NullArgumentException.validateNotEmpty( applicationName, "applicationName" );
        NullArgumentException.validateNotEmpty( pageName, "pageName" );

        String filter = "(&(" + ContentSource.APPLICATION_NAME + "=" + applicationName + ")" + "(" + ContentSource.PAGE_NAME + "="
                + pageName + "))";
        try
        {
            ServiceReference[] refs = context.getServiceReferences( PageContentSource.class.getName(), filter );
            if ( refs == null )
            {
                return new PageContentSource[0];
            }
            PageContentSource[] pageSources = new PageContentSource[refs.length];
            int count = 0;
            for ( ServiceReference ref : refs )
            {
                pageSources[count++] = (PageContentSource) context.getService( ref );
            }

            return pageSources;
        }
        catch ( InvalidSyntaxException e )
        {
            LOGGER.warn( "Invalid syntax [" + filter + "]. This should not happen unless if both application name " +
                    "and page name contains ldap filters.", e );

            // can not happen, RIGHT!
            return new PageContentSource[0];
        }
    }
}