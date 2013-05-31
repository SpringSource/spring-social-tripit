/*
 * Copyright 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.social.tripit.connect;

import org.springframework.social.oauth1.AbstractOAuth1ServiceProvider;
import org.springframework.social.oauth1.OAuth1Template;
import org.springframework.social.oauth1.OAuth1Version;
import org.springframework.social.tripit.api.TripIt;
import org.springframework.social.tripit.api.impl.TripItTemplate;

/**
 * TripIt ServiceProvider implementation.
 * @author Craig Walls
 */
public class TripItServiceProvider extends AbstractOAuth1ServiceProvider<TripIt> {

	public TripItServiceProvider(String consumerKey, String consumerSecret) {
		super(consumerKey, consumerSecret, new OAuth1Template(consumerKey, consumerSecret,
			"https://api.tripit.com/oauth/request_token",
			"https://www.tripit.com/oauth/authorize",			
			"https://api.tripit.com/oauth/access_token",
			OAuth1Version.CORE_10));
	}

	public TripIt getApi(String accessToken, String secret) {
		return new TripItTemplate(getConsumerKey(), getConsumerSecret(), accessToken, secret);
	}

}
