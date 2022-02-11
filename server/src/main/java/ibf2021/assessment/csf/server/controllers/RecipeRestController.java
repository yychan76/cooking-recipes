package ibf2021.assessment.csf.server.controllers;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.net.URI;
import java.util.Optional;
import java.util.logging.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import ibf2021.assessment.csf.server.models.Recipe;
import ibf2021.assessment.csf.server.services.RecipeService;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonString;

/* Write your request hander in this file */

@RestController
@RequestMapping(path = "/api/recipe", produces = MediaType.APPLICATION_JSON_VALUE)
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

    @PostMapping(
        consumes=MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<String> postAddRecipe(@RequestBody String payload) {
        logger.info("Payload:" + payload);


        try {
            Recipe recipe = new Recipe();
            try (Reader reader = new StringReader(payload);
                JsonReader jsonReader = Json.createReader(reader);) {

                JsonObject recipeJson = jsonReader.readObject();
                recipe.setTitle(recipeJson.getString("title"));
                recipe.setInstruction(recipeJson.getString("instruction"));
                recipe.setImage(recipeJson.getString("image"));
                recipeJson.getJsonArray("ingredients").stream()
                    .forEach(ingredient -> recipe.addIngredient(((JsonString)ingredient).getString()));
            } catch (JsonProcessingException jpe) {
                logger.severe(jpe.getMessage());
            } catch (IOException ioe) {
                logger.severe(ioe.getMessage());
            }
            logger.info("Adding %s".formatted(recipe));
            this.recipeService.addRecipe(recipe);
            URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(recipe.getId()).toUri();
            return ResponseEntity.created(location).build();
        } catch (Exception e) {
            JsonObject errBody = Json.createObjectBuilder()
                .add("error", e.getMessage())
                .build();
            return ResponseEntity.badRequest().body(errBody.toString());
        }
    }
}
