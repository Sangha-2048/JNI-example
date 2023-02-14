import pandas as pd
import numpy as np

X = np.random.randint(5,10000,size = 10000)
y = np.random.randint(5,10000,size = 10000)

df = pd.DataFrame()

df['Num1'] = X
df['Num2'] = y

print(df.head())
print(df.count())

DF = pd.DataFrame()
counter = 1 
while counter <= 100:
    DF = DF.append(df)
    counter = counter + 1

print(len(DF))

DF.to_csv('data.csv')