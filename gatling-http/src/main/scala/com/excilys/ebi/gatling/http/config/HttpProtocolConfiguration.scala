/**
 * Copyright 2011-2013 eBusiness Information, Groupe Excilys (www.ebusinessinformation.fr)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 		http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.excilys.ebi.gatling.http.config

import com.excilys.ebi.gatling.core.config.ProtocolConfiguration
import com.excilys.ebi.gatling.core.util.RoundRobin
import com.excilys.ebi.gatling.http.response.ExtendedResponse
import com.ning.http.client.{ ProxyServer, Request }
import java.net.InetAddress

/**
 * HttpProtocolConfiguration class companion
 */
object HttpProtocolConfiguration {
	val HTTP_PROTOCOL_TYPE = "httpProtocol"

	lazy val DEFAULT_HTTP_PROTOCOL_CONFIG = HttpProtocolConfigurationBuilder.default.build
}

/**
 * Class containing the configuration for the HTTP protocol
 *
 * @param baseURL the radix of all the URLs that will be used (eg: http://mywebsite.tld)
 * @param proxy a proxy through which all the requests must pass to succeed
 */
case class HttpProtocolConfiguration(
	baseURLs: Option[Seq[String]],
	proxy: Option[ProxyServer],
	securedProxy: Option[ProxyServer],
	followRedirectEnabled: Boolean,
	automaticRefererEnabled: Boolean,
	cachingEnabled: Boolean,
	responseChunksDiscardingEnabled: Boolean,
	shareClient: Boolean,
	shareConnections: Boolean,
	baseHeaders: Map[String, String],
	virtualHost: Option[String],
  localAddress: Option[InetAddress],
	extraRequestInfoExtractor: Option[(Request => List[String])],
	extraResponseInfoExtractor: Option[(ExtendedResponse => List[String])]) extends ProtocolConfiguration {

	val protocolType = HttpProtocolConfiguration.HTTP_PROTOCOL_TYPE

	val roundRobinUrls = baseURLs.map(urls => RoundRobin(urls.toArray))

	def baseURL(): Option[String] = roundRobinUrls.map(_.next)
}