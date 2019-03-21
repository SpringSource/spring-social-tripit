/*
 * Copyright 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.social.tripit.api.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.springframework.social.tripit.api.Trip;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * Holder class for Trips. 
 * Relies on custom deserializer to handle cases where the upcoming list of trips is either an actual list or just a single object.
 * @author Craig Walls
 */
@JsonIgnoreProperties(ignoreUnknown = true)
class TripList {

	private final List<Trip> trips;
	
	@JsonCreator
	public TripList(
			@JsonProperty("Trip") @JsonDeserialize(using = TripListDeserializer.class) List<Trip> trips) {
		this.trips = trips != null ? trips : Collections.<Trip>emptyList();
	}

	public List<Trip> getList() {
		return trips;
	}
	
	private static class TripListDeserializer extends JsonDeserializer<List<Trip>> {
		@Override
		public List<Trip> deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.registerModule(new TripItModule());
			
			JsonNode node = jp.readValueAs(JsonNode.class);
			if(node.asToken() == JsonToken.START_OBJECT) {
				Trip trip = objectMapper.reader(Trip.class).readValue(node);
				return Collections.singletonList(trip);
			} else if(node.asToken() == JsonToken.START_ARRAY) {
				List<Trip> trips = new ArrayList<Trip>(node.size());
				for(Iterator<JsonNode> iterator = node.elements(); iterator.hasNext(); ) {
					trips.add((Trip) objectMapper.reader(Trip.class).readValue(iterator.next()));
				}
				return trips;
			}
			
			return Collections.emptyList();
		}
	}
}
