package guru.springframework.controllers;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.services.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/recipe")
public class RecipeController {

    public static final String RECIPE = "recipe";
    private final RecipeService recipeService;

    @RequestMapping("/{id}")
    public String showById(@PathVariable String id, Model model) {

        model.addAttribute(RECIPE, recipeService.findById(new Long(id)));

        return "recipe/show";
    }

    @RequestMapping("/{id}/update")
    public String updateRecipe(@PathVariable String id, Model model) {
        model.addAttribute(RECIPE, recipeService.findCommandById(Long.valueOf(id)));
        return "recipe/form";
    }

    @RequestMapping("/new")
    public String newRecipe(Model model) {
        model.addAttribute(RECIPE, new RecipeCommand());

        return "recipe/form";
    }

    @PostMapping()
    @RequestMapping("")
    public String saveOrUpdate(@ModelAttribute RecipeCommand command) {
        RecipeCommand savedCommand = recipeService.save(command);

        return "redirect:/recipe/" + savedCommand.getId();
    }
}
