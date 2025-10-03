# Searching the bug 

## Step 1 - Where to look
- After compiling the classes for the first time I notice that we're failing test 9.
- Looking at the expected and actual values for command `java Simulator 1 5 30 35` I notice that the first problem is encountered with the field `Creature.lab`:

```sh
                          Creature.lab
                                     *
expected:   <...76 62 y43 43 y12 38 [c74 ...
output:     <...76 62 y43 43 y12 38 [y74 ...
```

## Step 2 - Searching for the label
- Following the instructions, I start to look where the `label` changes inside the class `Cat`.
- I notice that `.lab` is only used on 3 occasions: one in the class' contructor (changes the value to `y` only one time, the bug is not here) and twice in the function `setTargetMouse`.
- Since it's the only function that can change a `Cat`'s `label`, I look where and how this function is used.
- `setTargetMouse` is only used in another function: `findClosestMouse()`; this might be the the buggy function since every our `Cat` (check function `takeAction`) checks if it can eat, if not goes to the closestMouse and **should** turn into **cyan** if it finds something (it's not happening, that our bug).

## Step 3 - Print debugging

See `final_error.log`.
