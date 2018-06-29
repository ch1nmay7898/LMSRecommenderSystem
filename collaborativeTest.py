import tensorflow as tf
import numpy as np


BOOKS = np.matrix([[1, 0, 5, 4, 0, 0], [0, 4, 0, 0, 2, 0], [0, 0, 3, 1, 0, 2], [0, 0, 0, 1, 0, 0], [0, 5, 0, 0, 3, 0], [0, 0, 0, 0, 1, 4]])

# prepare data
R = np.array(BOOKS)
N = len(BOOKS)
M = len(BOOKS[0])
K = 2  # number of hidden features
P = np.random.rand(N, K)
Q = np.random.rand(M, K)

# input placeholders
ratings = tf.placeholder(tf.float32, name='ratings')

# model variables
tP = tf.Variable(P, dtype=tf.float32, name='P')
tQ = tf.Variable(Q, dtype=tf.float32, name='Q')

# build model
pMultq = tf.matmul(tP, tQ, transpose_b=True)

squared_deltas = tf.square(pMultq - ratings)
loss = tf.reduce_sum(squared_deltas)
tf.summary.scalar('loss', loss)
tf.summary.scalar('sumP', tf.reduce_sum(tP))
tf.summary.scalar('sumQ', tf.reduce_sum(tQ))

# create an optimizer towards minimizing the loss value
optimizer = tf.train.GradientDescentOptimizer(0.01)
train = optimizer.minimize(loss)

sess = tf.Session()
init = tf.global_variables_initializer()
sess.run(init)

# write summaries to TensorBoard log directory
summary_op = tf.summary.merge_all()
file_writer = tf.summary.FileWriter('./logs', sess.graph)

# now run the training loop to reduce the loss
for i in range(5000):
    # also execute a summary operation together with the train node for TensorBoard
    _, summary = sess.run([train, summary_op], {ratings: R})
    file_writer.add_summary(summary, i)

print(np.around(sess.run(pMultq), 3))
