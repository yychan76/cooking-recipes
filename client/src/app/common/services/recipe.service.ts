import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { lastValueFrom } from 'rxjs';
import { Recipe, RecipeSummary } from '../models';

const URL_API_GET_ALL = '/api/recipes';
const URL_API_GET_BY_ID = '/api/recipe/';
const URL_API_POST = '/api/recipe/';

@Injectable({
  providedIn: 'root',
})
export class RecipeService {
  recipes: RecipeSummary[] = [];

  constructor(private http: HttpClient) {}

  getAllRecipes(): Promise<any> {
    return lastValueFrom(this.http.get<any>(URL_API_GET_ALL));
  }

  getRecipe(recipeId: string): Promise<Recipe> {
    return lastValueFrom(this.http.get<Recipe>(`${URL_API_GET_BY_ID}${recipeId}`));
  }

  addRecipe(recipe: Recipe) {
    return lastValueFrom(this.http.post<Recipe>(URL_API_POST, recipe));
  }
}
