package guru.springframework.controllers;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.services.RecipeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/recipe")
public class RecipeController {

    public static final String RECIPE = "recipe";
    public static final String RECIPE_FORM = "recipe/form";
    private final RecipeService recipeService;

    @GetMapping("/{id}")
    public String showById(@PathVariable String id, Model model) {

        model.addAttribute(RECIPE, recipeService.findById(new Long(id)));

        return "recipe/show";
    }

    @GetMapping("/{id}/update")
    public String updateRecipe(@PathVariable String id, Model model) {
        model.addAttribute(RECIPE, recipeService.findCommandById(Long.valueOf(id)));
        return RECIPE_FORM;
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

        return RECIPE_FORM;
    }

    @PostMapping("")
    public String saveOrUpdate(@Valid @ModelAttribute("recipe") RecipeCommand command, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(objectError -> log.debug(objectError.toString()));

            return RECIPE_FORM;
        }
        RecipeCommand savedCommand = recipeService.save(command);

        return "redirect:/recipe/" + savedCommand.getId();
    }
}
