/*
 * JBoss, Home of Professional Open Source
 * Copyright 2010, Red Hat, Inc., and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,  
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.cdi.tck.tests.context.conversation.event;

import static org.jboss.cdi.tck.TestGroups.INTEGRATION;
import static org.jboss.cdi.tck.cdi.Sections.CONVERSATION_CONTEXT_EE;
import static org.testng.Assert.assertTrue;

import java.net.URL;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.cdi.tck.AbstractTest;
import org.jboss.cdi.tck.shrinkwrap.WebArchiveBuilder;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.test.audit.annotations.SpecAssertion;
import org.jboss.test.audit.annotations.SpecAssertions;
import org.jboss.test.audit.annotations.SpecVersion;
import org.testng.annotations.Test;

import com.gargoylesoftware.htmlunit.TextPage;
import com.gargoylesoftware.htmlunit.WebClient;

/**
 * <p>
 * This test was originally part of Weld test suite.
 * <p>
 * 
 * @author Jozef Hartinger
 * @author Martin Kouba
 */
@Test(groups = INTEGRATION)
@SpecVersion(spec = "cdi", version = "2.0-EDR2")
public class TransientConversationLifecycleEventTest extends AbstractTest {

    @ArquillianResource(Servlet.class)
    private URL contextPath;

    @Deployment(testable = false)
    public static WebArchive createTestArchive() {
        return new WebArchiveBuilder()
                .withTestClassDefinition(TransientConversationLifecycleEventTest.class)
                .withClasses(Servlet.class, ConversationScopedObserver.class, ApplicationScopedObserver.class,
                        ConversationScopedBean.class).build();
    }

    @Test
    @SpecAssertions({ @SpecAssertion(section = CONVERSATION_CONTEXT_EE, id = "ba"), @SpecAssertion(section = CONVERSATION_CONTEXT_EE, id = "bc") })
    public void testLifecycleEventFiredForTransientConversation() throws Exception {

        WebClient client = new WebClient();

        TextPage page = client.getPage(contextPath + "/display-transient");
        assertTrue(page.getContent().contains("Initialized:true")); // the current transient
        assertTrue(page.getContent().contains("Destroyed:false")); // not destroyed yet

        page = client.getPage(contextPath + "/display");
        assertTrue(page.getContent().contains("Initialized:true")); // the current transient
        assertTrue(page.getContent().contains("Destroyed:true")); // the previous transient was destroyed
    }
}
