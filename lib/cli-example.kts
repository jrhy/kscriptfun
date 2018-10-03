#!/usr/bin/env kscript
//DEPS com.offbytwo:docopt:0.6.0.20150202,log4j:log4j:1.2.14

import org.docopt.Docopt
import org.apache.log4j.Logger;

import org.apache.log4j.ConsoleAppender
import org.apache.log4j.Level
import org.apache.log4j.PatternLayout

var console = ConsoleAppender() //create appender
val PATTERN = "%d [%p|%c] %m%n";
console.setLayout(PatternLayout(PATTERN));
console.setThreshold(Level.INFO);
console.activateOptions();
//add appender to any Logger (here is root)
Logger.getRootLogger().addAppender(console);


val log = Logger.getLogger(this::class.java.getName())
log.info("hi")

val usage = """
Use this cool tool to do cool stuff
Usage: cooltool.kts [options] <igenome> <fastq_files>...

Options:
 --gtf <gtfFile>     Custom gtf file instead of igenome bundled copy
 --pc-only           Use protein coding genes only for mapping and quantification
"""

val doArgs = Docopt(usage).parse(args.toList())

println("Hello from Kotlin!")
println("Parsed script arguments are: \n" + doArgs)
