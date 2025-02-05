package org.jboss.cdi.tck.tests.build.compatible.extensions.priority;

import jakarta.annotation.Priority;
import jakarta.enterprise.inject.build.compatible.spi.BeanInfo;
import jakarta.enterprise.inject.build.compatible.spi.BuildCompatibleExtension;
import jakarta.enterprise.inject.build.compatible.spi.ClassConfig;
import jakarta.enterprise.inject.build.compatible.spi.Discovery;
import jakarta.enterprise.inject.build.compatible.spi.Enhancement;
import jakarta.enterprise.inject.build.compatible.spi.Messages;
import jakarta.enterprise.inject.build.compatible.spi.Registration;
import jakarta.enterprise.inject.build.compatible.spi.Validation;

import java.util.LinkedHashSet;
import java.util.List;

public class PriorityExtension implements BuildCompatibleExtension {
    private final LinkedHashSet<String> invocations = new LinkedHashSet<>();

    @Discovery
    @Priority(10)
    public void first() {
        invocations.add("1");
    }

    @Discovery
    @Priority(20)
    public void second() {
        invocations.add("2");
    }

    @Enhancement(types = Object.class, withSubtypes = true)
    @Priority(15)
    public void third(ClassConfig clazz) {
        invocations.add("3");
    }

    @Registration(types = Object.class)
    @Priority(5)
    public void fourth(BeanInfo clazz) {
        invocations.add("4");
    }

    @Validation
    @Priority(1_000)
    public void fifth() {
        invocations.add("5");
    }

    @Validation
    public void sixth() {
        invocations.add("6");
    }

    @Validation
    @Priority(10_000)
    public void seventh() {
        invocations.add("7");
    }

    @Validation
    @Priority(1_000_000)
    public void test(Messages msg) {
        if (!List.of("1", "2", "3", "4", "5", "6", "7").equals(List.copyOf(invocations))) {
            msg.error("Incorrect invocation order of extension methods");
        }
    }
}
