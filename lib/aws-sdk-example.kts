#!/usr/bin/env kscript

//INCLUDE sequence-pages.kt
//DEPS com.amazonaws:aws-java-sdk-servicediscovery:1.11.327
//DEPS com.amazonaws:aws-java-sdk-route53:1.11.327

import com.amazonaws.regions.Regions.*
import com.amazonaws.services.route53domains.AmazonRoute53DomainsClient
import com.amazonaws.services.route53domains.model.ListDomainsRequest
import com.amazonaws.services.servicediscovery.AWSServiceDiscoveryClient
import com.amazonaws.services.servicediscovery.model.ListServicesRequest
import com.amazonaws.services.servicediscovery.model.ListServicesResult

val sd = AWSServiceDiscoveryClient.builder().build()
val responses: Sequence<ListServicesResult> = depaginate(
        request = { println("calling..."); ListServicesRequest().withNextToken(it) },
        function = { sd.listServices(it) },
        nextToken = { it.nextToken })
val services = responses.map { it.services }.flatten()
println("services:")
services.forEach { println("service: ${it.arn}")}
println("")

val r53 = AmazonRoute53DomainsClient.builder()
        .withRegion(US_EAST_1)
        .build()
val domains = depaginate(
        request = { println("calling..."); ListDomainsRequest().withMarker(it) },
        function = { r53.listDomains(it) },
        nextToken = { it.nextPageMarker })
        .map { it.domains }
        .flatten()

println("domains:")
domains.forEach { println("  ${it.domainName} expires ${it.expiry}") }
