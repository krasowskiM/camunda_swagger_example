package com.test.generate_swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.parser.OpenAPIV3Parser;
import io.swagger.v3.parser.core.models.ParseOptions;
import org.camunda.bpm.model.dmn.Dmn;
import org.camunda.bpm.model.dmn.DmnModelInstance;
import org.camunda.bpm.model.dmn.instance.DecisionTable;
import org.camunda.bpm.model.xml.instance.ModelElementInstance;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.util.stream.Collectors;

@SpringBootTest
public class SwaggerGenerationTest {

    @Test
    void shouldGenerateDefinitionToSwagger() throws IOException {
        DmnModelInstance modelInstance = Dmn.readModelFromFile(new File("/home/maciek/projects/swagger_aaa/src/main/resources/dmns/some_random_stuff.dmn"));
        DecisionTable decisionTable = modelInstance.getModelElementById("DecisionTable_0ea8v51");
        var entryValues = decisionTable
                .getRules()
                .stream()
                .flatMap(rule -> rule.getOutputEntries().stream().map(ModelElementInstance::getRawTextContent))
                .collect(Collectors.toList());
        ParseOptions parseOptions = new ParseOptions();
        parseOptions.setResolve(true); // implicit
        parseOptions.setResolveFully(true);
        OpenAPIV3Parser openAPIV3Parser = new OpenAPIV3Parser();
        String swaggerPath = "/home/maciek/projects/swagger_aaa/api_defs/swagger.yaml";
        OpenAPI openAPI = openAPIV3Parser.readLocation(swaggerPath,null,parseOptions).getOpenAPI();
//        entryValues.forEach(entryValue ->
//                ((StringSchema) openAPI.getComponents().getSchemas().get("response").$ref();
//        Yaml.pretty().writeValue(new File(swaggerPath), openAPI);
    }
}
