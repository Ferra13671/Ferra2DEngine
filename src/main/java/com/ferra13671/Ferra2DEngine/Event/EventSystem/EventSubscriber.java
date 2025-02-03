package com.ferra13671.Ferra2DEngine.Event.EventSystem;

import com.ferra13671.Ferra2DEngine.Event.EventSystem.EventModifiers.EventPriority;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Ferra13671
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface EventSubscriber {
    int priority() default EventPriority.NORMAL;
}