package br.com.dbserver.controller;

import br.com.dbserver.builder.PautaBuilder;
import br.com.dbserver.service.PautaService;
import br.com.dbserver.service.dto.PautaDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PautaControllerIntegrationTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private PautaService pautaService;

    @Autowired
    private PautaBuilder pautaBuilder;

    private MockMvc mvc;

    private final String URL = "/api/pauta/";
    private final int PAGE = 0;
    private final int SIZE = 20;

    private static byte[] convertObjectToJsonBytes(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        JavaTimeModule module = new JavaTimeModule();
        mapper.registerModule(module);

        return mapper.writeValueAsBytes(object);
    }

    @Before
    public void setUp() {
        this.mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void test01_saveSuccess() throws Exception {
        this.mvc.perform(post(URL)
                .content(convertObjectToJsonBytes(this.pautaBuilder.getPautaDto()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void test02_updateSuccess() throws Exception {
        PautaDto pautaDto = pautaService.findByDescricaoContainingIgnoreCase(this.pautaBuilder.getPautaDto().getDescricao(), PageRequest.of(PAGE, SIZE)).getContent().get(0);
        pautaDto.setSessao(null);

        this.mvc.perform(put(URL + pautaDto.getId())
                .content(convertObjectToJsonBytes(pautaDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void test03_openSessionSuccess() throws Exception {
        PautaDto pautaDto = pautaService.findByDescricaoContainingIgnoreCase(this.pautaBuilder.getPautaDto().getDescricao(), PageRequest.of(PAGE, SIZE)).getContent().get(0);

        this.mvc.perform(patch(URL + pautaDto.getId())
                .param("minutos", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void test04_findByNameSuccess() throws Exception {
        this.mvc.perform(get(URL + "?descricao= " + this.pautaBuilder.getPautaDto().getDescricao() + "&page=" + PAGE + "&size="+ SIZE))
                .andExpect(status().isOk());
    }

    @Test
    public void test05_findByIdSuccess() throws Exception {
        PautaDto pautaDto = pautaService.findByDescricaoContainingIgnoreCase(this.pautaBuilder.getPautaDto().getDescricao(), PageRequest.of(PAGE, SIZE)).getContent().get(0);
        this.mvc.perform(get(URL + pautaDto.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("descricao", equalTo(this.pautaBuilder.getPautaDto().getDescricao())));
    }

    @Test
    public void test06_findByIdNoContent() throws Exception {
        this.mvc.perform(get(URL + "/0"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void test07_deleteByIdSuccess() throws Exception {
        PautaDto pautaDto = pautaService.findByDescricaoContainingIgnoreCase(this.pautaBuilder.getPautaDto().getDescricao(), PageRequest.of(PAGE, SIZE)).getContent().get(0);
        this.mvc.perform(delete(URL + pautaDto.getId()))
                .andExpect(status().isNoContent());
    }
}