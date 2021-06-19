package space.laimengchao.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger2配置文件
 */
@EnableSwagger2
@Configuration
@Profile(value = "local")//只在本地环境开启swagger
public class Swagger2Config {

    @Bean
    public Docket createLotteryRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("彩票")
                .pathMapping("/")
                .select()
                .apis(RequestHandlerSelectors.basePackage("space.laimengchao.controller"))
                .paths(PathSelectors.regex("/lottery/*.*|/subscribe/*.*"))
                .build().apiInfo(new ApiInfoBuilder()
                        .title("Api")
                        .description("彩票结果查询功能")
                        .version("1.0")
                        .contact(new Contact("Crom.lai", "http://laimengchao.space:8090/", "Crom.lai@outlook.com"))
                        .build());
    }

    @Bean
    public Docket createWeatherRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("天气")
                .pathMapping("/")
                .select()
                .apis(RequestHandlerSelectors.basePackage("space.laimengchao.controller"))
                .paths(PathSelectors.regex("/weather/*.*"))
                .paths(PathSelectors.any())
                .build().apiInfo(new ApiInfoBuilder()
                        .title("Api")
                        .description("天气查询功能")
                        .version("1.0")
                        .contact(new Contact("Crom.lai", "http://laimengchao.space:8090/", "Crom.lai@outlook.com"))
                        .build());
    }

}
