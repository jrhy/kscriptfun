#!/usr/bin/env kscript
//DEPS log4j:log4j:1.2.14

import org.apache.log4j.Logger;

import org.apache.log4j.ConsoleAppender
import org.apache.log4j.Level
import org.apache.log4j.PatternLayout

var console = ConsoleAppender() //create appender
val PATTERN = "%d [%p|%c] %m%n";
console.setLayout(PatternLayout(PATTERN));
console.setThreshold(Level.INFO);
console.activateOptions();
Logger.getRootLogger().addAppender(console);

val log = Logger.getLogger(this::class.java.getName())
log.info("hi")

