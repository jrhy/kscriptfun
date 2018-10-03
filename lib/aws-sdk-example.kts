#!/usr/bin/env kscript

//DEPS com.amazonaws:aws-java-sdk-servicediscovery:1.11.327
//DEPS com.amazonaws:aws-java-sdk-route53:1.11.327

import com.amazonaws.ClientConfiguration
import com.amazonaws.ClientConfigurationFactory
import com.amazonaws.regions.Regions
import com.amazonaws.regions.Regions.*
import com.amazonaws.services.route53domains.AmazonRoute53DomainsClient
import com.amazonaws.services.route53domains.AmazonRoute53DomainsClientBuilder
import com.amazonaws.services.servicediscovery.AWSServiceDiscoveryClient
import com.amazonaws.services.servicediscovery.model.ListServicesRequest

val sd = AWSServiceDiscoveryClient.builder().build()
val services = sd.listServices(ListServicesRequest()).services
println("${services.size} services:")
services.forEach { println("  $it") }
println("")

val r53 = AmazonRoute53DomainsClient.builder()
        .withRegion(US_EAST_1)
        .build()
val domains = r53.listDomains().domains
println("${domains.size} domains:")
domains.forEach { println("  ${it.domainName}") }
