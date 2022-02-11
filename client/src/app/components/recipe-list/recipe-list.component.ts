import { Component, OnInit } from '@angular/core';
import { RecipeSummary } from 'src/app/common/models';
import { RecipeService } from 'src/app/common/services/recipe.service';

@Component({
  selector: 'app-recipe-list',
  templateUrl: './recipe-list.component.html',
  styleUrls: ['./recipe-list.component.scss'],
})
export class RecipeListComponent implements OnInit {
  recipes: RecipeSummary[] = [];
  constructor(private recipeService: RecipeService) {}

  ngOnInit(): void {
    this.recipeService.getAllRecipes().then(result => {
      result.forEach((recipe: RecipeSummary) => {
        this.recipes.push(recipe as RecipeSummary);
      })
    });
  }
}
