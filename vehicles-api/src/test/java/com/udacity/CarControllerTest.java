package com.udacity;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.net.URI;
import java.util.Collections;
import org.hibernate.annotations.OrderBy;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.print.attribute.standard.Media;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class CarControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<Car> json;

    @MockBean
    private CarService carS;

    @MockBean
    private PriceClient priceC;

    @MockBean
    private MapsClient mapsC;

    @Before
    public void begin() {
        Car c = get();
        c.setId(1L);
        given(carS.save(any())).willReturn(c);
        given(carS.findById(any())).willReturn(c);
        given(carS.list()).willReturn(Collections.singletonList(c));
    }

    @Test
    public void add() throws Exception {
        Car c = get();
        mvc.perform(post(new URI("/cars")) .content(json.write(c).getJson()) .contentType(MediaType.APPLICATION_JSON_UTF8) .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isCreated());
    }

    @Test
    public void list() throws Exception {
        Car c = get();
        mvc.perform(
            get(new URI("/cars"))
        .accept(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    public void find() throws Exception {
        Car c = get();
        mvc.perform(get(new URI("/cars/"+c.getId()))
            .accept(MediaType.APPLICATION_JSON_UTF8)
        ).andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(jsonPath("$.id").value(c.getId()));

    }

    @Test
    public void update() throws Exception{
        Car c = get();
        c.setCondition(Condition.NEW);
        mvc.perform(put(new URI("/cars/" + c.getId()))
            .content(json.write(c).getJson())
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .accept(MediaType.APPLICATION_JSON_UTF8)
        ).andExpect(status().isOk())
        .andDo(print());
    }

    @Test
    public void delete() throws Exception {
        Car c = get();
        mvc.perform(delete(new URI("/cars/"+c.getId())).accept(MediaType.APPLICATION_JSON_UTF8)) .andExpect(status().isNoContent());
    }

    private Car get() {
        Car c = new Car();
        c.setId(1L);
        c.setLocation(new Location(12.45236, 65.365236));
        Details de = new Details();
        Manufacturer ma = new Manufacturer(352, "huyhue");
        de.setManufacturer(ma);
        de.setFuelType("dias");
        c.setDetails(de);
        c.setCondition(Condition.USED);
        return c;
    }
}