package org.ops4j.pax.wicket.samples.shiro.internal;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;

public class HomePage extends WebPage {
    
    public HomePage() {

        add(new Label("oneComponent", "Welcome to the most simple pax-wicket application based on springdm"));
        add(new BookmarkablePageLink<Void>("securedPageLink", SecuredPage.class));
    }
}
