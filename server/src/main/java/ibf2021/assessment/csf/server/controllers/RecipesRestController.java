package ibf2021.assessment.csf.server.controllers;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ibf2021.assessment.csf.server.models.Recipe;
import ibf2021.assessment.csf.server.services.RecipeService;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;

/* Write your request hander in this file */

@RestController
@RequestMapping(path = "/api/recipes", produces = MediaType.APPLICATION_JSON_VALUE)
public class RecipesRestController {
    private final Logger logger = Logger.getLogger(RecipesRestController.class.getName());

    @Autowired
    RecipeService recipeService;

    @GetMapping
    public ResponseEntity<String> getRecipes() {
        List<JsonObject> recipes = recipeService.getAllRecipes().stream()
                .map(Recipe.class::cast)
                .map(item -> Json.createObjectBuilder()
                        .add("id", item.getId())
                        .add("name", item.getTitle())
                        .build()
                )
                .toList();
        // logger.info(recipes.toString());
        JsonArray recipesJson = Json.createArrayBuilder(recipes).build();
        logger.info(recipesJson.toString());
        return ResponseEntity.ok(recipesJson.toString());
    }

}
