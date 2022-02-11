import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { RecipeService } from 'src/app/common/services/recipe.service';

@Component({
  selector: 'app-recipe-add',
  templateUrl: './recipe-add.component.html',
  styleUrls: ['./recipe-add.component.scss'],
})
export class RecipeAddComponent implements OnInit {
  form!: FormGroup;

  constructor(private fb: FormBuilder, private recipeService: RecipeService, private router: Router) {}

  ngOnInit(): void {
    this.createForm();
  }

  createForm() {
    this.form = this.fb.group({
      title: ['', [Validators.required, Validators.minLength(3)]],
      image: ['', Validators.required],
      instruction: ['', [Validators.required, Validators.minLength(3)]],
      ingredients: this.fb.array([
        ['', [Validators.required, Validators.minLength(3)]],
      ]),
    });
  }

  get ingredients() {
    return this.form.get('ingredients') as FormArray;
  }

  addIngredients() {
    this.ingredients.push(this.fb.control('', [Validators.required, Validators.minLength(3)]));
  }

  deleteIngredient(index: number) {
    if (index > 0) {
      this.ingredients.removeAt(index);
    }
  }

  addRecipe() {
    console.log('ADD RECIPE', this.form.value);
    this.recipeService.addRecipe(this.form.value);
    this.form.reset();
    this.goBack();
  }

  goBack() {
    this.router.navigate(['/']);
  }
}
