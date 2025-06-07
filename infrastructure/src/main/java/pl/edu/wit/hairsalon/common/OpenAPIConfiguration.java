package pl.edu.wit.hairsalon.common;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.ArraySchema;
import io.swagger.v3.oas.models.media.IntegerSchema;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.Parameter;
import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;

import static io.swagger.v3.oas.annotations.enums.ParameterIn.QUERY;
import static java.util.Arrays.stream;
import static java.util.Objects.nonNull;

@Configuration
class OpenAPIConfiguration {

    @Bean
    public GroupedOpenApi.Builder groupedOpenApiBuilder(OperationCustomizer acceptLanguageOperationCustomizer,
                                                        OperationCustomizer pageableOperationCustomizer) {
        return GroupedOpenApi.builder()
                .group("hair-salon")
                .pathsToMatch("***")
                .addOperationCustomizer(acceptLanguageOperationCustomizer)
                .addOperationCustomizer(pageableOperationCustomizer);
    }
    
    @Bean
    OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info().title("Hair salon API")
                        .description("Serwis który umożliwiaja rezerwację terminu usługi bezpośredniej u fryzjera")
                        .version("1.0"));
    }

    @Bean
    public OperationCustomizer pageableOperationCustomizer() {
        return (operation, handlerMethod) -> {
            var hasPageable = stream(handlerMethod.getMethodParameters()).anyMatch(param -> Pageable.class.isAssignableFrom(param.getParameterType()));

            if (hasPageable) {
                var newParameters = nonNull(operation.getParameters())
                        ? operation.getParameters()
                        : new ArrayList<Parameter>();
                newParameters.removeIf(parameter -> parameter.getName().equals("pageable") && parameter.getIn().equals(QUERY.toString()));

                newParameters.add(createParameter(
                        "page",
                        "Results page you want to retrieve (0..N)",
                        new IntegerSchema()._default(0))
                );
                newParameters.add(createParameter(
                        "size",
                        "Number of records per page",
                        new IntegerSchema()._default(25))
                );
                newParameters.add(createParameter(
                        "sort",
                        "Sorting criteria in the format: property(,asc|desc). Default sort order is ascending.",
                        new ArraySchema().items(new StringSchema()))
                );

                operation.setParameters(newParameters);
            }
            return operation;
        };
    }

    private Parameter createParameter(String name, String description, Schema<?> schema) {
        return new Parameter()
                .in(QUERY.toString())
                .name(name)
                .description(description)
                .required(true)
                .schema(schema);
    }
    
}
