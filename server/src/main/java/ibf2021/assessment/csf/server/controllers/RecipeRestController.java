package ibf2021.assessment.csf.server.controllers;

import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ibf2021.assessment.csf.server.models.Recipe;
import ibf2021.assessment.csf.server.services.RecipeService;
import jakarta.json.Json;
import jakarta.json.JsonObject;

/* Write your request hander in this file */

@RestController
@RequestMapping(
    path="/api/recipe",
    produces=MediaType.APPLICATION_JSON_VALUE
)
public class RecipeRestController {
    private final Logger logger = Logger.getLogger(RecipeRestController.class.getName());

    @Autowired
    RecipeService recipeService;

    @GetMapping("{recipeId}")
    public ResponseEntity<String> getRecipeById(@PathVariable String recipeId) {

        Optional<Recipe> opt = recipeService.getRecipeById(recipeId);
        if (opt.isPresent()) {
            Recipe recipe = opt.get();

            JsonObject recipeJson = Json.createObjectBuilder()
                .add("id", recipe.getId())
                .add("title", recipe.getTitle())
                .add("image", recipe.getImage())
                .add("instruction", recipe.getInstruction())
                .add("ingredients", Json.createArrayBuilder(recipe.getIngredients()))
                .build();
            logger.info(recipeJson.toString());

            return ResponseEntity.ok(recipeJson.toString());
        } else {
            JsonObject notFound = Json.createObjectBuilder()
                .add("message", "Recipe with id %s not found".formatted(recipeId))
                .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(notFound.toString());
        }
    }
}
