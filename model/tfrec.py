import numpy as np
import tensorrec
import scipy.sparse as sp

# Build the model with default parameters
model = tensorrec.TensorRec()

# Generate some dummy data
interactions, user_features, item_features = tensorrec.util.generate_dummy_data(
    num_users=5,
    num_items=15,
    interaction_density=.07
)

# Fit the model for 5 epochs
model.fit(interactions, user_features, item_features, epochs=5, verbose=True)

# Predict scores and ranks for all users and all items
predictions = model.predict(user_features=user_features,
                            item_features=item_features)
predicted_ranks = model.predict_rank(user_features=user_features,
                                     item_features=item_features)

# Calculate and print the recall at 10
r_at_k = tensorrec.eval.recall_at_k(predicted_ranks, interactions, k=10)
print(np.mean(r_at_k))

it_fe = np.array(
    [[5000, 60, 150, 18, 59.3, ],
     [4500, 150, 400, 18.1, 59.8, ],
     [3500, 40, 100, 17.9, 58.9, ],
     [4200, 200, 200, 18.15, 59.5, ],
     [3300, 125, 450, 18.08, 59.015, ],
     [2300, 60, 150, 11.9, 57.7, ],
     [2500, 250, 300, 11.8, 57.6, ],
     [2600, 300, 1000, 11.85, 57.55, ],
     [2200, 50, 150, 11.98, 57.96, ]]
)
it_fe = sp.csr_matrix(it_fe)
print(it_fe)
'''
import numpy as np
import scipy.stats as st

item_features = np.array(
    [[5000,      60,     150,      18,      59.3,  ],
     [4500,     150,     400,      18.1,     59.8,  ],
     [3500,      40,     100,      17.9,     58.9,  ],
     [4200,     200,     200,      18.15,    59.5,  ],
     [3300,     125,     450,      18.08,    59.015,],
     [2300,      60,     150,      11.9,     57.7,  ],
     [2500,     250,     300,      11.8,     57.6,  ],
     [2600,     300,    1000,      11.85,    57.55, ],
     [2200,      50,     150,      11.98,   57.96, ]]
 )

norm_item_features = st.zscore(item_features, axis=0)

# Yields new item features that are not parallel:

array([[ 1.70332997, -0.86065224, -0.64808756,  0.8791161 ,  0.84503229],
       [ 1.18890146,  0.14241008,  0.2926847 ,  0.91175655,  1.44152567],
       [ 0.16004443, -1.08355498, -0.83624201,  0.84647565,  0.36783759],
       [ 0.88024435,  0.69966693, -0.45993311,  0.92807677,  1.08362964],
       [-0.04572698, -0.13621834,  0.48083916,  0.90522846,  0.50503106],
       [-1.07458401, -0.86065224, -0.64808756, -1.1119513 , -1.06374653],
       [-0.8688126 ,  1.25692378, -0.0836242 , -1.14459175, -1.18304521],
       [-0.7659269 ,  1.81418063,  2.55053813, -1.12827153, -1.24269455],
       [-1.17746971, -0.97210361, -0.64808756, -1.08583894, -0.75356997]])
'''
