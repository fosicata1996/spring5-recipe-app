package guru.springframework.controllers;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.services.RecipeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/recipe")
public class RecipeController {

    public static final String RECIPE = "recipe";
    private final RecipeService recipeService;

    @GetMapping("/{id}")
    public String showById(@PathVariable String id, Model model) {

        model.addAttribute(RECIPE, recipeService.findById(new Long(id)));

        return "recipe/show";
    }

    @GetMapping("/{id}/update")
    public String updateRecipe(@PathVariable String id, Model model) {
        model.addAttribute(RECIPE, recipeService.findCommandById(Long.valueOf(id)));
        return "recipe/form";
    }

    @GetMapping("/{id}/delete")
    public String deleteById(@PathVariable String id) {
        log.debug("Deleting id: " + id);

        recipeService.deleteById(Long.valueOf(id));
        return "redirect:/";
    }

    @GetMapping("/new")
    public String newRecipe(Model model) {
        model.addAttribute(RECIPE, new RecipeCommand());

        return "recipe/form";
    }

    @PostMapping("")
    public String saveOrUpdate(@ModelAttribute RecipeCommand command) {
        RecipeCommand savedCommand = recipeService.save(command);

        return "redirect:/recipe/" + savedCommand.getId();
    }
}
