package com.narvar;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class GreetingControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void gettingDefaulMessage() throws Exception {

		this.mockMvc.perform(get("/greeting")).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$.content").value("Hello, World!"));
	}

	@Test
	public void getGreetingException() throws Exception {

		this.mockMvc.perform(get("/greetingEx")).andDo(print()).andExpect(status().is5xxServerError())
				.andExpect(jsonPath("$.status").value("INTERNAL_SERVER_ERROR"));

	}

	@Test
	public void getGreetingExcpPost() throws Exception {

		this.mockMvc.perform(post("/greetingException")).andDo(print()).andExpect(status().is5xxServerError())
				.andExpect(jsonPath("$.status").value("INTERNAL_SERVER_ERROR"));

	}

	/*
	 * Junit Test case for failed Cases ..
	 */
	/*
	 * @Test public void genericBadRequestException() throws Exception {
	 * 
	 * this.mockMvc.perform(get("/g1")).andDo(print()).andExpect(status().
	 * is4xxClientError()) .andExpect(jsonPath("$.Status").value("404"));
	 * 
	 * }
	 */

	/*
	 * Junit Test case for failed Cases ..
	 */

	/*
	 * @Test public void genericInvalidRequestType() throws Exception {
	 * 
	 * this.mockMvc.perform(delete("/greeting")).andDo(print()).andExpect(status
	 * ().is4xxClientError()) .andExpect(jsonPath("$.status").value("405"));
	 * 
	 * }
	 */

	@Test
	public void greetingJsonEx() throws Exception {

		this.mockMvc.perform(post("/jsonRequestEx")).andDo(print()).andExpect(status().is4xxClientError())
				.andExpect(jsonPath("$.status").value("UNSUPPORTED_MEDIA_TYPE"));

	}

	@Test
	public void genericMediaNotSupported() throws Exception {

		this.mockMvc.perform(post("/greetingPost").contentType(MediaType.APPLICATION_FORM_URLENCODED)).andDo(print())
				.andExpect(status().is(415));

	}
	

	@Test
	public void genericJsonResponseReq() throws Exception {

		this.mockMvc.perform(get("/jsonRequest")).andDo(print()).andExpect(status().is2xxSuccessful())
				.andExpect(jsonPath("$.FName").value("FirtName"));

	}

	@Test
	public void genericXmlResponseReq() throws Exception {

		this.mockMvc.perform(post("/xmlRequestEx")).andDo(print()).andExpect(status().is4xxClientError())
				.andExpect(jsonPath("$.status").value("NOT_FOUND"));

	}

}