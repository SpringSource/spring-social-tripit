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

import static org.junit.Assert.*;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.tripit.api.TripIt;
import org.springframework.social.tripit.api.TripItProfile;

public class TripItAdapterTest {

	private TripItAdapter apiAdapter = new TripItAdapter();
	
	private TripIt tripit = Mockito.mock(TripIt.class);
	
	@Test
	public void fetchProfile() {
		Mockito.when(tripit.getUserProfile()).thenReturn(new TripItProfile("habuma", "habuma", "Craig Walls", "cwalls@vmware.com", "Plano, TX", "SpringSource", "people/habuma", "http://static.tripit.com/uploads/images/0/0/6/006b210269799fa70ff6ae2c0cdb8a41e9c.jpg"));
		UserProfile profile = apiAdapter.fetchUserProfile(tripit);
		assertEquals("Craig Walls", profile.getName());
		assertEquals("Craig", profile.getFirstName());
		assertEquals("Walls", profile.getLastName());
		assertEquals("cwalls@vmware.com", profile.getEmail());
		assertEquals("habuma", profile.getUsername());
	}

	@Test
	public void fetchProfileFirstNameOnly() {
		Mockito.when(tripit.getUserProfile()).thenReturn(new TripItProfile("habuma", "habuma", "Craig", "cwalls@vmware.com", "Plano, TX", "SpringSource", "people/habuma", "http://static.tripit.com/uploads/images/0/0/6/006b210269799fa70ff6ae2c0cdb8a41e9c.jpg"));
		UserProfile profile = apiAdapter.fetchUserProfile(tripit);
		assertEquals("Craig", profile.getName());
		assertEquals("Craig", profile.getFirstName());
		assertNull(profile.getLastName());
		assertEquals("cwalls@vmware.com", profile.getEmail());
		assertEquals("habuma", profile.getUsername());
	}

	@Test
	public void fetchProfileMiddleName() {
		Mockito.when(tripit.getUserProfile()).thenReturn(new TripItProfile("habuma", "habuma", "Michael Craig Walls", "cwalls@vmware.com", "Plano, TX", "SpringSource", "people/habuma", "http://static.tripit.com/uploads/images/0/0/6/006b210269799fa70ff6ae2c0cdb8a41e9c.jpg"));
		UserProfile profile = apiAdapter.fetchUserProfile(tripit);
		assertEquals("Michael Craig Walls", profile.getName());
		assertEquals("Michael", profile.getFirstName());
		assertEquals("Walls", profile.getLastName());
		assertEquals("cwalls@vmware.com", profile.getEmail());
		assertEquals("habuma", profile.getUsername());
	}
	
	@Test
	public void fetchProfileExtraWhitespace() {
		Mockito.when(tripit.getUserProfile()).thenReturn(new TripItProfile("habuma", "habuma", "Michael    Craig Walls", "cwalls@vmware.com", "Plano, TX", "SpringSource", "people/habuma", "http://static.tripit.com/uploads/images/0/0/6/006b210269799fa70ff6ae2c0cdb8a41e9c.jpg"));
		UserProfile profile = apiAdapter.fetchUserProfile(tripit);
		assertEquals("Michael    Craig Walls", profile.getName());
		assertEquals("Michael", profile.getFirstName());
		assertEquals("Walls", profile.getLastName());
		assertEquals("cwalls@vmware.com", profile.getEmail());
		assertEquals("habuma", profile.getUsername());
	}
	
}
