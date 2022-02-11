import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Recipe } from 'src/app/common/models';
import { RecipeService } from 'src/app/common/services/recipe.service';

@Component({
  selector: 'app-recipe-detail',
  templateUrl: './recipe-detail.component.html',
  styleUrls: ['./recipe-detail.component.scss'],
})
export class RecipeDetailComponent implements OnInit {
  id!: string;
  recipe!: Recipe;

  constructor(
    private recipeService: RecipeService,
    private router: Router,
    private activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.id = this.activatedRoute.snapshot.params['recipeId'];
    this.populateRecipe();
  }

  populateRecipe() {
    this.recipeService.getRecipe(this.id).then((recipe) => {
      this.recipe = recipe;
    });
  }
}
