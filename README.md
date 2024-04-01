# Run from Git

1. clone the repository
2. make sure the generated folder in ur project is empty
3. runData and wait for it to finish
4. runClient to start minecraft with the mod installed

# Export Mod as Jar

1. clone repository
2. make sure generated folder in ur project is empty
3. runData and wait fot it to finish
4. run build gradle task
5. jar is in ur project folder under build/libs

# Add own brushing recipes
## Brushing recipe
this is the recipe where u can have the brush and the item in the hand and right click on each of them to brush it.
1. go to core/datagen/ModRecipeProvider there you should find a method named buildRecipes
2. use the BrushingRecipeBuilder class to make a recipe
3. in the end its very important to call the .build method and make sure that there arent two recipes with the same name. The recipe is normally named after the output item
4. the just delete the generated folder and runData again
## Water Brushing recipe
this recipe is meant when u right click with an item on water or have a water bottle in the other hand
steps are the same as with teh brushing recipe just at step 2 use the WaterWashingRecipeBuilder instead.