package com.tharun.Food.Controller;

import com.tharun.Food.Service.InterfaceService.IngredientsService;
import com.tharun.Food.model.IngredientCategory;
import com.tharun.Food.model.IngredientsItems;
import com.tharun.Food.request.IngredientCategoryRequest;
import com.tharun.Food.request.IngredientRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/ingredients")
public class IngredientController {

    @Autowired
    private IngredientsService ingredientsService;

    @PostMapping("/category")
    public ResponseEntity<IngredientCategory> createIngredientCategory(@RequestBody IngredientCategoryRequest request) throws Exception {
        IngredientCategory category = ingredientsService.createIngredientCategory(request.getName(), request.getRestaurantId());
        return new ResponseEntity<>(category, HttpStatus.CREATED);
    }

    @PostMapping()
    public ResponseEntity<IngredientsItems> createIngredientItem(@RequestBody IngredientRequest request) throws Exception {
        IngredientsItems item = ingredientsService.createIngredientsItems(request.getRestaurantId(), request.getName(), request.getCategoryid());
        return new ResponseEntity<>(item, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/stock")
    public ResponseEntity<IngredientsItems> updateStock(@PathVariable Long id) throws Exception {
        IngredientsItems item = ingredientsService.updateStock(id);
        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    @GetMapping("/restaurant/{id}")
    public ResponseEntity<List<IngredientsItems>> getRestaurantIngredients(@PathVariable Long id) throws Exception {
        List<IngredientsItems> items = ingredientsService.findRestaurantsIngredients(id);
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @GetMapping("/restaurant/{id}/category")
    public ResponseEntity<List<IngredientCategory>> getRestaurantCategory(@PathVariable Long id) throws Exception {
        List<IngredientCategory> categories = ingredientsService.findIngredientCategoryByRestaurantID(id);
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }
}
