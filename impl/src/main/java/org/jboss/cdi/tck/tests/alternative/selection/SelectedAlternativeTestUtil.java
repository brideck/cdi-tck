/*
 * JBoss, Home of Professional Open Source
 * Copyright 2012, Red Hat, Inc., and individual contributors
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

package org.jboss.cdi.tck.tests.alternative.selection;

import org.jboss.cdi.tck.shrinkwrap.EnterpriseArchiveBuilder;
import org.jboss.cdi.tck.shrinkwrap.WebArchiveBuilder;

/**
 * @author Martin Kouba
 *
 */
public final class SelectedAlternativeTestUtil {

    public static WebArchiveBuilder createBuilderBase() {
        return new WebArchiveBuilder().withLibrary(SelectedAlternativeTestUtil.class, TestBean.class, Wild.class, Tame.class,
                AssertBean.class);
    }

    public static EnterpriseArchiveBuilder createEnterpriseBuilderBase() {
        return new EnterpriseArchiveBuilder().withLibrary(SelectedAlternativeTestUtil.class, TestBean.class, Wild.class,
                Tame.class, AssertBean.class);
    }

}
