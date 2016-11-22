# PikaPika
Pokemon Go Analysis-Poke Monsters Spawn Prediction

Big Data Systems Engineer using Scala

Professor Robin Hillyard (Github @rchillyard/Scalaprof)

By : Wenbo Liu, Shuxian Wu
     
You can find our presentation on Prezi.com at the link:

http://prezi.com/4bapseyrrq5c/?utm_campaign=share&utm_medium=copy&rc=ex0share

# Data Source
Predict'em All from Kaggle: https://www.kaggle.com/semioniy/predictemall

# Platform

1. Experiment on Zeppelin

2. Transplant to IDE

# Next Step

1. Data Cleansing
     
     (1) Drop these columns: pokemonId, appearedLocalTime, X_id, cellId_90m, cellId_180m, cellId_370m, cellId_730m, cellId_1460m, cellId_2920m, cellId_5850m, appearedDayOfWeek, appearedMonth, appearedYear, weatherIcon
     
     (2) Replace all "TURE" and "FALSE" with "1" and "0"
     
     (3) Represent appearedTimeOfDay, city, continent and weather as their corresponding number (start from 1)

2. Logistic Regression

3. Dimension reduction
