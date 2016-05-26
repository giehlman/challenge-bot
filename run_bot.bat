@echo off
set args=-useDriver firefox -jsonRecipe C:\Users\test1\Desktop\challenge-bot\src\main\resources\recipe_liveperson_canned.json -useWit
java -jar ./out/artifacts/challenge_bot_jar/challenge-bot.jar %args% > log.txt