# challenge-bot

Little coding challenge for creating a low-level chatbot driven by Selenium2.

Type -help for an overview of the input arguments or see method parseArgs() in chatbot/ChallengeBot.java.

```sh
$ java -jar ./out/artifacts/challenge_bot_jar/challenge-bot.jar
	-help
```

The default invocation (as given in run_bot.bat) is

```sh
$ java -jar ./out/artifacts/challenge_bot_jar/challenge-bot.jar
	-useDriver firefox 
	-jsonRecipe C:\Users\test1\Desktop\challenge-bot\src\main\resources\recipe_liveperson_canned.json 
	-useWit
```

If you want to use the bot to run with other webchats, define your own recipe. As a template you can use the *.json recipes given in source. The recipes get evaluated in method interpretRecipe(WebDriver driver, JSONArray instructions) in chatbot/recipes/JSONRecipe.java.
