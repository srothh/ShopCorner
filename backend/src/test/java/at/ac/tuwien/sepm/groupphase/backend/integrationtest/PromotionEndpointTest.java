package at.ac.tuwien.sepm.groupphase.backend.integrationtest;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.config.properties.SecurityProperties;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.PaginationDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.PromotionDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.PromotionMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.Promotion;
import at.ac.tuwien.sepm.groupphase.backend.repository.PromotionRepository;
import at.ac.tuwien.sepm.groupphase.backend.security.JwtTokenizer;
import at.ac.tuwien.sepm.groupphase.backend.service.PromotionService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class PromotionEndpointTest implements TestData {

    private final Promotion promotion = new Promotion(0L, TEST_PROMOTION_NAME, TEST_PROMOTION_DISCOUNT, LocalDateTime.now(), TEST_PROMOTION_EXPIRATIONDATE, TEST_PROMOTION_CODE, TEST_PROMOTION_MINIMUMORDERVALUE);
    private final Promotion promotion2 = new Promotion(0L, TEST_PROMOTION_NAME, TEST_PROMOTION_DISCOUNT, LocalDateTime.now(), TEST_PROMOTION_EXPIRATIONDATE, "testest", TEST_PROMOTION_MINIMUMORDERVALUE);
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private PromotionMapper promotionMapper;
    @Autowired
    private PromotionService promotionService;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private PromotionRepository promotionRepository;
    @Autowired
    private SecurityProperties securityProperties;
    @Autowired
    private JwtTokenizer jwtTokenizer;
    @Autowired
    private CacheManager cacheManager;

    @BeforeEach
    @CacheEvict(value = "counts", allEntries = true)
    public void beforeEach() {
        promotionRepository.deleteAll();
    }

    @Test
    void givenNothing_whenPost_thenPromotionWithAllSetPropertiesPlusId() throws Exception {
        PromotionDto promotionDto = promotionMapper.promotionToPromotionDto(promotion);
        String body = objectMapper.writeValueAsString(promotionDto);

        MvcResult mvcResult = this.mockMvc.perform(post(PROMOTION_BASE_URI)
            .contentType(MediaType.APPLICATION_JSON)
            .content(body).header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(ADMIN_USER, ADMIN_ROLES)
        ))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
        assertEquals(MediaType.APPLICATION_JSON_VALUE, response.getContentType());

        PromotionDto promotionResponse = objectMapper.readValue(response.getContentAsString(),
            PromotionDto.class);

        assertNotNull(promotionResponse.getId());
        assertNotNull(promotionResponse.getName());
        assertNotNull(promotionResponse.getExpirationDate());
        assertNotNull(promotionResponse.getCode());
        promotionResponse.setId(null);

        assertAll(
            () -> assertEquals(promotion.getName(), promotionMapper.promotionDtoToPromotion(promotionResponse).getName()),
            () -> assertEquals(promotion.getCode(), promotionMapper.promotionDtoToPromotion(promotionResponse).getCode()),
            () -> assertEquals(promotion.getExpirationDate(), promotionMapper.promotionDtoToPromotion(promotionResponse).getExpirationDate()),
            () -> assertEquals(promotion.getMinimumOrderValue(), promotionMapper.promotionDtoToPromotion(promotionResponse).getMinimumOrderValue())
        );
    }

    @Test
    void givenNothing_whenPostInvalid_then400() throws Exception {
        promotion.setName(null);
        promotion.setCode(null);
        PromotionDto dto = promotionMapper.promotionToPromotionDto(promotion);
        String body = objectMapper.writeValueAsString(dto);

        MvcResult mvcResult = this.mockMvc.perform(post(PROMOTION_BASE_URI)
            .contentType(MediaType.APPLICATION_JSON)
            .content(body)
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(ADMIN_USER, ADMIN_ROLES)))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertAll(
            () -> assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus()),
            () -> {
                String content = response.getContentAsString();
                content = content.substring(content.indexOf('[') + 1, content.indexOf(']'));
                String[] errors = content.split(",");
                assertEquals(4, errors.length);
            }
        );
    }

    @Test
    void givenNothing_whenFindAllCustomers_thenEmptyList() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get(PROMOTION_BASE_URI + "?page=0&pageCount=1")
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(ADMIN_USER, ADMIN_ROLES)))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(MediaType.APPLICATION_JSON_VALUE, response.getContentType());
        PaginationDto<PromotionDto> paginationDto = objectMapper.readValue(response.getContentAsString(),
            new TypeReference<>() {
            });

        assertEquals(0, paginationDto.getItems().size());
    }

    @Test
    void givenTwoPromotions_whenFindAllWithPage_thenListWithSizeTwoPromotionsWithAllProperties()
        throws Exception {
        PromotionDto promotionDto = promotionMapper.promotionToPromotionDto(promotion);
        String body = objectMapper.writeValueAsString(promotionDto);
        PromotionDto promotionDto2 = promotionMapper.promotionToPromotionDto(promotion2);
        String body2 = objectMapper.writeValueAsString(promotionDto2);

        this.mockMvc.perform(post(PROMOTION_BASE_URI)
            .contentType(MediaType.APPLICATION_JSON)
            .content(body)
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(ADMIN_USER, ADMIN_ROLES)))
            .andDo(print())
            .andReturn();
        this.mockMvc.perform(post(PROMOTION_BASE_URI)
            .contentType(MediaType.APPLICATION_JSON)
            .content(body2)
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(ADMIN_USER, ADMIN_ROLES)))
            .andDo(print())
            .andReturn();

        MvcResult mvcResultGet = this.mockMvc.perform(get(PROMOTION_BASE_URI + "?page=0&page_count=10")
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(ADMIN_USER, ADMIN_ROLES)))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse responseGet = mvcResultGet.getResponse();

        assertEquals(HttpStatus.OK.value(), responseGet.getStatus());
        assertEquals(MediaType.APPLICATION_JSON_VALUE, responseGet.getContentType());

        PaginationDto<PromotionDto> dto = objectMapper.readValue(responseGet.getContentAsString(),
            new TypeReference<>() {
            });

        assertEquals(2, dto.getItems().size());
        PromotionDto checkDto = dto.getItems().get(0);
        assertAll(
            () -> assertEquals(TEST_PROMOTION_NAME, checkDto.getName()),
            () -> assertEquals(TEST_PROMOTION_DISCOUNT, checkDto.getDiscount()),
            () -> assertEquals(TEST_PROMOTION_CODE, checkDto.getCode()),
            () -> assertEquals(TEST_PROMOTION_EXPIRATIONDATE, checkDto.getExpirationDate()),
            () -> assertEquals(TEST_PROMOTION_MINIMUMORDERVALUE, checkDto.getMinimumOrderValue())
        );
    }

    @Test
    void givenTwoCustomers_whenGetCount_thenArrayWithTwo()
        throws Exception {
        PromotionDto promotionDto = promotionMapper.promotionToPromotionDto(promotion);
        String body = objectMapper.writeValueAsString(promotionDto);
        PromotionDto customerDto2 = promotionMapper.promotionToPromotionDto(promotion2);
        String body2 = objectMapper.writeValueAsString(customerDto2);
        MvcResult mvcResultGet1 = this.mockMvc.perform(post(PROMOTION_BASE_URI)
            .contentType(MediaType.APPLICATION_JSON)
            .content(body)
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(ADMIN_USER, ADMIN_ROLES)))
            .andDo(print())
            .andReturn();
        MvcResult mvcResultGet2 = this.mockMvc.perform(post(PROMOTION_BASE_URI)
            .contentType(MediaType.APPLICATION_JSON)
            .content(body2)
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(ADMIN_USER, ADMIN_ROLES)))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse responseGet2 = mvcResultGet1.getResponse();
        MockHttpServletResponse responseGet1 = mvcResultGet2.getResponse();
        MvcResult mvcResultGet = this.mockMvc.perform(get(PROMOTION_BASE_URI)
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(ADMIN_USER, ADMIN_ROLES)))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse responseGet = mvcResultGet.getResponse();
        assertEquals(HttpStatus.CREATED.value(), responseGet1.getStatus());
        assertEquals(HttpStatus.CREATED.value(), responseGet2.getStatus());

        assertEquals(HttpStatus.OK.value(), responseGet.getStatus());
        assertEquals(MediaType.APPLICATION_JSON_VALUE, responseGet.getContentType());

        PaginationDto<PromotionDto> promotionDtos = objectMapper.readValue(responseGet.getContentAsString(),
            new TypeReference<>() {
            });
        assertEquals(2, promotionDtos.getTotalItemCount());

    }

    @Test
    void givenTwoCustomers_whenGetCount_thenTwoInCache()
        throws Exception {

        PromotionDto promotionDto = promotionMapper.promotionToPromotionDto(promotion);
        String body = objectMapper.writeValueAsString(promotionDto);
        PromotionDto customerDto2 = promotionMapper.promotionToPromotionDto(promotion2);
        String body2 = objectMapper.writeValueAsString(customerDto2);
        MvcResult mvcResultGet1 = this.mockMvc.perform(post(PROMOTION_BASE_URI)
            .contentType(MediaType.APPLICATION_JSON)
            .content(body)
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(ADMIN_USER, ADMIN_ROLES)))
            .andDo(print())
            .andReturn();
        MvcResult mvcResultGet2 = this.mockMvc.perform(post(PROMOTION_BASE_URI)
            .contentType(MediaType.APPLICATION_JSON)
            .content(body2)
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(ADMIN_USER, ADMIN_ROLES)))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse responseGet2 = mvcResultGet1.getResponse();
        MockHttpServletResponse responseGet1 = mvcResultGet2.getResponse();
        MvcResult mvcResultGet = this.mockMvc.perform(get(PROMOTION_BASE_URI)
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(ADMIN_USER, ADMIN_ROLES)))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse responseGet = mvcResultGet.getResponse();
        assertEquals(HttpStatus.CREATED.value(), responseGet1.getStatus());
        assertEquals(HttpStatus.CREATED.value(), responseGet2.getStatus());

        assertEquals(HttpStatus.OK.value(), responseGet.getStatus());
        assertEquals(MediaType.APPLICATION_JSON_VALUE, responseGet.getContentType());

        PaginationDto<PromotionDto> promotionDtos = objectMapper.readValue(responseGet.getContentAsString(),
            new TypeReference<>() {
            });
        assertEquals(2, promotionDtos.getTotalItemCount());
        assertEquals(2, cacheManager.getCache("counts").get("promotions", Long.class));
    }

}
