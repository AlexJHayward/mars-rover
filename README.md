# The Mars Rover Navigator 2000 🤖

## Running the code

I've packaged an `/.sbt` script and `sbt-dist` directory (shamelessly stolen from the PlayFramework SBT starter template) that should hopefully make this easy enough to run. As long as you have Java installed (any version should do) then the scripts here should deal with getting everything else setup for you. If for whatever reason that doesn't work, `brew install sbt` should be all you need to get up and running.

The other option is opening the project in IntelliJ with the Scala plugin and that _should_ be packaged with everything you need to click the big green arrow in `Main.scala`. 

You will need to have Java installed for it to work, I tested the bundled SBT locally with Java 11 and Java 17 and both were fine.

Simply `cd` into the project root directory, then run:

```bash
./sbt run
```

It may pop up with some errors about a JAR missing, you can just ignore those. 

The program will then prompt you through the steps required to run it.

## Structure of the project

Most of the interesting stuff lives in `domain/` and in `Program.scala`. `Main.scala` and `input/` are concerned with all the interaction with the console, our 'side-effects'. The actual 'business' logic lives in `Program`, and `Main` is just a run-harness. 

The only dependency this project has is `scalatest` for testing, the stuff the scripts install is mostly for running SBT itself.

## What I didn't get to/want to do next

- [ ] There needs to be a bunch more test coverage, especially around parsing inputs, I just ran out of time here to add many more erroneous test cases.
- [ ] Currently, the parsing code for each different type lives with that class declaration. This should make things nicely organised for a project of this size, but should it grow any larger, then I'd be tempted to introduce more layers and move the string parsing stuff up to live in the `input` package, to keep the domain more pure.
- [ ] The input-loop is a bit inconsistent. If the grid-size is entered incorrectly, the program exists, whereas the loop for entering the rover positions and instructions lets you keep trying until you get it right. It would be nicer if the grid also let you retry in the same way, but alas, 3 hour limit.
- [ ] `RoverPosition` should have the `applyMovement` function in a companion object.
- [ ] We have implicit syntax classes that contain the functionality for things like `Coordinate`. It would be nicer to have the pure functionality in a companion object, then expose the syntax classes as a separate import, but this is minor and not a big deal.
- [ ] I don't like that `Coordinate.fromString` relies on whitespace being stripped before the string gets there. I'd be tempted to either make that a private function in the class that strips the whitespace, or perhaps introduce a type something like `SpacelessString` that can only be constructed on a string value with no whitespace, or something along those lines.
