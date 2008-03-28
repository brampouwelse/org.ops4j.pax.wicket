/*
 * Copyright 2007 Edward Yakop.
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
package org.ops4j.pax.wicket.internal;

import static org.apache.wicket.Application.set;

import javax.servlet.http.HttpServletRequest;

import org.apache.wicket.Page;
import org.apache.wicket.Request;
import org.apache.wicket.authorization.strategies.role.Roles;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.IWebApplicationFactory;
import org.apache.wicket.protocol.http.MockHttpServletRequest;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.protocol.http.WicketFilter;
import org.apache.wicket.util.tester.WicketTester;
import org.jmock.Expectations;
import org.jmock.integration.junit3.MockObjectTestCase;
import org.ops4j.pax.wicket.api.PaxWicketAuthenticator;
import org.osgi.framework.BundleContext;

public class PaxWicketSessionTestCase extends MockObjectTestCase
{

    public final void testAuthentication()
        throws Throwable
    {
        // Initialize [PaxAuthenticatedWicketApplication]
        BundleContext context = mock( BundleContext.class );
        String appName = "appName";
        DelegatingClassResolver classResolver = new DelegatingClassResolver( context, appName );
        PaxWicketPageFactory pageFac = new PaxWicketPageFactory( context, appName );
        PaxWicketAuthenticator authenticator = mock( PaxWicketAuthenticator.class );
        PaxAuthenticatedWicketApplication application = new PaxAuthenticatedWicketApplication(
            context, appName, "mountPoint", null, Page.class, pageFac, classResolver, authenticator, WebPage.class
        );
        set( application );

        IWebApplicationFactory appFactory = new IWebApplicationFactory()
        {
            public WebApplication createApplication( WicketFilter wicketFilter )
            {
                return new WicketTester.DummyWebApplication();
            }
        };
        WicketFilter filter = new PaxWicketFilter( appFactory );
        application.setWicketFilter( filter );

        // Invoke internal init;  TODO Is this needed?
//        Class<WebApplication> aClass = WebApplication.class;
//        Method method = aClass.getDeclaredMethod( "internalInit", (Class[]) null );
//        method.setAccessible( true );
//        method.invoke( application, (Object[]) null );

        HttpServletRequest httpRequest = new MockHttpServletRequest( application, null, null );
        Request request = new PaxWicketRequest( "/mock-point", httpRequest );
        PaxWicketSession session = new PaxWicketSession( application, request );
        //PaxWicketSession session = new PaxWicketSession( request );

        // Test unsuccesfull authentication
        Expectations exp1 = new Expectations();
        exp1.one( authenticator ).authenticate( null, null );
        exp1.will( exp1.returnValue( null ) );

        checking( exp1 );
        assertEquals( false, session.authenticate( null, null ) );

        // Test successfull authentication
        Expectations exp2 = new Expectations();
        String username = "efy";
        String password = "password";
        exp2.one( authenticator ).authenticate( username, password );
        exp2.will( exp2.returnValue( new Roles() ) );

        checking( exp2 );
        assertEquals( true, session.authenticate( username, password ) );
        assertEquals( username, session.getLoggedInUser() );
        assertEquals( new Roles(), session.getRoles() );

        try
        {
            session.invalidateNow();
        } catch( Throwable e )
        {
            // Expected because we haven't set up the application properly
            // This also set the invalidate has never been called :( I.e. 80% test method coverage
        }

        assertEquals( null, session.getLoggedInUser() );
    }
}
