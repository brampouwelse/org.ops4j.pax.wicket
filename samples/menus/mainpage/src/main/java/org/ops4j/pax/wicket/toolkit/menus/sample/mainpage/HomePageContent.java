/*
 * Copyright 2006 Niclas Hedhman.
 *
 * Licensed  under the  Apache License,  Version 2.0  (the "License");
 * you may not use  this file  except in  compliance with the License.
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
package org.ops4j.pax.wicket.toolkit.menus.sample.mainpage;

import org.ops4j.pax.wicket.service.AbstractPageContent;
import org.ops4j.pax.wicket.service.DefaultPageContainer;
import org.ops4j.pax.wicket.toolkit.menus.PaxWicketMenu;
import org.ops4j.pax.wicket.toolkit.menus.sample.application.Application;
import org.osgi.framework.BundleContext;
import wicket.Page;
import wicket.PageParameters;

public class HomePageContent extends AbstractPageContent
{

    private DefaultPageContainer m_container;
    private PaxWicketMenu m_globalMenu;
    private PaxWicketMenu m_localMenu;

    public HomePageContent( BundleContext context, DefaultPageContainer container, String applicationName,
                            String pageName )
        throws IllegalArgumentException
    {
        super( context, "home", applicationName, pageName );
        m_container = container;
        m_globalMenu = new PaxWicketMenu( context, Application.NAME, "globalmenu", "mainmenu" );
        m_localMenu = new PaxWicketMenu( context, Application.NAME, "localmenu", "mainmenu" );
    }

    /**
     * Returns the page class instance represented by this {@code PageContent}.
     *
     * @return The page class represented by this {@code PageContent}.
     *
     * @since 1.0.0
     */
    public Class getPageClass()
    {
        return HomePage.class;
    }

    /**
     * Creates a page with the specified {@code params}.
     *
     * @param params The page parameters.
     *
     * @return An instance of page.
     *
     * @since 1.0.0
     */
    public Page createPage( PageParameters params )
    {
        return new HomePage( m_container, m_globalMenu, m_localMenu );
    }
}