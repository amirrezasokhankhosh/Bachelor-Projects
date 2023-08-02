import tensorflow as tf
import numpy as np
import matplotlib.pyplot as plt
from tqdm import tqdm
import pandas as pd
import random

from sklearn.preprocessing import StandardScaler, OneHotEncoder


class Preprocess:
    def __init__(self, train, test):
        self.train = train
        self.test = test
        self.scalar = StandardScaler()
        self.encoder = OneHotEncoder()
    
    def process_train(self):
        train_cat = self.train.select_dtypes(include='object').copy()
        train_num = self.train.select_dtypes(exclude='object').copy()

        train_cat = self.encoder.fit_transform(train_cat).toarray()
        train_num = self.scalar.fit_transform(train_num)
        train_processed = np.concatenate([train_num, train_cat], axis=1)
        
        y_train = train_processed[:, 1].copy()
        X_train = np.delete(train_processed, obj=1, axis=1)
        
        return X_train, y_train.reshape((y_train.shape[0], 1))

    def process_test(self):
        test_cat = self.test.select_dtypes(include='object').copy()
        test_num = self.test.select_dtypes(exclude='object').copy()

        test_cat = self.encoder.transform(test_cat).toarray()
        test_num = self.scalar.transform(test_num)
        test_processed = np.concatenate([test_num, test_cat], axis=1)

        y_test = test_processed[:, 1].copy()
        X_test = np.delete(test_processed, obj=1, axis=1)
        return X_test, y_test.reshape((y_test.shape[0], 1))

def relu(z):
    return tf.math.maximum(0, z)

def linear(X, W, b):
    return (X @ W) + b

def compute_loss(y_hat, y):
    return tf.reduce_mean((y_hat - y) ** 2, axis=0)


df = pd.read_csv('./Fish.csv').sample(frac=1, random_state=1).reset_index(drop=True)

train, test = df.iloc[:-30], df.iloc[-30:]

preprocess = Preprocess(train, test)
X_train, y_train = preprocess.process_train()

layer0, layer1, layer2 = 50, 100, 1

input_size = X_train.shape[1]
output_size = 1

W0 = tf.Variable(np.random.rand(input_size, layer0))*0.1
W1 = tf.Variable(np.random.rand(layer0, layer1))*0.1
W2 = tf.Variable(np.random.rand(layer1, layer2))*0.1
b0 = tf.Variable(np.zeros(layer0))
b1 = tf.Variable(np.zeros(layer1))
b2 = tf.Variable(np.zeros(layer2))

learning_rate = 1e-2
epochs = 1500
batch_size = 16

losses = []
for _ in tqdm(range(epochs)):
    with tf.GradientTape() as tape:
        tape.watch([W0, W1, W2, b0, b1, b2])

        indexes = random.sample(range(len(X_train)), batch_size)
        X_batch = X_train[indexes]
        y_batch = y_train[indexes]

        X_batch = tf.constant(X_batch)
        y_batch = tf.constant(y_batch)

        h0 = relu(linear(X_batch, W0, b0))
        h1 = relu(linear(h0, W1, b1))
        y_hat = linear(h1, W2, b2)
        loss = compute_loss(y_hat, y_batch)
    
    grad = tape.gradient(loss, {
        "W0" : W0,
        "W1" : W1,
        "W2" : W2,
        "b0" : b0,
        "b1" : b1,
        "b2" : b2
    })

    W0 = W0 - learning_rate * grad["W0"]
    W1 = W1 - learning_rate * grad["W1"]
    W2 = W2 - learning_rate * grad["W2"]
    b0 = b0 - learning_rate * grad["b0"]
    b1 = b1 - learning_rate * grad["b1"]
    b2 = b2 - learning_rate * grad["b2"]
    losses.append(loss.numpy()[0])

X_test, y_test = preprocess.process_test()
h0 = relu(linear(X_test, W0, b0))
h1 = relu(linear(h0, W1, b1))
y_hat = linear(h1, W2, b2)
plt.scatter(y=y_hat, x=range(len(y_hat)), c='r')
plt.scatter(y=y_test, x=range(len(y_hat)), c='b')
plt.show()