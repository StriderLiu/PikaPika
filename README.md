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

1. [Done]Filter data of U.S. cities: New_York, Los_Angeles, Chicago, Phoenix, Denver, Indianapolis, Detroit, Boise, Louisville, Monrovia

2. [Processing]Request zip code using Google Map reverse geoencoding api ((latitude, longitude) -> ZIP)
     (1)Need about 48 google keys
     (2)Store every 2500 zip codes in a file, then merge them into one 

3. Add Zip as output feature and remove coordinates columns (need to use SparkSQL)

4. Feature Adjustments (Generate a cleansing-complete csv file)

5. Logistic Regression

6. Dimension reduction
