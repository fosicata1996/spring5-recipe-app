package guru.springframework.services;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.converters.RecipeToCommand;
import guru.springframework.converters.RecipeToEntity;
import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeToEntity recipeToEntity;
    private final RecipeToCommand recipeToCommand;


    @Override
    public Set<Recipe> getRecipes() {
        log.debug("I'm in the service");

        Set<Recipe> recipes = new HashSet<>();
        recipeRepository.findAll().iterator().forEachRemaining(recipes::add);
        return recipes;
    }

    @Override
    public Recipe findById(Long l) {
        return recipeRepository.findById(l).orElseThrow(() -> new RuntimeException("Recipe Not Found!"));
    }

    @Override
    @Transactional
    public RecipeCommand save(RecipeCommand command) {
        Recipe detachedRecipe = recipeToEntity.convert(command);

        Recipe savedRecipe = recipeRepository.save(detachedRecipe);
        log.debug("Saved RecipeId: " + savedRecipe.getId());

        return recipeToCommand.convert(savedRecipe);
    }
}
