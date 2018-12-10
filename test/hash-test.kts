#!/usr/bin/env kscript

//INCLUDE ../lib/hash.kt

assertEquals(
	"f0e4c2f76c58916ec258f246851bea091d14d4247a2fc3e18694461b1816e13b",
	"asdf".hash("SHA-256").hexString())

fun <T> assertEquals(expected: T, actual: T) {
	if (actual != expected) {
		throw RuntimeException("expected $expected, got $actual")
	}
}

