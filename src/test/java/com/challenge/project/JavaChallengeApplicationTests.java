package com.challenge.project;

import com.challenge.project.model.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class JavaChallengeApplicationTests {

	private static final CoffeeDto mochaccinoCoffee = CoffeeDto.builder().name("Mochaccino").description("Mochaccino coffee").cost(new BigDecimal(2)).enabled(true).build();
	private static final CoffeeDto espressoCoffee = CoffeeDto.builder().name("Espresso").description("Espresso coffee").cost(new BigDecimal(1)).enabled(true).build();
	private static final CoffeeDto macchiatoCoffee = CoffeeDto.builder().name("Macchiato").description("Macchiato or espresso coffee").cost(new BigDecimal(3)).enabled(true).build();
	private static final AdditionalDto vanillaAdditional = AdditionalDto.builder().id(1L).description("Vanilla").cost(new BigDecimal(3)).combineCostPercentage(new BigDecimal(100)).build();
	private static final AdditionalDto creamerAdditional = AdditionalDto.builder().id(2L).description("Creamer").cost(new BigDecimal(5)).combineCostPercentage(new BigDecimal(80)).build();
	private static final OrderDto firstOrder = OrderDto.builder().description("First order").orderDate(LocalDateTime.now()).coffees(List.of(macchiatoCoffee, mochaccinoCoffee)).build();

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Nested
	class CoffeesTest {
		@Test
		void createCoffeeSuccessTest() throws Exception {
			CoffeeDto newCoffee = CoffeeDto.builder().name("Latte").description("Latte coffee").cost(new BigDecimal(4)).enabled(true).build();
			String request = objectMapper.writeValueAsString(newCoffee);

			String result = mockMvc.perform(MockMvcRequestBuilders.post("/api/coffee")
							.content(request)
							.contentType(MediaType.APPLICATION_JSON))
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andDo(MockMvcResultHandlers.print())
					.andReturn().getResponse().getContentAsString();
			CoffeeDto actual = objectMapper.readValue(result, CoffeeDto.class);
			assertEquals(newCoffee.getName(), actual.getName());
		}

		@Test
		void findAllCoffeeSuccessTest() throws Exception {
			String result = mockMvc.perform(MockMvcRequestBuilders.get("/api/coffees")
							.contentType(MediaType.APPLICATION_JSON))
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andDo(MockMvcResultHandlers.print())
					.andReturn().getResponse().getContentAsString();
			List<CoffeeDto> actual = objectMapper.readValue(result, new TypeReference<List<CoffeeDto>>(){});
			assertNotNull(actual);
		}

		@Test
		void coffeeNotFoundTest() throws Exception {
			String filter = "void";
			String result = mockMvc.perform(MockMvcRequestBuilders.get("/api/coffee")
							.param("filter", filter)
							.contentType(MediaType.APPLICATION_JSON))
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andDo(MockMvcResultHandlers.print())
					.andReturn().getResponse().getContentAsString();
			List<CoffeeDto> actual = objectMapper.readValue(result, new TypeReference<List<CoffeeDto>>(){});
			assertArrayEquals(new ArrayList[]{}, actual.toArray());
		}

		@Test
		void coffeeFoundTest() throws Exception {
			String filter = "mochaccino";
			String result = mockMvc.perform(MockMvcRequestBuilders.get("/api/coffee")
							.param("filter", filter)
							.contentType(MediaType.APPLICATION_JSON))
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andDo(MockMvcResultHandlers.print())
					.andReturn().getResponse().getContentAsString();
			List<CoffeeDto> actual = objectMapper.readValue(result, new TypeReference<List<CoffeeDto>>(){});
			assertEquals(1, actual.size());
		}

		@Test
		void combineCoffeeSuccessTest() throws Exception {

			CombineCoffeeRequest objRequest = new CombineCoffeeRequest();
			objRequest.setCoffee(mochaccinoCoffee);
			objRequest.setAdditionals(List.of(vanillaAdditional, creamerAdditional));
			String request = objectMapper.writeValueAsString(objRequest);

			String result = mockMvc.perform(MockMvcRequestBuilders.post("/api/coffee/combine")
							.content(request)
							.contentType(MediaType.APPLICATION_JSON))
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andDo(MockMvcResultHandlers.print())
					.andReturn().getResponse().getContentAsString();
			CoffeeDto actual = objectMapper.readValue(result, CoffeeDto.class);
			assertEquals("Creamer Vanilla Coffee", actual.getName());
			assertEquals(BigDecimal.valueOf(9), actual.getCost());
		}

		@Test
		void updatePriceTest() throws Exception {

			Long coffeeId = 3L;
			BigDecimal newPrice = BigDecimal.valueOf(2.3);

			String result = mockMvc.perform(MockMvcRequestBuilders.patch("/api/coffee/price")
							.param("coffeeId", coffeeId.toString())
							.param("newPrice", newPrice.toString())
							.contentType(MediaType.APPLICATION_JSON))
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andDo(MockMvcResultHandlers.print())
					.andReturn().getResponse().getContentAsString();
			CoffeePriceChangeResponse actual = objectMapper.readValue(result, CoffeePriceChangeResponse.class);
			assertEquals(coffeeId, actual.getCoffee().getId());
			assertEquals(newPrice, actual.getCoffee().getCost());
		}
	}


	@Nested
	class OrdersTest {
		@Test
		void createOrderSuccessTest() throws Exception {
			OrderDto newOrder = OrderDto.builder().description("New order test").coffees(List.of(espressoCoffee, macchiatoCoffee)).orderDate(LocalDateTime.now()).build();
			String request = objectMapper.writeValueAsString(newOrder);

			String result = mockMvc.perform(MockMvcRequestBuilders.post("/api/order")
							.content(request)
							.contentType(MediaType.APPLICATION_JSON))
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andDo(MockMvcResultHandlers.print())
					.andReturn().getResponse().getContentAsString();
			OrderDto actual = objectMapper.readValue(result, OrderDto.class);
			assertEquals(newOrder.getDescription(), actual.getDescription());
		}

		@Test
		void findAllOrdersSuccessTest() throws Exception {
			String result = mockMvc.perform(MockMvcRequestBuilders.get("/api/orders")
							.contentType(MediaType.APPLICATION_JSON))
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andDo(MockMvcResultHandlers.print())
					.andReturn().getResponse().getContentAsString();
			List<OrderDto> actual = objectMapper.readValue(result, new TypeReference<List<OrderDto>>(){});
			assertNotNull(actual);
		}

		@Test
		void getOrderByIdTest() throws Exception {
			Long id = 1L;
			String result = mockMvc.perform(MockMvcRequestBuilders.get("/api/order/{id}", id)
							.contentType(MediaType.APPLICATION_JSON))
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andDo(MockMvcResultHandlers.print())
					.andReturn().getResponse().getContentAsString();
			OrderDto actual = objectMapper.readValue(result, OrderDto.class);
			assertEquals(firstOrder.getDescription(), actual.getDescription());
		}

		@Test
		void searchOrderByCoffeeNameNotFoundTest() throws Exception {
			String filter = "Latte";
			String result = mockMvc.perform(MockMvcRequestBuilders.get("/api/order")
							.param("coffeeName", filter)
							.contentType(MediaType.APPLICATION_JSON))
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andDo(MockMvcResultHandlers.print())
					.andReturn().getResponse().getContentAsString();
			List<OrderDto> actual = objectMapper.readValue(result, new TypeReference<List<OrderDto>>(){});
			assertArrayEquals(new ArrayList[]{}, actual.toArray());
		}

		@Test
		void orderByCoffeeNameFoundTest() throws Exception {
			String filter = "Espresso";
			String result = mockMvc.perform(MockMvcRequestBuilders.get("/api/order")
							.param("coffeeName", filter)
							.contentType(MediaType.APPLICATION_JSON))
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andDo(MockMvcResultHandlers.print())
					.andReturn().getResponse().getContentAsString();
			List<CoffeeDto> actual = objectMapper.readValue(result, new TypeReference<List<CoffeeDto>>(){});
			assertNotNull(actual);
		}
	}

}
