export interface Ingredient extends Record<string, string> {
  ingredient: string
}

export interface RecipeSummary extends Record<string, any> {
  id?: string
  title: string
}

export interface Recipe extends RecipeSummary {
  image: string
  instruction: string
  ingredients: Array<Ingredient>
}

export interface FormGuard {
  needsSave(): boolean
}
