package br.com.dbserver.controller;

import br.com.dbserver.builder.VotacaoBuilder;
import br.com.dbserver.service.AssociadoService;
import br.com.dbserver.service.PautaService;
import br.com.dbserver.service.dto.VotacaoDto;
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
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@Transactional
public class VotacaoControllerIntegrationTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private PautaService pautaService;

    @Autowired
    private AssociadoService associadoService;

    @Autowired
    private VotacaoBuilder votacaoBuilder;

    private MockMvc mvc;

    private final String URL = "/api/votacao/";

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
        VotacaoDto votacaoDto = this.votacaoBuilder.getVotacaoDto();

        this.mvc.perform(post(URL)
                .content(convertObjectToJsonBytes(votacaoDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        this.mvc.perform(post(URL)
                .content(convertObjectToJsonBytes(votacaoDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        votacaoDto.getAssociado().setCpf("11111111111");
        associadoService.save(votacaoDto.getAssociado());
        this.mvc.perform(post(URL)
                .content(convertObjectToJsonBytes(votacaoDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        votacaoDto.getAssociado().setCpf("02479643018");
        associadoService.save(votacaoDto.getAssociado());
        votacaoDto.getPauta().getSessao().setDataInicio(LocalDateTime.now().plusMinutes(-1L));
        votacaoDto.getPauta().getSessao().setDataTermino(LocalDateTime.now().plusMinutes(-1L));
        pautaService.save(votacaoDto.getPauta());
        this.mvc.perform(post(URL)
                .content(convertObjectToJsonBytes(votacaoDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        this.mvc.perform(get(URL + votacaoDto.getPauta().getId()))
                .andExpect(status().isOk());

        this.votacaoBuilder.excluir();
    }
}
